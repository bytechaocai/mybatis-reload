package com.chen.mybatisreload.core;

import com.chen.mybatisreload.core.bean.MapperDirectory;
import com.chen.mybatisreload.core.bean.MapperXml;
import com.chen.mybatisreload.core.bean.ReloadContext;
import com.chen.mybatisreload.core.exceptions.ReloadException;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMap;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 热加载服务。
 *
 * @author chen
 */
@Component
public class MyBatisReloadService implements InitializingBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyBatisReloadService.class);
    @Resource
    private SqlSessionFactory sqlSessionFactory;

    private Map<String, MappedStatement> mappedStatements;
    private Map<String, ResultMap> resultMaps;
    private Map<String, ParameterMap> parameterMaps;
    private Map<String, XNode> sqlFragments;


    /**
     * 重载所有映射接口。
     *
     * @return 重载结果。
     */
    public ReloadContext reloadAllMapperInterface() {
        return new ReloadContext();
    }

    /**
     * 重新加载mapper.
     *
     * @param reloadContext 要加载的mapper。
     *
     * @return
     */
    public ReloadContext reloadMapper(ReloadContext reloadContext) {
        LOGGER.info("开始加载");
        List<MapperDirectory> xmlList = reloadContext.getXmlList();
        for (MapperDirectory mapperDirectory : xmlList) {
            LOGGER.info("当前加载的目录:{}", mapperDirectory.getDirectory());
            for (MapperXml mapperXml : mapperDirectory.getMapperXmlList()) {
                LOGGER.info("当前加载的文件:{}", mapperXml.getFilename());
                reloadXml(mapperXml, mapperDirectory.getDirectory());
                LOGGER.info("文件{}加载完成", mapperXml.getFilename());
            }
            LOGGER.info("目录{}加载完成", mapperDirectory.getDirectory());
        }
        LOGGER.info("加载完成");
        return reloadContext;
    }

    /**
     * 重新加载单个xml。
     *
     * @param mapperXml 要加载的xml。
     * @param directory 目录，以斜杠结尾。
     */
    private void reloadXml(MapperXml mapperXml, String directory) {
        String filepath = directory + mapperXml.getFilename();
        ClassPathResource cpr = new ClassPathResource(filepath);
        if (!cpr.exists()) {
            throw new ReloadException(String.format("文件[%s]不存在", filepath));
        }
        Configuration configuration = sqlSessionFactory.getConfiguration();
        try (InputStream in = cpr.getInputStream()) {
            XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(in, configuration, cpr.toString(),
                    configuration.getSqlFragments());
            preReloadXml(xmlMapperBuilder);
            xmlMapperBuilder.parse();
            mapperXml.setSuccess(true);
            mapperXml.setMessage("加载成功");
        } catch (@SuppressWarnings("java:S2139") IOException e) {
            mapperXml.setSuccess(false);
            mapperXml.setMessage(e.getMessage() + ",cause: " + e.getCause());
            LOGGER.info("读取xml文件出现异常{}", e.getMessage());
            throw new ReloadException(e);
        }
    }

    /**
     * 删除当前文件已经解析的内容。
     *
     * @param xmlMapperBuilder 映射构建器。
     */
    @SuppressWarnings({"java:S3011", "DataFlowIssue", "java:S2259"})
    private void preReloadXml(XMLMapperBuilder xmlMapperBuilder) {
        Field field = ReflectionUtils.findField(xmlMapperBuilder.getClass(), "parser");
        field.setAccessible(true);
        XPathParser parser;
        try {
            parser = (XPathParser) field.get(xmlMapperBuilder);
        } catch (IllegalAccessException e) {
            throw new ReloadException(e);
        }
        XNode xNode = parser.evalNode("/mapper");
        String namespace = xNode.getStringAttribute("namespace");
        LOGGER.info("开始删除命名空间{}中已经解析的内容", namespace);
        remove(namespace, mappedStatements);
        remove(namespace, resultMaps);
        remove(namespace, parameterMaps);
        remove(namespace, sqlFragments);
        LOGGER.info("删除完成");
    }

    @SuppressWarnings({"unchecked", "java:S3740", "rawtypes"})
    private void remove(String namespace, Map map) {
        List<String> keyList = (List<String>) map.keySet().stream().filter(p -> p.toString().startsWith(namespace))
                .collect(Collectors.toList());
        // 假设有一个mapper接口，全类名为com.example.Mapper，其中有一个方法selectOne，那么这条映射会有两个id，
        // com.example.Mapper.selectOne和selectOne。
        // 只有xml文件没有接口的sql只有一个id
        List<String> simpleKeyList = keyList.stream().map(p -> p.substring(namespace.length() + 1))
                .collect(Collectors.toList());
        keyList.forEach(map::remove);
        simpleKeyList.forEach(map::remove);
    }

    /**
     * 通过反射获取statement,resultMaps,parameterMaps,sqlFragments对象。
     *
     * @param fieldName 字段名。
     */
    @SuppressWarnings({"java:S3011", "DataFlowIssue", "java:S2259"})
    private Object getFieldValueInConfiguration(String fieldName) {
        Field field = ReflectionUtils.findField(Configuration.class, fieldName);
        field.setAccessible(true);
        return ReflectionUtils.getField(field, this.sqlSessionFactory.getConfiguration());
    }


    @Override
    @SuppressWarnings("unchecked")
    public void afterPropertiesSet() throws Exception {
        LOGGER.info("mybatis已经初始化完成，开始提取xml中的statement,resultMaps,parameterMaps,sqlFragments");
        Configuration configuration = sqlSessionFactory.getConfiguration();
        this.mappedStatements = (Map<String, MappedStatement>) getFieldValueInConfiguration("mappedStatements");
        this.sqlFragments = configuration.getSqlFragments();
        this.parameterMaps = (Map<String, ParameterMap>) getFieldValueInConfiguration("parameterMaps");
        this.resultMaps = (Map<String, ResultMap>) getFieldValueInConfiguration("resultMaps");
        LOGGER.info("提取完成");
    }
}
