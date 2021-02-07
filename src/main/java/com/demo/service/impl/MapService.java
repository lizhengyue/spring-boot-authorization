package com.demo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapService {



    public void test(){
        Map<String,String> a = new HashMap<>();

        List<String> b = new ArrayList<>();

        HashMap<String,String> c = new HashMap<>();


        String[] aa =  {"aaa","bbbb","cccc"};

        for (int i=0;i<aa.length;i++){
            System.out.println(aa[i]);
        }
    }
}
