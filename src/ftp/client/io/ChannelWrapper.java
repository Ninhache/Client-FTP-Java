package ftp.client.io;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/*
 * Encapsule un canal de communication afin de pouvoir changer de canal de façon transparente dans le client 
 */
public class ChannelWrapper implements Channel {
	protected Channel channel;

	public ChannelWrapper() {}
	
	public ChannelWrapper(Channel channel) {
		setChannel(channel);
	}
	
	public Channel getChannel() {
		return channel;
	}
	
	public void setChannel(Channel channel) {
		setChannel(channel, true);
	}
	
	public void setChannel(Channel channel, boolean closePrevious) {
		if (closePrevious && this.channel != null && !this.channel.getSocket().isClosed()) {
			this.channel.close();
		}
		this.channel = channel;
	}
	
	
	@Override
	public void connect() throws IOException {
		channel.connect();
	}

	@Override
	public void println(String value) {
		channel.println(value);
	}

	@Override
	public void println(String... value) {
		channel.println(value);
	}

	@Override
	public boolean ready() throws IOException {
		return channel.ready();
	}

	@Override
	public String readln() throws IOException {
		return channel.readln();
	}

	@Override
	public Socket getSocket() {
		return channel.getSocket();
	}

	@Override
	public InetAddress getAddress() {
		return channel.getAddress();
	}

	@Override
	public String getHost() {
		return channel.getHost();
	}

	@Override
	public int getPort() {
		return channel.getPort();
	}

	@Override
	public void close() {
		channel.close();
	}
}
