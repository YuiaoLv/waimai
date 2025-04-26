package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetMealDishMapper {


    /**
     * 根据菜品id查询套餐id
     */
    List<Long> getSetMealIdsByDishIds(List<Long> ids);

    /**
     * 批量插入套餐菜品关系
     */
    void insertBatch(List<SetmealDish> setMealDishes);

    /**
     * 根据套餐id删除套餐菜品关系
     */
    void deleteBySetMealIds(List<Long> ids);

    @Select("select * from sky_take_out.setmeal_dish where setmeal_id = #{setmealId}")
    List<SetmealDish> getBySetMealId(Long setmealId);
}
