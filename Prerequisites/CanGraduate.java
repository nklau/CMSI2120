public class CanGraduate {
    /**
     * There are a total of num courses you have to take, labeled from 0 to num-1.
     * You are given an array prerequisites where prerequisites[i] = [a_i, b_i] indicates
     * that you must take course b_i first if you want to take course a_i
     * 
     * For example, the pair [1, 3] means to take course 1 you must take course 3
     * 
     * Return true if it is possible to finish all courses. Otherwise return false
     * 
     * You do not need to efficiently check. Just find the correct answer.
     * 
     * @param num the number of courses
     * @param prerequisites array of prerequisite pairs
     * 
     */
    public static boolean canGraduate(int num, int[][] prerequisites) {
        Graph classes = new Graph(num);

        for (int[] prereq : prerequisites) {
            classes.addEdge(prereq[0], prereq[1]);
        }
        return !classes.hasCycle();
    }
}