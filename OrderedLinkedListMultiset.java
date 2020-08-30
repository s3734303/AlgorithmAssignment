package implementation;

import java.util.List;

/**
 * Ordered linked list implementation of a multiset. See comments in
 * RmitMultiset to understand what each overriden method is meant to do.
 *
 * @author Jeffrey Chan & Yongli Ren, RMIT 2020
 */
public class OrderedLinkedListMultiset extends RmitMultiset {
	private Node head;

	public OrderedLinkedListMultiset() {
		head = null;
	}

	@Override
	public void add(String item) {
		Node newNode = new Node(item);
		Node prevNode = null;
		Node currNode = head;

		if (contains(item)) {
			while (currNode != null) {
				if (currNode.getData().equals(item)) {
					currNode.instanceIncrement();
					return;
				}
				currNode = currNode.getNext();
			}
		} else {
			while ((currNode != null) && (item.compareToIgnoreCase(currNode.getData()) >= 0)) {
				prevNode = currNode;
				currNode = currNode.getNext();
			}
			if (prevNode == null) {
				newNode.setNext(currNode);
				head = newNode;
			} else {
				prevNode.setNext(newNode);
				newNode.setNext(currNode);
			}

		}

	} // end of add()

	@Override
	public int search(String item) {

		Node currNode = head;
		while (currNode != null) {
			if (currNode.getData().equals(item)) {
				return currNode.getNumOfInstance();
			}
			currNode = currNode.getNext();
		}

		return searchFailed;
	} // end of search()

	@SuppressWarnings("unchecked")
	@Override
	public List<String> searchByInstance(int instanceCount) {
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
			while (currNode != null) {
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

		while (currNode != null) {
			if (currNode.getData().equals(item)) {
				if (currNode.getNumOfInstance() > 1) {
					currNode.instanceDecrement();
					return;
					// remove node if only one instance left
				} else if (currNode.getNumOfInstance() == 1) {
					if (currNode == head) {
						head = currNode.getNext();
						return;
					}
					prevNode.setNext(currNode.getNext());
					currNode = null;
					return;
				}
			}
			prevNode = currNode;
			currNode = currNode.getNext();
		}

	} // end of removeOne()

	@Override
	public String print() {
		int size = getSize();

		// sort nodes
		for (int i = 0; i < size; i++) {
			Node prevNode = head;
			Node currNode = head.getNext();
			for (int j = 0; j < size - i - 1; j++) {
				if (prevNode.getNumOfInstance() < currNode.getNumOfInstance()) {
					// swap
					int tempInstance = prevNode.getNumOfInstance();
					String tempElem = prevNode.getData();

					prevNode.setData(currNode.getData());
					prevNode.setNumOfInstance(currNode.getNumOfInstance());
					currNode.setData(tempElem);
					currNode.setNumOfInstance(tempInstance);
				}
				prevNode = currNode;
				currNode = currNode.getNext();
			}

		}

		// append elements and num of instance to string
		Node currNode = head;
		String elementsToPrint = "";
		while (currNode != null) {
			elementsToPrint += currNode.getData() + ":" + currNode.getNumOfInstance() + "\n";
			currNode = currNode.getNext();
		}

		return elementsToPrint;
	} // end of OrderedPrint

	@Override
	public String printRange(String lower, String upper) {
		String inRange = "";

		Node currNode = head;
		while (currNode != null) {
			if (currNode.getData().compareToIgnoreCase(lower) >= 0
					&& currNode.getData().compareToIgnoreCase(upper) <= 0) {
				inRange += currNode.getData() + ":" + currNode.getNumOfInstance() + "\n";
			}
			currNode = currNode.getNext();
		}

		return inRange;
	} // end of printRange()

	@Override
	public RmitMultiset union(RmitMultiset other) {
		OrderedLinkedListMultiset sum = new OrderedLinkedListMultiset();

		Node currNode = head;
		while (currNode != null) {
			sum.add(currNode.getData());
			sum.setNumOfInstance(currNode.getData(), currNode.getNumOfInstance());
			currNode = currNode.getNext();
		}

		currNode = ((OrderedLinkedListMultiset) other).getHead();
		while (currNode != null) {
			sum.add(currNode.getData());
			sum.setNumOfInstance(currNode.getData(), sum.search(currNode.getData()) + currNode.getNumOfInstance() - 1);
			currNode = currNode.getNext();
		}

		return sum;
	} // end of union()

	@Override
	public RmitMultiset intersect(RmitMultiset other) {
		OrderedLinkedListMultiset intersect = new OrderedLinkedListMultiset();

		Node currNode = head;
		while (currNode != null) {
			if (other.contains(currNode.getData())) {
				int intersectInstance = min(currNode.getNumOfInstance(), other.search(currNode.getData()));
				intersect.add(currNode.getData());
				if (intersectInstance > 1) {
					intersect.setNumOfInstance(currNode.getData(), intersectInstance);
				}
			}
			currNode = currNode.getNext();
		}
		return intersect;
	} // end of intersect()

	@Override
	public RmitMultiset difference(RmitMultiset other) {
		OrderedLinkedListMultiset difference = new OrderedLinkedListMultiset();

		Node currNode = head;
		while (currNode != null) {
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
			currNode = currNode.getNext();
		}
		return difference;
	} // end of difference()

	private Node getHead() {
		return head;
	}

	private int getSize() {
		Node currNode = head;
		int size = 0;
		while (currNode != null) {
			size++;
			currNode = currNode.getNext();
		}
		return size;
	}

	private void setNumOfInstance(String elem, int numOfInstance) {
		Node currNode = head;
		while (currNode != null) {
			if (currNode.getData().equals(elem)) {
				currNode.setNumOfInstance(numOfInstance);
			}
			currNode = currNode.getNext();
		}
	}

	private int min(int int1, int int2) {
		if (int1 > int2) {
			return int2;
		}
		return int1;
	}

} // end of class OrderedLinkedListMultiset
