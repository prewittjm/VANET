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
    public double getSpeed();

    /**
     * Returns the xCoordinate of the vehicle
     * @return double representing the xCoordinate
     */
    public double getxCoordinate();

    /**
     * Returns the yCoordinate of the vehicle
     * @return double representing the yCoordinate
     */
    public double getyCoordinate();

    /**
     * Sets the speed of the vehicle
     * @param speed - the new speed of the vehicle
     */
    public void setSpeed(double speed);

    /**
     * Sets a new xCoordinate for the vehicle
     * @param xCoordinate - the new xCoordinate of the vehicle
     */
    public void setxCoordinate(double xCoordinate);

    /**
     * Sets a new yCoordinate for the vehicle
     * @param yCoordinate - the new yCoordinate of the vehicle
     */
    public void setyCoordinate(double yCoordinate);

    /**
     * Returns the Port Number this vehicle will use to transmit packets between each neighbor
     * @return int representing the port number of the vehicle
     */
    public int getPortNumber();

    /**
     * Sets a new post number for the vehicle
     * @param portNumber - int representing the port number of the vehicle
     */
    public void setPortNumber(int portNumber);

    /**
     * Returns the unique address of the vehicle
     * @return int representing the unique address of the vehicle
     */
    public int getMyID();

    /**
     * Sets a new unique address for the vehicle
     * @param address - the new address of the vehicle
     */
    public void setId(int address);

    /**
     * Returns the length of the vehicle
     * @return double representing the length of the vehicle
     */
    public double getLength();

    /**
     * Sets a new length for the vehicle
     * @param length - new length of the vehicle
     */
    public void setLength(double length);

    /**
     * Returns the width of the vehicle
     * @return - double representing the width of the vehicle
     */
    public double getWidth();

    /**
     * Sets a new width for the vehicle
     * @param width
     */
    public void setWidth(double width);

    /**
     * Sets a new hostname for the vehicle
     * @param hostname - the new hostname for the vehicle
     */
    public void setHostname(String hostname);

    /**
     * Returns a the hostname
     * @return String representing the hostname
     */
    public String getHostname();

    /**
     * Returns ArrayList of neighbors
     * @return - ArrayList of nodes representing the neighbors
     */
    public ArrayList<Node> getNeighbors();

    /**
     * Sets a new list of neighbors
     * @param neighborsIn - the new ArrayList of neighbor nodes
     */
    public void setNeighbors(ArrayList<Node> neighborsIn);


}
