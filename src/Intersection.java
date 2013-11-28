public class Intersection {
	public AdjList adj;
	public boolean known;
	public double dist;
	public int ID;
	public Intersection path;
	public double xcoor;
	public double ycoor;
	public String key;
	
	public Intersection(int ID, String key, double xcoor, double ycoor){
		this.ID = ID;
		this.key = key;
		this.xcoor = xcoor;
		this.ycoor = ycoor;
		this.dist =  Double.POSITIVE_INFINITY;
		this.known = false;
		this.path = null;
	}
	
	public void setID(int ID){
		this.ID = ID;
	}
	
	public boolean Distance(){
		if (dist == Double.POSITIVE_INFINITY)
			return true;
		else
			return false;
	}
	
	public String toString(){
		return ID + " " + key + " " + xcoor + " " + ycoor;
	}
	
	public Intersection getIntersection(){
		return this;
	}
	
	public boolean getKnown() {
        return known;
	}
	
	public void setKnown(boolean b) {
	    known = b;
	}
}
