package com.taotao.search.service.impl;

import com.taotao.common.pojo.SearchItem;
import com.taotao.manager.mapper.SearchItemMapper;
import com.taotao.search.service.SearchItemService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class MyMessageListener implements MessageListener {
    @Autowired
    private SearchItemService searchItemService;
    @Autowired
    private SearchItemMapper searchItemMapper;
    @Override
    public void onMessage(Message message) {

        try {
            //TextMessage textMessage = (TextMessage) message;
            //取消息内容
           // String text = textMessage.getText();
           // System.out.println(text);
            TextMessage textMessage = null;
             Long itemId = null;
            if (message instanceof TextMessage) {
                textMessage = (TextMessage) message;
                itemId = Long.parseLong(textMessage.getText());
                SearchItem item = searchItemMapper.getItemById(itemId);
                System.out.println("等到对象");
                searchItemService.addDocument(item);
                System.out.println("添加对象成功");
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}

