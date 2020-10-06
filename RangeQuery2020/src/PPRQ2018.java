import java.math.BigInteger;
import java.util.Random;
import it.unisa.dia.gas.jpbc.Element;

public class PPRQ2018 {

	
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
	
	public static int[][] genMatR(int[] M) {
		int len=(int)Math.sqrt(M.length);
		int[][] R =new int[len][len];
		int indx=-1;
		for(int i=0; i<len;i++){
			for(int j=0; j<len;j++){
				indx = j+i*len;
				if (M[indx]==1){
					R[i][j]=1;
					}
				else{
					R[i][j]=0;
					}
				}
			}
		return R;
	}

	public static boolean isAllZero(int[] vec){
	    for(int i=0; i< vec.length; i++){
	        if (vec[i]==1){
	            return false;
	        }
	    }
	    return true;
	}
	     
	public static boolean isAllOne(int[] vec){
	    for(int i=0; i< vec.length; i++){
	        if (vec[i]==0){
	            return false;
	        }
	    }
	    return true;
	}
	     
	public static byte rowStatus(int[] vec){
	    if (isAllZero(vec)) return 0;
	    if (isAllOne(vec)) return 1;
	    return -1;
	}

	public static boolean isColZero(int[][] M, int col){
	    for(int i=0; i< M.length; i++){
	        if (M[i][col]==1){
	        	return false;
	        	}
	        }
	    return true;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		//Range Query Parameters
		int N=500;//number of IoT devices.
		int m=14;//m*m=n
		int n=m*m;//range query upper bound.
		int []w= new int[N];//wi is a prepared data from IoT device Di and it is btw. 0 and n-1. 
		int L=1;
		int U=n/2;//lower and upper bound as well as 0 <= L <= U <= n-1.
		
		//BGN Parametres
        BGN bgn = new BGN();
        // Key Generation
        bgn.keyGeneration(512);
        BGN.PublicKey pubkey = bgn.getPubkey();
        BGN.PrivateKey prikey = bgn.getPrikey();

	    long startTime;
	    long endTime;
	    long elapsedTime;
        
		int[] A=new int[n];
		int[][] R=new int[m][m];
		int[][] R1=new int[m][m];
		int[][] R2=new int[m][m];
		int[][] R3=new int[m][m];
	    int[] Y1= new int[m];//1-Y1
	    int[] X1= new int[m];//X'1
	    int[] X2= new int[m];//X'2
	    int[] Y3= new int[m];//1-Y3
	    int[] X3= new int[m];//X'3
	    Element[] EY1= new Element[m];//1-Y1
	    Element[] EX1= new Element[m];//X'1
	    Element[] EX2= new Element[m];//X'2
	    Element[] EY3= new Element[m];//1-Y3
	    Element[] EX3= new Element[m];//X'3
	    
		
		//Generating matrices in user side.
		System.out.println("n= "+n+", √n= "+m);
		System.out.println("L= "+L+", U= "+U);
	    System.out.println("Generating A ...");
	    A = genRngQryA(L, U, n);
	    //printMatrix(A);
	    
	    startTime=System.currentTimeMillis();
	    System.out.println("Generating R ...");
	    R = genMatR(A);
	    //printMatrix(R);
	    System.out.println("Generating R1, R2, R3 ...");
	    int lastRoI=-1;
	    for(int i=0;i<m;i++){
	    	if(rowStatus(R[i])==0){
	    		for(int j=0;j<m;j++){//row of 0s
	    			R1[i][j]=0;
	                R2[i][j]=0;
	                R3[i][j]=0;
	                }
	    		}
	    	else
	    		if(rowStatus(R[i])==1){//row of 1s
	    			for(int j=0;j<m;j++){
	    				R2[i][j]=1;
	    				}
	    			lastRoI = i;
	        }else if(rowStatus(R[i])==-1){//row with 0s and 1s
	                  if(lastRoI==-1){
	                      for(int j=0;j<m;j++){
	                          R1[i][j]=R[i][j];
	                      }
	                      lastRoI = i;
	                      }else{
	                    	  for(int j=0;j<m;j++){
	                    		  R3[i][j]=R[i][j];
	                    		  }
	                    	  }
	                  }
	    	}
	    System.out.println("R1:");
	    //printMatrix(R1);
	    System.out.println("R2:");
	    //printMatrix(R2);
	    System.out.println("R3:");
	    //printMatrix(R3);
	    
	    for(int i=0;i<m;i++){
	        if(isColZero(R1,i)) Y1[i]=0; else Y1[i]=1;
	        if(isColZero(R3,i)) Y3[i]=0; else Y3[i]=1;
	    }
	    
	    for(int i=0;i<m;i++){
	        if(rowStatus(R1[i])==-1) X1[i]=1; else X1[i]=0;
	        if(rowStatus(R3[i])==-1) X3[i]=1; else X3[i]=0;
	    }

	    for(int i=0;i<m;i++){
	        if(rowStatus(R2[i])==1) X2[i]=1; else X2[i]=0;
	    }

	    endTime = System.currentTimeMillis();
    	elapsedTime = endTime-startTime;
    	System.out.println("Decomposition process (generating R, Ris, and related 5 vectors) time(ms): "+elapsedTime);
    	
	    System.out.println("Y1: ");
	    printMatrix(Y1);
	    
	    System.out.println("X1: ");
	    printMatrix(X1);
	    
	    System.out.println("X2: ");
	    printMatrix(X2);
	    
	    System.out.println("Y3: ");
	    printMatrix(Y3);
	    
	    System.out.println("X3: ");
	    printMatrix(X3);


	    System.out.println("Encrypting vectors X1,Y1,X2,X3,Y3 ...");
	    try {
	    	startTime=System.currentTimeMillis();
	    	for(int i=0;i<m;i++) {
	    		EY1[i] = BGN.encrypt(Y1[i], pubkey);
	    		EX1[i] = BGN.encrypt(X1[i], pubkey);
	    		EX2[i] = BGN.encrypt(X2[i], pubkey);	    		
	    		EY3[i] = BGN.encrypt(Y3[i], pubkey);
	    		EX3[i] = BGN.encrypt(X3[i], pubkey);
	    		}
	    	endTime=System.currentTimeMillis();
	    	elapsedTime = endTime-startTime;
	    	System.out.println("Encryption time(ms): "+elapsedTime);
	    	System.out.println("Number of encryptions: "+5*m);
	    	}catch (Exception e) {e.printStackTrace ();}
	    
	    
	    /*try {
	    	for(int i=0;i<m;i++) {
	    		System.out.print(BGN.decrypt(EY1[i], pubkey, prikey)+" ");
	    		}
	    	System.out.println();
	    	for(int i=0;i<m;i++) {
	    		System.out.print(BGN.decrypt(EX1[i], pubkey, prikey)+" ");
	    		}
	    	System.out.println();
	    	for(int i=0;i<m;i++) {
	    		System.out.print(BGN.decrypt(EX2[i], pubkey, prikey)+" ");
	    		}
	    	System.out.println();
	    	for(int i=0;i<m;i++) {
	    		System.out.print(BGN.decrypt(EY3[i], pubkey, prikey)+" ");
	    		}
	    	System.out.println();
	    	for(int i=0;i<m;i++) {
	    		System.out.print(BGN.decrypt(EX3[i], pubkey, prikey)+" ");
	    		}
	    	}catch (Exception e) {e.printStackTrace ();}
	    */
	    Random rand= new Random();    		
    	for (int i=0; i<N; i++) {//Generate random value for each IoT devices Di, 0<=i<=N-1.
    		w[i] = rand.nextInt(n);//wi for Di is an integer 0<=wi<=n-1.
    		//System.out.println(w[i]);
    	}
    	Element []cipherW=new Element[N];
    	try {
    	int tmpWi=-1;
    	int iIndex=-1;
    	int jIndex=-1;
    	Element tmpRes;
    	Element tmpRes_P1;
    	Element tmpRes_P2;
    	Element tmpRes_P3;
    	Element tmpRes_P4;
    	for(int i=0; i<N; i++) {
    	    startTime=System.nanoTime();	
    		tmpWi = w[i];
    		iIndex = tmpWi / m;
    		jIndex = tmpWi % m;	
    		//System.out.println(w[i]+ " ** " + iIndex + " ** " + jIndex);
    		//System.out.print(BGN.decrypt(EY1[jIndex], pubkey, prikey)+" * ");
    		//System.out.print(BGN.decrypt(EX1[iIndex], pubkey, prikey)+" * ");
    		//System.out.print(BGN.decrypt(EX2[iIndex], pubkey, prikey)+" * ");
    		//System.out.print(BGN.decrypt(EY3[jIndex], pubkey, prikey)+" * ");
    		//System.out.println(BGN.decrypt(EX3[iIndex], pubkey, prikey));
    		tmpRes_P1 = BGN.mul2(EY1[jIndex], EX1[iIndex], pubkey);
    		tmpRes_P2 = BGN.convToGT(EX2[iIndex], pubkey);    		
    		tmpRes_P3 = BGN.mul2(EY3[jIndex], EX3[iIndex], pubkey);
    		tmpRes_P4 = BGN.mul2(pubkey.getG(), pubkey.getH(), pubkey).pow(BigInteger.valueOf(rand.nextLong()+1));
    		tmpRes = BGN.add(tmpRes_P1, tmpRes_P2);
    		tmpRes = BGN.add(tmpRes, tmpRes_P3);
    		tmpRes = BGN.add(tmpRes, tmpRes_P4); 
    		//System.out.println(BGN.decrypt_mul2(tmpRes, pubkey, prikey)+"\n");
            endTime=System.nanoTime();
            elapsedTime = endTime-startTime;
        	cipherW[i] = tmpRes;
        	System.out.println("Preparing response at IOT device D("+i+") - time(ns): "+elapsedTime);
            System.out.println("time(ns): "+elapsedTime);

    	}   	   	
    	}catch (Exception e) {e.printStackTrace ();}

    	
    	
    	
    	Element finalResult = cipherW[0];
    	try {
	    	startTime=System.nanoTime();    	    	
	    	for(int i=1;i<N;i++) {//Aggregation on Fog node.
    			finalResult = BGN.add(finalResult, cipherW[i]); 
	    		//System.out.println(BGN.decrypt_mul2(finalResult, pubkey, prikey));
	    		}
	        endTime=System.nanoTime();
	    	elapsedTime = endTime-startTime;
	    	System.out.println("Response aggregation at fog - time(ns): "+elapsedTime);
    	}catch (Exception e) {e.printStackTrace ();}
    	   	
    	
    	
    	
    	
    	
    	
    	try {    	
    		startTime=System.nanoTime();
        	int finalQryResult=BGN.decrypt_mul2(finalResult, pubkey, prikey);
        	endTime=System.nanoTime();
        	elapsedTime = endTime-startTime;
        	System.out.println("Response recovery at user side - time(ns): "+elapsedTime);    	
        	System.out.println(finalQryResult);
    	}catch (Exception e) {e.printStackTrace ();}

    	
    	
    	
    	
    	
    		
    	

    	
    	
	}

}
