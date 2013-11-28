import java.util.*;

public class Heap implements Interface{
	private int Capacity = 2;
	Comparable[] heap;
	private int size;
	
	public Heap(){
		size = 0;
		heap = new Comparable[Capacity];
	}
	
	public Heap(Comparable[] input){
		heap = new Comparable[input.length];
		size = input.length;
		for(int i = 0; i < size; i++){
			heap[i] = input[i];
		}
		for(int i = size/2; i >= 3; i--){
			bubbleDown(i);
		}
	}

	public Object deleteMin(){
		Comparable min = heap[1];
		heap[1] = heap[size--];
		bubbleDown(1);
		Comparable [] temp = new Comparable[heap.length - 1];
		for (int i = 0; i < temp.length; i++)
			temp[i] = heap [i];
		heap = temp;
		return min;
	}
	
	public void bubbleDown(int j){
		Comparable temp = heap[j];
		int bob;
		for(; 2*j <= size; j = bob) {
			bob = 2*j;
			if(bob != size && heap[bob].compareTo(heap[bob + 1]) > 0) 
				bob++;
			if(temp.compareTo(heap[bob]) > 0) 
				heap[j] = heap[bob];
			else
				break;
		}
		heap[j] = temp;
	}
	
	public boolean isEmpty(){
		if(size == 0)
			return true;
		else
			return false;
	}
	
	public void insert(Comparable o){
		if(size == heap.length - 1) 
			biggerArray();
		bubbleUp(o);
	}
	
	public void bubbleUp(Comparable o){
		int position = ++size;
		for(; position > 1 && o.compareTo(heap[position/2]) < 0; position = position/2)
			heap[position] = heap[position/2];
		heap[position] = o;
	}
	
	public void biggerArray(){
		Comparable[] temp = new Comparable[heap.length * 2];
		for(int i = 0; i < heap.length; i++){
			temp[i] = heap[i];
		}
		heap = temp;
	}
	
	public void printHeap(){
		for(int i = 1; i < heap.length-1; i ++)
			System.out.println(heap[i]);
	}
	
	public int size(){
		return size;
	}
}