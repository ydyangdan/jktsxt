package com.example.jktx.controller;

import com.example.jktx.common.InMessage;
import com.example.jktx.common.OutMessage;
import com.example.jktx.common.XmlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@RestController
public class WeChatController {


    @PostMapping(value = "/wechat",produces = MediaType.APPLICATION_XML_VALUE)
    public String callback(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 解析微信服务器发送的POST请求
        ServletInputStream inputStream = request.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        StringBuilder requestBody = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            requestBody.append(line);
        }

        // 解析请求中的XML数据
        Map<String, String> requestMap = XmlUtils.parseXml(requestBody.toString());

        // 获取用户的OpenID
        assert requestMap != null;
        String openId = requestMap.get("FromUserName");
        String ToUserName = requestMap.get("ToUserName");
        String MsgType = requestMap.get("MsgType");
        String Content = requestMap.get("Content");
        String CreateTime = requestMap.get("CreateTime");
        System.out.println("返回消息"+requestBody);
        System.out.println(openId);
        System.out.println(ToUserName);
        System.out.println(MsgType);
        System.out.println(Content);

        // 处理其他业务逻辑...

        // 返回响应给微信服务器

        String replyContent = "成功了";
        StringBuilder xmlBuilder = new StringBuilder();
        xmlBuilder.append("<xml>");
        xmlBuilder.append("<ToUserName><![CDATA[").append(ToUserName).append("]]></ToUserName>");
        xmlBuilder.append("<FromUserName><![CDATA[").append(openId).append("]]></FromUserName>");
        xmlBuilder.append("<CreateTime>").append(CreateTime).append("</CreateTime>");
        xmlBuilder.append("<MsgType><![CDATA[text]]></MsgType>");
        xmlBuilder.append("<Content><![CDATA[").append(replyContent).append("]]></Content>");
        xmlBuilder.append("</xml>");

        return xmlBuilder.toString();
    }
        @PostMapping(value = "/wechat",produces = MediaType.APPLICATION_XML_VALUE)
        public String callback(@RequestBody InMessage inMessage) throws IOException {

            System.out.println(inMessage);

            return "success";
        }

//    @PostMapping(value = "wechat", produces = "application/xml;charset=UTF-8")
//    public Object handleMessage(@RequestBody InMessage inMessage){
//        // 创建响应消息实体对象
//        OutMessage outMessage = new OutMessage();
//        // 把原来的接收方设置为发送方
//        outMessage.setFromUserName(inMessage.getToUserName());
//        // 把原来的发送方设置为接收方
//        outMessage.setToUserName(inMessage.getFromUserName());
//        // 设置消息类型
//        outMessage.setMsgType(inMessage.getMsgType());
//        // 设置消息时间
//        outMessage.setCreateTime(System.currentTimeMillis());
//        // 根据接收到消息类型，响应对应的消息内容
//        if ("text".equals(inMessage.getMsgType())){
//            // 文本
//            outMessage.setContent(inMessage.getContent());
//        }else if ("image".equals(inMessage.getMsgType())){
//            // 图片
//            outMessage.setMediaId(new String[]{inMessage.getMediaId()});
//        }
//        return outMessage;
//    }




}
