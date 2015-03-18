/**
 * Creates a GUI showing all the cars
 * Created by prewittjm on 3/12/15.
 */

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import javax.swing.*;


@SuppressWarnings("serial")
public class CarGUI extends JFrame {
private List<Node> nList;

    public CarGUI(List<Node> list) {
        super("Road Train");
        this.nList = list;
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1000, 300);
        setResizable(false);
        getContentPane().setBackground(Color.GRAY);
        setVisible(true);
    }

    public void paint(Graphics graphic) {
        super.paint(graphic);
        graphic.setColor(Color.BLACK);
        graphic.fillRect(0, 100, 1000, 100);
        graphic.setColor(Color.ORANGE);
        graphic.drawLine(0, 150, 1000, 150);
        for(Node node : nList){
            int x= (int)node.getxCoordinate()%1000;
            int y = 300/2 - (int)node.getyCoordinate() + 50;
            graphic.setColor(Color.WHITE);
            graphic.drawString(Integer.toString(node.getNodeID()), x, y);
            if(node.getNodeID() != 1){
                graphic.setColor(Color.RED);
                graphic.fillRect(x, y, 5, 3);
            } else{
                graphic.setColor(Color.BLUE);
                graphic.fillRect(x, y, 10, 3);
            }
            for(Node neighbor: node.getLinks()){
                int neighborX =(int)neighbor.getxCoordinate()%1000;
                int neighborY = 300/2 - (int)neighbor.getyCoordinate() + 50;
                graphic.setColor(Color.CYAN);
                graphic.drawLine(x, y, neighborX, neighborY);
            }
        }
    }
}
