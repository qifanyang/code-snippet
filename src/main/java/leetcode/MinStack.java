package leetcode;

import java.util.Stack;

/**
 * Created by yangqifan on 2020/8/29.
 */
public class MinStack {
    private Stack<Integer> dataStack;
    private Stack<Integer> minStack;

    public MinStack(){
        dataStack = new Stack<>();
        //存下了单调递减的有序数字, 相当定义了N个变量存储最小值
        //当栈具有单调性时, 两个栈大小一致, 一般情况N小于数据栈元素数量, 空间要小

        //简化模型, 定义一个变量存储最小值
        //但是最小时移除后, 新的最小值怎么办, 所以新的栈就是用于存储新的最小值, 而且是有序的
        //保证getMin获取的最小值
        minStack = new Stack<>();
    }

    public void push(int x){
        dataStack.push(x);
        if(minStack.isEmpty() || x <= minStack.peek()){
            minStack.push(x);
        }

    }

    public void pop(){
        Integer x = dataStack.pop();
        if(x == minStack.peek()){
            minStack.pop();
        }
    }

    public int top(){
        return dataStack.peek();
    }

    public int getMin(){
        return minStack.peek();
    }

    public static void main(String[] args) {
        MinStack minStack = new MinStack();
        minStack.push(-1);
        minStack.push(-2);
        minStack.push(-3);
        System.out.println(minStack.getMin());
        minStack.pop();
        System.out.println(minStack.getMin());

    }

}
