package et.com.gebeya.parkinglotservice.service;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ResourceLoader resourceLoader;
    private final long maxImageSize = 1048576; // 1 MB in bytes

//    @Value("${upload.dir}")
    private String uploadDir;


    public String storeImage(MultipartFile image)  {
        // Validation:
        validateImage(image);

        try {
            // Generate unique filename
            String uniqueFilename = generateUniqueFilename(image.getOriginalFilename());

            File uploadPath = new File(uploadDir);

            if (!uploadPath.exists()) {
                uploadPath.mkdirs();
            }


            File imageFile = new File(uploadPath, uniqueFilename);
            image.transferTo(imageFile);

            String imageUrl = "/uploads/" + uniqueFilename;
            return imageUrl;
        }
//        catch (IOException e) {
//            throw new ImageUploadException("Error storing image: " + e.getMessage(), e);
//        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void validateImage(MultipartFile image) {
        if (image == null || image.isEmpty()) {
            throw new Error("Image file cannot be empty.");
        }

        if (image.getSize() > maxImageSize) {
            throw new Error("Image size exceeds maximum limit of " + maxImageSize / 1048576 + " MB.");
        }

        String originalFilename = image.getOriginalFilename();
        if (!isValidImageType(originalFilename)) {
            throw new Error("Unsupported image type. Only JPEG, PNG, and GIF are allowed.");
        }
    }

    private boolean isValidImageType(String filename) {
        String extension = FilenameUtils.getExtension(filename).toLowerCase();
        return extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png") || extension.equals("gif");
    }

    private String generateUniqueFilename(String originalFilename) {
        String extension = FilenameUtils.getExtension(originalFilename);
        return UUID.randomUUID().toString() + "." + extension;
    }
}
