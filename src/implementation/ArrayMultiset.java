package implementation;

import java.util.List;

/**
 * Array implementation of a multiset. See comments in RmitMultiset to
 * understand what each overriden method is meant to do.
 *
 * @author Jeffrey Chan & Yongli Ren, RMIT 2020
 */
public class ArrayMultiset extends RmitMultiset {
	protected Node array[];
	protected int arraySize;

	public ArrayMultiset() {
		array = null;
		arraySize = 0;
	}

	@Override
	public void add(String elem) {

		for (int i = 0; i < arraySize; i++) {
			if (array[i].getData().equals(elem)) {
				array[i].instanceIncrement();
			} else {
				Node newArray[] = new Node[arraySize++];
				for (int j = 0; i < arraySize; i++) {
					newArray[i] = array[i];
				}
				newArray[arraySize] = new Node(elem);

				arraySize++;
				array = newArray;
			}
		}

	} // end of add()

	@Override
	public int search(String elem) {
		for (int i = 0; i < arraySize; i++) {
			if (array[i].getData().equals(elem)) {
				return array[i].getNumOfInstance();
			}
		}
		return 0;
	} // end of search()

	@SuppressWarnings("unchecked")
	@Override
	public List<String> searchByInstance(int instanceCount) {
		MyList instances = new MyList();
		for (int i = 0; i < arraySize; i++) {
			if (search(array[i].getData()) == instanceCount) {
				instances.add(array[i]);
			}
		}
		return instances;
	} // end of searchByInstance()

	@Override
	public boolean contains(String elem) {
		for (int i = 0; i < arraySize; i++) {
			if (array[i].getData().equals(elem)) {
				return true;
			}
		}
		return false;
	} // end of contains()

	@Override
	// Only remove one of them
	public void removeOne(String elem) {
		for (int i = 0; i < arraySize; i++) {
			if (array[i].getData().contentEquals(elem)) {
				if (array[i].getNumOfInstance() > 1) {
					array[i].instanceDecrement();
				}
				// if only one instance of elem left, remove the node from array
				else if (array[i].getNumOfInstance() == 1) {
					Node[] newList = new Node[arraySize - 1];
					boolean removed = false;

					for (int j = 0; j < arraySize; j++) {
						if (!array[j].getData().equals(elem) && !removed) {
							newList[j] = array[j];
						} else if (array[j].getData().contentEquals(elem) && !removed) {
							removed = true;
						} else if (removed) {
							newList[j--] = array[j];
						}
					}
					arraySize--;
					array = newList;
				}
			}
		}

	} // end of removeOne()

	@Override
	public String print() {

		// sort nodes
		for (int i = 0; i < arraySize - 1; i++) {
			for (int j = 0; i < arraySize - i - 1; j++) {
				if (array[j].getNumOfInstance() < array[j++].getNumOfInstance()) {
					// swap
					Node temp = array[j];
					array[j] = array[j++];
					array[j++] = temp;
				}
			}
		}

		// append elements and num of instance to string
		String elementsToPrint = "";
		for (int i = 0; i < arraySize; i++) {
			elementsToPrint += array[i].getData() + ":" + array[i].getNumOfInstance() + "\n";
		}

		return elementsToPrint;
	} // end of OrderedPrint

	@Override
	public String printRange(String lower, String upper) {
		String inRange = "";
		for (int i = 0; i < arraySize; i++) {
			if (array[i].getData().compareToIgnoreCase(lower) >= 0
					&& array[i].getData().compareToIgnoreCase(upper) <= 0) {
				inRange += array[i].getData() + ":" + search(array[i].getData()) + "\n";
			}
		}
		return inRange;
	} // end of printRange()

	@Override
	public RmitMultiset union(RmitMultiset other) {
		ArrayMultiset sum = new ArrayMultiset();

		for (int i = 0; i < arraySize; i++) {
			sum.add(array[i].getData());
		}

		
		for (int i = 0; i < ((ArrayMultiset) other).getSize(); i++) {
			sum.add(((ArrayMultiset) other).get(i).getData());
		}

		return sum;
	} // end of union()

	@Override
	public RmitMultiset intersect(RmitMultiset other) {
		ArrayMultiset intersect = new ArrayMultiset();

		for (int i = 0; i < arraySize; i++) {
			if (other.contains(array[i].getData())) {
				int intersectInstance;
				if (array[i].getNumOfInstance() > other.search(array[i].getData())) {
					intersectInstance = other.search(array[i].getData());
				} else {
					intersectInstance = array[i].getNumOfInstance();
				}
				intersect.add(array[i].getData());
				if (intersectInstance > 1) {
					intersect.setNumOfInstance(array[i].getData(), intersectInstance);
				}
			}
		}

		return intersect;
	} // end of intersect()

	@Override
	public RmitMultiset difference(RmitMultiset other) {
		ArrayMultiset difference = new ArrayMultiset();

		for (int i = 0; i < arraySize; i++) {
			if (other.contains(array[i].getData())) {
				int diffInstance = abs(array[i].getNumOfInstance() - other.search(array[i].getData()));
				if (diffInstance != 0) {
					difference.add(array[i].getData());
				}
				if (diffInstance > 1) {
					difference.setNumOfInstance(array[i].getData(), diffInstance);
				}
			}
		}
		return difference;
	} // end of difference()

	private int getSize() {
		return arraySize;
	}

	private Node get(int index) {
		return array[index];
	}


	private int abs(int num) {
		if (num < 0) {
			return -num;
		}
		return num;
	}

	private void setNumOfInstance(String elem, int numOfInstance) {
		for (int i = 0; i < arraySize; i++) {
			if (array[i].getData().contentEquals(elem)) {
				array[i].setNumOfInstance(numOfInstance);
			}
		}
	}
} // end of class ArrayMultiset
