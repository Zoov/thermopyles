package com.salvinien.testspongycastle;

import java.security.Security;


/**
 * This class goal is to test the presence of provider!
 *
 * @author lsalvinien
 *
 *         Created by laurent.salvinien on 2014-09-13.
 *         Copyright: This class is property of MU Consulting, and is not free of use.
 */

public class SimpleProviderTest
{
    public static void test() throws Exception
    {
        String providerName = "BC";

        if(Security.getProvider(providerName) == null)
        {
            System.out.println(providerName + "  not installed");
        }
        else
        {
            System.out.println(providerName + "  is installed");
        }
    }
}

