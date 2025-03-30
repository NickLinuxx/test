package ce326.hw1;

import java.util.Scanner;

public class HW1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HBTree tree = new HBTree();
        String command;
        
        while (scanner.hasNext()) {
            command = scanner.next();
            
            if (command.equals("-i")) {
                // Insert command
                if (scanner.hasNextInt()) {
                    int key = scanner.nextInt();
                    boolean success = tree.insert(key);
                    System.out.println();
                    System.out.println(key + (success ? " I" : " NI"));
                } else {
                    System.out.println("Invalid Command!");
                }
            } else if (command.equals("-d")) {
                // Delete command
                if (scanner.hasNextInt()) {
                    int key = scanner.nextInt();
                    boolean success = tree.delete(key);
                    System.out.println();
                    System.out.println(key + (success ? " D" : " ND"));
                } else {
                    System.out.println("Invalid Command!");
                }
            } else if (command.equals("-f")) {
                // Find command
                if (scanner.hasNextInt()) {
                    int key = scanner.nextInt();
                    HBNode node = tree.find(key);
                    System.out.println();
                    System.out.println(key + (node != null ? " F" : " NF"));
                } else {
                    System.out.println("Invalid Command!");
                }
            } else if (command.equals("-p")) {
                // Print command
                System.out.println();
                System.out.println("size: " + tree.getSize() + ", max_height: " + 
                                  tree.getMaxHeight() + ", min_height: " + tree.getMinHeight());
                
                String preOrderTraversal = tree.printPreOrder();
                System.out.println(preOrderTraversal.trim());
            } else if (command.equals("-q")) {
                // Quit command
                break;
            } else {
                System.out.println("Invalid Command!");
            }
        }
        
        scanner.close();
    }
}