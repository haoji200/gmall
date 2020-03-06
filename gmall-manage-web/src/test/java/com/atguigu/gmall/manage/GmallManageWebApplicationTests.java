package com.atguigu.gmall.manage;


import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GmallManageWebApplicationTests {

    @Test
    public void contextLoads() throws Exception {

        String file = this.getClass().getResource("/tracker.conf").getFile();
        ClientGlobal.init(file);
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getTrackerServer();
        StorageClient storageClient = new StorageClient(trackerServer, null);
        String orginalFilename = "D://c.jpg";
        String[] upload_file = storageClient.upload_file(orginalFilename, "jpg", null);
        String urlPath = "http://192.168.19.135/";
        for (String url : upload_file) {
            urlPath += url;
            System.out.println(url);
        }
        System.out.println(urlPath + "========================================");


    }

}
