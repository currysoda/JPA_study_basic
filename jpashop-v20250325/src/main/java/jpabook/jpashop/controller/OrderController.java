package jpabook.jpashop.controller;

import jpabook.jpashop.member.entity.Member;
import jpabook.jpashop.order.entity.Order;
import jpabook.jpashop.order.entity.OrderSearch;
import jpabook.jpashop.item.Item;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.service.MemberService;
import jpabook.jpashop.order.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {
	
	private final OrderServiceImpl orderServiceImpl;
	private final MemberService    memberService;
	private final ItemService      itemService;
	
	@GetMapping(value = "/order")
	public String createForm(Model model) {
		
		List<Member> members = memberService.findMembers();
		List<Item>   items   = itemService.findItems();
		
		model.addAttribute("members", members);
		model.addAttribute("items", items);
		
		return "order/orderForm";
	}
	
	@PostMapping(value = "/order")
	public String order(@RequestParam("memberId") Long memberId, @RequestParam("itemId") Long itemId, @RequestParam("count") int count) {
		
		orderServiceImpl.order(memberId, itemId, count);
		return "redirect:/orders";
	}
	
	@GetMapping(value = "/orders")
	public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model) {
		
		List<Order> orders = orderServiceImpl.findOrders(orderSearch);
		model.addAttribute("orders", orders);
		
		return "order/orderList";
	}
	
	@PostMapping(value = "/orders/{orderId}/cancel")
	public String cancelOrder(@PathVariable("orderId") Long orderId) {
		
		orderServiceImpl.cancelOrder(orderId);
		
		return "redirect:/orders";
	}
}