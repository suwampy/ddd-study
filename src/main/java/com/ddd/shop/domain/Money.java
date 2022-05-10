package com.ddd.shop.domain;

public class Money {
    private int value;

    public Money(int value) {
        this.value = value;
    }

    public Money add(Money money) {
        return new Money(this.value + money.value);
    }

    public Money multiply(int multiplier) {
        return new Money(value * multiplier);
    }

    public int getValue() {
        return this.value;
    }

    // value를 변경할 수 있는 메서드 없음
 }
