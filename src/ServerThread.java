import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Creates a new UDP server socket that waits for a packet from another neighbor
 * Created by prewittjm on 3/12/15.
 */
public class ServerThread extends Thread{
    private int portNum;
    private PacketAcknowledgement packAck;

    ServerThread(int portNum, PacketAcknowledgement packIn) {
        this.portNum = portNum;
        this.packAck = packIn;
    }

    @Override
    public void run() {
        DatagramPacket packet;
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(portNum);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        System.out.println("Server Socket Created");
        System.out.println("-----------Running------------");
        while (true) {
            packet = new DatagramPacket(new byte[4096], 4096);
            System.out.println("Made it here");
            try {
                socket.receive(packet);
                System.out.println("Packet Received!");
//            packAck.receivePacket(packet);
            }
            catch (IOException error) {
                error.getMessage();
            }
            try {
                packAck.receivePacket(packet);
            }
            catch (NullPointerException e) {
                e.getMessage();
//            	System.exit(1);
            }
        }

    }
}
