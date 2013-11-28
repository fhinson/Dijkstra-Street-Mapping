import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JPanel;
	
public class Graph {
	private int intersectionCount, roadCount;
	boolean digraph;
	private double adj[][];
	private double[] dist;
	private int[] path;
	private boolean[] known;
	static Road[] tree;
	private ArrayList<Integer> paths = new ArrayList<Integer>();
	
	public Graph(int intersectionCount, boolean isDirgraph){
		this.intersectionCount = intersectionCount;
		digraph = isDirgraph;
		adj = new double[intersectionCount][intersectionCount];
		dist = new double[intersectionCount];
		known = new boolean[intersectionCount];
		path = new int[intersectionCount];
		
		for (int i=0; i<adj.length; i++){
			for(int j=0; j<adj[i].length; j++){
				adj[i][j] = (i==j)? 0 : -1;
			}
		}
	}
	
	public boolean directed(){
		return digraph;
	}
	
	public int V(){
		return intersectionCount;
	}
	
	public int E(){
		return roadCount;
	}
	
	public void insert(Road e){
		if (Road(e.v, e.w))
			return;
		adj[e.v.ID][e.w.ID] = e.weight;
		roadCount++;
		if (!digraph){
			adj[e.w.ID][e.v.ID] = e.weight;
		}
	}
	
	public void delete(Road e){
		if(!Road(e.v,e.w))
			return;
		adj[e.v.ID][e.w.ID] = -1;
		if (!digraph){
			adj[e.w.ID][e.v.ID] = -1;
		}
		roadCount--;
	}
	
	public void changeWeight(Road e, int newWeight){
		e.weight = newWeight;
		adj[e.v.ID][e.w.ID] = e.weight;
	}
	
	public AdjList getAdjList(Intersection v){
		return new AdjArray(v);
	}
	
	public boolean Road(Intersection v, Intersection w){
		return adj[v.ID][w.ID] > 0;
	}
	
	public double roadDistance(Intersection v, Intersection w){
		return new Road("anyKey",v,w).findWeight(v,w);
	}
	
	private class AdjArray implements AdjList {
		private Intersection v;
		private Intersection i = new Intersection(0,"i0",0,0);
		AdjArray(Intersection v){
			this.v = v;
			i.setID(-1);
		}
		public int next(){
			for(i.ID++;i.ID<V();i.ID++){
				if(Road(v,i)){
					return i.ID;
				}
			}
			return -1;
		}
		public int begin(){
			i.setID(-1);
			return next();
		}
		public boolean end(){
			if (i.ID < V())
				return false;
			return true;
		}
	}
	
	public ArrayList<Integer> printPath(int v){
		if(path[v] != -1){
			printPath(path[v]);
		}
		paths.add(v);
		return paths;
	}
	
	public void printDist(int v){
		System.out.println("Distance from origin is " + dist[v]);
	}
	
	public void djikstra(int s){		
		for (int i = 0; i < dist.length; i++){
			dist[i] = (i == s) ? 0 : Integer.MAX_VALUE;
			path[i] = -1;
			known[i] = false;
		}
		int aRoad;
		while((aRoad = findSmallestIntersection(s)) != -1){ 
			known[aRoad] = true; 
			AdjList A = getAdjList(new Intersection(aRoad,"i0",0,0));
			for(int w = A.begin(); !A.end(); w = A.next()){
				if(!known[w] && (dist[aRoad] + adj[aRoad][w] < dist[w])){
						dist[w] = dist[aRoad] + adj[aRoad][w];
						path[w] = aRoad;
				}
			}
		}
	}
	
	public int findSmallestIntersection(int start){
		double smallestDist = Double.MAX_VALUE;
		int aRoad = -1;
		for (int i = 0; i < V(); i++){
			if (!known[i] && dist[i] < smallestDist){
				smallestDist = dist[i];
				aRoad = i;
			}
		}
		return aRoad;
	}
}