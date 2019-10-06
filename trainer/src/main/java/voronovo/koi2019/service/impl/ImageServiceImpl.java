package voronovo.koi2019.service.impl;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import voronovo.koi2019.generation.util.ConstantsHolder;
import voronovo.koi2019.service.ImageService;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {
    @Value("${app.files.images.path}")
    private String basePath;

    private final Logger logger = LoggerFactory.getLogger(ImageService.class);

    private boolean isSupportedContentType(String contentType) {
        return ConstantsHolder.SUPPORTED_IMAGES_TYPES.contains(contentType);
    }

    @PostConstruct
    public void createImageFolder() {
        File imageStore = new File(basePath);
        logger.info("Trying to create base path for images, {}", imageStore.getAbsolutePath());
        if (!new File(basePath).mkdirs()) {
            logger.info("Can't create image storage");
        } else {
            logger.info("Base image path was created");
        }
    }

    @Override
    public String createImage(MultipartFile image) {
        if (!image.isEmpty()) {
            if (image.getSize() > ConstantsHolder.IMAGE_MAX_SIZE_IN_BYTES) {
                throw new RuntimeException("image size too large, should be " + ConstantsHolder.MAX_IMAGE_SIZE_IN_MBYTES + "mb");
            }
            String fileContentType = image.getContentType();
            if (isSupportedContentType(fileContentType)) {
                String fileName = UUID.randomUUID().toString();
                String path = basePath + fileName;

                try ( FileOutputStream fileOutputStream = new FileOutputStream(path)){
                    fileOutputStream.write(image.getBytes());
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    return fileName;
                } catch (Exception e) {
                    throw new RuntimeException("Can't save file by path " + path);
                }
            } else
                throw new RuntimeException("Unsupported image type " + fileContentType);
        } else
            throw new RuntimeException("Image file is empty" + image.getOriginalFilename());
    }

    @Override
    public void deleteImage(String imageName) {
        try {
            File file = new File(basePath + imageName);
            if (!file.delete()) {
                throw new RuntimeException("Can't remove image file " + imageName);
            }
        } catch (Exception e) {
            throw new RuntimeException(imageName + " not found", e);
        }

    }

    @Override
    public byte[] getImage(String imageName) {
        File file = new File(basePath + imageName);
        try {
            InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
            return IOUtils.toByteArray(inputStream);
        } catch (Exception e) {
            throw new RuntimeException("Image not found", e);
        }
    }
}
