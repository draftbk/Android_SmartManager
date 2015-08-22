package com.example.apple1.smartmanager2.tools;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by draft on 2015/7/24.
 */
public class EncryptMD5 {

    public EncryptMD5() {

    }

    public  String GetMD5(String string) {

        byte[] hash;

        try {

            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));

        } catch (NoSuchAlgorithmException e) {

            throw new RuntimeException("Huh, MD5 should be supported?", e);

        } catch (UnsupportedEncodingException e) {

            throw new RuntimeException("Huh, UTF-8 should be supported?", e);

        }



        StringBuilder hex = new StringBuilder(hash.length * 2);

        for (byte b : hash) {

            if ((b & 0xFF) < 0x10) hex.append("0");

            hex.append(Integer.toHexString(b & 0xFF));

        }

        return hex.toString();

    }
}
