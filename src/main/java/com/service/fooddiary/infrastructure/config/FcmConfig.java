package com.service.fooddiary.infrastructure.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

import static com.service.fooddiary.infrastructure.common.constants.AppConstants.FIREBASE_TEST_KEY_PATH;

@Slf4j
@Configuration
class FcmConfig {

    @Bean
    public FirebaseMessaging firebaseMessaging() throws IOException {
        if (FirebaseApp.getApps().isEmpty()) {
            try (FileInputStream serviceAccount = new FileInputStream(FIREBASE_TEST_KEY_PATH)) {
                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();

                FirebaseApp.initializeApp(options);
                log.info("Fcm Setting Completed");
            } catch (IOException e) {
                log.error("Failed to initialize FirebaseApp", e);
                throw e;
            }
        }
        return FirebaseMessaging.getInstance();
    }

//    public void sendMessages(List<Message> messages) {
//        RequestStatisticsUtil.processRequests(
//                messages,
//                message -> {
//                    try {
//                        firebaseMessaging.send(message);
//                        return true;
//                    } catch (Exception e) {
//                        log.error("Error sending FCM message", e);
//                        return false;
//                    }
//                },
//                summary -> log.info("FCM Message Summary: {}", summary)
//        );
//    }
}
