package com.salvinien.testspongycastle;


import android.util.Base64;

import org.spongycastle.jce.provider.BouncyCastleProvider;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAKeyGenParameterSpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

/**
 * Created by lsalvinien on 8/14/2014.
 */
public class SecMod
{

    static int KEY_SIZE=256;

    static
    {
        //Security.addProvider(new org.spongycastle.jce.provider.BouncyCastleProvider());
        ///Security.addProvider(new BouncyCastleProvider());
        Security.insertProviderAt(new BouncyCastleProvider(), 1);
    }


    public static PublicKey getRSAPublicKeyFromString(String apiKey) throws Exception
    {
        KeyFactory keyFactory=KeyFactory.getInstance("RSA", "SC");
        byte[] publicKeyBytes=Base64.decode(apiKey.getBytes("UTF-8"), Base64.DEFAULT);
        X509EncodedKeySpec x509KeySpec=new X509EncodedKeySpec(publicKeyBytes);
        return keyFactory.generatePublic(x509KeySpec);
    }

    public static PrivateKey getRSAPrivateKeyFromString(String key) throws Exception
    {
        byte[] clear=Base64.decode(key, Base64.DEFAULT);
        PKCS8EncodedKeySpec keySpec=new PKCS8EncodedKeySpec(clear);
        KeyFactory fact=KeyFactory.getInstance("RSA", "SC");
        PrivateKey priv=fact.generatePrivate(keySpec);
        Arrays.fill(clear, (byte) 0);
        return priv;
    }

    /**
     * Generate a keyPair suitable for RSA encryption
     *
     * @return The generated key
     * @throws NoSuchAlgorithmException
     */
    public static KeyPair generateRSAKeyPair() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException
    {
        SecureRandom random=new SecureRandom();
        RSAKeyGenParameterSpec spec=new RSAKeyGenParameterSpec(KEY_SIZE, RSAKeyGenParameterSpec.F4);
        KeyPairGenerator generator=KeyPairGenerator.getInstance("RSA", "SC");
        generator.initialize(spec, random);
        return generator.generateKeyPair();
    }

}
