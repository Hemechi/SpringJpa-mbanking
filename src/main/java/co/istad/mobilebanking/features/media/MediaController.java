package co.istad.mobilebanking.features.media;

import co.istad.mobilebanking.features.media.dto.MediaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/medias")
@RequiredArgsConstructor
public class MediaController {
    private final MediaService mediaService;
    @PostMapping("/upload-single")
    MediaResponse uploadSingle(@RequestPart MultipartFile file) {
        return mediaService.uploadSingle(file,"IMAGE");
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/upload-multiple")
    List<MediaResponse> uploadMultiple(@RequestPart List<MultipartFile> files) {
        return mediaService.uploadMultiple(files, "IMAGE");
    }

    @GetMapping("/{mediaName}")
    MediaResponse loadMediaByName(@PathVariable String mediaName) {
        return mediaService.loadMediaByName(mediaName, "IMAGE");
    }

    @DeleteMapping("/{mediaName}")
    MediaResponse deleteMediaByName(@PathVariable String mediaName) {
        return mediaService.deleteMediaByName(mediaName, "IMAGE");
    }
    @GetMapping("/load-all-files")
    public List<MediaResponse> loadAllMediaFiles() {
        return mediaService.loadAllMediaFiles("IMAGE");
    }
    @GetMapping("/download/{mediaName}")
    public ResponseEntity<?> downloadMedia(@PathVariable String mediaName) {
        return mediaService.downloadMediaByName(mediaName, "IMAGE");
    }
}
