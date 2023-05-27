package com.example.jktx.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Data
@Repository
public class Leaves {
    /**
     * ID
     */
    @TableId(value = "id",type = IdType.AUTO)
    private int id;

    private String testName;
    private Date time;
    private String teacher;
    private String reasons;
    private String state;
    private Date createTime;
}
