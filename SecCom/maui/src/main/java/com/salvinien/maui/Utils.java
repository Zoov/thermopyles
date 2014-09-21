package com.salvinien.maui;

import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * This class wraps some useful functions which alone wouldn't deserve a class
 *
 * all methods class methods (static methods)
 *
 * @author laurent.salvinien
 *
 *         Created by laurent.salvinien on 2014-09-13.
 *
 *         Copyright: This class is property of MU Consulting, and is not free of use.
 */
public class Utils
{
    //Members
    /** an hexadecimal digits string */
    final private static String digits       = "0123456789abcdef";
    /** the singleton */
    private static       Utils  theSingleton = new Utils();
    /** a secure Random, (because it is always long to init a random */
    private static SecureRandom sRng;


    //CTOR
    private Utils()
    {
        sRng = null;
        try { sRng = SecureRandom.getInstance(MauiCrypterParameters.HASHALGOSALT);}
        catch(NoSuchAlgorithmException e) {e.printStackTrace();}

    }

    //Accessors

    /** returns the singleton */
    public static Utils getSingleton()
    {
        return theSingleton;
    }

    /** returns the secureRandom instance */
    public SecureRandom getRandom()
    { return sRng;}


    //METHODS

    /**
     * transform an array of byte into a string of Hex
     *
     * @param data
     *         byte[]: the bytes to transform
     * @param length
     *         int: number of bytes to transform
     *
     * @return String: the string of hex
     */
    public String toHex(byte[] data, int length)
    {
        StringBuffer buf = new StringBuffer();

        for(int i = 0; i != length; i++)
        {
            int v = data[ i ] & 0xff;

            buf.append(digits.charAt(v >> 4));
            buf.append(digits.charAt(v & 0xf));
        }


        return buf.toString();
    }


    /**
     * wrap  String toHex( byte[] data, int length)
     * to transfrom the complete byte array
     *
     * @param data
     *         byte[]: the bytes to transform
     */
    public String toHex(byte[] data)
    {
        return toHex(data, data.length);
    }


    /**
     * transform an  array of byte into a string of Hex and add:
     * -  cast into byte before each element
     * - {} around the values
     * - , between each element
     * it is useful to create an initializer of array byte that we just have to copy/paste
     *
     * @param data
     *         byte[]: the bytes to transform
     * @param length
     *         int: number of bytes to transfrom
     */
    public String toHex2(byte[] data, int length)
    {
        StringBuffer buf = new StringBuffer();

        buf.append("{ ");
        for(int i = 0; i != length; i++)
        {
            int v = data[ i ] & 0xff;


            buf.append("(byte)0x");
            buf.append(digits.charAt(v >> 4));
            buf.append(digits.charAt(v & 0xf));
            buf.append(", ");
        }
        buf.append("}");


        return buf.toString();
    }


    /**
     * wrap  String toHex2( byte[] data, int length)
     * to transform the complete byte array
     *
     * @param data
     *         byte[]: the bytes to transform
     */
    public String toHex2(byte[] data)
    {
        return toHex2(data, data.length);
    }


    /**
     * transform a long into a byte array
     *
     * @param value
     *         long: the value to transform
     *
     * @return data byte[]: the bytes result of the byte transformation
     */
    public byte[] toByteArray(long value)
    {
        ByteBuffer bb = ByteBuffer.allocate(8);
        return bb.putLong(value).array();
    }

    /**
     * transform a byte array into a long
     *
     * @param t
     *         byte[]: the bytes to transform
     *
     * @return long: the value result of the transformation
     */
    public long toLong(byte t[])
    {
        ByteBuffer bb = ByteBuffer.wrap(t);
        return bb.getLong();
    }


    /**
     * transform a double into a byte array
     *
     * @param value
     *         double: the value to transform
     *
     * @return data byte[]: the bytes result of the byte transformation
     */
    public byte[] toByteArray(double value)
    {
        ByteBuffer bb = ByteBuffer.allocate(8);
        return bb.putDouble(value).array();
    }

    /**
     * transform a byte array into a double
     *
     * @param t
     *         byte[]: the bytes to transform
     *
     * @return double: the value result of the transformation
     */
    public double toDouble(byte t[])
    {
        ByteBuffer bb = ByteBuffer.wrap(t);
        return bb.getDouble();
    }


    /**
     * create a secure random string
     *
     * @param characters
     *         String: String containing the set of characters in which the random will pick char
     *
     * @return lenght int: size of the string to be generated
     */
    public String generateString(String characters, int length)
    {
        char[] text = new char[ length ];
        for(int i = 0; i < length; i++)
        {
            text[ i ] = characters.charAt(sRng.nextInt(characters.length()));
        }
        return new String(text);
    }

    /**
     * create a secure random byte array
     */
    public void generateSecureRandom(byte[] r)
    {
        sRng.nextBytes(r);
    }


}
