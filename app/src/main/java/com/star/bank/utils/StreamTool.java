package com.star.bank.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by Hello World on 2018/4/7.
 */

public class StreamTool {
    //从流中读取数据
    public static byte[] read(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while((len = inStream.read(buffer)) != -1)
        {
            outStream.write(buffer,0,len);
        }
        inStream.close();
        return outStream.toByteArray();
    }
}
