package com.example.jktx.service;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import org.apache.commons.lang3.StringUtils;
import com.aliyuncs.http.MethodType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Component
public class PhoneService {
    public static boolean send(Map<String, Object> param, String phone) {

        if(StringUtils.isEmpty(phone)) return false;

        //default 地域节点，默认就好  后面是 阿里云的 id和秘钥（这里记得去阿里云复制自己的id和秘钥哦）
        DefaultProfile profile = DefaultProfile.getProfile("default", "LTAI5tRnmwTpT1oJtcD5cbQR", "iEBUfXR1K0lI8eepkb654jQqzSdGAj");
        IAcsClient client = new DefaultAcsClient(profile);

        //这里不能修改
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");

        request.putQueryParameter("PhoneNumbers", phone);                    //手机号
        request.putQueryParameter("SignName", "阿里云短信测试");    //申请阿里云 签名名称（暂时用阿里云测试的，自己还不能注册签名）
        request.putQueryParameter("TemplateCode", "SMS_154950909"); //申请阿里云 模板code（用的也是阿里云测试的）
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));

        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            return response.getHttpResponse().isSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String  sendPhone(String code,String phone) {

        Map<String,Object> param = new HashMap<>();
        param.put("code", code);

        //调用方法
        boolean isSend = send(param,phone);

        return Boolean.toString(isSend);
    }


}
