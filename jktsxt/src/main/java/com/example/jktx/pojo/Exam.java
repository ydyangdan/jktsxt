package com.example.jktx.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Data
@Repository
public class Exam {
    /**
     * ID
     */
    @TableId(value = "id")
    private String id;

    private String examName;

    private Date startTime;

    private Integer totalTime;
    private String location;
    private Integer number;
    private String teacher;
    private String userName;
    private String content;
    private Date createTime;
}
