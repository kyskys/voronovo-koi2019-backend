package voronovo.koi2019.firebase;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class PushNotificationService {

    @Value("#{${app.notifications.defaults}}")
    private Map<String, String> defaults;

    private FirebaseService service;

    public PushNotificationService(FirebaseService service) {
        this.service = service;
    }

    @Scheduled(initialDelay = 60000, fixedDelay = 60000)
    public void sendSamplePushNotification() {
        try {
            service.sendMessageWithoutData(getSamplePushNotificationRequest());
        } catch (InterruptedException | ExecutionException e) {
            //logger.error(e.getMessage());
        }
    }

    public void sendIsNewQuestionNotification(boolean isNewQuestion) {
        try {
            service.sendMessage(getIsNewQuestionData(isNewQuestion), getSamplePushNotificationRequest());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("error during sending push notification", e);
        }
    }

    public void sendPushNotification(PushNotificationRequest request) {
        try {
            service.sendMessage(getSamplePayloadData(), request);
        } catch (InterruptedException | ExecutionException e) {
            //logger.error(e.getMessage());
        }
    }

    public void sendPushNotificationWithoutData(PushNotificationRequest request) {
        try {
            service.sendMessageWithoutData(request);
        } catch (InterruptedException | ExecutionException e) {
            //logger.error(e.getMessage());
        }
    }


    public void sendPushNotificationToToken(PushNotificationRequest request) {
        try {
            service.sendMessageToToken(request);
        } catch (InterruptedException | ExecutionException e) {
            //logger.error(e.getMessage());
        }
    }


    private Map<String, String> getSamplePayloadData() {
        Map<String, String> pushData = new HashMap<>();
        pushData.put("messageId", defaults.get("payloadMessageId"));
        pushData.put("text", defaults.get("payloadData") + " " + LocalDateTime.now());
        pushData.put("isNewQuestion", "true");
        return pushData;
    }

    private Map<String, String> getIsNewQuestionData(boolean isNewQuestion) {
        Map<String, String> pushData = new HashMap<>();
        pushData.put("isNewQuestion", String.valueOf(isNewQuestion));
        return pushData;
    }

    private PushNotificationRequest getSamplePushNotificationRequest() {
        PushNotificationRequest request = new PushNotificationRequest(
                defaults.get("topic"),
                defaults.get("title"),
                defaults.get("message"),
                null);
        return request;
    }
}