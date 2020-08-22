package implementation;

import java.util.List;

/**
 * Dual linked list implementation of a multiset.  See comments in RmitMultiset to
 * understand what each overriden method is meant to do.
 *
 * @author Jeffrey Chan & Yongli Ren, RMIT 2020
 */
public class DualLinkedListMultiset extends RmitMultiset
{

    public Node find(Node root, String item){
        if(!(item.equals(root.getData()) || root == null))
            root = find(root.next,item);
        return root;
    }
    Node ascendant;
    Node instance;


    @Override
	public void add(String item) {
        if(ascendant == null){
            ascendant = new Node(item);
            instance = new Node(item);
            return;
        }
        Node header = ascendant;
        while (item.compareTo(ascendant.next.getData())>0)
            header=header.next;
        header.setNext(new Node(item));
        header = instance;
        while (header.hasNext()){
            if(item.equals(header.next.getData())){
                header.setNext(new Node(item));
                return;
            }
        }
        header.setNext(new Node(item));
    } // end of add()


    @Override
	public int search(String item) {
        Node header = find(ascendant,item);
        if(header==null)
            return searchFailed;
        int count=1;
        while (header.next.equals(item)){
            header=header.next;
            count++;
        }
        return count;
    } // end of search()


    @Override
	public List<String> searchByInstance(int instanceCount) {
        Node header = instance;
        List<String> list = new MyList();

        while(header.hasNext()){
            int count =1;
            if(header.getNext().getData().equals(header.getData())){
                while (header.getNext().getData().equals(header.getData())){
                    header=header.next;
                    count++;
                }
            }
            if(count==instanceCount)
                list.add(header.getData());
            header=header.next;
        }
        return list;
    } // end of searchByInstance    


    @Override
	public boolean contains(String item) {
        return search(item)>0;
    } // end of contains()


    @Override
	public void removeOne(String item) {
        Node header;
        header=ascendant;
        while (header.hasNext()){
            if(header.next.data.equals(item)){
                header.next=header.next.next;
                break;
            }
            header=header.next;
        }
        header=instance;
        while (header.hasNext()){
            if(header.next.data.equals(item)){
                header.next=header.next.next;
                break;
            }
            header=header.next;
        }
    } // end of removeOne()


    @Override
	public String print() {

        // Placeholder, please update.
        return new String();
    } // end of OrderedPrint


    @Override
	public String printRange(String lower, String upper) {

        // Placeholder, please update.
        return new String();
    } // end of printRange()


    @Override
	public RmitMultiset union(RmitMultiset other) {

        // Placeholder, please update.
        return null;
    } // end of union()


    @Override
	public RmitMultiset intersect(RmitMultiset other) {
        RmitMultiset intersect = new DualLinkedListMultiset();
        Node header = ascendant;
        while (header.hasNext()){
            if(other.contains(header.getData()))
                intersect.add(header.getData());
            header=header.next;
        }
        return intersect;
    } // end of intersect()


    @Override
	public RmitMultiset difference(RmitMultiset other) {
        Node header = ascendant;






        RmitMultiset set = new DualLinkedListMultiset();

        // Placeholder, please update.
        return set;
    } // end of difference()

} // end of class DualLinkedListMultiset
