package voronovo.koi2019.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;
import voronovo.koi2019.generation.util.ConstantsHolder;

import javax.annotation.PostConstruct;
import java.io.File;

public interface ImageService {
    String createImage(MultipartFile image);
    void deleteImage(String imageName);
    byte[] getImage(String imageName);
}
