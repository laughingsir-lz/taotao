package com.taotao.content.service.impl;
import com.taotao.common.pojo.AdResult;
import com.taotao.common.pojo.Constants;
import com.taotao.common.pojo.ItemCat;
import com.taotao.common.pojo.ItemCatResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.content.jedis.JedisClient;
import com.taotao.content.service.TbItemCatService;
import com.taotao.manager.mapper.TbItemCatMapper;
import com.taotao.manager.pojo.TbContent;
import com.taotao.manager.pojo.TbItemCat;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

import static com.taotao.common.pojo.Constants.KEY;

@Service
public class TbItemCatServiceImpl implements TbItemCatService {
    @Autowired
    private TbItemCatMapper tbItemCatMapper;
    @Autowired
    private JedisClient jedisClient;
    @Override
    public ItemCatResult getItemCats(Long id) {
        ItemCatResult result = new ItemCatResult();
        result.setData(getItemCatsResult(id));
        return result;
    }

    private List<?> getItemCatsResult(Long id) {
        List data =new ArrayList();
        //当前默认id = 0;这里就有一级目录对象的信息
       List<TbItemCat> tbItemCats = tbItemCatMapper.findTbItemCatByParentId(id);
        for (TbItemCat tbItemCat:tbItemCats) {
            //实例对象 item
            ItemCat item = new ItemCat();
            //根据isParent 判断 是否有子目录
            // 有则是一级和二级目录
            // 没有则说明已经到了数据的最里层 就是三级目录
            if (tbItemCat.getIsParent()){
                //一级和二级中u设置
                item.setUrl("/products/"+tbItemCat.getId()+".html");
                if (id==0){
                    //设置一级
                    item.setName("<a href = '/products/" +tbItemCat.getId()+".html'>"+tbItemCat.getName()+"</a>");
                }else {
                    item.setName(tbItemCat.getName());
                }
                // 设置一级和二级的i
                //运用迭代 调用自身 得到i 的 list
                //类似于生成list<,,list<,,list<,,,>>>
                item.setItem(getItemCatsResult(tbItemCat.getId()));
                data.add(item);
            }else {
                //三级目录
                data.add("/products/"+tbItemCat.getId()+"|"+tbItemCat.getName() );
            }
        }
        return data;
    }
    @Override
    public List<AdResult> getAd() {
        String json = jedisClient.get(Constants.KEY);
        if (StringUtils.isNotBlank(json)) {
            List<AdResult> results = JsonUtils.jsonToList(json,AdResult.class);
            System.out.println("从缓存中获取数据");
            return results;
        }
        List<AdResult> adResultList = new ArrayList<>();
        List<TbContent> tbContents =tbItemCatMapper.findTbContent();
        for (TbContent tbContent :tbContents) {
            AdResult adResult  =new AdResult();
            adResult.setHeight(240);
            adResult.setHeightB(240);
            adResult.setWidth(670);
            adResult.setWidthB(540);
            adResult.setAlt("");
            adResult.setSrc(tbContent.getPic());
            adResult.setSrcB(tbContent.getPic2());
            adResult.setHref(tbContent.getUrl());
            adResultList.add(adResult);
        }
        System.out.println("从数据库中获取数据");
        jedisClient.set(Constants.KEY,JsonUtils.objectToJson(adResultList));
        return adResultList;
        //return adRedis;
    }
}
