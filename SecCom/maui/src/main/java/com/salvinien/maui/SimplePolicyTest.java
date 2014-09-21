package com.salvinien.maui;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


/**
 * This class goal is to test security policy !
 *
 * @author lsalvinien
 *
 *         Copyright: This class is property of MU Consulting, and is not free of use.
 */

public class SimplePolicyTest
{


    public static void test() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
    {
        //data to crypt
        byte data[] = {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07};

        //3 keys
        byte key64[] = {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07};
        byte key192[] = {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f, 0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17};
        byte key256[] = {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f, 0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18, 0x19, 0x1a, 0x1b, 0x1c, 0x1d, 0x1e, 0x1f};


        //secret key creation
        SecretKey aSecretKey64 = new SecretKeySpec(key64, "Blowfish");
        SecretKey aSecretKey192 = new SecretKeySpec(key192, "Blowfish");
        SecretKey aSecretKey256 = new SecretKeySpec(key256, "Blowfish");


        //create a cipher
        Cipher aCipher = Cipher.getInstance("Blowfish/ECB/NoPadding");


        //try to encrypt with a 64b key
        aCipher.init(Cipher.ENCRYPT_MODE, aSecretKey64);
        aCipher.doFinal(data);
        System.out.println("64-bit test passed");

        //try to encrypt with a 192b key
        aCipher.init(Cipher.ENCRYPT_MODE, aSecretKey192);
        aCipher.doFinal(data);
        System.out.println("192-bit test passed");

        //try to encrypt with a 256b key
        aCipher.init(Cipher.ENCRYPT_MODE, aSecretKey256);
        aCipher.doFinal(data);
        System.out.println("256-bit test passed");


    }

}
