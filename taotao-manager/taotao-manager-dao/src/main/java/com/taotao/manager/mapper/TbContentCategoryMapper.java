package com.taotao.manager.mapper;


import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.pojo.TreeResult;
import com.taotao.manager.pojo.TbContent;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

public interface TbContentCategoryMapper {
    /**
     * 树结构生成
     * @param id
     * @return
     */
    List<TreeResult> treeSearch(@Param("id") long id);

    /**
     * 通过categoryId查找内容信息并分页显示
     * @param categoryId
     * @param page
     * @param limit
     * @return
     */
    @Select("SELECT * from tbcontent WHERE categoryId =#{categoryId} limit #{page},#{limit}")
    List<TbContent> searchContent(@Param("categoryId") long categoryId, @Param("page") Integer page, @Param("limit") Integer limit);

    /**
     * 得到categoryId所指向对象的所有数据条数
     * @param categoryId
     * @return
     */
    @Select("SELECT count(*) from tbcontent WHERE categoryId =#{categoryId}")
    int findCount(long categoryId);

    /**
     * 批量删除
     * @param tbContents
     * @return
     */
    int deleteContentByCategoryId(@Param("tbContents") List<TbContent> tbContents);

    int addContent(@Param("title") String title, @Param("titleDesc") String titleDesc, @Param("categoryId") Long categoryId,
                            @Param("subTitle") String subTitle, @Param("url")String url,
                            @Param("pic")String pic, @Param("pic2")String pic2, @Param("date") Date date);
}