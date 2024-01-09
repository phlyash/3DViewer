package com.cgvsu.math.Exceptions;

public class WrongAmountOfAxesGivenException extends RuntimeException {
    public WrongAmountOfAxesGivenException() {
        super("Wrong amount of axes given");
    }
}
