package collection;

/**
 * Coding Assignment 3, CSC 330, Spring 2019
 * (Note there is nothing in this file for you to change).
 * @author Nathaniel Kell
 */
public class TestHeightBalTrees {
    
   private static final String[] INORDER = 
    {
        "80, 90, 100",
        "20, 30, 35, 40, 45, 50, 60, 70, 75, 80, 90, 100",
        "20, 30, 35, 40, 45, 50, 60, 70, 75, 80, 85, 90, 100, 110, 120, 130, 140, 150",
        "1, 2, 3, 4, 5, 6, 7, 8, 9, 20, 30, 35, 40, 45, 50, 60, 70, 75, 80, 85, 90, 100, 110, 120, 130, 140, 150, 200, 210, 220, 230, 240, 250",      
        "2, 5, 6, 8, 9, 20, 30, 35, 70, 85, 100, 120, 130, 150, 220, 230, 240",    
    };
    
    private static final String[] ROT_PREORDER = 
    {
        "90, 80, 100",
        "75, 45, 35, 30, 20, 40, 60, 50, 70, 90, 80, 100",
        "75, 45, 35, 30, 20, 40, 60, 50, 70, 110, 90, 80, 85, 100, 130, 120, 140, 150",
        "110, 45, 20, 6, 4, 2, 1, 3, 5, 8, 7, 9, 35, 30, 40, 75, 60, 50, 70, 90, 80, 85, 100, 150, 130, 120, 140, 210, 200, 230, 220, 240, 250",      
        "70, 6, 2, 5, 20, 8, 9, 35, 30, 120, 85, 100, 220, 150, 130, 240, 230" 
    };
    
        
    private static final String[] SPLAY_PREORDER = 
    {
        "80, 90, 100",
        "20, 30, 35, 40, 45, 50, 60, 70, 75, 80, 90, 100",
        "85, 30, 20, 50, 40, 35, 45, 80, 70, 60, 75, 140, 120, 110, 90, 100, 130, 150",
        "1, 2, 3, 4, 5, 6, 7, 8, 9, 250, 230, 210, 85, 20, 30, 50, 40, 35, 45, 80, 70, 60, 75, 200, 150, 140, 120, 110, 90, 100, 130, 220, 240",      
        "120, 9, 5, 2, 8, 6, 70, 30, 20, 35, 85, 100, 150, 130, 230, 220, 240",    
    };
    
    public static int doTests(HeightBalancedTree<Integer> tree, String[] inorderStrings, String[] preOrderStrings){
        int[] totalCorrect  = {0}; 
        int testNumber = 1; 
       
        /* Test 1 */
        testOne(tree);
        printTestMessage(tree, testNumber, totalCorrect, inorderStrings, preOrderStrings);
        testNumber++;
    
        /* Test 2 */
        testTwo(tree);
        printTestMessage(tree, testNumber, totalCorrect, inorderStrings, preOrderStrings);
        testNumber++;
        
        /* Test 3 */
        testThree(tree);
        printTestMessage(tree, testNumber, totalCorrect, inorderStrings, preOrderStrings);
        testNumber++;
       
        /* Test 4 */
        testFour(tree);
        printTestMessage(tree, testNumber, totalCorrect, inorderStrings, preOrderStrings);
        testNumber++;
 
        /* Test 5 */
        testFive(tree);
        printTestMessage(tree, testNumber, totalCorrect, inorderStrings, preOrderStrings);

        return totalCorrect[0];
    }
    
    
    /* Modify the tree for test 1 */
    public static void testOne(HeightBalancedTree<Integer> tree){
        tree.insert(100);
        tree.insert(90);
        tree.insert(80);
    }
    
    /* Modify the tree for test 2 */
    public static void testTwo(HeightBalancedTree<Integer> tree){
        tree.insert(70);
        tree.insert(75);
        tree.insert(60);
        tree.insert(50);
        tree.insert(45);
        tree.insert(40);
        tree.insert(30);
        tree.insert(35);
        tree.insert(20);
    }
    
    /* Modify the tree for test 3 */
    public static void testThree(HeightBalancedTree<Integer> tree){
        tree.insert(110);
        tree.insert(120);
        tree.insert(130);
        tree.insert(140);
        tree.insert(150);
        tree.insert(85);
    }
   
    /* Modify the tree for test 4 */
    public static void testFour(HeightBalancedTree<Integer> tree){
        tree.insert(200);
        tree.insert(210);
        tree.insert(220);
        tree.insert(230);
        tree.insert(240);
        tree.insert(250);
        tree.insert(9);
        tree.insert(8);
        tree.insert(7);
        tree.insert(6);
        tree.insert(5);
        tree.insert(4);
        tree.insert(3);
        tree.insert(2);
        tree.insert(1);
    }
    
    /* Modify the tree for test 5 */
    public static void testFive(HeightBalancedTree<Integer> tree){
        tree.delete(80);
        tree.delete(110);
        tree.delete(60);
        tree.delete(40);
        tree.delete(90);
        tree.delete(140);
        tree.delete(75);
        tree.delete(45);
        tree.delete(200);
        tree.delete(210);
        tree.delete(250);
        tree.delete(7);
        tree.delete(4);
        tree.delete(3);
        tree.delete(1);
        tree.delete(50);
    }
    
    /* Print test results of a test */
    public static void printTestMessage(HeightBalancedTree<Integer> tree, 
            int tNum, int[] totalCorrect, String[] inOrderTests, String[] preOrderTests){
        System.out.printf("Test Number: %d\n", tNum);
        System.out.printf("Target in-order sequence: %s\n"
                + "Produced in-order sequence: %s\n"
                + "Target pre-order sequence: %s\n"
                + "Produced pre-order sequence: %s\n",
                inOrderTests[tNum-1], tree.inOrderString(), preOrderTests[tNum-1], tree.toString());
        if(inOrderTests[tNum-1].equals(tree.inOrderString()) && preOrderTests[tNum-1].equals(tree.toString())){
            System.out.println("*** CORRECT ***\n");
            totalCorrect[0]++; 
        }
        else
              System.out.println("*** INCORRECT ***\n");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        /* Rotation tests.*/
        System.out.println("\n**** ROTATION TESTS *****");
        RedBlackTree<Integer> tree = new RedBlackTree(); 
        int totalRotCorrect = doTests(tree, INORDER, ROT_PREORDER);
        System.out.printf("Total Rotation Tests Correct: %d\n", totalRotCorrect);
        /*
        /* SplayTree tests 
        System.out.println("\n-\n\n**** SPLAY TREE TESTS *****");
        SplayTree<Integer> sTree = new SplayTree(); 
        int totalSplayCorrect = doTests(sTree, INORDER,  SPLAY_PREORDER);
        System.out.printf("Total Splay Tree TayTrests Correct: %d\n", totalSplayCorrect);
        
       /* Total correct.
       System.out.printf("\nTotal Overall Correct: %d\n", totalRotCorrect + totalSplayCorrect); 
       */ 
    }
    
}
