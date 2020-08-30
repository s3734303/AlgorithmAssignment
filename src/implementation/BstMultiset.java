package implementation;

import java.util.List;

/**
 * BST implementation of a multiset.  See comments in RmitMultiset to
 * understand what each overridden method is meant to do.
 *
 * @author Jeffrey Chan & Yongli Ren, RMIT 2020
 */
public class BstMultiset extends RmitMultiset
{


    public static class Tree {
        Tree left;
        Tree right;
        String item;
        int instance=1;
        public Tree(String item) {
            this.item =item;
        }
        public String getSet(){return item;}
        public void setLeft(Tree left) {
            this.left = left;
        }
        public void setRight(Tree right){
            this.right = right;
        }
        public Tree LeftNode(){
            return left;
        }
        public Tree RightNode(){
            return right;
        }
        public boolean hasLeaf(){
            return left != null || right != null;
        }
    }

    public Tree addNode(Tree node, Tree newNode){
        if(node == null){
            node = newNode;
            return node;
        }
        int order = newNode.getSet().compareTo(node.getSet());
        if(newNode.getSet().equals(node.getSet())){
            node.instance++;
        }
        else if(order<0){
            node.setLeft(addNode(node.LeftNode(),newNode));
        }
        else if(order>0){
            node.setRight(addNode(node.RightNode(),newNode));
        }

        return node;
    }
    Tree root;
    List<String> list;
    StringBuilder stringBuilder;

    public BstMultiset(){
        root=null;
        stringBuilder = new StringBuilder();
    }
    public Tree searchNode(Tree root, String item){
        if(root==null)
            return null;
        int compare = item.compareTo(root.getSet());
        if(root.getSet().equals(item))
            return root;
        if(compare>0)
            return searchNode(root.RightNode(),item);
        else
            return searchNode(root.LeftNode(),item);
    }

    public Tree lastNode(Tree node){
        while(node.RightNode()!=null)
            node = node.RightNode();
        return node;
    }

    public BstMultiset addTree(BstMultiset origin,Tree adder){
        if(adder!=null){
            addTree(origin,adder.left);
            origin.add(adder.item);
            addTree(origin,adder.right);
        }
        return origin;
    }



    public Tree removeNode(Tree deleteNode,Tree root){
        if(root==null)
            return null;
        if(deleteNode.item.equals(root.item)){
            if(!root.hasLeaf())
                return null;
            else if(root.LeftNode() !=null && root.RightNode() !=null){
                Tree pr = lastNode(root.LeftNode());
                root.setLeft(removeNode(pr,root.LeftNode()));
                Tree pL = root.left;
                Tree pR = root.right;
                root = new Tree(pr.item);
                root.setRight(pR);
                root.setLeft(pL);
            }
            else if(root.LeftNode()!=null)
                root = root.LeftNode();
            else if(root.RightNode()!=null)
                root = root.RightNode();
        }
        else if(deleteNode.item.compareTo(root.item)<=0){
            root.setLeft(removeNode(deleteNode,root.LeftNode()));
        }
        else
            root.setRight(removeNode(deleteNode,root.RightNode()));
        return root;

    }

    @Override
	public void add(String item) {
        root = addNode(root, new Tree(item));
    } // end of add()

    @Override
	public int search(String item) {
        Tree header= root;
        header = searchNode(header,item);
        if(header==null)
            return searchFailed;
        return header.instance;
    } // end of search()


    public BstMultiset deduction(Tree root,BstMultiset set1,BstMultiset set2){
        if(root !=null){
            deduction(root.left,set1,set2);

            if(set2.contains(root.item))
                set1.removeOne(root.item);
            deduction(root.right,set1,set2);
        }
        return set1;
    }

    public BstMultiset common(Tree root,BstMultiset set,Tree setRoot,BstMultiset out){
        if(root!=null){
            common(root.left,set,setRoot,out);
            if(set.contains(root.item)){
                for(int i=0;i<Math.min(root.instance,set.searchNode(setRoot,root.item).instance);i++)
                    out.add(root.item);
            }
            common(root.right,set,setRoot,out);
        }
        return out;
    }
    List<Tree> printSort;
    public void order(Tree node,int func,int instanceCount,String lower,String upper){
        if(node ==null)
            return;
        order(node.left,func,instanceCount,lower,upper);
        switch (func){
            case(0)://print
                printSort.add(node);
                break;
            case(1)://searchByInstance
                if(instanceCount==node.instance)
                    list.add(node.item);
                break;
            case(2):
                if(node.item.compareTo(lower)>=0 && node.item.compareTo(upper)<=0)
                    stringBuilder.append(node.item).append(":").append(node.instance).append("\n");
                break;

        }
        order(node.right,func,instanceCount,lower,upper);
    }
    @SuppressWarnings("unchecked")
    @Override
	public List<String> searchByInstance(int instanceCount) {

        list = new MyList();
        order(root,1,instanceCount,null,null);
        return list;
    } // end of searchByInstance


    @Override
	public boolean contains(String item) {
        return searchNode(root, item) != null;
    } // end of contains()


    @Override
	public void removeOne(String item) {
        Tree target =searchNode(root,item);
        if(target!=null){
            target.instance--;
            if(target.instance<1)
                root =removeNode(target,root);
        }


    } // end of removeOne()

    @SuppressWarnings("unchecked")
    @Override
	public String print() {
        stringBuilder = new StringBuilder();
        printSort= new MyList();
        order(root,0,-1,null,null);
        for(int i=0;i<printSort.size()-1;i++)
            for(int j=0;j<printSort.size()-i-1;j++)
                if(printSort.get(j).instance<printSort.get(j+1).instance)
                {
                    Tree temp = new Tree(printSort.get(j).item);
                    temp.instance = printSort.get(j).instance;
                    printSort.set(j,printSort.get(j+1));
                    printSort.set(j+1,temp);
                }
        for(Tree t : printSort)
            stringBuilder.append(t.item).append(":").append(t.instance).append("\n");


        return stringBuilder.toString();
    } // end of OrderedPrint


    @Override
	public String printRange(String lower, String upper) {
        stringBuilder = new StringBuilder();
        order(root,2,-1,lower,upper);
        return stringBuilder.toString();
    } // end of printRange()


    @Override
	public RmitMultiset union(RmitMultiset other) {
        BstMultiset multiset = new BstMultiset();
        multiset.root=clone(root);
        Tree branch = clone(((BstMultiset)other).root);
        multiset=addTree(multiset,branch);
        return multiset;
    } // end of union()


    @Override
	public RmitMultiset intersect(RmitMultiset other) {
        BstMultiset multiset = new BstMultiset();
        multiset= common(root, (BstMultiset) other, ((BstMultiset) other).root,multiset);
        return multiset;
    } // end of intersect()


    @Override
	public RmitMultiset difference(RmitMultiset other) {
        BstMultiset multiset = new BstMultiset();
        multiset.root=clone(root);
        multiset= deduction(multiset.root,multiset, (BstMultiset) other);
        return multiset;
    } // end of difference()

    public Tree clone(Tree origin){
        if(origin ==null) return null;
        Tree tree = new Tree(origin.getSet());
        tree.instance= origin.instance;
        tree.left=clone(origin.left);
        tree.right=clone(origin.right);
        return tree;
    }


} // end of class BstMultiset
