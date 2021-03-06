/**
 * Creates a car from both a set of numbers and from a Node.
 * Created by prewittjm on 3/7/15.
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class Car implements Vehicle, PacketAcknowledgement {

    private int portNumber, myID;
    private String hostname;
    private double speed, xCoordinate, yCoordinate;
    private ArrayList<Node> neighbors;
    private ArrayList<Node> biDirectionalLinks;
    private ArrayList<Integer> receivedHelloFrom;
    private double length = 5.0;
    private double width = 3.0;
    private CacheTable cacheTable;
    private ExecutorService myExecutor;
    private HelloTable helloTable;
    private int sequenceNumber, helloSequenceNum, packetsLost, packetsSent;

    private boolean ifInRoadTrain, MPR;
    int receivePrintCounter = 0;
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
//    public Car(int address, int portNumber, String hostname, double speed, double xCoordinate, double yCoordinate, ArrayList<Node> neighborsIn) {
//        this.myID = address;
//        this.portNumber = portNumber;
//        this.speed = speed;
//        this.hostname = hostname;
//        this.xCoordinate = xCoordinate;
//        this.yCoordinate = yCoordinate;
//        cacheTable = new CacheTable();
//        this.sequenceNumber = 0;
//        packetsSent = 0;
//        packetsLost = 0;
//        myExecutor = Executors.newFixedThreadPool(50);
//        this.neighbors = neighborsIn;
//        this.ifInRoadTrain = false;
//        ServerThread serverThread = new ServerThread(getPortNumber(), this);
//        Broadcaster broadcasterThread = new Broadcaster();
//        PacketsThread packetsThread = new PacketsThread();
//        UpdateLocation updateThread = new UpdateLocation();
//        serverThread.setDaemon(true);
//        broadcasterThread.setDaemon(true);
//        packetsThread.setDaemon(true);
//        updateThread.setDaemon(true);
//        serverThread.start();
//        broadcasterThread.start();
//        packetsThread.start();
//        updateThread.start();
//    }

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
        helloTable = new HelloTable();
        this.sequenceNumber = 0;
        this.helloSequenceNum = 0;
        this.MPR = false;
        packetsSent = 0;
        packetsLost = 0;
        this.speed = 30.0;
        this.ifInRoadTrain = false;
        this.biDirectionalLinks = new ArrayList<Node>();
        this.receivedHelloFrom = new ArrayList<Integer>();
        myExecutor = Executors.newFixedThreadPool(50);
        this.neighbors = nodeIn.getLinks();
        ServerThread serverThread = new ServerThread(getPortNumber(), this);
        Broadcaster broadcasterThread = new Broadcaster();
        PacketsThread packetsThread = new PacketsThread();
        UpdateLocation updateThread = new UpdateLocation();
        AttemptRoadTrainThread roadTrainThread = new AttemptRoadTrainThread();
        HelloPacketBroadcast helloThread = new HelloPacketBroadcast();
        serverThread.setDaemon(true);
        broadcasterThread.setDaemon(true);
        packetsThread.setDaemon(true);
        updateThread.setDaemon(true);
        roadTrainThread.setDaemon(true);
        helloThread.setDaemon(true);
        serverThread.start();
        broadcasterThread.start();
        packetsThread.start();
        updateThread.start();
        roadTrainThread.start();
        helloThread.start();
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
    public void setIfInRoadTrain(boolean inRoadTrain) {
        this.ifInRoadTrain = inRoadTrain;
    }

    @Override
    public boolean getIfInRoadTrain() {
        return ifInRoadTrain;
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
    /**
     * Increases packets lost
     * @return current amount of packets lost
     */
    public int increasePacketLost() {
        packetsLost++;
        return packetsLost;
    }
    /**
     * Increases packets sent
     * @return current amount of packets sent
     */
    public int increasePacketSent() {
        packetsSent++;
        return packetsSent;
    }
    /**
     * Increases the sequence number of the hello packets
     * @return - the current sequence number of the hello packet
     */
    public int increaseHelloSeqNum() {
        helloSequenceNum ++;
        return helloSequenceNum;
    }

    /**
     * Refactored into a function for readability and better functionality. Takes in a packet and updates the coordinates
     * Only prints out every 100 packets received
     * @param packetIn - the packet being processed
     * @param sequenceNumberIn - current sequence number
     * @param sourceNodeIDIn - the id of the source node from the packet
     */
    public void processPacket(Packet packetIn, int sequenceNumberIn, int sourceNodeIDIn) {

        int nodeID = this.getMyID();

        for (Node node : neighbors) {
            if (node.getNodeID() == packetIn.getPreviousHop()) {
                node.setxCoordinate(packetIn.getxCoordinate());
                node.setyCoordinate(packetIn.getyCoordinate());
//        		System.out.println("*****UPDATED NODE " + sourceNodeID + "*****\n" +
//        				"X: " + node.getxCoordinate()+"\nY: " + node.getyCoordinate());
            }
        }
        long currentTime;
        currentTime = System.currentTimeMillis();
        long latency = currentTime - packetIn.getCurrentTime();
        if (receivePrintCounter == 100) {
            receivePrintCounter = 0;
            System.out.println("-----------------------------");
            System.out.println("Received packet from: " + packetIn.getPreviousHop() + "\nWith source: " + packetIn.getSourceNode()
                    + "\nSequence Number: " + packetIn.getSequenceNumber() + "\nCoordinates - x: " + packetIn.getxCoordinate() + " y: " + packetIn.getyCoordinate()
                    + "\nLatency of this packet: " + latency);
            System.out.println("-----------------------------");
        } else {
            receivePrintCounter++;
        }
        if (nodeID != sourceNodeIDIn) {
            int cacheSequenceNum = this.cacheTable.checkForSequenceNumber(Integer.toString(sourceNodeIDIn));

            if (cacheSequenceNum < sequenceNumberIn) {
                this.cacheTable.addNewEntryToTable(Integer.toString(sourceNodeIDIn), sequenceNumberIn);
            } else if (cacheSequenceNum == sequenceNumberIn) {
                int broadcastNum = cacheTable.getNumberOfBroadcasts((Integer.toString(sourceNodeIDIn)));
                if (Calculations.retransmissionRate(broadcastNum)) {
                    sendToNeighboringVehicles(packetIn);//TODO: Need to fix to only forward if MPR
                }
            }
        }
    }

    /**
     * Refactored into a function for readability and better functionality. Takes in a packet and updates the coordinates
     * Only prints out every 100 packets received. Only called if the packet type is one of a node in the road train
     * @param packetIn - the packet being processed
     * @param sequenceNumberIn - current sequence number
     * @param sourceNodeIDIn - the id of the source node from the packet
     */
    public void processPacketWRoadtrain(Packet packetIn, int sequenceNumberIn, int sourceNodeIDIn) {
        int nodeID = this.getMyID();

        for (Node node : neighbors) {
            if (node.getNodeID() == packetIn.getPreviousHop()) {
                node.setxCoordinate(packetIn.getxCoordinate());
                node.setyCoordinate(packetIn.getyCoordinate());
//        		System.out.println("*****UPDATED NODE " + sourceNodeID + "*****\n" +
//        				"X: " + node.getxCoordinate()+"\nY: " + node.getyCoordinate());
                if (!node.getIsInRoadTrain()){
                    node.setInRoadTrain(true);
                }
            }
        }
        long currentTime;
        currentTime = System.currentTimeMillis();
        long latency = currentTime - packetIn.getCurrentTime();
        if (receivePrintCounter == 100) {
            receivePrintCounter = 0;
            System.out.println("-----------------------------");
            System.out.println("Received packet from: " + packetIn.getPreviousHop() + "\nWith source: " + packetIn.getSourceNode()
                    + "\nSequence Number: " + packetIn.getSequenceNumber() + "\nCoordinates - x: " + packetIn.getxCoordinate() + " y: " + packetIn.getyCoordinate()
                    + "\nLatency of this packet: " + latency);
            System.out.println("-----------------------------");
        } else {
            receivePrintCounter++;
        }
        if (nodeID != sourceNodeIDIn) {
            int cacheSequenceNum = this.cacheTable.checkForSequenceNumber(Integer.toString(sourceNodeIDIn));

            if (cacheSequenceNum < sequenceNumberIn) {
                this.cacheTable.addNewEntryToTable(Integer.toString(sourceNodeIDIn), sequenceNumberIn);
            } else if (cacheSequenceNum == sequenceNumberIn) {
                int broadcastNum = cacheTable.getNumberOfBroadcasts((Integer.toString(sourceNodeIDIn)));
                if (Calculations.retransmissionRate(broadcastNum)) {
                    sendToNeighboringVehicles(packetIn);//TODO: Need to fix to only forward if MPR
                }
            }
        }
    }

    /**
     * Processes the hello packets from the neighbors
     * @param packetIn - the hello packet
     */
    public void processHelloPacket(Packet packetIn) {
        System.out.println("Received Hello Packet");
        int nodeID = this.getMyID();
        if (!receivedHelloFrom.contains(packetIn.getId())) {
            receivedHelloFrom.add(packetIn.getId());
        }

        for (Node node : neighbors) {
            if (node.getNodeID() == packetIn.getId() && receivedHelloFrom.contains(packetIn.getId())) {
                biDirectionalLinks.add(node);
            }
        }

        if (helloTable.checkForNode(packetIn.getId())) {
            helloTable.updateLinks(packetIn.getId(), packetIn.getBiDirectionalLinks());
            for (Node node : biDirectionalLinks) {
                if (node.getNodeID() == packetIn.getId()) {
                    helloTable.updateStatus(node.getNodeID(), 1);
                }
            }
        }
        else {
            helloTable.addNewEntryToHelloTable(packetIn.getId(), 0, packetIn.getBiDirectionalLinks());
        }
    }

    /**
     * Decelerates the car in order to fit into the road train
     * @param distanceRequired - the distance required between this car and the car in front of it
     * @param nodeToFollow - the car this car wants to follow
     */
    public void decelerate(double distanceRequired, Node nodeToFollow) {
        double currentDistanceAway = Calculations.distanceBetweenPoints(getxCoordinate(),getyCoordinate(),nodeToFollow.getxCoordinate(),nodeToFollow.getyCoordinate());
        while (currentDistanceAway < distanceRequired) {
            setSpeed(this.speed--);
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        setSpeed(30.0);

    }
    /**
     * Accelerates the car in order to fit into the road train
     * @param distanceRequired - the distance required between this car and the car in front of it
     * @param nodeToFollow - the car this car wants to follow
     */
    public void accelerate(double distanceRequired, Node nodeToFollow) {
        double currentDistanceAway = Calculations.distanceBetweenPoints(getxCoordinate(),getyCoordinate(),nodeToFollow.getxCoordinate(),nodeToFollow.getyCoordinate());
        while (currentDistanceAway > distanceRequired) {
            setSpeed(this.speed++);
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        setSpeed(30.0);
    }

    /**
     * The function that ultimately joins this car into the road train. Will go through each neighbor to determine the
     * car/truck that is optimal to follow in the road train. Tests to see if the car is in front or behind then
     * figures out if the car in front is already in the roadtrain. If not then it will not attempt to join.
     * @return - true if able to join the train, false if not
     */
    public void joinRoadTrain() {
        Node nodeToGetBehind = null;
        double distance = Double.MAX_VALUE;
        boolean plausibleNode = false;
        for (Node node : neighbors) {
           Double newDistance = Calculations.distanceBetweenPoints(getxCoordinate(),getyCoordinate(),node.getxCoordinate(),node.getyCoordinate());
            if (getxCoordinate() < node.getxCoordinate() && newDistance < distance) {
                distance = newDistance;
                nodeToGetBehind = node;
                plausibleNode = true;
            }
            if (getxCoordinate() > node.getxCoordinate() && !plausibleNode) {
                nodeToGetBehind = node;
            }
        }
        if (plausibleNode && nodeToGetBehind.getIsInRoadTrain()) {
            if (getyCoordinate() != nodeToGetBehind.getyCoordinate()) {
                this.setyCoordinate(nodeToGetBehind.getyCoordinate());
            }

            if (Calculations.distanceBetweenX(getxCoordinate(), nodeToGetBehind.getxCoordinate()) < 10.0) {
                decelerate(30.0, nodeToGetBehind);
                this.setIfInRoadTrain(true);

            } else if (Calculations.distanceBetweenX(getxCoordinate(), nodeToGetBehind.getxCoordinate()) > 20.0) {
                accelerate(30.0, nodeToGetBehind);
                this.setIfInRoadTrain(true);
            }
            System.out.println("Joined Road Train!");
        }
        else {
            System.out.println("Cannot join road train");
        }
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
            } catch (ClassNotFoundException e) {
                e.getMessage();
            }
        } catch (IOException e) {
            e.getMessage();
        }

        assert myPacket != null;
        int packetType = myPacket.getPacketType();
        if (packetType == 0) {
        processHelloPacket(myPacket);
        }
        else {
            int sequenceNum = myPacket.getSequenceNumber();
            int sourceNodeID = myPacket.getId();
            int toNodeID = myPacket.getIdTo();
            if ((packetType == 1) || (packetType == 2)) {
                processPacket(myPacket, sequenceNum, sourceNodeID);
//            int nodeID = this.getMyID();
//            for (Node node : neighbors) {
//                if (node.getNodeID() == sourceNodeID) {
//                    node.setxCoordinate(myPacket.getxCoordinate());
//                    node.setyCoordinate(myPacket.getyCoordinate());
////        		System.out.println("*****UPDATED NODE " + sourceNodeID + "*****\n" +
////        				"X: " + node.getxCoordinate()+"\nY: " + node.getyCoordinate());
//                }
//            }
//            long currentTime;
//            currentTime = System.currentTimeMillis();
//            long latency = currentTime - myPacket.getCurrentTime();
//            if (receivePrintCounter == 100) {
//                receivePrintCounter = 0;
//                System.out.println("-----------------------------");
//                System.out.println("Received packet from: " + myPacket.getPreviousHop() + "\nWith source: " + myPacket.getSourceNode()
//                        + "\nSequence Number: " + myPacket.getSequenceNumber() + "\nCoordinates - x: " + myPacket.getxCoordinate() + " y: " + myPacket.getyCoordinate()
//                        + "\nLatency of this packet: " + latency);
//            } else {
//                receivePrintCounter++;
//            }
//            if (nodeID != sourceNodeID) {
//                int cacheSequenceNum = this.cacheTable.checkForSequenceNumber(Integer.toString(sourceNodeID));
//
//                if (cacheSequenceNum < sequenceNum) {
//                    this.cacheTable.addNewEntryToTable(Integer.toString(sourceNodeID), sequenceNum);
//                } else if (cacheSequenceNum == sequenceNum) {
//                    int broadcastNum = cacheTable.getNumberOfBroadcasts((Integer.toString(sourceNodeID)));
//                    if (Calculations.retransmissionRate(broadcastNum)) {
//                        sendToNeighboringVehicles(myPacket);
//                    }
//                }
//            }
            } else if (packetType == 4) {

                processPacketWRoadtrain(myPacket, sequenceNum, sourceNodeID);
//            int nodeID = this.getMyID();
//            for (Node node : neighbors) {
//                if (node.getNodeID() == sourceNodeID) {
//                    node.setxCoordinate(myPacket.getxCoordinate());
//                    node.setyCoordinate(myPacket.getyCoordinate());
//                    if (!node.getIsInRoadTrain()){
//                        node.setInRoadTrain(true);
//                    }
////        		System.out.println("*****UPDATED NODE " + sourceNodeID + "*****\n" +
////        				"X: " + node.getxCoordinate()+"\nY: " + node.getyCoordinate());
//                }
//            }
//            long currentTime;
//            currentTime = System.currentTimeMillis();
//            long latency = currentTime - myPacket.getCurrentTime();
//            if (receivePrintCounter == 100) {
//                receivePrintCounter = 0;
//                System.out.println("-----------------------------");
//                System.out.println("Received packet from: " + myPacket.getPreviousHop() + "\nWith source: " + myPacket.getSourceNode()
//                        + "\nSequence Number: " + myPacket.getSequenceNumber() + "\nCoordinates - x: " + myPacket.getxCoordinate() + " y: " + myPacket.getyCoordinate()
//                        + "\nLatency of this packet: " + latency);
//            } else {
//                receivePrintCounter++;
//            }
//            if (nodeID != sourceNodeID) {
//                int cacheSequenceNum = this.cacheTable.checkForSequenceNumber(Integer.toString(sourceNodeID));
//
//                if (cacheSequenceNum < sequenceNum) {
//                    this.cacheTable.addNewEntryToTable(Integer.toString(sourceNodeID), sequenceNum);
//                } else if (cacheSequenceNum == sequenceNum) {
//                    int broadcastNum = cacheTable.getNumberOfBroadcasts((Integer.toString(sourceNodeID)));
//                    if (Calculations.retransmissionRate(broadcastNum)) {
//                        sendToNeighboringVehicles(myPacket);
//                    }
//                }
//            }
            } else if (packetType == 3) {
                processPacket(myPacket, sequenceNum, sourceNodeID);
                int nodeID = this.getMyID();
//                for (Node node : neighbors) {
//                    if (node.getNodeID() == sourceNodeID) {
//                        node.setxCoordinate(myPacket.getxCoordinate());
//                        node.setyCoordinate(myPacket.getyCoordinate());
////        		System.out.println("*****UPDATED NODE " + sourceNodeID + "*****\n" +
////        				"X: " + node.getxCoordinate()+"\nY: " + node.getyCoordinate());
//                    }
//                }
//                long currentTime;
//                currentTime = System.currentTimeMillis();
//                long latency = currentTime - myPacket.getCurrentTime();
//                if (receivePrintCounter == 100) {
//                    receivePrintCounter = 0;
//                    System.out.println("-----------------------------");
//                    System.out.println("Received packet from: " + myPacket.getPreviousHop() + "\nWith source: " + myPacket.getSourceNode()
//                            + "\nSequence Number: " + myPacket.getSequenceNumber() + "\nCoordinates - x: " + myPacket.getxCoordinate() + " y: " + myPacket.getyCoordinate()
//                            + "\nLatency of this packet: " + latency);
//                } else {
//                    receivePrintCounter++;
//                }
//                if (nodeID != sourceNodeID) {
//                    int cacheSequenceNum = this.cacheTable.checkForSequenceNumber(Integer.toString(sourceNodeID));
//
//                    if (cacheSequenceNum < sequenceNum) {
//                        this.cacheTable.addNewEntryToTable(Integer.toString(sourceNodeID), sequenceNum);
//                    } else if (cacheSequenceNum == sequenceNum) {
//                        int broadcastNum = cacheTable.getNumberOfBroadcasts((Integer.toString(sourceNodeID)));
//                        if (Calculations.retransmissionRate(broadcastNum)) {
//                            sendToNeighboringVehicles(myPacket);
//                        }
//                    }
//                }
                if (nodeID == toNodeID) {
                    joinRoadTrain();
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
                if (!ifInRoadTrain) {
                    Packet newPacket = new Packet(currentSN, getHostname(), getPortNumber(), getMyID(), getMyID(), getSpeed(), getxCoordinate(), getyCoordinate(),
                            System.currentTimeMillis(), 1);
                    sendToNeighboringVehicles(newPacket);
                }
                else {
                    Packet newPacket = new Packet(currentSN, getHostname(), getPortNumber(), getMyID(), getMyID(), getSpeed(), getxCoordinate(), getyCoordinate(),
                            System.currentTimeMillis(), 4);
                    sendToNeighboringVehicles(newPacket);
                }
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
    /**
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
    /**
     * Thread that attempts to join the road train every second
     */
    private class AttemptRoadTrainThread extends Thread {
        @Override
        public void run() {
            System.out.println("Road Train Packet Sent");
            while (!ifInRoadTrain) {
                int currentSN = increaseSequenceNumber();
                Packet newPacket = new Packet(currentSN, getHostname(), getPortNumber(), getMyID(), getMyID(), getSpeed(), getxCoordinate(), getyCoordinate(),
                        System.currentTimeMillis(), 2);
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

    private class HelloPacketBroadcast extends Thread {
        @Override
        public void run() {
             Packet newPacket = new Packet(getMyID(), biDirectionalLinks, receivedHelloFrom);
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
