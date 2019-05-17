package hr.fer.zemris.java.gui.prim;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;


class PrimListModelTest {

	@Test
	void testNext() {
		PrimListModel model = new PrimListModel();
		model.next();
		
		assertEquals(model.getElementAt(0), 1);
		assertEquals(model.getElementAt(1), 2);
		
		model.next();
		
		assertEquals(model.getElementAt(2), 3);
		
		model.next();
		model.next();
		model.next();
		model.next();
		
		assertEquals(model.getElementAt(6), 13);
	}
	
	@Test
	public void testGetSize() {
		PrimListModel model = new PrimListModel();
		
		assertEquals(model.getSize(), 1);
		
		model.next();
		model.next();
		model.next();
		model.next();
		model.next();
		model.next();
		model.next();
		model.next();
		
		assertEquals(model.getSize(), 9);
	}
	
	@Test
	public void testGetElementAt() {
		PrimListModel model = new PrimListModel();
		
		model.next(); // 2 
		model.next(); // 3
		model.next(); // 5
		model.next(); // 7
		model.next(); // 11
		model.next(); // 13
		model.next(); // 17
		model.next(); // 19
		
		assertEquals(model.getElementAt(8), 19);
		assertEquals(model.getElementAt(4), 7);
		
		assertThrows(IndexOutOfBoundsException.class, () ->  {
			model.getElementAt(-1);
		});
		
		assertThrows(IndexOutOfBoundsException.class, () -> {
			model.getElementAt(9);
		});
	}
	
	@Test
	public void testAddListDataListener() {
		PrimListModel model = new PrimListModel();
		
		assertThrows(NullPointerException.class, () -> {
			model.addListDataListener(null);
		});
	}
	
	@Test
	public void tesstRemoveListDataListener() {
		PrimListModel model = new PrimListModel();
		
		assertThrows(NullPointerException.class, () -> {
			model.removeListDataListener(null);
		});
	}

}
