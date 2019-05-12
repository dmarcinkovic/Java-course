package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Program that allows user to input numbers in binary search tree. Left subtree
 * of binary search tree contains smaller values and right subtree contains only
 * greater numbers. Duplicates are not allowed. Program prints number in
 * increasing and decreasing order.
 * 
 * @author david
 *
 */
public class UniqueNumbers {

	/**
	 * Method invoked when running the program. Arguments explained below. Method
	 * allows user to input numbers which will be inserted in binary search tree. To
	 * exit input type 'kraj'. At the end method prints number in increasing and
	 * decreasing order.
	 * 
	 * @param args Arguments given from command line. In this program they are not
	 *             used-
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		TreeNode root = null;

		while (true) {
			System.out.print("Unesite broj > ");
			String input = sc.next();

			if (input.equals("kraj")) {
				break;
			}

			try {
				int value = Integer.parseInt(input);
				
				if (containsValue(root, value)) {
					System.out.println("Broj već postoji. Preskačem.");
				}else {
					root = addNode(root, value);
					System.out.println("Dodano.");
				}
				
				
			} catch (NumberFormatException e) {
				System.out.println("'" + input + "' nije cijeli broj.");
			}
		}

		System.out.print("Ispis od najmanjeg: ");
		printIncreasing(root);
		System.out.println();

		System.out.print("Ispis od najvećeg: ");
		printDecreasing(root);
		System.out.println();

		sc.close();
	}

	/**
	 * Method that print numbers in increasing order using inorder binary tree
	 * traversal.
	 * 
	 * @param root Root of the binary search tree.
	 */
	private static void printIncreasing(TreeNode root) {
		if (root == null)
			return;
		printIncreasing(root.left);
		System.out.print(root.value + " ");
		printIncreasing(root.right);
	}

	/**
	 * Method that print numbers in decreasing order using reversed inorder binary
	 * tree traversal.
	 * 
	 * @param root Root of the binary search tree.
	 */
	private static void printDecreasing(TreeNode root) {
		if (root == null) {
			return;
		}
		printDecreasing(root.right);
		System.out.print(root.value + " ");
		printDecreasing(root.left);
	}

	/**
	 * Class that represents one node of binary search tree.
	 * 
	 * @author david
	 *
	 */
	public static class TreeNode {
		/**
		 * Field that represents value stored in one node of binary search tree.
		 */
		int value;

		/**
		 * Fields pointing to left and right subtree of binary search tree.
		 */
		TreeNode left, right;

		/**
		 * Constructor to initialize value of field value.
		 * 
		 * @param value Value that is stored in one node of binary search tree.
		 */
		public TreeNode(int value) {
			this.value = value;
		}
	}

	/**
	 * Method that adds new node to binary search tree.
	 * If value already exists in binary search tree, it will be ignored.
	 * 
	 * @param root  Root of the binary search tree.
	 * @param value Value to be stored in binary search tree.
	 * @return Root of the binary search tree.
	 */
	public static TreeNode addNode(TreeNode root, int value) {
		if (root == null) {
			TreeNode newNode = new TreeNode(value);
			return newNode;
		} else if (value < root.value) {
			root.left = addNode(root.left, value);
		} else if (value > root.value){
			root.right = addNode(root.right, value);
		}
		return root;
	}
	
	/**
	 * Method to check if given value is presented in binary search tree.
	 * 
	 * @param root  Root of the binary search tree.
	 * @param value The value to check if is presented in binary search tree.
	 * @return True if binary search contains given value.
	 */
	public static boolean containsValue(TreeNode root, int value) {
		if (root == null) {
			return false;
		}

		if (value == root.value) {
			return true;
		} else if (value < root.value) {
			return containsValue(root.left, value);
		} else {
			return containsValue(root.right, value);
		}
	}

	/**
	 * Method to calculate size of binary search tree. Size of binary search tree is
	 * number of nodes in it.
	 * 
	 * @param root Root of the binary search tree.
	 * @return Size of the binary search tree.
	 */
	public static int treeSize(TreeNode root) {
		if (root == null) {
			return 0;
		}
		int size = treeSize(root.left) + treeSize(root.right);

		return size + 1;
	}
}
