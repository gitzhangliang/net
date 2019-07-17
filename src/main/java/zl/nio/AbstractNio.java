package zl.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.logging.Logger;

/**
 * @author zhangliang
 * @date 2019/6/25.
 */
public class AbstractNio {
    private static final Logger LOGGER = Logger.getLogger("Nio");
    public void read(SelectionKey key,String socketName) throws IOException {
        // 服务器可读取消息:得到事件发生的Socket通道
        SocketChannel channel = (SocketChannel) key.channel();
        // 创建读取的缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(512);
        channel.read(buffer);
        byte[] data = buffer.array();
        String msg = new String(data).trim();
        LOGGER.info(socketName + msg+" : time ="+System.currentTimeMillis());
        ByteBuffer outBuffer = ByteBuffer.wrap(msg.getBytes("utf-8"));
        // 将消息回送给客户端
        channel.write(outBuffer);
    }
}
