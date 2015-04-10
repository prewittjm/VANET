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

    private int portNumber, myID;
    private String hostname;
    private double speed, xCoordinate, yCoordinate;
    private ArrayList<Node> neighbors;
    private double length = 5.0;
    private double width = 3.0;
    private CacheTable cacheTable;
    private ExecutorService myExecutor;
    private int sequenceNumber;
    private int packetsSent;
    private int packetsLost;
    //private PacketAcknowledgement packetAck;

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
        this.myID = address;
        this.portNumber = portNumber;
        this.speed = speed;
        this.hostname = hostname;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        cacheTable = new CacheTable();
        this.sequenceNumber = 0;
        packetsSent = 0;
        packetsLost = 0;
        myExecutor = Executors.newFixedThreadPool(50);
        this.neighbors = neighborsIn;
        ServerThread serverThread = new ServerThread(getPortNumber(), this);
        Broadcaster broadcasterThread = new Broadcaster();
        PacketsThread packetsThread = new PacketsThread();
        UpdateLocation updateThread = new UpdateLocation();
        serverThread.setDaemon(true);
        broadcasterThread.setDaemon(true);
        packetsThread.setDaemon(true);
        updateThread.setDaemon(true);
        serverThread.start();
        broadcasterThread.start();
        packetsThread.start();
        updateThread.start();
    }

    /**
     * Creates a car using a node as input
     * @param nodeIn - the node where the car data comes from
     */
    public Car(Node nodeIn) {
        this.myID = nodeIn.getNodeID();
        this.portNumber = nodeIn.getPortNumber();
        this.yCoordinate = nodeIn.getyCoordinate();
        this.xCoordinate = nodeIn.getxCoordinate();
        this.hostname = nodeIn.getHostname();
        cacheTable = new CacheTable();
        this.sequenceNumber = 0;
        packetsSent = 0;
        packetsLost = 0;
        this.speed = 30.0;
        myExecutor = Executors.newFixedThreadPool(50);
        this.neighbors = nodeIn.getLinks();
        ServerThread serverThread = new ServerThread(getPortNumber(), this);
        Broadcaster broadcasterThread = new Broadcaster();
        PacketsThread packetsThread = new PacketsThread();
        UpdateLocation updateThread = new UpdateLocation();
        serverThread.setDaemon(true);
        broadcasterThread.setDaemon(true);
        packetsThread.setDaemon(true);
        updateThread.setDaemon(true);
        serverThread.start();
        broadcasterThread.start();
        packetsThread.start();
        updateThread.start();
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
    public int getMyID() {
        return myID;
    }

    @Override
    public int getPortNumber() {
        return portNumber;
    }

    @Override
    public void setId(int id) {
        this.myID = id;
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
     * Returns packets sent
     * @return - number of packets sent
     */
    public int getPacketsSent() {
        return packetsSent;
    }

    /**
     * Returns packets lost
     * @return - number of packets lost
     */
    public int getPacketsLost() {
        return packetsLost;
    }

    /**
     * Returns total number of packets
     * @return - number of total packets
     */
    public int getTotalNumberOfPackets() {
        return getPacketsLost() + getPacketsSent();
    }

    /**
     * Returns packet lost rate
     * @return - packet lost rate
     */
    public double lostPacketsOverTotal() {
        if (getTotalNumberOfPackets() == 0) {
            return 0.0;
        }
        else {
            return (double) packetsLost / ((double) packetsLost + (double) packetsSent);
        }
    }
    public int increasePacketLost() {
        packetsLost++;
        return packetsLost;
    }
    public int increasePacketSent() {
        packetsSent++;
        return packetsSent;
    }
    /**
     * Overriden method from the PacketAcknowledgement Interface. Will be called every time a packed is
     * received by the server. Will take the packet and determine if should be retransmitted based on
     * the RBA algorithm.
     * @param packetIn - the packet received by the server
     */
    int receivePrintCounter = 0;
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
        int nodeID = this.getMyID();
        int sourceNodeID = myPacket.getId();
        for (Node node: neighbors) {
            if (node.getNodeID() == sourceNodeID) {

                node.setxCoordinate(myPacket.getxCoordinate());
                node.setyCoordinate(myPacket.getyCoordinate());
//        		System.out.println("*****UPDATED NODE " + sourceNodeID + "*****\n" +
//        				"X: " + node.getxCoordinate()+"\nY: " + node.getyCoordinate());
            }
        }

        long currentTime = System.currentTimeMillis();
        long latency = (long)(currentTime - myPacket.getCurrentTime());
        if (receivePrintCounter == 100) {
            receivePrintCounter = 0;
            System.out.println("-----------------------------");
            System.out.println("Received packet from: " + myPacket.getPreviousHop() + "\nWith source: " + myPacket.getSourceNode()
                    + "\nSequence Number: " + myPacket.getSequenceNumber() + "\nCoordinates - x: " + myPacket.getxCoordinate() + " y: " + myPacket.getyCoordinate()
                    + "\nLatency of this packet: " + latency);
        }
        else {
            receivePrintCounter++;
        }
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

            //if (true) {
            if (Calculations.isPacketLostBetweenPoints(this.getxCoordinate(),this.getyCoordinate(), nVX, nVY)) {
                aPacket.setPreviousHop(this.getMyID());
                int nVPort = nVehicle.getPortNumber();
                String nVehicleName = nVehicle.getHostname();
                myExecutor.execute(new ClientThread(aPacket, nVehicleName, nVPort));
                increasePacketSent();
            }
            else {
                //System.out.println("Lost packet!");
                increasePacketLost();
            }
        }
    }

    /**
     * This thread constantly sends packets to the neighboring nodes
     */
    private class Broadcaster extends Thread {
        @Override
        public void run() {
            System.out.println("Broadcaster Thread Created");
            while (true) {
                int currentSN = increaseSequenceNumber();
                //System.out.println("ID OF PACKET CREATOR: "+getId());

                Packet newPacket = new Packet(currentSN, getHostname(), (int) getMyID(), (int) getMyID(), getSpeed(), getxCoordinate(), getyCoordinate(), System.currentTimeMillis());
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

    /**
     * This thread will print out total packets lost, packets sent from this node, and packet lost rate.
     */
    private class PacketsThread extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    sleep(10000);
                }
                catch (InterruptedException e) {
                    e.getMessage();
                }
                System.out.println("*********************************************************************");
                System.out.println("Total Number of Packets lost from this node: " + getPacketsLost());
                System.out.println("Total Number of Packets sent from this node: " + getTotalNumberOfPackets());
                System.out.println("Throughput: " + getPacketsSent() / getTotalNumberOfPackets());
                System.out.println("Packet Loss %: " + lostPacketsOverTotal());
                System.out.println("*********************************************************************");

            }


        }


    }
    /*
     * This thread will update the location based on speed
     */
    private class UpdateLocation extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    sleep(1000);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                double newX = getSpeed() + getxCoordinate();
                setxCoordinate(newX);
            }
        }

    }
}
