package com.elias.michalczuk.dynamodbspring;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

//@SpringBootTest
class DynamodbSpringApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void matchStringTest() {
		System.out.println(matchingStrings(List.of("def", "de", "fgh"), List.of("de", "imn", "fgh")));
	}

	public static List<Integer> matchingStrings(List<String> strings, List<String> queries) {
		List<Integer> res = new ArrayList<>();

		queries.forEach(q -> res.add((int) strings.stream().filter(str -> str.equals(q)).count()));
		return res;
	}

	@Test
	public static int[] calPoints() {
		var a = new ArrayList<Integer>();
//		a.stream().mapToInt(i -> i.intValue()).toarr;
		int[] myint = new int[]{};
		var aa = new ArrayList<Integer>();
		return aa.stream().mapToInt(i -> i.intValue()).toArray();
	}
	
	@Test
	public void aaa() throws IOException {
		int ch;
		var list = new ArrayList<Integer>();
		while ((ch = System.in.read ()) != -1) {
			if (list.contains((Integer) ch)) {
				System.out.write(ch);
			} else {
				list.add((Integer) ch);
			}
		}

	}

	public class Solution {

		public void main(String[] args) throws IOException  {
			int ch;
			var list = new ArrayList<Integer>();
			while ((ch = System.in.read ()) != -1) {
				if (list.contains((Integer) ch)) {
					System.out.write(ch);
				} else {
					list.add((Integer) ch);
				}
			}
		}
	}

	@Test
	public void checkOnlyChild() {


		var childleft = new BinaryTreeNode("childleft", null, null);
		var rightThree = new BinaryTreeNode("rightThree", null, null);
		var rightTwo = new BinaryTreeNode("rightTwo", null, rightThree);
		var rightOne = new BinaryTreeNode("rightOne", null, rightTwo);
		var leftOne = new BinaryTreeNode("leftOne", childleft, null);
		var root = new BinaryTreeNode("root", leftOne, rightOne);

		var onlyChildren =  new ArrayList<BinaryTreeNode>();
		transverse(root, onlyChildren);
		System.out.println("only chilndrem: ");
		System.out.println(onlyChildren);
	}

	public void transverse(BinaryTreeNode node, ArrayList<BinaryTreeNode> onlychildren) {
		if (node.left != null) {
			transverse(node.left, onlychildren);
		}
		if ((node.left != null && node.right == null) || (node.right != null && node.left == null)) {
			onlychildren.add(node);
		}
		if (node.right != null) {
			transverse(node.right, onlychildren);
		}
	}


}
