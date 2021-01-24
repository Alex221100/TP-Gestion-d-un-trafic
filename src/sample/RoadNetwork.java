package sample;

import javafx.scene.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class RoadNetwork {
    private Point [][] matrice ;
    private List<Car> allCar = new ArrayList();
    private int nbRoutes;

    public RoadNetwork(int nbRoutes){
        this.nbRoutes=nbRoutes;
        matrice = new Point[nbRoutes*4-3][nbRoutes*4-3];
    }

    public RoadNetwork(){}

    public Point[][] getMatrice() {
        return matrice;
    }

    public void setMatrice(Point p) {
        this.matrice[p.getX()][p.getY()] = p;
    }

    public int getNbRoutes() {
        return nbRoutes;
    }

    public void setNbRoutes(int nbRoutes) {
        this.nbRoutes = nbRoutes;
    }

    public void setMatrice(Point[][] matrice) {
        this.matrice = matrice;
    }

    public List<Car> getAllCar() {
        return allCar;
    }

    public void setAllCar(List<Car> allCar) {
        this.allCar = allCar;
    }

    public List<Car> generateCar(int nbCar) {
        Scanner scanner = new Scanner( System.in );
        while (nbCar > nbRoutes){
            //on demande à l'utilisateur un nombre correct
            System.out.println("Entrer le nombre de voiture à créer sur une ligne");
            nbCar = scanner.nextInt();
        }

        for (int i = 0; i < nbCar; i++) {
            Car v1 = new Car(this.matrice[i*4][matrice.length-1],'N');
            allCar.add(v1);
            if (i != matrice.length-1) {
                Car v2 = new Car(this.matrice[0][i*4], 'E');
                allCar.add(v2);
            }
        }
        return allCar;
    }

    public List<Node> process(){
        return allCar.stream().map(Car::process).collect(Collectors.toList());
    }


}
