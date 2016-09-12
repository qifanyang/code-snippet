package mysql.client.packet;

import io.netty.buffer.ByteBuf;
import lombok.Data;
import mysql.client.ProtocolUtils;

/**
 * 当连接到MySQL Server时会收到该包
 * @author yangqf
 * @version 1.0 2016/9/10
 */
@Data
public class InitialHandshakePacket implements Packet{
    private byte protocolVersion;//协议版本,添加版本号是为了扩展该协议,新版本的server,可以添加多功能
    private String serverVersion;//string<null>
    private long connectionId;
    private String seed;//string<8>  seed1 用于challenge handshake

    private int serverCapabilities;
    private byte serverCharsetIndex;
    private short serverStatus;
    private byte authPluginDataLength;

    @Override
    public ByteBuf serialized(){
        return null;
    }

    @Override
    public void deserialized(ByteBuf buf){
        protocolVersion = buf.readByte();
        serverVersion = ProtocolUtils.readNullTerminalString(buf);
        connectionId = buf.readUnsignedInt();
        //seed1
        String authPluginDataPart1 = ProtocolUtils.readFixLengthString(buf, 8);
        buf.readByte();//[00] filter

        serverCapabilities = buf.readShort();//lower 2 bytes 服务器端功能
        //========上面是V9 数据部分, 接下来是V10 新增部分==========

        serverCharsetIndex = buf.readByte();
        serverStatus = buf.readShort();
        serverCapabilities |= buf.readShort() << 16;//服务器支持的功能还用两字节表示,很明显这两字节是后面加的

        if((serverCapabilities & CLIENT_PLUGIN_AUTH) != 0){
            authPluginDataLength = buf.readByte();//length of auth-plugin-data
        }else {
            buf.readByte();//skip 不处理这种情况
        }

        buf.skipBytes(10);//10 bytes reversed

        String authPluginDataPart2 = ProtocolUtils.readFixLengthString(buf, Math.max(13, authPluginDataLength - 8));
        seed = authPluginDataPart1 + authPluginDataPart2;

        //Initial Handshake Packet 包读取完毕
    }
}
