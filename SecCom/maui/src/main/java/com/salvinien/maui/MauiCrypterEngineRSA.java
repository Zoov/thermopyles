package com.salvinien.maui;

import java.security.Key;
import java.security.KeyPair;

import javax.crypto.Cipher;


/**
 * This class goal is to manage asymmetric encryption
 *
 * algo and algo size are manage externally by MauiCrypterParameters
 *
 *
 * Unless for specific purpose, this class shouldn't be used directly, it must be used through MauiCrypter
 *
 * @author lsalvinien
 *
 *         Copyright: This class is property of MU Consulting, and is not free of use.
 */

public class MauiCrypterEngineRSA extends MauiCrypterEngine
{

    //Ctor
    public MauiCrypterEngineRSA(Key MyPrivateKey, Key aPublicKey)
    {
        setKeys(MyPrivateKey, aPublicKey);
    }


    //Methods

    /**
     * show examples of use
     */
    public static void example()
    {
        KeyPair kp = MauiCrypterManager.generateRSAKey();
        Key priv = kp.getPrivate();
        Key pub = kp.getPublic();

        MauiCrypterEngineRSA c = new MauiCrypterEngineRSA(priv, pub);


        String s = "coucou mon general";

        byte plainBytes[] = s.getBytes();
        byte cypherBytes[] = c.encrypt(plainBytes);
        byte deCypherBytes[] = c.decrypt(cypherBytes);

        String t = new String(deCypherBytes);


        System.out.println("Private key:" + Utils.getSingleton().toHex(priv.getEncoded()));
        System.out.println("Public  key:" + Utils.getSingleton().toHex(pub.getEncoded()));
        System.out.println("Message:" + s);
        System.out.println("plain bytes   :" + Utils.getSingleton().toHex(plainBytes));
        System.out.println("cypher bytes  :" + Utils.getSingleton().toHex(cypherBytes));
        System.out.println("decypher bytes:" + Utils.getSingleton().toHex(deCypherBytes));
        System.out.println("Message:" + t);

    }

    /**
     * init the cipher engines with pub and priv keys
     *
     * @param MyPrivateKey
     *         Key: private key (the one used to decrypt)
     * @param aPublicKey
     *         Key: public key (the one used  to encrypt)
     */
    public void setKeys(Key MyPrivateKey, Key aPublicKey)
    {
        try
        {
            cipher = Cipher.getInstance(MauiCrypterParameters.RSA_ALGO_LONG, MauiCrypterParameters.PROVIDER);
            if(aPublicKey != null) { cipher.init(Cipher.ENCRYPT_MODE, aPublicKey); }


            deCipher = Cipher.getInstance(MauiCrypterParameters.RSA_ALGO_LONG, MauiCrypterParameters.PROVIDER);
            if(MyPrivateKey != null) { deCipher.init(Cipher.DECRYPT_MODE, MyPrivateKey); }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    /**
     * encrypt an array of byte
     *
     * @param plainBytes
     *         byte[]: array of bytes to encrypt
     *
     * @return an array of encrypted bytes
     */
    public byte[] encrypt(byte[] plainBytes)
    {
        //encryption
        byte[] cipherBytes = null;
        try
        {
            cipherBytes = cipher.doFinal(plainBytes);
        }
        catch(Exception e) { e.printStackTrace();}

        return cipherBytes;
    }

    /**
     * decrypt an array of byte
     *
     * @param encryptedBytes
     *         byte[]: array of encrypted bytes
     *
     * @return an array of decrypted bytes
     */
    public byte[] decrypt(byte[] encryptedBytes)
    {
        //decryption
        byte[] plainBytes = null;
        try
        {
            plainBytes = deCipher.doFinal(encryptedBytes);
        }
        catch(Exception e) {e.printStackTrace();}
        //catch (IllegalBlockSizeException | BadPaddingException e) {e.printStackTrace();}

        return plainBytes;
    }
}
