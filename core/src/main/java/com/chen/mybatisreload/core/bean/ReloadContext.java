package com.chen.mybatisreload.core.bean;

import java.util.List;

/**
 * 重新加载的内容。
 *
 * @author chen
 */
public class ReloadContext {
    private List<MapperDirectory> xmlList;
    /**
     * 请求是否成功。
     */
    private boolean success;
    /**
     * 请求消息。
     */
    private String message;

    public List<MapperDirectory> getXmlList() {
        return xmlList;
    }

    public void setXmlList(List<MapperDirectory> xmlList) {
        this.xmlList = xmlList;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
