package sample;

import javafx.scene.Node;

import java.util.ArrayList;
import java.util.List;

public class Point {
    protected int x;
    protected int y;
    protected Node representation;
    protected TrafficLight trafficLightNorth;
    protected TrafficLight trafficLightEast;

    public Point(int x, int y, Node representation) {
        this.x = x;
        this.y = y;
        this.representation=representation;
        this.representation.setTranslateX(x);
        this.representation.setTranslateY(y);
    }

    public Point() {}

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Node getRepresentation() {
        return representation;
    }

    public void setRepresentation(Node representation) {
        this.representation = representation;
    }

    public TrafficLight getTrafficLightNorth() {
        return trafficLightNorth;
    }

    public void setTrafficLightNorth(TrafficLight trafficLightNorth) {
        this.trafficLightNorth = trafficLightNorth;
    }

    public TrafficLight getTrafficLightEast() {
        return trafficLightEast;
    }

    public void setTrafficLightEast(TrafficLight trafficLightEast) {
        this.trafficLightEast = trafficLightEast;
    }


    public List<Node> process(){
        List<Node> trafficLight = new ArrayList<>();
        trafficLight.add(this.representation);
        if (this.trafficLightEast !=null){
            trafficLight.add(this.trafficLightEast.process());
        }
        if (this.trafficLightNorth!=null){
            trafficLight.add(this.trafficLightNorth.process());
        }
        return trafficLight;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                ", representation=" + representation +
                ", trafficLightNorth=" + trafficLightNorth +
                ", trafficLightEast=" + trafficLightEast +
                '}';
    }
}
