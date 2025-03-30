package ce326.hw1;

import java.util.Scanner;

public class HW1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HBTree tree = new HBTree();
        
        while (scanner.hasNext()) {
            String command = scanner.next();

            if ("-q".equals(command)) {
                break;
            }
            else if ("-p".equals(command)) {
                int[] treeInfo = tree.getTreeInfo();
                System.out.printf("size: %d, max_height: %d, min_height: %d%n", treeInfo[0], treeInfo[1], treeInfo[2]);
                tree.preOrderTraversal(tree.getRoot());
                System.out.println();
                continue;
            }

            if ("-i".equals(command)) {
                if (scanner.hasNextInt()) {
                    int x = scanner.nextInt();
                    System.out.println(x + (tree.insert(x) ? " I" : " NI"));
                } else {
                    // If next input is not an integer, print "NI"
                    String invalidInput = scanner.next();
                    System.out.println(invalidInput + " NI");
                }
                continue;
            }
            
            int x = Integer.MIN_VALUE;
            if (scanner.hasNextInt()) {
                x = scanner.nextInt();
            }
            
            switch (command) {
                case "-d" -> System.out.println(x + (tree.delete(x) ? " D" : " ND"));
                case "-f" -> System.out.println(x + (tree.find(x) ? " F" : " NF"));
                default -> System.out.println("Invalid Command!");
            }
        }
        
        scanner.close();
    }
}