package com.taotao.content.service.impl;

import com.taotao.common.pojo.Constants;
import com.taotao.common.pojo.CurrencyResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.pojo.TreeResult;
import com.taotao.content.jedis.JedisClient;
import com.taotao.content.service.TbContentCategoryService;
import com.taotao.manager.mapper.TbContentCategoryMapper;
import com.taotao.manager.pojo.LowerResult;
import com.taotao.manager.pojo.TbContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.taotao.common.pojo.Constants.KEY;

@Service
public class TbContentCategoryServiceImpl implements TbContentCategoryService {
    @Autowired
    private TbContentCategoryMapper tbContentCategoryMapper;
    @Autowired
    private JedisClient jedisClient;
    @Override
    public List<TreeResult> treeSearch(long id) {
        List<TreeResult> treeResults =tbContentCategoryMapper.treeSearch( id);
        return treeResults;
    }

    @Override
    public CurrencyResult searchContent(long categoryId, Integer page, Integer limit) {
        CurrencyResult currencyResult = new CurrencyResult();
        List<TbContent> data = tbContentCategoryMapper.searchContent(categoryId,(page-1)*limit,limit);
        currencyResult.setCode(0);
        currencyResult.setMsg("");
        int count =tbContentCategoryMapper.findCount(categoryId);
        currencyResult.setCount(count);
        currencyResult.setData(data);
        return currencyResult;
    }

    @Override
    public LowerResult deleteContentByCategoryId(List<TbContent> tbContents) {
        LowerResult lowerResult =new LowerResult();
        int count=tbContentCategoryMapper.deleteContentByCategoryId(tbContents);
        if(count>0){
            jedisClient.del(Constants.KEY);
            lowerResult.setStatus(200);
            lowerResult.setMsg("内容删除成功");
            lowerResult.setData(null);
        }else {
            lowerResult.setStatus(500);
            lowerResult.setMsg("内容删除失败");
            lowerResult.setData(null);
        }
        return lowerResult;
    }

    @Override
    public TaotaoResult addContent(String title, String titleDesc, Long categoryId, String subTitle, String url, String pic, String pic2) {
        Date date = new Date();
        TaotaoResult taotaoResult = new TaotaoResult();
        int count =tbContentCategoryMapper. addContent( title, titleDesc, categoryId,  subTitle, url,  pic,  pic2,date);
        if(count>0){
            jedisClient.del(Constants.KEY);
            return  taotaoResult.build(200,"内容添加成功");
        }else
            return taotaoResult.build(500,"内容添加失败");
    }

}
