package com.taotao.common.pojo;

import java.io.Serializable;

public class TbItemParamKey implements Serializable{
    private  Integer id;
    private  String  paramName;
    private  Integer groupId;
    //建立多对一关系
    private  TbItemParamGroup paramGroup;

    public TbItemParamGroup getParamGroup() {
        return paramGroup;
    }

    public void setParamGroup(TbItemParamGroup paramGroup) {
        this.paramGroup = paramGroup;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }
}
