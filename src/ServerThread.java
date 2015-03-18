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
        while (true) {
        packet = new DatagramPacket(new byte[4096], 4096);
            try {
                socket.receive(packet);
            }
            catch (IOException error) {
                error.getMessage();
            }
            catch(NullPointerException e) {
                e.getMessage();
            }
            packAck.receivePacket(packet);
        }

    }
}
