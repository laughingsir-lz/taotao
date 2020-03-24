package com.taotao.common.pojo;

import java.io.Serializable;

public class Data  implements Serializable{
    private  String src;

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    @Override
    public String toString() {
        return "Data{" +
                "src='" + src + '\'' +
                '}';
    }
}
