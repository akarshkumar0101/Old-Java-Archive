package util;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Stack;


//THIS IS NOT MY BINARY SERACH TREE
//GOT THIS FROM:
//https://www.cs.cmu.edu/~adamchik/15-121/lectures/Trees/code/BST.java
// TODO make own binary search tree
public class BST <T extends Comparable<T>> implements Iterable<T>
{
   public static void main(String[] args)
   {
      Integer[] a = {1,5,2,7,4};
      BST<Integer> bst = new BST<>();
      for(Integer n : a) bst.insert(n);

      bst.preOrderTraversal();
      System.out.println();

      //testing comparator
      //build a mirror BST with a rule:  Left > Parent > Right
      //code for the comparator at the bottom of the file
      bst = new BST<>(new MyComp1());
      for(Integer n : a) bst.insert(n);

      bst.preOrderTraversal();
      System.out.println();
      bst.inOrderTraversal();
      System.out.println();


      for(Integer n : bst) System.out.print(n);
      System.out.println();

      System.out.println(bst);

      //testing restoring a tree from two given traversals
      bst.restore(new Integer[] {11,8,6,4,7,10,19,43,31,29,37,49},
                      new Integer[] {4,6,7,8,10,11,19,29,31,37,43,49});
      bst.preOrderTraversal();
      System.out.println();
      bst.inOrderTraversal();
      System.out.println();

      //testing diameter
      System.out.println("diameter = " + bst.diameter());
      //testing width
      System.out.println("width = " + bst.width());
   }


   private Node root;
   private Comparator<T> comparator;

   public BST()
   {
      root = null;
      comparator = null;
   }

   public BST(Comparator<T> comp)
   {
      root = null;
      comparator = comp;
   }

   private int compare(T x, T y)
   {
      if(comparator == null) return x.compareTo(y);
      else
      return comparator.compare(x,y);
   }

/*****************************************************
*
*            INSERT
*
******************************************************/
   public void insert(T data)
   {
      root = insert(root, data);
   }
   private Node insert(Node p, T toInsert)
   {
      if (p == null)
         return new Node(toInsert);

      if (compare(toInsert, p.data) == 0)
      	return p;

      if (compare(toInsert, p.data) < 0)
         p.left = insert(p.left, toInsert);
      else
         p.right = insert(p.right, toInsert);

      return p;
   }

/*****************************************************
*
*            SEARCH
*
******************************************************/
   public boolean search(T toSearch)
   {
      return search(root, toSearch);
   }
   private boolean search(Node p, T toSearch)
   {
      if (p == null)
         return false;
      else
      if (compare(toSearch, p.data) == 0)
      	return true;
      else
      if (compare(toSearch, p.data) < 0)
         return search(p.left, toSearch);
      else
         return search(p.right, toSearch);
   }

/*****************************************************
*
*            DELETE
*
******************************************************/

   public void delete(T toDelete)
   {
      root = delete(root, toDelete);
   }
   private Node delete(Node p, T toDelete)
   {
      if (p == null)  throw new RuntimeException("cannot delete.");
      else
      if (compare(toDelete, p.data) < 0)
      p.left = delete (p.left, toDelete);
      else
      if (compare(toDelete, p.data)  > 0)
      p.right = delete (p.right, toDelete);
      else
      {
         if (p.left == null) return p.right;
         else
         if (p.right == null) return p.left;
         else
         {
         // get data from the rightmost node in the left subtree
            p.data = retrieveData(p.left);
         // delete the rightmost node in the left subtree
            p.left =  delete(p.left, p.data) ;
         }
      }
      return p;
   }
   private T retrieveData(Node p)
   {
      while (p.right != null) p = p.right;

      return p.data;
   }

/*************************************************
 *
 *            toString
 *
 **************************************************/

   @Override
public String toString()
   {
      StringBuffer sb = new StringBuffer();
      for(T data : this) sb.append(data.toString());

      return sb.toString();
   }

/*************************************************
 *
 *            TRAVERSAL
 *
 **************************************************/

   public void preOrderTraversal()
   {
      preOrderHelper(root);
   }
   private void preOrderHelper(Node r)
   {
      if (r != null)
      {
         System.out.print(r+" ");
         preOrderHelper(r.left);
         preOrderHelper(r.right);
      }
   }

   public void inOrderTraversal()
   {
      inOrderHelper(root);
   }
   private void inOrderHelper(Node r)
   {
      if (r != null)
      {
         inOrderHelper(r.left);
         System.out.print(r+" ");
         inOrderHelper(r.right);
      }
   }

/*************************************************
 *
 *            CLONE
 *
 **************************************************/

   @Override
public BST<T> clone()
   {
      BST<T> twin = null;

      if(comparator == null)
         twin = new BST<>();
      else
         twin = new BST<>(comparator);

      twin.root = cloneHelper(root);
      return twin;
   }
   private Node cloneHelper(Node p)
   {
      if(p == null)
         return null;
      else
         return new Node(p.data, cloneHelper(p.left), cloneHelper(p.right));
   }

/*************************************************
 *
 *            MISC
 *
 **************************************************/

   public int height()
   {
      return height(root);
   }
   private int height(Node p)
   {
      if(p == null) return -1;
      else
      return 1 + Math.max( height(p.left), height(p.right));
   }

   public int countLeaves()
   {
      return countLeaves(root);
   }
   private int countLeaves(Node p)
   {
      if(p == null) return 0;
      else
      if(p.left == null && p.right == null) return 1;
      else
      return countLeaves(p.left) + countLeaves(p.right);
   }



  //This method restores a BST given preorder and inorder traversals
   public void restore(T[] pre, T[] in)
   {
      root = restore(pre, 0, pre.length-1, in, 0, in.length-1);
   }
   private Node restore(T[] pre, int preL, int preR, T[] in, int inL, int inR)
   {
      if(preL <= preR)
      {
         int count = 0;
         //find the root in the inorder array
         while(pre[preL] != in[inL + count]) count++;

         Node tmp = new Node(pre[preL]);
         tmp.left = restore(pre, preL+1, preL + count, in, inL, inL +count-1);
         tmp.right = restore(pre, preL+count+1, preR, in, inL+count+1, inR);
         return tmp;
      }
      else
         return null;
   }


   //The width of a binary tree is the maximum number of elements on one level of the tree.
   public int width()
   {
      int max = 0;
      for(int k = 0; k <= height(); k++)
      {
         int tmp = width(root, k);
         if(tmp > max) max = tmp;
      }
      return max;
   }
   //rerturns the number of node on a given level
   public int width(Node p, int depth)
   {
      if(p==null) return 0;
      else
      if(depth == 0) return 1;
      else
      return width(p.left, depth-1) + width(p.right, depth-1);
   }

   //The diameter of a tree is the number of nodes
   //on the longest path between two leaves in the tree.
   public int diameter()
   {
      return diameter(root);
   }
   private int diameter(Node p)
   {
      if(p==null) return 0;

      //the path goes through the root
      int len1 = height(p.left) + height(p.right) +3;

      //the path does not pass the root
      int len2 = Math.max(diameter(p.left), diameter(p.right));

      return Math.max(len1, len2);
   }


/*****************************************************
*
*            TREE ITERATOR
*
******************************************************/

   @Override
public Iterator<T> iterator()
   {
      return new MyIterator();
   }
   //pre-order
   private class MyIterator implements Iterator<T>
   {
      Stack<Node> stk = new Stack<>();

      public MyIterator()
      {
         if(root != null) stk.push(root);
      }
      @Override
	public boolean hasNext()
      {
         return !stk.isEmpty();
      }

      @Override
	public T next()
      {
         Node cur = stk.peek();
         if(cur.left != null)
         {
            stk.push(cur.left);
         }
         else
         {
            Node tmp = stk.pop();
            while( tmp.right == null )
            {
               if(stk.isEmpty()) return cur.data;
               tmp = stk.pop();
            }
            stk.push(tmp.right);
         }

         return cur.data;
      }//end of next()

      @Override
	public void remove()
      {

      }
   }//end of MyIterator

/*****************************************************
*
*            the Node class
*
******************************************************/

   private class Node
   {
      private T data;
      private Node left, right;

      public Node(T data, Node l, Node r)
      {
         left = l; right = r;
         this.data = data;
      }

      public Node(T data)
      {
         this(data, null, null);
      }

      @Override
	public String toString()
      {
         return data.toString();
      }
   } //end of Node
}//end of BST

class MyComp1 implements Comparator<Integer>
{
   @Override
public int compare(Integer x, Integer y)
   {
        return y-x;
   }
}