package com.taotao.manager.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.pojo.TbItemParamValue;
import com.taotao.common.pojo.CurrencyResult;
import com.taotao.manager.pojo.LowerResult;
import com.taotao.manager.pojo.TbItem;
import com.taotao.common.pojo.TreeResult;

import java.util.List;


public interface TbitemService {
    TbItem findTbitemById(long tbitemid);
    /**
     * 分页查询商品信息
     * @param page 当前页面 数
     * @param limit 当前页数据条数
     * @return json实体类
     */
    CurrencyResult findByPage(int page, int limit);

    /**
     * 商品下架
     * @param tbItems 传入的商品对象
     * @return
     */
    LowerResult takeOffBatch(List<TbItem> tbItems);

    /**
     * 上架
     * @param tbItems
     * @return
     */
    LowerResult takeUpBatch(List<TbItem> tbItems);

    /**
     * 删除
     * @param tbItems
     * @return
     */
    LowerResult deleteBatch(List<TbItem> tbItems);
    /**
     * 动态查询商品
     * @param title 商品title 用模糊查询
     * @param priceMin 价格范围 最低价
     * @param priceMax 价格范围 最高价
     * @param cId 商品 cid
     * @return
     */
    CurrencyResult searchItem(Integer page , Integer limit, String title,Integer priceMin,Integer priceMax,Long cId);

    /**
     * 树结构生成 通过父id 查询子目录内容
     * @param id 父id
     * @return 返回 TreeResult
     */
    List<TreeResult> treesearch (long id);

    /**
     * 商品添加
     * @param item 商品基本信息
     * @param desc 商品描述信息
     * @return
     */
    TaotaoResult addItem(TbItem item, String desc,List<TbItemParamValue> tbItemParamValues);

    /**
     * 通过cId 显示规格参数组信息和每一个对应的规格参数项
     * @param cId 分类id
     * @return 规格参数组
     */
    TaotaoResult showItemGroupById(Long cId);

    /**
     * 通过分类id和参数实现添加规格参数组，创建模板
     * @param cId
     * @param params
     * @return
     */
    TaotaoResult addItemParamTemplate(Long cId,String params);


    void  dataInit();


}
