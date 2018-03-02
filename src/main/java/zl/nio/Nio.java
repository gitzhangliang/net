package zl.nio;

import org.junit.Test;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by tzxx on 2018/2/27.
 */
public class Nio {
    @Test
    public void fileChannelTest() throws Exception{
        RandomAccessFile aFile = new RandomAccessFile("e://nio-data.txt", "rw");
        FileChannel inChannel = aFile.getChannel();

        ByteBuffer buf = ByteBuffer.allocate(2);
        int a =0;
        int bytesRead = inChannel.read(buf);
        while (bytesRead != -1) {

            System.out.println("a:"+a);
            a++;
            System.out.println("Read " + bytesRead);
            buf.flip();

            while(buf.hasRemaining()){
                System.out.println((char) buf.get());
            }

            buf.clear();
            bytesRead = inChannel.read(buf);
        }
        aFile.close();
    }

    @Test
    public void fileChannelCopyTest1() throws Exception{
        RandomAccessFile fromFile = new RandomAccessFile("e://nio-data.txt", "rw");
        FileChannel fromChannel = fromFile.getChannel();

        RandomAccessFile toFile = new RandomAccessFile("e://to-nio-data.txt", "rw");
        FileChannel toChannel = toFile.getChannel();
        fromChannel.transferTo(0,fromChannel.size(),toChannel);
    }

    @Test
    public void fileChannelCopyTest2() throws Exception{
        RandomAccessFile fromFile = new RandomAccessFile("e://nio-data.txt", "rw");
        FileChannel fromChannel = fromFile.getChannel();

        RandomAccessFile toFile = new RandomAccessFile("e://to-nio-data.txt", "rw");
        FileChannel toChannel = toFile.getChannel();
        toChannel.transferFrom(fromChannel,0,fromChannel.size());
    }
    @Test
    public void selectorTest() throws Exception {
        Selector selector = Selector.open();
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(false);
        SelectionKey selectionKey = channel.register(selector, SelectionKey.OP_READ);
        while(true) {
            int readyChannels = selector.select();
            if(readyChannels == 0) continue;
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey>  keyIterator = selectedKeys.iterator();
            while(keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                if(key.isAcceptable()) {
                    System.out.println(" a connection was accepted by a ServerSocketChannel");
                    // a connection was accepted by a ServerSocketChannel.
                } else if (key.isConnectable()) {
                    System.out.println(" a connection was established with a remote server");

                    // a connection was established with a remote server.
                } else if (key.isReadable()) {
                    System.out.println(" a channel is ready for reading");

                    // a channel is ready for reading
                } else if (key.isWritable()) {
                    System.out.println(" a channel is ready for writing");

                    // a channel is ready for writing
                }
                keyIterator.remove();
            }
        }
    }
}
