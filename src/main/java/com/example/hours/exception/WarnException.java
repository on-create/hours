package com.example.hours.exception;

/**
 * 警告信息
 */
public class WarnException extends HourException {

    public WarnException(String msg) {
        super(msg);
    }

    public WarnException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
