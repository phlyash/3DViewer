package com.cgvsu.math.Vector;


import com.cgvsu.math.Matrix.MatrixUtils;
import com.cgvsu.math.Matrix.Matrixes.AbstractMatrix;
import com.cgvsu.math.Vector.Vectors.AbstractVector;
import com.cgvsu.math.Vector.Vectors.Vector3D;

import java.lang.reflect.InvocationTargetException;
import com.cgvsu.math.ReflectionUtils;

public final class VectorFactory {
    public static AbstractVector createClearVectorWithSameDimension(AbstractVector vector) {
        try {
            return (AbstractVector) ReflectionUtils.getConstructorOfObject(vector, float[].class).newInstance(new float[]{});
        }
        catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static AbstractVector createMultipliedOnNumberVector(AbstractVector vector, float number) {
        return ReflectionUtils.getResultOfBiFunction(vector, number, VectorUtils::multiply);
    }

    public static AbstractVector createDividedOnNumberVector(AbstractVector vector, float number) {
        return ReflectionUtils.getResultOfBiFunction(vector, number, VectorUtils::divide);
    }

    public static AbstractVector createSummedVector(AbstractVector vector1, AbstractVector vector2) {
        return ReflectionUtils.getResultOfBiFunction(vector1, vector2, VectorUtils::addition);
    }

    public static AbstractVector createSubtractedVector(AbstractVector vector1, AbstractVector vector2) {
        return ReflectionUtils.getResultOfBiFunction(vector1, vector2, VectorUtils::subtraction);
    }

    public static Vector3D createVectorProduct(Vector3D vector1, Vector3D vector2) {
        return new Vector3D(
                vector1.get(1) * vector2.get(2) - vector1.get(2) * vector2.get(1),
                vector1.get(2) * vector2.get(0) - vector1.get(0) * vector2.get(2),
                vector1.get(0) * vector2.get(1) - vector1.get(1) * vector2.get(0)
        );
    }

    public static AbstractVector createVectorByMultiplicationOnMatrix(AbstractMatrix matrix, AbstractVector vector) {
        return MatrixUtils.multiplicationOnVector(matrix, vector);
    }
}
