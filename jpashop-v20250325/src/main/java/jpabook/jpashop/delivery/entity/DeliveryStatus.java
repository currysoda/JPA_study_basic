package jpabook.jpashop.delivery.entity;

public enum DeliveryStatus {
	READY(1), COMPLETE(2);
	
	private int code;
	
	private DeliveryStatus(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
}