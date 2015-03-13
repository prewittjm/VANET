import java.io.IOException;
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

    CarGUI gui = new CarGUI(currNode);

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
