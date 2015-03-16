import java.util.Hashtable;

/**
 * Class that keeps track of all the packets and number of times they have been retransmitted
 * Created by prewittjm on 3/15/15.
 */
public class CacheTable {
    private Hashtable<String, PacketInfo> cacheTable;

    /**
     * Creates the new CacheTable.
     */
    public CacheTable() {
    cacheTable = new Hashtable<String, PacketInfo>();
    }

    /**
     * Checks the CacheTable for the sequence number of the packet
     * @param hostName - the hostName or nodeId of the source of the packet
     * @return returns the sequenceNumber of the packet if found, otherwise it returns -1
     */
    public int checkForSequenceNumber(String hostName) {
    PacketInfo packetInfo = cacheTable.get(hostName);
    if (packetInfo == null) {
        return -1;
    }
    else {
        return packetInfo.getSequenceNumber();
    }
    }

    /**
     * Checks the number of broadcasts of the latest packet for the hostname entered
     * @param hostName - the hostname of the packet's source
     * @return - number of broadcasts of the specific packet
     */
    public int getNumberOfBroadcasts(String hostName) {
    PacketInfo packetInfo = cacheTable.get(hostName);
    if (packetInfo == null) {
        return 0;
    }
    else {
        return packetInfo.getCurrentNumberOfBroadcasts();
    }

    }

    /**
     *  Increases the number of broadcasts for the specific packet
     * @param hostName - the hostname of the source of the packet
     * @return - the new value of broadcasts for the packet
     */
    public int increaseNumberOfBroadcasts(String hostName) {
        PacketInfo packetInfo = cacheTable.get(hostName);
        if (packetInfo == null) {
            return 0;
        }
        else {
            int currentNum = packetInfo.getCurrentNumberOfBroadcasts();
            int newNum = currentNum + 1;
            packetInfo.setCurrentNumberOfBroadcasts(newNum);
            return newNum;
        }
    }

    /**
     *  Adds a new entry into the cache table
     * @param hostname - the hostname of the source of the packet
     * @param sequenceNumberIn - the sequence number of the new packet
     */
    public void addNewEntryToTable(String hostname, int sequenceNumberIn) {
        int currSeqNum = checkForSequenceNumber(hostname);
        if (currSeqNum == -1) {
            cacheTable.put(hostname, new PacketInfo(sequenceNumberIn, 1));
        }
        if (currSeqNum < sequenceNumberIn) {
            cacheTable.put(hostname, new PacketInfo(sequenceNumberIn, 1));
        }
    }


    /**
     * Private class in the CacheTable class that keeps track of each sequence number and number of broadcasts for
     * that sequence number. This is used in the RBA for retransmissions of the received packets
     */
    private class PacketInfo {

        private int currentNumberOfBroadcasts;
        private int sequenceNumber;

        /**
         * Used as an entry into the hashtable
         * @param sequenceNumberIn - sequence number of the packet being added ot the cacetable
         * @param currentNumberOfBroadcastsIn - number of broadcasts this packet has had
         */
        PacketInfo(int sequenceNumberIn, int currentNumberOfBroadcastsIn) {
            sequenceNumber = sequenceNumberIn;
            currentNumberOfBroadcasts = currentNumberOfBroadcastsIn;
        }

        /**
         * Returns the sequence number of the packet
         * @return - int representing the sequence number of the packet
         */
        public int getSequenceNumber() {
            return sequenceNumber;
        }

        /**
         * Returns the broadcast number of the packet
         * @return - int representing the number of broadcasts for this packet
         */
        public int getCurrentNumberOfBroadcasts() {
            return currentNumberOfBroadcasts;
        }

        /**
         * Sets the new number of broadcasts for the packet
         * @param currentNumberOfBroadcasts - new number of broadcasts for the packet
         */
        public void setCurrentNumberOfBroadcasts(int currentNumberOfBroadcasts) {
            this.currentNumberOfBroadcasts = currentNumberOfBroadcasts;
        }

        /**
         * Sets a new sequence number for the packet
         * @param sequenceNumber - new number for the sequence number of the packet
         */
        public void setSequenceNumber(int sequenceNumber) {
            this.sequenceNumber = sequenceNumber;
        }
    }
}
