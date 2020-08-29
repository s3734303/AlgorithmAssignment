package implementation;

public class Node {
	private String data;
	private Node next;
	private int numOfInstance;

	public Node(String data) {
		this.data = data;
		next = null;
		numOfInstance = 1;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Node getNext() {
		return next;
	}

	public void setNext(Node nextNode) {
		next = nextNode;
	}

	public boolean hasNext() {
		return next != null;
	}
	
	public void instanceIncrement() {
		numOfInstance++;
	}
	
	public void instanceDecrement() {
		numOfInstance--;
	}
	
	public int getNumOfInstance() {
		return numOfInstance;
	}

	public void setNumOfInstance(int numOfInstance) {
		this.numOfInstance = numOfInstance;
		
	}

}
