package mysql.client.packet;

import lombok.Data;

/**
 * 包含长度,和序列号, 因为包解析需要使用包长度来解析string<eof>类型字段
 * @author yangqf
 * @version 1.0 2016/9/12
 */
@Data
public abstract class AbstractPacket implements Packet{
    /**
     * 数据包长度,int<3>
     */
    private int length;
    /**
     * 多包时分包的序列号,开始新的命令时该值重置为0, int<1>
     */
    private byte sequenceId;
}
