
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.util.ArrayList;
import java.io.ByteArrayInputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Creates a truck either from a set of data or an existing node
 * Created by prewittjm on 3/7/15.
 */
public class Truck implements Vehicle, PacketAcknowledgement  {
    private int id, portNumber;
    private String hostname;
    private double speed, xCoordinate, yCoordinate;
    private double length = 10.0;
    private double width = 3.0;
    private ArrayList<Node> neighbors;
    private CacheTable cacheTable;
    private ExecutorService myExecutor;
    private int sequenceNumber;
    private int packetsSent;
    private int packetsLost;

    /**
     * Creates a truck from a given set of data
     * @param id - the id of the truck
     * @param portNumber - the port number of the truck
     * @param speed - the speed of the truck
     * @param xCoordinate - the x coordinate of the truck
     * @param yCoordinate - the y coordinate of the truck
     * @param neighborsIn - the neighboring nodes of the truck
     * @param hostname - the hostname of the truck
     */
    public Truck(int id, int portNumber, String hostname, double speed, double xCoordinate, double yCoordinate, ArrayList<Node> neighborsIn) {
        this.id = id;
        this.portNumber = portNumber;
        this.speed = speed;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.neighbors = neighborsIn;
        this.hostname = hostname;
        this.cacheTable = new CacheTable();
        myExecutor = Executors.newFixedThreadPool(50);
        sequenceNumber = 0;
        packetsSent = 0;
        packetsLost = 0;
        ServerThread serverThread = new ServerThread(getPortNumber(), this);
        Broadcaster broadcasterThread = new Broadcaster();
        PacketsThread packetsThread = new PacketsThread();
        serverThread.setDaemon(true);
        broadcasterThread.setDaemon(true);
        packetsThread.setDaemon(true);
        serverThread.start();
        broadcasterThread.start();
        //packetsThread.start();
    }

    /**
     * Creates a truck from a given node
     * @param nodeIn - node which will turn into a truck
     */
    public Truck(Node nodeIn) {
        this.id = nodeIn.getNodeID();
        this.xCoordinate = nodeIn.getxCoordinate();
        this.yCoordinate = nodeIn.getyCoordinate();
        this.portNumber = nodeIn.getPortNumber();
        this.hostname = nodeIn.getHostname();
        this.neighbors = nodeIn.getLinks();
        this.cacheTable = new CacheTable();
        myExecutor = Executors.newFixedThreadPool(50);
        sequenceNumber = 0;
        packetsSent = 0;
        packetsLost = 0;
        this.speed = 35.0;
        ServerThread serverThread = new ServerThread(getPortNumber(), this);
        Broadcaster broadcasterThread = new Broadcaster();
        PacketsThread packetsThread = new PacketsThread();
        serverThread.setDaemon(true);
        broadcasterThread.setDaemon(true);
        packetsThread.setDaemon(true);
        serverThread.start();
        broadcasterThread.start();
        //packetsThread.start();
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

    @Override
    public void setNeighbors(ArrayList<Node> neighbors) {
        this.neighbors = neighbors;
    }

    @Override
    public ArrayList<Node> getNeighbors() {
        return neighbors;
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
        return getPacketsLost() / getTotalNumberOfPackets();
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
        ObjectInputStream inputStream = null;
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
        long currentTime = System.currentTimeMillis();
        long latency = currentTime - myPacket.getCurrentTime();
        System.out.println("-----------------------------");
        System.out.println("Received packet from: " + myPacket.getPreviousHop() + "\nWith source: " + myPacket.getSourceNode()
        + "\nSequence Number: " + myPacket.getSequenceNumber() + "Coordinates - x: " + myPacket.getxCoordinate() + " y: " + myPacket.getyCoordinate()
        + "\nLatency of this packet: " + latency);
        System.out.println("-----------------------------");

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
                //if (true) {
                aPacket.setPreviousHop(this.getId());
                int nVPort = nVehicle.getPortNumber();
                String nVehicleName = nVehicle.getHostname();
                myExecutor.execute(new ClientThread(aPacket, nVehicleName, nVPort));
                packetsSent = packetsSent + 1;
               // System.out.println("Packet Sent!");
            }
            else {
                System.out.println("Lost packet!");
                packetsLost = packetsLost + 1;
            }
        }
    }

    /**
     * This thread constantly sends packets to each neighbor in the neighboring nodes list.
     */
    private class Broadcaster extends Thread {

        @Override
        public void run() {
            while (true) {
                int currentSN = increaseSequenceNumber();
                Packet newPacket = new Packet(currentSN, getHostname(), (int) getId(), (int) getId(), getSpeed(), getxCoordinate(), getyCoordinate(), System.currentTimeMillis());
                sendToNeighboringVehicles(newPacket);
                try {
                    sleep(1000);
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
                    sleep(1000);
                }
                catch (InterruptedException e) {
                    e.getMessage();
                }
                System.out.println("*********************************************************************");
                System.out.println("Total Number of Packets lost from this node: " + getPacketsLost());
                System.out.println("Total Number of Packets sent from this node: " + getTotalNumberOfPackets());
                System.out.println("Packet Lost Rate: " + lostPacketsOverTotal());
                System.out.println("*********************************************************************");

            }


        }


    }
}


