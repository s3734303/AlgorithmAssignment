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
		if (!contains(elem)) {
			Node newArray[] = new Node[arraySize + 1];
			Node newNode = new Node(elem);

			for (int j = 0; j < arraySize; j++) {
				newArray[j] = array[j];
			}

			newArray[arraySize] = newNode;

			arraySize++;
			array = newArray;
		} else {
			for (int i = 0; i < arraySize; i++) {
				if (array[i].getData().equals(elem)) {
					array[i].instanceIncrement();
				}
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
		return searchFailed;
	} // end of search()

	@SuppressWarnings("unchecked")
	@Override
	public List<String> searchByInstance(int instanceCount) {
		MyList instances = new MyList();
		for (int i = 0; i < arraySize; i++) {
			if (search(array[i].getData()) == instanceCount) {
				instances.add(array[i].getData());
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
			if (array[i].getData().equals(elem)) {
				if (array[i].getNumOfInstance() > 1) {
					array[i].instanceDecrement();
					arraySize--;
					return;
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
			for (int j = 0; j < arraySize - i - 1; j++) {
				if (array[j].getNumOfInstance() < array[j + 1].getNumOfInstance()) {
					// swap
					Node temp = array[j];
					array[j] = array[j + 1];
					array[j + 1] = temp;
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
			Node currNode = array[i];
			sum.add(currNode.getData());
			sum.setNumOfInstance(currNode.getData(), currNode.getNumOfInstance());
		}

		for (int i = 0; i < ((ArrayMultiset) other).getSize(); i++) {
			Node currNode = ((ArrayMultiset) other).get(i);
			sum.add(currNode.getData());
			sum.setNumOfInstance(currNode.getData(), sum.search(currNode.getData()) + currNode.getNumOfInstance() - 1);

		}

		return sum;
	} // end of union()

	@Override
	public RmitMultiset intersect(RmitMultiset other) {
		ArrayMultiset intersect = new ArrayMultiset();

		for (int i = 0; i < arraySize; i++) {
			if (other.contains(array[i].getData())) {
				int intersectInstance = min(array[i].getNumOfInstance(), other.search(array[i].getData()));
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
			Node currNode = array[i];
			if (other.contains(currNode.getData())) {
				int diffInstance = currNode.getNumOfInstance() - other.search(currNode.getData());
				if (diffInstance > 0) {
					difference.add(currNode.getData());
					if (diffInstance > 1) {
						difference.setNumOfInstance(currNode.getData(), diffInstance);
					}
				}

			} else {
				difference.add(currNode.getData());
				difference.setNumOfInstance(currNode.getData(), currNode.getNumOfInstance());
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

	private void setNumOfInstance(String elem, int numOfInstance) {
		for (int i = 0; i < arraySize; i++) {
			if (array[i].getData().equals(elem)) {
				array[i].setNumOfInstance(numOfInstance);
			}
		}
	}

	private int min(int int1, int int2) {
		if (int1 > int2) {
			return int2;
		}
		return int1;
	}
} // end of class ArrayMultiset
