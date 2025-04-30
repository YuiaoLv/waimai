package com.sky.mapper;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper

public interface ShoppingCartMapper {
    //动态查询
    List<ShoppingCart> list(ShoppingCart shoppingCart);

    /**
     * 根据id修改商品数量
     * @param shoppingCart1
     */
    @Update("update sky_take_out.shopping_cart set number = #{number} where id = #{id}")
    void updateNumberById(ShoppingCart shoppingCart1);

    /**
     * 插入购物车数据
     * @param shoppingCart
     */
    @Insert("insert into sky_take_out.shopping_cart (name, user_id, dish_id, setmeal_id, dish_flavor, number, amount, image, create_time) values (#{name}, #{userId}, #{dishId}, #{setmealId}, #{dishFlavor}, #{number}, #{amount}, #{image}, #{createTime})")
    void insert(ShoppingCart shoppingCart);

    @Delete("delete from sky_take_out.shopping_cart where user_id = #{currentId}")
    void deleteByUserId(Long currentId);

    @Delete("delete from sky_take_out.shopping_cart where id = #{id}")
    void deleteById(Long id);
}
