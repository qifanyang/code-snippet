package leetcode;

import java.util.Stack;

/**
 * Created by yangqifan on 2020/8/29.
 */
public class LC_232_Stack_List {

    //用栈实现队列, 保持先进入栈的元素在栈顶即可
    //栈FILO, 队列FIFO, 一般使用双栈实现队列, 双队列实现栈

    Stack<Integer> s1 = new Stack<>();
    Stack<Integer> s2 = new Stack<>();
    //中间状态即是 读状态还是写状态
    private boolean middle = false;

    //为了变判断栈是否有元素不好理解, 使用s1作为最终栈

    /**
     * Push element x to the back of queue.
     */
    public void push(int x) {
        while (!s1.isEmpty()) {
            s2.push(s1.pop());
        }
        s2.push(x);
        //移动到s1的操作可以优化
        //如果接下来是连续的push, 没有pop/peek, 可以保持中间状态
        //可以理解为push merge, 在大量连续push操作时性能提升
        //

        middle = true;

    }

    /**
     * Removes the element from in front of queue and returns that element.
     */
    public int pop() {
        if(middle){
            middle = false;
            while (!s2.isEmpty()) {
                s1.push(s2.pop());
            }
        }
        return s1.pop();
    }

    /**
     * Get the front element.
     */
    public int peek() {
        if(middle){
            middle = false;
            while (!s2.isEmpty()) {
                s1.push(s2.pop());
            }
        }
        return s1.peek();
    }

    /**
     * Returns whether the queue is empty.
     */
    public boolean empty() {
        return s1.empty() && s2.empty();
    }
}
