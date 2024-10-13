package com.chen.mybatisreload.core.bean;

import java.util.List;

/**
 * 映射目录。
 *
 * @author chen
 */
public class MapperDirectory {
    /**
     * 目录。
     */
    private String directory;
    /**
     * 文件清单。
     */
    private List<MapperXml> mapperXmlList;

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public List<MapperXml> getMapperXmlList() {
        return mapperXmlList;
    }

    public void setMapperXmlList(List<MapperXml> mapperXmlList) {
        this.mapperXmlList = mapperXmlList;
    }
}
