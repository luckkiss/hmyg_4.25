package com.mrwujay.cascade.model;

import android.widget.Checkable;
import android.widget.CompoundButton;

import java.util.List;

public class ProvinceModel implements CompoundButton.OnCheckedChangeListener, Checkable {
    private String name;
    private String cityCode;
    private List<CityModel> cityList;

    private boolean isChecked = false;

    public ProvinceModel(String name, String cityCode, List<CityModel> cityList) {
        super();
        this.name = name;
        this.cityCode = cityCode;
        this.cityList = cityList;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }


    public ProvinceModel(String name, String cityCode) {
        this.name = name;
        this.cityCode = cityCode;
    }

    public ProvinceModel() {

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CityModel> getCityList() {
        return cityList;
    }

    public void setCityList(List<CityModel> cityList) {
        this.cityList = cityList;
    }

    @Override
    public String toString() {
        return "ProvinceModel{" +
                "name='" + name + '\'' +
                ", cityCode='" + cityCode + '\'' +
                ", isChecked=" + isChecked +
                '}';
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        this.isChecked = isChecked;
    }


    @Override
    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public void toggle() {

    }
}
