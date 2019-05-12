package hr.fer.zemris.lysytems.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl.MyLSystems;

class LSystemBuilderImplTest {

	@Test
	void test() {
		LSystemBuilderImpl builder = new LSystemBuilderImpl();
		builder.setAxiom("F");
		builder.registerProduction('F', "F+F--F+F");
		
		MyLSystems mySystems = builder.new MyLSystems();
		
		String s1 = mySystems.generate(0);
		assertEquals(s1, "F");
		
		String s2 = mySystems.generate(1);
		assertEquals(s2, "F+F--F+F");
		
		String s3 = mySystems.generate(2);
		assertEquals(s3, "F+F--F+F+F+F--F+F--F+F--F+F+F+F--F+F");
	}
	
	@Test
	public void test2() {
		LSystemBuilderImpl builder = new LSystemBuilderImpl();
		builder.setAxiom("F");
		builder.registerProduction('F', "F+");
		
		MyLSystems mySystems = (MyLSystems) builder.build();
		
		String s1 = mySystems.generate(0);
		assertEquals(s1, "F");
		
		String s2 = mySystems.generate(1);
		assertEquals(s2, "F+");
		
		String s3 = mySystems.generate(2);
		assertEquals(s3, "F++");
	}

}
