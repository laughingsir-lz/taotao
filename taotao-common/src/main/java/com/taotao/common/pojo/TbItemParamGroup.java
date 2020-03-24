package com.taotao.common.pojo;

import java.io.Serializable;
import java.util.List;

public class TbItemParamGroup implements Serializable{
    private  Integer id;
    private  String groupName;
    private  Long ItemCatid;
    //建立一对多关系
    List<TbItemParamKey> paramKeys;

    public List<TbItemParamKey> getParamKeys() {
        return paramKeys;
    }

    public void setParamKeys(List<TbItemParamKey> paramKeys) {
        this.paramKeys = paramKeys;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Long getItemCatid() {
        return ItemCatid;
    }

    public void setItemCatid(Long itemCatid) {
        ItemCatid = itemCatid;
    }

    @Override
    public String toString() {
        return "TbItemParamGroup{" +
                "id=" + id +
                ", groupName='" + groupName + '\'' +
                ", ItemCatid=" + ItemCatid +
                '}';
    }
}
