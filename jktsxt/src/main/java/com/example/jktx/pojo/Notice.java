package com.example.jktx.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.Date;
/**
 * 公告
 */
@Repository
@Data
public class Notice {
    /**
     * ID
     */
    @TableId(value = "id",type = IdType.AUTO)
    private int id;

    /**
     * 公告标题
     */
    private String noticeName;
    /**
     * 公告时间
     */
    private Date time;
    /**
     * 内容
     */
    private String content;
}
