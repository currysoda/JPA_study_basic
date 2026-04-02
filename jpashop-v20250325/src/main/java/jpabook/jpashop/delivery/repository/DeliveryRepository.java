package jpabook.jpashop.delivery.repository;


import jpabook.jpashop.delivery.entity.Delivery;

public interface DeliveryRepository {
	
	Delivery save(Delivery delivery);
	
	
}
