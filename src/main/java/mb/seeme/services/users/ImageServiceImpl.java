package mb.seeme.services.users;

import mb.seeme.repositories.ServiceProviderRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public class ImageServiceImpl implements ImageService {

    private final ServiceProviderRepository providerRepository;

    public ImageServiceImpl(ServiceProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }

    @Override
    @Transactional
    public void saveProviderImage(Long providerId, MultipartFile file) {
        try {
            Byte[] byteObjects = new Byte[file.getBytes().length];
            int i = 0;
            for (byte b : file.getBytes())
                byteObjects[i++] = b;

            providerRepository.saveImage(byteObjects, providerId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
