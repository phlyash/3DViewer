package com.cgvsu;

import com.cgvsu.model.Texture;
import javafx.scene.paint.Color;

public class Settings {
    private boolean drawMesh = false;
    private boolean drawTexture = false;
    private boolean useAmbient = false;
    private boolean useDiffuse = false;
    private boolean useSpecular = false;
    private float ambientFactor = 1f;
    private float specularFactor = 1f;
    private Texture texture = new Texture(Color.CRIMSON);
    private static Settings settings;

    public static Settings getSettings() {
        if (settings == null) settings = new Settings();
        return settings;
    }

    private Settings() {

    }

    public boolean isDrawMesh() {
        return drawMesh;
    }

    public void setDrawMesh(boolean drawMesh) {
        this.drawMesh = drawMesh;
    }

    public boolean isDrawTexture() {
        return drawTexture;
    }

    public void setDrawTexture(boolean drawTexture) {
        this.drawTexture = drawTexture;
    }

    public boolean isUseAmbient() {
        return useAmbient;
    }

    public void setUseAmbient(boolean useAmbient) {
        this.useAmbient = useAmbient;
    }

    public boolean isUseDiffuse() {
        return useDiffuse;
    }

    public void setUseDiffuse(boolean useDiffuse) {
        this.useDiffuse = useDiffuse;
    }

    public boolean isUseSpecular() {
        return useSpecular;
    }

    public void setUseSpecular(boolean useSpecular) {
        this.useSpecular = useSpecular;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public float getAmbientFactor() {
        return ambientFactor;
    }

    public void setAmbientFactor(float ambientFactor) {
        this.ambientFactor = ambientFactor;
    }

    public float getSpecularFactor() {
        return specularFactor;
    }

    public void setSpecularFactor(float specularFactor) {
        this.specularFactor = specularFactor;
    }
}
