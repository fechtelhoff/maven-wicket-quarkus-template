package ${package}.web.gui;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SecondPageMO implements Serializable {

	private final LocalDateTime timestamp;

	public SecondPageMO(final LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getTimestampAsString() {
		return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(timestamp);
	}
}
