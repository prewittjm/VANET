import java.net.*;

/**
 * Created by eddieseay on 3/16/15.
 */
public interface PacketAcknowledgement {
    public void receivePacket(DatagramPacket packetIn);
}
