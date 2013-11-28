import javax.swing.*;

import java.awt.*;
import java.awt.geom.Line2D;
import java.io.*;
import java.util.*;

public class Mapper extends JFrame{
	
	private static int intersectionCount, intersectionID = 1;
	private static Map<String, Intersection> intersectionMap = new HashMap<String, Intersection>();
	private static Map<Integer, Intersection> valIntMap = new HashMap<Integer, Intersection>();
	private static Map<String, Road> roadMap = new HashMap<String, Road>();
	private static Map<String, Road> valRoadMap = new HashMap<String, Road>();
	private static ArrayList<Integer> path = new ArrayList<Integer>();
	static Graph map = new Graph(0,false); //+1 necessary to resolve array index issue
	private static ArrayList<Road> tx = new ArrayList<Road>();
	static Road[] tree;
	
	public static void main (String[] args) throws IOException {
		
		try {
			FileReader fileread = new FileReader("test.txt");
			BufferedReader graphFile = new BufferedReader(fileread);
			
			String line;
			int lineCounter = 1;
			while((line = graphFile.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line);
				String identifier = st.nextToken();
				if (identifier.equals("i")) {
					String key = st.nextToken();
					double x = Double.parseDouble(st.nextToken());
					double y = Double.parseDouble(st.nextToken());
					if (intersectionMap.get(key) == null) {
						Intersection v = new Intersection(intersectionID, key, x, y);
						intersectionMap.put(key, v);
						valIntMap.put(intersectionID, v);
						intersectionCount = intersectionID++;
					}
					
				} 
				
				if(intersectionID == lineCounter){
					map = new Graph(intersectionCount+1, false);
				}
				
				else if (identifier.equals("r")) {
					String key = st.nextToken();
					String intersectionA = st.nextToken();
					String intersectionB = st.nextToken();
					Intersection v1 = intersectionMap.get(intersectionA);
					Intersection v2 = intersectionMap.get(intersectionB);
					Road r = new Road(key, v1, v2);
					map.insert(r);
					roadMap.put(key,r);
					valRoadMap.put((intersectionA+"|"+intersectionB), r);
				}
				lineCounter++;
			}
			
			String start = JOptionPane.showInputDialog(null,"What point are you starting from? Enter an intersection key.");
			String end = JOptionPane.showInputDialog(null,"What point are you ending at? Enter an intersection key.");
			
			map.djikstra(intersectionMap.get(start).ID);
			path = map.printPath(intersectionMap.get(end).ID);
			
			for(int i = 0; i<path.size(); i++){
				System.out.print("Intersection " + valIntMap.get(path.get(i)).key);
				if(i != path.size()-1)
					System.out.print(" to ");
			}
			System.out.println();
			for(int i = 0; i<path.size()-1; i++){
				if (valRoadMap.get((valIntMap.get(path.get(i)).key+"|"+valIntMap.get(path.get(i+1)).key)) != null)
					System.out.print("Road " + valRoadMap.get((valIntMap.get(path.get(i)).key+"|"+valIntMap.get(path.get(i+1)).key)).key);
				else
					System.out.print("Road " + valRoadMap.get((valIntMap.get(path.get(i+1)).key+"|"+valIntMap.get(path.get(i)).key)).key);
				if(i != path.size()-2)
					System.out.print(" to ");
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 tree = indexRoads(tx);
         tree();
         final ArrayList<Road> finalTree = treeMaker();
		
		Mapper t = new Mapper();
        t.add(new JComponent() {
            public void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                
                Iterator it = roadMap.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pairs = (Map.Entry)it.next();
                    Road r = (Road)pairs.getValue();
                    Shape l = new Line2D.Double(r.v.xcoor, r.v.ycoor, r.w.xcoor, r.w.ycoor);
                    g2.draw(l);
                    it.remove(); // avoids a ConcurrentModificationException
                }
                //draw mst
                g2.setColor(Color.green);
                for (Road r : finalTree) {
                        Road road = r;
                        Shape line = new Line2D.Double(r.v.xcoor, r.v.ycoor, r.w.xcoor, r.w.ycoor);
                        g2.draw(line);
                }
                //now draw shortest path
                g2.setColor(Color.orange);
                if(path.size() > 1){
                	for(int i = 0; i<path.size()-1; i++){
	    				Shape l = new Line2D.Double(valIntMap.get(path.get(i)).xcoor,valIntMap.get(path.get(i)).ycoor,valIntMap.get(path.get(i+1)).xcoor,valIntMap.get(path.get(i+1)).ycoor);
	    				g2.draw(l);	
    				}
    			}	
            }
        });

        t.setDefaultCloseOperation(EXIT_ON_CLOSE);
        t.setSize(800, 800);
        t.setVisible(true);
	}
	
	public static void tree() {
        for (int i = 0; i < tree.length; i++) {
                double min = tree[i].weight;
                int index = i;
                for (int j = i+1; j < tree.length; j++) {
                        if ((double) tree[j].weight < min) {
                                min = tree[j].weight;
                                index = j;
                        }
                }
                if (index != i) {
                        Road e = tree[index];
                        tree[index] = tree[i];
                        tree[i] = e;
                }
        }
	}
	
	public static ArrayList<Road> treeMaker() {
        ArrayList<Road> tl = new ArrayList<Road>();
        for (int i = 0; i < tree.length; i++) {
                if (!tree[i].getRoadKnown()) {
                        tl.add(tree[i]);
                        tree[i].setRoadKnown();
                }
        }
        return tl;
	}
	
	public static Road[] indexRoads(ArrayList<Road> r) {
        Road[] tree = new Road[r.size()];
        for (int i = 0; i < r.size(); i++)
                tree[i] = r.get(i);
        return tree;
	}
}