package com.demo.service.impl;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;





public class MapServiceTest {





    @Test
    public void test(){
        //Map 是一个接口 它不可能被实例化  hashMap是实现Map接口的一个类 所以HashMap可以被实例化
        //Map一定是有键值对的 key 和value 不可能Map<String>
        //相同key会被覆盖
        //map顺序是随机的(无须)
        Map<String,Integer> a = new HashMap<>(9);
        a.put("zs1",new Integer(120));
        a.put("zs2",new Integer(120));
        a.put("zs3",new Integer(120));
        a.put("zs4",new Integer(120));
        a.put("zs5",new Integer(120));







        Map<String,String> a2 = new HashMap<>(9);








        List<Map<String,Integer>> as = new ArrayList<>();


        List<String> b = new ArrayList<>();


        b.add("a");
        b.add("b");

        for (int i =0 ;i<b.size();i++) {
            System.out.println(b.get(i));
        }








        String[] aa =  {"aaa","bbbb","cccc"};

        for (int i=0;i<aa.length;i++){
            System.out.println(aa[i]);
        }

        for(String i : aa){
            System.out.println(i);
        }

    }

    @Test
    public void testMap(){
        Map<String,Integer> a = new HashMap<>(9);
        a.put("zs1",new Integer(120));
        a.put("zs2",new Integer(100));
        a.put("zs3",new Integer(80));
        a.put("zs4",new Integer(88));
        a.put("zs5",new Integer(10));

        //获取map的key值
        //keySet性能不行
        for (String key : a.keySet()){
                System.out.println(key+"***********"+a.get(key));
        }

        //获取map的value值
        for (Integer value : a.values()){
            System.out.println(value);
        }


        for (Map.Entry<String,Integer>  entry : a.entrySet()){
            entry.getKey();
            entry.getValue();
        }


        //迭代器
        Iterator<Map.Entry<String, Integer>> iterator = a.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, Integer> next = iterator.next();
            next.getValue();
            next.getKey();
        }

        //values,entrySet,iteratorz这3个性能差不多


;
    }



    @Test
    public void  testListMap(){
        Map<String, Integer> map1 = new HashMap<>();
        map1.put("yuwen",120);
        map1.put("shuxue",100);
        map1.put("english",1);

        Map<String, Integer> map2 = new HashMap<>();
        map2.put("yuwen",110);
        map2.put("shuxue",10);
        map2.put("english",1);

        //listMap和listMap1是一样的效果
        Map<String,Map<String, Integer>> listMap = new HashMap<>();
        Map<String,Map<String, Integer>> listMap1 = new HashMap<>();
        listMap.put("zhangsan1",map1);
        listMap.put("zhangsan2",map2);


        //List集合是不存在key这种的 key都是从0下标开始的
        List<Map<String,Integer>> objects = new ArrayList<>();
        objects.add(map1);
        objects.add(map2);

        objects.get(0);

        System.out.println(listMap);
        System.out.println(objects);
    }



}