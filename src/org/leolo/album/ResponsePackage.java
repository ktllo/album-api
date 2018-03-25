package org.leolo.album;

import java.io.IOException;
import java.io.OutputStream;

public abstract class ResponsePackage {
	
	public abstract String getContentType();
	
	public void write(OutputStream out) throws IOException{
		out.write(getData());
	}
	
	protected abstract byte[] getData();
}
