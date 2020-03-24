package com.taotao.manager.web.controller;

import com.taotao.manager.pojo.TreeResult;
import com.taotao.manager.service.TbitemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/itemCat")
public class TreeController {
    @Autowired
    private TbitemService tbitemService;
    @RequestMapping("/showZtree")
    @ResponseBody
    public List<TreeResult> show (@RequestParam(value = "id",defaultValue = "0") long id){
        List<TreeResult> treeResults =tbitemService.treesearch(id);
        return treeResults;
    }
}
