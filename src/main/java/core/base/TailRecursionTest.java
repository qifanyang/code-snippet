package core.base;

import leetcode.ListNode;

/**
 * 尾递归
 * https://zh.wikipedia.org/wiki/%E5%B0%BE%E8%B0%83%E7%94%A8
 * http://www.cnblogs.com/zhuyf87/archive/2013/01/30/2883357.html
 * 递归调用可能会造成栈溢出,尾递归可以解决这个问题
 * Created by Administrator on 2017/3/6.
 */
public class TailRecursionTest {

    //每次方法调用需要保存当前栈,方法返回后再计算
    public static int GetLengthRecursively(ListNode head)
    {
        if (head == null) return 0;
        return GetLengthRecursively(head.next) + 1;
    }

    //提供了一个累加值,每次方法调用不用保存当前栈,直接将中间计算结果传入到下一次方法调用
    //相当于直接调用方法很多次
    //but java not surppored tail recursion optimization
    public static int GetLengthTailRecursively(ListNode head, int acc)
    {
        if (head == null) return acc;
        return GetLengthTailRecursively(head.next, acc + 1);
    }

    public static int FibonacciRecursively(int n)
    {
        if (n < 2) return n;
        return FibonacciRecursively(n - 1) + FibonacciRecursively(n - 2);
    }

    public static int FibonacciTailRecursively(int n, int acc1, int acc2)
    {
        if (n == 0) return acc1;
        return FibonacciTailRecursively(n - 1, acc2, acc1 + acc2);
    }

    public static void main(String[] args) {
//        System.out.println(FibonacciRecursively(Integer.MAX_VALUE));
        System.out.println(FibonacciTailRecursively(Integer.MAX_VALUE,0 , 1));
    }
}
