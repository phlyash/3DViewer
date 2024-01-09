package com.cgvsu.math.Vector.Vectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class VectorTest {
    Vector2D vector2D;
    Vector3D vector3D;
    Vector4D vector4D;
    AbstractVector[] vectors;
    float[] givenValuesForTests;

    @BeforeEach
    void setUp() {
        givenValuesForTests = new float[] {1, 2, 3, 4};
        vector2D = new Vector2D();
        vector3D = new Vector3D();
        vector4D = new Vector4D();

        vectors = new AbstractVector[]{vector2D, vector3D, vector4D};
        for(AbstractVector vector: vectors)
            for(int i = 0; i < vector.getLength(); i++) vector.set(givenValuesForTests[i], i);
    }

    @Test
    @DisplayName("Vector on number multiplication")
    void testMultiplicationOnNumber() {
        for(AbstractVector vector: vectors) vector.multiply(2.5f);

        for(AbstractVector vector: vectors) {
            for(int i = 0; i < vector.getLength(); i++) {
                Assertions.assertTrue(Math.abs(vector.get(i) - givenValuesForTests[i] * 2.5f) < 1e-6);
            }
        }
    }

    @Test
    @DisplayName("Vector on number dividing")
    void testDividingOnNumber() {
        float divider = 2.5f;
        for(AbstractVector vector: vectors) vector.divide(divider);

        for(AbstractVector vector: vectors)
            for(int i = 0; i < vector.getLength(); i++)
                Assertions.assertTrue(Math.abs(vector.get(i) - givenValuesForTests[i] / divider) < 1e-6);
    }

    @Test
    @DisplayName("Vector on zero dividing")
    void testZeroDividing() {
        ArithmeticException exception = Assertions.assertThrows(ArithmeticException.class, () -> vectors[0].divide(0));
        Assertions.assertTrue(exception.getMessage().contains("You cant divide on 0 (zero)"));
    }

    @Test
    @DisplayName("Adding vector to vector")
    void testAdditionOfVectors() {
        for(AbstractVector vector: vectors) {
            vector.addVector(vector);

            for(int i = 0; i < vector.getLength(); i++)
                Assertions.assertEquals(vector.get(i), givenValuesForTests[i] * 2);
        }
    }

    @Test
    @DisplayName("Subtract vector from vector")
    void testSubtraction() {
        for(AbstractVector vector: vectors) {
            vector.subtractVector(vector);

            for(int i = 0; i < vector.getLength(); i++)
                Assertions.assertEquals(vector.get(i), 0);
        }
    }

    @Test
    @DisplayName("Calculating length of vector")
    void testCalculatingLength() {
        for(AbstractVector vector: vectors) {
            float exceptedLength = 0;
            for(int i = 0; i < vector.getLength(); i++) exceptedLength += vector.get(i) * vector.get(i);
            exceptedLength = (float) Math.sqrt(exceptedLength);

            Assertions.assertTrue(Math.abs(vector.calculateLength() - exceptedLength) < 1e-6);
        }
    }

    @Test
    @DisplayName("Normalizing vector")
    void testNormalizingVectors() {
        for(AbstractVector vector: vectors) {
            float exceptedLength = vector.calculateLength();

            vector.normalize();
            for(int i = 0; i < vector.getLength(); i++)
                Assertions.assertTrue(Math.abs(vector.get(i) - givenValuesForTests[i] / exceptedLength) < 1e-6);
        }
    }

    @Test
    @DisplayName("Scalar multiplication of vectors")
    void testScalarMultiplication() {
        for(AbstractVector vector: vectors) {
            float exceptedScalarMultiplication = 0;
            for(int i = 0; i < vector.getLength(); i++) exceptedScalarMultiplication += givenValuesForTests[i] * givenValuesForTests[i];

            Assertions.assertTrue(Math.abs(vector.getScalarMultiplication(vector) - exceptedScalarMultiplication) < 1e-6);
        }
    }

    @Test
    @DisplayName("3D vector product")
    void testVector3DProduct() {
        vector3D.product(vector3D);
        Assertions.assertEquals(vector3D, new Vector3D());
    }
}
