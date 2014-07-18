package com.demofoio.crawler.http;

import java.nio.ByteBuffer;
import java.nio.channels.Selector;

/**
 * @author : lihaoquan
 */
public class HttpFetcher {

    private Selector selector;//可以感知喜怒哀乐

    private ByteBuffer byteBuffer = ByteBuffer.allocate(8192);//8K


    public void fetch() {
        while (true) {
            try {

            }catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
