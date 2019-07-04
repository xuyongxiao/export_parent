package com.itheima.web.convertes;



import com.itheima.common.utils.UtilFuns;
import org.springframework.core.convert.converter.Converter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StringToDateConverter implements Converter<String, Date> {

    //支持注入
    private String pattern;

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public Date convert(String source) {
        try {
            //给解析规则设置默认值
            if (UtilFuns.isEmpty(pattern)){
                pattern = "yyyy-MM-dd";
            }
            //使用解析规则创建解析对象
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            //解析日期
            Date date = format.parse(source);
            //返回
            return date;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
