package com.example.jktx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;
    //这一步是获取application.properties中设置的发件人邮箱地址
    @Value("${spring.mail.username}")
    private String username ;
    String address = "yd1950223347@163.com";
    /**
     /*
     * @Description  发送邮箱
     * @Date 0:09 2021/5/19
     * @Param []
     * @return void
     **/
    public  String   sendMail(String address,String text) { //String address

        //发邮件
        SimpleMailMessage message = new SimpleMailMessage();
        //发件人邮件地址(上面获取到的，也可以直接填写,string类型)
        message.setFrom(username);
        //要发送的qq邮箱(收件人地址)
        message.setTo(address);//address
        //邮件主题
        message.setSubject("考试监控提醒信息");
        //邮件正文
        message.setText(text);//！！！
        javaMailSender.send(message);
        return "发送成功";
    }

}
