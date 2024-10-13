package com.chen.mybatisreload.core.exceptions;

/**
 * 重载时的异常。
 *
 * <p>如果是复制源代码到项目中，可以将异常替换为自己项目内的异常。</p>
 *
 * @author chen
 */
public class ReloadException extends RuntimeException {
    public ReloadException() {
        super();
    }

    public ReloadException(String message) {
        super(message);
    }

    public ReloadException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReloadException(Throwable cause) {
        super(cause);
    }

    protected ReloadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
