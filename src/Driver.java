
public class Driver {
	public static void main(String []args){
		Graph a;
		Intersection ax = new Intersection(1,"i3",(double)1,(double)1);
		Intersection bx = new Intersection(2,"i4",(double)8,(double)1);
		Intersection cx = new Intersection(3,"i5",(double)8,(double)4);
		Intersection dx = new Intersection(4,"i6",(double)10,(double)5);
		Intersection ex = new Intersection(5,"i7",(double)3,(double)14);
		
		a = new Graph(6,false);
		a.insert(new Road("r1",ax,bx));
		a.insert(new Road("r2",bx,cx));
		a.insert(new Road("r3",cx,dx));
		a.insert(new Road("r4",ax,ex));
		a.insert(new Road("r5",ex,cx));
	
		show(a);
		a.djikstra(5);
		a.printPath(4); System.out.println();
		a.printDist(4);
		
	}
	
	/** +1 is to account for how data is stored in array starting from 0 */
	static void show(Graph G){
		for(int s=0; s<G.V(); s++){
			AdjList A = G.getAdjList(new Intersection(s,"i0",0,0));
			for (int t = A.begin(); !A.end(); t=A.next()){
				System.out.println("For intersection " + (s) + " there is adjacent intersection " + (t) + " ");
			}
		}
	}

}
