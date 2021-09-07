package com.callteam.utill;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@Component
public class CloudConfig {

    @Value("${cloudProjectId}")
    private String projectId;

    @Value("${cloudJsonFile}")
    private String jsonFile;

    public StorageOptions configStorage() {


        try {

            File file = ResourceUtils.getFile("classpath:call-team.json");

            InputStream in = new FileInputStream(file);

            StorageOptions options = StorageOptions.newBuilder()
                    .setProjectId(projectId)
                    .setCredentials(GoogleCredentials.fromStream(
                           in)).build();

            return options;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
