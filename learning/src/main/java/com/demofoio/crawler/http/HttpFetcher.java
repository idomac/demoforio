package com.demofoio.crawler.http;

import com.demofoio.crawler.page.Page;

import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.spi.SelectorProvider;
import java.util.concurrent.BlockingQueue;

/**
 * @author : lihaoquan
 */
public class HttpFetcher {

    private Selector selector;//可以感知喜怒哀乐

    private ByteBuffer byteBuffer = ByteBuffer.allocate(8192);//8K

    private BlockingQueue<URI> linksQueue;

    private BlockingQueue<Page> pagesQueue;



    /**
     * 构造函数
     * @param linksQueue
     * @param pagesQueue
     * @throws IOException
     */
    public HttpFetcher(BlockingQueue<URI> linksQueue, BlockingQueue<Page> pagesQueue) throws IOException {
        this.linksQueue = linksQueue;
        this.pagesQueue = pagesQueue;
        this.selector = SelectorProvider.provider().openSelector();
    }

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
