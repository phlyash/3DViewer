package com.cgvsu.render_engine;

import com.cgvsu.Settings;
import com.cgvsu.math.Algorithms.Barycentric;
import com.cgvsu.math.Points.Point3D;
import com.cgvsu.math.Points.PolygonPoint;
import com.cgvsu.math.Vector.Vectors.Vector2D;
import com.cgvsu.math.Vector.Vectors.Vector3D;
import com.cgvsu.model.Texture;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

import java.util.Arrays;

public class Rasterizer {
    private float[][] zBuffer;
    private PixelWriter pw;
    private static Rasterizer rasterizer;

    public static Rasterizer getRasterizer() {
        if (rasterizer == null)
            rasterizer = new Rasterizer();
        return rasterizer;
    }

    private Rasterizer() {

    }

    public void setPw(PixelWriter pw) {
        this.pw = pw;
    }

    public void clearZBuffer(int height, int width) {
        zBuffer = new float[width][height];
        for(int i = 0; i < width; i++)
            Arrays.fill(zBuffer[i], Float.POSITIVE_INFINITY);
    }

    public void setColor(Point3D p1, Point3D p2, Point3D p3, int x, int y, Color color) {
        // to be thought about it
        if (x >= zBuffer.length || x < 0 || y >= zBuffer[0].length || y < 0) return;
        float z = Barycentric.calculateZ(p1, p2, p3, x, y);
        if (Math.abs(zBuffer[x][y] - z) < 1e-2) return;

        pw.setColor(x, y, color);
        zBuffer[x][y] = z;
    }

    // todo fix zBuffer with mesh
    // Ivan said to do it with another function but im not sure if it works properly
    public void setColorWithoutEpsilon(PolygonPoint p1, PolygonPoint p2, PolygonPoint p3, int x, int y) {
        if (x >= zBuffer.length || x < 0 || y >= zBuffer[0].length || y < 0) return;
        float[] barycentric = Barycentric.calculateCoeffs(p1.getCoords(), p2.getCoords(), p3.getCoords(), x, y);
        float z = Barycentric.calculateZ(p1.getCoords(), p2.getCoords(), p3.getCoords(), barycentric);
        if (zBuffer[x][y] <= z) return;

        Texture texture = Settings.getSettings().getTexture();

        int width = texture.getWidth();
        int height = texture.getHeight();
        Vector2D texturePixel = Barycentric.calculateTexture(p1, p2, p3, barycentric);
        int textureX = width - (int)(width * texturePixel.get(0));
        int textureY = height - (int)(height * texturePixel.get(1));
        Settings settings = Settings.getSettings();
        if (!settings.isDrawTexture()) {
            textureX = 0;
            textureY = 0;
        }

        if (textureX < 0 || textureX >= width || textureY < 0 || textureY >= height) return; // todo to be thought about it

        Color resultColor = texture.getPixel(textureX, textureY);
        Vector3D fragmentNormal = Barycentric.calculateNormal(p1, p2, p3, barycentric);

        Scene scene = Scene.getScene();

        if(settings.isUseAmbient() || settings.isUseDiffuse() || settings.isUseSpecular())
            for(int i = 0; i < scene.getLightsSize(); i++) {
                Vector3D ads = new Vector3D();
                LightSource lightSource = scene.getLight(i);
                Vector3D sceneLightColor = new Vector3D(
                        (float) lightSource.getSourceColor().getRed(),
                        (float) lightSource.getSourceColor().getBlue(),
                        (float) lightSource.getSourceColor().getGreen()
                );
                Vector3D fragmentPosition = Barycentric.calculateWorldCoords(p1, p2, p3, barycentric);

                if (settings.isUseAmbient()) ads.addVector(LightUtils.calculateAmbient(new Vector3D(sceneLightColor), settings.getAmbientFactor()));

                if (settings.isUseDiffuse())
                    ads.addVector(LightUtils.calculateDiffuse(fragmentNormal, fragmentPosition, new Vector3D(sceneLightColor),
                            new Vector3D(lightSource.getPosition())));

                if (settings.isUseSpecular())
                    ads.addVector(LightUtils.calculateSpecular(fragmentNormal, fragmentPosition, sceneLightColor,
                            new Vector3D(lightSource.getPosition()), Scene.getScene().getCurrentCamera().getPosition(), settings.getSpecularFactor()));

                resultColor = LightUtils.calculateColor(resultColor, ads);
            }

        pw.setColor(x, y, resultColor);

        zBuffer[x][y] = z;
    }

    public void setColor(Point3D p1, Color color) {
        if (p1.getX() >= zBuffer.length || p1.getX() < 0 || p1.getY() >= zBuffer[0].length || p1.getY() < 0) return;
        if (Math.abs(zBuffer[p1.getX()][p1.getY()] - p1.getZ()) < 1e-6) return;

        pw.setColor(p1.getX(), p1.getY(), color);

        zBuffer[p1.getX()][p1.getY()] = p1.getZ();
    }
}
