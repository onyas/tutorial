package com.onyas.mina.filetransfer.util;

import org.apache.mina.filter.codec.demux.DemuxingProtocolCodecFactory;

import com.onyas.mina.filetransfer.bean.BaseMessage;

public class MathProtocolCodecFactory extends DemuxingProtocolCodecFactory{
	
	public MathProtocolCodecFactory(boolean server){
		if(server){
			super.addMessageDecoder(BaseMessageDecoder.class);
		}else{
			super.addMessageEncoder(BaseMessage.class, BaseMessageEncoder.class);
		}
	}
}
