package com.salvinien.testspongycastle;


import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * The goal of this class is to manage symmetric encryption/decryption
 *
 * algo and algo size are manage externally by MauiCrypterParameters
 *
 * @author lsalvinien
 *
 *         Copyright: This class is property of MU Consulting, and is not free of use.
 */

public class MauiCrypterEngineAES extends MauiCrypterEngine
{
    //Members
    /** Secret used by the cypher engines */
    private SecretKey       aSecretKey;
    /** Initializing Vector */
    private IvParameterSpec ivSpec;


    //Accessors

    /**
     * ctor
     *
     * @param aKey
     *         byte[]: key
     * @param anIvVector
     *         byte[]: Initializing vector
     */
    public MauiCrypterEngineAES(byte aKey[], byte anIvVector[])
    {
        try
        {
            aSecretKey = new SecretKeySpec(aKey, MauiCrypterParameters.AES_ALGO_SHORT);
            ivSpec = new IvParameterSpec(anIvVector);

            cipher = Cipher.getInstance(MauiCrypterParameters.AES_ALGO_LONG, MauiCrypterParameters.PROVIDER);
            cipher.init(Cipher.ENCRYPT_MODE, aSecretKey, ivSpec);

            deCipher = Cipher.getInstance(MauiCrypterParameters.AES_ALGO_LONG, MauiCrypterParameters.PROVIDER);
            deCipher.init(Cipher.DECRYPT_MODE, aSecretKey, ivSpec);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /** returns the key */
    public byte[] getKey()
    { return aSecretKey.getEncoded();}


    //Ctor

    /** returns the IV */
    public byte[] getIv()
    { return ivSpec.getIV();}

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
        byte[] cipherBytes = new byte[ cipher.getOutputSize(plainBytes.length) ];

        int ctlength;
        try
        {
            ctlength = cipher.update(plainBytes, 0, plainBytes.length, cipherBytes, 0);
            ctlength += cipher.doFinal(cipherBytes, ctlength);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

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
        byte[] plainBytes = new byte[ cipher.getOutputSize(encryptedBytes.length) ];

        int ptlength;
        try
        {
            ptlength = deCipher.update(encryptedBytes, 0, encryptedBytes.length, plainBytes, 0);
            ptlength += deCipher.doFinal(plainBytes, ptlength);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }


        return plainBytes;
    }


}