package ftp.client.io;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

public interface Channel extends Closeable {
	void connect() throws IOException;
	void println(String value);
	void println(String... value);
	boolean ready() throws IOException;
	String readln() throws IOException;
	
	Socket getSocket();
	InetAddress getAddress();
	String getHost();
	int getPort();
	
	default String readlns() throws IOException {
		try {
			StringBuilder sb = new StringBuilder();
			while(sb.length() == 0) {
				String line;
				while ((line = readln()) != null) {
					if (sb.length() > 0) {
						sb.append("\n");						
					}
					sb.append(line);
				}
			}
			return sb.toString();
		} catch (NullPointerException|SocketTimeoutException ex) {
			return "";
		}
	}
	
	@Override
	void close();
}
