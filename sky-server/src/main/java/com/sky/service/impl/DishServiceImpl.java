package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class DishServiceImpl implements DishService {

@Autowired
   private DishMapper dishMapper;
    /**
     * 新增菜品和对应的口味
     * @param dishDTO
     * @return
     */
   @Transactional
    public void saveWithFlavor(DishDTO dishDTO) {
       //向菜品表插入一个数据
       Dish dish = new Dish();
       BeanUtils.copyProperties(dishDTO,dish);
       dishMapper.insert(dish);

       Long dishId = dish.getId();
       //向口味表插入多条数据
       List<DishFlavor> flavors = dishDTO.getFlavors();
       if(flavors != null && !flavors.isEmpty()){
           flavors.forEach(dishFlavor -> {
               dishFlavor.setDishId(dishId);
           });
           //批量插入
           dishMapper.insertFlavor(flavors);
       }
    }

    /**
     * 菜品分页查询
     * @param dishPageQueryDTO
     * @return
     */
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());
        Page<DishVO> page = dishMapper.pageQuery(dishPageQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());
    }
}
