import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;

/**
 * Thread that creates a UDP client for a thread
 * Created by prewittjm on 3/12/15.
 */

public class ClientThread extends Thread {

    private Packet packet;
    private String hostname;
    private int port;
    ClientThread(Packet packet, String hostname, int port) {
        this.packet = packet;
        this.hostname = hostname;
        this.port = port;
    }

    /**
     * Overrides the run() method in the Thread class. Creates a new client socket and
     * sends the packet to the given IP address and port.
     */
    @Override
    public void run() {
        InetAddress ipAddress = null;
        DatagramSocket socket = null;
        DatagramPacket packet1;

        try {
            ipAddress = InetAddress.getByName(hostname);
        } catch (UnknownHostException error) {
            error.getMessage();
        }
        try {
            socket = new DatagramSocket(0);
        } catch (SocketException error) {
            error.getMessage();
        }
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream outputStream;
        byte[] bytePacket;
        try {
            outputStream = new ObjectOutputStream(byteOut);
            outputStream.writeObject(packet);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        bytePacket = byteOut.toByteArray();
        try {
            byteOut.close();
        } catch (IOException e) {
            e.getMessage();
        }
        packet1 = new DatagramPacket(bytePacket, bytePacket.length,ipAddress,port);

        try {
            socket.send(packet1);
        }
        catch (IOException error) {
            error.getMessage();
        }

    }
}
