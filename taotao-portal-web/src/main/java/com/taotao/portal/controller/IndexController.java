package com.taotao.portal.controller;

import com.taotao.common.pojo.AdResult;
import com.taotao.common.pojo.Constants;
import com.taotao.common.utils.JsonUtils;
import com.taotao.content.service.TbItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static com.taotao.common.pojo.Constants.KEY;

@Controller
public class IndexController {
    @Autowired
    private TbItemCatService tbItemCatService;
    @RequestMapping("/index")
    public String showIndex(Model model){
        List<AdResult> adResults = tbItemCatService.getAd();
        String json = JsonUtils.objectToJson(adResults);
        model.addAttribute(Constants.KEY,json);
        return "index";
    }
}
