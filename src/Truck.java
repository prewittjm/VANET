import java.util.ArrayList;

/**
 * Created by prewittjm on 3/7/15.
 */
public class Truck implements Vehicle  {
    private int id, portNumber;
    private String hostname;
    private double speed, xCoordinate, yCoordinate;
    private double length = 10.0;
    private double width = 3.0;
    private ArrayList<Node> neighbors;

    public Truck(int id, int portNumber, double speed, double xCoordinate, double yCoordinate) {
        this.id = id;
        this.portNumber = portNumber;
        this.speed = speed;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public Truck(Node nodeIn) {
        this.id = nodeIn.getNodeID();
        this.xCoordinate = nodeIn.getxCoordinate();
        this.yCoordinate = nodeIn.getyCoordinate();
        this.portNumber = nodeIn.getPortNumber();
        this.hostname = nodeIn.getHostname();
        for (Node node : nodeIn.getLinks()) {
            neighbors.add(node);
        }
    }


    @Override
    public void setxCoordinate(double xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    @Override
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public double getyCoordinate() {
        return yCoordinate;
    }

    @Override
    public double getLength() {
        return length;
    }

    @Override
    public double getSpeed() {
        return speed;
    }

    @Override
    public void setyCoordinate(double yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getxCoordinate() {
        return xCoordinate;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getPortNumber() {
        return portNumber;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void setLength(double length) {
        this.length = length;
    }

    @Override
    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }

    @Override
    public void setWidth(double width) {
        this.width = width;
    }

    @Override
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    @Override
    public String getHostname() {
        return hostname;
    }


}

