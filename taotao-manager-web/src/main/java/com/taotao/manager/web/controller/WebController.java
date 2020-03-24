package com.taotao.manager.web.controller;

import com.taotao.common.pojo.PictureResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.manager.pojo.CurrencyResult;
import com.taotao.manager.pojo.LowerResult;
import com.taotao.manager.pojo.TbItem;
import com.taotao.manager.service.PictureService;
import com.taotao.manager.service.TbitemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/item")
public class WebController {
    //设置响应
    //从java 到 页面  确定返回数据类型为json 成不成功取决于jackson jar包是否存在
    // http://localhost:8081/item/showItemPage?page=1&limit=10
    //http://localhost:8081/item/showItemPage/1/10
    //@RequestMapping("/showItemPage")
    //@ResponseBody// 它是吧CurrencyResult作为响应 响应到页面上面 但是呢 又配合了 jackson 直接把对象变成了 json数据了
    @Autowired
    private TbitemService tbitemService;
    @Autowired
    private PictureService pictureService;
    @RequestMapping("/showItemPage")
    @ResponseBody
    public CurrencyResult show(int page,int limit){
        CurrencyResult currencyResult =tbitemService.findByPage(page,limit);
        return  currencyResult;
    }
    @RequestMapping("/commodityLowerShelves")
    @ResponseBody
    public LowerResult show(@RequestBody List<TbItem> tbItems){
        System.out.println(tbItems);
        LowerResult lowerResult =tbitemService.takeOffBatch(tbItems);
        System.out.println(lowerResult);
        return  lowerResult;
    }
    @RequestMapping("/commodityUpperShelves")
    @ResponseBody
    public LowerResult show2(@RequestBody List<TbItem> tbItems){
        LowerResult lowerResult =tbitemService.takeUpBatch(tbItems);
        return  lowerResult;
    }
    @RequestMapping("/itemDelete")
    @ResponseBody
    public LowerResult show3(@RequestBody List<TbItem> tbItems){
        LowerResult lowerResult =tbitemService.deleteBatch(tbItems);
        return  lowerResult;
    }
    @RequestMapping("/searchItem")
    @ResponseBody
    public CurrencyResult show4(Integer  page,Integer limit,String title, Integer priceMin, Integer priceMax, Long cId){
        CurrencyResult shopResult =tbitemService.searchItem(page,limit, title,  priceMin,  priceMax, cId);
        return shopResult ;
    }
    @RequestMapping("/fileUpload")
    @ResponseBody()
    public PictureResult uploda(MultipartFile file){
        try {
            byte[] bytes = file.getBytes();
            String name = file.getOriginalFilename();
            PictureResult pictureResult = pictureService.uploadFile(bytes,name);
            return pictureResult;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    @RequestMapping("/addItem")
    @ResponseBody
    public TaotaoResult saveItem(TbItem item, String itemDesc) {
        TaotaoResult result = tbitemService.addItem(item, itemDesc);
        return result;
    }

}

