package com.demofoio.basic.test;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author : lihaoquan
 *
 * Buffer的测试事例
 */
public class BufferTest {

    public static void main(String[] args) {
        try {
            String path = ChannelTest.class.getResource("/data/nio-data.txt").getPath().toString();
            RandomAccessFile aFile = new RandomAccessFile(path, "rw");

            FileChannel inChannel = aFile.getChannel();
            ByteBuffer buf = ByteBuffer.allocate(48);

            int readBytes = inChannel.read(buf);//向buffer中写入数据
            while (readBytes !=-1) {
                buf.flip();
                while (buf.hasRemaining()) {
                    System.out.println((char)buf.get()); //一个字节一个字节地读
                }
                buf.clear();
                readBytes = inChannel.read(buf);
            }
            aFile.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
