package com.example.charts;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.Arrays;

public class Bottle extends Group {
    private final Shape clone;

    public Bottle() {
        Polygon triangle = new Polygon();
        triangle.getPoints().addAll(new Double[] {
                310., 100.,
                10., 600.,
                610., 600.
        });

        Rectangle rectangle = new Rectangle(210, 10, 200, 400);

        // Combine the triangle and rectangle using Shape::union
        Shape combinedShape = Shape.union(triangle, rectangle);
        clone = Shape.union(combinedShape, combinedShape);

        LinearGradient gradient = new LinearGradient(100, 20, 100, 620, false, null,
                new Stop(0.0, Color.GREEN),
                new Stop(1, Color.RED));

        combinedShape.setFill(gradient);

        // Set the fill color

        // Set stroke properties
        combinedShape.setStroke(Color.BLACK);
        combinedShape.setStrokeWidth(20);

        setPercent(0.5);

        clone.setStroke(Color.BLACK);
        clone.setStrokeWidth(20);

        // Add the combined shape to the group
        super.getChildren().add(combinedShape);
        super.getChildren().add(clone);
    }

    public void setPercent(double d) {
        LinearGradient fill = new LinearGradient(100, 20, 100, 620, false, null,
                new Stop(0.0, Color.WHITE),
                new Stop(1-d, Color.WHITE),
                new Stop(1-d, Color.TRANSPARENT),
                new Stop(1, Color.TRANSPARENT));
        clone.setFill(fill);
    }
}
