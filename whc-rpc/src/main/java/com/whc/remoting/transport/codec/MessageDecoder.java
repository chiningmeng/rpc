package com.whc.remoting.transport.codec;

import com.whc.compress.Compress;
import com.whc.enums.CompressTypeEnum;
import com.whc.enums.WhereEnum;
import com.whc.enums.SerializationTypeEnum;
import com.whc.monitor.Monitor;
import com.whc.monitor.time.ServerTimeLine;
import com.whc.monitor.time.TimeLine;
import com.whc.remoting.dto.Message;
import com.whc.remoting.dto.Request;
import com.whc.remoting.dto.Response;
import com.whc.extensions.ExtensionLoader;
import com.whc.remoting.constants.RpcConstants;
import com.whc.remoting.serialize.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class MessageDecoder extends LengthFieldBasedFrameDecoder {

    public MessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
    }

    public MessageDecoder() {
        // lengthFieldOffset: magic code  4B, 版本号 1B, 5
        // lengthFieldLength: 全长 4B.  4
        // lengthAdjustment: 内容（content）要刨除报文头（header）和长度（length） -9
        // initialBytesToStrip: 因为需要校验magic code，不跳过任何字节  故为0
        this(RpcConstants.MAX_FRAME_LENGTH, 5, 4, -9, 0);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        long phaseStarTimeStamp = System.currentTimeMillis();
        Object decoded = super.decode(ctx, in);
        if (decoded instanceof ByteBuf) {
            ByteBuf byteBuf = (ByteBuf) decoded;
            if (byteBuf.readableBytes() >= RpcConstants.TOTAL_LENGTH) {
                try {
                    checkMagicNumber(byteBuf);
                    checkVersion(byteBuf);
                    int fullLength = byteBuf.readInt();
                    // build RpcMessage object
                    byte messageType = byteBuf.readByte();
                    byte codecType = byteBuf.readByte();
                    byte compressType = byteBuf.readByte();
                    int requestId = byteBuf.readInt();
                    Message message = Message.builder()
                            .codec(codecType)
                            .messageType(messageType).build();
                    if (messageType == RpcConstants.HEARTBEAT_REQUEST_TYPE) {
                        message.setData(RpcConstants.PING);
                        return message;
                    }
                    if (messageType == RpcConstants.HEARTBEAT_RESPONSE_TYPE) {
                        message.setData(RpcConstants.PONG);
                        return message;
                    }
                    int bodyLength = fullLength - RpcConstants.HEAD_LENGTH;
                    if (bodyLength > 0) {
                        byte[] bs = new byte[bodyLength];
                        byteBuf.readBytes(bs);
                        // decompress the bytes
                        String compressName = CompressTypeEnum.getName(compressType);
                        Compress compress = ExtensionLoader.getExtensionLoader(Compress.class)
                                .getExtension(compressName);
                        bs = compress.decompress(bs);
                        // 反序列化
                        String codecName = SerializationTypeEnum.getName(message.getCodec());
                        log.info("codec name: [{}] ", codecName);
                        Serializer serializer = ExtensionLoader.getExtensionLoader(Serializer.class)
                                .getExtension(codecName);
                        if (messageType == RpcConstants.REQUEST_TYPE) {
                            Request request = serializer.deserialize(bs, Request.class);
                            message.setData(request);

                            TimeLine timeLine = new ServerTimeLine(String.valueOf(request.getRequestId()), WhereEnum.Server);
                            timeLine.setStartTimeStamp(phaseStarTimeStamp);
                            timeLine.phaseEndAndNext(TimeLine.Phase.DESERIALIZE);
                            Monitor.addTimeLine(timeLine.getTraceId(), timeLine);
                        } else {
                            Response response = serializer.deserialize(bs, Response.class);
                            message.setData(response);

                            TimeLine timeLine = Monitor.getTimeLine(String.valueOf(response.getRequestId()));
                            timeLine.phaseEndWithTimeStamp(TimeLine.Phase.DESERIALIZE, phaseStarTimeStamp);
                        }
                    }
                    return message;
                } catch (Exception e) {
                    log.error("Decode byteBuf error!", e);
                    throw e;
                } finally {
                    byteBuf.release();
                }
            }
        }
        return decoded;
    }


    //todo 去version
    private void checkVersion(ByteBuf in) {
        // read the version and compare
        byte version = in.readByte();
        if (version != RpcConstants.VERSION) {
            throw new RuntimeException("version isn't compatible" + version);
        }
    }

    private void checkMagicNumber(ByteBuf in) {
        // read the first 4 bit, which is the magic number, and compare
        int len = RpcConstants.MAGIC_NUMBER.length;
        byte[] tmp = new byte[len];
        in.readBytes(tmp);
        for (int i = 0; i < len; i++) {
            if (tmp[i] != RpcConstants.MAGIC_NUMBER[i]) {
                throw new IllegalArgumentException("Unknown magic code: " + Arrays.toString(tmp));
            }
        }
    }

}
