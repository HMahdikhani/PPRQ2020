import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

public class PPRQ2019 {
	
	public static void PrintMats(ArrayList<byte[][]> R){
	    System.out.println(R.size());
	    for(int i=0; i<R.size(); i++){
	        System.out.println("R["+i+"]: ");
	        for(int j=0; j<R.get(i).length; j++){
	            for(int k=0; k<R.get(i)[j].length; k++){
	                System.out.print(R.get(i)[j][k] + "   ");
	            }
	            System.out.println();
	        }
	    }
	}
	
	public static void PrintEncMats(ArrayList<BigInteger[][]> R){
	    System.out.println(R.size());
	    for(int i=0; i<R.size(); i++){
	        System.out.println("cipherR["+i+"]: ");
	        for(int j=0; j<R.get(i).length; j++){
	            for(int k=0; k<R.get(i)[j].length; k++){
	                System.out.print(R.get(i)[j][k] + ",  ");
	            }
	            System.out.println();
	        }
	    }
	}
	
    public static ArrayList<String> Cumulative(String strIn){
        ArrayList<String> arrStrResult = new ArrayList<String>();
        StringBuilder strResult = new StringBuilder("");
	    strResult.append(strIn.charAt(0));
	    arrStrResult.add(strResult.toString());
	    int intTemp=0;
	    for(int i=1; i<strIn.length(); i++){
	        intTemp = Integer.parseInt(arrStrResult.get(i-1)) + Character.getNumericValue(strIn.charAt(i));
	        arrStrResult.add(Integer.toString(intTemp));
	    }
	    return arrStrResult;
    }
	
    public static int HammingWeight(int n) {
        int count = 0;
        if (n==0) return 0;
        while(n!=0) {
        	n = n & (n-1);
        	count++;
        }
        return count;
    }
    
    /*
     * public static int HammingWeight(int n) {
        int count = 0;
        if (n==0) return 0;
        int loop = (int) (Math.log(n)/Math.log(2));
        //System.out.print(loop);
        for (int i = 0; i <=loop ; ++i) {
            if (((n >>> i) & 1) == 1) {
                ++count;
                }
        }
        return count;
    }*/
    
    public static int HammingWeight(String n) {
        int count = 0;
        for(int i=0; i<n.length(); i++){
            if (n.charAt(i) =='1') count++;
        }
        return count;
    }  


	public static void printMatrix(byte M[][]){
	    for(int i=0; i<M.length;i++){
	        for(int j=0; j<M[i].length;j++){
	            System.out.print(M[i][j]+" ");
	        }
	        System.out.println();
	    }
	    System.out.println();
	}
	public static void printMatrix(int M[][]){
	    for(int i=0; i<M.length;i++){
	        for(int j=0; j<M[i].length;j++){
	            System.out.print(M[i][j]+" ");
	        }
	        System.out.println();
	    }
	    System.out.println();
	}

	public static void printMatrix(int M[]){
	    for(int i=0; i<M.length;i++){
	        System.out.print(M[i]+" ");
	    }
	    System.out.println();
	}
	
	public static int[] genRngQryA(int lowerbound, int upperbound, int len) {
		int[] A = new int[len];
	    for(int i=0;i<len;i++){
	        if(i>=lowerbound && i<=upperbound){
	            A[i]=1;
	        }else{
	            A[i]=0;
	        }
	    }
	    return A;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		

	    long startTime;
	    long endTime;
	    long elapsedTime;
	    
		//Range Query Parameters
		int N=500;//number of IoT devices.
		int m=10;//m=log2(n) or 2^m=n
		int n=(int) Math.pow(2, m);//range query upper bound.
		int []w= new int[N];//wi is a prepared data from IoT device Di and it is btw. 0 and n-1.
		BigInteger []cipherW = new BigInteger[N];
		int L=1;
		int U=n/2;//lower and upper bound as well as 0 <= L <= U <= n-1.
		
		//Symmetric Homomorphic Encryption Parametres

		
		int[] A = new int[n];


		//Generating matrices in user side.
	    System.out.println("Generating A ...");
	    A = genRngQryA(L, U, n);
	    //printMatrix(A);
	    

	    startTime=System.currentTimeMillis();
	    ArrayList<Integer> RangeQry = new ArrayList<Integer>();	    
	    for(int i=L; i<=U; i++){	        
	    	RangeQry.add(i);
	    }
	    
	    System.out.println("Query range (binary representation): ");
	    /*for (int Q : RangeQry) {
	        System.out.print(Integer.toBinaryString(n|Q).substring(1)+", ");
	    }*/
	    
	    ArrayList<ArrayList<String>> S = new ArrayList<ArrayList<String>>();
	    ArrayList<ArrayList<ArrayList<String>>> C = new ArrayList<ArrayList<ArrayList<String>>>();

	    ArrayList<String> SElement = new ArrayList<String>();
	    ArrayList<ArrayList<String>> CElement = new ArrayList<ArrayList<String>>();

	    for(int i=0; i<=m; i++){
	        S.add(i, SElement);
	        C.add(i, CElement);
	    }
	    
        int intIndex = 0;
        String tmpStr="";
        for(int i=0; i<=m; i++){
            SElement = new ArrayList<String>();
            CElement = new ArrayList<ArrayList<String>>();
            for (int Q : RangeQry){
                intIndex = HammingWeight(Q);
                if (intIndex == i){
                	tmpStr = Integer.toBinaryString(n|Q).substring(1);
                    SElement.add(tmpStr);
                    CElement.add(Cumulative(tmpStr));
                }
            }
            S.set(i,SElement);
            C.set(i,CElement);
        }

	    System.out.println("");
	    System.out.println("S: ");
	    //System.out.println(S);
	    System.out.println("C: ");
	    //System.out.println(C);
	    
	    ArrayList<byte[][]> R = new ArrayList<byte[][]>();
	    byte[][] RElement;
	    for(int i=0; i<=m; i++){
	        RElement = new byte[i+1][m];
	        R.add(RElement);
	        //System.out.println(R);
	    }
	    
	    
	   // System.out.println(R.get(5).length);

	    for(int i=0; i<=m; i++)
	        for(int j=0; j<R.get(i).length; j++)
	            for(int k=0; k<R.get(i)[j].length; k++){
	                R.get(i)[j][k] = 0;
	            }
	            
   
	    int intTemp = 0;
	    for(int i=0; i<=m; i++){
	        for(int j=0; j<C.get(i).size(); j++){
	            for(int k=0; k<m;k++){
	                intTemp = Integer.parseInt(C.get(i).get(j).get(k));
	                R.get(i)[intTemp][k]=1;
	            }
	        }
	    }
	    endTime = System.currentTimeMillis();
    	elapsedTime = endTime-startTime;
    	System.out.println("bit-Decomposition process time(ms): "+elapsedTime);

	    PrintMats(R);
	    
		SymHomSch shs = new SymHomSch();
		//KeyGen(k0,k1,k2)
		//k0>>k2>k1
		//k0: Length of large prime numbers p and q
		//k1: Length of message and message space
		//k2: Length of parameter L and generated random values in encryption method 

		int k2=40;
		int k1=(int)Math.ceil(Math.log((double)N)/Math.log(2.0))+20;//N is number of IoT devices.
		System.out.println(k1);		
		int k0=(m+1)*2*k2;//2^m=n and n is the range [1..n]
		shs.KeyGen(k0, k1, k2);//4*k2<<k0 it support 2 multiplication of enc values, enc(a)*enc(b)
		//support multilipcation 2k2+2K2 = 4*k2 for one multiplication enc(a)*enc(b)
		// 2k2+2k2+2k2 = 6*k2 for two multiplication, enc(a)*enc(b)*enc(c) and 6*k2 = 6*400 << 3500 (k0)
		//2k2+2k2+2k2+2k2 = 8*k2 for three multiplication, enc(a)*enc(b)*enc(c)*enc(d) and 8*k2= 8*400 << 3500
		//To support w multiplication, (w+1)*2K2 << k0 should be staisfied.
		SHSParamters pp = shs.getPublicParams();
		SHSParamters sp = shs.getSecretParams();
		
		
		System.out.println("N = " + pp.getParams().get(0).bitLength() + " " + pp.getParams().get(0));
		System.out.println("P = " + sp.getParams().get(0).bitLength() + " " + sp.getParams().get(0));
		System.out.println("Q = " + sp.getParams().get(3).bitLength() + " " + sp.getParams().get(3));
		System.out.println("N = " + sp.getParams().get(2).bitLength() + " " + sp.getParams().get(2));
		System.out.println("L = " + sp.getParams().get(1).bitLength() + " " + sp.getParams().get(1));
		

	    ArrayList<BigInteger[][]> cipherR = new ArrayList<BigInteger[][]>();
	    BigInteger[][] cipherRElement;	    
	    for(int i=0; i<=m; i++){
	    	cipherRElement = new BigInteger[i+1][m];
	    	cipherR.add(cipherRElement);
	        //System.out.println(R);
	    }
	    startTime=System.currentTimeMillis();
	    for(int i=0; i<=m; i++)
	        for(int j=0; j<cipherR.get(i).length; j++)
	            for(int k=0; k<cipherR.get(i)[j].length; k++){
	            	cipherR.get(i)[j][k] = shs.Enc(BigInteger.valueOf(R.get(i)[j][k]), sp);
	            	}
    	endTime=System.currentTimeMillis();
    	elapsedTime = endTime-startTime;
    	System.out.println("Encryption time(ms): "+elapsedTime);
    	System.out.println("Number of encryptions: "+(m*(m+1)*(m+2))/2);

    	System.out.println(cipherR.get(0)[0][0].bitLength());
	    //PrintEncMats(cipherR);

    	
    	Random rand= new Random();    		
    	for (int i=0; i<N; i++) {//Generate random value for each IoT devices Di, 0<=i<=N-1.
    		w[i] = rand.nextInt(n);//wi for Di is an integer 0<=wi<=n-1.
    		//System.out.println(w[i]);
    	}
    	int tmpWi = -1;  
    	String tmpBinaryWi = "";    	
    	int tmpHWWi = -1;
    	int tmpResult;
    	BigInteger tmpRes = BigInteger.ZERO;
    	ArrayList<String> tmpCumulativeWi = new ArrayList<String>();
    	for(int i=0; i<N; i++) {
    	    startTime=System.nanoTime();	
    		tmpWi = w[i];
    		//System.out.println(tmpWi);
    		tmpBinaryWi = Integer.toBinaryString(n|tmpWi).substring(1);
    		//System.out.println(tmpBinaryWi);
    		tmpHWWi = HammingWeight(tmpWi);
    		//System.out.println(tmpHWWi);
    		tmpCumulativeWi = Cumulative(tmpBinaryWi);
    		//System.out.println(tmpCumulativeWi);
    		tmpRes = cipherR.get(tmpHWWi)[Integer.parseInt(tmpCumulativeWi.get(0))][0];
    		for(int j=1;j<m;j++){    			
    			tmpRes = SymHomSch.Mul(tmpRes, cipherR.get(tmpHWWi)[Integer.parseInt(tmpCumulativeWi.get(j))][j] , pp);    		      		  
    		}    	
    		//tmpResult = shs.Dec(tmpRes, sp).intValue();
    		//System.out.println(tmpResult + " ** " + "L:" + L + ", U:" + U);
    		//if((tmpWi>=L && tmpWi<=U)&&(tmpResult==0)){System.out.println("Error"); System.exit(0);}
    		//System.out.println("===================");
        	endTime=System.nanoTime();
        	elapsedTime = endTime-startTime;
        	cipherW[i] = tmpRes;
        	//System.out.println("Preparing response at IOT device D("+i+") - time(ns): "+elapsedTime);
        	System.out.println("time(ns): "+elapsedTime);
        }
    	
    	startTime=System.nanoTime();
    	BigInteger finalResult = cipherW[0];    	
    	for(int i=1;i<N;i++) {//Aggregation on Fog node.
    		//System.out.println(shs.Dec(cipherW[i], sp));
    		finalResult = SymHomSch.Add(finalResult, cipherW[i], pp); 
    		//System.out.println(shs.Dec(finalResult, sp));
    	}
    	
    	endTime=System.nanoTime();
    	elapsedTime = endTime-startTime;
    	System.out.println("Response aggregation at fog - time(ns): "+elapsedTime);
    	
    	
    	startTime=System.nanoTime();
    	int finalQryResult=shs.Dec(finalResult, sp).intValue();
    	endTime=System.nanoTime();
    	elapsedTime = endTime-startTime;
    	System.out.println("Response recovery at user side - time(ns): "+elapsedTime);    	
    	System.out.println(finalQryResult);
	}

}
