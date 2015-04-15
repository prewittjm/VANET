import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by prewittjm on 3/10/15.
 */
public class Node {
    private int nodeID, portNumber;
    private double xCoordinate, yCoordinate, speed;
    private String hostname;
    private ArrayList<Node> links;
    private boolean isInRoadTrain;

    /**
     * Constructor that creates a new Node. The node will be created when the config file is first read. This way it will be
     * easier to read in the data and manipulate it to our liking.
     * @param nodeID - the unique node name for the node.
     * @param hostname - will be translated into an ip address for the vehicle to use when sending packets
     * @param portNumber - will be used when sending packets to each node
     * @param xCoordinate - the x coordinate for the location of the node
     * @param yCoordinate - the y coordinate for the location of the node
     */
    public Node(int nodeID, String hostname, int portNumber, double xCoordinate, double yCoordinate) {

        this.nodeID = nodeID;
        this.hostname = hostname;
        this.portNumber = portNumber;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        links = new ArrayList<Node>();
        isInRoadTrain = false;
        speed = 0;
    }

    /**
     * Sets a new port number for the node
     * @param portNumber - the new port number of the node
     */
    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }

    /**
     * Sets a new nodeID for the node
     * @param nodeID - the new nodeID for the node
     */
    public void setNodeID(int nodeID) {
        this.nodeID = nodeID;
    }

    /**
     * Sets a new xCoordinate for the location of the node
     * @param xCoordinate - the new x coordinate for the node
     */
    public void setxCoordinate(double xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    /**
     * Sets a new yCoordinate for the location of the node
     * @param yCoordinate - the new y coordinate for the node
     */
    public void setyCoordinate(double yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    /**
     * Sets a new Hostname for the node
     * @param hostname - the new hostname for the node
     */
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    /**
     * Returns the x coordinate of the node
     * @return - xCoordinate
     */
    public double getxCoordinate() {
        return xCoordinate;
    }

    /**
     * Returns the y coordinate of the node
     * @return yCoordinate
     */
    public double getyCoordinate() {
        return yCoordinate;
    }

    /**
     * Returns the portnumber of the node
     * @return - portNumber
     */
    public int getPortNumber() {
        return portNumber;
    }

    /**
     * Returns the hostname of the node
     * @return - hostname
     */
    public String getHostname() {
        return hostname;
    }

    /**
     * Returns the nodeID
     * @return - nodeID
     */
    public int getNodeID() {
        return nodeID;
    }

    public boolean getIsInRoadTrain() {
        return isInRoadTrain;
    }

    public void setInRoadTrain(boolean isInRoadTrain) {
        this.isInRoadTrain = isInRoadTrain;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
    /**
     * Returns a boolean value answering if a new node can be added to the list of nodes
     * @param nodeIn - the new node wanting to be added
     * @return - true if node can be added, false if the node cannot be added
     */
    public boolean addNewLink(Node nodeIn) {
        int linkInID = nodeIn.getNodeID();
        for (Node linkId : links) {
            if (this.nodeID == linkInID) {
                return false;
            }
        }
        this.links.add(nodeIn);
        return true;
    }

    /**
     * Returns a boolean value answering if a node can be removed from the list
     * @param nodeIn - node wanting to be removed
     * @return True if removed, false if not
     */
    public boolean removeLink(Node nodeIn) {
        return links.remove(nodeIn);
    }
    public ArrayList<Node> getLinks() {
        return links;
    }
    /**
     * Overrides the toString class so a String can be returned
     * @return String representing the Node
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Node");
        sb.append(" ");
        sb.append(this.nodeID);
        sb.append(" ");
        sb.append(this.hostname);
        sb.append(", ");
        sb.append(this.portNumber);
        sb.append(" ");
        sb.append(this.xCoordinate);
        sb.append(" ");
        sb.append(this.yCoordinate);
        sb.append(" ");
        sb.append("links");
        for (Node link : this.links) {
            sb.append(" ");
            sb.append(link.getNodeID());
        }
        return sb.toString();
    }
}
