package com.cgvsu.render_engine.Cameras;

import com.cgvsu.math.Vector.Vectors.Vector3D;
import com.cgvsu.render_engine.LightSource;
import javafx.scene.paint.Color;

public class LightCamera extends Camera implements LightSource {
    private Color sourceColor;
    public LightCamera(Vector3D position, Vector3D target, float fov, float aspectRatio, float nearPlane, float farPlane, Color sourceColor) {
        super(position, target, fov, aspectRatio, nearPlane, farPlane);
        this.sourceColor = sourceColor;
    }

    @Override
    public Color getSourceColor() {
        return sourceColor;
    }
}
