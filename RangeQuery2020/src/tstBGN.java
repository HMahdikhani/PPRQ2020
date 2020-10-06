import java.math.BigInteger;
import java.util.Random;


import it.unisa.dia.gas.jpbc.Element;

public class tstBGN {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//System.out.println("Hello");
		
        BGN bgn = new BGN();
        // Key Generation
        bgn.keyGeneration(512);
        BGN.PublicKey pubkey = bgn.getPubkey();
        BGN.PrivateKey prikey = bgn.getPrikey();

        System.out.println(pubkey.toString());
        
        
    	Element ktemp, ktemp2;
    	Element k[]=new Element[5];  
        try {
        	for(int i=0; i<5; i++) {
        		k[i] = BGN.encrypt(i+2, pubkey);
        		System.out.println(k[i]);
        		System.out.println(BGN.decrypt(k[i], pubkey, prikey));
        	}
        }catch (Exception e) {e.printStackTrace ();}
        finally {}
            
        	//BGN supports an arbitrary number of addition and one multiplication.
        	//BGN has additive homomorphic, it means E(m1)*E(m2)*E(m3)*...=E(m1+m2+m3+...) 
        	//BGN has additive homomorphic, it means E(m1)*E(m2)=E(m1+m2)
        	//Paillier supports additive homomorphic. i.e. E(m1)*E(m2)*(E(m3)*...=E(m1+m2+m3)
        	//Additive homomorphic; we apply an > operation on them but the result is sum.
        	//Multiplicative homomorphic; we apply an < operation on them but the result is multiply.
        System.out.println(">>> add[E(m1), E(m2)]=c1.mul(c2), decrypt");
        ktemp = k[0]; 
        try {
        	for(int i=1; i<5; i++) {
        		ktemp = BGN.add(ktemp, k[i]);
        		System.out.println(ktemp);
        		System.out.println(BGN.decrypt(ktemp, pubkey, prikey));
        	}
        }catch (Exception e) {e.printStackTrace ();}
        finally {}
        
        System.out.println(">>> mul2[E(m1), E(m2)]=Pairing(c1,c2), decrypt_mul2");
        ktemp = k[0];
        try {
        	for(int i=1; i<5; i++) {
        		ktemp = BGN.mul2(ktemp,k[i], pubkey);
        		System.out.println(ktemp);
        		System.out.println(BGN.decrypt_mul2(ktemp, pubkey, prikey));
        	}
        }catch (Exception e) {e.printStackTrace ();}
        finally {System.out.println();}
        
        
        System.out.println(">>> mul1[E(m1),m2]=c1.pow(m2), decrypt");
        ktemp = k[0];
        try {
        	for(int i=0; i<5; i++) {
        		ktemp = BGN.mul1(ktemp,2);
        		System.out.println(ktemp);
        		System.out.println(BGN.decrypt(ktemp, pubkey, prikey));
        	}
        }catch (Exception e) {e.printStackTrace ();}
        finally {}
        
        System.out.println(">>> selfBlind(E(m1),r]=c1.mul(h.pow(r2), decrypt");
        ktemp = k[4];
        try {
        	for(int i=0; i<5; i++) {
        		BigInteger r = pubkey.getPairing().getZr().newRandomElement().toBigInteger();
        		System.out.println("r.length()=" + r.bitLength());
        		ktemp = BGN.selfBlind(ktemp,r , pubkey);
        		System.out.println(ktemp);
        		System.out.println(BGN.decrypt(ktemp, pubkey, prikey));
        	}
        }catch (Exception e) {e.printStackTrace ();}
        finally {}
        

        System.out.println(">>> convToGT(c1): convert c1 from G to GT, decrypt_mul2");
        ktemp=k[3];
        try {
        System.out.println(ktemp);
        System.out.println("Bit Length:"+ktemp.getLengthInBytes()*8);
        System.out.println(BGN.decrypt(ktemp, pubkey, prikey));
        
        ktemp = BGN.convToGT(ktemp, pubkey);
        System.out.println(ktemp);        
        System.out.println("Bit Length:"+ktemp.getLengthInBytes()*8);
        System.out.println(BGN.decrypt_mul2(ktemp, pubkey, prikey));
        }catch(Exception e) {e.printStackTrace ();}
        
                
        System.out.println(">>> add(mul2(c0*c1),c2,mul2(c3,c4)), decrypt");
        try {

        	Element ktemp01=BGN.mul2(k[0], k[1], pubkey);
        	System.out.println(ktemp01);
        	System.out.println(BGN.decrypt_mul2(ktemp01, pubkey, prikey));        	       	

        	Element ktemp34=BGN.mul2(k[3], k[4], pubkey);
        	System.out.println(ktemp34);
        	System.out.println(BGN.decrypt_mul2(ktemp34, pubkey, prikey));
        	
        	ktemp = BGN.add(ktemp01, ktemp34);
        	ktemp = BGN.add(ktemp, BGN.mul2(k[2], BGN.encrypt(1, pubkey), pubkey));
        	System.out.println(ktemp);
        	System.out.println(BGN.decrypt_mul2(ktemp, pubkey, prikey));
        	
        	Element GTRnd1=BGN.mul2(pubkey.getG(), pubkey.getH(), pubkey);
        	GTRnd1 = GTRnd1.pow(pubkey.getPairing().getZr().newRandomElement().toBigInteger()).getImmutable();
        	
        	Element GTRnd2=BGN.mul2(pubkey.getG(), pubkey.getH(), pubkey);
        	GTRnd1 = GTRnd1.pow(pubkey.getPairing().getZr().newRandomElement().toBigInteger()).getImmutable();
    	
        	ktemp = BGN.add(ktemp, GTRnd1);
    		System.out.println(ktemp);
    		System.out.println(BGN.decrypt_mul2(ktemp, pubkey, prikey));
        	
        	ktemp = BGN.add(ktemp, GTRnd2);
    		System.out.println(ktemp);
    		System.out.println(BGN.decrypt_mul2(ktemp, pubkey, prikey));

        }catch (Exception e) {e.printStackTrace ();}
        finally {}
        

	}

}
