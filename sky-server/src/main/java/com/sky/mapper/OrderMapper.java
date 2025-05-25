package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.entity.OrderDetail;
import com.sky.entity.Orders;
import com.sky.vo.OrderVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {



    void insert(Orders orders);

    /**
     * 根据订单号查询订单
     * @param orderNumber
     */
    @Select("select * from sky_take_out.orders where number = #{orderNumber}")
    Orders getByNumber(String orderNumber);

    /**
     * 分页查询
     * @param ordersPageQueryDTO
     * @return
     */
    Page<Orders> pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);

    @Select("select * from sky_take_out.orders where id = #{id}")
    Orders getById(Long id);

    /**
     * 修改订单信息
     * @param orders
     */
    void update(Orders orders);

    @Select("select count(id) from sky_take_out.orders where status = #{status}")
    Integer countStatus(Integer status);


    @Update("update sky_take_out.orders set status = #{orderStatus},pay_status = #{orderPaidStatus} ,checkout_time = #{check_out_time} where id = #{id}")
    void updateStatus(Integer orderStatus, Integer orderPaidStatus, LocalDateTime check_out_time, Long id);
    /**
     * 根据状态查询订单
     * @param status
     * @return
     */
    @Select("select * from sky_take_out.orders where status = #{status} and order_time < #{orderTime}")
    List<Orders> getByStatusAndOrderTimeLT(Integer status, LocalDateTime orderTime);



    /**
     * 统计营业额
     * @Map
     * @return
     */
    Double sumByMap(Map map);

    /**
     * 统计订单数目
     * @param map
     * @return
     */
    Integer countByMap(Map map);
}
