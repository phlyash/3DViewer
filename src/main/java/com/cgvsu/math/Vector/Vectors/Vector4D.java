package com.cgvsu.math.Vector.Vectors;

import com.cgvsu.math.Exceptions.WrongAmountOfAxesGivenException;
import com.cgvsu.math.Exceptions.VectorExceptions.WrongDimensionVectorException;

public final class Vector4D extends AbstractVector {
    public Vector4D(float... axes) {
        vector = new float[4];
        switch (axes.length) {
            case 0 -> {} // Java initialized vector with zeros by default
            case 4 -> System.arraycopy(axes, 0, vector, 0, 4);
            default -> throw new WrongAmountOfAxesGivenException();
        }
    }

    public Vector4D(AbstractVector vector) {
        if (vector.getClass() != this.getClass()) throw new WrongDimensionVectorException();
        this.vector = new float[4];
        System.arraycopy(vector.vector, 0, this.vector, 0, 4);
    }
}
