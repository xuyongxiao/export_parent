package com.itheima.web.exception;

/**
 * 自定义异常处理
 */
public class CustomerException extends Exception {

    private String message;

    public CustomerException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage(){
       return message;
    }

}
