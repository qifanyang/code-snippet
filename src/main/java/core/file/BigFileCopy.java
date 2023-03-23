package core.file;

import java.io.*;

public class BigFileCopy {


    public static void main(String[] args) throws IOException {
        File file = new File("/Users");
        String path = "/Users/yangqifan/bigfile"+System.currentTimeMillis();
        long length = 1000*1000*1000*5L;
        createBigFile(path, length);
        copyBigFile(path);
    }

    private static void copyBigFile(String path) throws IOException {
        long start = System.currentTimeMillis();
        String copyPath = path + ".copy";
        createFile(copyPath);
        RandomAccessFile sourceFile = new RandomAccessFile(path, "r");
        RandomAccessFile destFile = new RandomAccessFile(copyPath, "rw");
        long leftSize = sourceFile.length();
        long pos = 0;
        int writeSize = 1000*1000*1000*1;
        while (leftSize > 0){
            //因操作系统有限制, 一次性映射较大文件不能一次完成, transferTo代码中也限制一次最大传输Integer.MAX_VALUE
            //sourceFile.getChannel().transferTo(0, sourceFile.length(), destFile.getChannel());
            long l = sourceFile.getChannel().transferTo(pos, writeSize, destFile.getChannel());
            System.out.println(l);
            leftSize -= writeSize;
            pos += writeSize;
        }
        destFile.close();
        sourceFile.close();
        System.out.println("copy file, cost time " +  (System.currentTimeMillis() - start));
    }
    private static void createBigFile2(String path, long length) throws IOException {
        createFile(path);
        RandomAccessFile accessFile = new RandomAccessFile(path, "rw");
        accessFile.setLength(length);
        accessFile.close();
    }

    private static void createBigFile(String path, long length) throws IOException {
        createFile(path);
        RandomAccessFile accessFile = new RandomAccessFile(path, "rw");
        int bathSize = 1000*1000*1000*1;
        byte[] bytes = new byte[bathSize];
        bytes[0]='0';
        bytes[bathSize-1]='1';
        while (length > 0){
            accessFile.write(bytes);
            accessFile.seek(accessFile.length());
            length -=bathSize;
        }
        System.out.println("文件生成完毕, size = " + accessFile.length());
        accessFile.close();
    }

    private static void createFile(String path) throws IOException {
        File file = new File(path);
        file.setWritable(true);
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
    }
}
