import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created by prewittjm on 3/12/15.
 */
public class ServerThread {
    private int portNum;


    ServerThread(int portNum) {
    this.portNum = portNum;
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
        //TODO Put callback function in here that walter was talking about that will take the packet and process it

        }

    }
}
