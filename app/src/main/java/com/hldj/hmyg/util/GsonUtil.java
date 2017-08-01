package com.hldj.hmyg.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

/**
 * Created by Administrator on 2017/4/12.
 */

public class GsonUtil<T> {
    private static Gson gson;


    public GsonUtil() {

    }

    public static void getJson() {

    }


    public static Gson getGson() {
        if (gson == null) {
            GsonBuilder gb = new GsonBuilder()
                    .registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory())
                    .serializeNulls()
                    .registerTypeAdapter(String.class, new StringConverter());
            gson = gb.create();
            //gson 非空统一解决
        }
        return gson;
    }


//    public T formateJson2Bean(String json, Class cls) {
//
//
//        return (T) getGson().fromJson(json, cls);
//
//    }

    public static <E> E formateJson2Bean(String json, Class<E> cls) {

//        System.out.println(t.getClass());


//        return (E) getGson().fromJson(json, cls);
        return getGson().fromJson(json, cls);
    }

    public static <E> E formateJson2Bean(String json, Type beanType) {

//        System.out.println(t.getClass());
//        return (E) getGson().fromJson(json, cls);
        return (E) getGson().fromJson(json, beanType);
    }


    public static String Bean2Json(Object object) {
        String json = "";
//        System.out.println(t.getClass());
        json = getGson().toJson(object);
        return json;
    }


}
