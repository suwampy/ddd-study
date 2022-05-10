package com.ddd.shop;

import lombok.AllArgsConstructor;

// 주문 항목
@AllArgsConstructor
public class OrderLine {
    private Product product; // 주문할 상품
    private Money price; // 상품의 가격
    private int quantity; // 구매 개수
    private Money amounts; // 구매 항목의 구매 가격

    public OrderLine(Product product, Money price, int quantity) {
        this.product = product;
        this.price = price;
        this.quantity = quantity;
        this.amounts = calculateAmounts();
    }

    // 각 상품의 구매 가격 합은 상품 가격에 구매 개수를 곱한 값이다
    private Money calculateAmounts() {
        return price.multiply(quantity);
    }
}
