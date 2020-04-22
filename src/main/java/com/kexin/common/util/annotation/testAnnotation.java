package com.kexin.common.util.annotation;

import java.lang.annotation.*;

public class testAnnotation {

}
//定义一个注解
//Target 表示这个注解可以用在哪些地方
//FIELD,METHOD,PARAMETER,CONSTRUCTOR,LOCAL_VARIABLE,ANNOTATION_TYPE,PACKAGE,TYPE_PARAMETER,TYPE_USE,MODULE;
@Target(value = {ElementType.METHOD,ElementType.TYPE})

//Retention 表示我们的注解在什么地方有效  SOURCE,CLASS,RUNTIME;
@Retention(value = RetentionPolicy.RUNTIME)

//Documented 表示是否将这个注解生成在javadoc文档中
@Documented

//Inherited 表示子类可以继承父类的注解
@Inherited
@interface  MyAnnotation{

}
