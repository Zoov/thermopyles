package com.salvinien.maui;

import javax.crypto.spec.SecretKeySpec;


/**
 * This class manage several AES engines to encrypt the different kind of data
 *
 * it is designed to encrypt database (but not only !)
 *
 * we use AES-256, which is considered to be safe as of 2012
 *
 * this class uses the MauiCrypterEngineAES class to do the basic encrypt.
 * It uses different Keys and IV (though different crypters)  for each type, so if on key has been hacked the rest of the data
 * is still safe.
 *
 * !!! the keys and Iv has to be changed for each program, the ones present are just default
 * !!! foo this either change the  initializations are use the setKeyxxx accessors
 *
 * @author lsalvinien
 *
 *         Copyright: This class is property of MU Consulting, and is not free of use.
 * @TODO: the IV is goal not fully implemented, it should be random AND changed frequently during the life of the instance
 */

public class MauiCrypterManagerAES_MultiKey extends MauiCrypterManager
{

    /** key for String */
    private byte keyString[];//= { (byte)0xd5, (byte)0xc9, (byte)0x9f, (byte)0x99, (byte)0x7d, (byte)0xb6, (byte)0x2b, (byte)0x94, (byte)0x3e, (byte)0xe1, (byte)0xb8, (byte)0x75, (byte)0xf8, (byte)0x07, (byte)0xdc, (byte)0xb3, (byte)0xe0, (byte)0x7e, (byte)0x14, (byte)0xd2, (byte)0xcf, (byte)0x5c, (byte)0x4b, (byte)0x64, (byte)0x96, (byte)0x5e, (byte)0xe7, (byte)0x3c, (byte)0x66, (byte)0x90, (byte)0xea, (byte)0xb0};
    /** Iv for String */
    private byte ivString[];// = { (byte)0x98, (byte)0xab, (byte)0x9a, (byte)0x2b, (byte)0x19, (byte)0x8e, (byte)0xb4, (byte)0xee, (byte)0x62, (byte)0x68, (byte)0x28, (byte)0x35, (byte)0x83, (byte)0x52, (byte)0x82, (byte)0xdf};

    /** key for long */
    private byte keyLong[];//	= { (byte)0xfb, (byte)0xf5, (byte)0x61, (byte)0xc0, (byte)0x61, (byte)0xdf, (byte)0x66, (byte)0xf0, (byte)0xcf, (byte)0xfc, (byte)0x24, (byte)0x1b, (byte)0x8b, (byte)0x43, (byte)0xc3, (byte)0xbb, (byte)0xa3, (byte)0xcb, (byte)0x82, (byte)0xb2, (byte)0x9f, (byte)0x8e, (byte)0x1f, (byte)0xeb, (byte)0x05, (byte)0xc6, (byte)0xff, (byte)0xb8, (byte)0xe3, (byte)0x75, (byte)0x11, (byte)0x8e};
    /** Iv for long */
    private byte ivLong[];//	= { (byte)0x49, (byte)0x8a, (byte)0xca, (byte)0x0e, (byte)0xc1, (byte)0xab, (byte)0xe9, (byte)0x0f, (byte)0x40, (byte)0xf7, (byte)0xf5, (byte)0x33, (byte)0xce, (byte)0xa1, (byte)0x75, (byte)0x40};

    /** key for double */
    private byte keyDouble[];//= { (byte)0x2d, (byte)0xa4, (byte)0x12, (byte)0xb7, (byte)0x0f, (byte)0x74, (byte)0x7c, (byte)0x8c, (byte)0x9a, (byte)0x05, (byte)0xaf, (byte)0x18, (byte)0xd2, (byte)0x2e, (byte)0x9c, (byte)0x48, (byte)0x94, (byte)0xcf, (byte)0x6d, (byte)0x88, (byte)0x5f, (byte)0x61, (byte)0x2f, (byte)0x4d, (byte)0x64, (byte)0x71, (byte)0x35, (byte)0x82, (byte)0x19, (byte)0x58, (byte)0x8b, (byte)0x64};
    /** Iv for double */
    private byte ivDouble[];//	= { (byte)0xba, (byte)0x32, (byte)0xbe, (byte)0xfc, (byte)0xc9, (byte)0x69, (byte)0xf6, (byte)0xa4, (byte)0xdf, (byte)0xdf, (byte)0x01, (byte)0xdb, (byte)0x89, (byte)0xd5, (byte)0x1b, (byte)0x49};

    /** key for bytes */
    private byte key[];//		= { (byte)0x25, (byte)0x5e, (byte)0x6c, (byte)0x2d, (byte)0x69, (byte)0x2c, (byte)0xf1, (byte)0xa4, (byte)0x33, (byte)0xe8, (byte)0x02, (byte)0x53, (byte)0x3e, (byte)0x8b, (byte)0x31, (byte)0xf6, (byte)0xb4, (byte)0x21, (byte)0x16, (byte)0xc3, (byte)0x1d, (byte)0x6f, (byte)0x20, (byte)0xef, (byte)0x8a, (byte)0x67, (byte)0x78, (byte)0x70, (byte)0xb2, (byte)0x06, (byte)0xae, (byte)0xc1};
    /** Iv for bytes */
    private byte iv[];//		= { (byte)0x69, (byte)0x7c, (byte)0xb9, (byte)0x3c, (byte)0xb4, (byte)0xf4, (byte)0xa9, (byte)0x09, (byte)0xb8, (byte)0xf9, (byte)0xa8, (byte)0x3f, (byte)0x52, (byte)0x02, (byte)0x18, (byte)0x3f};


    /** Ctor */
    public MauiCrypterManagerAES_MultiKey()
    {
    }

    /**
     * show an example of use
     */
    public static void example()
    {

        MauiCrypterManagerAES_MultiKey aT1 = new MauiCrypterManagerAES_MultiKey();


        System.out.println("========array byte================");
        byte input[] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        System.out.println("input  :" + Utils.getSingleton().toHex(input));
        byte output[] = aT1.encrypt(input);
        System.out.println("output :" + Utils.getSingleton().toHex(output));
        byte deciphered[] = aT1.decrypt(output);
        System.out.println("deciph :" + Utils.getSingleton().toHex(deciphered));


        System.out.println("===========String===============");
        String sI = "Hello Coucou et l'eau";
        System.out.println("input :" + sI);
        byte sO[] = aT1.encryptString(sI);
        System.out.println("output:" + Utils.getSingleton().toHex(sO));
        String sD = aT1.decryptString(sO);
        System.out.println("decpih :" + sD);

        System.out.println("===========long===============");
        for(long i = 100; i < 200; i++)
        {
            byte s2[] = aT1.encryptLong(i);
            long l = aT1.decryptLong(s2);

            System.out.println("--");
            System.out.println("input :" + i);
            System.out.println("output:" + Utils.getSingleton().toHex(s2));
            System.out.println("deciph:" + l);

            if(i != l)
            {
                System.out.println("+++++++++++++++++++++++++++++++++++++");
                System.out.println("input  :" + i);
                System.out.println("output :" + Utils.getSingleton().toHex(s2));
                System.out.println("decpih :" + l);
            }
        }

        System.out.println("===========double===============");
        for(long i = 500; i < 6000; i++)
        {
            double d = Math.PI * i;
            byte s2[] = aT1.encryptDouble(d);
            double d1 = aT1.decryptDouble(s2);


            System.out.println("--");
            System.out.println("input :" + d);
            System.out.println("output:" + Utils.getSingleton().toHex(s2));
            System.out.println("deciph:" + d1);

            if(d != d1)
            {
                System.out.println("+++++++++++++++++++++++++++++++++++++");
                System.out.println("input  :" + d);
                System.out.println("output :" + Utils.getSingleton().toHex(s2));
                System.out.println("decpih :" + d1);
            }
            //System.out.println( "--");

        }
        System.out.println("=============================");


    }

    /**
     * initialize engines
     */
    protected void init()
    {
        keyString = (new byte[]{(byte) 0xd5, (byte) 0xc9, (byte) 0x9f, (byte) 0x99, (byte) 0x7d, (byte) 0xb6, (byte) 0x2b, (byte) 0x94, (byte) 0x3e, (byte) 0xe1, (byte) 0xb8, (byte) 0x75, (byte) 0xf8, (byte) 0x07, (byte) 0xdc, (byte) 0xb3, (byte) 0xe0, (byte) 0x7e, (byte) 0x14, (byte) 0xd2, (byte) 0xcf, (byte) 0x5c, (byte) 0x4b, (byte) 0x64, (byte) 0x96, (byte) 0x5e, (byte) 0xe7, (byte) 0x3c, (byte) 0x66, (byte) 0x90, (byte) 0xea, (byte) 0xb0});
        ivString = (new byte[]{(byte) 0x98, (byte) 0xab, (byte) 0x9a, (byte) 0x2b, (byte) 0x19, (byte) 0x8e, (byte) 0xb4, (byte) 0xee, (byte) 0x62, (byte) 0x68, (byte) 0x28, (byte) 0x35, (byte) 0x83, (byte) 0x52, (byte) 0x82, (byte) 0xdf});

        keyLong = (new byte[]{(byte) 0xfb, (byte) 0xf5, (byte) 0x61, (byte) 0xc0, (byte) 0x61, (byte) 0xdf, (byte) 0x66, (byte) 0xf0, (byte) 0xcf, (byte) 0xfc, (byte) 0x24, (byte) 0x1b, (byte) 0x8b, (byte) 0x43, (byte) 0xc3, (byte) 0xbb, (byte) 0xa3, (byte) 0xcb, (byte) 0x82, (byte) 0xb2, (byte) 0x9f, (byte) 0x8e, (byte) 0x1f, (byte) 0xeb, (byte) 0x05, (byte) 0xc6, (byte) 0xff, (byte) 0xb8, (byte) 0xe3, (byte) 0x75, (byte) 0x11, (byte) 0x8e});
        ivLong = (new byte[]{(byte) 0x49, (byte) 0x8a, (byte) 0xca, (byte) 0x0e, (byte) 0xc1, (byte) 0xab, (byte) 0xe9, (byte) 0x0f, (byte) 0x40, (byte) 0xf7, (byte) 0xf5, (byte) 0x33, (byte) 0xce, (byte) 0xa1, (byte) 0x75, (byte) 0x40});

        keyDouble = (new byte[]{(byte) 0x2d, (byte) 0xa4, (byte) 0x12, (byte) 0xb7, (byte) 0x0f, (byte) 0x74, (byte) 0x7c, (byte) 0x8c, (byte) 0x9a, (byte) 0x05, (byte) 0xaf, (byte) 0x18, (byte) 0xd2, (byte) 0x2e, (byte) 0x9c, (byte) 0x48, (byte) 0x94, (byte) 0xcf, (byte) 0x6d, (byte) 0x88, (byte) 0x5f, (byte) 0x61, (byte) 0x2f, (byte) 0x4d, (byte) 0x64, (byte) 0x71, (byte) 0x35, (byte) 0x82, (byte) 0x19, (byte) 0x58, (byte) 0x8b, (byte) 0x64});
        ivDouble = (new byte[]{(byte) 0xba, (byte) 0x32, (byte) 0xbe, (byte) 0xfc, (byte) 0xc9, (byte) 0x69, (byte) 0xf6, (byte) 0xa4, (byte) 0xdf, (byte) 0xdf, (byte) 0x01, (byte) 0xdb, (byte) 0x89, (byte) 0xd5, (byte) 0x1b, (byte) 0x49});

        key = (new byte[]{(byte) 0x25, (byte) 0x5e, (byte) 0x6c, (byte) 0x2d, (byte) 0x69, (byte) 0x2c, (byte) 0xf1, (byte) 0xa4, (byte) 0x33, (byte) 0xe8, (byte) 0x02, (byte) 0x53, (byte) 0x3e, (byte) 0x8b, (byte) 0x31, (byte) 0xf6, (byte) 0xb4, (byte) 0x21, (byte) 0x16, (byte) 0xc3, (byte) 0x1d, (byte) 0x6f, (byte) 0x20, (byte) 0xef, (byte) 0x8a, (byte) 0x67, (byte) 0x78, (byte) 0x70, (byte) 0xb2, (byte) 0x06, (byte) 0xae, (byte) 0xc1});
        iv = (new byte[]{(byte) 0x69, (byte) 0x7c, (byte) 0xb9, (byte) 0x3c, (byte) 0xb4, (byte) 0xf4, (byte) 0xa9, (byte) 0x09, (byte) 0xb8, (byte) 0xf9, (byte) 0xa8, (byte) 0x3f, (byte) 0x52, (byte) 0x02, (byte) 0x18, (byte) 0x3f});


        crypterString = new MauiCrypterEngineAES(keyString, ivString);
        crypterLong = new MauiCrypterEngineAES(keyLong, ivLong);
        crypterDouble = new MauiCrypterEngineAES(keyDouble, ivDouble);
        crypterBytes = new MauiCrypterEngineAES(key, iv);
    }

    /**
     * set Keys and Ivs for Long engine
     *
     * @param aKey
     *         byte[]: key
     * @param anIv
     *         byte[]: Iv
     */
    public void setKeyAndIvLong(byte[] aKey, byte[] anIv)
    {
        keyLong = (new SecretKeySpec(aKey, MauiCrypterParameters.AES_ALGO_SHORT)).getEncoded();
        ivLong = anIv;

        crypterLong = new MauiCrypterEngineAES(keyLong, iv);
    }

    /**
     * set Keys and Ivs  for String engine
     *
     * @param aKey
     *         byte[]: key
     * @param anIv
     *         byte[]: Iv
     */
    public void setKeyAndIvString(byte[] aKey, byte[] anIv)
    {
        keyString = (new SecretKeySpec(aKey, MauiCrypterParameters.AES_ALGO_SHORT)).getEncoded();
        ivString = anIv;

        crypterString = new MauiCrypterEngineAES(keyString, iv);
    }

    /**
     * set Keys and Ivs  for Double engine
     *
     * @param aKey
     *         byte[]: key
     * @param anIv
     *         byte[]: Iv
     */
    public void setKeyAndIvDouble(byte[] aKey, byte[] anIv)
    {
        keyDouble = (new SecretKeySpec(aKey, MauiCrypterParameters.AES_ALGO_SHORT)).getEncoded();
        ivDouble = anIv;

        crypterDouble = new MauiCrypterEngineAES(keyDouble, iv);
    }

    /**
     * set Keys and Ivs  for Bytes engine
     *
     * @param aKey
     *         byte[]: key
     * @param anIv
     *         byte[]: Iv
     */
    public void setKeyAndIvBytes(byte[] aKey, byte[] anIv)
    {
        key = (new SecretKeySpec(aKey, MauiCrypterParameters.AES_ALGO_SHORT)).getEncoded();
        iv = anIv;

        crypterBytes = new MauiCrypterEngineAES(key, iv);
    }

}