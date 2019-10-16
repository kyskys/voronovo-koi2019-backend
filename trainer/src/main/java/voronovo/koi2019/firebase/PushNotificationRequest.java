package voronovo.koi2019.firebase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PushNotificationRequest {
    private String topic;
    private String title;
    private String message;
    private String token;
}
