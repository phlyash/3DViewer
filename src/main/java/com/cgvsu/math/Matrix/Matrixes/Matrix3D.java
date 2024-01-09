package com.cgvsu.math.Matrix.Matrixes;

import com.cgvsu.math.Exceptions.MatrixExceptions.WrongDimensionMatrixException;
import com.cgvsu.math.Exceptions.WrongAmountOfArgumentsException;
import com.cgvsu.math.Exceptions.WrongAmountOfAxesGivenException;
import com.cgvsu.math.Vector.Vectors.Vector3D;

public final class Matrix3D extends AbstractMatrix {
    public Matrix3D(Vector3D... vectors) {
        if(vectors.length != 3) throw new WrongAmountOfAxesGivenException();

        this.vectors = new Vector3D[3];
        System.arraycopy(vectors, 0, this.vectors, 0, 3);
    }

    public Matrix3D(float... numbers) {
        if(numbers.length != 9) throw new WrongAmountOfArgumentsException();

        this.vectors = new Vector3D[3];
        for(int i = 0; i < 3; i++)
            this.vectors[i] = new Vector3D(numbers[i * 3], numbers[i * 3 + 1], numbers[i * 3 + 2]);
    }

    public Matrix3D() {
        this.vectors = new Vector3D[3];
        for(int i = 0; i < 3; i++) this.vectors[i] = new Vector3D();
    }

    public Matrix3D(AbstractMatrix matrix) {
        if (matrix.getClass() != this.getClass()) throw new WrongDimensionMatrixException();

        this.vectors = new Vector3D[3];
        for(int i = 0; i < 3 ; i++)
            this.vectors[i] = new Vector3D(matrix.vectors[i]);
    }
}
