package com.example.jktx.controller;



import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.jktx.api.ApiError;
import com.example.jktx.api.ApiRest;
import com.example.jktx.api.controller.BaseController;
import com.example.jktx.common.WXUtils;
import com.example.jktx.mapper.UserMapper;
import com.example.jktx.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;


@Controller
@RequestMapping("/user")
@ResponseBody
@CrossOrigin
public class UserController extends BaseController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private HttpServletRequest request;

    /**
     * 登录
     * @return
     */
    @RequestMapping("/login")
    public String login(){
        return "forward:../index.html";
    }

    /**
     * 登录验证
     * @return
     */
    @RequestMapping("/loginCheck")
    public ApiRest<String> loginSuccess(@RequestBody User user){

        User user1 = userMapper.findByUser(user);
        if (user1==null){
            return failure(ApiError.ERROR_90010002.msg);
        }
        request.getSession().setAttribute("userName",user.getUserName());
        request.getSession().setAttribute("userRole",user.getUserRole());
        user1.setLatestLoginTime(new Date());
        userMapper.updateById(user1);
        if ("管理员".equals(user.getUserRole())){
            return success("登录成功","admin");
        }else if ("教师".equals(user.getUserRole())){
            return success("登录成功","teacher");
        }else {
            return success("登录成功","student");
        }
    }

    /**
     * 注册
     * @return
     */
    @RequestMapping("/registerCheck")
    public ApiRest<String> register( @RequestBody User user){
        user.setCreateTime(new Date());
        try {
          userMapper.insert(user);
        }catch (Exception e){
            return failure("注册失败");
        }
        return success("注册成功，请登录","login");

    }

    /**
     * 用户退出
     * @return
     */
    @RequestMapping(value = "/logout", method = {RequestMethod.POST})
    public ApiRest<String> logout() {
        request.getSession().removeAttribute("userName");
        return success("index.html");
    }

    /**
     * 新增用户
     * @return
     */
    @RequestMapping("/save")
    public ApiRest<String> save(@RequestBody User user){
     if (user.getPassword()==null){
         user.setPassword("123456");
     }
     user.setCreateTime(new Date());
        try {
         userMapper.insert(user);
        }catch (Exception e){
            return failure("新增失败");
        }
        return success("新增成功");

    }

    /**
     * 设置提醒方式
     * @return
     */
    @RequestMapping("/add")
    public ApiRest<String> add(@RequestBody User user) throws IOException {
        String openID = WXUtils.getOpenID();
        String userName = (String) request.getSession().getAttribute("userName");
        user.setUserName(userName);
        user.setWechat(openID);
        try {
            userMapper.updateByName(user);
        }catch (Exception e){
            return failure("保存失败");
        }
        return success("保存成功");

    }
    /**
     * 编辑用户
     * @return
     */
    @PutMapping()
    public ApiRest<String> update(@RequestBody User user){
        try {
           userMapper.updateById(user);
        }catch (Exception e){
            return failure("编辑失败");
        }
        return success("编辑成功");

    }

    /**
     * 删除用户
     * @return
     */
    @DeleteMapping("/{id}")
    public ApiRest<String> delete(@PathVariable Long id) {
        try {
            userMapper.deleteById(id);
        }catch (Exception e){
            return failure("删除失败");
        }
        return success("删除成功");
    }


    /**
     * 用户分页列表查询，包含用户表的一对多查询
     *
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @GetMapping("/queryUser")
    public ApiRest<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "" ) String search) {
        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery().orderByAsc(User::getId);
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(User::getUserName, search);
        }

        Page<User> userPage = userMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);

//        Page<User> userPage = userMapper.selectPage(new Page<>(pageNum, pageSize),
//                  Wrappers.<User>lambdaQuery().like(User::getUserName, search));
        return success(userPage);
    }

}
