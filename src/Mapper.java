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
	private static ArrayList<Integer> path = new ArrayList<Integer>();
	static Graph map = new Graph(0,false); //+1 necessary to resolve array index issue
	
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
				}
				lineCounter++;
			}
			//show(map);
			map.djikstra(1);
			path = map.printPath(3);
			
			for(int i = 0; i<path.size(); i++){
				System.out.print(valIntMap.get(path.get(i)).key);
				if(i != path.size()-1)
					System.out.print(" to ");
			}
			System.out.println();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
	
	
	static void show(Graph G){
		for(int s=0; s<G.V(); s++){
			AdjList A = G.getAdjList(new Intersection(s,"i0",0,0));
			for (int t = A.begin(); !A.end(); t=A.next()){
				System.out.println("For intersection/vertex " + (s) + " there is adjacent intersection " + (t) + " ");
			}
		}
	}
}