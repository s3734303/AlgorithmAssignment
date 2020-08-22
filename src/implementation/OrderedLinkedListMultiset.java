package implementation;

import java.util.List;

/**
 * Ordered linked list implementation of a multiset.  See comments in RmitMultiset to
 * understand what each overriden method is meant to do.
 *
 * @author Jeffrey Chan & Yongli Ren, RMIT 2020
 */
public class OrderedLinkedListMultiset extends RmitMultiset
{
	private Node head;
	
	
	public OrderedLinkedListMultiset() {
		head = null;
	}
	
	
    @Override
	public void add(String item) {
    	Node newNode = new Node(item);
        if (head == null) {
        	head = newNode;
        } 
        // else append the item
        else {
        	Node currNode = head;
        	while (currNode.getNext() != null) {
        		currNode = currNode.getNext();
        	}
        	
        	currNode.setNext(newNode);
        }
    } // end of add()


    @Override
	public int search(String item) {
    	int numOfInstance = 0;
    	
        Node currNode = head;
        while (currNode != null) {
        	if (currNode.getData().equals(item)) {
        		numOfInstance++;
        	}
        	currNode = currNode.getNext();
        }
        if (numOfInstance == 0) {
        	return searchFailed;
        }
        return numOfInstance;
    } // end of search()


    @SuppressWarnings("unchecked")
	@Override
	public List<String> searchByInstance(int instanceCount) {
    	// Put frequency of instances in a HashMap
    	MyList instances = new MyList();
    	
    	Node currNode = head;
    	while (currNode != null) {
    		if (search(currNode.getData()) == instanceCount) {
    			instances.add(currNode.getData());
    		}
    		currNode = currNode.getNext();
    	}

        return instances;
    } // end of searchByInstance


    @Override
	public boolean contains(String item) {
    	Node currNode = head;
    	
        if (head != null) {
        	while(currNode != null) {
        		if (currNode.getData().equals(item)) {
        			return true;
        		}
        		currNode = currNode.getNext();
        	}
        }
        return false;
    } // end of contains()


    @Override
	public void removeOne(String item) {
    	Node currNode = head;
    	Node prevNode = null;
    	
    	// check if value is head
    	if (currNode.getData().equals(item)) {
    		head = currNode.getNext();
    		return;
    	}
    	
    	prevNode = currNode;
    	currNode = currNode.getNext();
    	
    	while (currNode != null) {
    		if (currNode.getData().equals(item)) {
    			prevNode.setNext((currNode.getNext()));
    			currNode = null;
    			return;
    		}
    		prevNode = currNode;
    		currNode = currNode.getNext();
    	}

    } // end of removeOne()


    @Override
	public String print() {
    	// put frequency of elements in a list
    	Node currNode = head;
    	MyList freqList = new MyList();
    	while (currNode != null) {
    		freqList.add(search(currNode.getData()));
    		currNode = currNode.getNext();
    	}
    	
    	// sort freqList
    	freqList.reverseSort();
    	
    	return freqList.toStringInOrder(this);
    } // end of OrderedPrint


    @Override
	public String printRange(String lower, String upper) {
    	String inRange = "";
    	
    	Node currNode = head;
    	while(currNode != null) {
    		if (currNode.getData().compareToIgnoreCase(lower) >= 0 && currNode.getData().compareToIgnoreCase(upper) <= 0) {
    			inRange += currNode.getData() + ":" + search(currNode.getData()) + "\n";
    		}
    		currNode = currNode.getNext();
    	}

        return inRange;
    } // end of printRange()


    @Override
	public RmitMultiset union(RmitMultiset other) {
    	RmitMultiset sum = this.getCopy();
    	
    	Node currNode = ((OrderedLinkedListMultiset) other).getHead();
    	while (currNode != null) {
    		sum.add(currNode.getData());
    		currNode = currNode.getNext();
    	}

        return sum;
    } // end of union()


    @Override
	public RmitMultiset intersect(RmitMultiset other) {
    	RmitMultiset intersect = new OrderedLinkedListMultiset();
    	
    	OrderedLinkedListMultiset thisCopy = this.getCopy();
    	RmitMultiset otherCopy = ((OrderedLinkedListMultiset)other).getCopy();
    	
    	Node currNode = thisCopy.getHead();
    	while (currNode.getNext() != null) {
    		String currElem = currNode.getData();
    		if (otherCopy.contains(currElem)) {
    			intersect.add(currElem);
    			thisCopy.removeOne(currElem);
    			otherCopy.removeOne(currElem);
    		} else {
    			thisCopy.removeOne(currElem);
    		}
    		currNode = thisCopy.getHead();
    	}
        
        return intersect;
    } // end of intersect()


	@Override
	public RmitMultiset difference(RmitMultiset other) {
		OrderedLinkedListMultiset thisCopy = this.getCopy();
    	RmitMultiset otherCopy = ((OrderedLinkedListMultiset)other).getCopy();
    	
    	Node currNode = thisCopy.getHead();
    	while (currNode != null) {
    		String currElem = currNode.getData();
    		if (otherCopy.contains(currElem)) {
    			thisCopy.removeOne(currElem);
    			otherCopy.removeOne(currElem);
    		} else {
    			currNode = currNode.getNext();
    		}
    	}
		
        return thisCopy;
    } // end of difference()

    
    private Node getHead() {
    	return head;
    }
    
    
    private OrderedLinkedListMultiset getCopy() {
		OrderedLinkedListMultiset copy = new OrderedLinkedListMultiset();
		
		Node currNode = head;
    	while (currNode != null) {
    		copy.add(currNode.getData());
    		currNode = currNode.getNext();
    	}
    	
		return copy;
	}
    

} // end of class OrderedLinkedListMultiset
