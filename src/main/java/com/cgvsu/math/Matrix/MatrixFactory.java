package com.cgvsu.math.Matrix;

import com.cgvsu.math.Exceptions.MatrixExceptions.ReversingMatrixException;
import com.cgvsu.math.Exceptions.MatrixExceptions.WrongDimensionMatrixException;
import com.cgvsu.math.Exceptions.WrongAmountOfAxesGivenException;
import com.cgvsu.math.Matrix.Matrixes.AbstractMatrix;
import com.cgvsu.math.Matrix.Matrixes.Matrix2D;
import com.cgvsu.math.Matrix.Matrixes.Matrix3D;
import com.cgvsu.math.Matrix.Matrixes.Matrix4D;
import com.cgvsu.math.ReflectionUtils;

public class MatrixFactory {
    public static AbstractMatrix createZeroMatrix(int dimensions) {
        switch (dimensions) {
            case 2 -> {
                return new Matrix2D();
            }
            case 3 -> {
                return new Matrix3D();
            }
            case 4 -> {
                return new Matrix4D();
            }
            default -> throw new WrongAmountOfAxesGivenException();
        }
    }

    public static AbstractMatrix createIdentityMatrix(int dimensions) {
        AbstractMatrix matrix = createZeroMatrix(dimensions);

        for(int i = 0; i < matrix.getLength(); i++)
            matrix.getVector(i).set(1, i);

        return matrix;
    }

    public static AbstractMatrix createMatrixByMultiplication(AbstractMatrix matrix1, AbstractMatrix matrix2) {
        if (matrix1.getLength() != matrix2.getVectorLength(0)) throw new WrongDimensionMatrixException();

        AbstractMatrix newMatrix = createZeroMatrix(matrix1.getLength());

        for(int i = 0; i < matrix1.getVectorLength(0); i++)
            for(int j = 0; j < matrix2.getLength(); j++)
                for(int k = 0; k < matrix1.getLength(); k++)
                    newMatrix.getVector(i).set(newMatrix.getVector(i).get(j) + matrix1.getVector(i).get(k) * matrix2.getVector(k).get(j), j);

        return newMatrix;
    }

    public static AbstractMatrix createMatrixByAddition(AbstractMatrix matrix1, AbstractMatrix matrix2) {
        if (matrix1.getLength() != matrix2.getVectorLength(0) || matrix1.getVectorLength(0) != matrix2.getLength())
            throw new WrongDimensionMatrixException();

        return ReflectionUtils.getResultOfBiFunction(matrix1, matrix2, MatrixUtils::addition);
    }

    public static AbstractMatrix createMatrixBySubtraction(AbstractMatrix matrix1, AbstractMatrix matrix2) {
        if (matrix1.getLength() != matrix2.getVectorLength(0) || matrix1.getVectorLength(0) != matrix2.getLength())
            throw new WrongDimensionMatrixException();

        return ReflectionUtils.getResultOfBiFunction(matrix1, matrix2, MatrixUtils::subtraction);
    }

    public static AbstractMatrix createMatrixByMultiplicationOnNumber(AbstractMatrix matrix, float number) {
        return ReflectionUtils.getResultOfBiFunction(matrix, number, MatrixUtils::multiply);
    }

    public static AbstractMatrix createMatrixByDividingOnNumber(AbstractMatrix matrix, float number) {
        return ReflectionUtils.getResultOfBiFunction(matrix, number, MatrixUtils::divide);
    }

    public static AbstractMatrix createTransposedMatrix(AbstractMatrix matrix) {
        return ReflectionUtils.getResultOfFunction(matrix, MatrixUtils::transpose);
    }

    public static AbstractMatrix createReversedMatrix(AbstractMatrix matrix) {
        AbstractMatrix cofactorsMatrix = MatrixFactory.createZeroMatrix(matrix.getLength());

        float determinant = matrix.getDeterminant();
        if (Math.abs(determinant) < 1e-6) throw new ReversingMatrixException();

        for(int i = 0; i < matrix.getLength(); i++)
            for(int j = 0; j < matrix.getVectorLength(i); j++)
                cofactorsMatrix.setElement(MatrixUtils.calculateCofactor(matrix, j, i), j, i);

        // we don`t need to transpose matrix `cause we found already transposed matrix
        cofactorsMatrix.divide(determinant);

        return cofactorsMatrix;
    }
}
