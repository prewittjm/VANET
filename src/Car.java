/**
 * Creates a car from both a set of numbers and from a Node.
 * Created by prewittjm on 3/7/15.
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Car implements Vehicle, PacketAcknowledgement {

    private int portNumber, id;
    private String hostname;
    private double speed, xCoordinate, yCoordinate;
    private ArrayList<Node> neighbors;
    private double length = 5.0;
    private double width = 3.0;
    private CacheTable cacheTable;
    private ExecutorService myExecutor;
    private int sequenceNumber;

    /**
     * Creates a car from different parameters
     * @param address - The id of the node
     * @param portNumber - the portnumber used for this node
     * @param speed - the speed the car travels
     * @param xCoordinate - the x coordinate of the car
     * @param yCoordinate - the y coordinate of the car
     * @param neighborsIn - the neighboring nodes of the car
     * @param hostname - the hostname this car uses to run the program on
     */
    public Car(int address, int portNumber, String hostname, double speed, double xCoordinate, double yCoordinate, ArrayList<Node> neighborsIn) {
        this.id = address;
        this.portNumber = portNumber;
        this.speed = speed;
        this.hostname = hostname;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
//        for (Node node : neighborsIn) {
//            neighbors.add(node);
//        }
        cacheTable = new CacheTable();
        this.sequenceNumber = 0;
        myExecutor = Executors.newFixedThreadPool(50);
        this.neighbors = neighborsIn;
    }

    /**
     * Creates a car using a node as input
     * @param nodeIn - the node where the car data comes from
     */
    public Car(Node nodeIn) {
        this.id = nodeIn.getNodeID();
        this.portNumber = nodeIn.getPortNumber();
        this.yCoordinate = nodeIn.getyCoordinate();
        this.xCoordinate = nodeIn.getxCoordinate();
        this.hostname = nodeIn.getHostname();
//        for (Node node : nodeIn.getLinks()) {
//            neighbors.add(node);
//        }
        cacheTable = new CacheTable();
        this.sequenceNumber = 0;
        myExecutor = Executors.newFixedThreadPool(50);
        this.neighbors = getNeighbors();
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
    public void setNeighbors(ArrayList<Node> neighborsIn) {
        neighbors = neighborsIn;
    }

    @Override
    public ArrayList<Node> getNeighbors() {
        return neighbors;
    }

    @Override
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    /**
     * Increases the sequence number of the packet each time a new packet is sent
     * @return - the new value of the sequence number
     */
    public int increaseSequenceNumber(){
        sequenceNumber++;
        return sequenceNumber;
    }

    /**
     * Overriden method from the PacketAcknowledgement Interface. Will be called every time a packed is
     * received by the server. Will take the packet and determine if should be retransmitted based on
     * the RBA algorithm.
     * @param packetIn - the packet received by the server
     */
    @Override
    public void receivePacket(DatagramPacket packetIn) {
        ByteArrayInputStream in = new ByteArrayInputStream(packetIn.getData());
        ObjectInputStream inputStream;
        Packet myPacket = null;
        try {
            inputStream = new ObjectInputStream(in);
            try {
                myPacket = (Packet) inputStream.readObject();
                inputStream.close();
                in.close();
            }
            catch (ClassNotFoundException e) {
                e.getMessage();
            }
        }
        catch (IOException e) {
            e.getMessage();
        }

        int sequenceNum = myPacket.getSequenceNumber();
        int nodeID = this.getId();
        int sourceNodeID = myPacket.getId();

        System.out.println("Received packet from " + myPacket.getPreviousHop() + "\nWith source: " + myPacket.getSourceNode());

        if (nodeID != sourceNodeID){
            int cacheSequenceNum = this.cacheTable.checkForSequenceNumber(Integer.toString(sourceNodeID));

            if (cacheSequenceNum < sequenceNum) {
                this.cacheTable.addNewEntryToTable(Integer.toString(sourceNodeID), sequenceNum);
            }
            else if (cacheSequenceNum == sequenceNum) {
                int broadcastNum = cacheTable.getNumberOfBroadcasts((Integer.toString(sourceNodeID)));
                if (Calculations.retransmissionRate(broadcastNum)){
                    sendToNeighboringVehicles(myPacket);
                }
            }
        }
    }
    /**
     * Takes a packet and sends it to each neighbor in the neighboring nodes list. Only sends if the packet is not determined
     * to be lost using the calculation.
     * @param aPacket - packet being forwarded to all the nodes
     */
    public void sendToNeighboringVehicles(Packet aPacket) {
        for (Node nVehicle : getNeighbors()){
            cacheTable.increaseNumberOfBroadcasts(Integer.toString(nVehicle.getNodeID()));
            double nVX = nVehicle.getxCoordinate();
            double nVY = nVehicle.getyCoordinate();

            if (Calculations.isPacketLostBetweenPoints(this.getxCoordinate(),this.getyCoordinate(), nVX, nVY)) {
                aPacket.setPreviousHop(this.getId());
                int nVPort = nVehicle.getPortNumber();
                String nVehicleName = nVehicle.getHostname();
                myExecutor.execute(new ClientThread(aPacket, nVehicleName, nVPort));
            }
            else {
                System.out.println("Lost packet!");
            }
        }
    }

    /**
     * This thread constantly sends packets to the neighboring nodes
     */
    public class Broadcaster extends Thread {
        @Override
        public void run() {
            while (true) {
                int currentSN = increaseSequenceNumber();
                Packet newPacket = new Packet(currentSN, getHostname(), (int) getId(), (int) getId(), 69, getxCoordinate(), getyCoordinate());
                sendToNeighboringVehicles(newPacket);
                try {
                    sleep(10);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
