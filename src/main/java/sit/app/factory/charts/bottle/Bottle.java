package sit.app.factory.charts.bottle;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;

import static java.lang.Math.sqrt;

/**
 * Klasse zum Erstellen der Flasche.
 * <p>Die Klasse erzeugt eine Flasche mit Farbverlauf, welche je nach eingestellter Füllhöhe nicht ganz zu sehen ist.
 * Dies wird intern verwirklicht, indem zwei Flaschen erstellt werden;
 * eine im Hintergrund mit dem Farbverlauf, und eine davor welche weiß ist und die farbige in teilen überdeckt.</p>
 * <p>Es wird 20% des oberen Platzes freigehalten, damit Arbeiten über den soll möglich ist</p>
 */
public class Bottle extends Group {
    private final Shape clone; //Eine zweite Flasche, um die erste in Teilen abzudecken
    private int ist=100, soll=100;
    private final Label istLabel = new Label(Integer.toString(ist));
    private final Label sollLabel = new Label(Integer.toString(soll));

    private final double widthRect;
    private final double heightRect;
    private final double widthTrap;
    private final double heightTrap;
    private final double stroke;

    public Bottle() {
        this(200, 0, 200, 400, 200, 200, 20);
    }

    /**
     * Erstellt die Flasche
     * @param xRect der x-Wert des Rechtecks. Es ist die obere linke Ecke gemeint. Durch das untere Trapez kann auch links vom x-Wert eine Fläche sein.
     * @param yRect der y-Wert des Rechtecks. Es ist die obere Kante gemeint.
     * @param widthRect Die Breite des Rechtecks
     * @param heightRect Die Höhe des Rechtecks
     * @param widthTrap Die zusätzliche Breite des Trapezes verglichen mit dem Rechteck. Die Länge der unteren Kante des Rechtecks ist widthRect+2*widthTrap
     * @param heightTrap Die Höhe des Trapezes
     * @param stroke Die Breite des schwarzen Rahmens. Er wird außerhalb der Flasche erzeugt, also noch über den x- und y-Wert
     */
    public Bottle(double xRect, double yRect, double widthRect, double heightRect, double widthTrap, double heightTrap, double stroke) {
        this.widthRect=widthRect;
        this.heightRect=heightRect;
        this.widthTrap=widthTrap;
        this.heightTrap=heightTrap;
        this.stroke=stroke;

        h_d = heightTrap*(widthRect/2+this.widthTrap)/this.widthTrap;
        a_trap = heightTrap*(widthRect+this.widthTrap);
        a_ges = this.heightRect*widthRect + a_trap;
        fallunterscheidung = a_trap/a_ges;

        Polygon triangle = new Polygon();
        triangle.getPoints().addAll(
                xRect -this.widthTrap, yRect +this.heightRect+heightTrap, //Punkt Trapez unten links
                xRect, yRect +this.heightRect, //Punkt Trapez oben links
                xRect +widthRect, yRect +this.heightRect, //Punkt Trapez oben rechts
                xRect +widthRect+this.widthTrap, 600. //Punkt Trapez unten rechts
        );

        Rectangle rectangle = new Rectangle(xRect, yRect, widthRect, this.heightRect);

        sollLabel.setTranslateX(xRect -150-stroke);
        sollLabel.setTranslateY(heightRect+heightTrap-calcHeight(0.8)-stroke);
        sollLabel.setFont(new Font(30));
        sollLabel.setStyle("-fx-font-weight: bold");


        istLabel.setFont(new Font(30));
        istLabel.setStyle("-fx-font-weight: bold");

        // Combine the triangle and rectangle using Shape::union
        Shape combinedShape = Shape.union(triangle, rectangle);
        clone = Shape.union(combinedShape, combinedShape);

        double v = calcHeight(0.4) / (heightRect+heightTrap);
        double v1 = calcHeight(0.8) / (heightRect+heightTrap);
        LinearGradient gradient = new LinearGradient(widthRect/2, 0, widthRect/2, this.heightRect+heightTrap, false, null,
                new Stop(0., Color.TURQUOISE),
                new Stop(1-v1, Color.GREEN),
                new Stop(1-v, Color.YELLOW),
                new Stop(1., Color.RED));

        combinedShape.setFill(gradient);
        istLabel.setTextFill(Color.BLUE);

        // Set stroke properties
        combinedShape.setStroke(Color.BLACK);
        combinedShape.setStrokeType(StrokeType.OUTSIDE);
        combinedShape.setStrokeWidth(stroke);

        clone.setStroke(Color.BLACK);
        clone.setStrokeType(StrokeType.OUTSIDE);
        clone.setStrokeWidth(stroke);

        // Add the combined shape to the group
        super.getChildren().add(combinedShape);
        super.getChildren().add(clone);
        super.getChildren().add(sollLabel);
        super.getChildren().add(istLabel);
    }

    /**
     * Höhe des Dreiecks
     */
    private final double h_d;
    /**
     * Prozentzahl, ab wo das Trapez komplett ausgefüllt ist
     */
    private final double fallunterscheidung;
    /**
     * Der Flächeninhalt der Flasche
     */
    private final double a_ges;
    /**
     * Der Flächeninhalt des Trapezes
     */
    private final double a_trap;

    /**
     * Berechnet die Höhe des Füllstandes abhängig von Breite und Höhe der Flasche
     * @param percent Prozent der Fläche, welche ausgefüllt werden soll
     * @return Die Füllhöhe der Flasche
     */
    private double calcHeight(double percent) {
        return (percent<fallunterscheidung) ?
                h_d - sqrt(h_d * (h_d - 2 * (percent * a_ges) / (2 * widthTrap + widthRect))) : //Fall 1: Nur Teile des Trapezes ausfüllen
                heightTrap + ((percent * a_ges) - a_trap) / widthRect;  //Fall 2: Das ganze Trapez und Teile des Rechtecks ausfüllen
    }

    /**
     * Aktualisiert den sichtbaren Farbverlauf und die Position des Ist-Labels
     */
    private void setPercent() {
        double a_p = ist / (soll * 1.2); //Prozentzahl der auszufüllenden Fläche
        if(a_p>1) a_p = 1;

        double h_p = calcHeight(a_p); //Höhe, welche auszufüllen ist
        if(a_p < fallunterscheidung) { //Fall 1: Nur Teile des Trapezes ausfüllen
            istLabel.setTranslateX(250+ stroke + widthRect+ widthTrap - widthTrap*(h_p/heightTrap));
        }
        else { //Fall 2: Das ganze Trapez und Teile des Rechtecks ausfüllen
            istLabel.setTranslateX(250+ stroke + widthRect);
        }
        istLabel.setTranslateY(heightRect+heightTrap-h_p-stroke);
        double p = h_p / (heightRect+heightTrap);

        //Setze die Füllung, indem die Flasche ab (1-p) transparent ist
        LinearGradient fill = new LinearGradient(widthRect/2, 0, widthRect/2, heightRect+heightTrap, false, null,
                new Stop(0, Color.WHITE),
                new Stop(1-p, Color.WHITE),
                new Stop(1-p, Color.TRANSPARENT),
                new Stop(1, Color.TRANSPARENT));
        clone.setFill(fill);
    }

    public void setSoll(int soll) {
        this.soll=soll;
        setPercent();
        sollLabel.setText("Soll:\t"  + soll);
    }

    public void setIst(int ist) {
        this.ist = ist;
        setPercent();
        istLabel.setText("Ist:\t" + ist);
    }
}
