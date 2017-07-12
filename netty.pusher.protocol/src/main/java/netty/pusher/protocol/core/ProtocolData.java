package netty.pusher.protocol.core;

import org.msgpack.MessagePack;

import netty.pusher.protocol.enums.CompressType;
import netty.pusher.protocol.enums.ProtocolType;
import netty.pusher.protocol.enums.SerializeType;
import netty.pusher.protocol.protocol.Protocol;
import netty.pusher.protocol.util.ByteConverter;

/**
 * 
 * 1byte(版本号) | 4byte(协义总长度) | 1byte(消息体类型) | 1byte 所采用的压缩算法  | 1byte 序列化规则  | n byte消息体
 * 
 * 初始标记 和 结束标记 不计入协议长度
 * 
 * @author wly
 *
 */

public class ProtocolData {
	
	private static final byte version = 1;
	
	private int totalLen;
	
	private ProtocolType protocolType;
	
	private CompressType compressType = CompressType.UnCompress;
	
	private SerializeType serializeType = SerializeType.MsgPack;
	
	private Protocol protocol;

	private static final MessagePack messagePack = new MessagePack();
	
	/**
	 * 协义头8个byte
	 */
	private final static int HEAD_STACK_LENGTH = 8;
	
	public ProtocolData(ProtocolType protocolType,CompressType compressType,SerializeType serializeType,Protocol protocol){
		this.protocolType = protocolType;
		if(compressType != null){
			this.compressType = compressType;
		}
		if(serializeType != null){
			this.serializeType = serializeType;
		}
		this.protocol = protocol;
	}
	
	public ProtocolData(ProtocolType protocolType,Protocol protocol){
		this(protocolType, null, null, protocol);
	}
	
	public ProtocolData(ProtocolType protocolType){
		this(protocolType, null);
	}
	
	public ProtocolData(){
		
	}
	
	public static byte getVersion() {
		return version;
	}

	public int getTotalLen() {
		return totalLen;
	}

	public void setTotalLen(int totalLen) {
		this.totalLen = totalLen;
	}

	public ProtocolType getProtocolType() {
		return protocolType;
	}

	public void setProtocolType(ProtocolType protocolType) {
		this.protocolType = protocolType;
	}

	public CompressType getCompressType() {
		return compressType;
	}

	public void setCompressType(CompressType compressType) {
		this.compressType = compressType;
	}

	public SerializeType getSerializeType() {
		return serializeType;
	}

	public void setSerializeType(SerializeType serializeType) {
		this.serializeType = serializeType;
	}

	public Protocol getProtocol() {
		return protocol;
	}

	public void setProtocol(Protocol protocol) {
		this.protocol = protocol;
	}

	public byte[] toBytes() throws Exception{
		int startIndex = 0;
		byte[] realData = messagePack.write(protocol);
		int protocolLen = HEAD_STACK_LENGTH + realData.length;
		byte[] data = new byte[protocolLen];
		
		data[0] = ProtocolData.version;
		startIndex += ProtocolStruct.Version;
		
		//小端序
		System.arraycopy(ByteConverter.intToBytesLittleEndian(this.getTotalLen()), 0, data, startIndex, ProtocolStruct.TotalLen);
		startIndex += ProtocolStruct.TotalLen;
		
		data[startIndex] = (byte) this.getProtocolType().getNum();
		startIndex += ProtocolStruct.ProtocolType;
		
		data[startIndex] = (byte) this.getCompressType().getNum();
		startIndex += ProtocolStruct.CompressType;
		
		data[startIndex] = (byte) this.getSerializeType().getNum();
		startIndex += ProtocolStruct.SerializeType;
		
		System.arraycopy(realData, 0, data, startIndex, protocolLen - startIndex);
		
		return data;
	}
	
	public static ProtocolData fromBytes(byte[] data) throws Exception {
		ProtocolData protocolData = new ProtocolData();
		int startIndex = 0;
        if(data[startIndex] != ProtocolData.version) {
        	throw new Exception("协义版本错误");
        }
        
        startIndex += ProtocolStruct.Version;
        
        
        byte[] totalLengthByte = new byte[ProtocolStruct.TotalLen];
        for (int i = 0; i < ProtocolStruct.TotalLen; i++) {
        	totalLengthByte[i] = data[startIndex + i];
        }
        protocolData.setTotalLen(ByteConverter.bytesToIntLittleEndian(totalLengthByte));
        startIndex += ProtocolStruct.TotalLen;//5
        
        ProtocolType protocolType = ProtocolType.getProtocolType(data[startIndex]);
        protocolData.setProtocolType(protocolType);
        startIndex += ProtocolStruct.ProtocolType;
        
        CompressType compressType = CompressType.getCompressType(data[startIndex]);
        protocolData.setCompressType(compressType);
        startIndex += ProtocolStruct.CompressType;
        
        SerializeType serializeType = SerializeType.getSerializeType(startIndex);
        protocolData.setSerializeType(serializeType);
        startIndex += ProtocolStruct.SerializeType;
        
        byte[] realData = new byte[data.length - ProtocolData.HEAD_STACK_LENGTH];
        System.arraycopy(data,startIndex,realData,0,realData.length);
        Protocol protocol = (Protocol) messagePack.read(realData,ProtocolType.getProtocolClass(protocolType));
        protocolData.setProtocol(protocol);
        
        return protocolData;
	}
	
	
}
