package com.example.charts;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

public class BottleDrawing extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create a Canvas
        Group group = new Group();

        drawBottle(group);
        // Create a Scene
        Scene scene = new Scene(group);

        // Set the stage title and scene
        primaryStage.setTitle("Bottle Drawing");
        primaryStage.setScene(scene);

        // Show the stage
        primaryStage.show();
    }

    private void drawBottle(Group gc) {

        Polygon triangle = new Polygon();
        triangle.getPoints().addAll(new Double[] {
                600.0, 300.,
                300., 800.,
                900., 800.
        });

        Rectangle rectangle = new Rectangle(500, 200, 200, 400);

        // Combine the triangle and rectangle using Shape::union
        Shape combinedShape = Shape.union(triangle, rectangle);

        LinearGradient gradient = new LinearGradient(100, 220, 100, 820, false, null,
                new Stop(0, Color.WHITE),
                new Stop(0.0, Color.WHITE),
                new Stop(0.0, Color.GREEN),
                new Stop(1, Color.RED));

        combinedShape.setFill(gradient);

        // Set the fill color

        // Set stroke properties
        combinedShape.setStroke(Color.BLACK);
        combinedShape.setStrokeWidth(20);

        // Add the combined shape to the group
        gc.getChildren().add(combinedShape);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
