package com.atguigu.gmall.item.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Test
 *
 * @Author: wd
 * @CreateTime: 2020-03-08
 * @Description:
 */
public class Test {
    public static void main(String[] args) {
        String jsonStr = "{\"|274|276|279\":\"114\",\"|274|276|280\":\"115\",\"|274|278|282\":\"117\",\"|274|276|281\":\"116\"}";
        File file = new File("D:/ideaworkspace/gmall/gmall-item-web/src/main/resources/static/spu/spu_72.json");
        try {
            FileOutputStream os = new FileOutputStream(file);
            os.write(jsonStr.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}