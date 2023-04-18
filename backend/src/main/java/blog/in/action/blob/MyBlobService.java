package blog.in.action.blob;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class MyBlobService {
    private final AzureBlobProperties azureBlobProperties;

    private BlobContainerClient containerClient() {
        String test = "DefaultEndpointsProtocol=https://storagemynm.blob.core.windows.net;AccountName=storagemynm;AccountKey=+/kojtuZZIOyiUhHkrLHFpAXYssARb7G0evr6ceTAqXp6gelNPfW30f+JgZgD0tg6W9ra3wlhdSm+ASt6TXZjg==;EndpointSuffix=core.windows.net";
        BlobServiceClient serviceClient = new BlobServiceClientBuilder()
                .connectionString(test).buildClient();
        BlobContainerClient container = serviceClient.getBlobContainerClient("storagemynmcontainer");
        return container;
//        String container = "DefaultEndpointsProtocol=https://storagemynm.blob.core.windows.net;AccountName=storagemynm;AccountKey=+/kojtuZZIOyiUhHkrLHFpAXYssARb7G0evr6ceTAqXp6gelNPfW30f+JgZgD0tg6W9ra3wlhdSm+ASt6TXZjg==;EndpointSuffix=core.windows.net";
//        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().connectionString(container).buildClient();
//        return container;
    }


    public String storeFile(String filename, InputStream content, long length) {

        System.out.println("filename=> " + filename);
        System.out.println("content => " + content);
        System.out.println("length => " + length);
        //log.info("Azure store file BEGIN {}", filename);
        BlobClient client = containerClient().getBlobClient(filename); // 이미지가 있는지 확인
        if (client.exists()) {
            log.warn("The file was already located on azure");
        } else {
            client.upload(content, length);
        }

        log.info("Azure store file END");
        return "File uploaded with success!";
    }

}

