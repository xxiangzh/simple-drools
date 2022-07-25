package com.xzh.drools.entity;

/**
 * @author 向振华
 * @date 2020/06/30 16:11
 */
public class DemoEntity {

    private String name;
    private String remark;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public DemoEntity() {

    }

    public DemoEntity(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "DemoEntity{" +
                "name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
