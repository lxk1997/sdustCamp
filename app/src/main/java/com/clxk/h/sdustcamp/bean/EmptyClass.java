package com.clxk.h.sdustcamp.bean;

import com.othershe.groupindexlib.bean.BaseItem;

public class EmptyClass extends BaseItem {

    private String buildingno;
    private String name;
    private String capacity;

    public String getName() {
        return name;
    }

    public EmptyClass(String name, String capacity, String buildingno) {
        this.name = name;
        this.capacity = capacity;
        this.buildingno = buildingno;
    }

    public String getCapacity() {
        return capacity;
    }

    public String getBuildingno() {
        return buildingno;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBuildingno(String buildingno) {
        this.buildingno = buildingno;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

}
