package com.hldj.hmyg.Ui.friend.util;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;


public class EnumUtils {
	
	public static String toJson(Class class1){
		StringBuffer sbf = new StringBuffer();
		try{
	        Method[] methods = class1.getDeclaredMethods();
	        if(class1.isEnum()){
	            List<?> list = Arrays.asList(class1.getEnumConstants());
	            for(Object enu : list){
	            	String value = "";
	            	String text = "";
	                for(Method method : methods){
	                    if(method.getName().equals("getEnumValue")){
							value = method.invoke(enu).toString();
	                    }
	                    if(method.getName().equals("getEnumText")){
	                    	text = method.invoke(enu).toString();
	                    }
	                }
	                sbf.append("{id:'" + value + "',text:'" + text + "'},");
	            }
	            if(sbf.length()>0) sbf.deleteCharAt(sbf.length()-1);
	        }
		}catch(Exception e){
			e.printStackTrace();
		}
        return "[" + sbf.toString() + "]";
	}
	
	public static String toJsonExclude(Class class1,List<String> excludeList){
		StringBuffer sbf = new StringBuffer();
		try{
	        Method[] methods = class1.getDeclaredMethods();
	        if(class1.isEnum()){
	            List<?> list = Arrays.asList(class1.getEnumConstants());
	            for(Object enu : list){
	            	String value = "";
	            	String text = "";
	                for(Method method : methods){
	                    if(method.getName().equals("getEnumValue")){
							value = method.invoke(enu).toString();
	                    }
	                    if(method.getName().equals("getEnumText")){
	                    	text = method.invoke(enu).toString();
	                    }
	                }
	                if(null!=excludeList && excludeList.size()>0){
	                	if(!excludeList.contains(value)){
	                		sbf.append("{id:'" + value + "',text:'" + text + "'},");
	                	}
	                }else{
	                	sbf.append("{id:'" + value + "',text:'" + text + "'},");
	                }
	            }
	            if(sbf.length()>0) sbf.deleteCharAt(sbf.length()-1);
	        }
		}catch(Exception e){
			e.printStackTrace();
		}
        return "[" + sbf.toString() + "]";
	}
	
	
	
	public static String toMapJson(Class class1){
		StringBuffer sbf = new StringBuffer();
		try{
	        Method[] methods = class1.getDeclaredMethods();
	        if(class1.isEnum()){
	            List<?> list = Arrays.asList(class1.getEnumConstants());
	            for(Object enu : list){
	            	String value = "";
	            	String text = "";
	                for(Method method : methods){
	                    if(method.getName().equals("getEnumValue")){
							value = method.invoke(enu).toString();
	                    }
	                    if(method.getName().equals("getEnumText")){
	                    	text = method.invoke(enu).toString();
	                    }
	                }
	                sbf.append("\"" + value + "\"" + ":" + "\"" + text + "\",");
	            }
	            if(sbf.length()>0) sbf.deleteCharAt(sbf.length()-1);
	        }
		}catch(Exception e){
			e.printStackTrace();
		}
        return "{" + sbf.toString() + "}";
	}
	
	public static String listToJson(List<Object> list){
		StringBuffer sbf = new StringBuffer();
		try {
	         if(list.size()>0){
		for (Object object : list) {
			String value = "";
        	String text = "";
			Class<?> userClass = Class.forName(object.getClass().getName());  //返回与带有给定字符串名的类或接口相关联的 Class 对象			
			Method[] methods = userClass.getMethods();//获得该类的所有方法
			for (Method method : methods) {
				 if(method.getName().equals("getEnumValue")){
						value = method.invoke(object).toString();
                 }
                 if(method.getName().equals("getEnumText")){
                 	text = method.invoke(object).toString();
                 }
			}
			sbf.append("{id:'" + value + "',text:'" + text + "'},");
			}
			if(sbf.length()>0) sbf.deleteCharAt(sbf.length()-1);
			}
			} catch (Exception e) {
				e.getStackTrace();
			}
		return "[" + sbf.toString() + "]";
	}
	
}
