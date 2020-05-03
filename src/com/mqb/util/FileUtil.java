package com.mqb.util;

import java.io.*;


/**
 * 将静态资源文件内容写入到输出流
 */
public class FileUtil {

    public static boolean writeFile(InputStream inputStream, OutputStream outputStream) {
        boolean success = false;
        BufferedInputStream bufferedInputStream;
        BufferedOutputStream bufferedOutputStream;
        try {
            bufferedInputStream = new BufferedInputStream(inputStream);
            bufferedOutputStream = new BufferedOutputStream(outputStream);
            bufferedOutputStream.write(HttpUtil.getHttpResponseContext200("").getBytes("utf-8"));
            int count = 0;
            while (count == 0) {
                count = inputStream.available();
            }
            int fileSize = inputStream.available();
            long written = 0;
            int byteSize = 1024;
            byte[] bytes = new byte[byteSize];
            while (written < fileSize) {
                if (written + byteSize > fileSize) {
                    byteSize = (int) (fileSize - written);
                    bytes = new byte[byteSize];
                }
                bufferedInputStream.read(bytes);
                bufferedOutputStream.write(bytes);
                bufferedOutputStream.flush();
                written += byteSize;
            }
            success = true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return success;
    }

    public static boolean writeFile(File file, OutputStream outputStream) throws IOException {
        return writeFile(new FileInputStream(file), outputStream);
    }

    public static String getResourcePath(String path) {
        String resource = FileUtil.class.getResource("/").getPath();
        return resource + path;
    }
}
