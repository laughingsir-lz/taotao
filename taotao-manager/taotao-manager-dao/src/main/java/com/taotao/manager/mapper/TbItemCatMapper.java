package com.taotao.manager.mapper;


import com.taotao.manager.pojo.TbContent;
import com.taotao.manager.pojo.TbItemCat;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TbItemCatMapper {
    @Select("select * from tbitemcat where parentId = #{id}")
    List<TbItemCat> findTbItemCatByParentId(@Param("id") Long id);
    @Select("select * from tbcontent where categoryId = 89")
    List<TbContent> findTbContent();
}