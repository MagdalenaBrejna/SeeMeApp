package mb.seeme.services.users;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService{

    void saveProviderImage(Long providerId, MultipartFile file);
}
