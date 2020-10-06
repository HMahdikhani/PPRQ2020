//import java.util.HashMap;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.Arrays;

public class Tree { 
    Node root; 
  
    // Tree Node 
    static class Node { 
        int data; 
        Node left, right; 
        Node(int data) 
        { 
            this.data = data; 
            this.left = null; 
            this.right = null; 
        } 
    } 
  
    // Function to insert nodes in level order 
    public Node insertLevelOrder(int[] arr, Node root, int i) 
    { 
        // Base case for recursion 
        if (i < arr.length) { 
            Node temp = new Node(arr[i]); 
            root = temp; 
  
            // insert left child 
            root.left = insertLevelOrder(arr, root.left, 2 * i + 1); 
  
            // insert right child 
            root.right = insertLevelOrder(arr, root.right, 2 * i + 2); 
        } 
        return root; 
    } 
  
    // Function to print tree nodes in InOrder fashion 
    public void inOrder(Node root) 
    { 
        if (root != null) { 
            inOrder(root.left); 
            System.out.print(root.data + " "); 
            inOrder(root.right); 
        } 
    } 
  
     public void preOrder(Node root) 
    { 
        if (root != null) { 
            preOrder(root.left); 
            preOrder(root.right); 
            System.out.print(root.data + " "); 

        } 
    } 
    
    public void leafNodes(Node root) 
    { 
        if (root != null) { 
            if ((root.left == null)&&(root.right == null))
            {
                System.out.print(root.data + " "); 
            }
            else
            {
                leafNodes(root.left);
                leafNodes(root.right);
            }
        }
        
    } 
    
    public static Boolean isInRange(int qryPoint){
        return  ((L <= qryPoint) && (qryPoint <= U));
    }
    
    void printPaths(Node root, String[] path, int pathLen, String visited){
        
        //if(root==null)  return;
        
        path[pathLen]=visited;
        pathLen++;


        if ( (root.left == null) && (root.right == null) && (isInRange(root.data)) )
        {  
            //System.out.print(String.format("%1$5s:   ", Integer.toString(root.data)));
            //System.out.print(root.data + ":  ");
            addToNodesInRange(root.data,path, pathLen);  
        }  
        else
        {  
            if(root.left != null)
                printPaths(root.left, path, pathLen, "0");
            if(root.right != null)
                printPaths(root.right, path, pathLen, "1");
        }
    }
    
    void addToNodesInRange(int nodeVal, String ints[], int len)  {
        String strTemp = "";
        for (int i = 0; i < len; i++){  
           //System.out.print(ints[i] + " ");  
           strTemp = strTemp + ints[i];
        } 
        //Put into HashMap
        nodesInRange.put(nodeVal, strTemp);
        //System.out.println();
    }
    
    
    public static String LCP(String str1, String str2) { //str1<str2
        
        // find the minimum length btw. str1 and str2
        int end = Math.min(str1.length(), str2.length()); 
  
        // find the common prefix 
        int i = 0; 
        while (i < end && str1.charAt(i) == str2.charAt(i) ) 
            i++; 
            
        return str1.substring(0, i); 
    }

    static int n = 16; //Leaf nodes 2^{0,1,2,3,4,5,6,7,...}=1,2,4,8,16,32,64,128,...
    static int L = 1;
    static int U = 14;
    static int b = (int) (Math.log(n)/Math.log(2));
    
    static TreeMap<Integer, String> nodesInRange = new TreeMap<Integer, String>();
    
    public static String areSibling(String str1, String str2){
        int k1=str1.length();
        int k2=str2.length();
        if ( (k1==k2) && (str1.charAt(k1-1)!=str2.charAt(k2-1)) && str1.substring(0,k1-1).equals(str2.substring(0,k2-1)) ){
            return str1.substring(0,k1-1);
        }
        return "";
    }
    
    public static void main(String args[]) 
    { 
	    System.out.println("n (leaf nodes): " + n);
	    System.out.println("Lower bound: " + L);
	    System.out.println("Upper bound: " + U);
        System.out.println("Tree nodes: ");
        Tree T = new Tree(); 
        //int arr[] = { -1, -1, -1, 0, 1, 2, 3};
        //int arr[] = { -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7 }; 
        //Total number of nodes: 1+2+4+8+... = t1*(1-2^(b+1))/(1-2) = (1-n*2)/1-2 = n*2 -1
        int treeNodes[] = new int[2*n-1]; //n: number of leaf nodes.
        for(int i=0; i<=n-2; i++){
            treeNodes[i] = -1;
        }
        
        for(int i=n-1; i<2*n-1; i++){
            treeNodes[i] =  i - (n-1);
        }
        
        System.out.println(Arrays.toString(treeNodes));
        
        T.root = T.insertLevelOrder(treeNodes, T.root, 0); 
        //t2.inOrder(t2.root);
        //System.out.println();
        //t2.preOrder(t2.root);
        //System.out.println();
        //t2.leafNodes(t2.root); 
        //System.out.println();
        String ppath[] = new String[1000]; 
        System.out.println("Nodes in range query:");
        T.printPaths(T.root, ppath, 0, "");
        for (Integer i : nodesInRange.keySet()) {
            System.out.println(String.format("%1$5s:   ", i) + nodesInRange.get(i));
        } 
        
        TreeMap<Integer, String> subTrees = new TreeMap<Integer, String>();
        
        int start;
        int end;
        Entry<Integer,String> first = nodesInRange.firstEntry();
        start = first.getKey();
        if (start % 2 != 0) {
            subTrees.put(first.getKey(),first.getValue());
            start = start+1;
        }

        Entry<Integer,String> last = nodesInRange.lastEntry();
        end = last.getKey();
        if (end % 2 == 0) {
            subTrees.put(last.getKey(),last.getValue());
            end = end-1;
        }
        /*
        key => treemap.keySet().toArray()[0]
        value => treemap.get(key); 
        OR (if you just want value)
        treemap.values().toArray()[0]; 
        */
        
        //start = nodesInRange.firstKey();
        //end = nodesInRange.lastKey();
        //System.out.println(start);
        //System.out.println(end);;
        
       
        int current = start;
        String strRes="";
        for (int i=start; i<end; i++) {
            strRes = areSibling(nodesInRange.get(i),nodesInRange.get(i+1));
            if ( strRes.length()!=0 ){
                subTrees.put(current, strRes);
                i++;
                current++;
               //System.out.println(nodesInRange.get(i)+" "+nodesInRange.get(i+1));    
            }else{
                subTrees.put(current,nodesInRange.get(i));
                current++;
            }
        }
        
        System.out.println(subTrees);
        
        /*for(int i=start; i<=end;i++){
            System.out.println(nodesInRange.get(i));
        }
        System.out.println(subTrees);


        /*for (Integer i : nodesInRange.keySet()){
            System.out.println(i);
            System.out.println(i);
        }
        */
        
        
        
        
        
        














    } 
} 




























