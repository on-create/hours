package com.example.hours.exception;

public class DraftException extends RuntimeException {

    private String msg;

    public DraftException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public DraftException(String msg, Throwable cause) {
        super(msg, cause);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
