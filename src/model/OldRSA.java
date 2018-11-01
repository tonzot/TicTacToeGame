package model;

import javax.crypto.*;
import java.security.*;

public class OldRSA {

    public KeyPair key = null;



    /**
     * Generiert das Schlüsselpaar.
     */
    public void gen(){
        KeyPairGenerator keygen = null;
        try {
            //.getInstance(...) kann eine NoSuchAlgorithmException thrown.
            keygen = KeyPairGenerator.getInstance("OldRSA");
        } catch (NoSuchAlgorithmException exception){
            System.out.println("Dieser Algorithmus existiert nicht. \n Die Exception lautet: ");
            exception.printStackTrace();
        }
        //Keygröße 512 oder 1024 oder etwas höheres, je länger der Key desto langsamer ist dieser Prozess.
        keygen.initialize(1024);
        //Generiert das KeyPair
        key = keygen.generateKeyPair();

    }

    /**
     *
     * @param message Klartext
     * @param pk ist der public Key
     * @return verschlüsselte Nachricht
     */
    public byte[] encrypt(String message, PublicKey pk){
        Cipher cipher = null;

        cipher = getCipher(cipher);

        try {
            //...(Modus, Key)
            cipher.init(Cipher.ENCRYPT_MODE, pk);
        } catch (InvalidKeyException e) {
            //Dieser Fehler würde kommen, wenn man den Secret Key eingibt.
            System.out.println("Falscher Key. Die Exception lautet:");
            e.printStackTrace();
        }

        byte[] chiffrat = null;

        try {
            //verschlüsselt. Man kann nur Bytes und keinen direkten String verschlüsseln.
            chiffrat = cipher.doFinal(message.getBytes());
        } catch (IllegalBlockSizeException e) {
            System.out.println("Die Exception lautet:");
            e.printStackTrace();
        } catch (BadPaddingException e) {
            System.out.println("Die Exception lautet:");
            e.printStackTrace();
        }
        return chiffrat;
    }

    /**
     * Entschlüsselung
     * @param chiffrat verschlüsselte Nachricht
     * @param sk secret key
     * @return entschlüsselte Nachricht
     */
    public String decrypt(byte[] chiffrat, PrivateKey sk){
        byte[] dechiffrat = null;

        Cipher cipher = null;
        cipher = getCipher(cipher);

        try {
            cipher.init(Cipher.DECRYPT_MODE, sk);
        } catch (InvalidKeyException e) {
            System.out.println("Falscher Key. Die Exception lautet:");
            e.printStackTrace();
        }

        try {
            dechiffrat = cipher.doFinal(chiffrat);
        } catch (IllegalBlockSizeException e) {
            System.out.println("Die Exception lautet:");
            e.printStackTrace();
        } catch (BadPaddingException e) {
            System.out.println("Die Exception lautet:");
            e.printStackTrace();
        }
        return new String(dechiffrat);
    }

    /**
     * Diese sondierende Methode wird verwendet um keine Code-Duplizierung zu haben.
     * @param cipher cipher
     * @return
     */
    private Cipher getCipher(Cipher cipher) {
        try {
            cipher = Cipher.getInstance("OldRSA");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Dieser Algorithmus existiert nicht. \n Die Exception lautet: ");
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            System.out.println("Die Exception lautet:");
            e.printStackTrace();
        }
        return cipher;
    }



























}
