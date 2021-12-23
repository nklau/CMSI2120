import java.util.LinkedList;

public class FourSum {
    
    /**
     * Checks if any four elements in the array add up to a given sum.
     * Must use a hash table to solve the problem.
     * Time complexity should be no worse than O(n^3), though this hinted solution is better.
     * 
     * @param arr The array of integers to check. Each number is a unique positive integer.
     * @param sum The target sum
     * 
     * @return True if there exists any 4 elements that sum up to sum
     */
    public static boolean fourSum(int[] arr, int sum) {
        LinkedList<Integer> nums = getLinkedList(arr, sum - 3);

        if (nums.size() < 4) {
            return false;
        }

        HashTable table = getTable(nums, sum - 2);
        if (table.size() < 2) {
            return false;
        }
        return table.hasSum(sum);
    }

    /**
     * Turns an array into a LinkedList, excluding any number greater than a given integer
     * 
     * @param arr the array to turn into a LinkedList
     * @param greatest the max integer
     * 
     * @return a new LinkedList that contains only elements smaller than greatest
     */
    private static LinkedList<Integer> getLinkedList(int[] arr, int greatest) {
        LinkedList<Integer> list = new LinkedList<>(); //same explanation as in HashTable.java

        for (int i : arr) {
            if (i <= greatest) {
                list.add(i);
            }
        }
        return list;
    }

    /**
     * Creates new HashTable that contains all possible combinations of two numbers from the incoming LinkedList,
     * excluding all combinations that sum to an integer bigger than the incoming max integer
     * 
     * @param arr the LinkedList containing the ints to add to a HashTable
     * @param greatest the max integer
     * 
     * @return a new HashTable that contains only sums smaller than greatest
     */
    private static HashTable getTable(LinkedList<Integer> arr, int greatest) {
        HashTable table = new HashTable(arr.size() * (arr.size() - 1));

        while (arr.size() > 0) {
            int num = arr.remove(0);

            for (int i = 0; i < arr.size(); i++) {
                if (num + arr.get(i) <= greatest) {
                    table.putUnique(num, arr.get(i));
                }
            }
        }
        return table;
    }