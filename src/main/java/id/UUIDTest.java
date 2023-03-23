package id;

import java.util.UUID;

/**
 * 不易于存储：UUID太长，16字节128位，通常以36长度的字符串表示，很多场景不适用。信息不安全：
 * 基于MAC地址生成UUID的算法可能会造成MAC地址泄露，暴露使用者的位置。
 * 对MySQL索引不利：如果作为数据库主键，在InnoDB引擎下，UUID的无序性可能会引起数据位置频繁变动，严重影响性能，可以查阅 Mysql 索引原理 B+树的知识。#
 * ------
 * 著作权归@pdai所有
 * 原文链接：https://pdai.tech/md/arch/arch-z-id.html
 */
public class UUIDTest {
    public static void main(String[] args) {
        System.out.println(UUID.fromString("a-b-c-d-e"));
        System.out.println(UUID.randomUUID());
        System.out.println(UUID.randomUUID().toString().replace("-", "").length());
    }
}
