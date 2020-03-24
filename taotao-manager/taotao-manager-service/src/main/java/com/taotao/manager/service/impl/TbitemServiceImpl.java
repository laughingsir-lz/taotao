package com.taotao.manager.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.pojo.TbItemParamGroup;
import com.taotao.common.utils.IDUtils;
import com.taotao.manager.mapper.TbItemMapper;
import com.taotao.manager.pojo.*;
import com.taotao.manager.service.TbitemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TbitemServiceImpl implements TbitemService {
    @Autowired
    private TbItemMapper tbItemMapper;
    @Override
    public TbItem findTbitemById(long tbitemid) {
        TbItem item = tbItemMapper.findTbitemById( tbitemid);
        return item;
    }
    @Override
    public CurrencyResult findByPage(int page, int limit) {
        CurrencyResult currencyResult =new CurrencyResult();
        List<TbItem> items = tbItemMapper.findByPage((page-1)*limit,limit);
        currencyResult.setCode(0);
        currencyResult.setMsg("");
        int count =tbItemMapper.findCount();
        currencyResult.setCount(count);
        currencyResult.setData(items);
        return currencyResult;
    }
    @Override
    public LowerResult takeOffBatch(List<TbItem> tbItems) {
        LowerResult lowerResult =new LowerResult();
        int count=tbItemMapper.takeOffBatch(tbItems);
        if(count>0){
            lowerResult.setStatus(200);
            lowerResult.setMsg("商品下架成功");
            lowerResult.setData(null);
        }else {
            lowerResult.setStatus(500);
            lowerResult.setMsg("商品下架失败");
            lowerResult.setData(null);
        }
        return lowerResult;
    }

    @Override
    public LowerResult takeUpBatch(List<TbItem> tbItems) {
        LowerResult lowerResult =new LowerResult();
        int count=tbItemMapper.takeUpBatch(tbItems);
        if(count>0){
            lowerResult.setStatus(200);
            lowerResult.setMsg("商品上架成功");
            lowerResult.setData(null);
        }else {
            lowerResult.setStatus(500);
            lowerResult.setMsg("商品上架失败");
            lowerResult.setData(null);
        }
        return lowerResult;
    }

    @Override
    public LowerResult deleteBatch(List<TbItem> tbItems) {
        LowerResult lowerResult =new LowerResult();
        int count=tbItemMapper.deleteBatch(tbItems);
        if(count>0){
            lowerResult.setStatus(200);
            lowerResult.setMsg("商品删除成功");
            lowerResult.setData(null);
        }else {
            lowerResult.setStatus(500);
            lowerResult.setMsg("商品删除失败");
            lowerResult.setData(null);
        }
        return lowerResult;
    }

    @Override
    public CurrencyResult searchItem(Integer page, Integer limit, String title, Integer priceMin, Integer priceMax, Long cId) {
        CurrencyResult currencyResult = new CurrencyResult();
        List<TbItem> data = tbItemMapper.searchItem((page-1)*limit,limit,title,  priceMin,  priceMax,  cId);
        currencyResult.setCode(0);
        currencyResult.setMsg("");
        int count =tbItemMapper.findCountByC(title,  priceMin,  priceMax,  cId);
        currencyResult.setCount(count);
        currencyResult.setData(data);
        return currencyResult;
    }

    @Override
    public List<TreeResult> treesearch(long id) {
        List<TreeResult> treeResults =tbItemMapper.treesearch( id);
        return treeResults;
    }

    @Override
    public TaotaoResult addItem(TbItem item, String desc) {
        // 1、生成商品id
        long itemId = IDUtils.genItemId();
        // 2、补全TbItem对象的属性
        item.setId(itemId);
        //商品状态，1-正常，2-下架，3-删除
        item.setStatus((byte) 1);
        Date date = new Date();
        item.setCreated(date);
        item.setUpdated(date);
        // 3、向商品表插入数据
        tbItemMapper.insertTbItem(item);
        // 4、创建一个TbItemDesc对象
        TbItemDesc itemDesc = new TbItemDesc();
        // 5、补全TbItemDesc的属性
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(date);
        itemDesc.setUpdated(date);
        // 6、向商品描述表插入数据
        tbItemMapper.insertTbItemDesc(itemDesc);
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult showItemGroupById(Long cId) {
        TaotaoResult taotaoResult = new TaotaoResult();
        List<TbItemParamGroup> group =tbItemMapper.showItemGroupById(cId);
        if(group.size()<=0){
            taotaoResult.setStatus(500);
            taotaoResult.setMsg("该分类没有规格参数模板，请创建规格参数模板");
        }
        taotaoResult.setStatus(200);
        taotaoResult.setMsg("有规格参数模板");
        taotaoResult.setData(group);
        return taotaoResult;
    }
}

