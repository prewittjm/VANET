import javax.swing.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by prewittjm on 3/10/15.
 */
public class Vanet {


    public static void main(String args[]) {
        //Car car1;
        //Car car2;
        //car1 = new Car(1234,10120,30.0,15.0, 20.0);
        //car2 = new Car(2345, 10120, 45.0, 17.7, 21.2);
        //double distance;
        //distance = Math.sqrt((car1.getxCoordinate()-car2.getxCoordinate())*(car1.getxCoordinate()-car2.getxCoordinate())
//                + (car1.getyCoordinate()-car2.getyCoordinate())*(car1.getyCoordinate()-car2.getyCoordinate()));
    //System.out.println(distance);
        //System.out.println(canSend(car1, car2));
        List<Node> currNode = new ArrayList<Node>();
       try {
           currNode = ConfigFileReader.readEntireFile("/Users/prewittjm/IdeaProjects/VANET/src/config.txt");
       }
        catch (IOException error) {
        error.getMessage();
        }
//int j = 0;
//        String name = "";
        InetAddress localAddress = null;
        try {
            localAddress = InetAddress.getLocalHost();
        }
        catch (UnknownHostException e) {
            e.getMessage();
        }

        String localHostName = localAddress.getHostName();
        int indexOfPeriod = localHostName.indexOf('.');
        if (indexOfPeriod > 0) {
            localHostName = localHostName.substring(0, indexOfPeriod);
        }
//        try {
//           name = InetAddress.getLocalHost().getHostName();
//        }
//        catch (UnknownHostException e) {
//            e.getMessage();
//        }
        boolean nodeMade = false;
        boolean carCreated = false;
        boolean truckCreated = false;
        boolean simulationInProgress = true;
        int currentCount = 0;
        int nodeInList = 0;
        for (Node node : currNode) {
            if (node.getHostname().equals(localHostName)) {
                if (node.getNodeID() == 0) {
                    System.out.println("You are a truck.");
                    nodeMade = true;
                    truckCreated = true;
                    nodeInList = currentCount;
                    //Truck truck = new Truck(node);
                } else {
                    System.out.println("You are a car");
                    nodeMade = true;
                    carCreated = true;
                    nodeInList = currentCount;
                    //Car car = new Car(node);
                }
            }
        currentCount++;
        }
        if (!nodeMade) {
            System.out.println("Hostname not found in node list");
            System.exit(0);
        }
        else if (carCreated) {
            Node nodeUsing = currNode.get(nodeInList);
            Car car = new Car(nodeUsing);
        }
        else if (truckCreated) {
            Node nodeUsing = currNode.get(nodeInList);
            Truck truck = new Truck(nodeUsing);
        }

        JFrame carGUI = new CarGUI(currNode);
        while (true) {
            try {
                Thread.sleep(1000);
                carGUI.repaint();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        //System.out.println(localHostName);


//    while(j < currNode.size()) {
//
//        if (currNode.get(j).getNodeID() == 1) {
//            Truck truck = new Truck(currNode.get(j));
//        }
//        else {
//            Car car = new Car(currNode.get(j));
//        }
//
//    }
//
//    if (!currNode.isEmpty()) {
//        int i = 0;
//        while (i < currNode.size()) {
//            System.out.println(currNode.get(i).toString());
//            i++;
//        }
//
//
//    }




    }







}
