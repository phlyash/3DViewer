package com.cgvsu.math.Vector.Vectors;

import com.cgvsu.math.Exceptions.WrongAmountOfAxesGivenException;
import com.cgvsu.math.Exceptions.VectorExceptions.WrongDimensionVectorException;
import com.cgvsu.math.Vector.VectorUtils;

public final class Vector3D extends AbstractVector {
    public Vector3D(float... axes) {
        vector = new float[3];
        switch (axes.length) {
            case 0 -> {} // Java initialized vector with zeros by default
            case 3 -> System.arraycopy(axes, 0, vector, 0, 3);
            default -> throw new WrongAmountOfAxesGivenException();
        }
    }

    public Vector3D(AbstractVector vector) {
        if (vector.getClass() != this.getClass()) throw new WrongDimensionVectorException();
        this.vector = new float[3];
        System.arraycopy(vector.vector, 0, this.vector, 0, 3);
    }

    public void product(Vector3D vector) {
        VectorUtils.vectorProduct(this, vector);
    }
}
