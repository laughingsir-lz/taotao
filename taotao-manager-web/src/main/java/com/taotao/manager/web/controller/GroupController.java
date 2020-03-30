package com.taotao.manager.web.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.manager.service.TbitemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/itemGroup")
public class GroupController {
    @Autowired
    private TbitemService tbitemService ;
    @RequestMapping("/showItemGroup")
    @ResponseBody
    public TaotaoResult showItemGroupById(Long cId){
        TaotaoResult taotaoResult = tbitemService.showItemGroupById(cId);
        return taotaoResult;
    }
    @RequestMapping("/addGroup")
    @ResponseBody
    public  TaotaoResult  addGroup(Long cId,String params){
         TaotaoResult taotaoResult=tbitemService.addItemParamTemplate(cId,params);
      return  taotaoResult;
    }

}
