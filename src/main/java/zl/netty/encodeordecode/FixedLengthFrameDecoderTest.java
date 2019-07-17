package zl.netty.encodeordecode;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import org.junit.Test;
import static org.junit.Assert.*;
public class FixedLengthFrameDecoderTest {
    @Test
    public void testFramesDecoded() {
        ByteBuf buf = Unpooled.buffer();
        for (int i = 0; i < 9; i++) {
            buf.writeByte(i);
        }
        ByteBuf input = buf.duplicate();
        EmbeddedChannel channel = new EmbeddedChannel(new FixedLengthFrameDecoder(3));
// write bytes
        assertTrue(channel.writeInbound(input.retain()));

        testFramesDecoded2();
        assertTrue (channel.finish());
// read messages
        ByteBuf read = ( ByteBuf ) channel.readInbound();
        assertEquals(buf.readSlice(3), read);
        read.release();
        read = ( ByteBuf ) channel.readInbound();
        assertEquals(buf.readSlice(3), read);
        read.release();
        read = ( ByteBuf ) channel.readInbound();
        assertEquals(buf.readSlice(3), read);
        read.release();
        assertNull(channel.readInbound());
        buf.release();
    }

    @Test
    public void testFramesDecoded2() {
        ByteBuf buf = Unpooled.buffer();
        for (int i = 0; i < 9; i++) {
            buf.writeByte(i);
        }
        ByteBuf input = buf.duplicate();
        EmbeddedChannel channel = new EmbeddedChannel(new FixedLengthFrameDecoder(3));
        assertFalse(channel.writeInbound(input.readBytes(2)));
        assertTrue(channel.writeInbound(input.readBytes(7)));
        assertTrue(channel.finish());
        ByteBuf read = ( ByteBuf ) channel.readInbound();
        assertEquals(buf.readSlice(3), read);
        read.release();
        read = ( ByteBuf ) channel.readInbound();
        assertEquals(buf.readSlice(3), read);
        read.release();
        read = ( ByteBuf ) channel.readInbound();
        assertEquals(buf.readSlice(3), read);
        read.release();
        assertNull(channel.readInbound());
        buf.release();
    }
}