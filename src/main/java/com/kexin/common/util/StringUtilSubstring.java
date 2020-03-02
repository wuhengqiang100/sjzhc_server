package com.kexin.common.util;

public class StringUtilSubstring {

    /**
     * 对表结构中的一对多string重新截取组装书名,eg:roleString ,groupString
     * @param allString  所有字段
     * @param oldString  需要替换的字段
     * @param newString  需要重新组装的字段
     * @return
     */
    public static String subStringReplace(String allString ,String oldString ,String newString ){
        oldString=oldString+" ";
        newString=newString+" ";

        int a=allString.indexOf(oldString);

        if(a>=0){//判断包含字段
//            String strDelete=allString.substring(a,a+oldString.length());//需要删除的字符

            String strBefore=allString.substring(0,a);//删除的字符前面保留的字符
            String strAfter=allString.substring(a+oldString.length(),allString.length());//删除的字符后面保留的字符
            String strFinal=strBefore+strAfter+newString;
            return strFinal;//返回处理后的最终字段

        }else{
            return null;
        }
    }
}
