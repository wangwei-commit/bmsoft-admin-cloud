package com.bmsoft.cloud.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bmsoft.cloud.order.dao.OrderMapper;
import com.bmsoft.cloud.order.entity.Order;
import com.bmsoft.cloud.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 订单
 * </p>
 *
 * @author bmsoft
 * @date 2019-08-13
 */
@Slf4j
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

}
