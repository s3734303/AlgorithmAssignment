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

    class InstanceNode extends Node{
        int instance=1;
        public InstanceNode(String data) {
            super(data);
        }
        public InstanceNode getNext(){
            return (InstanceNode) next;
        }
        public void setNext(InstanceNode node){
            next= node;
        }
        public void setInstance(int instance){
            this.instance = instance;
        }
    }


    public Node find(Node root, String item){
        if(root==null)
            return null;
        if(!(item.equals(root.getData())))
            root = find(root.next,item);
        return root;
    }
    Node ascendantNode;
    InstanceNode instanceNode;
    StringBuilder stringBuilder;
    int size;
    public DualLinkedListMultiset(){
        ascendantNode =null;
        instanceNode =null;
        size=0;
    }


    @Override
	public void add(String item) {
        Node node;
        if(ascendantNode == null){
            ascendantNode = new Node(item);
            instanceNode = new InstanceNode(item);
            size++;
            return;
        }
        Node header = ascendantNode;

        node = new Node(item);

        if(item.compareTo(header.data)<0){
            node.setNext(header);
            ascendantNode=node;
        }
        else
            {
            while(header.hasNext()){
                if(item.compareTo(header.next.getData())>0){
                    header=header.getNext();
                    continue;
                }
                break;
            }
            node.setNext(header.next);
            header.setNext(node);
        }






        InstanceNode header0 = instanceNode;
        boolean b =true;

        while (header0!=null){
            if(item.equals(header0.data)){
                header0.instance++;
                b=false;
                break;
            }
            else if(header0.hasNext())
                header0=header0.getNext();
            else
                break;
        }
        if(b){
            header0.setNext(new InstanceNode(item));
            size++;
        }
        sorting();


    } // end of add()


    public void sorting(){
        InstanceNode header = instanceNode;
        InstanceNode iterate;
        int tempInstance;
        String tempItem;

        if(header==null)
            return;
        while (header!=null){

            iterate=header.getNext();
            while (iterate != null) {
                if(header.instance<iterate.instance){
                    tempInstance=header.instance;
                    tempItem=header.getData();
                    header.setInstance(iterate.instance);
                    header.setData(iterate.getData());
                    iterate.setData(tempItem);
                    iterate.setInstance(tempInstance);
                }
                iterate=iterate.getNext();
            }
            header=header.getNext();
        }

    }


    @Override
	public int search(String item) {
        InstanceNode header = (InstanceNode) find(instanceNode,item);
        if(header==null)
            return searchFailed;
        return header.instance;
    } // end of search()


    @Override
	public List<String> searchByInstance(int instanceCount) {
        InstanceNode header = instanceNode;
        List<String> list = new MyList();

        while(header.hasNext()){
            if(header.instance==instanceCount)
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

        Node header,tmp = null;
        header = ascendantNode;
        while (header!=null){
            if(header.getData().equals(item)){
                if(tmp!=null)
                    tmp.setNext(header.getNext());
                else
                    ascendantNode = header.getNext();
            }
            tmp = header;
            header=header.getNext();
        }

        InstanceNode header0,tmp0 = null;
        header0 = instanceNode;
        while (header0!=null){
            if(header0.getData().equals(item)){
                header0.instance--;
                if(header0.instance<1){
                    if (tmp0 != null)
                        tmp0.setNext(header0.getNext());
                    else
                        instanceNode = header0.getNext();
                }
            }
            tmp0 = header0;
            header0=header0.getNext();
        }



    } // end of removeOne()


    @Override
	public String print() {
        InstanceNode header = instanceNode;
        stringBuilder = new StringBuilder();
        while(header!=null){
            stringBuilder.append(header.getData()+":"+header.instance+"\n");
            header=header.getNext();
        }
        return stringBuilder.toString();
    } // end of OrderedPrint


    @Override
	public String printRange(String lower, String upper) {
        Node header = ascendantNode;
        int counter = 1;
        stringBuilder = new StringBuilder();
        if(!ascendantNode.hasNext())
            stringBuilder.append(header.getData()+":"+counter+"\n");
        else{
            while (header.hasNext()){
                if(header.getData().compareTo(lower)>=0 && header.getData().compareTo(upper)<=0 ){
                    while (header.getData().equals(header.getNext().getData())){

                        counter++;
                        header=header.getNext();
                    }
                    stringBuilder.append(header.getData()+":"+counter+"\n");
                    counter=1;
                }
                header= header.getNext();
            }
        }
        return stringBuilder.toString();
    } // end of printRange()


    @Override
	public RmitMultiset union(RmitMultiset other) {
        DualLinkedListMultiset multiset = new DualLinkedListMultiset();
        Node node =ascendantNode;
        while(node!=null){
            multiset.add(node.data);
            node=node.getNext();
        }
        Node otherNode = ((DualLinkedListMultiset)other).ascendantNode;
        while(otherNode!=null){
            multiset.add(otherNode.data);
            otherNode=otherNode.getNext();
        }


        return multiset;
    } // end of union()


    @Override
	public RmitMultiset intersect(RmitMultiset other) {
        RmitMultiset multiset = new DualLinkedListMultiset();
        InstanceNode node =instanceNode;
        while(node!=null){
            if(other.contains(node.getData())){
                int a,b=other.search(node.getData());
                a = Math.min(b, node.instance);
                for(int i=0;i<a;i++)
                    multiset.add(node.getData());
            }
            node=node.getNext();
        }
        return multiset;
    } // end of intersect()


    @Override
	public RmitMultiset difference(RmitMultiset other) {
        DualLinkedListMultiset multiset = new DualLinkedListMultiset();
        Node node =ascendantNode;
        while(node!=null){
            multiset.add(node.data);
            node=node.getNext();
        }
        Node otherNode = ((DualLinkedListMultiset)other).ascendantNode;
        while(otherNode!=null){
            multiset.removeOne(otherNode.getData());
            otherNode= otherNode.getNext();
        }
        return multiset;
    } // end of difference()

} // end of class DualLinkedListMultiset
