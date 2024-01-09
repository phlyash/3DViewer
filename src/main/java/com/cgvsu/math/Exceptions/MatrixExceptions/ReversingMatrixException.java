package com.cgvsu.math.Exceptions.MatrixExceptions;

public class ReversingMatrixException extends RuntimeException {
    public ReversingMatrixException() {
        super("This matrix doesn't have reversed matrix");
    }
}
