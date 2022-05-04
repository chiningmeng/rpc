package com.whc.remoting.transport.codec;

import com.whc.compress.Compress;
import com.whc.enums.CompressTypeEnum;
import com.whc.enums.SerializationTypeEnum;
import com.whc.monitor.Monitor;
import com.whc.monitor.time.ClientTimeLine;
import com.whc.monitor.time.TimeLine;
import com.whc.remoting.constants.RpcConstants;
import com.whc.remoting.dto.Message;
import com.whc.remoting.dto.Request;
import com.whc.remoting.dto.Response;
import com.whc.remoting.serialize.Serializer;
import com.whc.extensions.ExtensionLoader;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class MessageEncoder extends MessageToByteEncoder<Message> {
    private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger(0);
    @Override
    protected void encode(ChannelHandlerContext ctx, Message rpcMessage, ByteBuf out) {
        try {
            TimeLine timeLine = new ClientTimeLine();
            if (rpcMessage.getData() instanceof Request) {
                timeLine = Monitor.getTimeLine(String.valueOf(((Request)rpcMessage.getData()).getRequestId()));
            } else if (rpcMessage.getData() instanceof Response) {
                timeLine = Monitor.getTimeLine(String.valueOf(((Response)rpcMessage.getData()).getRequestId()));
            }


            out.writeBytes(RpcConstants.MAGIC_NUMBER);
            out.writeByte(RpcConstants.VERSION);
            // leave a place to write the value of full length
            out.writerIndex(out.writerIndex() + 4);
            byte messageType = rpcMessage.getMessageType();
            out.writeByte(messageType);
            out.writeByte(rpcMessage.getCodec());
            out.writeByte(CompressTypeEnum.GZIP.getCode());
            out.writeInt(ATOMIC_INTEGER.getAndIncrement());
            // build full length
            byte[] bodyBytes = null;
            int fullLength = RpcConstants.HEAD_LENGTH;
            // if messageType is not heartbeat message,fullLength = head length + body length
            if (messageType != RpcConstants.HEARTBEAT_REQUEST_TYPE
                    && messageType != RpcConstants.HEARTBEAT_RESPONSE_TYPE) {
                // serialize the object
                String codecName = SerializationTypeEnum.getName(rpcMessage.getCodec());
                log.info("codec name: [{}] ", codecName);
                Serializer serializer = ExtensionLoader.getExtensionLoader(Serializer.class)
                        .getExtension(codecName);
                bodyBytes = serializer.serialize(rpcMessage.getData());
                // compress the bytes
                String compressName = CompressTypeEnum.getName(rpcMessage.getCompress());
                Compress compress = ExtensionLoader.getExtensionLoader(Compress.class)
                        .getExtension(compressName);
                bodyBytes = compress.compress(bodyBytes);
                fullLength += bodyBytes.length;
            }

            if (bodyBytes != null) {
                out.writeBytes(bodyBytes);
            }
            int writeIndex = out.writerIndex();
            out.writerIndex(writeIndex - fullLength + RpcConstants.MAGIC_NUMBER.length + 1);
            out.writeInt(fullLength);
            out.writerIndex(writeIndex);

            timeLine.phaseEndAndNext(TimeLine.Phase.SERIALIZE);
            if (rpcMessage.getMessageType() == RpcConstants.RESPONSE_TYPE) {
                timeLine.setTotalTime();
            }
            if (rpcMessage.getData() instanceof Request) {
                timeLine.setRequestSize(bodyBytes.length);
            } else if (rpcMessage.getData() instanceof Response) {
                timeLine.setResponseSize(bodyBytes.length);
            }
        } catch (Exception e) {
            log.error("Encode request error!", e);
        }

    }

}
