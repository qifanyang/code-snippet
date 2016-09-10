package mysql.client.packet;

/**
 * 当连接到MySQL Server时会收到该包
 * @author yangqf
 * @version 1.0 2016/9/10
 */
public class InitialHandshakePacket{
    private byte protocolVersion;//协议版本,添加版本号是为了扩展该协议,新版本的server,可以添加多功能
    private String serverVersion;//string<null>
    private int connectionId;
    private String authPluginDataPart1;//string<8>  seed1 用于challenge handshake


}
