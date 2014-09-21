package com.salvinien.testspongycastle;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;


/**
 * This class manages a secure authentification based on a password
 *
 * to have the most secure way of authentification, the base is the following:
 *
 * we create a random salt for each new user, we iterate as much as possible the hash function
 *
 * the number of iteration will slow down a little our login procedure (barely noticeable by users)
 * but it will slow down a lot a brute force attack
 *
 * thanks to the random salt, if a hacker finds one password, he will not be able to have easily all the other passwords
 *
 * In the database, only the hashpassword and salt have to be stored
 *
 * we use sha-256, which is considered to be safe as of 2012
 *
 * @author lsalvinien
 *
 *         Copyright: This class is property of MU Consulting, and is not free of use.
 */

public class SecurePassword
{

    //Members
    /** password */
    private String password     = null;
    /** salt */
    private String salt         = null;
    /** hashed password */
    private String hashPassword = null;


    /**
     * prevents to create a "naked" SecurePassword
     */
    @SuppressWarnings("unused")
    private SecurePassword()
    {
    }


    /**
     * From a password, create the hashpassword and salt
     * This is used to create a password for a new user (or re-create a password if the user has forgotten it)
     *
     * @param aPassword
     *         String: The password to encrypt
     */
    public SecurePassword(String aPassword)
    {
        password = new String(aPassword);
        try { createrHashPasswordWithSalt(password); }
        catch(Exception e) { e.printStackTrace();}
        //catch (UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchProviderException e) { e.printStackTrace();}
    }


    /**
     * From a password and salt this method computes the hashpassword
     * This is used to check a password validity
     *
     * @param aPassword
     *         String: The password to encrypt
     * @param aSalt
     *         String: The salt used to encrypt
     */
    public SecurePassword(String aPassword, String aSalt)
    {
        salt = new String(aSalt);
        password = new String(aPassword);
        try { getHash(password, salt.getBytes(MauiCrypterParameters.STRINGCODAGE));}
        catch(Exception e) { e.printStackTrace();}
        //catch (NoSuchAlgorithmException | UnsupportedEncodingException | NoSuchProviderException e) { e.printStackTrace();}
    }

    //Ctors

    /**
     * show an example of use
     */
    public static void example()
    {
        String aPassword = "qwertyuiop";

        //create  hashpassword and salt
        SecurePassword s1 = new SecurePassword(aPassword);


        //we want to test a hashpassword with a specific salt (test should successed
        SecurePassword s2 = new SecurePassword(aPassword, s1.getSalt());
        boolean i = s1.checkPassword(s2.getHashPassword());

        System.out.println(i);

        //we want to test a hashpassword with a specific salt  (test should fail)
        SecurePassword s3 = new SecurePassword("Qwertyuiop", s1.getSalt());
        i = s1.checkPassword(s3.getHashPassword());

        System.out.println(i);


    }

    //Accesssors
    public String getSalt()
    { return salt;}

    public String getPassword() { return password;}


    //Methods

    public String getHashPassword() { return hashPassword;}

    /**
     * Check if aHashPassword (arg) and hashpassword are equals
     *
     * @param aHashPassword
     *         String: The encrypt password to test
     *
     * @return true if both hash passwords are equals else false
     */
    public boolean checkPassword(String aHashPassword)
    {

        if(hashPassword.compareTo(aHashPassword) == 0) { return true;}

        return false;
    }

    /**
     * From a password, a number of iterations and a salt, create the hashpassword (digest)
     *
     * @param password
     *         String: The password to encrypt
     * @param salt
     *         byte[]: The salt
     */
    private void getHash(String password, byte[] salt) throws NoSuchAlgorithmException, UnsupportedEncodingException, NoSuchProviderException
    {
        MessageDigest digest = MessageDigest.getInstance(MauiCrypterParameters.HASHALGO, MauiCrypterParameters.PROVIDER);    //get the object that will compute the hash
        digest.reset();                                                            //init the object
        digest.update(salt);                                                    //give the salt to the hash-er

        byte[] input = password.getBytes(MauiCrypterParameters.STRINGCODAGE);    //transform the string into something we can apply cipher algoruthm (actually byte array !)
        for(int i = 0; i < MauiCrypterParameters.ITERATION_NUMBER; i++)        //we iterate ITERATION_NUMBER times the hash, the higher ITERATION_NUMBER the better it is
        {
            digest.reset();
            input = digest.digest(input);
        }
        hashPassword = new String(input, MauiCrypterParameters.STRINGCODAGE);
    }

    /**
     * From a password, create the hashpassword (digest) and the random salt
     *
     * @param password
     *         String: The password to encrypt
     */
    private void createrHashPasswordWithSalt(String password) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchProviderException
    {
        //create a random salt for the this password
        byte[] bSalt = new byte[ 16 ];
        //SecureRandom random = SecureRandom.getInstance( MauiCrypterParameters.HASHALGOSALT);
        //random.nextBytes(bSalt);
        Utils.getSingleton().generateSecureRandom(bSalt);

        // Digest computation
        getHash(password, bSalt);

        // convert the salt in a String
        salt = new String(bSalt, MauiCrypterParameters.STRINGCODAGE);
    }


}
