package com.chsi.knowledge.util;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import com.chsi.cms.client.CmsServiceClient;
import com.chsi.cms.client.CmsServiceClientFactory;

public class RemoteCallTest {
    @Test
    public void testSaveCmsBlob() {
        /*CmsServiceClient cmsServiceClient = CmsServiceClientFactory.getCmsServiceClient();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream in = classLoader.getResourceAsStream("050022321.jpg");
        byte[] photo = image2byte(in);;
        String url = cmsServiceClient.saveBlob("050022321.jpg", "image/jpeg", photo);
        System.out.print(url);*/
    }
    
    private byte[] image2byte(InputStream input) {
        byte[] data = null;
        try {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int numBytesRead = 0;
            while ((numBytesRead = input.read(buf)) != -1) {
                output.write(buf, 0, numBytesRead);
            }
            data = output.toByteArray();
            output.close();
            input.close();
        } catch (FileNotFoundException ex1) {
            ex1.printStackTrace();
        } catch (IOException ex1) {
            ex1.printStackTrace();
        }
        return data;
    }
}
