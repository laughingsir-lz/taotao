package com.taotao.manager.service.impl;

import com.taotao.common.pojo.Data;
import com.taotao.common.pojo.PictureResult;
import com.taotao.common.utils.FtpUtil;
import com.taotao.common.utils.IDUtils;
import com.taotao.manager.service.PictureService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.ByteArrayInputStream;
@Service
public class PictureServiceImpl implements PictureService {
    @Value("${FTP_ADDRESS}")
    private String FTP_ADDRESS;
    @Value("${FTP_PORT}")
    private Integer FTP_PORT;
    @Value("${FTP_USERNAME}")
    private String FTP_USERNAME;
    @Value("${FTP_PASSWORD}")
    private String FTP_PASSWORD;
    @Value("${FILI_UPLOAD_PATH}")
    private String FILI_UPLOAD_PATH;
    @Value("${IMAGE_BASE_URL}")
    private String IMAGE_BASE_URL;
    @Override
    public PictureResult uploadFile(byte[] bytes, String name) {
        //获取图片的名称
        PictureResult pictureResult = new PictureResult();
        try {
            //生成图片名称  newName
            String newName = IDUtils.genImageName() + name.substring(name.lastIndexOf("."));
            //生成图片路径
            String filepath = new DateTime().toString("yyyy/MM/dd");
            //这是图片和文件在dubbo中传输是唯一的方式 必须使用 ByteArrayInputStream  字节数组输入流 来获取图片和文件的流对象
            //获得图片输入流 input  字节数组输入流 bis
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            //上传图片 调用 工具类FtpUtil 的upLoadFile方法
            FtpUtil.uploadFile(FTP_ADDRESS, FTP_PORT, FTP_USERNAME, FTP_PASSWORD, FILI_UPLOAD_PATH, filepath, newName, bis);
            pictureResult.setCode(0);
            pictureResult.setMsg("");
            Data data = new Data();
            //http://192.168.25.133/images/2018/06/26/
            data.setSrc(IMAGE_BASE_URL+"/"+filepath+"/"+newName);
            pictureResult.setData(data);
            return pictureResult;
        }   catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}


