package com.kexin.common.util.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class testDemo2 {

    @MyAnnotation2(age = 22 , name = "wu小强")
    public void test2(){
    }

    //当参数只有一个的时候,参数名可以省略,及下面的value
    // @MyAnnotation3(value="wu小强")
    @MyAnnotation3("wu小强")
    public void test3(){

    }
}


@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@interface MyAnnotation2{
    // 注解的参数 : 参数类型 + 参数名 () +默认值
    String name() default "";
    int age();
    int id() default -1;//如果默认值为-1,代表不存在
    String[] schools() default  "四川大学,成都大学";

}

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@interface MyAnnotation3{
    String value();
}

