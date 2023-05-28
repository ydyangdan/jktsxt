package com.example.jktx.common;
import cn.hutool.json.JSONString;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.tbp.v20190627.TbpClient;
import com.tencentcloudapi.tbp.v20190627.models.*;
public class Test {

    public static void main(String [] args) {
        try{
            // 实例化一个认证对象，入参需要传入腾讯云账户 SecretId 和 SecretKey，此处还需注意密钥对的保密
            // 代码泄露可能会导致 SecretId 和 SecretKey 泄露，并威胁账号下所有资源的安全性。以下代码示例仅供参考，建议采用更安全的方式来使用密钥，请参见：https://cloud.tencent.com/document/product/1278/85305
            // 密钥可前往官网控制台 https://console.cloud.tencent.com/cam/capi 进行获取
            Credential cred = new Credential("AKID6ybyISQAkIfpkpfGbicP7sOgjW7UcUAa", "KPuHUUFix1q294jIhqhHfrr196pIXfaW");
            // 实例化一个http选项，可选的，没有特殊需求可以跳过
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("tbp.tencentcloudapi.com");
            // 实例化一个client选项，可选的，没有特殊需求可以跳过
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            // 实例化要请求产品的client对象,clientProfile是可选的
            TbpClient client = new TbpClient(cred, "", clientProfile);
            // 实例化一个请求对象,每个接口都会对应一个request对象
            TextProcessRequest req = new TextProcessRequest();
            req.setBotId("8f940585-c491-4417-a96b-f3940d7fd3d4");
            req.setBotEnv("dev");
            req.setTerminalId("123");
            req.setInputText("山东");
            // 返回的resp是一个TextProcessResponse的实例，与请求对象对应
            TextProcessResponse resp = client.TextProcess(req);

            // 输出json格式的字符串回包
            String content = TextProcessResponse.toJsonString(resp);
            // 解析 JSON 字符串
            JSONObject jsonObject = JSON.parseObject(content);

            // 访问 "ResponseMessage" 对象
            JSONObject responseMessage = jsonObject.getJSONObject("ResponseMessage");

             // 访问 "GroupList" 数组
            JSONArray groupList = responseMessage.getJSONArray("GroupList");

            // 访问 "GroupList" 数组中的第一个对象
            JSONObject group = groupList.getJSONObject(0);

            // 访问 "Content" 字段
            String contentValue = group.getString("Content");

            // 打印 "Content" 的值
            System.out.println(contentValue);
            System.out.println(content);
        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
        }
    }

}
