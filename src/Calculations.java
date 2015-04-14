

/**
 * Class that contains many of the calculations needed for this application.
 * Created by prewittjm on 3/12/15.
 */
public class Calculations {

    /**
     * Returns the distance between two vehicles
     * @param veh1 - vehicle one
     * @param veh2 - vehicle two
     * @return - distance between the vehicles as a double
     */
    public static double distanceBetweenVehicles(Vehicle veh1, Vehicle veh2) {
        double distanceBetweenX = veh1.getxCoordinate() - veh2.getxCoordinate();
        double xSquared = Math.pow(distanceBetweenX, 2);
        double distanceBetweenY = veh1.getyCoordinate() - veh2.getyCoordinate();
        double ySquared = Math.pow(distanceBetweenY, 2);
        return Math.sqrt(xSquared + ySquared);
    }

    /**
     * Returns the distance between two points
     * @param x1 - x coordinate of point 1
     * @param y1 - y coordinate of point 1
     * @param x2 - x coordinate of point 2
     * @param y2 - y coordinate of point 2
     * @return - distance between the two points as a double
     */
    public static double distanceBetweenPoints(double x1, double y1, double x2, double y2) {
        double distanceBetweenX = x1 - x2;
        double xSquared = Math.pow(distanceBetweenX, 2);
        double distanceBetweenY = y1 - y2;
        double ySquared = Math.pow(distanceBetweenY, 2);
        return Math.sqrt(xSquared + ySquared);
    }

    /**
     * Returns boolean whether the packet can be sent or not.
     * @param veh1 - Vehicle 1
     * @param veh2 - Vehicle 2
     * @return - True if packet can be sent, false if can not; The closer the vehicles are together the higher
     * chance of sending
     */
    public static boolean isPacketLostBetweenVehicles(Vehicle veh1, Vehicle veh2) {
        double distance;
        distance = distanceBetweenVehicles(veh1, veh2);
        double prob;
        prob = (((100-distance)/100)*((100-distance)/100)) * 100;
        double random = Math.random() * 100;
        return random < prob;

    }
    /**
     * Returns boolean whether the packet can be sent or not.
     * @param x1 - x coordinate of point 1
     * @param y1 - y coordinate of point 1
     * @param x2 - x coordinate of point 2
     * @param y2 - y coordinate of point 2
     * @return - True if packet can be sent, false if can not; The closer the vehicles are together the higher
     * chance of sending
     */
    public static boolean isPacketLostBetweenPoints(double x1, double y1, double x2, double y2) {
        double distance;
        distance = distanceBetweenPoints(x1,y1,x2,y2);
        double prob;
        prob = (((100-distance)/100)*((100-distance)/100)) * 100;
        double random = Math.random() * 100;
        return random < prob;
    }

    /**
     * Returns boolean whether a collision between two vehicles has occurred
     * @param veh1 - vehicle one in the collision detection system
     * @param veh2 - vehicle two in the collision detection system.
     * @return - True if collision between two cars has occurred, false otherwise
     */
    public static boolean hasCollisionOccurredBetweenVehicles(Vehicle veh1, Vehicle veh2) {
        double veh1XCoordinate = veh1.getxCoordinate();
        double veh1YCoordinate = veh1.getyCoordinate();
        double veh2XCoordinate = veh2.getxCoordinate();
        double veh2YCoordinate = veh2.getyCoordinate();
        double veh1HalfWidth = 3/2;
        double veh2HalfWidth = 3/2;
        double veh1HalfLength;
        double veh2HalfLength;
        int veh1ToTheRight;
        int veh1ToTheTop;
        int veh2ToTheRight;
        int veh2ToTheTop;
        int xIntersect = 0;
        int yIntersect = 0;
        if (veh1.getMyID() == 1) {
            veh1HalfLength = 10/2;
            veh2HalfLength = 5/2;
        }
        else if (veh2.getMyID() == 1) {
            veh2HalfLength = 10/2;
            veh1HalfLength = 5/2;
        }
        else {
            veh1HalfLength = 5/2;
            veh2HalfLength = 5/2;
        }
        if (veh1XCoordinate < veh2XCoordinate) {
            veh1XCoordinate = veh1XCoordinate + veh1HalfLength;
            veh2XCoordinate = veh2YCoordinate - veh2HalfLength;
            veh1ToTheRight = 0;
            veh2ToTheRight = 1;
        }
        else if (veh1XCoordinate > veh2XCoordinate) {
            veh1XCoordinate = veh1XCoordinate - veh1HalfLength;
            veh2XCoordinate = veh2XCoordinate + veh2HalfLength;
            veh1ToTheRight = 1;
            veh2ToTheRight = 0;
        }
        else {
            veh1ToTheRight = 0;
            veh2ToTheRight = 0;
            xIntersect = 1;
        }
        if(veh1YCoordinate < veh2YCoordinate) {
            veh1YCoordinate = veh1YCoordinate + veh1HalfWidth;
            veh2YCoordinate = veh2YCoordinate - veh2HalfWidth;
            veh1ToTheTop = 0;
            veh2ToTheTop = 1;
        }
        else if(veh1YCoordinate > veh2YCoordinate) {
            veh1YCoordinate = veh1YCoordinate - veh1HalfWidth;
            veh2YCoordinate = veh2YCoordinate + veh2HalfWidth;
            veh1ToTheTop = 1;
            veh2ToTheTop = 0;
        }
        else {
            veh1ToTheTop = 0;
            veh2ToTheTop = 0;
            yIntersect = 1;
        }
        if (veh1ToTheRight == 1) {
            if ((veh1XCoordinate - veh2XCoordinate) <= 0) {
                xIntersect = 1;
            }
        }
        if (veh2ToTheRight == 1) {
            if ((veh2XCoordinate - veh1XCoordinate) <= 0) {
                xIntersect = 1;
            }
        }
        if (veh1ToTheTop == 1) {
            if ((veh1YCoordinate - veh2YCoordinate) <= 0) {
                yIntersect = 1;
            }
        }
        if (veh2ToTheTop == 1) {
            if ((veh2YCoordinate - veh1YCoordinate) <= 0) {
                yIntersect = 1;
            }
        }
        return xIntersect == 1 && yIntersect == 1;
    }

    /**
     * Returns boolean whether a collision between two vehicle has occurred
     * @param x1 - x coordinate of first vehicle
     * @param y1 - y coordinate of first vehicle
     * @param x2 - x coordinate of second vehicle
     * @param y2 - y coordinate of second vehicle
     * @param id1 - id of first vehicle
     * @param id2 - id of second vehicle
     * @return True if collision between the coordinates has occurred, false otherwise
     */
    public static boolean hasCollisionOccurredBetweenTwoPoints(double x1, double y1, double x2, double y2, int id1, int id2) {
        double veh1XCoordinate = x1;
        double veh1YCoordinate = y1;
        double veh2XCoordinate = x2;
        double veh2YCoordinate = y2;
        double veh1HalfWidth = 3/2;
        double veh2HalfWidth = 3/2;
        double veh1HalfLength;
        double veh2HalfLength;
        int veh1ToTheRight;
        int veh1ToTheTop;
        int veh2ToTheRight;
        int veh2ToTheTop;
        int xIntersect = 0;
        int yIntersect = 0;
        if (id1 == 1) {
            veh1HalfLength = 10/2;
            veh2HalfLength = 5/2;
        }
        else if (id2 == 1) {
            veh2HalfLength = 10/2;
            veh1HalfLength = 5/2;
        }
        else {
            veh1HalfLength = 5/2;
            veh2HalfLength = 5/2;
        }
        if (veh1XCoordinate < veh2XCoordinate) {
            veh1XCoordinate = veh1XCoordinate + veh1HalfLength;
            veh2XCoordinate = veh2YCoordinate - veh2HalfLength;
            veh1ToTheRight = 0;
            veh2ToTheRight = 1;
        }
        else if (veh1XCoordinate > veh2XCoordinate) {
            veh1XCoordinate = veh1XCoordinate - veh1HalfLength;
            veh2XCoordinate = veh2XCoordinate + veh2HalfLength;
            veh1ToTheRight = 1;
            veh2ToTheRight = 0;
        }
        else {
            veh1ToTheRight = 0;
            veh2ToTheRight = 0;
            xIntersect = 1;
        }
        if(veh1YCoordinate < veh2YCoordinate) {
            veh1YCoordinate = veh1YCoordinate + veh1HalfWidth;
            veh2YCoordinate = veh2YCoordinate - veh2HalfWidth;
            veh1ToTheTop = 0;
            veh2ToTheTop = 1;
        }
        else if(veh1YCoordinate > veh2YCoordinate) {
            veh1YCoordinate = veh1YCoordinate - veh1HalfWidth;
            veh2YCoordinate = veh2YCoordinate + veh2HalfWidth;
            veh1ToTheTop = 1;
            veh2ToTheTop = 0;
        }
        else {
            veh1ToTheTop = 0;
            veh2ToTheTop = 0;
            yIntersect = 1;
        }
        if (veh1ToTheRight == 1) if ((veh1XCoordinate - veh2XCoordinate) <= 0) {
            xIntersect = 1;
        }
        if (veh2ToTheRight == 1) if ((veh2XCoordinate - veh1XCoordinate) <= 0) {
            xIntersect = 1;
        }
        if (veh1ToTheTop == 1) if ((veh1YCoordinate - veh2YCoordinate) <= 0) {
            yIntersect = 1;
        }
        if (veh2ToTheTop == 1) if ((veh2YCoordinate - veh1YCoordinate) <= 0) {
            yIntersect = 1;
        }
        return xIntersect == 1 && yIntersect == 1;
    }

    /**
     * Part of the RBA. Takes in the cacheTable from the vehicle and checks if the packet has already been to this node.
     * If it has a random number is generated and if it is less than the probability of retransmission then
     * the packet will be forwarded to all other neighbor nodes
     * @param packet - incoming packet to the node that needs to be put through the algorithm
     * @param cacheTable - the cache table of the node for checking if the packet has already been sent to this node
     * @return - true if packet should be retransmitted, false if the packet should be dropped
     */
    public static boolean retransmissionRate(Packet packet, CacheTable cacheTable) {
        String hostname = packet.getSourceNode();
        int broadcastNumbers = cacheTable.getNumberOfBroadcasts(hostname);
        double random = Math.random();
        double rate = Math.pow(.5, broadcastNumbers);
        return rate == 0 || random < rate;
    }

    /**
     * Part of the RBA. Takes in the number of times this packet has been sent to the node and returns the RBA value of true or false if the packet can be sent
     * @param numberSent - number of times the packet has been sent
     * @return - true if packet can be sent, false otherwise
     */
    public static boolean retransmissionRate(int numberSent){
        double random = Math.random();
        double rate = Math.pow(.5, numberSent);
        return rate == 0 || random < rate;
    }

    /**
     * Checks to see if two vehicles are close to each other up to 3.0 meters
     * @param veh1 - vehicle 1
     * @param veh2 - vehicle 2
     * @return - True if the vehicles are close together
     */
    public static boolean isCloseToNeighboringVehicle(Vehicle veh1, Vehicle veh2) {
        return (distanceBetweenVehicles(veh1, veh2) <= 3.0);
    }

    /**
     * Checks to see if two points are close to each other
     * @param x1 - x coordinate of first point
     * @param y1 - y coordinate of first point
     * @param x2 - x coordinate of second point
     * @param y2 - y coordinate of second point
     * @return - True if the points are close to each other
     */
    public static boolean isCloseToNeighboringPoints(double x1, double y1, double x2, double y2) {
        return (distanceBetweenPoints(x1, y1, x2, y2) <= 3.0);
    }
}
