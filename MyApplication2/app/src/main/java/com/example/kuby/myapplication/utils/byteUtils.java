package com.example.kuby.myapplication.utils;

/**
 * Created by Kuby on 2016/10/11.
 */
public class byteUtils {

    // byte转十六进制字符串
    public static String bytes2HexString(byte[] bytes) {
        String ret = "0x";
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            ret += hex.toUpperCase() + " ";
        }
        return ret;
    }


    /**
     * 反转
     * @param a
     * @return
     */
    public static byte[] reverseBytes(byte[] a)
    {
        int len = a.length;
        byte[] b = new byte[len];
        for (int k = 0; k < len; k++) {
            b[k] = a[a.length - 1 - k];
        }
        return b;
    }
}
