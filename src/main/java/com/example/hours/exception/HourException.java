package com.example.hours.exception;

public class HourException extends RuntimeException {

    /**
     * 错误信息
     */
    private String message;

    public HourException(String msg) {
        super(msg);
        this.message = msg;
    }

    public HourException(String msg, Throwable cause) {
        super(msg, cause);
        this.message = msg;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
