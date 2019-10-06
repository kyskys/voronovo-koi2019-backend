package voronovo.koi2019.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import voronovo.koi2019.service.ImageService;

@RestController
@RequestMapping("image")
public class ImageController {
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping
    public byte[] getImage(String name) {
        return imageService.getImage(name);
    }

    @PostMapping
    public String uploadImage(MultipartFile file) {
        return imageService.createImage(file);
    }

    @DeleteMapping
    public void deleteImage(String name) {
        imageService.deleteImage(name);
    }
}
