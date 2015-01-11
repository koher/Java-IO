package org.koherent.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Serializations {
	private Serializations() {
	}

	public static byte[] toByteArray(Object object) {
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		ObjectOutputStream out;
		try {
			out = new ObjectOutputStream(byteOut);
			out.writeObject(object);
		} catch (IOException e) {
			throw new Error("Never reaches here.", e);
		}
		return byteOut.toByteArray();
	}

	@SuppressWarnings("unchecked")
	public static <T> T toObject(byte[] bytes) throws ClassNotFoundException {
		try {
			return (T) new ObjectInputStream(new ByteArrayInputStream(bytes))
					.readObject();
		} catch (IOException e) {
			throw new Error("Never reaches here.");
		}
	}
}
