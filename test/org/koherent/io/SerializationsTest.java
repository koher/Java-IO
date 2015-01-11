package org.koherent.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class SerializationsTest {
	@Test
	public void testString() {
		test("ABC");
	}

	@Test
	public void testInteger() {
		test(123);
	}

	@Test
	public void testList() {
		test(Arrays.asList("ABC", "DEF", "GHI"));
	}

	@Test
	public void testMap() {
		Map<String, Integer> map = new HashMap<>();
		map.put("ABC", 123);
		map.put("DEF", 456);
		map.put("GHI", 789);

		test(map);
	}

	@Test
	public void testPerson() {
		test(new Person("Alber", "Einstein", 28));
	}

	private <T> void test(T object) {
		byte[] bytes = Serializations.toByteArray(object);
		try {
			T object2 = Serializations.toObject(bytes);
			assertEquals(object, object2);
			assertTrue(equals(bytes, Serializations.toByteArray(object2)));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	private static boolean equals(byte[] bytes1, byte[] bytes2) {
		int length = bytes1.length;
		if (bytes2.length != length) {
			return false;
		}

		for (int byteIndex = 0; byteIndex < length; byteIndex++) {
			if (bytes1[byteIndex] != bytes2[byteIndex]) {
				return false;
			}
		}

		return true;
	}

	private static class Person implements Serializable {
		private static final long serialVersionUID = 1L;

		private String firstName;
		private String lastName;
		private int age;

		public Person(String firstName, String lastName, int age) {
			this.firstName = firstName;
			this.lastName = lastName;
			this.age = age;
		}

		@Override
		public boolean equals(Object obj) {
			Person another = (Person) obj;

			return firstName.equals(another.firstName)
					&& lastName.equals(another.lastName) && age == another.age;
		}
	}
}
