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
        };
    if (!currNode.isEmpty()) {
        int i = 0;
        while (i < currNode.size() -1) {
            System.out.print(currNode.get(i).toString());
        }


    }




    }

    /**
     * Determines if the packet can be sent between the two cars based off the distance
     * @param veh1 - the first car put into the distance formula
     * @param veh2 - the second car put into the distance formula
     * @return - returns true if packet can be sent
     */
    public static boolean canSend(Vehicle veh1, Vehicle veh2) {
        double distance;
        distance = Math.sqrt((veh1.getxCoordinate()-veh2.getxCoordinate())*(veh1.getxCoordinate()-veh2.getxCoordinate())
                + (veh1.getyCoordinate()-veh2.getyCoordinate())*(veh1.getyCoordinate()-veh2.getyCoordinate()));
        double prob;
        prob = (((100-distance)/100)*((100-distance)/100)) * 100;
        double random = Math.random() * 100;
        System.out.println(prob);
        System.out.println(random);
        if (random < prob) {
            return true;
            //Send packet
            //Add to an integer that keeps track of packets sent
        }
        else {
            return false;
            //Add to an integer that keeps track of packets lost
        }
    }





}
