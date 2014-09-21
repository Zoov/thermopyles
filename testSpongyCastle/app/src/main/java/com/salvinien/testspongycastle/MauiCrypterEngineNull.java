package com.salvinien.testspongycastle;


/**
 * The goal of this class is to do, on purpose, nothing in term of encryption
 *
 * this kind of class is usefull to avoid breaks in algos
 *
 * notice we overload the method that encrypt and decrypt to do nothing
 *
 * @author lsalvinien
 *
 *         Copyright: This class is property of MU Consulting, and is not free of use.
 */

public class MauiCrypterEngineNull extends MauiCrypterEngine
{
    //Methods

    /**
     * encrypt an array of byte
     *
     * @param plainBytes
     *         byte[]: array of bytes to crypt
     *
     * @return an array of encrypted bytes
     */
    public byte[] encrypt(byte[] plainBytes)
    {return plainBytes.clone();}


    /**
     * decrypt an array of byte
     *
     * @param encryptedBytes
     *         byte[]: array of crypted bytes
     *
     * @return an array of decrypted bytes
     */
    public byte[] decrypt(byte[] encryptedBytes)
    {return encryptedBytes.clone();}
}
