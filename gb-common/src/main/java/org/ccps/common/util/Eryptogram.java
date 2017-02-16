package org.ccps.common.util;


import javax.crypto.*;
import java.security.*;

/**
 * <p>Title: ECCM</p>
 *
 * <p>Description: Enterprise Collaboration, Customer, and Resources Management</p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: eConage Software</p>
 *
 * @author not attributable
 * @version 1.0
 */
public class Eryptogram {
    private  static  String  Algorithm ="DES";
    static  boolean  debug  = false ;
    private static Eryptogram self;

    public static Eryptogram getInstance() {
        if (self == null) {
            self = new Eryptogram();
        }
        return self;
    }

    public static byte[] getSecretKey() throws Exception {
        KeyGenerator keygen = KeyGenerator.getInstance(Algorithm);
        SecretKey deskey = keygen.generateKey();
        if (debug)System.out.println("生成密钥:" + byte2hex(deskey.getEncoded()));
        return deskey.getEncoded();
    }

    public static byte[] encryptData(byte[] input, byte[] key) throws Exception {
        SecretKey deskey = new javax.crypto.spec.SecretKeySpec(key, Algorithm);
        if (debug) {
            System.out.println("加密前的二进串:" + byte2hex(input));
            System.out.println("加密前的字符串:" + new String(input));
        }
        Cipher c1 = Cipher.getInstance(Algorithm);
        c1.init(Cipher.ENCRYPT_MODE, deskey);
        byte[] cipherByte = c1.doFinal(input);
        if (debug)
            System.out.println("加密后的二进串:" + byte2hex(cipherByte));
        return cipherByte;
    }

    public static byte[] decryptData(byte[] input, byte[] key) throws Exception {
        SecretKey deskey = new javax.crypto.spec.SecretKeySpec(key, Algorithm);
        if (debug)
            System.out.println("解密前的信息:" + byte2hex(input));
        Cipher c1 = Cipher.getInstance(Algorithm);
        c1.init(Cipher.DECRYPT_MODE, deskey);
        byte[] clearByte = c1.doFinal(input);
        if (debug) {
            System.out.println("解密后的二进串:" + byte2hex(clearByte));
            System.out.println("解密后的字符串:" + (new String(clearByte)));
        }
        return clearByte;
    }

    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else hs = hs + stmp;
            //if (n < b.length - 1) hs = hs + ":";
        }
        return hs.toUpperCase();
    }

    public byte[] hex2byte(String hex) throws IllegalArgumentException {
        if (hex.length() % 2 != 0) {
            throw new IllegalArgumentException();
        }
        char[] arr = hex.toCharArray();
        byte[] b = new byte[hex.length() / 2];
        for (int i = 0, j = 0, l = hex.length(); i < l; i++, j++) {
            String swap = "" + arr[i++] + arr[i];
            int byteint = Integer.parseInt(swap, 16) & 0xFF;
            b[j] = new Integer(byteint).byteValue();
        }
        return b;
    }
    public static String encryptOp(String input,String encrypt_key) throws Exception {
        if(StringUtil.isBlank(input)) return input;
        Eryptogram etg = getInstance();
        String output = input;
        try {
            byte [] key = encrypt_key.getBytes("iso-8859-1");
            byte [] data  = input.getBytes ("iso-8859-1");
            byte [] en  = etg.encryptData (data ,key );
            output = etg.byte2hex(en);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            return output;
        }
    }
    public static String decryptOp(String input,String encrypt_key) throws Exception {
        if(StringUtil.isBlank(input)) return input;
        Eryptogram etg = getInstance();
        String output = input;
        try {
            byte [] key = encrypt_key.getBytes("iso-8859-1");
            byte[] de = etg.decryptData(etg.hex2byte(input), key);
            output = new String(de);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            return output;
        }
    }

    public static void main(String args[]) throws Exception {/*
      try {
          String password = "123456";
          String key = "ourhooha";
          String encrypt_newpwd =ApplicationHelper.getSuperUserPwd();
          System.out.println(encrypt_newpwd);
          System.out.println(encryptOp("econage", ApplicationHelper.PWD_KEY));
      }catch (Exception  e ){
          e.printStackTrace ();
      }
    */}

}
