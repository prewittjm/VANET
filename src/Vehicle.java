/**
 * Interface for a vehicle. Car and Truck implement this file.
 * Created by prewittjm on 3/7/15.
 */

import java.util.ArrayList;

/**
 * Interface that can be used to create a vehicle either a car or truck
 */
public interface Vehicle {
    /**
     * Returns the speed of the vehicle;
     * @return double representing the speed of the vehicle
     */
    double getSpeed();

    /**
     * Returns the xCoordinate of the vehicle
     * @return double representing the xCoordinate
     */
    double getxCoordinate();

    /**
     * Returns the yCoordinate of the vehicle
     * @return double representing the yCoordinate
     */
    double getyCoordinate();

    /**
     * Sets the speed of the vehicle
     * @param speed - the new speed of the vehicle
     */
    void setSpeed(double speed);

    /**
     * Sets a new xCoordinate for the vehicle
     * @param xCoordinate - the new xCoordinate of the vehicle
     */
    void setxCoordinate(double xCoordinate);

    /**
     * Sets a new yCoordinate for the vehicle
     * @param yCoordinate - the new yCoordinate of the vehicle
     */
    void setyCoordinate(double yCoordinate);

    /**
     * Returns the Port Number this vehicle will use to transmit packets between each neighbor
     * @return int representing the port number of the vehicle
     */
    int getPortNumber();

    /**
     * Sets a new post number for the vehicle
     * @param portNumber - int representing the port number of the vehicle
     */
    void setPortNumber(int portNumber);

    /**
     * Returns the unique address of the vehicle
     * @return int representing the unique address of the vehicle
     */
    int getMyID();

    /**
     * Sets a new unique address for the vehicle
     * @param address - the new address of the vehicle
     */
    void setId(int address);

    /**
     * Returns the length of the vehicle
     * @return double representing the length of the vehicle
     */
    double getLength();

    /**
     * Sets a new length for the vehicle
     * @param length - new length of the vehicle
     */
    void setLength(double length);

    /**
     * Returns the width of the vehicle
     * @return - double representing the width of the vehicle
     */
    double getWidth();

    /**
     * Sets a new width for the vehicle
     * @param width
     */
    void setWidth(double width);

    /**
     * Sets a new hostname for the vehicle
     * @param hostname - the new hostname for the vehicle
     */
    void setHostname(String hostname);

    /**
     * Returns a the hostname
     * @return String representing the hostname
     */
    String getHostname();

    /**
     * Returns ArrayList of neighbors
     * @return - ArrayList of nodes representing the neighbors
     */
    ArrayList<Node> getNeighbors();

    /**
     * Sets a new list of neighbors
     * @param neighborsIn - the new ArrayList of neighbor nodes
     */
    void setNeighbors(ArrayList<Node> neighborsIn);

    /**
     * Sets the boolean value for if the car/truck is in the road train
     * @param inRoadTrain - boolean value representing if the car/truck is in the road train
     */
    void setIfInRoadTrain(boolean inRoadTrain);

    /**
     * Returns the boolean value of whether the car is in the road train
     * @return true of false depending on if the car is in the road train
     */
    boolean getIfInRoadTrain();

}
