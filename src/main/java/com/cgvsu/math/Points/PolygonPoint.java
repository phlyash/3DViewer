package com.cgvsu.math.Points;

import com.cgvsu.math.Vector.Vectors.Vector2D;
import com.cgvsu.math.Vector.Vectors.Vector3D;

public class PolygonPoint {
    private Point3D coords;
    private Vector2D texture;
    private Vector3D normal;
    private Vector3D worldCoords;

    public PolygonPoint(Point3D coords, Vector2D texture, Vector3D normal, Vector3D worldCoords) {
        this.coords = coords;
        this.texture = texture;
        this.normal = normal;
        this.worldCoords = worldCoords;
    }

    public Point3D getCoords() {
        return coords;
    }

    public Vector2D getTexture() {
        return texture;
    }

    public Vector3D getNormal() {
        return normal;
    }

    public Vector3D getWorldCoords() {
        return worldCoords;
    }
}
