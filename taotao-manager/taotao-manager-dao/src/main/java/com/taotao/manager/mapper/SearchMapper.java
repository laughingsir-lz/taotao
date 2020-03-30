package com.taotao.manager.mapper;

import com.taotao.common.pojo.SearchResult;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SearchMapper {

    List<SearchResult> findResult();
}
