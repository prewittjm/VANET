import java.net.*;

/**
 * An interface that must be implemented by the class implementing it. This method will be used to deal with
 * The packet
 * Created by eddieseay on 3/16/15.
 */
public interface PacketAcknowledgement {
    /**
     * The function called when the server receives a packet
     * @param packetIn - the packet received by the server
     */
    public void receivePacket(DatagramPacket packetIn);
}
