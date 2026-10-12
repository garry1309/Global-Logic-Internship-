package com.gaurav.internship.calculator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class CalculatorTest {

    @Test
    void addsTwoPositiveNumbers() {
        Calculator calculator = new Calculator();
        assertEquals(5, calculator.add(2, 3));
    }

    @Test
    void addsNegativeNumbers() {
        Calculator calculator = new Calculator();
        assertEquals(-1, calculator.add(-3, 2));
    }

    @Test
    void subtractsTwoNumbers() {
        Calculator calculator = new Calculator();
        assertEquals(1, calculator.subtract(4, 3));
    }

    @Test
    void multipliesTwoNumbers() {
        Calculator calculator = new Calculator();
        assertEquals(12, calculator.multiply(4, 3));
    }

    @Test
    void divideThrowsOnZeroDivisor() {
        Calculator calculator = new Calculator();
        assertThrows(ArithmeticException.class, () -> calculator.divide(10, 0));
    }

    @Test
    void dividesTwoNumbers() {
        Calculator calculator = new Calculator();
        assertEquals(2.5, calculator.divide(5, 2));
    }
}
