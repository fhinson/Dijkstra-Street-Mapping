public class Road {
	Intersection v, w;
	double weight;
	String key;
	
	/** -1 is included to place into array for graph*/
	public Road(String key, Intersection v, Intersection w){
		this.v = new Intersection(v.ID,v.key,v.xcoor,v.ycoor);
		this.w = new Intersection(w.ID,w.key,w.xcoor,w.ycoor);
		this.weight = findWeight(v,w);
		this.key = key;
	}
	
	
	public double findWeight(Intersection v, Intersection w){
		return Math.pow(Math.pow((w.xcoor-v.xcoor),2)+Math.pow((w.ycoor-v.ycoor),2),0.5);
	}
	
	public void setRoadKnown() {
		v.setKnown(true);
		w.setKnown(true);
	}
	
	public boolean getRoadKnown() {
		return (v.getKnown() && w.getKnown());
	}
	
}