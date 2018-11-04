package control;

import java.math.BigInteger;
import java.util.Random;

public class RSA {
    private BigInteger m, c, n, e, d, phi, p, q;

    /**
     * Konstruktor der RSA Klasse
     * Berechnet alle zuvor erstellten Werte, die für das RSA Verfahren notwendig sind
     */
    public RSA(){
        //p
        p = BigInteger.probablePrime(1000, new Random());

        //q
        do {
            q = BigInteger.probablePrime(1000, new Random());
        }while(p.compareTo(q) == 0);

        //n
        n = p.multiply(q);

        //phi
        phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        //e
        do{
            e = BigInteger.probablePrime(5, new Random());
        }while(e.gcd(phi).compareTo(BigInteger.ONE) != 0);

        //d
        int k = 1;
        while(BigInteger.valueOf(k).multiply(phi).add(BigInteger.ONE).mod(e).compareTo(BigInteger.ZERO) != 0){
            k++;
        }
        d = BigInteger.valueOf(k).multiply(phi).add(BigInteger.ONE).divide(e);
    }

    /**
     * Diese Methode verschlüsselt einen übergebenen Text mit dem RSA-Verfahren
     * @param m Die zu verschlüsselnde Nachricht
     * @param publicKey Das Schlüsselpaar, mit dem die Nachricht verschlüsselt werden soll
     * @return Der verschlüsselte Text
     */
    public String encrypt(String m, String[] publicKey){
        String c = "";

        char[] chars= m.toCharArray();
        for(int i = 0; i < chars.length; i++){
            String a = Integer.toBinaryString(((int)chars[i]));
            while(a.length() < 8){
                a = "0" + a;
            }
            c = c + a;
        }

        c = new BigInteger(c).modPow(new BigInteger(publicKey[1]), new BigInteger(publicKey[0])).toString();

        return c;
    }

    /**
     * Entschlüsselt eine NAchricht mit dem eigenen privateKey,
     * welche zuvor mit dem eigenen publicKey verschlüsselt wurde
     * @param c Die zu entschlüsselnde Nachricht
     * @return Die entschlüsselte Nachricht
     */
    public String decrypt(String c){
        String m;

        m = new BigInteger(c).modPow(d, n).toString();

        while(m.length() % 8 != 0){
            m = "0" + m;
        }
        char[] chars = m.toCharArray();
        String[] strings = new String[m.length() / 8];
        for(int i = 0; i < strings.length; i++){
            strings[i] = "";
            for(int j = 0; j < 8; j++){
                strings[i] = strings[i] + chars[i*8 + j];
            }
        }

        int[] ints = new int[strings.length];
        chars = new char[ints.length];
        for(int i = 0; i < ints.length; i++){
            ints[i] = Integer.parseInt(strings[i], 2);
            chars[i] = (char)ints[i];
        }

        m = String.valueOf(chars);

        return m;
    }

    /**
     * Sondierende Methode, die den eigenen publicKey zurückgibt
     * @return Der eigene publicKey, in einem StringArray gespeichert
     */
    public String[] getPublicKey(){
        String[] a = new String[2];
        a[0] = n.toString();
        a[1] = e.toString();
        return a;
    }
}
