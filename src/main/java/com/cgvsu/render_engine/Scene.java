package com.cgvsu.render_engine;

import com.cgvsu.model.Model;
import com.cgvsu.render_engine.Cameras.Camera;

import java.util.ArrayList;

public class Scene {
    private int currentCamera = 0;
    private final ArrayList<Camera> cameras = new ArrayList<>();
    private final ArrayList<Model> models = new ArrayList<>();
    private final ArrayList<LightSource> lights = new ArrayList<>();
    private static Scene scene;

    public static Scene getScene() {
        if(scene == null)
            scene = new Scene();
        return scene;
    }

    private Scene() {

    }

    public void addCamera(Camera camera) {
        if (camera instanceof LightSource) {
            addLight((LightSource) camera);
        }
        cameras.add(camera);
    }

    public Camera getCamera(int index) {
        return cameras.get(index);
    }

    public void addLight(LightSource light) {
        this.lights.add(light);
    }

    public LightSource getLight(int index) {
        return this.lights.get(index);
    }

    public int getLightsSize(){
        return lights.size();
    }

    public void addModel(Model model) {
        models.add(model);
    }

    public Model getModel(int index) {
        return models.get(index);
    }

    public int getModelsSize() {
        return models.size();
    }

    public Camera getCurrentCamera() {
        return getCamera(currentCamera);
    }

    public void setCurrentCamera(int index) {
        if (index >= cameras.size()) return;

        currentCamera = index;
    }

    public boolean isEmpty() {
        return models.isEmpty();
    }
}
