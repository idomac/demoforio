package com.demofoio.crawler.http;

import com.demofoio.crawler.page.Page;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

/**
 * @author : lihaoquan
 */
public class HttpFetcher {

    private Selector selector;//可以感知喜怒哀乐

    private ByteBuffer readBuffer = ByteBuffer.allocate(8192);//8K

    private Map<URI,ByteBuffer> writeBuffers = new HashMap<URI, ByteBuffer>();

    private BlockingQueue<URI> linksQueue;

    private BlockingQueue<Page> pagesQueue;

    private Map<URI,ByteArrayOutputStream> strams = new HashMap<URI, ByteArrayOutputStream>();

    private static int DEFAULT_CONNECTION_COUNT = 10;

    private HttpRequestBuilder httpRequestBuilder = new HttpRequestBuilder();

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

                initiateNewConnections();//建立通讯连接
                int nb = selector.select();
                if(nb ==0 && linksQueue.isEmpty()) {
                    break;
                }

                Iterator<SelectionKey> iter =  selector.selectedKeys().iterator();
                while (iter.hasNext()) {
                    SelectionKey selectionKey = iter.next();
                    iter.remove();

                    if(!selectionKey.isValid()) {
                        continue;
                    }

                    if(selectionKey.isConnectable()) {

                        connect(selectionKey);

                    }else if(selectionKey.isWritable()) {

                        write(selectionKey);

                    }else if(selectionKey.isReadable()) {

                        read(selectionKey);
                    }
                }
            }catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }

    /**
     * 初始化新连接功能
     * @throws Exception
     */
    private void initiateNewConnections() throws Exception {

        for(int i = 0; i< DEFAULT_CONNECTION_COUNT ; i++) {
            URI url = linksQueue.poll();
            if(url == null) {
                break;
            }

            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);

            int port = url.getPort() > 0 ? url.getPort() : 80;
            socketChannel.connect(new InetSocketAddress(url.getHost(), port));

            SelectionKey selectionKey = socketChannel.register(selector,SelectionKey.OP_CONNECT);
            selectionKey.attach(url);

            strams.put(url,new ByteArrayOutputStream());

        }
    }


    /**
     * 连接处理
     */
    public void connect(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        socketChannel.finishConnect();
        key.interestOps(SelectionKey.OP_WRITE);
    }

    /**
     * 写入处理
     */
    public void write(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();

        URI url = (URI) key.attachment();

        ByteBuffer writeBuffer = writeBuffers.get(url);
        if(writeBuffer == null) {
            String getRequest = httpRequestBuilder.buildGet(url);
            writeBuffer = ByteBuffer.wrap(getRequest.getBytes());
            writeBuffers.put(url,writeBuffer);
        }

        socketChannel.write(writeBuffer);
        if(!writeBuffer.hasRemaining()) {
            writeBuffers.remove(url);

            //now we want to read
            key.interestOps(SelectionKey.OP_READ);
        }
    }

    /**
     * 读出处理
     */
    public void read(SelectionKey key) throws IOException {

        URI url = (URI) key.attachment();
        SocketChannel socketChannel = (SocketChannel) key.channel();
        readBuffer.clear();

        int numRead = socketChannel.read(readBuffer);
        if(numRead > 0) {
            strams.get(url).write(readBuffer.array(),0,numRead);
        }else {

            key.channel().close();
            key.cancel();

            ByteArrayOutputStream byteArrayOutputStream = strams.get(url);
            Page page = new Page(url,byteArrayOutputStream.toByteArray());
            pagesQueue.add(page);
        }
    }
}
