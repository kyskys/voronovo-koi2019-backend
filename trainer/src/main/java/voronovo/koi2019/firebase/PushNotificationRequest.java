package voronovo.koi2019.firebase;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PushNotificationRequest {
    private String topic;
    private String title;
    private String message;
    private String token;
}
