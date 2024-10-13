package com.chen.mybatisreload.core;

import com.chen.mybatisreload.core.bean.MapperDirectory;
import com.chen.mybatisreload.core.bean.MapperXml;
import com.chen.mybatisreload.core.bean.ReloadContext;
import com.chen.mybatisreload.core.exceptions.ReloadException;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.session.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 热加载服务。
 *
 * @author chen
 */
@Component
public class MyBatisReloadService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyBatisReloadService.class);
    private Configuration configuration;

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
        ClassPathResource cpr = new ClassPathResource("classpath:" + filepath);
        if (!cpr.exists()) {
            throw new ReloadException(String.format("文件[%s]不存在", filepath));
        }
        try (InputStream in = cpr.getInputStream()) {
            XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(in, configuration, cpr.toString(),
                    configuration.getSqlFragments());
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
}
