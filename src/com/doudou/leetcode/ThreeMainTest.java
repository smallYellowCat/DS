package com.doudou.leetcode;


import java.util.*;
import java.util.stream.Collectors;

public class ThreeMainTest {
    /**
     *入口函数，自动生成
     * @author 豆豆
     * @date 2019/7/31 21:21a
    */
    public static void main(String[] args){
        //addBinary("1010", "1011");
        simplifyPath("/../");
    }

    /**
     * 基础加1
     * @param digits
     * @return
     */
    public int[] plusOne(int[] digits) {
        for (int i = digits.length - 1; i >= 0; i--){
            digits[i]++;
            digits[i] = digits[i] % 10;
            //如果没有进位了直接返回
            if (digits[i] != 0) return digits;
        }
        //如果全部进位了那么这个数一定是高位为1，其余位为0，只需扩展下数组大小，高位置一
        digits = new int[digits.length + 1];
        digits[0] = 1;
        return digits;

    }

    /**
     * 二进制求和
     * @param a
     * @param b
     * @return
     */
    public static String addBinary(String a, String b) {
        if (a.equals("0")) return b;
        if (b.equals("0")) return a;
        int la = a.length(), lb= b.length();
        //从后向前遍历，记录进位
        StringBuilder sb = new StringBuilder();
        int up = la >= lb ? la : lb;
        int low = la < lb ? la : lb;
        int sum = 0, pop = 0;
        for (int i = 0; i < up; i++){
            if (i < low)
                sum = a.charAt(la-i-1) - 48 + b.charAt(lb-i-1) - 48 + pop;
            else if (a.length() > b.length()){
                sum = pop + a.charAt(la-i-1) - 48;
            }else {
                sum = pop + b.charAt(lb-i-1) - 48;
            }
            sb.append(sum % 2);
            pop = sum/2;
        }
        sb.reverse();
        return pop == 1 ? "1" + sb.toString() : sb.toString();
    }


    /**
     * 牛顿法求x的平方根
     * @param x
     * @return
     */
    public int mySqrt(int x) {
        if (x == 0) return 0;
        double x1, x2;
        x1 = x;
        x2 = (x1/2) + (x/(x1*2));
        //误差范围
        while (Math.abs(x2-x1) > 0.00001){
            x1 = x2;
            x2 = (x1/2) + (x/(x1*2));
        }
        return (int) x2;
    }


    /**
     * 爬楼梯，每次爬一个或两个台阶
     * @param n
     * @return
     */
    public int climbStairs(int n) {
        if (n < 2) return n;
        int dp[] = new int[n];
        dp[0] = 1; dp[1] = 2;
        for (int i = 2; i < n; i++){
           dp[i] = dp[i-1]+dp[i-2];
        }
       return dp[n-1];

    }


    /**
     * 简化路径， 路径压栈，碰到.不压栈，向后走一位，碰到..,不压栈，弹出两个，最后遍历栈，字符串反转，截取0到length-1
     * @param path
     * @return
     */
    public static String simplifyPath(String path) {
        Stack<String> stack = new Stack<>();
        String[] str = path.split("/");
        for (int i = 0; i < str.length; i++){
            if (str[i].equals(".")) continue;
            if (str[i].equals("..") && stack.size() > 0){
                stack.pop();
            }else if (str[i].equals("..") && stack.isEmpty()){
                continue;
            }else if (!str[i].isEmpty()){
                stack.push(str[i]);
            }
        }

        StringBuilder sb = new StringBuilder();
        if (stack.isEmpty()){
            sb.append("/");
        }
        for (String s : stack){
            sb.append("/").append(s);
        }

        //sb.reverse();
        return sb.toString();
    }

    /**
     * 1~n 之间所有的k个数的组合
     * @param n
     * @param k
     * @return
     */
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> rs = new ArrayList<>();
        if (n < k) return rs;
        backTrack(n, 1, k, new ArrayList<>(), rs);
        return rs;

    }

    static void backTrack(int n, int start, int k, List<Integer> list, List<List<Integer>> rs){
        if (list.size() == k) {

            rs.add(new ArrayList<>(list));
            return;
        }
        for (int i = start; i <= n; i++){
            list.add(i);
            backTrack(n, i+1, k, list, rs);
            list.remove(list.size() - 1);
        }
    }

    /**
     * 求子集
     * @param nums
     * @return
     */
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> rs = new ArrayList<>();
        backtrack(0, nums, rs, new ArrayList<>());
        return rs;
    }

    void backtrack(int i, int[] nums, List<List<Integer>> rs, List<Integer> list){
        rs.add(new ArrayList<>(list));
        for (int j = i; j < nums.length; j++){
            list.add(nums[j]);
            backtrack(j + 1, nums, rs, list);
            list.remove(list.size() - 1);
        }
    }


    /**
     * 单词搜索
     * @param board
     * @param word
     * @return
     */
    public boolean exist(char[][] board, String word) {
        //得到行列长度
        int row =  board.length, column = board[0].length;

        boolean[][] visited = new boolean[row][column];

        for (int i = 0; i < row; i++){
            for (int j = 0; j < column; j++){
                //成功条件
                if (word.charAt(0) == board[i][j] && backtrack(i, j, 0, word, visited, board))
                    return true;
            }
        }

        return false;

    }

    private boolean backtrack(int i, int j, int idx, String word, boolean[][] visited, char[][] board){
        if (idx == word.length()) return true;
        if (i >= board.length || i < 0 || j >= board[0].length || j < 0 || board[i][j] != word.charAt(idx) || visited[i][j])
            return false;
        visited[i][j] = true;

        if (backtrack(i+1, j, idx+1, word, visited, board) || backtrack(i-1, j, idx+1, word, visited, board)
                || backtrack(i, j+1, idx+1, word, visited, board) || backtrack(i, j-1, idx+1, word, visited, board))
            return true;
        visited[i][j] = false;
        return false;
    }



}
