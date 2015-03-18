/**
 * Class that creates a packet and allows manipulation of that packet
 * Created by prewittjm on 3/7/15.
 */

import java.io.Serializable;

/**
 * This class will take different parts of the packet and serialize them into bytes that can then be sent over UDP.
 */
public class Packet implements Serializable {
    private int sequenceNumber, id, previousHop;
    private double speed, xCoordinate, yCoordinate;
    private String sourceNode;
    /**
     * Constructor to be used to make a packet.
     * @param sequenceNumber - a number to identify the packet. Increased each time a packet is created.
     * @param sourceNode - the source hostname of the packet
     * @param id - the source id of the packet. Used to know where the packet came from.
     * @param previousHop - the previous node the packet came from. May be the same as source address.
     * @param speed - the current speed of the car sending the packet
     * @param xCoordinate - the current xCoordinate of the car sending the packet
     * @param yCoordinate - the current yCoordinate of the car sending the packet
     */
    public Packet(int sequenceNumber, String sourceNode, int id, int previousHop, double speed, double xCoordinate, double yCoordinate) {
        this.sequenceNumber = sequenceNumber;
        this.previousHop = previousHop;
        this.id = id;
        this.speed = speed;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.sourceNode = sourceNode;
    }

    /**
     * Constructor that sets the current packet to a packet already created
     * @param packetIn - packet coming in
     */
    public Packet(Packet packetIn) {
        this.id = packetIn.getId();
        this.yCoordinate = packetIn.getyCoordinate();
        this.xCoordinate = packetIn.getxCoordinate();
        this.previousHop = packetIn.getPreviousHop();
        this.sequenceNumber = packetIn.getSequenceNumber();
        this.speed = packetIn.getSpeed();
        this.sourceNode = packetIn.getSourceNode();
    }
    /**
     * Sets a new value for the previous hop
     * @param previousHop - the new value for previous hop
     */
    public void setPreviousHop(int previousHop) {
        this.previousHop = previousHop;
    }
    /**
     * Sets a new value for the sequence number
     * @param sequenceNumber - the new value for the sequence number
     */
    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }
    /**
     * Sets a new value for hte source address
     * @param id - the new value for the source address
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Sets a new value for the Car's speed
     * @param speed - the new value for the car's speed
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }
    /**
     * Sets a new value for the xCoordinate representing the car's position
     * @param xCoordinate - the new value for the xCoordinate in the car's position
     */
    public void setxCoordinate(double xCoordinate) {
        this.xCoordinate = xCoordinate;
    }
    /**
     * Sets a new value for the yCoordinate representing the car's position
     * @param yCoordinate - the new value for the yCoordinate in the car's position
     */
    public void setyCoordinate(double yCoordinate) {
        this.yCoordinate = yCoordinate;
    }
    /**
     * Returns the speed as a double
     * @return double representing speed of the car
     */
    public double getSpeed() {
        return speed;
    }
    /**
     * Returns the speed as a double
     * @return double representing xCoordinate location of the car
     */
    public double getxCoordinate() {
        return xCoordinate;
    }
    /**
     * Returns the previous hop that the packet has traveled
     * @return int representing the last hope the packet traveled through
     */
    public int getPreviousHop() {
        return previousHop;
    }
    /**
     * Returns the sequence number that essentially ID's the packet
     * @return - int representing the sequence number of the packet
     */
    public int getSequenceNumber(){
        return sequenceNumber;
    }
    /**
     * Returns the yCoordinate of the car sending the packet
     * @return - double representing the yCoordinate of the car that sent the packet
     */
    public double getyCoordinate() {
        return yCoordinate;
    }
    /**
     * Returns the source address of the car that sent the packet
     * @return -  int representing the address of where the packet was sent from
     */
    public int getId() {
        return id;
    }

    /**
     * Sets a new source node for the packet
     * @param sourceNode - the new source node of the packet
     */
    public void setSourceNode(String sourceNode) {
        this.sourceNode = sourceNode;
    }

    /**
     * Returns the source node of the packet
     * @return - String representing the source node of the packet
     */
    public String getSourceNode() {
        return sourceNode;
    }
}
