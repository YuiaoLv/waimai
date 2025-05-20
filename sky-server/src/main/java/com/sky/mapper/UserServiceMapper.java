package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.User;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserServiceMapper {

    @Select("select * from sky_take_out.user where openid = #{openid}")
    User getByOpenid(String openid);

    /*
     * 新增用户
     */
    @AutoFill(value = OperationType.INSERT)
    void insert(User user);

    @Select("select * from sky_take_out.user where id = #{userId}")
    User getById(Long userId);

}
