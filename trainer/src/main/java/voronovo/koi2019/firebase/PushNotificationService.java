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

    @Value("#{${app.notifications.question.new}}")
    private Map<String, String> newQuestionMap;

    @Value("#{${app.notifications.question.solved}}")
    private Map<String, String> solvedQuestionMap;

    private FirebaseService service;

    public PushNotificationService(FirebaseService service) {
        this.service = service;
    }

    public void sendNewQuestionNotification() {
        try {
            service.sendMessage(getIsNewQuestionData(true), getSamplePushNotificationRequest(this.newQuestionMap));
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("error during sending new question push notification", e);
        }
    }
    public void sendSolvedQuestionNotification() {
        try {
            service.sendMessage(getIsNewQuestionData(false), getSamplePushNotificationRequest(this.solvedQuestionMap));
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("error during sending solved question push notification", e);
        }
    }

    private Map<String, String> getIsNewQuestionData(boolean isNewQuestion) {
        Map<String, String> pushData = new HashMap<>();
        pushData.put("isNewQuestion", String.valueOf(isNewQuestion));
        return pushData;
    }

    private PushNotificationRequest getSamplePushNotificationRequest(Map<String, String> map) {
        PushNotificationRequest request = new PushNotificationRequest(
                map.get("topic"),
                map.get("title"),
                map.get("message"));
        return request;
    }
}