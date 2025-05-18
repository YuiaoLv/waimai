package com.sky.service;

import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.entity.OrderDetail;
import com.sky.result.PageResult;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;

public interface OrderService {




    OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);

    /**
     * 订单分页查询
     * @param ordersPageQueryDTO
     * @return
     */
    PageResult pageQuery4Admin(OrdersPageQueryDTO ordersPageQueryDTO);

    OrderVO getOrderDetail(Long id);

    void userCancelById(Long id);

    void repetition(Long id);
}
