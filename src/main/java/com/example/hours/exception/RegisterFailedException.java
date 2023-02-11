package com.example.hours.exception;

public class RegisterFailedException extends RuntimeException {

    private String msg;

    public RegisterFailedException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public RegisterFailedException(String msg, Throwable cause) {
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
