package hr.fer.zemris.java.hw06.crypto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw06.crypto.Util;

class UtilTest {

	@Test
	void testHexToByte() {
		byte[] array = Util.hextobyte("01aE22"); 
		
		assertEquals(array[0], 1); 
		assertEquals(array[1], -82);
		assertEquals(array[2], 34);
	}
	
	@Test
	public void testHexToByteException() {
		assertThrows(IllegalArgumentException.class, () -> {
			Util.hextobyte("abcdefgh");
		});
		
		assertThrows(IllegalArgumentException.class, () -> {
			Util.hextobyte("1");
		});
		
		assertThrows(IllegalArgumentException.class, () -> {
			Util.hextobyte("123");
		});
	}

	@Test
	public void testHexToByteEmpty() {
		byte[] array = Util.hextobyte(""); 
		
		assertEquals(array.length, 0);
	}
	
	@Test
	public void testHexToByteNullPointerException() {
		assertThrows(NullPointerException.class, () -> 
			Util.hextobyte(null)
		);
	}
	
	
	@Test
	public void testByteToHex() {
		byte[] array = new byte[] {1, -82, 34};
		String result =  Util.bytetohex(array); 
		assertEquals(result, "01ae22");
		
		assertArrayEquals(Util.hextobyte(result), array);
	}
	
	@Test
	public void testByteToHex2() {
		byte[] array = new byte[] {0, 0, -1, 127, -128};
		String result =  Util.bytetohex(array); 
		assertEquals(result, "0000ff7f80");
		
		assertArrayEquals(Util.hextobyte(result), array);
	}
	
	@Test
	public void testByteToHexException() {
		assertThrows(NullPointerException.class, () -> {
			Util.bytetohex(null);
		});
	}
	
}
