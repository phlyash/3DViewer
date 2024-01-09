package com.cgvsu.math.Matrix;

import com.cgvsu.math.Matrix.Matrixes.AbstractMatrix;
import com.cgvsu.math.Matrix.Matrixes.Matrix2D;
import com.cgvsu.math.Matrix.Matrixes.Matrix3D;
import com.cgvsu.math.Matrix.Matrixes.Matrix4D;
import com.cgvsu.math.Vector.Vectors.Vector2D;
import com.cgvsu.math.Vector.Vectors.Vector3D;
import com.cgvsu.math.Vector.Vectors.Vector4D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

// this class testes work of ReflectionUtils class with methods from MatrixUtils,
// so we only need to test special functions that is not tested in MatrixTest class
public class MatrixFactoryTest {
    @Test
    @DisplayName("Creating identity matrix")
    void testCreateIdentityMatrix() {
        AbstractMatrix matrix2D = MatrixFactory.createIdentityMatrix(2);
        AbstractMatrix matrix3D = MatrixFactory.createIdentityMatrix(3);
        AbstractMatrix matrix4D = MatrixFactory.createIdentityMatrix(4);

        AbstractMatrix[] matrixes = {matrix2D, matrix3D, matrix4D};

        for(AbstractMatrix matrix: matrixes)
            for(int i = 0; i < matrix.getLength(); i++)
                for(int j = 0; j < matrix.getVectorLength(i); j++)
                    if (i == j) Assertions.assertTrue(Math.abs(matrix.getElement(i, j) - 1) < 1e-6);
                    else Assertions.assertTrue(Math.abs(matrix.getElement(i, j)) < 1e-6);
    }

    @Test
    @DisplayName("Creating zero matrix")
    void testCreateZeroMatrix() {
        AbstractMatrix matrix2D = MatrixFactory.createZeroMatrix(2);
        AbstractMatrix matrix3D = MatrixFactory.createZeroMatrix(3);
        AbstractMatrix matrix4D = MatrixFactory.createZeroMatrix(4);

        AbstractMatrix[] matrixes = {matrix2D, matrix3D, matrix4D};

        for(AbstractMatrix matrix: matrixes)
            for(int i = 0; i < matrix.getLength(); i++)
                for(int j = 0; j < matrix.getVectorLength(i); j++)
                    Assertions.assertTrue(Math.abs(matrix.getElement(i, j)) < 1e-6);
    }

    @Test
    @DisplayName("Creating matrixes by addition")
    void testCreateMatrixByAddition() {
        AbstractMatrix matrix2D = new Matrix2D(new Vector2D(1, 2), new Vector2D(3, 4));
        AbstractMatrix matrix3D = new Matrix3D(new Vector3D(1, 2, 3), new Vector3D(4, 5, 6), new Vector3D(7, 8, 9));
        AbstractMatrix matrix4D = new Matrix4D(new Vector4D(1, 2, 3, 4), new Vector4D(5, 6, 7, 8),
                new Vector4D(9, 10, 11, 12), new Vector4D(13, 14, 15, 16));

        AbstractMatrix[] matrixes = {matrix2D, matrix3D, matrix4D};

        for(AbstractMatrix matrix: matrixes) {
            AbstractMatrix additionMatrix = MatrixFactory.createMatrixByAddition(matrix, matrix);
            for(int i = 0; i < matrix.getLength(); i++)
                for(int j = 0; j < matrix.getVectorLength(i); j++)
                    Assertions.assertTrue(Math.abs(additionMatrix.getElement(i, j) - 2 * matrix.getElement(i, j)) < 1e-6);
        }
    }

    @Test
    @DisplayName("Creating transposed matrix")
    void testCreateTransposedMatrix() {
        AbstractMatrix matrix2D = new Matrix2D(new Vector2D(1, 2), new Vector2D(3, 4));
        AbstractMatrix matrix3D = new Matrix3D(new Vector3D(1, 2, 3), new Vector3D(4, 5, 6), new Vector3D(7, 8, 9));
        AbstractMatrix matrix4D = new Matrix4D(new Vector4D(1, 2, 3, 4), new Vector4D(5, 6, 7, 8),
                new Vector4D(9, 10, 11, 12), new Vector4D(13, 14, 15, 16));

        AbstractMatrix[] matrixes = {matrix2D, matrix3D, matrix4D};

        for(AbstractMatrix matrix: matrixes) {
            AbstractMatrix transposedMatrix = MatrixFactory.createTransposedMatrix(matrix);
            for(int i = 0; i < matrix.getLength(); i++)
                for(int j = 0; j < matrix.getVectorLength(i); j++)
                    Assertions.assertTrue(Math.abs(transposedMatrix.getElement(i, j) - matrix.getElement(j, i)) < 1e-6);
        }
    }
}

