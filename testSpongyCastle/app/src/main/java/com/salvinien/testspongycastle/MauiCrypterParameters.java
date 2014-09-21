package com.salvinien.testspongycastle;

/**
 * The goal of this class is to give the main parameters for encryption engines
 *
 * @author laurent.salvinien
 *
 *         Copyright: This class is property of MU Consulting, and is not free of use.
 *
 *         Created by laurent.salvinien on 2014-09-13.
 */
public class MauiCrypterParameters
{
    //Constants
    //global
    /** String codage */
    public static final String STRINGCODAGE = "UTF-16";
    /** the provider which is BouncyCastle */
    public static final String PROVIDER     = "BC";

    //AES
    /** algorithm short name */
    public static final String AES_ALGO_SHORT  = "AES";
    /** algorithm long name */
    public static final String AES_ALGO_LONG   = "AES/CFB/NoPadding";
    /** AES code size, we use AES-256 which is considered to be safe as of 2012 */
    public static final int    AES_CODAGE_SIZE = 256;
    public static final int    IV_SIZE         = AES_CODAGE_SIZE / 16;


    //SHA
    /** Hash algorithm */
    public static final String HASHALGO         = "SHA-256";
    /** Salt creation algorithm */
    public static final String HASHALGOSALT     = "SHA1PRNG";
    /** hash iteration number */
    public static final int    ITERATION_NUMBER = 12345;


    //RSA
    public static final String RSA_ALGO_SHORT  = "RSA";
    public static final String RSA_ALGO_LONG   = "RSA/None/NoPadding";
    public static final int    RSA_CODAGE_SIZE = 2048;
}
