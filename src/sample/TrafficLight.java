package sample;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class TrafficLight {
    private Point location;
    private char orientation;
    private char color; //R,O,G ou R=red, O=orange, G=green
    private Node representation;
    private int timer;

    public TrafficLight(Point location, char orientation) {
        this.location = location;
        this.orientation=orientation;
        this.color = 'R';
        Circle circle = new Circle();
        if (orientation=='N'){
            circle.setCenterX(location.getX() * 25+20 );
            circle.setCenterY(location.getY() * 25-20);
        }else{
            circle.setCenterX(location.getX() * 25-20);
            circle.setCenterY(location.getY() * 25+20);
        }
        circle.setFill(Color.RED);
        circle.setRadius(10);
        this.representation = circle;
        this.timer = 5;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public char getColor() {
        return color;
    }

    public void setColor(char color) {
        if (color=='G'){
            ((Circle)this.representation).setFill(Color.GREEN);
        }else{
            ((Circle)this.representation).setFill(Color.RED);
        }
        this.color = color;
    }

    public Node getRepresentation() {
        return representation;
    }

    public void setRepresentation(Node representation) {
        this.representation = representation;
    }

    public char getOrientation() {
        return orientation;
    }

    public void setOrientation(char orientation) {
        this.orientation = orientation;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public Node process() {
        timer--;
        if (timer == 0) {
            switch (this.color) {
                case 'R':
                    ((Circle) this.representation).setFill(Color.GREEN);
                    this.color='G';
                    timer = 2;
                    break;
                case 'O':
                    ((Circle) this.representation).setFill(Color.RED);
                    this.color='R';
                    timer = 1;
                    break;
                case 'G':
                    ((Circle) this.representation).setFill(Color.ORANGE);
                    this.color='O';
                    timer = 2;
                    break;
            }
        }
        return this.representation;
    }
}