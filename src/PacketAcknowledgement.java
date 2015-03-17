import java.net.*;

/**
 * Created by eddieseay on 3/16/15.
 */
public interface PacketAcknowledgement {
    /**
     * The function called when the server receives a packet
     * @param packetIn - the packet received by the server
     */
    public void receivePacket(DatagramPacket packetIn);
}
