package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetMealDishMapper;
import com.sky.mapper.SetMealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetMealService;
import com.sky.vo.SetmealVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class SetMealServiceImpl implements SetMealService {

    @Autowired
    private SetMealMapper setMealMapper;
    @Autowired
    private SetMealDishMapper setMealDishMapper;
    @Autowired
    private DishMapper dishMapper;

    /**
     * 新增套餐
     * @param setmealDTO
     */
    @Transactional
    public void saveWithDish(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);
        //插入数据
        setMealMapper.insert(setmeal);
        //获取套餐id
        Long id = setmeal.getId();
        List<SetmealDish> setMealDishes = setmealDTO.getSetmealDishes();
        setMealDishes.forEach(setmealDish -> {
            setmealDish.setSetmealId(id);
        });
        setMealDishMapper.insertBatch(setMealDishes);
    }

    /**
     * 套餐分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    public PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(),setmealPageQueryDTO.getPageSize());
        Page<SetmealVO> page = setMealMapper.pageQuery(setmealPageQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * 批量删除套餐
     * @param ids
     */
    public void deleteBatch(List<Long> ids) {
        //判断套餐是否在售
        List<Setmeal> setMealOnSale = setMealMapper.getSetMealOnSale(ids);
        if(setMealOnSale != null && !setMealOnSale.isEmpty()){
            throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
        }
        //删除套餐
        setMealMapper.deleteBatch(ids);
        //删除套餐中的菜品
        setMealDishMapper.deleteBySetMealIds(ids);
    }

    @Override
    public SetmealVO getByIdWithDish(Long id) {
        SetmealVO setmealVO = new SetmealVO();
        Setmeal setmeal = setMealMapper.getById(id);
        List<SetmealDish> setMealDishes = setMealDishMapper.getBySetMealId(id);
        BeanUtils.copyProperties(setmeal,setmealVO);
        setmealVO.setSetmealDishes(setMealDishes);
        return setmealVO;
    }

    @Override
    public void update(SetmealDTO setmealDTO) {
        //更新套餐表
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);
        setMealMapper.update(setmeal);
        //删除套餐和菜品的关联关系
        Long setmealId = setmealDTO.getId();
        setMealDishMapper.deleteBySetMealId(setmealId);
        //重新插入套餐和菜品的关联关系
        List<SetmealDish> setMealDishes = setmealDTO.getSetmealDishes();
        setMealDishes.forEach(setmealDish -> {
            setmealDish.setSetmealId(setmealId);
        });
        setMealDishMapper.insertBatch(setMealDishes);

    }

    @Override
    public void startOrStop(Integer status, Long id) {
        //套餐起售时判断关联菜品是否都起售
        if(status.equals(StatusConstant.ENABLE)){
            List<Dish> Dishes = dishMapper.getBySetMealId(id);
            if(Dishes != null && !Dishes.isEmpty()){
                Dishes.forEach(dish -> {
                    if(dish.getStatus().equals(StatusConstant.DISABLE)){
                        throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
                    }
                });
            }

        }
        Setmeal setmeal = Setmeal.builder()
                .id(id)
                .status(status)
                .build();
        setMealMapper.update(setmeal);
    }


}
