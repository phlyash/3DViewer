package com.cgvsu.render_engine.Cameras;
import com.cgvsu.math.Vector.Vectors.Vector3D;
import com.cgvsu.math.Matrix.Matrixes.Matrix4D;
import com.cgvsu.render_engine.GraphicConveyor;

public class Camera {

    public Camera(
            final Vector3D position,
            final Vector3D target,
            final float fov,
            final float aspectRatio,
            final float nearPlane,
            final float farPlane) {
        this.position = position;
        this.target = target;
        this.fov = fov;
        this.aspectRatio = aspectRatio;
        this.nearPlane = nearPlane;
        this.farPlane = farPlane;
    }

    public void setPosition(final Vector3D position) {
        this.position = position;
    }

    public void setTarget(final Vector3D target) {
        this.target = target;
    }

    public void setAspectRatio(final float aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public Vector3D getPosition() {
        return position;
    }

    public Vector3D getTarget() {
        return target;
    }

    public void movePosition(final Vector3D translation) {
        this.position.addVector(translation);
    }

    public void moveTarget(final Vector3D translation) {
        this.target.addVector(target);
    }

    public Matrix4D getViewMatrix() {
        return GraphicConveyor.lookAt(position, target);
    }

    public Matrix4D getProjectionMatrix() {
        return GraphicConveyor.perspective(fov, aspectRatio, nearPlane, farPlane);
    }

    private Vector3D position;
    private Vector3D target;
    private float fov;
    private float aspectRatio;
    private float nearPlane;
    private float farPlane;
}