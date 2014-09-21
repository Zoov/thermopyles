package com.salvinien.maui;

/**
 * This class manage engines that does nothing
 *
 * it is used to avoid breaks in alogos
 *
 * @author lsalvinien
 *
 *         Copyright: This class is property of MU Consulting, and is not free of use.
 */


public class MauiCrypterManagerNull extends MauiCrypterManager
{

    public MauiCrypterManagerNull()
    {
    }


    /**
     * Initialize the engines
     * In this case, we have the same null engine for all type data to encryp/decrypt
     */
    protected void init()
    {
        crypterString = crypterLong = crypterDouble = crypterBytes = new MauiCrypterEngineNull();
    }

}
