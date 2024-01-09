package com.cgvsu.math.Matrix.Matrixes;

import com.cgvsu.math.Matrix.MatrixFactory;
import com.cgvsu.math.Vector.VectorFactory;
import com.cgvsu.math.Vector.Vectors.AbstractVector;
import com.cgvsu.math.Vector.Vectors.Vector2D;
import com.cgvsu.math.Vector.Vectors.Vector3D;
import com.cgvsu.math.Vector.Vectors.Vector4D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MatrixTest {
    Matrix2D matrix2D;
    Matrix3D matrix3D;
    Matrix4D matrix4D;
    AbstractMatrix[] matrixes;
    float[][] givenValuesForTests;
    @BeforeEach
    void setUp() {
        givenValuesForTests = new float[][] {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        };

        matrix2D = new Matrix2D();
        matrix3D = new Matrix3D();
        matrix4D = new Matrix4D();
        matrixes = new AbstractMatrix[] {matrix2D, matrix3D, matrix4D};

        for(AbstractMatrix matrix: matrixes)
            for(int i = 0; i < matrix.getLength(); i++)
                for(int j = 0; j < matrix.getVectorLength(i); j++)
                    matrix.setElement(givenValuesForTests[i][j], i, j);
    }

    @Test
    @DisplayName("Addition of matrixes")
    void testAddition() {
        for(AbstractMatrix matrix: matrixes) {
            matrix.addMatrix(matrix);

            for(int i = 0; i < matrix.getLength(); i++)
                for(int j = 0; j < matrix.getVectorLength(i); j++)
                    Assertions.assertTrue(Math.abs(matrix.getElement(i, j) - 2 * givenValuesForTests[i][j]) < 1e-6);
        }
    }

    @Test
    @DisplayName("Subtracting matrixes")
    void testSubtraction() {
        for(AbstractMatrix matrix: matrixes) {
            matrix.subtractMatrix(matrix);

            for(int i = 0; i < matrix.getLength(); i++)
                for(int j = 0; j < matrix.getVectorLength(i); j++)
                    Assertions.assertEquals(matrix.getElement(i, j), 0);
        }
    }

    @Test
    @DisplayName("Matrix and number multiplication")
    void testMultiplicationOnNumber() {
        float multiplier = 2.5f;

        for(AbstractMatrix matrix: matrixes) {
            matrix.multiply(multiplier);

            for(int i = 0; i < matrix.getLength(); i++)
                for(int j = 0; j < matrix.getVectorLength(i); j++)
                    Assertions.assertTrue(Math.abs(matrix.getElement(i, j) - multiplier * givenValuesForTests[i][j]) < 1e-6);
        }
    }

    @Test
    @DisplayName("Dividing matrix on number")
    void testDivisionOnNumber() {
        float divider = 2.5f;
        for(AbstractMatrix matrix: matrixes) {
            matrix.divide(divider);

            for(int i = 0; i < matrix.getLength(); i++)
                for(int j = 0; j < matrix.getVectorLength(i); j++)
                    Assertions.assertTrue(Math.abs(matrix.getElement(i, j) -  givenValuesForTests[i][j] / divider) < 1e-6);
        }
    }

    @Test
    @DisplayName("Dividing matrix on zero")
    void testZeroDividing() {
        ArithmeticException exception = Assertions.assertThrows(ArithmeticException.class, () -> matrixes[0].divide(0));
        Assertions.assertTrue(exception.getMessage().contains("You cant divide on 0 (zero)"));
    }

    @Test
    @DisplayName("Multiplication of matrix and vector")
    void testMultiplyingOnVector() {
        for(AbstractMatrix matrix: matrixes) {
            AbstractVector vectorToMultiply = VectorFactory.createClearVectorWithSameDimension(matrix.getVector(0));
            for(int j = 0; j < matrix.getLength(); j++)
                vectorToMultiply.set(givenValuesForTests[0][j], j);
            AbstractVector multipliedVector = matrix.multiplyOnVector(vectorToMultiply);

            for(int i = 0; i < matrix.getLength(); i++) {
                float sum = 0;
                for (int j = 0; j < matrix.getVectorLength(i); j++)
                    sum += vectorToMultiply.get(j) * matrix.getElement(i, j);
                Assertions.assertTrue(Math.abs(multipliedVector.get(i) - sum) < 1e-6);
            }
        }
    }

    @Test
    @DisplayName("Transposing matrix")
    void testTransposingMatrix() {
        for(AbstractMatrix matrix: matrixes) {
            matrix.transpose();
            for(int i = 0; i < matrix.getLength(); i++)
                for (int j = 0; j < matrix.getVectorLength(i); j++) Assertions.assertTrue(Math.abs(givenValuesForTests[j][i] - matrix.getElement(i, j)) < 1e-6);
        }
    }

    @Test
    @DisplayName("Multiplying matrixes")
    void testMultiplyingMatrixes() {
        for(AbstractMatrix matrix: matrixes) {
            matrix.multiplyOnMatrix(matrix);
            for(int i = 0; i < matrix.getLength(); i++) {
                for (int j = 0; j < matrix.getVectorLength(i); j++) {
                    float sum = 0;
                    for(int k = 0; k < matrix.getVectorLength(i); k++)
                        sum += givenValuesForTests[i][k] * givenValuesForTests[k][j];
                    Assertions.assertTrue(Math.abs(matrix.getElement(i, j) - sum) < 1e-6);
                }
            }
        }
    }

    @Test
    @DisplayName("Minor test")
    void testMinor() {
        for(int i = 1; i < matrixes.length; i++) {
            for(int j = 0; j < matrixes[i].getLength(); j++)
                for(int k = 0; k < matrixes[i].getVectorLength(i); k++) {
                    AbstractMatrix minor = matrixes[i].getMinor(j, k);
                    for (int z = 0, xPassed = 0; z < minor.getLength(); z++) {
                        if (z == j) {
                            xPassed = 1;
                            continue;
                        }
                        for (int q = 0, yPassed = 0; q < minor.getVectorLength(z); q++) {
                            if (q == k) {
                                yPassed = 1;
                                continue;
                            }
                            Assertions.assertTrue(Math.abs(minor.getElement(z, q) - givenValuesForTests[z + xPassed][q + yPassed]) < 1e-6);
                        }
                    }
                }
        }
    }

    @Test
    @DisplayName("Determinant test for 3D and 4D matrixes")
    void testDeterminant() {
        for(AbstractMatrix matrix: matrixes) {
            float determinant = matrix.getDeterminant();
            float exceptedDeterminant = matrix instanceof Matrix2D ? -4 : 0;

            Assertions.assertTrue(Math.abs(determinant - exceptedDeterminant) < 1e-6);
        }
    }

    @Test
    @DisplayName("Reverse 2D matrix test")
    void testReversingMatrix2D() {
        Matrix2D matrix = new Matrix2D(new Vector2D(1, 2), new Vector2D(3, 4));
        Matrix2D matrixToReverse = new Matrix2D(new Vector2D(1, 2), new Vector2D(3, 4));
        matrixToReverse.reverseMatrix();
        matrix.multiplyOnMatrix(matrixToReverse);

        AbstractMatrix identityMatrix = MatrixFactory.createIdentityMatrix(2);
        for(int i = 0; i < matrix.getLength(); i++)
            for (int j = 0; j < matrix.getVectorLength(i); j++)
                Assertions.assertTrue(Math.abs(matrix.getElement(i, j) - identityMatrix.getElement(i, j)) < 1e-6);
    }

    @Test
    @DisplayName("Reverse 3D matrix test")
    void testReversingMatrix3D() {
        Matrix3D matrix = new Matrix3D(new Vector3D(2, 6, 5), new Vector3D(5, 3, -2), new Vector3D(7, 4, -3));
        Matrix3D matrixToReverse = new Matrix3D(new Vector3D(2, 6, 5), new Vector3D(5, 3, -2), new Vector3D(7, 4, -3));
        matrixToReverse.reverseMatrix();
        matrix.multiplyOnMatrix(matrixToReverse);

        AbstractMatrix identityMatrix = MatrixFactory.createIdentityMatrix(3);
        for(int i = 0; i < matrix.getLength(); i++)
            for (int j = 0; j < matrix.getVectorLength(i); j++)
                Assertions.assertTrue(Math.abs(matrix.getElement(i, j) - identityMatrix.getElement(i, j)) < 1e-6);
    }

    @Test
    @DisplayName("Reverse 4D matrix test")
    void testReversingMatrix4D() {
        Matrix4D matrix = new Matrix4D(new Vector4D(2, 6, 5, 1), new Vector4D(5, 3, -2, 1), new Vector4D(7, 4, -3, 1), new Vector4D(1, 1, 1, 1));
        Matrix4D matrixToReverse = new Matrix4D(new Vector4D(2, 6, 5, 1), new Vector4D(5, 3, -2, 1), new Vector4D(7, 4, -3, 1), new Vector4D(1, 1, 1, 1));
        matrixToReverse.reverseMatrix();
        matrix.multiplyOnMatrix(matrixToReverse);

        AbstractMatrix identityMatrix = MatrixFactory.createIdentityMatrix(4);
        for(int i = 0; i < matrix.getLength(); i++)
            for (int j = 0; j < matrix.getVectorLength(i); j++)
                Assertions.assertTrue(Math.abs(matrix.getElement(i, j) - identityMatrix.getElement(i, j)) < 1e-5);
    }
}
