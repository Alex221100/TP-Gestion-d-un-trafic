package sample;

import javafx.scene.Node;

public class SubPoint extends Point{

    public SubPoint(int x, int y, Node representation){
        super();
        this.x = x;
        this.y = y;
        this.representation=representation;
        this.trafficLightEast=null;
        this.trafficLightNorth=null;
    }

}
