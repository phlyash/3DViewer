package com.cgvsu.render_engine.Rasterizers;

import com.cgvsu.math.Points.Point3D;
import com.cgvsu.math.Points.PolygonPoint;
import javafx.scene.paint.Color;

import java.util.List;

public class MeshRasterizer {
    public static void rasterize(List<PolygonPoint> triangle, Color color) {
        for(int i = 0; i < triangle.size(); i++) {
            Point3D p1 = triangle.get(i).getCoords();
            Point3D p2 = triangle.get((i + 1) % triangle.size()).getCoords();
            Point3D p3 = triangle.get((i + 2) % triangle.size()).getCoords();
            BaseRasterizer.drawLine(p1, p2, p3, color);
        }
    }

}
