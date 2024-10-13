package com.chen.mybatisreload.core.bean;

/**
 * 映射xml。
 *
 * @author chen
 */
public class MapperXml {
    /**
     * 文件名。
     */
    private String filename;
    /**
     * 刷新是否成功。
     */
    private boolean success;
    /**
     * 刷新结果，如果是异常，则返回异常message和cause.message。
     */
    private String message;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
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
