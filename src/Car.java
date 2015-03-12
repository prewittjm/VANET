/**
 * Created by prewittjm on 3/7/15.
 */

import java.net.*;

public class Car implements Vehicle {

    private int address, portNumber;
    private double speed, xCoordinate, yCoordinate;
    private double length = 5.0;
    private double width = 3.0;

    public Car(int address, int portNumber, double speed, double xCoordinate, double yCoordinate) {
        this.address = address;
        this.portNumber = portNumber;
        this.speed = speed;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
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
    public int getAddress() {
        return address;
    }

    @Override
    public int getPortNumber() {
        return portNumber;
    }

    @Override
    public void setAddress(int address) {
        this.address = address;
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
        if (this.address == packet.getSourceAddress()) {
            return false;
        }
        else {
            return false;
            //Look up the source address in CacheTable
        }

    }

}
