package com.cgvsu.math.Vector.Vectors;

import com.cgvsu.math.Exceptions.WrongAmountOfAxesGivenException;
import com.cgvsu.math.Exceptions.VectorExceptions.WrongDimensionVectorException;

public final class Vector2D extends AbstractVector {
    public Vector2D(float... axes) {
        vector = new float[2];
        switch (axes.length) {
            case 0 -> {} // Java initialized vector with zeros by default
            case 2 -> System.arraycopy(axes, 0, vector, 0, 2);
            default -> throw new WrongAmountOfAxesGivenException();
        }
    }

    public Vector2D(AbstractVector vector) {
        if (vector.getClass() != this.getClass()) throw new WrongDimensionVectorException();
        this.vector = new float[2];
        System.arraycopy(vector.vector, 0, this.vector, 0, 2);
    }
}
