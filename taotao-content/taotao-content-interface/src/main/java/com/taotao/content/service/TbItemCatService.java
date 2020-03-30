package com.taotao.content.service;

import com.taotao.common.pojo.AdResult;
import com.taotao.common.pojo.ItemCatResult;

import java.util.List;

public interface TbItemCatService {
   ItemCatResult getItemCats(Long id);
   List<AdResult> getAd();
}
