package com.example.jktx.common;


import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.UUID;

public class WXUtils {

    /**
     * 公众号appid
     */
    private static String APP_ID = "wx37de6e2fea9c6f89";

    /**
     * 公众号 Secre
     */
    private static String AppSecret = "219b75ffd6192c8f0c9e06c515c810dc";
    /**
     * 模板消息id
     */
    private static String TemplateId = "FRcPYPZ102F_XioeXfMQ91cwwuJwM9PNoprlERdGyA4";
    /**
     * 点击模版消息要访问的网址
     */
    private static String TemplateUrl = "https://www.baidu.com/";

    /**
     * 推送模板消息
     * @param openId
     */
    public static void pushMsg(String openId, String name,String exam, String time,String place){
        //1，配置
        WxMpInMemoryConfigStorage wxStorage = new WxMpInMemoryConfigStorage();
        wxStorage.setAppId(APP_ID);
        wxStorage.setSecret(AppSecret);
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxStorage);

        //2,推送消息
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .toUser(openId)//要推送的用户openid
                .templateId(TemplateId)//模版id
                .url(TemplateUrl)//点击模版消息要访问的网址
                .build();
        //3,模板内容
        templateMessage.addData(new WxMpTemplateData("name", name, "#000000"))
                .addData(new WxMpTemplateData("exam", exam, "#000000"))
         .addData(new WxMpTemplateData("time", time, "#000000"))
          .addData(new WxMpTemplateData("place", place, "#000000"));

        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        } catch (Exception e) {
            System.out.println("推送失败：" + e.getMessage());
        }
    }

    // 获取微信公众号关注者列表 OpenID
    public static String getOpenID() throws IOException {
        // 获取 access_token
        String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + APP_ID+ "&secret=" + AppSecret;
        JSONObject tokenObj = getJsonObject(tokenUrl);
        String accessToken = tokenObj.getStr("access_token");

        // 获取关注者列表
        String listUrl = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=" + accessToken;
        JSONObject listObj = getJsonObject(listUrl);
        JSONObject dataObj = listObj.getJSONObject("data");
        JSONArray openidArr = dataObj.getJSONArray("openid");
        System.out.println(openidArr);

        String openid = openidArr.getStr(0);
        System.out.println(openid);

        return openid;
    }

    // 发送 HTTPS 请求，获取 JSON 数据
    private static JSONObject getJsonObject(String url) throws IOException {
        URLConnection conn = new URL(url).openConnection();
        InputStream is = conn.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        reader.close();
        is.close();
        return new JSONObject(sb.toString());
    }

    public static void main(String[] args) throws IOException {
//        pushMsg("oNf_q6cA_SpCwF-oOnTSAAmtrm7w","ss","ss","ssss","sss");
//        System.out.println("成功");
//        getOpenID();
        //生成token
        String s = UUID.randomUUID().toString().replace("-", "");
        System.out.println(s);
    }

}

