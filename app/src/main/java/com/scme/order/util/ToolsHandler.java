package com.scme.order.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ToolsHandler {

	/**
	 * InputStreamתByte
	 * @param InputStream is
	 * @return byte 
	 */
	public byte[] InputStreamToByte(InputStream is) throws IOException 
	{  
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int len = 0;
			byte[] b = new byte[1024];
			while ((len = is.read(b, 0, b.length)) != -1) 
			{                     
			     baos.write(b, 0, len);
			 }
			byte[] buffer =  baos.toByteArray();
			return buffer;
	}
}
