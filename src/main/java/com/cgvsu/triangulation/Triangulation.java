package com.cgvsu.triangulation;

import com.cgvsu.math.Vector.VectorFactory;
import com.cgvsu.math.Vector.Vectors.Vector3D;
import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;

import java.util.*;

public class Triangulation {
    public static List<Polygon> triangulation(Polygon polygon) {
        ArrayList<Polygon> triangularPolygons = new ArrayList<>();

        ArrayList<Integer> vertexIndices = polygon.getVertexIndices();
        int quantityVertexes = vertexIndices.size();

        ArrayList<Integer> textureVertexIndices = polygon.getTextureVertexIndices();
        checkForCorrectListSize(textureVertexIndices, quantityVertexes, "текстурных координат");

        ArrayList<Integer> normalIndices = polygon.getNormalIndices();
        checkForCorrectListSize(normalIndices, quantityVertexes, "нормалей");


        for (int index = 1; index < vertexIndices.size() - 1; index++) {
            ArrayList<Integer> threeVertexIndices = getIndicesListForCurrentPolygon(vertexIndices, index);
            ArrayList<Integer> threeTextureVertexIndices = getIndicesListForCurrentPolygon(textureVertexIndices, index);
            ArrayList<Integer> threeNormalIndices = getIndicesListForCurrentPolygon(normalIndices, index);

            Polygon triangularPolygon = new Polygon();
            triangularPolygon.setVertexIndices(threeVertexIndices);
            triangularPolygon.setTextureVertexIndices(threeTextureVertexIndices);
            triangularPolygon.setNormalIndices(threeNormalIndices);

            triangularPolygons.add(triangularPolygon);
        }

        return triangularPolygons;
    }

    private static void checkForCorrectListSize(List<Integer> list, int expectedSize, String listName) {
        if (list.size() != 0 && list.size() != expectedSize) {
            throw new IllegalArgumentException("Некорректное количество " + listName + " в полигоне");
        }
    }

    private static ArrayList<Integer> getIndicesListForCurrentPolygon(List<Integer> list, int indexSecondVertex) {
        ArrayList<Integer> indices = new ArrayList<>();

        if (list.size() != 0) {
            indices.add(list.get(0));
            indices.add(list.get(indexSecondVertex));
            indices.add(list.get(indexSecondVertex + 1));
        }

        return indices;
    }

    public static void recalculateNormals(Model model) {
        model.normals.clear();

        for (int i = 0; i < model.vertices.size(); i++) {
            model.normals.add(calculateNormalForVertexInModel(model, i));
        }
    }

    protected static Vector3D calculateNormalForPolygon(final Polygon polygon, final Model model){

        List<Integer> vertexIndices = polygon.getVertexIndices();
        int verticesCount = vertexIndices.size();

        Vector3D point1 = model.vertices.get(vertexIndices.get(0)), point2 = model.vertices.get(vertexIndices.get(1)),
                point3 = model.vertices.get(vertexIndices.get(verticesCount - 1));
        Vector3D vector1 = (Vector3D) VectorFactory.createSubtractedVector(point1, point2);
        Vector3D vector2 = (Vector3D) VectorFactory.createSubtractedVector(point1, point3);

        return VectorFactory.createVectorProduct(vector1, vector2);
    }

    protected static Vector3D calculateNormalForVertexInModel(final Model model, final int vertexIndex) {
        List<Vector3D> saved = new ArrayList<>();

        for (Polygon polygon : model.polygons) {
            if (polygon.getVertexIndices().contains(vertexIndex)) {
                Vector3D polygonNormal = calculateNormalForPolygon(polygon, model);
                if (polygonNormal.getLength() > 0) {
                    saved.add(polygonNormal);
                }
            }
        }

        if (saved.isEmpty()) {
            return new Vector3D();
        }

        Vector3D normals = new Vector3D();
        for(Vector3D vector3D : saved) {
            normals.addVector(vector3D);
        }

        normals.divide(saved.size());

        return normals;
    }

    public static Model getTriangulatedModel(Model model) {
        Triangulation.recalculateNormals(model);
        Model triangulatedModel = new Model();
        triangulatedModel.textureVertices = model.textureVertices;
        triangulatedModel.normals = model.normals;
        triangulatedModel.vertices = model.vertices;
        for(int i = 0; i < model.polygons.size(); i++) {
            if(model.polygons.get(i).getVertexIndices().size() == 3) {
                triangulatedModel.polygons.add(model.polygons.get(i));
            }
            else {
                triangulatedModel.polygons.addAll(Triangulation.triangulation(model.polygons.get(i)));
            }
        }
        return triangulatedModel;
    }
}