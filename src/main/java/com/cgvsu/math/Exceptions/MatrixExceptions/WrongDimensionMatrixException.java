package com.cgvsu.math.Exceptions.MatrixExceptions;

public class WrongDimensionMatrixException extends RuntimeException {
    public WrongDimensionMatrixException() {
        super("Matrixes with wrong dimension given");
    }
}
