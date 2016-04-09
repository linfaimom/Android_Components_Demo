package com.example.marcus.fragmenttest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by marcus on 16/4/6.
 */
public class BookContent {
    //定义一个内部类
    public static class Book {
        public Integer id;
        public String title;
        public String desc;
        public Book(Integer id, String title, String desc){
            this.id = id;
            this.title = title;
            this.desc = desc;
        }

        @Override
        public String toString() {
            return title;
        }
    }

    public static List<Book> ITEMS = new ArrayList<>();

    public static Map<Integer, Book> ITEM_MAP = new HashMap<>();

    static {
        //使用静态初始化代码,将book对象添加到list,map集合中
        addItem(new Book(1,"疯狂Android讲义","还行吧,挺不错的~"));
        addItem(new Book(2,"疯狂Java讲义","还行吧,挺不错的~"));
        addItem(new Book(3,"疯狂XML讲义","还行吧,挺不错的~"));
    }

    private static void addItem(Book book){
        ITEMS.add(book);
        ITEM_MAP.put(book.id,book);
    }
}
