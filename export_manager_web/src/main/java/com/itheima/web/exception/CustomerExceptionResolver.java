package com.itheima.web.exception;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义异常处理器
 */
@Component
public class CustomerExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        //创建返回值对象
        ModelAndView mv = new ModelAndView();
        //设置响应视图
        mv.setViewName("error");
        if (e instanceof CustomerException ){
            //业务异常
            mv.addObject("errorMsg", e.getMessage());
        }else{
            //系统异常
            mv.addObject("errorMsg","服务器忙...");
            e.printStackTrace();
        }
        return mv;
    }
}
