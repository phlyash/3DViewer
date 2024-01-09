package com.cgvsu.math.Matrix.Matrixes;

import com.cgvsu.math.Exceptions.MatrixExceptions.ReversingMatrixException;
import com.cgvsu.math.Exceptions.MatrixExceptions.WrongDimensionMatrixException;
import com.cgvsu.math.Exceptions.WrongAmountOfArgumentsException;
import com.cgvsu.math.Exceptions.WrongAmountOfAxesGivenException;
import com.cgvsu.math.Vector.Vectors.Vector2D;

public final class Matrix2D extends AbstractMatrix {
    public Matrix2D(Vector2D... vectors) {
        if (vectors.length != 2) throw new WrongAmountOfAxesGivenException();

        this.vectors = new Vector2D[2];
        System.arraycopy(vectors, 0, this.vectors, 0, 2);
    }

    public Matrix2D(float... numbers) {
        if (numbers.length != 4) throw new WrongAmountOfArgumentsException();

        this.vectors = new Vector2D[2];
        for(int i = 0; i < 2; i++)
            this.vectors[i] = new Vector2D(numbers[i * 2], numbers[i * 2 + 1]);
    }

    public Matrix2D() {
        this.vectors = new Vector2D[2];
        for(int i = 0; i < 2; i++) this.vectors[i] = new Vector2D();
    }

    public Matrix2D(AbstractMatrix matrix) {
        if (matrix.getClass() != this.getClass()) throw new WrongDimensionMatrixException();

        this.vectors = new Vector2D[2];
        for(int i = 0; i < 2 ; i++)
            this.vectors[i] = new Vector2D(matrix.vectors[i]);
    }

    @Override
    public float getDeterminant() {
        return this.getVector(0).get(0) * this.getVector(1).get(1) -
                this.getVector(1).get(0) * this.getVector(0).get(1);
    }

    @Override
    public void reverseMatrix() {
        float determinant = getDeterminant();
        if (Math.abs(determinant) < 1e-6) throw new ReversingMatrixException();

        this.setElement(-this.getElement(0, 1), 0, 1);
        this.setElement(-this.getElement(1, 0), 1, 0);

        this.setElement(getElement(1, 1) + getElement(0, 0), 1, 1);
        this.setElement(getElement(1, 1) - getElement(0, 0), 0, 0);
        this.setElement(getElement(1, 1) - getElement(0, 0), 1, 1);

        this.divide(determinant);
    }

    @Override
    public AbstractMatrix getMinor(int y, int x) {
        throw new RuntimeException("Minors for 2D matrixes are not supported");
    }
}
