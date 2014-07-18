package com.demofoio.basic.test;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author : lihaoquan
 *
 * 基本的Channel的测试事例
 */
public class ChannelTest {

    public static void main(String[] args) {
        try {

            String path = ChannelTest.class.getResource("/data/nio-data.txt").getPath().toString();
            RandomAccessFile aFile = new RandomAccessFile(path, "rw");

            FileChannel inChannal = aFile.getChannel();

            ByteBuffer buf = ByteBuffer.allocate(24);//设置一个24字节长度的缓冲区
            int bytesRead =  inChannal.read(buf);//数据读入channel
            while(bytesRead!=-1) {
                System.out.println("Read : "+bytesRead);
                buf.flip();
                while (buf.hasRemaining()) {
                    System.out.print((char) buf.get());
                }
                buf.clear();
                bytesRead = inChannal.read(buf);
            }
            aFile.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
