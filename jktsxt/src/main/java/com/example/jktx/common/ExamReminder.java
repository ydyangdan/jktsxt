package com.example.jktx.common;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.jktx.mapper.ExamMapper;
import com.example.jktx.mapper.UserMapper;
import com.example.jktx.pojo.Exam;
import com.example.jktx.pojo.User;
import com.example.jktx.service.MailService;
import com.example.jktx.service.PhoneService;
import com.example.jktx.service.WechatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Component
public class ExamReminder {

    @Autowired
    private ExamMapper examMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MailService mailService;

//    @Scheduled(fixedDelay = 60000) // 每分钟检查一次
    public void remindUpcomingExams() {
        List<Exam> exams = examMapper.selectList(new QueryWrapper<>());
        Date now = new Date();
        Instant instantNow = now.toInstant();
        for (Exam exam : exams) {
            Instant instantExam = exam.getStartTime().toInstant();
            Duration duration = Duration.between(instantNow, instantExam);
            //提前一个小时
            if (duration.toMinutes() == 60) {

                String teacher = exam.getTeacher();
                String[] teas = teacher.split(",");
                for (int i = 0; i < teas.length; i++) {
                    User user = userMapper.findByName(teas[i]);
                    String remindType = user.getRemindType();
                    String[] strings = remindType.split(",");
                    for (int i1 = 0; i1 < strings.length; i1++) {
                        if ("微信提醒".equals(strings[i1])) {
                            WechatService.pushMsg(user.getWechat(),user.getUserName(),exam.getExamName(),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(exam.getStartTime()),exam.getLocation());
                        }else if ("短信提醒".equals(strings[i1])){
                            PhoneService.sendPhone("1010",user.getPhone());
                        }else if ("邮件提醒".equals(strings[i1])){
                            mailService.sendMail(user.getMail(), user.getUserName()+"，你有一个监考，考试名称是"+exam.getExamName()+",考试时间是"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(exam.getStartTime())+"，考试地点是"
                            +exam.getLocation()+"。");
                        }
                    }
                }
                // 发送提醒消息
                // TODO: 实现发送微信消息或邮件提醒的逻辑
            }
        }
    }
}

