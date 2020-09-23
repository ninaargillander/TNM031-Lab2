
import java.math.BigInteger;
import java.util.Random;

// Generate public and private keys
// encrypt a plain text message given one key.
// decrypt the ciphertext message given the other key.

public class Main {
    public static void main(String[] args) {
    	
    	
        BigInteger e, n, d;
        BigInteger[] myKeys;
        
        myKeys = keys();
        e = myKeys[0];
        n = myKeys[1];
        d = myKeys[2];
        
        
        String message = "Hej hej Louise";
        System.out.println("Message before: " + message);

        BigInteger encrypted = encrypt(e,n, message);
        System.out.println("Message encrypted: " + encrypted);

        String decrypted = decrypt(d,n, encrypted);
        System.out.println("Message decrypted: " + decrypted);


    }


    // Bob -> väljer två primtal p och q (dessa är hemliga)

    private static BigInteger generatePrime() {
        BigInteger number;
        Random rand = new Random();
        number = BigInteger.valueOf(rand.nextInt(100000) + 1);

        number = number.nextProbablePrime();
        return number;
    }

    private static BigInteger[] keys(){
    	
        BigInteger p, q, n, d, e;
        p = generatePrime();
        q = generatePrime();

        n = p.multiply(q);

        e = generatePrime();
        
        //System.out.println(e.gcd((p.subtract(BigInteger.valueOf(1))).multiply(q.subtract(BigInteger.valueOf(1)))));
        while(e.gcd((p.subtract(BigInteger.valueOf(1))).multiply(q.subtract(BigInteger.valueOf(1)))).intValue() != 1){
            e = generatePrime();
        }

        BigInteger k = BigInteger.valueOf(3);
        BigInteger pq_minus1 = p.subtract(BigInteger.valueOf(1)).multiply(q.subtract(BigInteger.valueOf(1)));
        d = ((pq_minus1.multiply(k)).add(BigInteger.valueOf((1)))).divide(e);
 
        
        System.out.println((e.multiply(d)).mod(pq_minus1));
        		

        
        BigInteger[] result = new BigInteger[3];
        result[0] = e;
        result[1] = n;
        result[2] = d;

        return result;
    }

    private static BigInteger encrypt(BigInteger public_e, BigInteger public_n, String text){
        BigInteger msg = new BigInteger(text.getBytes());

        return msg.modPow(public_e, public_n);
    }

    private static String decrypt(BigInteger private_d, BigInteger public_n, BigInteger c){
        BigInteger msg = c.modPow(private_d, public_n);

        return new String(msg.toByteArray());
    }
}
// p*q = n
// Bob -> väljer e så SGM(e, (p-1)*(q-1)) = 1
// Bob -> beräknar d så e*d mod ((p-1)*(q-1)) = 1
// Bob gör n och e publikt
// Alice krypterar m som c = m^e mod n och skickar c till Bob
// bob dekrypterar genom att beräkna m = c^d mod n

