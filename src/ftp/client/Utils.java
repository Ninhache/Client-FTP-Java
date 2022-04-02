package ftp.client;

public final class Utils {
	private Utils() {}
	
	public static final void println(StringBuilder sb, String line) {
		if (line == null) {
			return;
		}
		if (sb.length() > 0) {
			sb.append("\n");
		}
		sb.append(line);
	}
	
	public static final void println(StringBuilder sb, String format, Object... values) {
		println(sb, String.format(format, values));
	}
}
