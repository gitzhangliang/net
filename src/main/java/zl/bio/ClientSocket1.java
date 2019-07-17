package zl.bio;



/**
 * @author zhangliang
 */
public class ClientSocket1 extends AbstractClientSocket{
    public static void main(String[] args) throws Exception {
        new ClientSocket1().connect("127.0.0.1",8080,"你好,server,I am client 1");
    }
}
