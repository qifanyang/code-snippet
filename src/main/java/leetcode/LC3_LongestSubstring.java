package leetcode;

import java.util.HashSet;

/**
 * @author yangqf
 * @version 1.0 2017/2/17
 */
public class LC3_LongestSubstring{

    static HashSet<Character> judeRepeat = new HashSet<>();
    public int lengthOfLongestSubstring(String s) {
        judeRepeat.clear();
        int max = 0;
//        char[] chars = s.toCharArray();
        int current = 0;
        for(int i = 0; i < s.length();++i){
            if(judeRepeat.contains(s.charAt(i))){
                int temp = i - current;
                if(temp > max){
                    max = temp;
                }
                i = current;
                current++;
                judeRepeat.clear();
            }else {
                judeRepeat.add(s.charAt(i));
            }
        }
        return judeRepeat.size() > max ? judeRepeat.size() : max;
    }

    public static void main(String[] args){
        LC3_LongestSubstring test = new LC3_LongestSubstring();
        assertTrue(test.lengthOfLongestSubstring("pwwkew"), 3);
        assertTrue(test.lengthOfLongestSubstring("abcabcbb"), 3);
        assertTrue(test.lengthOfLongestSubstring("bbbbb"), 1);
        assertTrue(test.lengthOfLongestSubstring(""), 0);
        assertTrue(test.lengthOfLongestSubstring("dvdf"), 3);
    }

    static void assertTrue(int a , int except){
        System.out.println(a);
        if(a != except){
            throw new IllegalStateException("except value is "+except+ " but it is " + a);
        }
    }
}
