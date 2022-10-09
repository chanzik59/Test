package com.example.on_java.test;

import org.springframework.lang.NonNull;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Base64;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * @author chenzhiqin
 * @date 2022/10/9 11:37
 * @info XX
 */
public class ZipUtil {

    public static void toZip(String srcDir, OutputStream outputStream) {
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream)) {
            File file = new File(srcDir);
            compress(file, zipOutputStream, file.getName(), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void compress(File file, @NonNull ZipOutputStream zipOutputStream, String name, boolean keepStruct) throws IOException {
        byte[] buffer = new byte[2048];
        if (file.isFile()) {
            zipOutputStream.putNextEntry(new ZipEntry(name));
            int len;
            FileInputStream fileInputStream = new FileInputStream(file);
            while ((len = fileInputStream.read(buffer)) != -1) {
                zipOutputStream.write(buffer, 0, len);
            }
            zipOutputStream.closeEntry();
            fileInputStream.close();
            zipOutputStream.close();
        } else {
            File[] files = file.listFiles();
            if (Objects.isNull(files) || files.length == 0) {
                if (keepStruct) {
                    zipOutputStream.putNextEntry(new ZipEntry(name + "/"));
                    zipOutputStream.closeEntry();
                }
            } else {
                if (keepStruct) {
                    compress(file, zipOutputStream, name + "/", true);
                } else {
                    compress(file, zipOutputStream, name, false);
                }
            }
        }
    }


    public static void unZip(String desPath, InputStream inputStream) {
        try (ZipInputStream zipInputStream = new ZipInputStream(inputStream)) {
            ZipEntry zipEntry;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                int size;
                byte[] buffer = new byte[2048];
                File file = new File(desPath + "\\" + zipEntry.getName());
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
                while ((size = zipInputStream.read(buffer, 0, buffer.length)) != -1) {
                    bufferedOutputStream.write(buffer, 0, size);
                }
                bufferedOutputStream.flush();
                bufferedOutputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void unZip(byte[] fileBytes, String desPath) {
        unZip(desPath, new BufferedInputStream(new ByteArrayInputStream(fileBytes)));
    }

    public static void unZip(String srcDir, String desPath) throws FileNotFoundException {
        unZip(desPath, new BufferedInputStream(new FileInputStream(srcDir)));
    }


    public static void main(String[] args) throws IOException {
        String srcPath = "C:\\Users\\phone\\Desktop\\TestFile\\渠道商户配置模板 (1).xls";
        String out = "C:\\Users\\phone\\Desktop\\TestFile\\aa.zip";
        String des = "C:\\Users\\phone\\Desktop";

        FileInputStream fileInputStream = new FileInputStream(out);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[2048];
        int size;
        while ((size = fileInputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, size);
        }
        fileInputStream.close();
        byte[] bytes = byteArrayOutputStream.toByteArray();
        String fileBase64 = Base64.getEncoder().encodeToString(bytes);
        byte[] decode = Base64.getDecoder().decode(fileBase64);
        unZip(decode, des);


    }
}
