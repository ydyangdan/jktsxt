package com.example.jktx.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.jktx.api.ApiRest;
import com.example.jktx.api.controller.BaseController;
import com.example.jktx.mapper.LeavesMapper;
import com.example.jktx.mapper.NoticeMapper;
import com.example.jktx.pojo.Exam;
import com.example.jktx.pojo.Leaves;
import com.example.jktx.pojo.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping("/leaves")
public class LeavesController extends BaseController {
    @Autowired
    private LeavesMapper leavesMapper;
    @Autowired
    private HttpServletRequest request;

    /**
     * 请假信息分页列表查询
     *
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @GetMapping("/queryLeaves")
    public ApiRest<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "10") Integer pageSize,
                               @RequestParam(defaultValue = "" ) String search) {
        LambdaQueryWrapper<Leaves> wrapper = Wrappers.<Leaves>lambdaQuery().orderByAsc(Leaves::getId);

        if (StrUtil.isNotBlank(search)) {
            wrapper.like(Leaves::getTestName, search);
        }
        String userRole = (String) request.getSession().getAttribute("userRole");
        String  userName= (String) request.getSession().getAttribute("userName");
        if ("教师".equals(userRole)){
            wrapper.like(Leaves::getTeacher,userName);
        }
        Page<Leaves> userPage = leavesMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return success(userPage);
    }

    /**
     * 新增请假信息
     * @return
     */
    @PostMapping("/save")
    public ApiRest<String> save(@RequestBody Leaves leaves ){
        String  userName= (String) request.getSession().getAttribute("userName");
        leaves.setTeacher(userName);
//        leaves.setTime(new Date());
        leaves.setCreateTime(new Date());
        try {
            leavesMapper.insert(leaves);
        }catch (Exception e){
            return failure("新增失败");
        }
        return success("新增成功");

    }

    /**
     * 删除请假信息
     * @return
     */
    @DeleteMapping("/{id}")
    public ApiRest<String> delete(@PathVariable Long id) {
        try {
            leavesMapper.deleteById(id);
        }catch (Exception e){
            return failure("删除失败");
        }
        return success("删除成功");
    }

    /**
     * 编辑请假信息
     * @return
     */
    @PutMapping()
    public ApiRest<String> update(@RequestBody Leaves leaves){
        try {
            leavesMapper.updateById(leaves);
        }catch (Exception e){
            return failure("编辑失败");
        }
        return success("编辑成功");

    }
}
