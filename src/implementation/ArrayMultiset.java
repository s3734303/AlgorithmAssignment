package implementation;

import java.util.List;

/**
 * Array implementation of a multiset.  See comments in RmitMultiset to
 * understand what each overriden method is meant to do.
 *
 * @author Jeffrey Chan & Yongli Ren, RMIT 2020
 */
public class ArrayMultiset extends RmitMultiset
{
	protected String array[];
	protected int arraySize;
	
	
    public ArrayMultiset() {
    	array = null;
    	arraySize = 0;
	}

	@Override
	public void add(String elem) {
    	if (array == null) {
    		array = new String[1];
    		array[0] = elem;
    		arraySize++;
    	}
    	else {
    		String newArray[] = new String[arraySize + 1];
    		for (int i = 0; i < arraySize; i++) {
    			newArray[i] = array[i];
    		}
    		newArray[arraySize] = elem;
    		arraySize++;
    		array = newArray;
    	}
    } // end of add()


    @Override
	public int search(String elem) {
    	int numOfInstance = 0;
    	if (array != null) {
    		for (int i = 0; i < arraySize; i++) {
    			if (array[i].equals(elem)) {
    				numOfInstance++;
    			}
    		}
    	}
    	if (numOfInstance == 0) {
    		return searchFailed;
    	}
        return numOfInstance;
    } // end of search()


    @SuppressWarnings("unchecked")
	@Override
    public List<String> searchByInstance(int instanceCount) {
    	MyList instances = new MyList();
    	for (int i = 0; i < arraySize; i++) {
    		if (search(array[i]) == instanceCount) {
    			instances.add(array[i]);
    		}
    	}
    	return instances;
    } // end of searchByInstance


    @Override
	public boolean contains(String elem) {
    	if (array != null) {
    		for (int i = 0; i < array.length; i++) {
    			if (array[i].equals(elem)) {
    				return true;
    			}
    		}
    	}
    	return false;
    } // end of contains()


    @Override
    // Only remove one of them
	public void removeOne(String elem) {
    	String[] newList = new String[arraySize - 1];
    	boolean removed = false;
    	
    	if (array != null && contains(elem)) {
    		for (int i = 0; i < arraySize; i++) {
    			if (!array[i].equals(elem) && !removed) {
    				newList[i] = array[i];
    			} else if (array[i].contentEquals(elem) && !removed) {
    				removed = true;
    			} else if(removed) {
    				newList[i-1] = array[i];
    			} 
    		}
    	}
		arraySize--;
		array = newList;
    } // end of removeOne()

    
    @Override
	public String print() {
    	// put frequency of elements in a list
    	MyList freqList = new MyList();
    	for (int i = 0; i < arraySize; i++) {
    		freqList.add(search(array[i]));
    	}
    	
    	// sort freqList
        freqList.reverseSort();
        
        return freqList.toStringInOrder(this);
    } // end of OrderedPrint


    @Override
	public String printRange(String lower, String upper) {
    	String inRange = "";
    	for (int i = 0; i < arraySize; i++) {
    		if (array[i].compareToIgnoreCase(lower) >= 0 && array[i].compareToIgnoreCase(upper) <= 0) {
    			inRange += array[i] + ":" + search(array[i]) + "\n";
    		}
    	}
        return inRange;
    } // end of printRange()
    

    @Override
	public RmitMultiset union(RmitMultiset other) {
    	RmitMultiset sum = this.getCopy();

    	for (int i = 0; i < ((ArrayMultiset)other).getSize(); i++) {
    		sum.add(((ArrayMultiset)other).get(i));
    	}
    	
        return sum;
    } // end of union()


    @Override
	public RmitMultiset intersect(RmitMultiset other) {
    	RmitMultiset intersect = new ArrayMultiset();
    	
    	ArrayMultiset thisCopy = this.getCopy();
    	RmitMultiset otherCopy = ((ArrayMultiset)other).getCopy();
    	
    	while (thisCopy.getSize() > 0) {
    		String currElem = thisCopy.get(0);
    		if (otherCopy.contains(currElem)) {
    			intersect.add(currElem);
    			thisCopy.removeOne(currElem);
    			otherCopy.removeOne(currElem);
    		} else {
    			thisCopy.removeOne(currElem);
    		}
    	}

        return intersect;
    } // end of intersect()


    @Override
	public RmitMultiset difference(RmitMultiset other) {
    	ArrayMultiset thisCopy = this.getCopy();
    	RmitMultiset otherCopy = ((ArrayMultiset)other).getCopy();
    	
    	int i = 0;
    	while (i < thisCopy.getSize()) {
    		String currElem = thisCopy.get(i);
    		if (otherCopy.contains(currElem)) {
    			thisCopy.removeOne(currElem);
    			otherCopy.removeOne(currElem);
    		} else {
    			i++;
    		}
    	}

        return thisCopy;
    } // end of difference()
    
    
    private int getSize() {
    	return arraySize;
    }
    
    
    private String get(int index) {
    	return array[index];
    }
    
    
    private ArrayMultiset getCopy() {
    	ArrayMultiset copy = new ArrayMultiset();
    	
    	for (int i = 0; i < arraySize; i++) {
    		copy.add(array[i]);
    	}
    	
    	return copy;
    }
} // end of class ArrayMultiset
