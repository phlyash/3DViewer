package com.cgvsu.model;
import com.cgvsu.math.Vector.Vectors.Vector2D;
import com.cgvsu.math.Vector.Vectors.Vector3D;

import java.util.*;

public class Model {

    public ArrayList<Vector3D> vertices;
    public ArrayList<Vector2D> textureVertices;
    public ArrayList<Vector3D> normals;
    public ArrayList<Polygon> polygons;
    private String name;

    public Model() {
        vertices = new ArrayList<>();
        textureVertices = new ArrayList<>();
        normals = new ArrayList<>();
        polygons = new ArrayList<>();
    }

    public Model(ArrayList<Vector3D> vertices, ArrayList<Vector2D> textureVertices, ArrayList<Vector3D> normals, ArrayList<Polygon> polygons) {
        this.vertices = vertices;
        this.textureVertices = textureVertices;
        this.normals = normals;
        this.polygons = polygons;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
