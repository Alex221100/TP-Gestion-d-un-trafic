package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main extends Application {
    private int taille;
    private List<Point> points = new ArrayList();
    private List<Node> representations = new ArrayList();
    private List<Node> routes = new ArrayList();

    /**lancement de l'application*/
    public void start(Stage primaryStage) throws IOException {
        construireFenetre(primaryStage);

    }

    void construireFenetre(Stage primaryStage) throws IOException {
        Scanner scanner = new Scanner( System.in );
        System.out.println("Entrer le nombre de route");
        int route = scanner.nextInt();
        int largeur = 90*route;
        int hauteur = 90*route;
        taille=largeur;
        //definir la troupe
        Group root = new Group(); //permet d'ajouter des points, traits, des objets ...
        //definir la scene principale
        Scene scene = new Scene(root, largeur, hauteur, Color.BLACK);
        primaryStage.setTitle("Trafic");
        primaryStage.setScene(scene);
        RoadNetwork r = new RoadNetwork();
        //on crée le plateau
        r = createRoadNetwork(largeur, hauteur, route);
        System.out.println("Entrer le nombre de voiture à créer sur une ligne");
        int nbCar = scanner.nextInt();
        //on génère le nombre de voitures voulu
        r.generateCar(nbCar);
        //on crée le mouvement
        root.getChildren().setAll(Stream.concat(process().stream(), r.process().stream())
                .collect(Collectors.toList()));
        RoadNetwork finalR = r;
        Timeline littleCycle = new Timeline(new KeyFrame(Duration.millis(2500),
                event-> {
                    updateCar(finalR);
                    root.getChildren().setAll(Stream.concat(process().stream(), finalR.process().stream())
                            .collect(Collectors.toList()));
                }));
        littleCycle.setCycleCount(Timeline.INDEFINITE);
        littleCycle.play();

        //afficher le theatre
        primaryStage.show();
    }

    public List<Node> process(){
        List<Node> n = new ArrayList<>();
        points.stream().map(Point::process).forEach(n::addAll);
        n.addAll(routes);
        this.representations=n;
        return n;
    }

    RoadNetwork createRoadNetwork(int largeur, int hauteur, int route) {
        RoadNetwork r = new RoadNetwork(route);
        for (int i = 0; i < r.getNbRoutes(); i++) {
            //Route verticale
            Line line = new Line(i * 100, 0, i * 100, hauteur);
            line.setStrokeWidth(20);
            line.setStroke(Color.GRAY);
            routes.add(line);
            //Pointillé vertical
            Line line1 = new Line(i * 100, 0, i * 100, hauteur);
            line1.getStrokeDashArray().addAll(10d, 15d);
            line1.setStroke(Color.WHITE);
            routes.add(line1);

            //Route horizontale
            Line line2 = new Line(0, i * 100, largeur, i * 100);
            line2.setStrokeWidth(20);
            line2.setStroke(Color.GRAY);
            routes.add(line2);
            //Pointillé horizontal
            Line line3 = new Line(0, i * 100, largeur, i * 100);
            line3.getStrokeDashArray().addAll(10d, 15d);
            line3.setStroke(Color.WHITE);
            routes.add(line3);

            //noeud
            for (int j = 0; j < r.getNbRoutes(); j++) {
                Circle c1 = new Circle();
                c1.setCenterX(i * 100);
                c1.setCenterY(j * 100);
                c1.setRadius(20);
                c1.setFill(Color.PURPLE);
                Point p1 = new Point(i * 4, j * 4, c1);
                r.setMatrice(p1);
                points.add(p1);

                //création des feux pour chaque point
                if (i == 0) {
                    TrafficLight t2 = new TrafficLight(p1, 'E');
                    p1.setTrafficLightEast(t2);
                } else {
                    TrafficLight t1 = new TrafficLight(p1, 'N');
                    t1.setColor('G');
                    p1.setTrafficLightNorth(t1);

                    TrafficLight t2 = new TrafficLight(p1, 'E');
                    p1.setTrafficLightEast(t2);
                }

                //Sous-noeud
                for(int k=1;k<4;k++) {
                    //sous-noeuds horizontaux
                    if (i*4+k<=r.getMatrice().length-1) {
                        Circle c2 = new Circle();
                        c2.setCenterX(i * 100 + k * 20);
                        c2.setCenterY(j * 100);
                        c2.setRadius(10);
                        c2.setFill(Color.BLUE);
                        Point p2 = new Point(i * 4 + k, j * 4, c2);
                        r.setMatrice(p2);
                        points.add(p2);
                    }
                    //sous-noeuds verticaux
                    if (j*4+k<=r.getMatrice().length-1) {
                        Circle c3 = new Circle();
                        c3.setCenterX(i * 100);
                        c3.setCenterY(j * 100 + k * 25);
                        c3.setRadius(10);
                        c3.setFill(Color.BLUE);
                        Point p3 = new Point(i * 4, j * 4 + k, c3);
                        r.setMatrice(p3);
                        points.add(p3);
                    }
                }

            }
        }
        return r;
    }


    void updateCar(RoadNetwork r){
        r.getAllCar().removeIf(Car::isCrashed);

        //on fait avancer la voiture dans la matrice seulement si le feu est vert
        for(Car v : r.getAllCar()) {
            if(v.getOrientation()=='N'){
                if(v.getLocation().getTrafficLightNorth()==null || v.getLocation().getTrafficLightNorth().getColor()=='G' ) {
                    if (v.getLocation().getY() != 0) {
                        v.setLocation(r.getMatrice()[v.getLocation().getX()][v.getLocation().getY() - 1]);
                    }
                }
            }else{
                if(v.getLocation().getTrafficLightEast()==null || v.getLocation().getTrafficLightEast().getColor()=='G') {
                    if (v.getLocation().getX() != r.getMatrice().length - 1) {
                        v.setLocation(r.getMatrice()[v.getLocation().getX() + 1][v.getLocation().getY()]);
                    }
                }
            }

            //collision
            for(Car v2 : r.getAllCar()) {
                if (!v.equals(v2) && v.getLocation()==v2.getLocation()){
                    ((Rectangle)v.getRepresentation()).setFill(Color.RED);
                    ((Rectangle)v2.getRepresentation()).setFill(Color.RED);
                    v.setCrashed(true);
                    v2.setCrashed(true);
                }
            }
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
