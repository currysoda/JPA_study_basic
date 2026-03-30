package jpabook.jpashop.order.service;

import jpabook.jpashop.order.entity.Order;
import jpabook.jpashop.order.entity.OrderSearch;

import java.util.List;

public interface OrderService {

	Long order(Long memberId, Long itemId, int count);

	void cancelOrder(Long orderId);

	List<Order> findOrders(OrderSearch orderSearch);
}
