package com.cgvsu.math.Matrix.Matrixes;

import com.cgvsu.math.Exceptions.MatrixExceptions.WrongDimensionMatrixException;
import com.cgvsu.math.Exceptions.WrongAmountOfArgumentsException;
import com.cgvsu.math.Exceptions.WrongAmountOfAxesGivenException;

import com.cgvsu.math.Vector.Vectors.Vector4D;

public final class Matrix4D extends AbstractMatrix {
    public Matrix4D(Vector4D... vectors) {
        if(vectors.length != 4) throw new WrongAmountOfAxesGivenException();

        this.vectors = new Vector4D[4];
        System.arraycopy(vectors, 0, this.vectors, 0, 4);
    }

    public Matrix4D(float... numbers) {
        if(numbers.length != 16) throw new WrongAmountOfArgumentsException();

        this.vectors = new Vector4D[4];
        for(int i = 0; i < 4; i++)
            this.vectors[i] = new Vector4D(numbers[i * 4], numbers[i * 4 + 1], numbers[i * 4 + 2], numbers[i * 4 + 3]);
    }

    public Matrix4D() {
        this.vectors = new Vector4D[4];
        for(int i = 0; i < 4; i++) this.vectors[i] = new Vector4D();
    }

    public Matrix4D(AbstractMatrix matrix) {
        if (matrix.getClass() != this.getClass()) throw new WrongDimensionMatrixException();

        this.vectors = new Vector4D[4];
        for(int i = 0; i < 4 ; i++)
            this.vectors[i] = new Vector4D(matrix.vectors[i]);
    }
}
