package co.istad.mobilebanking.features.media;

import co.istad.mobilebanking.features.media.dto.MediaResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MediaService {
    MediaResponse uploadSingle(MultipartFile file, String folderName);
    List<MediaResponse> uploadMultiple(List<MultipartFile> files, String folderName);
    MediaResponse loadMediaByName(String mediaName, String folderName);
    MediaResponse deleteMediaByName(String mediaName, String folderName);
    List<MediaResponse> loadAllMediaFiles(String folderName);
    ResponseEntity<Resource> downloadMediaByName(String fileName, String folderName);
}
