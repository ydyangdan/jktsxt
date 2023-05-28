package com.example.jktx.controller;

import com.example.jktx.common.InMessage;
import com.example.jktx.common.OutMessage;
import com.example.jktx.common.TextProcess;
import com.example.jktx.common.XmlUtils;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;
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
import java.time.Duration;
import java.util.Map;

@RestController
public class WeChatController {



    @PostMapping(value = "wechat", produces = "application/xml;charset=UTF-8")
    public Object handleMessage(@RequestBody InMessage inMessage){
        // 创建响应消息实体对象
        OutMessage outMessage = new OutMessage();
        // 把原来的接收方设置为发送方
        outMessage.setFromUserName(inMessage.getToUserName());
        // 把原来的发送方设置为接收方
        outMessage.setToUserName(inMessage.getFromUserName());
        // 设置消息类型
        outMessage.setMsgType(inMessage.getMsgType());
        // 设置消息时间
        outMessage.setCreateTime(System.currentTimeMillis());
        // 根据接收到消息类型，响应对应的消息内容
        if ("text".equals(inMessage.getMsgType())){
            // 文本
//            outMessage.setContent(inMessage.getContent());
            String aiResponse = TextProcess.getAiResponse(inMessage.getContent());
            System.out.println(aiResponse);
            outMessage.setContent(aiResponse);
        }else if ("image".equals(inMessage.getMsgType())){
            // 图片
            outMessage.setMediaId(new String[]{inMessage.getMediaId()});
        }
        return outMessage;
    }
//    @GetMapping("/ai")
    public static void sendMsg() throws InterruptedException {
        System.out.println("开始提问题～");
        String GPT_TOKEN = "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";
        //GPT_TOKEN即你的代码密钥
        OpenAiService service = new OpenAiService(GPT_TOKEN, Duration.ofSeconds(10000));
        CompletionRequest completionRequest = CompletionRequest.builder()
                //使用的模型
                .model("text-davinci-003")
                //输入提示语
                .prompt("你是一个工作助手，请帮忙设计一份活动策划书")
                //该值越大每次返回的结果越随机，即相似度越小，可选参数，默认值为 1，取值 0-2
                .temperature(0.5)
                //返回结果最大分词数
                .maxTokens(2048)
                //与temperature类似
                .topP(1D)
                .build();
        service.createCompletion(completionRequest).getChoices().forEach(System.out::println);
        Thread.sleep(6000);
    }

    public static void main(String[] args) throws InterruptedException {
        sendMsg();
    }

}
