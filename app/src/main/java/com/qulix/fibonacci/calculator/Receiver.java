package com.qulix.fibonacci.calculator;

public interface Receiver<T> {
    void receive(T t);
}
