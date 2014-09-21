package com.salvinien.testspongycastle;

import javax.crypto.Cipher;

/**
 * This is the mother class for crypto engines
 *
 * notice we use two Cipher instances, one for encryption one for decryption, this is just for performance matters
 * actually both could be use to cipher or de-cypher, but in that case we would have to re-init the cypher instances
 * each time with the mode (DECRYPT or CRYPT) which is time consuming..
 *
 * Unless for specific purpose, this class shouldn't be used directly, it must be used through MauiCrypter
 *
 * @author lsalvinien
 *
 *         Copyright: This class is property of MU Consulting, and is not free of use.
 */

public abstract class MauiCrypterEngine
{
    /** Cipher engine */
    protected Cipher cipher;
    /** de-Cipher engine */
    protected Cipher deCipher;


    /**
     * encrypt an array of byte
     *
     * @param plainBytes
     *         byte[]: array of bytes to encrypt
     *
     * @return an array of encrypted bytes
     */
    public abstract byte[] encrypt(byte[] plainBytes);

    /**
     * decrypt an array of byte
     *
     * @param encryptedBytes
     *         byte[]: array of encrypted bytes
     *
     * @return an array of decrypted bytes
     */
    public abstract byte[] decrypt(byte[] encryptedBytes);

}
