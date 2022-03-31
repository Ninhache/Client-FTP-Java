package ftp.client.io;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

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
			StringBuilder sb = new StringBuilder(readln());
			while (ready()) {
				sb.append("\n");
				sb.append(readln());
			}
			return sb.toString();
		} catch (NullPointerException ex) {
			return "";
		}
	}
	
	@Override
	void close();
}
