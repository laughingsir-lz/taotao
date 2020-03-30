package com.taotao.content.service;

import com.taotao.common.pojo.CurrencyResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.pojo.TreeResult;
import com.taotao.manager.pojo.LowerResult;
import com.taotao.manager.pojo.TbContent;

import java.util.List;

public interface TbContentCategoryService {
    /**
     * 内容管理器加载树结构
     * @param id
     * @return
     */
    List<TreeResult> treeSearch(long id);

    /**
     * 通过categoryId查找到内容管理器数据
     * @param categoryId
     * @param page
     * @param limit
     * @return
     */
    CurrencyResult searchContent(long categoryId,Integer page , Integer limit);

    /**
     * 批量删除
     * @param categoryId
     * @return
     */
    LowerResult deleteContentByCategoryId(List<TbContent> categoryId);
    /**
     *   添加内容
     */
    TaotaoResult addContent(String title,String titleDesc,Long categoryId,String subTitle,String url,String pic,String pic2);
}
