package hr.fer.zemris.java.gui.layouts;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.jupiter.api.Test;

class CalcLayoutTest {

	@Test
	public void testFirstExampleFromHomework() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel("");
		l1.setPreferredSize(new Dimension(10, 30));
		JLabel l2 = new JLabel("");
		l2.setPreferredSize(new Dimension(20, 15));
		p.add(l1, new RCPosition(2, 2));
		p.add(l2, new RCPosition(3, 3));
		Dimension dim = p.getPreferredSize();

		assertEquals(dim.getWidth(), 152);
		assertEquals(dim.getHeight(), 158);
	}
	
	@Test
	public void testSecondExampleFromHomework() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(108,15));
		JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(16,30));
		p.add(l1, new RCPosition(1,1));
		p.add(l2, new RCPosition(3,3));
		Dimension dim = p.getPreferredSize();
		
		assertEquals(dim.getWidth(), 152);
		assertEquals(dim.getHeight(), 158);
	}

	/**
	 * Case when component with row and column 1 is too large. For example: if this
	 * component has preferred width 200 and all other components have preferred
	 * width 10 and gap is equal to zero then total preferred size is 40 * 7 = 280,
	 * because columns from 1 to 5 have width 40.
	 */
	@Test
	public void myFirstExample() {
		JPanel p = new JPanel(new CalcLayout(2));

		JLabel l1 = new JLabel("This is some really long text in component with row and col equal to one.");

		JLabel l2 = new JLabel("");
		l2.setPreferredSize(new Dimension(20, 15));
		p.add(l1, new RCPosition(1, 1));
		p.add(l2, new RCPosition(3, 3));
		Dimension dim = p.getPreferredSize();

		assertEquals(dim.getWidth(), 572);
		assertEquals(dim.getHeight(), 83);
	}
	
	@Test
	public void testException() {
		JPanel p = new JPanel(new CalcLayout(2));
		
		JLabel l1 = new JLabel("This is some text");
		JLabel l2 = new JLabel("This is some another text");
		
		RCPosition constraint = new RCPosition(2, 2);
		p.add(l1, constraint);
		 
		// Add with the same constraint.
		assertThrows(CalcLayoutException.class, () -> {
			p.add(l2, constraint);
		});
		
		assertThrows(CalcLayoutException.class, () -> {
			p.add(l2, new RCPosition(1,2));
		});
		
		assertThrows(CalcLayoutException.class, () -> {
			p.add(l2, new RCPosition(1, 5));
		});
		
		assertThrows(CalcLayoutException.class, () -> {
			p.add(l2, new RCPosition(-1, 1));
		});
		
		assertThrows(CalcLayoutException.class, () -> {
			p.add(l2, new RCPosition(0, 1));
		});
		
		assertThrows(CalcLayoutException.class, () -> {
			p.add(l2, new RCPosition(1, -1));
		});
		
		assertThrows(CalcLayoutException.class, () -> {
			p.add(l2, new RCPosition(6, 1));
		});
		
		assertThrows(CalcLayoutException.class, () -> {
			p.add(l2, new RCPosition(2, 8));
		});
	}
}
