package com.example.jktx.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 用户信息
 */
@Data
@Repository
@Validated
@TableName("user")
public class User {

    /**
     * ID
     */
    @TableId(value = "id",type = IdType.AUTO)
    private int id;

    /**
     * 用户名
     */
    @TableField("user_name")
    @NotEmpty(message = "用户名不能为空")
    private String userName;


    /**
     * 密码
     */
    private String password;

    /**
     * 用户身份
     */
    @TableField("user_role")
    private String userRole;

    /**
     * 提醒方式
     */
    private String remindType;

    /**
     *  提前时间
     */
    private double duration;

    /**
     * 微信号
     **/
    private String wechat;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String mail;
    /**
     * 用户性别
     */
    @TableField("user_sex")
    private String userSex;


    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 最新登录时间
     */
    @TableField("latest_login_time")
    private Date latestLoginTime;





}
