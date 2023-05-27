package com.example.jktx.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.jktx.api.ApiRest;
import com.example.jktx.api.controller.BaseController;
import com.example.jktx.mapper.NoticeMapper;
import com.example.jktx.pojo.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/notice")
public class NoticeController extends BaseController {
    @Autowired
    private NoticeMapper noticeMapper;
    @Autowired
    private HttpServletRequest request;

    /**
     * 公告分页列表查询
     *
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @GetMapping("/queryNotice")
    public ApiRest<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "10") Integer pageSize,
                               @RequestParam(defaultValue = "" ) String search) {
        LambdaQueryWrapper<Notice> wrapper = Wrappers.<Notice>lambdaQuery().orderByAsc(Notice::getId);

        if (StrUtil.isNotBlank(search)) {
            wrapper.like(Notice::getNoticeName, search);
        }

        Page<Notice> userPage = noticeMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return success(userPage);
    }

    /**
     * 新增公告
     * @return
     */
    @RequestMapping("/save")
    public ApiRest<String> save(@RequestBody Notice notice ){
        notice.setTime(new Date());
        try {
            noticeMapper.insert(notice);
        }catch (Exception e){
            return failure("新增失败");
        }
        return success("新增成功");

    }

    /**
     * 删除公告
     * @return
     */
    @DeleteMapping("/{id}")
    public ApiRest<String> delete(@PathVariable Long id) {
        try {
            noticeMapper.deleteById(id);
        }catch (Exception e){
            return failure("删除失败");
        }
        return success("删除成功");
    }

    /**
     * 编辑公告
     * @return
     */
    @PutMapping()
    public ApiRest<String> update(@RequestBody Notice notice){

        try {
            noticeMapper.updateById(notice);
        }catch (Exception e){
            return failure("编辑失败");
        }
        return success("编辑成功");

    }

    /**
     * 公告查询
     *
     * @return
     */
    @GetMapping("/getNotice")
    public ApiRest<?> getNotice() {
        LambdaQueryWrapper<Notice> wrapper = Wrappers.<Notice>lambdaQuery().orderByAsc(Notice::getId);
        List<Notice> list = noticeMapper.selectList(wrapper);
        return success(list);
    }


}
