package com.taotao.manager.web.controller;

import com.taotao.common.pojo.CurrencyResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.pojo.TbItemParamValue;
import com.taotao.common.pojo.TreeResult;
import com.taotao.content.service.TbContentCategoryService;
import com.taotao.manager.pojo.LowerResult;
import com.taotao.manager.pojo.TbContent;
import com.taotao.manager.pojo.TbItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/content")
public class ContentController {
    @Autowired
    private TbContentCategoryService tbContentCategoryService;
    @RequestMapping("/showContentZtree")
    @ResponseBody
    public List<TreeResult> show (@RequestParam(value = "id",defaultValue = "0") long id){
        List<TreeResult> treeResults =tbContentCategoryService.treeSearch(id);
        return treeResults;
    }
    @RequestMapping("/showContentTable")
    @ResponseBody
    public  CurrencyResult  showContentTable(@RequestParam(value = "categoryId",defaultValue = "0") long categoryId,Integer page,Integer limit){
        CurrencyResult currencyResult =tbContentCategoryService.searchContent(categoryId,page,limit);
        return  currencyResult;
    }
    @RequestMapping("/deleteContentByCategoryId")
    @ResponseBody
    public LowerResult deleteContentByCategoryId(@RequestBody List<TbContent> tbContents){
        LowerResult lowerResult = tbContentCategoryService.deleteContentByCategoryId(tbContents);
        return  lowerResult;
    }
    @RequestMapping("/addContent")
    @ResponseBody
    public TaotaoResult addContent(String title, String titleDesc, Long categoryId, String subTitle, String url, String pic, String pic2) {
        TaotaoResult result = tbContentCategoryService.addContent( title,  titleDesc, categoryId,  subTitle,  url,  pic,  pic2);
        return result;
    }

}


