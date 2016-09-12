package mysql.client.packet;

import io.netty.buffer.ByteBuf;

/**
 * Data between client and server is exchanged in packets of max 16MByte size.
 * @author yangqf
 * @version 1.0 2016/9/12
 */
public interface Packet{
    //http://dev.mysql.com/doc/internals/en/capability-flags.html
    //this capablity flags are uesd by the client and server to indicate which features they support and want to use
    int CLIENT_PLUGIN_AUTH = 0x00080000;//Sends extra data in Initial Handshake Packet and supports the pluggable authentication protocol. client Supports authentication plugins.
    int CLIENT_PLUGIN_AUTH_LENENC_CLIENT_DATA = 0x00200000;//2097152
    int CLIENT_SECURE_CONNECTION = 0x00008000;//32768
    int CLIENT_TRANSACTIONS = 0x00002000;//8192
    int CLIENT_INTERACTIVE = 0x00000400;//1024 wait_timeout versus wait_interactive_timeout.
    int CLIENT_PROTOCOL_41 = 0x00000200;//512
    int CLIENT_CONNECT_WITH_DB = 0x00000008;//在handshake_response_packet中指明数据包名
    int CLIENT_LONG_FLAG = 0x00000004;
    int CLIENT_LONG_PASSWORD = 0x00000001;

    int HEADER_LENGTH = 4;
    int AUTH_411_OVERHEAD = 33;

    ByteBuf serialized();

    void deserialized(ByteBuf buf);

}
