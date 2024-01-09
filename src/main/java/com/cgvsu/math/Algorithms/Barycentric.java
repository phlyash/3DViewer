package com.cgvsu.math.Algorithms;

import com.cgvsu.math.Points.Point3D;
import com.cgvsu.math.Points.PolygonPoint;
import com.cgvsu.math.Vector.Vectors.Vector2D;
import com.cgvsu.math.Vector.Vectors.Vector3D;

public class Barycentric {
    public static float[] calculateCoeffs(Point3D p1, Point3D p2, Point3D p3, int x, int y) {
//        float denominator = p1.getX() * (p2.getY() - p3.getY()) - p2.getX() * (p1.getY() - p3.getY()) + p3.getX() * (p1.getY() - p2.getY());
//        float alpha = (x * (p2.getY() - p3.getY()) - p2.getX() * (y - p3.getY()) + p3.getX() * (y - p2.getY())) / denominator;
//        float beta = -(x * (p1.getY() - p3.getY()) - p1.getX() * (y - p3.getY()) + p3.getX() * (y - p1.getY())) / denominator;
//        float gamma = 1 - alpha - beta;
//
//        if(p1.getY() == p2.getY() || p2.getY() == p3.getY()) {
//            gamma = (x * (p1.getY() - p2.getY()) - p1.getX() * (y - p2.getY()) + p2.getX() * (y - p1.getY())) / denominator;
//            beta = 1 - alpha - gamma;
//        }
//
//        return new float[] {alpha, beta, gamma};
        float alpha = (float) ((p2.getY() - p3.getY()) * (x - p3.getX()) + (p3.getX() - p2.getX()) * (y - p3.getY())) / ((p2.getY() - p3.getY()) * (p1.getX() - p3.getX()) + (p3.getX() - p2.getX()) * (p1.getY() - p3.getY()));
        float beta = (float) ((p3.getY() - p1.getY()) * (x - p3.getX()) + (p1.getX() - p3.getX()) * (y - p3.getY())) / ((p2.getY() - p3.getY()) * (p1.getX() - p3.getX()) + (p3.getX() - p2.getX()) * (p1.getY() - p3.getY()));
        float gamma = 1 - alpha - beta;
        return new float[]{alpha, beta, gamma};
    }

    public static float calculateZ(Point3D v1, Point3D v2, Point3D v3, int x, int y) {
        float[] barycentric = calculateCoeffs(v1, v2, v3, x, y);
        return barycentric[0] * v1.getZ() + barycentric[1] * v2.getZ() + barycentric[2] * v3.getZ();
    }

    public static float calculateZ(Point3D v1, Point3D v2, Point3D v3, float[] barycentric) {
        return barycentric[0] * v1.getZ() + barycentric[1] * v2.getZ() + barycentric[2] * v3.getZ();
    }

    public static Vector2D calculateTexture(PolygonPoint v1, PolygonPoint v2, PolygonPoint v3, float[] barycentric) {
        return new Vector2D(
                barycentric[0] * v1.getTexture().get(0) + barycentric[1] * v2.getTexture().get(0) + barycentric[2] * v3.getTexture().get(0),
                barycentric[0] * v1.getTexture().get(1) + barycentric[1] * v2.getTexture().get(1) + barycentric[2] * v3.getTexture().get(1)
        );
    }

    public static Vector3D calculateNormal(PolygonPoint v1, PolygonPoint v2, PolygonPoint v3, float[] barycentric) {
        return new Vector3D(
          barycentric[0] * v1.getNormal().get(0) + barycentric[1] * v2.getNormal().get(0) + barycentric[2] * v3.getNormal().get(0),
          barycentric[0] * v1.getNormal().get(1) + barycentric[1] * v2.getNormal().get(1) + barycentric[2] * v3.getNormal().get(1),
          barycentric[0] * v1.getNormal().get(2) + barycentric[1] * v2.getNormal().get(2) + barycentric[2] * v3.getNormal().get(2)
        );
    }

    public static Vector3D calculateWorldCoords(PolygonPoint v1, PolygonPoint v2, PolygonPoint v3, float[] barycentric) {
        return new Vector3D(
                barycentric[0] * v1.getWorldCoords().get(0) + barycentric[1] * v2.getWorldCoords().get(0) + barycentric[2] * v3.getWorldCoords().get(0),
                barycentric[0] * v1.getWorldCoords().get(1) + barycentric[1] * v2.getWorldCoords().get(1) + barycentric[2] * v3.getWorldCoords().get(1),
                barycentric[0] * v1.getWorldCoords().get(2) + barycentric[1] * v2.getWorldCoords().get(2) + barycentric[2] * v3.getWorldCoords().get(2)
        );
    }
}
