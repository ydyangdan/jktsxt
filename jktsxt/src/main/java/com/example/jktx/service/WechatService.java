package com.example.jktx.service;

import com.example.jktx.common.WXUtils;
import org.springframework.stereotype.Service;

@Service
public class WechatService {
    public static void pushMsg(String openId, String name,String exam, String time,String place){
        WXUtils.pushMsg(openId,name,exam,time,place);
    }
}
