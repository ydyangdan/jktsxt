package com.example.jktx.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.jktx.api.ApiRest;
import com.example.jktx.api.controller.BaseController;
import com.example.jktx.mapper.ExamMapper;
import com.example.jktx.pojo.Exam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@RestController
@RequestMapping("/exam")
public class ExamController extends BaseController {
    @Autowired
    private ExamMapper examMapper;
    @Autowired
    private HttpServletRequest request;

    /**
     * 考试分页列表查询
     *
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @GetMapping("/queryExam")
    public ApiRest<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "10") Integer pageSize,
                               @RequestParam(defaultValue = "" ) String search) {
        LambdaQueryWrapper<Exam> wrapper = Wrappers.<Exam>lambdaQuery().orderByAsc(Exam::getId);

        if (StrUtil.isNotBlank(search)) {
            wrapper.like(Exam::getExamName, search);
        }
        String userRole = (String) request.getSession().getAttribute("userRole");
        String  userName= (String) request.getSession().getAttribute("userName");
        if ("教师".equals(userRole)){
            wrapper.like(Exam::getTeacher,userName);
        }
        Page<Exam> userPage = examMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return success(userPage);
    }

    /**
     * 新增考试
     * @return
     */
    @RequestMapping("/save")
    public ApiRest<String> save(@RequestBody Exam exam){
        String  userName= (String) request.getSession().getAttribute("userName");
        exam.setUserName(userName);
//        exam.setStartTime(new Date());
        exam.setCreateTime(new Date());

        try {
            examMapper.insert(exam);
        }catch (Exception e){
            return failure("新增失败");
        }
        return success("新增成功");

    }

    /**
     * 删除考试
     * @return
     */
    @DeleteMapping("/{id}")
    public ApiRest<String> delete(@PathVariable String id) {
        try {
            examMapper.deleteById(id);
        }catch (Exception e){
            return failure("删除失败");
        }
        return success("删除成功");
    }

    /**
     * 编辑考试
     * @return
     */
    @PutMapping()
    public ApiRest<String> update(@RequestBody Exam exam){

        try {
            examMapper.updateById(exam);
        }catch (Exception e){
            return failure("编辑失败");
        }
        return success("编辑成功");

    }
}
