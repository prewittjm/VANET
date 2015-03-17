import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.util.ArrayList;
import java.io.ByteArrayInputStream;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
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


    public Truck(int id, int portNumber, double speed, double xCoordinate, double yCoordinate, ArrayList<Node> neighborsIn) {
        this.id = id;
        this.portNumber = portNumber;
        this.speed = speed;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.neighbors = neighborsIn;
//        for (Node node: neighborsIn) {
//            neighbors.add(node);
//        }
        this.cacheTable = new CacheTable();
        myExecutor = Executors.newFixedThreadPool(50);
        sequenceNumber = 0;
    }

    public Truck(Node nodeIn) {
        this.id = nodeIn.getNodeID();
        this.xCoordinate = nodeIn.getxCoordinate();
        this.yCoordinate = nodeIn.getyCoordinate();
        this.portNumber = nodeIn.getPortNumber();
        this.hostname = nodeIn.getHostname();
        this.neighbors = nodeIn.getLinks();
//        for (Node node : nodeIn.getLinks()) {
//            neighbors.add(node);
//        }
        this.cacheTable = new CacheTable();
        myExecutor = Executors.newFixedThreadPool(50);
        sequenceNumber = 0;
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

    public int increaseSequenceNumber(){
        sequenceNumber++;
        return sequenceNumber;
    }

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

        System.out.println("Received packet from " + myPacket.getPreviousHop() + "\nWith source: " + myPacket.getSourceNode());

        if (nodeID != sourceNodeID){
            int cacheSequenceNum = this.cacheTable.checkForSequenceNumber(Integer.toString(sourceNodeID));

            if (cacheSequenceNum < sequenceNum) {
                this.cacheTable.addNewEntryToTable(Integer.toString(sourceNodeID), sequenceNum);
            }
            else if (cacheSequenceNum == sequenceNum) {
                int broadcastNum = cacheTable.getNumberOfBroadcasts((Integer.toString(sourceNodeID)));
                if (Calculations.retransmissionRate(broadcastNum)){

                }
            }
        }
    }

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


