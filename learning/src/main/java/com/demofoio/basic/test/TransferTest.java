package com.demofoio.basic.test;


import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;

/**
 * @author : lihaoquan
 *
 * 通道传输测试
 *
 * 模拟通道间的文件复制
 */
public class TransferTest {


    public static void main(String[] args) {


        final Path copy_from = Paths.get("/Users/lihaoquan/IdeaProjects/GitHub/demoforio_2/learning/src/main/resources/data/formFile.txt");
        final Path copy_to = Paths.get("/Users/lihaoquan/IdeaProjects/GitHub/demoforio_2/learning/src/main/resources/data/toFile.txt");

        int bufferSizeKB = 4;
        int bufferSize = bufferSizeKB * 1024;

        System.out.println("Using FileChannel and direct buffer ...");

        try {

            FileChannel fileChannel_from = FileChannel.open(copy_from,EnumSet.of(StandardOpenOption.READ));
            FileChannel fileChannel_to = FileChannel.open(copy_to,
                    EnumSet.of(StandardOpenOption.CREATE_NEW,StandardOpenOption.WRITE));

            ByteBuffer bytebuffer = ByteBuffer.allocateDirect(bufferSize);

            while ((fileChannel_from.read(bytebuffer))>0) {
                bytebuffer.flip();
                fileChannel_to.write(bytebuffer);
                bytebuffer.clear();
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
