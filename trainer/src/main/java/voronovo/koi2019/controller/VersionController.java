package voronovo.koi2019.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersionController {

    @Value("#{${app.version}}")
    private String version;

    @Value("#{${app.version.href}}")
    private String href;

    @GetMapping("version")
    public ResponseEntity<String> getVersion() {
        return ResponseEntity.ok(
                String.format("{" +
                        "\"version\": %s, " +
                        "\"href\": \"%s\"" +
                        "}", version, href)
        );
    }
}
