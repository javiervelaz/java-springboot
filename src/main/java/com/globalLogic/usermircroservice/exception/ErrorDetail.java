package com.globalLogic.usermircroservice.exception;

import java.sql.Timestamp;

public class ErrorDetail {
    private Timestamp timestamp;
    private int code;
    private String detail;

    public ErrorDetail(String string, int value) {
    }
    public Timestamp getTimestamp() {
        return timestamp;
    }
    public int getCode() {
        return code;
    }
    public String getDetail() {
        return detail;
    }
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public void setDetail(String detail) {
        this.detail = detail;
    }
}