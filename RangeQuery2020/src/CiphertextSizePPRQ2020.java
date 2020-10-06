//import java.util.List;
//import java.util.Arrays;
//import java.util.List;
import java.util.Random;

//import com.sun.xml.internal.messaging.saaj.soap.Envelope;

import java.math.BigInteger;
import java.util.ArrayList;
public class CiphertextSizePPRQ2020{
	
	
    public static String LCP(String str1, String str2) { //str1<str2
        
        // find the minimum length btw. str1 and str2
        int end = Math.min(str1.length(), str2.length()); 
  
        // find the common prefix 
        int i = 0; 
        while (i < end && str1.charAt(i) == str2.charAt(i) ) 
            i++; 
            
        return str1.substring(0, i); 
    }



	//Range Query Parameters
	static int N=500;//number of IoT devices.
	static int m=28;//m=log2(n) or 2^m=n
	static int n=(int) Math.pow(2, m);//range query upper bound. //Leaf nodes 2^{0,1,2,3,4,5,6,7,...}=1,2,4,8,16,32,64,128,...
    static int range[] = {3,3};//{1,n-2}; //{0,7,16,19,24,24,28,29};
        //[Ls.Us] = [L1.U1] U [L2.U2] U ... U [Ls.Us]
	static int []w= new int[2];//wi is a prepared data from IoT device Di and it is btw. 0 and n-1.
	//BigInteger []cipherW = new BigInteger[N];
	
    
	    
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
    
    public static String invertPath(String strPathIn) {//Give you inverted binary string. Flip 0s to 1s and vice versa.
    	String strTemp = "";
    	for(int i=0; i< strPathIn.length();i++) {
    		if(strPathIn.charAt(i)=='0')
    			strTemp = strTemp  + "1";
    		else
    			strTemp = strTemp  + "0";    		
    	}
    	return strTemp;
    }


    public static void main(String args[]) {

	    
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
		    /*
		     *//////////////////////////////Convert to Binary////////////////////////////////////////
		     
	        for(int i=0;i<n;i++){
	            if (isInRange(i)){
	            	reducedPath[0].add(String.format("%"+m+"s", Integer.toBinaryString(i)).replace(' ', '0'));
	            }	            
	        }
		     //////////////////////////////Convert to Binary////////////////////////////////////////
		    /*
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
	                if (T.printPath(T.root, pathtoleaf, i, ' ')){
	                    addToPaths(pathtoleaf);
	                    pathtoleaf.clear();
     		  	    System.out.println("Nodes in range query:"+i);
	                }
	            }
	        }
      		//////////////////////////////Convert to Tree/////////////////////////////////////////
	        */


	        //System.out.println(reducedPath[0]);
	        
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
	                //System.out.println(reducedPath[dstPath]);
	                reducedPath[srcPath].clear();
	                srcPath = (srcPath+1)%2;
	                dstPath = (dstPath+1)%2;
	            }
	        }while(ContinueFlg==true);
	        
	        System.out.println("Reduced paths:" + reducedPath[dstPath]);
	        int pathLength=0;
	        for(String qPath : reducedPath[dstPath]){
	            pathLength = pathLength + qPath.length();
	        }
	        System.out.println("Total length:" + pathLength);
	        System.out.println("Maximum length [(lg(n)+2)(lg(n)-1)]:" + (m+2)*(m-1));

	        
	        //Invert the reducedPath's items.
	        String rPath="";//Reduced path
	        for(int i=0; i<reducedPath[dstPath].size();i++){
	        	rPath=reducedPath[dstPath].get(i);
	        	reducedPath[dstPath].set(i, invertPath(rPath));	            
	        }
	        
	        
	        System.out.println("Inverted reduced paths:" + reducedPath[dstPath]);
	        
			SymHomSch shs = new SymHomSch();
			//KeyGen(k0,k1,k2)
			//k0>>k2>k1
			//k0: Length of large prime numbers p and q
			//k1: Length of message and message space
			//k2: Length of parameter L and generated random values in encryption method
			int k1 = (int)Math.ceil(Math.log((double)N)/Math.log(2.0))+10;//Same as PPRQ2019
			//int k1=(int)Math.ceil(Math.log((double)N)/Math.log(2.0))+m+2;//N is number of IoT devices and sum og items.
			int k2 = 40;//Same as PPRQ2019
			//int k2=(int)(1.5*k1)<=40?40:(int)(1.5*k1);		
			int k0 = (int)((2*m)*2*k2);
			k0 = (int)(k0+k0*.15);
			//To calculate XOR we need m multilipcation
			//    E()  E()  E()  E()
			//XOR
			//    E()  E()  E()  E()
			//=   E*E  E*E  E*E  E*E
			//We should multiply them cacluate the final
			//So we have (E*E)*(E*E)*(E*E)*(E*E) ==> we need (m+1)+(m) multiplication, so far.
			//To support dummy and non-dummy we need one more extra multiplication. //we DO NOT consider it here.
			shs.KeyGen(k0, k1, k2);//4*k2<<k0 it support 2 multiplication of enc values, enc(a)*enc(b)
			System.out.println("k0: "+ k0 + ", k1: "+ k1 + ", k2: "+k2);
			//support multilipcation 2k2+2K2 = 4*k2 for one multiplication enc(a)*enc(b)
			// 2k2+2k2+2k2 = 6*k2 for two multiplication, enc(a)*enc(b)*enc(c) and 6*k2 = 6*400 << 3500 (k0)
			//2k2+2k2+2k2+2k2 = 8*k2 for three multiplication, enc(a)*enc(b)*enc(c)*enc(d) and 8*k2= 8*400 << 3500
			//To support w multiplication, (w+1)*2K2 << k0 should be staisfied.
			SHSParamters pp = shs.getPublicParams();
			SHSParamters sp = shs.getSecretParams();     
	        
	        //BigInteger[][] encReducedPath = new ArrayList[reducedPath[dstPath].size()];
	        //List<List<BigInteger>> encReducedPath = new ArrayList<List<BigInteger>>();
		    ArrayList<BigInteger[]> encReducedPath = new ArrayList<BigInteger[]>();
		    
	        
	        BigInteger[] biTemp;
	        for(int i=0; i<reducedPath[dstPath].size();i++){
	        	biTemp = new BigInteger[reducedPath[dstPath].get(i).length()];//Length of each path string.
	        	for(int j=0; j<biTemp.length; j++) {
	        		biTemp[j] = shs.Enc(BigInteger.valueOf(Character.getNumericValue(reducedPath[dstPath].get(i).charAt(j))),sp);
    				//System.out.println(shs.Dec(biTemp[j],sp));
    				//System.out.println(biTemp[j].bitLength());
	        	}
	        	encReducedPath.add(biTemp);
	        	biTemp = null;
	        }

	    	System.out.println("Number of encryptions: "+pathLength);
	    	System.out.println("Ciphertexts length: "+ encReducedPath.get(0)[0].bitLength());
	    	
	        /*System.out.println("Encrypted reduced paths: ");
	        for(int i=0; i<encReducedPath.size();i++){
	        	for(int j=0; j<encReducedPath.get(i).length; j++) {
	        		System.out.print("E("+shs.Dec(encReducedPath.get(i)[j],sp)+") ");
	        	}
	        	System.out.println();
	        }*/

	    	////////////////////////Free memory///////////////////////
	    	reducedPath = null;
	    	System.gc();
	    	////////////////////////Free memory///////////////////////	    	
	    	
	    	//Generating Enc(0) and E(1) for IoT devices;		    
	    	BigInteger[] E0 = new BigInteger[2];
	    	BigInteger[] E1 = new BigInteger[2];
	    	for(int i=0; i<2; i++) {
	    		E0[i] = shs.Enc(BigInteger.valueOf(0), sp);
	    		E1[i] = shs.Enc(BigInteger.valueOf(1), sp);
	    	}
	    	

	    	Random rand= new Random();    		
	    	String wBinary[] = new String[2];
	    	ArrayList<BigInteger[]> EWBinary = new ArrayList<BigInteger[]>();
	    	BigInteger[] tmpEBInt;
	    	for (int i=0; i<2; i++) {//Generate random value for each IoT devices Di, 0<=i<=N-1.
	    		//w[i] = rand.nextInt(n);//wi for Di is an integer 0<=wi<=n-1.
	    		w[0]=3;
	    		w[1]=4;
	    		
	    		/////////////////Convert w to binary and compute Enc(w[i]) at each IoT device////////////////
	    		wBinary[i] = String.format("%"+m+"s", Integer.toBinaryString(w[i])).replace(' ', '0');
	    		tmpEBInt = new BigInteger[m];
	    		for(int j=0; j<m; j++) {
	    			if(wBinary[i].charAt(j) == '0') {
	    				tmpEBInt[j] = E0[i].multiply(BigInteger.valueOf(rand.nextInt(9999-1)+1));
	    				//tmpEBInt[j] = shs.Mul(E0[i], BigInteger.valueOf(rand.nextInt(9999-1)+1), pp);
	    				//System.out.println(shs.Dec(tmpEBInt[j],sp));
	    				//System.out.println(E0[i].bitLength() + " *** " + tmpEBInt[j].bitLength());
	    				

	    			}else if(wBinary[i].charAt(j) == '1') {	    				
	    				tmpEBInt[j] = E1[i].add(E0[i].multiply(BigInteger.valueOf(rand.nextInt(9999-1)+1)));
	    				//tmpEBInt[j] = shs.Mul(E1[i], BigInteger.valueOf(rand.nextInt(9999-1)+1), pp);
	    				//System.out.println(shs.Dec(tmpEBInt[j],sp));
	    				//System.out.println(E1[i].bitLength() + " *** " + tmpEBInt[j].bitLength());
	    			}	    			
	    		}
	    		EWBinary.add(i,tmpEBInt);
	    		tmpEBInt = null;//Reset and prepare for next IoT device.	    				
	    	}
	    	
	    		    	
	    	////////////////////////Free memory///////////////////////
	    	E0 = null;
	    	E1 = null;
	    	wBinary = null;
	    	System.gc();
	    	////////////////////////Free memory///////////////////////	    	


	    	////////////////////// IoT Response Calculate XOR ////////////////////// 
	    	ArrayList<ArrayList<BigInteger[]>> tmpIoTResponses = new ArrayList<ArrayList<BigInteger[]>>();
	    	ArrayList<BigInteger[]> tmpIoTResponse;
	    	BigInteger[] tmpIoTRowResponse;
	    	for(int i=0; i<2; i++) {
	    		tmpIoTResponse = new ArrayList<BigInteger[]>();
	    		for(int j=0; j<encReducedPath.size(); j++) {
	    			tmpIoTRowResponse = new BigInteger[encReducedPath.get(j).length];
	    			for( int k=0; k<encReducedPath.get(j).length; k++) {	    				
	    				tmpIoTRowResponse[k] = shs.Add(shs.Add(encReducedPath.get(j)[k], EWBinary.get(i)[k], pp), shs.Mul(encReducedPath.get(j)[k], EWBinary.get(i)[k], pp).multiply(BigInteger.valueOf(-2)), pp);	    				
	    				//System.out.println(tmpIoTRowResponse[k].bitLength());
	    				//System.out.println(shs.Dec(tmpIoTRowResponse[k],sp));	    				
	    			}
	    			tmpIoTResponse.add(j,tmpIoTRowResponse);
	    			tmpIoTRowResponse = null;
	    		}
	    		tmpIoTResponses.add(i,tmpIoTResponse);
	    		tmpIoTResponse = null;
	    	}

	    	/*HASSAN OUTPUT COMMENT 
	    	for(int i=0; i<N; i++) {
	    		System.out.print("IoT["+i+"], w="+w[i]+": ");
	    		for(int j=0; j<encReducedPath.size(); j++) {	    			
	    			for( int k=0; k<encReducedPath.get(j).length; k++) {	    				
	    				System.out.print(shs.Dec(tmpIoTResponses.get(i).get(j)[k], sp));
	    			}
	    			System.out.print(", ");
	    		}
	    		System.out.println();
	    	}
	    	HASSAN OUTPUT COMMENT*/


	    	////////////////////////Free memory///////////////////////
	    	
	    	EWBinary = null;
	    	encReducedPath = null;
	    	tmpIoTResponse = null;
	    	tmpIoTRowResponse = null;
	    	System.gc();
	    	////////////////////////Free memory///////////////////////	    	

	    	
	    	//////////////////////IoT Response Calculate Multiplication in each reduced path //////////////////////
	    	
	    	ArrayList<BigInteger[]> IoTResponseReducedPath = new ArrayList<BigInteger[]>();  
	    	BigInteger[] tmpIoTRowResponseReducedPath;
	    	BigInteger tmpMultiplyRes;
	    	for(int i=0; i<2; i++) {	    		
    			tmpIoTRowResponseReducedPath = new BigInteger[tmpIoTResponses.get(i).size()];
	    		for(int j=0; j< tmpIoTResponses.get(i).size(); j++) {
	    			tmpMultiplyRes = tmpIoTResponses.get(i).get(j)[0];//Initialize with item in index k=0
	    			//System.out.println(shs.Dec(tmpMultiplyRes, sp));
	    			for(int k=1; k<tmpIoTResponses.get(i).get(j).length; k++) {	    				
	    				tmpMultiplyRes = shs.Mul(tmpMultiplyRes, tmpIoTResponses.get(i).get(j)[k], pp);
	    				System.out.println(tmpMultiplyRes.bitLength());
	    				System.out.println(shs.Dec(tmpMultiplyRes,sp));    				
	    				//System.out.println(shs.Dec(tmpMultiplyRes, sp));
	    			}
	    			//System.out.println();
	    			tmpIoTRowResponseReducedPath[j] = tmpMultiplyRes;
	    		}	    		
	    		IoTResponseReducedPath.add(tmpIoTRowResponseReducedPath);
	    		tmpIoTRowResponseReducedPath = null;
	    	}

	    	/*HASSAN OUTPUT COMMENT 
	    	for(int i=0; i<N; i++) {
	    		System.out.print("IoT["+i+"], w="+w[i]+": ");
	    		for(int j=0; j<IoTResponseReducedPath.get(i).length; j++) {
	    			System.out.print(shs.Dec(IoTResponseReducedPath.get(i)[j], sp)+", ");
	    		}
	    		System.out.println();
	    	}
	    	HASSAN OUTPUT COMMENT*/
	    	
	    	
	    	////////////////////////Free memory///////////////////////
	    	
	    	tmpIoTResponses = null;
	    	tmpIoTRowResponseReducedPath = null;
	    	System.gc();
	    	////////////////////////Free memory///////////////////////	    	

	    	BigInteger[] finalIoTResponse = new BigInteger[N];
	    	BigInteger tmpAddRes;
	    	for(int i=0; i<2; i++) {
	    		tmpAddRes = IoTResponseReducedPath.get(i)[0];
	    		for(int j=1; j<IoTResponseReducedPath.get(i).length;j++) {
	    			tmpAddRes = shs.Add(tmpAddRes, IoTResponseReducedPath.get(i)[j],pp);	    					
	    		}
	    		finalIoTResponse[i] = tmpAddRes;
	    	}

	    	for(int i=0; i<2; i++) {
	    		System.out.print("IoT["+i+"], w="+w[i]+": ");
	    		System.out.println(shs.Dec(finalIoTResponse[i],sp)+", ");
    		}

	    	////////////////////////Free memory///////////////////////
	    	
	    	IoTResponseReducedPath = null;
	    	System.gc();
	    	////////////////////////Free memory///////////////////////
	    	
	    	
	    	BigInteger totalQryRes;	    	
	
	    	totalQryRes = finalIoTResponse[0];
	    	for(int i=1; i<2; i++) {
	    		totalQryRes = shs.Add(totalQryRes, finalIoTResponse[i], pp);
    		}
	    	
	    	BigInteger finalQryRes;
	
	    	finalQryRes = shs.Dec(totalQryRes,sp);

	    	
	    	System.out.println(finalQryRes);

	    	
	    	
    }//End of main 
}




    

    

  
