package com.cgvsu;

import com.cgvsu.math.Vector.Vectors.Vector3D;
import com.cgvsu.model.Texture;
import com.cgvsu.objreader.IncorrectFileException;
import com.cgvsu.render_engine.Cameras.LightCamera;
import com.cgvsu.render_engine.Rasterizer;
import com.cgvsu.render_engine.RenderEngine;
import com.cgvsu.render_engine.Scene;
import com.cgvsu.triangulation.Triangulation;
import javafx.fxml.FXML;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.io.File;
import java.util.Optional;

import com.cgvsu.model.Model;
import com.cgvsu.objreader.ObjReader;

public class GuiController {

    final private float TRANSLATION = .5F;

    @FXML
    public CheckMenuItem ambientBox;

    @FXML
    public CheckMenuItem diffuseBox;

    @FXML
    public CheckMenuItem specularBox;

    @FXML
    public CheckMenuItem meshBox;

    @FXML
    public CheckMenuItem textureBox;

    @FXML
    AnchorPane anchorPane;

    @FXML
    private Canvas canvas;

    Scene scene;

    private Timeline timeline;

    @FXML
    private void initialize() {
        scene = Scene.getScene();
        scene.addCamera(new LightCamera(
                new Vector3D(0, 0, 100),
                new Vector3D(0, 0, 0),
                1.0F, 1, 0.1F, 1000, Color.WHITE)
        );
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame frame = new KeyFrame(Duration.millis(100), event -> {
            double width = canvas.getWidth();
            double height = canvas.getHeight();

            canvas.getGraphicsContext2D().clearRect(0, 0, width, height);
            scene.getCurrentCamera().setAspectRatio((float) (width / height));

            Rasterizer rasterizer = Rasterizer.getRasterizer();
            rasterizer.setPw(canvas.getGraphicsContext2D().getPixelWriter());
            rasterizer.clearZBuffer((int) height, (int) width);

            RenderEngine.renderFrame((int) width, (int) height);
        });

        timeline.getKeyFrames().add(frame);
        timeline.play();
    }

    @FXML
    private void onOpenModelMenuItemClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Load Model");

        File file = fileChooser.showOpenDialog((Stage) canvas.getScene().getWindow());
        if (file == null) {
            return;
        }

        Path fileName = Path.of(file.getAbsolutePath());

        try {
            String fileContent = Files.readString(fileName);
            Model readenModel = ObjReader.read(fileContent);
            Model triangulatedModel = Triangulation.getTriangulatedModel(readenModel);
            Triangulation.recalculateNormals(triangulatedModel);
            scene.addModel(triangulatedModel);
        } catch (IOException | IncorrectFileException exception) {
            exception.printStackTrace();
        }
    }

    @FXML
    public void onTextureSelectClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Texture (*.jpg, *.png)", "*.png", "*.jpg"));
        fileChooser.setTitle("Load Texture");

        File file = fileChooser.showOpenDialog(canvas.getScene().getWindow());
        if (file == null) return;


        try {
            // to lazy now for realizing several models correctly
            Settings.getSettings().setTexture(new Texture(file.getAbsolutePath()));
            textureBox.setSelected(true);
            Settings.getSettings().setDrawTexture(true);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @FXML
    public void handleCameraForward() {
        scene.getCurrentCamera().movePosition(new Vector3D(0, 0, -TRANSLATION));
    }

    @FXML
    public void handleCameraBackward() {
        scene.getCurrentCamera().movePosition(new Vector3D(0, 0, TRANSLATION));
    }

    @FXML
    public void handleCameraLeft() {
        scene.getCurrentCamera().movePosition(new Vector3D(TRANSLATION, 0, 0));
    }

    @FXML
    public void handleCameraRight() {
        scene.getCurrentCamera().movePosition(new Vector3D(-TRANSLATION, 0, 0));
    }

    @FXML
    public void handleCameraUp() {
        scene.getCurrentCamera().movePosition(new Vector3D(0, TRANSLATION, 0));
    }

    @FXML
    public void handleCameraDown() {
        scene.getCurrentCamera().movePosition(new Vector3D(0, -TRANSLATION, 0));
    }

    @FXML
    public void handleAmbientChange() {
        Settings.getSettings().setUseAmbient(ambientBox.isSelected());
    }

    @FXML
    public void handleDiffuseChange() {
        Settings.getSettings().setUseDiffuse(diffuseBox.isSelected());
    }

    @FXML
    public void handleSpecularChange() {
        Settings.getSettings().setUseSpecular(specularBox.isSelected());
    }

    @FXML
    public void handleMeshChange() {
        Settings.getSettings().setDrawMesh(meshBox.isSelected());
    }

    @FXML
    public void handleTextureChange() {
        Settings.getSettings().setDrawTexture(textureBox.isSelected());
    }

    @FXML
    public void onColorSelectClick() {
        Dialog<Color> dialog = new Dialog();
        dialog.setTitle("Color picker");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.APPLY);
        ColorPicker colorPicker = new ColorPicker();
        dialog.getDialogPane().setContent(colorPicker);
        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.APPLY)
                return colorPicker.getValue();
            return null;
        });
        Optional<Color> result = dialog.showAndWait();

        result.ifPresent(color -> Settings.getSettings().setTexture(new Texture(color)));
    }

    @FXML
    public void onAmbientCoefficientClick() {
        Dialog<Double> dialog = new Dialog();
        dialog.setTitle("Ambient picker");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.APPLY);

        Spinner<Double> ambientSpinner = new Spinner<>();
        SpinnerValueFactory<Double> spinnerValueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 1, Settings.getSettings().getAmbientFactor(), 0.01f);
        ambientSpinner.setValueFactory(spinnerValueFactory);

        dialog.getDialogPane().setContent(ambientSpinner);
        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.APPLY)
                return ambientSpinner.getValue();
            return null;
        });
        Optional<Double> result = dialog.showAndWait();

        result.ifPresent(d -> Settings.getSettings().setAmbientFactor(d.floatValue()));
    }

    @FXML
    public void onSpecularCoefficientClick() {
        Dialog<Double> dialog = new Dialog<>();
        dialog.setTitle("Specular picker");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.APPLY);

        Spinner<Double> specularSpinner = new Spinner<>();
        SpinnerValueFactory<Double> spinnerValueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 1, Settings.getSettings().getSpecularFactor(), 0.01f);
        specularSpinner.setValueFactory(spinnerValueFactory);

        dialog.getDialogPane().setContent(specularSpinner);
        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.APPLY)
                return specularSpinner.getValue();
            return null;
        });
        Optional<Double> result = dialog.showAndWait();

        result.ifPresent(d -> Settings.getSettings().setSpecularFactor(d.floatValue()));
    }
}