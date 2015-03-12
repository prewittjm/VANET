/**
 * Creates a car from both a set of numbers and from a Node.
 * Created by prewittjm on 3/7/15.
 */

import java.util.ArrayList;

public class Car implements Vehicle {

    private int portNumber, id;
    private String hostname;
    private double speed, xCoordinate, yCoordinate;
    private ArrayList<Node> neighbors;
    private double length = 5.0;
    private double width = 3.0;

    public Car(int address, int portNumber, double speed, double xCoordinate, double yCoordinate) {
        this.id = address;
        this.portNumber = portNumber;
        this.speed = speed;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }
    public Car(Node nodeIn) {
        this.id = nodeIn.getNodeID();
        this.portNumber = nodeIn.getPortNumber();
        this.yCoordinate = nodeIn.getyCoordinate();
        this.xCoordinate = nodeIn.getxCoordinate();
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
    public String getHostname() {
        return hostname;
    }

    @Override
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    private class ServerThread extends Thread {
        private String name;

        ServerThread(String name){
            this.name = name;
        }

        public void run() {

        }
    }

    private class ClientThread extends Thread {
        private String name;

        ClientThread(String name) {
            this.name = name;
        }

        public void run() {


        }
    }

    public boolean packetForwarding(Packet packet) {
        if (this.id == packet.getId()) {
            return false;
        }
        else {
            return false;
            //Look up the source address in CacheTable
        }

    }

}
