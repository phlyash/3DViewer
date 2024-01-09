package com.cgvsu.math.Vector.Vectors;

import com.cgvsu.math.Vector.VectorUtils;

import java.util.Arrays;

public abstract class AbstractVector {
    protected float[] vector;

    public void multiply(float number) {
        VectorUtils.multiply(this, number);
    }

    public void divide(float number) {
        VectorUtils.divide(this, number);
    }

    public void normalize() {
        divide(calculateLength());
    }

    public void set(float number, int axis) {
        vector[axis] = number;
    }

    public float get(int axis) {
        return vector[axis];
    }

    public int getLength() {
        return vector.length;
    }

    public void addVector(AbstractVector vectorToAdd) {
        VectorUtils.addition(this, vectorToAdd);
    }

    public void subtractVector(AbstractVector vectorToSubtract) {
        VectorUtils.subtraction(this, vectorToSubtract);
    }

    public float getScalarMultiplication(AbstractVector vectorToMultiplyBy) {
        return VectorUtils.getScalar(this, vectorToMultiplyBy);
    }

    public void componentWiseMultiplication(AbstractVector vector) {
        VectorUtils.vectorComponentWiseMultiplication(this, vector);
    }

    public float calculateLength() {
        return VectorUtils.getLength(this);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        AbstractVector that = (AbstractVector) object;
        return Arrays.equals(vector, that.vector);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(vector);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() +
                "{vector=" + Arrays.toString(vector) +
                '}';
    }
}
