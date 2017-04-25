package com.hldj.hmyg.DaoBean.SaveJson;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/4/24.
 */

@Entity
public class SavaBean {

    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "JSON")
    private String json;

    @Override
    public String toString() {
        return "SavaBean{" +
                "id=" + id +
                ", json='" + json + '\'' +
                '}';
    }

    public String getJson() {
        return this.json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Generated(hash = 1260212127)
    public SavaBean(Long id, String json) {
        this.id = id;
        this.json = json;
    }

    @Generated(hash = 2063251674)
    public SavaBean() {
    }

}
