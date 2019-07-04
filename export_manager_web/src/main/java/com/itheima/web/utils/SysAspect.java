package com.itheima.web.utils;

import com.itheima.domain.system.SysLog;
import com.itheima.domain.system.User;
import com.itheima.service.system.ISysLogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Date;

@Component
@Aspect
public class SysAspect {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ISysLogService sysLogService;

    /**
     * 环绕通知记录日志
     * @param pjp
     * @return
     */
    @Around("execution(* com.itheima.web.controller.*.*.*(..))")
    public Object arroundAdvice(ProceedingJoinPoint pjp){
        //获取当前登录的用户
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        //判断用户是否登录
        if (user != null){
            //1、从session中获取方法签名
            Signature signature = pjp.getSignature();
            //2、判断是不是方法签名
            if (signature instanceof MethodSignature){
                //强转
                MethodSignature methodSignature = (MethodSignature) signature;
                ///3、获取当前执行的方法
                Method method = methodSignature.getMethod();
                //4、判断当前方法上是否有RequestMapping注解
                boolean hasAnnotated = method.isAnnotationPresent(RequestMapping.class);
                if (hasAnnotated){
                    //5、取出注解
                    RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                    //6、得到注解的属性，用于填充SysLog对象
                    String[] value = requestMapping.value();
                    StringBuilder ss = new StringBuilder();
                    if (value.length>1){
                        for (String path : value) {
                            ss.append(path).append("，");
                        }
                    }else{
                        ss.append(value);
                    }
                    String name = requestMapping.name();
                    //7、创建日志对象
                    SysLog sysLog = new SysLog();
                    sysLog.setIp(request.getRemoteAddr());
                    sysLog.setTime(new Date());
                    sysLog.setMethod(method.getName());
                    sysLog.setAction(name);
                    sysLog.setUserName(user.getUserName());
                    sysLog.setCompanyId(user.getCompanyId());
                    sysLog.setCompanyName(user.getCompanyName());
                    //8、保存日志
                    sysLogService.save(sysLog);
                }
            }
        }

        //获取执行方法所需的参数
        Object[] args = pjp.getArgs();

        //定义返回值
        Object proceed = null;
        try {
            //切入点方法的执行
            proceed = pjp.proceed(args);
        }catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return proceed;
    }

}
