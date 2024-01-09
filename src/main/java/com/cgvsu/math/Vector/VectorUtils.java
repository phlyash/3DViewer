package com.cgvsu.math.Vector;

import com.cgvsu.math.Exceptions.VectorExceptions.WrongDimensionVectorException;
import com.cgvsu.math.Vector.Vectors.AbstractVector;
import com.cgvsu.math.Vector.Vectors.Vector3D;

public final class VectorUtils {
    public static void multiply(AbstractVector vector, float number) {
        for(int i = 0; i < vector.getLength(); i++) vector.set(vector.get(i) * number, i);
    }

    public static void divide(AbstractVector vector, float number) {
        if (Math.abs(number) < 1e-9) throw new ArithmeticException("You cant divide on 0 (zero)");
        for(int i = 0; i < vector.getLength(); i++) vector.set(vector.get(i) / number, i);
    }

    public static float getLength(AbstractVector vector) {
        float squareSumOfAxes = 0;
        for(int i = 0; i < vector.getLength(); i++) squareSumOfAxes += vector.get(i) * vector.get(i);
        return (float) Math.sqrt(squareSumOfAxes);
    }

    public static void addition(AbstractVector vectorToBeChanged, AbstractVector vectorChanger) {
        checkVectorCompatibility(vectorToBeChanged, vectorChanger);
        for(int i = 0; i < vectorToBeChanged.getLength(); i++) vectorToBeChanged.set(vectorToBeChanged.get(i) + vectorChanger.get(i), i);
    }

    public static void subtraction(AbstractVector vectorToBeChanged, AbstractVector vectorChanger) {
        checkVectorCompatibility(vectorToBeChanged, vectorChanger);
        for(int i = 0; i < vectorToBeChanged.getLength(); i++) vectorToBeChanged.set(vectorToBeChanged.get(i) - vectorChanger.get(i), i);
    }

    public static float getScalar(AbstractVector vector, AbstractVector vectorToMultiplyBy) {
        checkVectorCompatibility(vector, vectorToMultiplyBy);
        float sum = 0;
        for(int i = 0; i < vector.getLength(); i++) sum += vector.get(i) * vectorToMultiplyBy.get(i);
        return sum;
    }

    public static void vectorProduct(Vector3D vectorToBeChanged, Vector3D vectorChanger) {
        for(int i = 0; i < vectorToBeChanged.getLength(); i++) vectorToBeChanged.set
                (vectorToBeChanged.get((i + 1) % 3) * vectorChanger.get((i + 2) % 3) - vectorToBeChanged.get((i + 2) % 3) * vectorChanger.get((i + 1) % 3), i);
    }

    public static void vectorComponentWiseMultiplication(AbstractVector vectorToBeChanged, AbstractVector vectorChanger) {
        checkVectorCompatibility(vectorChanger, vectorToBeChanged);

        for(int i = 0; i < vectorToBeChanged.getLength(); i++) {
            vectorToBeChanged.set(vectorToBeChanged.get(i) * vectorChanger.get(i), i);
        }
    }

    private static void checkVectorCompatibility(AbstractVector vector1, AbstractVector vector2) {
        if (vector1.getClass() != vector2.getClass()) throw new WrongDimensionVectorException();
    }
}
