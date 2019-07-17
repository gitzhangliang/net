package zl.jdknio.classtest;

import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * @author zhangliang
 * @date 2019/6/27.
 */
public class ByteBufferTest {

    @Test
    public void clear(){
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        byteBuffer.put("abc".getBytes());
        System.out.println("position--"+byteBuffer.position());
        System.out.println("limit--"+byteBuffer.limit());
        System.out.println("remain--"+byteBuffer.remaining());
        System.out.println(new String(byteBuffer.array()));

        byteBuffer.clear();
        System.out.println("position--"+byteBuffer.position());
        System.out.println("limit--"+byteBuffer.limit());
        System.out.println("remain--"+byteBuffer.remaining());
        System.out.println(new String(byteBuffer.array()));

        byteBuffer.put("d".getBytes());
        System.out.println("position--"+byteBuffer.position());
        System.out.println("limit--"+byteBuffer.limit());
        System.out.println("remain--"+byteBuffer.remaining());
        System.out.println(new String(byteBuffer.array()));

    }
}
