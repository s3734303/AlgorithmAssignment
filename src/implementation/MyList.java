package implementation;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

@SuppressWarnings({ "rawtypes" })
public class MyList implements java.util.List {
	
	private Object[] list;
	private int numOfElements;  // to keep track of number of instances in this list

	MyList() {
		list = null;
		numOfElements = 0;
	}
	
	
	@Override
	public boolean add(Object o) {
        if (!contains(o)) {
        	Object[] temp = new Object[numOfElements + 1];
        	for (int i = 0; i < numOfElements; i++) {
        		temp[i] = list[i];
        	}
        	
        	temp[numOfElements] = o;
        	numOfElements++;
        	list = temp;
        	
        	return true;
        }
        return false;
	}
	
	@Override
	public boolean contains(Object o) {
		for (int i = 0; i < numOfElements; i++) {
			if (list[i].equals(o)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public int size() {
		return numOfElements;
	}

	@Override
	public Object get(int index) {
		return list[index];
	}

	@Override
	public Object set(int index, Object element) {
		Object previous = list[index];
		list[index] = element;
		return previous;
	}


	@Override
	public Iterator iterator() {
		Iterator<Object> it = new Iterator<Object>() {

            private int i = 0;

            @Override
            public boolean hasNext() {
                return i < numOfElements && list[i] != null;
            }

            @Override
            public Object next() {
                return list[i++];
            }

        };
        return it;
	}

	
	public void reverseSort() {
    	// bubble sort
        for (int i = 0; i < numOfElements-1; i++) {
            for (int j = 0; j < numOfElements-i-1; j++) {
                if ((int)list[j] < (int)list[j+1]) {
                    // swap
                    int temp = (int)list[j];
                    list[j] = list[j+1];
                    list[j+1] = temp;
                }
            }
        }
	}
	
	public String toStringInOrder(RmitMultiset rmitMultiset) {
		String elements = "";

		// searchByInstance() and put into string
        MyList strSameFreq = new MyList();
    	for (int i = 0; i < numOfElements; i++) {
    		strSameFreq = (MyList) rmitMultiset.searchByInstance((int)list[i]);
    		for (int j = 0; j < strSameFreq.size(); j++) {
    			elements += strSameFreq.get(j) + ":" + list[i] + "\n";
    		}
    	}
    	
        return elements;
	}
	
	
	@Override
	public boolean isEmpty() {
		return false;
//		for (int i = 0; i < numOfElements; i++) {
//			if (list[i] != null) {
//				return false;
//			}
//		}
//		return true;
	}
	 
	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] toArray(Object[] a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAll(Collection c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(Collection c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(int index, Collection c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add(int index, Object element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object remove(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int indexOf(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int lastIndexOf(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ListIterator listIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListIterator listIterator(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List subList(int fromIndex, int toIndex) {
		// TODO Auto-generated method stub
		return null;
	}


}
