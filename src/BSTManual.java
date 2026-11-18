/*
 * Name: Rachit Jain
 * PID: A18942551
 * Sources used: None
 *
 * Hand-traced answers for the PA6 BST worksheet.
 */

import java.util.*;

public class BSTManual {

/**
 * Hard-coded answers for the BST insertion, deletion, and traversal questions.
 * @author Rachit Jain
 * @since  2026-11-17
 */

// No style for this file.

public static ArrayList<String>  insertElements() {

	ArrayList<String> answer_pr1 = new ArrayList<>(11);

	answer_pr1.add("54");
	answer_pr1.add("18, 67");
	answer_pr1.add("12, 41, X, 72");
	answer_pr1.add("X, X, 29, 43, X, X, X, 95");
	answer_pr1.add("X, X, X, X, 24, X, X, X, X, X, X, X, X, X, X, X");
	return answer_pr1;

}

public static ArrayList<ArrayList<String>>  deleteElements() {

	ArrayList<ArrayList<String>> answer_pr2 = new ArrayList<>(5);

	// After deleting 22
	ArrayList<String> afterDeletion1 = new ArrayList<>();
	afterDeletion1.add("56");
	afterDeletion1.add("47, 60");
	afterDeletion1.add("20, X, 59, 72");
	afterDeletion1.add("X, 21, X, X, 57, X, 63, 94");
	answer_pr2.add(afterDeletion1);

	// After deleting 59
	ArrayList<String> afterDeletion2 = new ArrayList<>();
	afterDeletion2.add("56");
	afterDeletion2.add("47, 60");
	afterDeletion2.add("20, X, 57, 72");
	afterDeletion2.add("X, 21, X, X, X, X, 63, 94");
	answer_pr2.add(afterDeletion2);

	// After deleting 56
	ArrayList<String> afterDeletion3 = new ArrayList<>();
	afterDeletion3.add("57");
	afterDeletion3.add("47, 60");
	afterDeletion3.add("20, X, X, 72");
	afterDeletion3.add("X, 21, X, X, X, X, 63, 94");
	answer_pr2.add(afterDeletion3);

	// After deleting 72
	ArrayList<String> afterDeletion4 = new ArrayList<>();
	afterDeletion4.add("57");
	afterDeletion4.add("47, 60");
	afterDeletion4.add("20, X, X, 94");
	afterDeletion4.add("X, 21, X, X, X, X, 63, X");
	answer_pr2.add(afterDeletion4);

	// After deleting 63
	ArrayList<String> afterDeletion5 = new ArrayList<>();
	afterDeletion5.add("57");
	afterDeletion5.add("47, 60");
	afterDeletion5.add("20, X, X, 94");
	afterDeletion5.add("X, 21, X, X, X, X, X, X");
	answer_pr2.add(afterDeletion5);

	return answer_pr2;

}

public static ArrayList<String>  traversals() {

	ArrayList<String> answer_pr3 = new ArrayList<>(11);

	answer_pr3.add("4, 19, 22, 28, 39, 56, 64, 68, 93, 95, 96, 97, 98");
	answer_pr3.add("39, 19, 4, 22, 28, 93, 64, 56, 68, 96, 95, 98, 97");
	answer_pr3.add("4, 28, 22, 19, 56, 68, 64, 95, 97, 98, 96, 93, 39");
	return answer_pr3;

}


}
