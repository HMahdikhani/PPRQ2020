public class drlurange{

public static void printMat(int M[][]){
    for(int i=0; i<M.length;i++){
        for(int j=0; j<M[i].length;j++){
            System.out.print(" " + M[i][j]);
        }
        System.out.println();
    }
    System.out.println();
}

public static void printMat(int M[]){
    for(int i=0; i<M.length;i++){
        System.out.print(" " + M[i]);
    }
    System.out.println();
}


public static boolean isAllZero(int[] vec){
    boolean flag = false;
    for(int i=0; i< vec.length; i++){
        if (vec[i]==1){
            return false;
        }
    }
    return true;
}
     
public static boolean isAllOne(int[] vec){
    boolean flag = false;
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
    boolean flag = false;
    for(int i=0; i< M.length; i++){
        if (M[i][col]==1){
            return false;
        }
    }
    return true;
}

public static void main(String []args){
    int w=16;
    int L=8;
    int U=15;//0<=L<=U<=n-1
    int m=32;
    int n=m*m;//n=100 >>> n=m*m
    int[][] R=new int [m][m];
    int[][] R1=new int [m][m];
    int[][] R2=new int [m][m];
    int[][] R3=new int [m][m];
   
    int indxA;
    for(int i=0; i<m;i++){
        for(int j=0; j<m;j++){
            indxA = j+i*m; 
            if ((indxA>=L) && (indxA<=U)){
                R[i][j]=1;
            }else{
                    R[i][j]=0;
            }
        }
    }
    
    System.out.println("A: ");
    for(int i=0;i<n;i++){
        if(i>=L && i<=U){
            System.out.print(" " + 1);
        }else{
            System.out.print(" " + 0);
        }
    }
    
    System.out.println();  
    
    System.out.println("R: ");   
    printMat(R);

    int lastRoI=-1;
    for(int i=0;i<m;i++){
        if(rowStatus(R[i])==0){
            for(int j=0;j<m;j++){//row of 0s
                R1[i][j]=0;
                R2[i][j]=0;
                R3[i][j]=0;
            }
        }else if(rowStatus(R[i])==1){//row of 1s
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
        
    System.out.println("R1: ");
    printMat(R1);
    System.out.println("R2: ");
    printMat(R2);
    System.out.println("R3: ");
    printMat(R3);
    
    int[] Y1= new int[m];//1-Y1
    int[] X1= new int[m];//X'1
    int[] X2= new int[m];//X'2
    int[] Y3= new int[m];//1-Y3
    int[] X3= new int[m];//X'3
    
    
    
    
    
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

    
    System.out.println("Y1: ");
    printMat(Y1);
    
    System.out.println("X1: ");
    printMat(X1);
    
    System.out.println("X2: ");
    printMat(X2);
    
    System.out.println("Y3: ");
    printMat(Y3);
    
    System.out.println("X3: ");
    printMat(X3);

    System.out.println();
    int i=0;
    int j=0;
    boolean flagBreak=false;
    for(i=0;i<m;i++){
        for(j=0;j<m;j++){
            if(j+i*m==w){
                System.out.println("w= " +w+" >>> i= " +i+", j= " +j );
                flagBreak=true;
                break;
            }
        }
        if (flagBreak) break;
    }
    
    System.out.println("L= "+ L+"  U="+U);
    System.out.println("R(i="+i+",j="+j+")");
    System.out.print("Y1["+j+"].X1["+i+"]+X2["+i+"]+Y3["+j+"].X3["+i+"]="+Y1[j]+"x"+X1[i]+"+"+X2[i]+"+"+Y3[j]+"x"+X3[i]+"= ");
    System.out.println(Y1[j]*X1[i]+X2[i]+Y3[j]*X3[i]);
    
    
    
    
} //end of main
    
    
    
}


/*oooo*/





























