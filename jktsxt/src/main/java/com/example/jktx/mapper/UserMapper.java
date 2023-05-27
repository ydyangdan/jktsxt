package com.example.jktx.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.jktx.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("select * from user where user_name = #{userName} and password = #{password} and user_role = #{userRole}")
    User findByUser(User user);

    @Insert("insert into user values (null,#{userName},#{password},null,null,#{userRole},null,null,null)")
    int save(User user);

    @Select("select * from user where user_name=#{userName}")
    User findByName(String name);

    @Update("update user set remind_type = #{remindType}, duration = #{duration}, wechat = #{wechat}, phone = #{phone}, mail = #{mail} where user_name=#{userName}")
    void updateByName(User user);

//    User findByPassword(User user);
}
