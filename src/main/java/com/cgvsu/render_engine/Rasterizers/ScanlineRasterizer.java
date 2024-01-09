package com.cgvsu.render_engine.Rasterizers;

import com.cgvsu.math.Algorithms.Barycentric;
import com.cgvsu.math.Points.Point3D;
import com.cgvsu.math.Points.PolygonPoint;

import java.util.List;

public class ScanlineRasterizer {
    public static void rasterize(List<PolygonPoint> triangle) {
        sortTrianglePointsByY(triangle);

        if (triangle.get(2).getCoords().getY() - triangle.get(1).getCoords().getY() == 0)
            fillBottomTriangle(triangle.get(0), triangle.get(1), triangle.get(2));
        else if (triangle.get(1).getCoords().getY() - triangle.get(0).getCoords().getY() == 0)
            fillTopTriangle(triangle.get(2), triangle.get(1), triangle.get(0));
        else {
            int middlePointX = triangle.get(0).getCoords().getX() - (triangle.get(0).getCoords().getX() - triangle.get(2).getCoords().getX()) *
                    (triangle.get(0).getCoords().getY() - triangle.get(1).getCoords().getY()) / (triangle.get(0).getCoords().getY() - triangle.get(2).getCoords().getY());
            int middlePointY = triangle.get(1).getCoords().getY();

            float[] barycentric = Barycentric.calculateCoeffs(triangle.get(0).getCoords(), triangle.get(1).getCoords(), triangle.get(2).getCoords(),
                    middlePointX, triangle.get(1).getCoords().getY());
            PolygonPoint middlePoint = new PolygonPoint(
                    new Point3D(middlePointX,
                        middlePointY,
                        Barycentric.calculateZ(
                                triangle.get(0).getCoords(), triangle.get(1).getCoords(), triangle.get(2).getCoords(),
                                barycentric
                        )
                    ),
                    Barycentric.calculateTexture(triangle.get(0), triangle.get(1), triangle.get(2), barycentric),
                    Barycentric.calculateNormal(triangle.get(0), triangle.get(1), triangle.get(2), barycentric),
                    Barycentric.calculateWorldCoords(triangle.get(0), triangle.get(1), triangle.get(2), barycentric)
            );

            fillTopTriangle(triangle.get(2), triangle.get(1), middlePoint);
            fillBottomTriangle(triangle.get(0), triangle.get(1), middlePoint);
        }
    }

    private static void sortTrianglePointsByY(List<PolygonPoint> triangle) {
        PolygonPoint swap;
        if (triangle.get(0).getCoords().getY() >= triangle.get(2).getCoords().getY() && triangle.get(0).getCoords().getY() >= triangle.get(1).getCoords().getY()) {
            swap = triangle.get(2);
            triangle.set(2, triangle.get(0));
            triangle.set(0, swap);
        }
        else if (triangle.get(1).getCoords().getY() >= triangle.get(2).getCoords().getY() && triangle.get(1).getCoords().getY() >= triangle.get(0).getCoords().getY()) {
            swap = triangle.get(1);
            triangle.set(1, triangle.get(2));
            triangle.set(2, swap);
        }

        if (triangle.get(0).getCoords().getY() > triangle.get(1).getCoords().getY()) {
            swap = triangle.get(0);
            triangle.set(0, triangle.get(1));
            triangle.set(1, swap);
        }
    }
    private static void fillBottomTriangle(PolygonPoint B, PolygonPoint C, PolygonPoint D) {
        float tgDB = (float) (D.getCoords().getX() - B.getCoords().getX()) / (D.getCoords().getY() - B.getCoords().getY());
        float tgCB = (float) (C.getCoords().getX() - B.getCoords().getX()) / (C.getCoords().getY() - B.getCoords().getY());

        float DX = D.getCoords().getX();
        float CX = C.getCoords().getX();

        for(int y = C.getCoords().getY(); y >= B.getCoords().getY(); y--) {
            BaseRasterizer.drawParallel(B, C, D, (int) DX, (int) CX, y);
            DX -= tgDB;
            CX -= tgCB;
        }
    }

    private static void fillTopTriangle(PolygonPoint A, PolygonPoint C, PolygonPoint D) {
        float tgDA = (float) (D.getCoords().getX() - A.getCoords().getX()) / (D.getCoords().getY() - A.getCoords().getY());
        float tgCA = (float) (C.getCoords().getX() - A.getCoords().getX()) / (C.getCoords().getY() - A.getCoords().getY());

        float CX = C.getCoords().getX();
        float DX = D.getCoords().getX();

        for(int y = C.getCoords().getY(); y <= A.getCoords().getY(); y++) {
            BaseRasterizer.drawParallel(A, C, D, (int) CX, (int) DX, y);
            CX += tgCA;
            DX += tgDA;
        }
    }
}
