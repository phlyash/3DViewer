package com.cgvsu.render_engine;

import com.cgvsu.math.Vector.VectorFactory;
import com.cgvsu.math.Vector.Vectors.Vector3D;
import javafx.scene.paint.Color;

public class LightUtils {
    public static Vector3D calculateAmbient(Vector3D sourceColor, float ambientFactor) {
        return (Vector3D) VectorFactory.createMultipliedOnNumberVector(sourceColor, ambientFactor);
    }

    // для расчета диффузной составляющей следует учитывать аффинные преобразования, которые были
    // произведены для модели так как я это пока что не реализую, из-за недостатка времени, то
    // можно это не учитывать wи лишний раз не считать)
    // то же самое по текстурам
    public static Vector3D calculateDiffuse(Vector3D fragmentNormal, Vector3D fragmentPosition, Vector3D sourceColor,
            Vector3D lightPosition) {
        Vector3D lightDirection = (Vector3D) VectorFactory.createSubtractedVector(lightPosition, fragmentPosition);
        fragmentNormal.normalize();
        lightDirection.normalize();
        sourceColor.multiply(Math.max(fragmentNormal.getScalarMultiplication(lightDirection), 0));
        return sourceColor;
    }

    public static Vector3D calculateSpecular(Vector3D fragmentNormal, Vector3D fragmentPosition, Vector3D sourceColor,
                                             Vector3D lightPosition, Vector3D cameraPosition, float specularStr) {
        Vector3D viewDirection = (Vector3D) VectorFactory.createSubtractedVector(cameraPosition, fragmentPosition);
        viewDirection.normalize();

        lightPosition.multiply(-1);
        fragmentNormal.multiply(2f * lightPosition.getScalarMultiplication(fragmentNormal));
        Vector3D reflectedRay = (Vector3D) VectorFactory.createSubtractedVector(lightPosition, fragmentNormal);
        reflectedRay.normalize();

        float spec = (float) Math.pow(Math.max(viewDirection.getScalarMultiplication(reflectedRay), 0), 8);

        sourceColor.multiply(spec);
        sourceColor.multiply(specularStr);

        return sourceColor;
    }

    public static Color calculateColor(Color textureColor, Vector3D ads) {
        return new Color(
            Math.min(textureColor.getRed() * ads.get(0), 1),
            Math.min(textureColor.getGreen() * ads.get(1), 1),
            Math.min(textureColor.getBlue() * ads.get(2), 1),
            textureColor.getOpacity()
        );
    }
}
