package com.hldj.hmyg.base;

import android.app.Activity;
import android.support.annotation.Keep;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import net.tsz.afinal.annotation.view.EventListener;
import net.tsz.afinal.annotation.view.Select;
import net.tsz.afinal.annotation.view.ViewInject;

import java.lang.reflect.Field;


/**
 * 罗大傻。修改反射方法。能够获取父类。  getFields
 */

public class MyFinalActivity extends Activity {
    public MyFinalActivity() {
    }

    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        initInjectedView(this);
    }

    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        initInjectedView(this);
    }

    public void setContentView(View view) {
        super.setContentView(view);
        initInjectedView(this);
    }

    public static void initInjectedView(Activity activity) {
        initInjectedView(activity, activity.getWindow().getDecorView());
    }

    public static void initInjectedView(Object injectedSource, View sourceView) {
//        Field[] fields = injectedSource.getClass().getDeclaredFields();
        Field[] fields = injectedSource.getClass().getFields();
        if (fields != null && fields.length > 0) {
            Field[] var6 = fields;
            int var5 = fields.length;

            for (int var4 = 0; var4 < var5; ++var4) {
                Field field = var6[var4];

                try {
                    field.setAccessible(true);
                    if (field.get(injectedSource) == null) {
                        ViewInject e = (ViewInject) field.getAnnotation(ViewInject.class);
                        if (e != null) {
                            int viewId = e.id();
                            field.set(injectedSource, sourceView.findViewById(viewId));
                            setListener(injectedSource, field, e.click(), MyFinalActivity.Method.Click);
                            setListener(injectedSource, field, e.longClick(), MyFinalActivity.Method.LongClick);
                            setListener(injectedSource, field, e.itemClick(), MyFinalActivity.Method.ItemClick);
                            setListener(injectedSource, field, e.itemLongClick(), MyFinalActivity.Method.itemLongClick);
                            Select select = e.select();
                            if (!TextUtils.isEmpty(select.selected())) {
                                setViewSelectListener(injectedSource, field, select.selected(), select.noSelected());
                            }
                        }
                    }
                } catch (Exception var10) {
                    var10.printStackTrace();
                }
            }
        }

    }

    private static void setViewSelectListener(Object injectedSource, Field field, String select, String noSelect) throws Exception {
        Object obj = field.get(injectedSource);
        if (obj instanceof View) {
            ((AbsListView) obj).setOnItemSelectedListener((new EventListener(injectedSource)).select(select).noSelect(noSelect));
        }

    }

    @Keep
    private static void setListener(Object injectedSource, Field field, String methodName, MyFinalActivity.Method method) throws Exception {
        if (methodName != null && methodName.trim().length() != 0) {
            Object obj = field.get(injectedSource);
            switch (method) {
                case Click:
                    if (obj instanceof View) {
                        ((View) obj).setOnClickListener((new EventListener(injectedSource)).click(methodName));
                    }
                    break;
                case LongClick:
                    if (obj instanceof View) {
                        ((View) obj).setOnLongClickListener((new EventListener(injectedSource)).longClick(methodName));
                    }
                    break;
                case ItemClick:
                    if (obj instanceof AbsListView) {
                        ((AbsListView) obj).setOnItemClickListener((new EventListener(injectedSource)).itemClick(methodName));
                    }
                    break;
                case itemLongClick:
                    if (obj instanceof AbsListView) {
                        ((AbsListView) obj).setOnItemLongClickListener((new EventListener(injectedSource)).itemLongClick(methodName));
                    }
            }

        }
    }

    @Keep
    private static enum Method {
        Click,
        LongClick,
        ItemClick,
        itemLongClick;

        private Method() {
        }
    }
}
