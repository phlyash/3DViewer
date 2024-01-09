package com.cgvsu.render_engine;

import java.util.ArrayList;

import com.cgvsu.Settings;
import com.cgvsu.math.Points.PolygonPoint;
import com.cgvsu.math.Vector.Vectors.*;
import com.cgvsu.math.Matrix.Matrixes.*;
import com.cgvsu.render_engine.Cameras.Camera;
import com.cgvsu.render_engine.Rasterizers.MeshRasterizer;
import com.cgvsu.render_engine.Rasterizers.ScanlineRasterizer;
import com.cgvsu.model.Model;
import javafx.scene.paint.Color;

import static com.cgvsu.render_engine.GraphicConveyor.*;

public class RenderEngine {

    public static void renderFrame(int width, int height)
    {
        Scene scene = Scene.getScene();

        for(int model = 0; model < scene.getModelsSize(); model++) {
            renderModel(scene.getModel(model), scene.getCurrentCamera(), width, height);
        }
    }

    private static void renderModel(Model model, Camera camera, int width, int height) {
        Matrix4D modelMatrix = rotateScaleTranslate();
        Matrix4D viewMatrix = camera.getViewMatrix();
        Matrix4D projectionMatrix = camera.getProjectionMatrix();

        Matrix4D modelViewProjectionMatrix = new Matrix4D(projectionMatrix);
        modelViewProjectionMatrix.multiplyOnMatrix(viewMatrix);
        modelViewProjectionMatrix.multiplyOnMatrix(modelMatrix);

        final int nPolygons = model.polygons.size();

        polygonLoop:
        for (int polygonInd = 0; polygonInd < nPolygons; ++polygonInd) {
            final int nVerticesInPolygon = model.polygons.get(polygonInd).getVertexIndices().size();

            ArrayList<PolygonPoint> resultPoints = new ArrayList<>();
            for (int vertexInPolygonInd = 0; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                Vector3D vertex = model.vertices.get(model.polygons.get(polygonInd).getVertexIndices().get(vertexInPolygonInd));

                Vector3D projectionNormalised = multiplyPVMonVector(modelViewProjectionMatrix, vertex);
                if (Math.abs(projectionNormalised.get(0)) > 1 || Math.abs(projectionNormalised.get(1)) > 1 || Math.abs(projectionNormalised.get(2)) > 1)
                    continue polygonLoop;

                Vector2D texture = model.textureVertices.get(model.polygons.get(polygonInd).getTextureVertexIndices().get(vertexInPolygonInd));
                Vector3D normal;
                // триангулятор работает неправильно
                // todo fix recalculating normals
                // допущение: вроде как эта рекалькуляцию по итогу имеет ссылки как на обычные вершины?
                if (model.polygons.get(polygonInd).getNormalIndices().isEmpty())
                    normal = model.normals.get(model.polygons.get(polygonInd).getVertexIndices().get(vertexInPolygonInd));
                else
                    normal = model.normals.get(model.polygons.get(polygonInd).getNormalIndices().get(vertexInPolygonInd));

                PolygonPoint resultPoint = new PolygonPoint(
                        vertexToPoint(multiplyPVMonVector(modelViewProjectionMatrix, vertex), width, height),
                        texture,
                        normal,
                        vertex
                );
                resultPoints.add(resultPoint);
            }

            ScanlineRasterizer.rasterize(resultPoints);
            if(Settings.getSettings().isDrawMesh()) MeshRasterizer.rasterize(resultPoints, Color.BLACK);
        }
    }
}