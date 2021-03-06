/**
 * Class that creates a packet and allows manipulation of that packet
 * Created by prewittjm on 3/7/15.
 */

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class will take different parts of the packet and serialize them into bytes that can then be sent over UDP.
 */
public class Packet implements Serializable {
    private int sequenceNumber, id, previousHop, packetType, portNumber, idTo;
    private double speed, xCoordinate, yCoordinate;
    private String sourceNode;
    private long currentTime;
    private ArrayList<Node> biDirectionalLinks;
    private ArrayList<Integer> receivedHelloFrom;
    private Boolean MPR;
    /**
     * Constructor to be used to make a packet.
     * @param sequenceNumber - a number to identify the packet. Increased each time a packet is created.
     * @param sourceNode - the source hostname of the packet
     * @param id - the source id of the packet. Used to know where the packet came from.
     * @param previousHop - the previous node the packet came from. May be the same as source address.
     * @param speed - the current speed of the car sending the packet
     * @param xCoordinate - the current xCoordinate of the car sending the packet
     * @param yCoordinate - the current yCoordinate of the car sending the packet
     * @param packetType - the value of the packet
     */
    public Packet(int sequenceNumber, String sourceNode, int portNumber, int id, int previousHop, double speed, double xCoordinate, double yCoordinate,
                  long currentTime, int packetType) {
        this.sequenceNumber = sequenceNumber;
        this.previousHop = previousHop;
        this.id = id;
        this.speed = speed;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.sourceNode = sourceNode;
        this.currentTime = currentTime;
        this.packetType = packetType;
        this.portNumber = portNumber;

        idTo = 0;
    }
    /**
     *
     * Constructor to be used to make a packet.
     * @param sequenceNumber - a number to identify the packet. Increased each time a packet is created.
     * @param sourceNode - the source hostname of the packet
     * @param id - the source id of the packet. Used to know where the packet came from.
     * @param previousHop - the previous node the packet came from. May be the same as source address.
     * @param speed - the current speed of the car sending the packet
     * @param xCoordinate - the current xCoordinate of the car sending the packet
     * @param yCoordinate - the current yCoordinate of the car sending the packet
     * @param packetType - the value of the packet
     * @param idTo - the node the packet is intended for
     */
    public Packet(int sequenceNumber, String sourceNode, int portNumber, int id, int previousHop, double speed, double xCoordinate, double yCoordinate,
                  long currentTime, int packetType, int idTo) {
        this.sequenceNumber = sequenceNumber;
        this.previousHop = previousHop;
        this.id = id;
        this.speed = speed;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.sourceNode = sourceNode;
        this.currentTime = currentTime;
        this.packetType = packetType;
        this.portNumber = portNumber;
        this.idTo = idTo;

    }
    /**
     * The HELLO packet used to set up the MPRs
     * @param biDirectionalLinks - list of addresses of the neighbors which there exists a bi-directional link
     * @param receivedHelloFrom - the list of addresses of the neighbors which are heard by this node (a HELLO has been received)
     */
    public Packet(int id, ArrayList<Node> biDirectionalLinks, ArrayList<Integer> receivedHelloFrom) {
        this.biDirectionalLinks = biDirectionalLinks;
        this.receivedHelloFrom = receivedHelloFrom;
        packetType = 0;
        this.id = id;
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
        this.packetType = packetIn.getPacketType();
        this.portNumber = packetIn.getPortNumber();
        this.idTo = packetIn.getIdTo();
        this.packetType = packetIn.getPacketType();
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
    /**
     * Returns the current time in milliseconds
     * @return - long representing the current time
     */
    public long getCurrentTime() {
        return currentTime;
    }
    /**
     * Sets a new current time
     * @param currentTime - the new current time
     */
    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }
    /**
     * Returns the packet type. 1 if it is a regular packet sending coordinates, 2 if car is attempting to join road train, 3 if
     * car is ok to join train
     * @return - int representing the type of packet being sent
     */
    public int getPacketType() {
        return packetType;
    }
    /**
     * Sets a new value for the packet type
     * @param packetType - the new value for the packet
     */
    public void setPacketType(int packetType) {
        this.packetType = packetType;
    }
    /**
     * Returns the portnumber of the node that sent this packet
     * @return - portnumber of the node that sent this packet
     */
    public int getPortNumber() {
        return portNumber;
    }
    /**
     * Sets a new portnumber of the node that sent this packet
     * @param portNumber - new portnumber
     */
    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }
    /**
     * Returns the id of the node that
     * @return - the id of the node the packe is meant for
     */
    public int getIdTo() {
        return idTo;
    }
    /**
     * Sets a new id to send to
     * @param idTo - the new id
     */
    public void setIdTo(int idTo) {
        this.idTo = idTo;
    }
    /**
     * Returns a list of bidirectional links
     * @return - ArrayList of biDirectionalLinks
     */
    public ArrayList<Node> getBiDirectionalLinks() {
        return biDirectionalLinks;
    }
    /**
     * Returns a list of nodes that this node has received a hello from
     * @return - ArrayList of nodes that this node has received a hello from
     */
    public ArrayList<Integer> getReceivedHelloFrom() {
        return receivedHelloFrom;
    }
    /**
     * Sets a new value for the bidirectional links
     * @param biDirectionalLinks - the new list of bidirectional links
     */
    public void setBiDirectionalLinks(ArrayList<Node> biDirectionalLinks) {
        this.biDirectionalLinks = biDirectionalLinks;
    }
    /**
     * Sets a new value for the receviedhellofrom list
     * @param receivedHelloFrom - the new list with new values for received hello from
     */
    public void setReceivedHelloFrom(ArrayList<Integer> receivedHelloFrom) {
        this.receivedHelloFrom = receivedHelloFrom;
    }
    /**
     * Returns the boolean value for if this node is an MPR
     * @return
     */
    public Boolean getMPR() {
        return MPR;
    }
    /**
     * Returns the boolean value for if this node is an MPR
     * @param MPR
     */
    public void setMPR(Boolean MPR) {
        this.MPR = MPR;
    }
}
