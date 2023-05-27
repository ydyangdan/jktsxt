package com.example.jktx.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.log.Log;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.aliyuncs.utils.XmlUtils;
import com.example.jktx.common.RestClientUtils;
import com.example.jktx.common.Utils;

import oracle.jrockit.jfr.VMJFR;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TestweixinController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value="/wechat")
    public void testWechat(HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();

        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");

        System.out.println("微信加密签名:"+signature);
        System.out.println("时间戳:"+timestamp);
        System.out.println("随机数:"+nonce);
        System.out.println("随机字符串:"+echostr);


        if(Utils.check(timestamp,nonce,signature)){
            //校验正确并返回echostr，才能正式成为一名公众号开发者，即填写的URL和TOKEN才能生效
            out.print(echostr);
        }else{
            //校验失败
            out.print("---请到公众号执行相应操作---");
        }
        out.flush();
        out.close();
    }

    /**
     * 恒定配置接口，post类型用于接收各种消息推送和回调
     *
     * @param request 请求体
     * @return 请求合法的情况下恒定返回字符串 “success”
     */
    @PostMapping("/auth")
    public String sayHi(HttpServletRequest request) {
        //获取param参数(可用以验签，确定请求方身份是否合法)
        String reqStr = "";
        Logger log = LoggerFactory.getLogger(TestweixinController.class);
        try {
            Map<String, String[]> parameterMap = request.getParameterMap();
            if (ObjectUtil.isNotEmpty(parameterMap)) {
                reqStr = JSONObject.toJSONString(parameterMap, SerializerFeature.WriteMapNullValue);
            }
        } catch (Exception e) {
            log.error("接收param消息异常" + e.getMessage(), e);
        }
        log.info("恒定配置接口接收param参数：" + reqStr);

        //获取xml参数（可用以具体业务处理）
        Map<String, String> xmlMap = new HashMap<>(16);
        try {
            // 从request中取得输入流
            InputStream inputStream = request.getInputStream();
            // 读取输入流
            SAXReader reader = new SAXReader();
            Document document = reader.read(inputStream);
            // 得到xml根元素
            Element root = document.getRootElement();
            // 得到根元素的所有子节点
            List<Element> elementList = root.elements();
            // 遍历所有子节点
            for (Element e : elementList) {
                xmlMap.put(e.getName(), e.getText());
            }
        } catch (Exception e) {
            log.error("接收xml消息异常" + e.getMessage(), e);
        }
        log.info("恒定配置接口接收xml参数：" + JSONObject.toJSONString(xmlMap, SerializerFeature.WriteMapNullValue));

        return "success";
    }

    /**
     * 发送模板消息
     *
     * @param reqMap 包含所需参数的集合
     * @return 响应结果
     */
    @PostMapping("/tem-msg")
    public String sendTemplateMsg(@RequestBody Map<String, String> reqMap) {
        String uri = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
        String accessToken = reqMap.get("accessToken");
        uri = uri.replace("ACCESS_TOKEN", accessToken);

        String temId = reqMap.get("temId");
        String redirectUri = reqMap.get("redirectUri");
        String openId = reqMap.get("openId");

        Map<String, Object> wxMap = new HashMap<>(16);
        wxMap.put("touser", openId);
        wxMap.put("template_id", temId);
        wxMap.put("url", redirectUri);
        wxMap.put("topcolor", "#FF0000");

        Map<String, Map<String, String>> dataMap = new HashMap<>(16);
        Map<String, String> map1 = new HashMap<>(2);
        map1.put("value", "孙武");
        map1.put("color", "#173177");
        dataMap.put("one", map1);

        Map<String, String> map2 = new HashMap<>(2);
        map2.put("value", "核酸检测报告");
        map2.put("color", "#173177");
        dataMap.put("two", map2);

        Map<String, String> map3 = new HashMap<>(2);
        map3.put("value", "http://www.baidu.com");
        map3.put("color", "#173177");
        dataMap.put("three", map3);

        wxMap.put("data", dataMap);

        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(wxMap));

        String res = RestClientUtils.sendPostString(restTemplate, uri, null, jsonObject, null);

        return res;
    }


}
