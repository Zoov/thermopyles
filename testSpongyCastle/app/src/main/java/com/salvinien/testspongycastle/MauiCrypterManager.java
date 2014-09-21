package com.salvinien.testspongycastle;

import java.io.UnsupportedEncodingException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;


/**
 * this abstract class defines what a manager of encryption engine should implement
 *
 * a MauiCrypterManager manages one to several encryption engines to provide an easy and secure way of encrypting/decrypting
 *
 * the engines can be of the same kind, or of different kind, they have to be instancied in the init() method
 *
 * @author lsalvinien
 *
 *         Copyright: This class is property of MU Consulting, and is not free of use.
 */

public abstract class MauiCrypterManager
{
    /** crypter for string */
    protected MauiCrypterEngine crypterString;

    /** crypter for long */
    protected MauiCrypterEngine crypterLong;

    /** crypter for double */
    protected MauiCrypterEngine crypterDouble;

    /** crypter for bytes */
    protected MauiCrypterEngine crypterBytes;


    /** Ctor */
    public MauiCrypterManager()
    { init();}

    /**
     * produce a random AES key
     *
     * @return SecretKey : the key
     */
    public static SecretKey generateAESKey()
    {
        //get a keygenerator
        KeyGenerator keyGen = null;
        try
        {
            keyGen = KeyGenerator.getInstance(MauiCrypterParameters.AES_ALGO_SHORT, MauiCrypterParameters.PROVIDER);
        }
        catch(Exception e1) { e1.printStackTrace(); }
        //catch (NoSuchAlgorithmException | NoSuchProviderException e1) { e1.printStackTrace(); }

        //init the generator
        keyGen.init(MauiCrypterParameters.AES_CODAGE_SIZE);

        //get a new key
        SecretKey aKey = keyGen.generateKey();
        return aKey;
    }


    //Methods

    /**
     * produce a random IV
     *
     * @return IvParameterSpec : the Iv
     */
    public static IvParameterSpec generateIV()
    {
        byte[] anIv = new byte[ MauiCrypterParameters.IV_SIZE ];

        //SecureRandom random = new SecureRandom();
        //random.nextBytes(anIv);
        Utils.getSingleton().generateSecureRandom(anIv);

        IvParameterSpec ivSpec = new IvParameterSpec(anIv);

        return ivSpec;
    }

    /**
     * create a random pair of priv and pub keys
     *
     * @return KeyPair: a random key pair
     */
    public static KeyPair generateRSAKey()
    {
        SecureRandom random = null;
        KeyPairGenerator generator = null;
        try
        {
            generator = KeyPairGenerator.getInstance(MauiCrypterParameters.RSA_ALGO_SHORT, MauiCrypterParameters.PROVIDER);
            //random = SecureRandom.getInstance( MauiCrypterParameters.HASHALGOSALT);
            random = Utils.getSingleton().getRandom();
        }
        catch(Exception e) { e.printStackTrace();}
        //catch (NoSuchAlgorithmException | NoSuchProviderException e) { e.printStackTrace();}

        generator.initialize(MauiCrypterParameters.RSA_CODAGE_SIZE, random);

        return generator.generateKeyPair();
    }

    /**
     * Initialize the engines
     */
    protected abstract void init();

    /**
     * encrypt a string
     *
     * @param aString
     *         String: string to crypt
     *
     * @return byte[] : the string crypted
     */
    public byte[] encryptString(String aString)
    {
        byte plainText[] = null;
        try
        {
            plainText = aString.getBytes(MauiCrypterParameters.STRINGCODAGE);
        }
        catch(UnsupportedEncodingException e) { e.printStackTrace();}

        byte encryptedText[] = crypterString.encrypt(plainText);

        return encryptedText;
    }

    /**
     * decrypt a string
     *
     * @param encryptedText
     *         byte[]: value to decrypt
     *
     * @return String : the value un-crypted
     */
    public String decryptString(byte[] encryptedText)
    {
        byte[] plainText = crypterString.decrypt(encryptedText);

        String ret = null;
        try
        {
            ret = new String(plainText, MauiCrypterParameters.STRINGCODAGE);
        }
        catch(UnsupportedEncodingException e) {e.printStackTrace();}

        return ret;
    }

    /**
     * encrypt a long
     *
     * @param aLong
     *         long: value to crypt
     *
     * @return byte[] : the encrypted value
     */
    public byte[] encryptLong(long aLong)
    { return crypterLong.encrypt(Utils.getSingleton().toByteArray(aLong));}

    /**
     * decrypt a long
     *
     * @param encrypted
     *         byte[] : value to decrypt
     *
     * @return long : the decrypted value
     */
    public long decryptLong(byte encrypted[])
    { return Utils.getSingleton().toLong(crypterLong.decrypt(encrypted));}

    /**
     * encrypt a double
     *
     * @param aDouble
     *         double: value to encrypt
     *
     * @return byte[] : the value encrypted
     */
    public byte[] encryptDouble(Double aDouble)
    { return crypterDouble.encrypt(Utils.getSingleton().toByteArray(aDouble));}

    /**
     * decrypt a double
     *
     * @param encrypted
     *         byte[] : value to decrypt
     *
     * @return double : the value decrypted
     */
    public double decryptDouble(byte encrypted[])
    { return Utils.getSingleton().toDouble(crypterDouble.decrypt(encrypted));}

    /**
     * encrypt an array of bytes
     *
     * @param plainBytes
     *         byte[]: value to encrypt
     *
     * @return byte[] : the encrypted value
     */
    public byte[] encrypt(byte[] plainBytes)
    { return crypterBytes.encrypt(plainBytes);}

    /**
     * decrypt an array of bytes
     *
     * @param encrypted
     *         byte[] : value to decrypt
     *
     * @return byte[] : the value un-crypted
     */
    public byte[] decrypt(byte encrypted[])
    { return crypterBytes.decrypt(encrypted);}


}
