
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


    
    //Function that generate a big interger primenumber
    private static BigInteger generatePrime() {
        BigInteger number;
        Random rand = new Random();
        number = BigInteger.valueOf(rand.nextInt(100000) + 1);

        number = number.nextProbablePrime();
        return number;
    }

    // Function that calls generatePrime and send the user 2 public keys and 1 private key
    private static BigInteger[] keys(){
    	
        BigInteger p, q, n, d, e;
        p = generatePrime();
        q = generatePrime();

        n = p.multiply(q);

        
        e = generatePrime();
        
        BigInteger phi = p.subtract(BigInteger.valueOf(1)).multiply(q.subtract(BigInteger.valueOf(1)));
        //Checks if the gcd of e and (p-1)(q-1) is 1 otherwise generate another prime
        while(e.gcd(phi).intValue() != 1){
            e = generatePrime();
        }
        //System.out.println(e);
        System.out.println("This is the e.gcd(pq_minus1): " + e.gcd(phi));
        //Calculations for d
        d = e.modInverse(phi);
        
        System.out.println("This is the e*d mod pqminus1: " + e.multiply(d).mod(phi));

        //Saves the 3 keys as an array and send it to the main, to the user
        BigInteger[] result = new BigInteger[3];
        result[0] = e;
        result[1] = n;
        result[2] = d;

        return result;
    }

    //Function that encrypt the message and returns a big integer 
    private static BigInteger encrypt(BigInteger public_e, BigInteger public_n, String text){
        BigInteger c = new BigInteger(text.getBytes());
        BigInteger encrypt_msg = c.modPow(public_e, public_n);
        	
        return encrypt_msg;
    }

    //Function that decrypt the message with private key and returns the string message
    private static String decrypt(BigInteger private_d, BigInteger public_n, BigInteger c){
        BigInteger msg = c.modPow(private_d, public_n);
        String decrypted_message = new String(msg.toByteArray());
        return decrypted_message;
    }
}


