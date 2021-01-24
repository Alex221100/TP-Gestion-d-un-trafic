package sample;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Car {
    private Point location;
    private float velocity;
    private float acceleration;
    private float maxspeed; //vitesse max
    private char orientation; //N,E N=> nord, orientation de la tete du véhicule vers le haut
    private Node representation;
    private boolean isCrashed;


    public Car(Point location, char orientation) {
        this.location = location;
        this.orientation = orientation;
        Rectangle rectangle = new Rectangle();
        rectangle.setX(location.getX() * 25);
        if (orientation =='E') {
            rectangle.setY(location.getY()*25);
        }else{
            rectangle.setY(location.getY());
        }
        rectangle.setHeight(15);
        rectangle.setWidth(25);
        rectangle.setFill(Color.MISTYROSE);
        //si la voiture est orientée vers le nord, il faut faire une rotation du rectangle
        if (this.orientation == 'N'){
            rectangle.setRotate(90);
        }
        this.representation=rectangle;
        this.isCrashed=false;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public float getVelocity() {
        return velocity;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }

    public float getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }

    public float getMaxspeed() {
        return maxspeed;
    }

    public void setMaxspeed(float maxspeed) {
        this.maxspeed = maxspeed;
    }

    public char getOrientation() {
        return orientation;
    }

    public void setOrientation(char orientation) {
        this.orientation = orientation;
    }

    public Node getRepresentation() {
        return representation;
    }

    public void setRepresentation(Node representation) {
        this.representation = representation;
    }

    public boolean isCrashed() {
        return isCrashed;
    }

    public void setCrashed(boolean crashed) {
        isCrashed = crashed;
    }

    @Override
    public String toString() {
        return "Voiture{" +
                "location=" + location +
                ", velocity=" + velocity +
                ", acceleration=" + acceleration +
                ", maxspeed=" + maxspeed +
                ", orientation=" + orientation +
                '}';
    }

    public Node process(){
        if (orientation=='E') {
            this.representation.setTranslateX(this.getLocation().getX() * 25);
        }else{
            this.representation.setTranslateY(this.getLocation().getY() * 25);
        }

        return this.representation;
    }
}

