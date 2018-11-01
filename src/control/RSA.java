package control;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

import java.util.Base64;
import java.util.Random;

public class RSA {

    private BigInteger p = null, q = null;

    boolean found = false;

    private BigInteger e, d, n;

    public RSA(){


        p = BigInteger.probablePrime(4, new Random());
        q = BigInteger.probablePrime(4, new Random());

        while(p.equals(q)){
            p = BigInteger.probablePrime(4, new Random());
            q = BigInteger.probablePrime(4, new Random());
        }

        //System.out.println(p + "," + q);
        p = new BigInteger("" + 5);
        q = new BigInteger("" + 11);

        BigInteger n = p.multiply(q);

        //Löschung

        BigInteger r = (p.subtract(new BigInteger("" + 1))).multiply((q.subtract(new BigInteger("" + 1))));

        //Löschung von p und q
        p = q = null;

        //System.out.println(p + ", " + q + "," + r);
        BigInteger e = new BigInteger("" + -1);
        for (int i = 1; e.equals(new BigInteger("" + -1)); i++){
            e = zahlOhneTeilerTest(r, new BigInteger("" + i));
        }

        BigInteger d = new BigInteger("" + -1);
        for(int i = 1; d.equals(new BigInteger("" + -1)); i++){
            d = dBestimmen(r, e, new BigInteger("" + i));

        }
        //System.out.println(e);
        //System.out.println(d);

        this.n = n;
        this.d = d;
        this.e = e;

        /*byte[] encoded = Base64.getEncoder().encode("hi".getBytes(StandardCharsets.UTF_8));
        String encodedString = encoded.toString();
        BigInteger encryptedMessage = encrypt(encoded);
        System.out.println("hi : " + encodedString);
        System.out.println(encryptedMessage);


        BigInteger decryptedMessage = decrypt(encryptedMessage);
        byte[] decoded = null;
        try {
            decoded = Base64.getDecoder().decode(decryptedMessage.toByteArray());
        } catch (IllegalArgumentException ee){
            ee.printStackTrace();
        }
        String decodedString = decoded.toString();
        System.out.println("decoded and decrypted: " + decodedString);*/
        System.out.println(encrypt("hi"));
    }

    private BigInteger zahlOhneTeilerTest(BigInteger r, BigInteger e){

        int i = 0;


        for(BigInteger teiler = new BigInteger("" + 1); teiler.equals(r) || teiler.compareTo(r) == -1; teiler = teiler.add(new BigInteger("" + 1))) {


            if(r.mod(e).equals(new BigInteger("" + 0))) {
                i++;

            }

        }
        if (i == 0){
            return e;
        }
        return new BigInteger("" + -1);
    }

    private BigInteger dBestimmen(BigInteger r, BigInteger e, BigInteger d){

        if(e.multiply(d).mod(r).equals(new BigInteger("" + 1))){
            return d;
        }
        return new BigInteger("" + -1);

    }

    public BigInteger encrypt(String msg){
        BigInteger m = new BigInteger(msg);
        BigInteger c = m.pow(e.intValue()).mod(n);
        System.out.println(c);
        return c;
    }

    public BigInteger decrypt(BigInteger c){
        BigInteger m = c.pow(d.intValue()).mod(n);
        System.out.println(m);
        return m;
    }

    private String encodeString(String text) {

        return Base64.getEncoder().encodeToString(text.getBytes());


    }

    private byte[] decodeString(byte[] text){
        return Base64.getDecoder().decode(text);
    }



}
