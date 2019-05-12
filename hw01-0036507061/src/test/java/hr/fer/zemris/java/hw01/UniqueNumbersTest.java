package hr.fer.zemris.java.hw01;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw01.UniqueNumbers.TreeNode;

/**
 * JUnit test cases to test methods addNode, containsValue, treeSize from class
 * UniqueNumbers.
 * 
 * @author david
 *
 */
class UniqueNumbersTest {

	@Test
	public void testAddNode() {
		TreeNode root = null;

		root = UniqueNumbers.addNode(root, 12);
		root = UniqueNumbers.addNode(root, 13);
		root = UniqueNumbers.addNode(root, 11);

		assertEquals(root.left.value, 11);
	}

	@Test
	public void testForDuplicates() {
		TreeNode root = null;

		root = UniqueNumbers.addNode(root, 12);
		root = UniqueNumbers.addNode(root, 12);

		assertEquals(UniqueNumbers.treeSize(root), 1);
	}

	@Test
	public void testTreeSize() {
		TreeNode root = null;

		root = UniqueNumbers.addNode(root, 12);
		root = UniqueNumbers.addNode(root, 13);
		root = UniqueNumbers.addNode(root, 11);

		assertEquals(UniqueNumbers.treeSize(root), 3);
	}

	@Test
	public void testContainsValue() {
		TreeNode root = null;

		root = UniqueNumbers.addNode(root, 7);
		root = UniqueNumbers.addNode(root, 4);
		root = UniqueNumbers.addNode(root, 1);
		root = UniqueNumbers.addNode(root, -11);

		assertTrue(UniqueNumbers.containsValue(root, -11));
	}

	@Test
	public void testForEmptyTree() {
		TreeNode root = null;

		assertEquals(UniqueNumbers.treeSize(root), 0);
	}

	@Test
	public void testForNullPointerException() {

		assertThrows(NullPointerException.class, () -> {
			TreeNode root = null;
			@SuppressWarnings({ "unused", "null" })
			int value = root.value;
		});
	}

	@Test
	public void testForNonExistingLeftChild() {

		assertThrows(NullPointerException.class, () -> {
			TreeNode root = null;

			root = UniqueNumbers.addNode(root, 12);
			root = UniqueNumbers.addNode(root, 34);

			@SuppressWarnings("unused")
			int value = root.left.value;
		});
	}
}
