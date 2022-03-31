package ftp.client.io;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.function.Consumer;

/*
 * Encapsule un canal de communication afin de pouvoir changer de canal de façon transparente dans le client 
 */
public class ChannelWrapper implements Channel {
	protected Channel channel;
	
	protected final Consumer<Channel> CLOSED_CALLBACK;

	public ChannelWrapper() {
		this(null);
	}
	
	public ChannelWrapper(Consumer<Channel> closedCallback) {
		this(null, closedCallback);
	}
	
	public ChannelWrapper(Channel channel, Consumer<Channel> closedCallback) {
		setChannel(channel);
		CLOSED_CALLBACK = closedCallback;
	}
	
	public Channel getChannel() {
		return channel;
	}
	
	public void setChannel(Channel channel) {
		System.out.println("=== DATA CHANNEL ASSIGNED ===");
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
	public void close() {}
	
	public void closeChannel() {
		if (CLOSED_CALLBACK != null) {
			CLOSED_CALLBACK.accept(channel);
		}
		channel.close();
	}
}
