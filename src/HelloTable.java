import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by prewittjm on 4/25/15.
 */
public class HelloTable {
    private Hashtable<Integer, LinkInfo> helloTable;

    public HelloTable() {
        helloTable = new Hashtable<Integer, LinkInfo>();
    }

    /**
     * Adds a new node to the hello table
     * @param newNode - the new node
     * @param status - the status for the node
     * @param links - the links for the node
     * @return - true if the node can be entered into the hash table, false if the
     */
    public boolean addNewEntryToHelloTable(Integer newNode, int status, ArrayList<Node> links) {
        if (!checkForNode(newNode)) {
            helloTable.put(newNode, new LinkInfo(status, links));
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Updates the node in the table with new links
     * @param newNode - the node the new links are added to
     * @param links - the new links
     * @return - true if the lists were added to the node, false if not
    */
    public boolean updateLinks(Integer newNode, ArrayList<Node> links) {
        LinkInfo linkInfo = helloTable.get(newNode);
        if (linkInfo == null) {
            return false;
        }
        else {
            linkInfo.setLinks(links);
            helloTable.remove(newNode);
            helloTable.put(newNode, linkInfo);
            return true;
        }
    }

    /**
     * Updates the status of the node in the table
     * @param newNode - the node for which the status should be changed
     * @param status - the new status of the node
     * @return - true if the status can be updated
     */
    public boolean updateStatus(Integer newNode, int status) {
        LinkInfo linkInfo = helloTable.get(newNode);
        if (linkInfo == null) {
            return false;
        }
        else {
            linkInfo.setStatus(status);
            helloTable.remove(newNode);
            helloTable.put(newNode, linkInfo);
            return true;
        }


    }

    /**
     * Returns the status of the node
     * @param newNode - the node whose status needs to be returned
     * @return - the status of the node
     */
    public int returnStatus(Integer newNode) {
        LinkInfo linkInfo = helloTable.get(newNode);
        if (linkInfo == null) {
            return -1;
        }
        else {
            return linkInfo.getStatus();
        }

    }

    /**
     * Returns the one hop links of the node
     * @param newNode - the node
     * @return - ArrayList of nodes that are links to the node
     */
    public ArrayList<Node> returnLinks(Integer newNode) {
        LinkInfo linkInfo = helloTable.get(newNode);
        if (linkInfo == null) {
            return null;
        }
        else {
            return linkInfo.getLinks();
        }
    }

    /**
     * Checks to see if the node is in the hash table
     * @param node - the node that is being checked if it is in the hash table
     * @return - true if the node is already in the table, false otherwise
     */
    public boolean checkForNode(Integer node) {
        return helloTable.containsKey(node);
    }

    /**
     * A class to keep up with the LinkInfo of each link
     */
    private class LinkInfo {
        private int status; // 0 = unidirectional, 1 = bidirectional, 2 = MPR
        private ArrayList<Node> links;

        LinkInfo(int status, ArrayList<Node> links) {
            this.status = status;
            this.links = links;
        }

        /**
         * Returns the links of the link, or the two step links of the current car
         * @return - ArrayList of nodes aka the links
         */
        public ArrayList<Node> getLinks() {
            return links;
        }

        /**
         * Returns the link status of the node, 0 = unidirectional, 1 = bidirectional, 2 = MPR
         * @return - the status of the current link
         */
        public int getStatus() {
            return status;
        }

        /**
         * Sets a new set of links for this node
         * @param links - the new list of nodes
         */
        public void setLinks(ArrayList<Node> links) {
            this.links = links;
        }

        /**
         * Sets a new status for the node
         * @param status - the new status for the node
         */
        public void setStatus(int status) {
            this.status = status;
        }
    }





}
