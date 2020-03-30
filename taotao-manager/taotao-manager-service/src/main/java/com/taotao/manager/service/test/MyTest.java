package com.taotao.manager.service.test;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.pojo.TbItemParamGroup;
import com.taotao.common.pojo.TbItemParamKey;
import com.taotao.manager.mapper.TbItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class MyTest {
    @Autowired
    private TbItemMapper tbItemMapper;
    public TaotaoResult addItemParamTemplate(Long cId, String params) {
        //实例化一个参数组集合
       // List<TbItemParamGroup> groups = new ArrayList<TbItemParamGroup>();
        //第一次切分 将 （组、项 clive 组、项） 切成（（组、项），（组、项））
        String[] split = params.split("clive");
        //遍历字符串数组
        for (int i=0;i<split.length;i++){
            //以i=0为例  这个时候 有split[0]="组，项1，项2，..."
            TbItemParamGroup group =new TbItemParamGroup();
            String string = split[i];
            //对一个split[i]再作切割 将"组，项1，项2，..."切成数组[组，项1，项2，....]
            //所以这个数组中第一个元素就是组名，其余的是项对象的元素
            String[] split2 =string.split(",");
           // List<TbItemParamKey> tbItemParamKeys = new ArrayList<TbItemParamKey>();
            for(int j=0;j<=split2.length;j++){
                int id = 0 ;
                if (j==0){
                    //获取组名
                    String name =split2[j];
                    group.setGroupName(split2[j]);
                    //设置cId
                    group.setItemCatId(cId);
                    //把这个group立刻存入参数group组表（groupName,itemCatId）
                    //执行sql 插入
                    tbItemMapper.addParamGroup(group);
                    //通过刚存入的两个元素唯一锁定组id
                    //执行sql 查询
                    TbItemParamGroup group1 = tbItemMapper.findIdByCIdAndName(cId,name);
                    //将查出来的id，赋值给变量id
                    id=group1.getId();
                }else {
                    TbItemParamKey tbItemParamKey = new TbItemParamKey();
                    //数组中的其他元素都是项  直接set
                    tbItemParamKey.setParamName(split2[i]);
                    //锁定 id 作为key的groupid
                    tbItemParamKey.setGroupId(id);
                    //直接存入key表
                    tbItemMapper.addParamKey(tbItemParamKey);
                }
            }

        }
        return TaotaoResult.build(200,"添加规格参数成功");
    }
}
