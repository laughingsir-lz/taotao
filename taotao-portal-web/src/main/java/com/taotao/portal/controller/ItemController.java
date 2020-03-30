package com.taotao.portal.controller;

import com.taotao.common.pojo.ItemCatResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.content.service.TbItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("item/cat/itemcat")
public class ItemController {
    @Autowired
    private TbItemCatService tbItemCatService;
   @RequestMapping("/all")
   @ResponseBody //把数据发送到页面
   public String show(@RequestParam(value = "id",defaultValue = "0")long id){
       ItemCatResult itemCatResult =tbItemCatService.getItemCats(id);
       String json = JsonUtils.objectToJson(itemCatResult);
       return json;

    }

}
