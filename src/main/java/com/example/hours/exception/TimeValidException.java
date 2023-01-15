package com.example.hours.exception;

public class TimeValidException extends RuntimeException{

    private String msg;

    public TimeValidException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public TimeValidException(String msg, Throwable cause) {
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
