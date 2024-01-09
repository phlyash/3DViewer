package com.cgvsu.math.Matrix.Matrixes;

import com.cgvsu.math.Matrix.MatrixUtils;
import com.cgvsu.math.Vector.Vectors.AbstractVector;

import java.util.Arrays;

//TODO special exception for reversing matrix and constructor for matrixes with floats
public abstract class AbstractMatrix {
    protected AbstractVector[] vectors;

    public void addMatrix(AbstractMatrix matrix) {
        MatrixUtils.addition(this, matrix);
    }

    public void subtractMatrix(AbstractMatrix matrix) {
        MatrixUtils.subtraction(this, matrix);
    }

    public void multiply(float number) {
        MatrixUtils.multiply(this, number);
    }

    public void divide(float number) {
        MatrixUtils.divide(this, number);
    }

    public AbstractVector multiplyOnVector(AbstractVector vector) {
        return MatrixUtils.multiplicationOnVector(this, vector);
    }

    public void transpose() {
        MatrixUtils.transpose(this);
    }

    public void multiplyOnMatrix(AbstractMatrix matrix) {
        MatrixUtils.matrixMultiplication(this, matrix);
    }

    public void reverseMatrix() {
        MatrixUtils.reverseMatrix(this);
    }

    public AbstractVector getVector(int number) {
        return vectors[number];
    }

    public float getElement(int y, int x) {
        return vectors[x].get(y);
    }

    public void setVector(AbstractVector vector, int number) {
        vectors[number] = vector;
    }

    public void setElement(float number, int y, int x) {
        vectors[x].set(number, y);
    }

    public int getLength() {
        return vectors.length;
    }
    
    public int getVectorLength(int vector) {
        return vectors[vector].getLength();
    }

    public AbstractMatrix getMinor(int y, int x) {
        return MatrixUtils.getMinor(this, y, x);
    }

    public float getDeterminant() {
        float determinant = 0;
        for(int i = 0; i < this.getLength(); i++)
            // we can use here MatrixUtils.calculateCofactor but this could be a bit faster
            determinant += this.getVector(i).get(0) * this.getMinor(0, i).getDeterminant() * (i % 2 == 0 ? 1 : -1);

        return determinant;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        AbstractMatrix that = (AbstractMatrix) object;
        return Arrays.equals(vectors, that.vectors);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(vectors);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() +
                "{vectors=" + Arrays.toString(vectors) +
                '}';
    }
}
