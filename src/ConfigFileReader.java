import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * This class will read the config file into a list. This list contains each node found in the config file.
 * Created by prewittjm on 3/11/15.
 */
public class ConfigFileReader {


    private static List<Node> nodeList;

    /**
     * Reads the config file list and makes a new node with each line
     * @param filename - the filename that is entered into the program
     * @return - static list of nodes created from the file
     * @throws IOException
     */
    public static List<Node> readEntireFile(String filename) throws IOException {
        File fileIn = new File(filename);
        Scanner fileScan = new Scanner(fileIn);
        Scanner lineScan;

        List<String> lines = new ArrayList<String>();
        List<Node> currentNodes = new ArrayList<Node>();
        Map<Integer, List<Integer>> linksForNode = new HashMap<Integer, List<Integer>>();
        Map<Integer, Node> idForNode = new HashMap<Integer, Node>();
        while (fileScan.hasNextLine()) {
            // currentLine = fileScan.nextLine();
            lines.add(fileScan.nextLine());
        }

        Node newNode;
        for (String currLine : lines) {
            int id;
            String hostname;
            int portnum;
            double x;
            double y;
            List<Integer> links = new ArrayList<Integer>();
            lineScan = new Scanner(currLine);
            lineScan.next(); // Ignored because "Node" in the config file has no use
            id = lineScan.nextInt();
            do {
                hostname = lineScan.next().trim();
            } while (hostname.indexOf("tux") < 0);
            hostname = hostname.substring(0, 6);//Eliminates the "," from the hostname
            portnum = lineScan.nextInt();
            x = lineScan.nextDouble();
            y = lineScan.nextDouble();
            lineScan.next();
            while (lineScan.hasNext()){
                links.add(lineScan.nextInt());
            }
            newNode = new Node(id, hostname, portnum, x, y);
            currentNodes.add(newNode);
            linksForNode.put(id, links);
            idForNode.put(id, newNode);
            lineScan.close();
        }
        for (Node node : currentNodes) {
            for (Integer linkIn : linksForNode.get(node.getNodeID())) {
                node.addNewLink(idForNode.get(linkIn));
            }
        }
        fileScan.close();

        nodeList = currentNodes;
        return nodeList;

    }
}
