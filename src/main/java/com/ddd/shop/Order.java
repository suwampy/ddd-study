package com.ddd.shop;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Objects;

// 주문->출고 전에 배송지를 변겨알 수 있다. 주문 취소는 배송 전에만 할 수 있다.
public class Order {
    private OrderNo id; // 엔티티가 가지는 식별자
    private List<OrderLine> orderLines; // 한 종류 이상의 상품을 주문할 수 있으므로 Order는 최소 한 개 이상의 OrderLine을 포함해야 한다
    private Money totalAmounts; // 총 주문 금액
    private ShippingInfo shippingInfo; // 배송지 정보
    private OrderState state; // 주문 상태

    public Order(Orderer orderer, List<OrderLine> orderLines, ShippingInfo shippingInfo, OrderState state) {
        setOrderer(orderer);
        setOrderLines(orderLines);
        setShippingInfo(shippingInfo);
    }

    private void setOrderer(Orderer orderer) {
        if (orderer == null)
            throw new IllegalArgumentException("no orderer");
        this.orderer = orderer;
    }

    private void setOrderLines(List<OrderLine> orderLines) {
        verifyAtLeastOneOrMoreOrderLines(orderLines);
        this.orderLines = orderLines;
        calculateTotalAmounts();
    }

    private void verifyAtLeastOneOrMoreOrderLines(List<OrderLine> orderLines) {
        if (orderLines == null || orderLines.isEmpty()) {
            throw new IllegalArgumentException("no OrderLine");
        }
    }

    private void calculateTotalAmounts() {
        int sum = orderLines.stream()
                .mapToInt(x -> x.getAmounts())
                .sum();
        this.totalAMmunts = new Money(sum);
    }

    private void setShippingInfo(ShippingInfo shippingInfo) {
        if (shippingInfo == null) { // 배송지 정보 필수 도메인 규칙 구현
            throw new IllegalArgumentException("no ShippingInfo");
        }
        this.shippingInfo = shippingInfo;
    }

    // 출고 상태로 변경하기
    public void changeShipped() {}

    // 배송지 정보 변경하기
    public void changeShippingInfo(ShippingInfo newShipping){
        verifyNotYetShipped();
    }

    // 주문 취소하기
    public void cancel() {
        verifyNotYetShipped();
        this.state = OrderState.CANCELED;
    }

    // 배송지 변경이나 주문 취소 기능은 출고 전에만 가능하다는 제약 규칙
    public void verifyNotYetShipped() {
        if (state != OrderState.PAYMENT_WATTING && state != OrderState.PREPARING)
            throw new IllegalArgumentException("already shipped");
    }

    // 결제 완료하기
    public void completePayment(){}

    public OrderNo getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(orderNumber, order.orderNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderNumber);
    }
}
