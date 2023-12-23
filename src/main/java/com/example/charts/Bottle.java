package com.example.charts;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;

public class Bottle extends Group {
    private final Shape clone;
    private int ist=100, soll=100;
    private Label istLabel = new Label(Integer.toString(ist)),
    sollLabel = new Label(Integer.toString(soll));

    public Bottle() {
        Polygon triangle = new Polygon();
        triangle.getPoints().addAll(310., 100.,
                10., 600.,
                610., 600.);

        Rectangle rectangle = new Rectangle(210, 10, 200, 400);

        sollLabel.setTranslateX(50);
        sollLabel.setFont(new Font(30));
        sollLabel.setStyle("-fx-font-weight: bold");


        istLabel.setFont(new Font(30));
        istLabel.setStyle("-fx-font-weight: bold");

        // Combine the triangle and rectangle using Shape::union
        Shape combinedShape = Shape.union(triangle, rectangle);
        clone = Shape.union(combinedShape, combinedShape);

        LinearGradient gradient = new LinearGradient(100, 20, 100, 620, false, null,
                new Stop(0.0, Color.GREEN),
                new Stop(1, Color.RED));

        combinedShape.setFill(gradient);
        istLabel.setTextFill(Color.BLUE);

        // Set the fill color

        // Set stroke properties
        combinedShape.setStroke(Color.BLACK);
        combinedShape.setStrokeWidth(20);

        clone.setStroke(Color.BLACK);
        clone.setStrokeWidth(20);

        // Add the combined shape to the group
        super.getChildren().add(combinedShape);
        super.getChildren().add(clone);
        super.getChildren().add(sollLabel);
        super.getChildren().add(istLabel);
    }

    private void setPercent() {
        double d = (double) ist/soll;
        LinearGradient fill = new LinearGradient(100, 20, 100, 620, false, null,
                new Stop(0.0, Color.WHITE),
                new Stop(1-d, Color.WHITE),
                new Stop(1-d, Color.TRANSPARENT),
                new Stop(1, Color.TRANSPARENT));
        clone.setFill(fill);

    }

    public void setSoll(int soll) {
        this.soll=soll;
        setPercent();
        sollLabel.setText("Soll:\t"  + Integer.toString(soll));
    }

    public void setIst(int ist) {
        this.ist = ist;
        setPercent();
        double d = (double) ist/soll;
        double y = 570 - (d) * 570;
        double x = 500;
        if(y>200) x += (y-200)/400. * 200;

        istLabel.setTranslateX(x);
        istLabel.setTranslateY(y);
        istLabel.setText("Ist:\t" + Integer.toString(ist));
    }
}
