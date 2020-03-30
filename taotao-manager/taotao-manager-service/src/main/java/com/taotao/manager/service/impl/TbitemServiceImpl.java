package com.taotao.manager.service.impl;

import com.taotao.common.pojo.*;
import com.taotao.common.utils.IDUtils;
import com.taotao.manager.mapper.SearchMapper;
import com.taotao.manager.mapper.TbItemMapper;
import com.taotao.manager.pojo.*;
import com.taotao.manager.service.TbitemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TbitemServiceImpl implements TbitemService {
    @Autowired
    private TbItemMapper tbItemMapper;
    @Autowired
    private SearchMapper searchMapper;
    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private Destination destination;
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
        List<TreeResult> treeResults =tbItemMapper.treesearch(id);
        return treeResults;
    }

    @Override
    public TaotaoResult addItem(TbItem item, String desc,List<TbItemParamValue> tbItemParamValues) {
        // 1、生成商品id
        final long itemId = IDUtils.genItemId();
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

        //7、绑定规格参数值表中itemid
        for (TbItemParamValue tbItemParamValue:tbItemParamValues
             ) {
            tbItemParamValue.setItemId(itemId);
        }
        //7、向规格参数值表中写入值数据
        tbItemMapper.insertTbItemParamValue(tbItemParamValues);
        //8、添加完信息后，要发布个activeMQ消息队列
        jmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                //消息的内容就是商品id
                TextMessage textMessage = session.createTextMessage(itemId + "");
                System.out.println("发送消息成功");
                return textMessage;
            }
        });
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult showItemGroupById(Long cId) {
        TaotaoResult taotaoResult = new TaotaoResult();
        List<TbItemParamGroup> group =tbItemMapper.showItemGroupById(cId);
        if(group.size()<=0){
            taotaoResult.setStatus(500);
            taotaoResult.setMsg("该分类没有规格参数模板，请创建规格参数模板");
        }else {
            taotaoResult.setStatus(200);
            taotaoResult.setMsg("有规格参数模板333333");
            taotaoResult.setData(group);
        }

        return taotaoResult;
    }
    @Override
    public TaotaoResult addItemParamTemplate(Long cId, String params) {
        List<TbItemParamGroup> groups = new ArrayList<TbItemParamGroup>();
        String[] split = params.split("clive");
        for (int i=0;i<split.length;i++){
            TbItemParamGroup group =new TbItemParamGroup();
            String string = split[i];
            String[] split2 =string.split(",");
            List<TbItemParamKey> tbItemParamKeys = new ArrayList<TbItemParamKey>();
            for(int j=0;j<=split2.length;j++){
                if (j==0){
                    group.setGroupName(split2[j]);
                }else {
                    TbItemParamKey tbItemParamKey = new TbItemParamKey();
                    tbItemParamKey.setParamName(split2[i]);
                    tbItemParamKeys.add(tbItemParamKey);
                }
            }
            group.setParamKeys(tbItemParamKeys);
            groups.add(group);
        }
        for (TbItemParamGroup group: groups) {
            group.setItemCatId(cId);
        }
        int i =tbItemMapper.addParamGroup(groups);
        if (i<=0){
            return TaotaoResult.build(500,"添加规格参数失败");
        }else {
            List<TbItemParamGroup> itemParamGroups =tbItemMapper.findTbItemGroupBycId(cId);
            for (int j = 0; j<groups.size();j++){
                TbItemParamGroup dataBaseGroup = itemParamGroups.get(j);
                TbItemParamGroup javaGroup =groups.get(j);
                if (dataBaseGroup.getGroupName().equals(javaGroup.getGroupName())){
                    List<TbItemParamKey> paramKeys =javaGroup.getParamKeys();
                    for (TbItemParamKey tbItemParamKey:paramKeys){
                        tbItemParamKey.setGroupId(dataBaseGroup.getId());
                    }
                }
            }
        }
        for (TbItemParamGroup group:groups) {
            List<TbItemParamKey> paramKeys = group.getParamKeys();
            int j =tbItemMapper.addParamGroupKeys(paramKeys);
            if (j<=0){
                return TaotaoResult.build(500,"添加规格参数失败");
            }
        }
        return TaotaoResult.build(200,"添加规格参数成功");
    }

    @Override
    public void dataInit() {
        List<SearchResult> searchResults =searchMapper.findResult();
        System.out.println(searchResults);
    }
}

