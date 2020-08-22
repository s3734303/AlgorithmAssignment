package implementation;

import java.util.List;

/**
 * BST implementation of a multiset.  See comments in RmitMultiset to
 * understand what each overriden method is meant to do.
 *
 * @author Jeffrey Chan & Yongli Ren, RMIT 2020
 */
public class BstMultiset extends RmitMultiset
{
    public class Node{
        private Node left;
        private Node right;
        private String set;


        public Node(String set) {
            this.set=set;
        }
        public void setLeft(Node left) {
            this.left = left;
        }
        public void setRight(Node right){
            this.right = right;
        }
        public Node LeftNode(){
            return left;
        }
        public Node RightNode(){
            return right;
        }
        public boolean hasLeaf(){if(left!=null || right!=null)return true; return false;}
    }
    Node root=null;
    @Override
	public void add(String item) {
        if(root==null)
            root= new Node(item);
        return;

    } // end of add()


    @Override
	public int search(String item) {
        // Implement me!

        // Placeholder, please update.
        return searchFailed;
    } // end of search()


    @Override
	public List<String> searchByInstance(int instanceCount) {

        // Placeholder, please update.
        return null;
    } // end of searchByInstance    


    @Override
	public boolean contains(String item) {
        // Implement me!

        // Placeholder, please update.
        return false;
    } // end of contains()


    @Override
	public void removeOne(String item) {
        // Implement me!
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

        // Placeholder, please update.
        return null;
    } // end of intersect()


    @Override
	public RmitMultiset difference(RmitMultiset other) {

        // Placeholder, please update.
        return null;
    } // end of difference()

} // end of class BstMultiset
