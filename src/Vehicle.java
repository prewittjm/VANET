/**
 * Created by prewittjm on 3/7/15.
 */

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
    public int getAddress();

    /**
     * Sets a new unique address for the vehicle
     * @param address - the new address of the vehicle
     */
    public void setAddress(int address);

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


}