package org.apache.bcel.util;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public final class ByteSequence extends DataInputStream {
	private ByteArrayStream byte_stream = (ByteArrayStream) in;

	private static final class ByteArrayStream extends ByteArrayInputStream {
		ByteArrayStream(byte[] bytes) {
			super(bytes);
		}

		final int getPosition() {
			return pos;
		}

		final void unreadByte() {
			if (pos > 0)
				pos--;
		}
	}

	public ByteSequence(byte[] bytes) {
		super(new ByteArrayStream(bytes));
	}

	public final int getIndex() {
		return byte_stream.getPosition();
	}

	final void unreadByte() {
		byte_stream.unreadByte();
	}
}
