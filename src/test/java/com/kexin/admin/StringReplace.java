package com.kexin.admin;

public class StringReplace {
    public static void main(String[] args) {

        String string="good morning everyone ";

        String sub="morning ";

        int a=string.indexOf(sub);

        if(a>=0){

            System.out.println("morning在字符串中的位置:"+a);

            String strDelete=string.substring(a,a+sub.length());//需要删除的字符

            String strBefore=string.substring(0,a);//删除的字符前面保留的字符
            String strAfter=string.substring(a+sub.length(),string.length());//删除的字符后面保留的字符
            String strFinal=strBefore+strAfter;

            System.out.println("你需要的结果是:"+strFinal);

            System.out.println("删掉的字符是:"+strDelete);

        }else{

            System.out.println("不存在");

        }

    }
}
