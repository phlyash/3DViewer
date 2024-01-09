package com.cgvsu.math.Vector;

import com.cgvsu.math.Vector.Vectors.AbstractVector;
import com.cgvsu.math.Vector.Vectors.Vector2D;
import com.cgvsu.math.Vector.Vectors.Vector3D;
import com.cgvsu.math.Vector.Vectors.Vector4D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

// main purpose of this test is testing ReflectionUtils class work
public class VectorFactoryTest {
    @Test
    @DisplayName("Creating Vector with given dimension")
    void testCreatingVectorWithSameDimension() {
        Vector2D vector2D = new Vector2D(1, 2);
        Vector3D vector3D = new Vector3D(1, 2, 3);
        Vector4D vector4D = new Vector4D(1, 2, 3, 4);

        AbstractVector[] vectors = {vector2D, vector3D, vector4D};

        for(AbstractVector vector: vectors) {
            AbstractVector vectorWithSameDimensions = VectorFactory.createClearVectorWithSameDimension(vector);
            Assertions.assertEquals(vector.getLength(), vectorWithSameDimensions.getLength());
        }
    }

    @Test
    @DisplayName("Creating vector multiplied on number")
    void testCreatingVectorMultipliedOnNumber() {
        Vector2D vector2D = new Vector2D(1, 2);
        Vector3D vector3D = new Vector3D(1, 2, 3);
        Vector4D vector4D = new Vector4D(1, 2, 3, 4);

        AbstractVector[] vectors = {vector2D, vector3D, vector4D};
        float numberToMultiply = 2.5f;

        for(AbstractVector vector: vectors) {
            AbstractVector multipliedVector = VectorFactory.createMultipliedOnNumberVector(vector, numberToMultiply);
            for(int i = 0; i < vector.getLength(); i++)
                Assertions.assertTrue(Math.abs(multipliedVector.get(i) - numberToMultiply * vector.get(i)) < 1e-6);
        }
    }
}
