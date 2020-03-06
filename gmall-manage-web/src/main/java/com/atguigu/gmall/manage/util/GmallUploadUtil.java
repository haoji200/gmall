package com.atguigu.gmall.manage.util;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.web.multipart.MultipartFile;

public class GmallUploadUtil {
    public static String uploadImage(MultipartFile multipartFile) {

        String responseUrl = "http://192.168.19.135";

        String path = GmallUploadUtil.class.getResource("/tracker.conf").getPath();
        try {
            ClientGlobal.init(path);
            // 链接tracker
            TrackerClient trackerClient = new TrackerClient();
            TrackerServer trackerServer = trackerClient.getTrackerServer();

            // 获得storage
            StorageClient storageClient = new StorageClient(trackerServer, null);

            // 上传文件
            String originalFilename = multipartFile.getOriginalFilename();// sfsdfsd.fefe-efefe.a.jpg
            int index = originalFilename.lastIndexOf(".");
            String ext = originalFilename.substring(index + 1);
            String[] urls = storageClient.upload_file(multipartFile.getBytes(), ext, null);

            // 解析返回的图片的路径url信息
            for (String url : urls) {
                responseUrl = responseUrl + "/" + url;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return responseUrl;
    }
}
