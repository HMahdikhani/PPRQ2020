//import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public class PBTree { 
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
        if (i < arr.length) { 
            Node temp = new Node(arr[i]); 
            root = temp; 
  
            root.left = insertLevelOrder(arr, root.left, 2 * i + 1); 
  
            root.right = insertLevelOrder(arr, root.right, 2 * i + 2); 
        } 
        return root; 
    } 
  
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
        Boolean result = false;
        for(int i=0; i<range.length; i+=2){
            result = result || ((range[i] <= qryPoint) && (qryPoint <= range[i+1]));
        }
        return  result;
    }


    
    public static void addToPaths(ArrayList<Character> path){
        String strTemp="";
        for(int i=1; i<path.size(); i++){
            strTemp = strTemp + path.get(i);
        }
        reducedPath[0].add(strTemp);
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
    static int range[] = {5,12}; //{0,7,16,19,24,24,28,29};
    //[Ls.Us] = [L1.U1] U [L2.U2] U ... U [Ls.Us]
    static int b = (int) (Math.log(n)/Math.log(2));
    
    static ArrayList<String>[] reducedPath = new ArrayList[2];

    
    //static TreeMap<Integer, String> nodesInRange = new TreeMap<Integer, String>();
    
    public static String areSibling(String str1, String str2){
        int k1=str1.length();
        int k2=str2.length();
        if ( (k1==k2) && (str1.charAt(k1-1)!=str2.charAt(k2-1)) && str1.substring(0,k1-1).equals(str2.substring(0,k2-1)) ) {
            return str1.substring(0,k1-1);
        }
        return "";
    }
    
    public static boolean printPath(Node root, ArrayList<Character> pathToLeafNode, int leafNode, char visitedEdge) { 
        if (root==null) 
           return false; 
        
        pathToLeafNode.add(visitedEdge);
        
        if (root.data == leafNode)     
           return true; 
           
        if (printPath(root.left, pathToLeafNode, leafNode, '0') || printPath(root.right, pathToLeafNode, leafNode, '1'))
           return true; 
           
        pathToLeafNode.remove(pathToLeafNode.size()-1);
        return false;             
    }

    
    public static void main(String args[]) 
    { 
        //nge = new int []{0,7,11,11,28,29};
        reducedPath[0] = new ArrayList<String>();
        reducedPath[1] = new ArrayList<String>();
    
	    System.out.println("n (leaf nodes): " + n);
	    System.out.println("Range query [L,U]: ");
	    for(int i=0; i <range.length; i+=2){
	        System.out.print("[" + range[i]+",");
	        System.out.print(range[i+1]+"]  ");
	    }
	    System.out.println();
        System.out.println("Tree nodes: ");
        PBTree T = new PBTree(); 
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
        //String ppath[] = new String[1000]; 
        
        
        ArrayList<Character> pathtoleaf = new ArrayList<Character>();
        System.out.println("Nodes in range query:");
        for(int i=0;i<n;i++){
            if (isInRange(i)){
                if (printPath(T.root, pathtoleaf, i, ' ')){
                    addToPaths(pathtoleaf);
                    pathtoleaf.clear();
                }
            }
        }

        System.out.println(reducedPath[0]);
        
        int srcPath = 0;
        int dstPath = 1;
        String strRes = "";
        Boolean ContinueFlg;
        do{
            ContinueFlg = false;
            strRes = "";
            for(int i=0; i<reducedPath[srcPath].size(); i++){
                if(i < reducedPath[srcPath].size()-1){
                    strRes = areSibling(reducedPath[srcPath].get(i),reducedPath[srcPath].get(i+1));
                    if (strRes.equals("")){
                        reducedPath[dstPath].add(reducedPath[srcPath].get(i));
                    }
                    else{
                        reducedPath[dstPath].add(strRes);
                        ContinueFlg=true;
                        i++;
                    }
                }else{
                    reducedPath[dstPath].add(reducedPath[srcPath].get(reducedPath[srcPath].size()-1));
                }
            }
            if(ContinueFlg==true){
                System.out.println(reducedPath[dstPath]);
                reducedPath[srcPath].clear();
                srcPath = (srcPath+1)%2;
                dstPath = (dstPath+1)%2;
            }
        }while(ContinueFlg==true);
        
        System.out.println("Final paths:" + reducedPath[dstPath]);
        int pathLength=0;
        for(String qPath : reducedPath[dstPath]){
            pathLength = pathLength + qPath.length();
        }
        System.out.println("Total length:" + pathLength);
        System.out.println("Maximum length [(lg(n)+2)(lg(n)-1)]:" + (b+2)*(b-1));
 
        
    } 
}
