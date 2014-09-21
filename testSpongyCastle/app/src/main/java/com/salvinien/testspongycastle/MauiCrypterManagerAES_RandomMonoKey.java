package com.salvinien.testspongycastle;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * This class manage one AES engines to encrypt the different kind of data
 *
 * it is designed to encrypt network communication
 *
 * we use AES-256, which is considered to be safe as of 2012
 *
 * this class uses the MauiCrypterEngineAES class to do the basic encrypt.
 * It uses different Keys and IV (though different crypters)  for each type, so if on key has been hacked the rest of the data
 * is still safe.
 *
 * @author lsalvinien
 *
 *         Copyright: This class is property of MU Consulting, and is not free of use.
 * @TODO: the IV is goal not fully implemented, it is random but it should changed frequently during the life of the instance
 */
public class MauiCrypterManagerAES_RandomMonoKey extends MauiCrypterManager
{

    /** secret key */
    private SecretKey sK;

    /** iv */
    private byte iv[];


    /**
     * Initialize the engines
     * In this case, we have the same engine for all type data to encryp/decrypt, and the key is random
     */
    protected void init()
    {
        sK = MauiCrypterManager.generateAESKey();
        iv = MauiCrypterManager.generateIV().getIV();

        setKeyAndIv(sK.getEncoded(), iv);
    }

    //Accessors

    /**
     * @return the key
     */
    public byte[] getKey()
    { return sK.getEncoded();}

    /**
     * @return the Iv
     */
    public byte[] getIv()
    { return iv;}


    /**
     * set Keys and Ivs
     *
     * @param aKey
     *         byte[]: key
     * @param anIv
     *         byte[]: Iv
     */
    public void setKeyAndIv(byte[] aKey, byte[] anIv)
    {
        sK = new SecretKeySpec(aKey, MauiCrypterParameters.AES_ALGO_SHORT);
        iv = anIv;

        crypterString = crypterLong = crypterDouble = crypterBytes = new MauiCrypterEngineAES(sK.getEncoded(), iv);
    }

}
