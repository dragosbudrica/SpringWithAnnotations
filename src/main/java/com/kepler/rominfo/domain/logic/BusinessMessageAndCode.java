package com.kepler.rominfo.domain.logic;

import java.util.Locale;

public class BusinessMessageAndCode {

    private String message;
    private int code;
    private Object object;
    private Locale locale;

    public static final int ERROR = -1;
    public static final int SUCCESS = 0;

    public BusinessMessageAndCode(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public BusinessMessageAndCode(Object object, String message, int code) {
        this.object = object;
        this.message = message;
        this.code = code;
    }

    public BusinessMessageAndCode(Object object, Locale locale, int code) {
        this.object = object;
        this.locale = locale;
        this.code = code;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
