package voronovo.koi2019.firebase;

import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.pbkdf2.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
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
                new String(map.get("title").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8),
                new String(map.get("message").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
        return request;
    }
}