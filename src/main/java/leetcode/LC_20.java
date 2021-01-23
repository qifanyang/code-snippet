package leetcode;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;

/**
 * Created by yangqifan on 2020/8/28.
 */
public class LC_20 {

    public static void main(String[] args) {
        System.out.println(isValid("{}[()]"));
    }

    public static boolean isValid(String s) {

        Deque<Character> deque = new LinkedList<>();
        for (char c : s.toCharArray()) {
            if (!deque.isEmpty()) {// null 不能和char比较
                if (c == ']' && deque.poll() == '[') {
                    continue;
                }
                if (c == '}' && deque.poll() == '{') {
                    continue;
                }
                if (c == ')' && deque.poll() == '(') {
                    continue;
                }
            }
            //右括号永远不会放到栈中
            if (c == ']' || c == '}' || c == ')') return false;
            deque.push(c);
        }

        return deque.isEmpty();
    }
}
