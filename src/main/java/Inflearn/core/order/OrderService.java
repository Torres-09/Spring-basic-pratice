package Inflearn.core.order;

public interface OrderService {
    Order createOrder(Long orderId, Long memberId, String itemName, int itemPrice);
}
