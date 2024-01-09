package com.cgvsu.render_engine;
import com.cgvsu.math.Matrix.Matrixes.*;
import com.cgvsu.math.Points.Point3D;
import com.cgvsu.math.Vector.VectorFactory;
import com.cgvsu.math.Vector.Vectors.*;

public class GraphicConveyor {

    public static Matrix4D rotateScaleTranslate() {
        float[] matrix = new float[]{
                1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1};
        return new Matrix4D(matrix);
    }

    public static Matrix4D lookAt(Vector3D eye, Vector3D target) {
        return lookAt(eye, target, new Vector3D(0F, 1.0F, 0F));
    }

    public static Matrix4D lookAt(Vector3D eye, Vector3D target, Vector3D up) {
        Vector3D resultZ = (Vector3D) VectorFactory.createSubtractedVector(target, eye);
        Vector3D resultX = VectorFactory.createVectorProduct(up, resultZ);
        Vector3D resultY = VectorFactory.createVectorProduct(resultZ, resultX);

        resultX.normalize();
        resultY.normalize();
        resultZ.normalize();

        return new Matrix4D(
                resultX.get(0), resultX.get(1), resultX.get(2), -resultX.getScalarMultiplication(eye),
                resultY.get(0), resultY.get(1), resultY.get(2), -resultY.getScalarMultiplication(eye),
                resultZ.get(0), resultZ.get(1), resultZ.get(2), -resultZ.getScalarMultiplication(eye),
                0, 0, 0, 1
        );
    }

    public static Matrix4D perspective(
            final float fov,
            final float aspectRatio,
            final float nearPlane,
            final float farPlane) {
        float tangentMinusOnDegree = (float) (1.0F / (Math.tan(fov * 0.5F)));

        return new Matrix4D(
                tangentMinusOnDegree / aspectRatio, 0, 0, 0,
                0, tangentMinusOnDegree, 0, 0,
                0, 0, (farPlane + nearPlane) / (farPlane - nearPlane), (2 * farPlane * nearPlane) / (nearPlane - farPlane),
                0, 0, 1, 0
        );
    }

    public static Vector3D multiplyPVMonVector(final Matrix4D matrix, final Vector3D vertex) {
        final float x = (vertex.get(0) * matrix.getElement(0,0)) + (vertex.get(1) * matrix.getElement(1,0)) + (vertex.get(2) * matrix.getElement(2,0)) + matrix.getElement(3,0);
        final float y = (vertex.get(0) * matrix.getElement(0,1)) + (vertex.get(1) * matrix.getElement(1,1)) + (vertex.get(2) * matrix.getElement(2,1)) + matrix.getElement(3,1);
        final float z = (vertex.get(0) * matrix.getElement(0,2)) + (vertex.get(1) * matrix.getElement(1,2)) + (vertex.get(2) * matrix.getElement(2,2)) + matrix.getElement(3,2);
        final float w = (vertex.get(0) * matrix.getElement(0,3)) + (vertex.get(1) * matrix.getElement(1,3)) + (vertex.get(2) * matrix.getElement(2,3)) + matrix.getElement(3,3);
        return new Vector3D(x / w, y / w, z / w);
    }

    public static Point3D vertexToPoint(final Vector3D vertex, final int width, final int height) {
        return new Point3D((int) (vertex.get(0) * width + width / 2.0F), (int) (-vertex.get(1) * height + height / 2.0F), vertex.get(2));
    }
}
