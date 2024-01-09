package com.cgvsu.render_engine;

import com.cgvsu.math.Vector.Vectors.Vector3D;
import javafx.scene.paint.Color;

public interface LightSource {
    Vector3D getPosition();
    Color getSourceColor();
}
