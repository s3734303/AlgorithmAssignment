package implementation;

import java.util.List;
import java.util.function.DoubleUnaryOperator;

/**
 * Dual linked list implementation of a multiset.  See comments in RmitMultiset to
 * understand what each overriden method is meant to do.
 *
 * @author Jeffrey Chan & Yongli Ren, RMIT 2020
 */
public class DualLinkedListMultiset extends RmitMultiset
{




    public Node find(Node root, String item){
        if(root==null)
            return null;
        if(!(item.equals(root.getData())))
            root = find(root.getNext(),item);
        return root;
    }
    Node ascendantNode;
    Node instanceNode;
    StringBuilder stringBuilder;

    public DualLinkedListMultiset(){
        ascendantNode =null;
        instanceNode =null;
    }


    @Override
	public void add(String item) {
        Node node;
        if(ascendantNode == null){
            ascendantNode = new Node(item);
            instanceNode = new Node(item);
            return;
        }

        Node newNode = new Node(item);
        Node prevNode = null;
        Node currNode = ascendantNode;

        if (contains(item)) {
            while (currNode != null) {
                if (currNode.getData().equals(item)) {
                    currNode.instanceIncrement();
                    break;
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
                ascendantNode = newNode;
            } else {
                prevNode.setNext(newNode);
                newNode.setNext(currNode);
            }

        }



        Node header0 = instanceNode;
        boolean b =true;

        while (header0!=null){
            if(item.equals(header0.getData())){
                header0.instanceIncrement();
                b=false;
                break;
            }
            else if(header0.hasNext())
                header0=header0.getNext();
            else
                break;
        }
        if(b){
            assert header0 != null;
            header0.setNext(new Node(item));
        }
        sorting();
    } // end of add()


    public void sorting(){
        Node header = instanceNode;
        Node iterate;
        int tempInstance;
        String tempItem;

        if(header==null)
            return;
        while (header!=null){

            iterate=header.getNext();
            while (iterate != null) {
                if(header.getNumOfInstance()<iterate.getNumOfInstance()){
                    tempInstance=header.getNumOfInstance();
                    tempItem=header.getData();
                    header.setNumOfInstance(iterate.getNumOfInstance());
                    header.setData(iterate.getData());
                    iterate.setData(tempItem);
                    iterate.setNumOfInstance(tempInstance);
                }
                iterate=iterate.getNext();
            }
            header=header.getNext();
        }

    }


    @Override
	public int search(String item) {
        Node header = (Node) find(instanceNode,item);
        if(header==null)
            return searchFailed;
        return header.getNumOfInstance();
    } // end of search()


    @Override
	public List<String> searchByInstance(int instanceCount) {
        Node header = instanceNode;
        List<String> list = new MyList();

        while(header.hasNext()){
            if(header.getNumOfInstance()==instanceCount)
                list.add(header.getData());
            header=header.getNext();
        }
        return list;
    } // end of searchByInstance    


    @Override
	public boolean contains(String item) {
        return search(item)>0;
    } // end of contains()


    @Override
	public void removeOne(String item) {

        Node header0= ascendantNode,
                tmp = null,
                header1= instanceNode,
                tmp0 = null;

        while (header0!=null){
            if(header0.getData().equals(item)){
                header0.instanceDecrement();
                if(header0.getNumOfInstance()<1){
                    if(tmp!=null)
                        tmp.setNext(header0.getNext());
                    else
                        ascendantNode = header0.getNext();
                }
            }
            tmp = header0;
            header0=header0.getNext();
        }
        while (header1!=null){
            if(header1.getData().equals(item)){
                header1.instanceDecrement();
                if(header1.getNumOfInstance()<1){
                    if (tmp0 != null)
                        tmp0.setNext(header1.getNext());
                    else
                        instanceNode = header1.getNext();
                }
            }
            tmp0 = header1;
            header1=header1.getNext();
        }



    } // end of removeOne()


    @Override
	public String print() {
        Node header = instanceNode;
        stringBuilder = new StringBuilder();
        while(header!=null){
            stringBuilder.append(header.getData()).append(":").append(header.getNumOfInstance()).append("\n");
            header=header.getNext();
        }
        return stringBuilder.toString();
    } // end of OrderedPrint


    @Override
	public String printRange(String lower, String upper) {
        Node header = instanceNode;
        stringBuilder = new StringBuilder();
        while (header!=null){
            if(header.getData().compareTo(lower)>=0 && header.getData().compareTo(upper)<=0 )
                stringBuilder.append(header.getData()).append(":").append(header.getNumOfInstance()).append("\n");
            header= header.getNext();
        }
        return stringBuilder.toString();
    } // end of printRange()

    public DualLinkedListMultiset addByNode(Node instanceNode, DualLinkedListMultiset multiset){

        if(multiset.instanceNode==null){
            multiset.instanceNode=instanceNode;
            return multiset;
        }

        Node header = multiset.instanceNode;
        boolean b=true;
        while (header!=null){
            if(instanceNode.getData().equals(header.getData())){
                header.setNumOfInstance(header.getNumOfInstance()+instanceNode.getNumOfInstance());
                b=false;
                break;
            }
            else if(header.hasNext())
                header=header.getNext();
            else
                break;
        }
        if(b){
            assert header != null;
            Node newNode = new Node(header.getData());
            newNode.setNumOfInstance(header.getNumOfInstance());
            header.setNext(newNode);

        }


        return multiset;
    }

    @Override
	public RmitMultiset union(RmitMultiset other) {
        DualLinkedListMultiset multiset = new DualLinkedListMultiset();
        /*
         *alphabetical sorting
         */

        Node node =ascendantNode;
        while(node!=null){
            for(int i=0;i<node.getNumOfInstance();i++)
                multiset.add(node.getData());
            node=node.getNext();
        }


        Node otherNode = ((DualLinkedListMultiset)other).ascendantNode;
        while(otherNode!=null){
            for(int i=0;i<otherNode.getNumOfInstance();i++)
                multiset.add(otherNode.getData());
            otherNode=otherNode.getNext();
        }
        return multiset;
    } // end of union()


    @Override
	public RmitMultiset intersect(RmitMultiset other) {
        DualLinkedListMultiset multiset = new DualLinkedListMultiset();
        Node node =instanceNode;
        while(node!=null){
            if(other.contains(node.getData())){
                int a = Math.min(other.search(node.getData()), node.getNumOfInstance());
                Node newNode = new Node(node.getData());
                newNode.setNumOfInstance(a);
                multiset = addByNode(newNode,multiset);
            }
            node=node.getNext();
        }
        multiset.sorting();


        Node currNode = ascendantNode;
        while (currNode != null) {
            if (other.contains(currNode.getData())) {
                int intersectInstance = Math.min(currNode.getNumOfInstance(), other.search(currNode.getData()));
                for(int i=0;i<intersectInstance;i++)
                    multiset.add(currNode.getData());
            }
            currNode = currNode.getNext();
        }

        return multiset;
    } // end of intersect()


    @Override
	public RmitMultiset difference(RmitMultiset other) {
        DualLinkedListMultiset multiset = new DualLinkedListMultiset();
        Node node =ascendantNode;

        while(node!=null){
            for(int i=0;i<node.getNumOfInstance();i++)
                multiset.add(node.getData());
            node=node.getNext();
        }

        Node temp=null;
        Node setAscent = multiset.ascendantNode;
        Node setInstance = multiset.instanceNode;
        while (setAscent!=null){
            if(other.contains(setAscent.getData()))
                setAscent.setNumOfInstance(setAscent.getNumOfInstance()-other.search(setAscent.getData()));
            if(setAscent.getNumOfInstance()<1){
                if(temp==null || temp.getNumOfInstance()<1)
                    multiset.ascendantNode=setAscent.getNext();
                else
                    temp.setNext(setAscent.getNext());
            }
            temp = setAscent;
            setAscent=setAscent.getNext();
        }
        temp=null;
        while(setInstance!=null){
            if(other.contains(setInstance.getData()))
                setInstance.setNumOfInstance(setInstance.getNumOfInstance()-other.search(setInstance.getData()));
            if(setInstance.getNumOfInstance()<1){
                if(temp==null || temp.getNumOfInstance()<1)
                    multiset.instanceNode=setInstance.getNext();
                else
                    temp.setNext(setInstance.getNext());
            }

            temp = setInstance;
            setInstance=setInstance.getNext();
        }


        return multiset;
    } // end of difference()

} // end of class DualLinkedListMultiset
