import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created by prewittjm on 3/12/15.
 */
public class ServerThread {
    private int portNum;
    private PacketAcknowledgement packAck;

    ServerThread(int portNum, PacketAcknowledgement packIn) {
        this.portNum = portNum;
        this.packAck = packIn;
    }

    public void run() {
        DatagramPacket packet = null;
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
            packAck.receivePacket(packet);
        }

    }
}
