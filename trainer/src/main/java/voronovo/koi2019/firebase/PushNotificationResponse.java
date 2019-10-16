package voronovo.koi2019.firebase;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PushNotificationResponse {
    private int code;
    private String message;
}
