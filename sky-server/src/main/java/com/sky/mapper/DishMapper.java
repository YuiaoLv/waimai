package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.enumeration.OperationType;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface DishMapper {


    /**
     * 根据分类id查询菜品数量
     * @param categoryId
     * @return
     */
    @Select("select count(id) from  sky_take_out.dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);


    /**
     * 新增菜品
     * @param dish
     */
    @AutoFill(value = OperationType.INSERT)
    void insert(Dish dish);

    /**
     * 新增菜品口味数据
     * @param flavor
     */
    void insertFlavor(List<DishFlavor> flavor);
    /**
     * 菜品分页查询
     * @param dishPageQueryDTO
     * @return
     */
    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);


    /**
     * 根据id查询菜品
     * @param id
     * @return
     */
    @Select("select * from sky_take_out.dish where id = #{id}")
    Dish getById(Long id);
    List<Long> getDishOnSale(List<Long> ids);

    void deleteById(List<Long> ids);
    /**
     * 根据菜品id批量删除菜品口味
     */
    void deleteFlavorsByDishId(List<Long> ids);

    /**
     * 根据菜品id单独删除菜品口味
     */
    @Delete("delete from sky_take_out.dish_flavor where dish_id = #{id}")
    void deleteFlavorByDishId(Long id);
    /**
     * 根据id查询菜品口味
     */
    @Select("select * from sky_take_out.dish_flavor where dish_id = #{dishId}")
    List<DishFlavor> getFlavorsByDishId(Long dishId);

    /**
     * 根据id修改菜品
     */
    @AutoFill(value = OperationType.UPDATE)
    void update(Dish dish);


    List<Dish> getDishByCategoryId(Dish dish);

    @Select("select a.* from sky_take_out.dish a left join sky_take_out.setmeal_dish b on a.id = b.dish_id where b.setmeal_id = #{setmealId}")
    List<Dish> getBySetMealId(Long id);

    /**
     * 根据条件统计菜品数量
     * @param map
     * @return
     */
    Integer countByMap(Map map);
}
