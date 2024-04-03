package co.istad.mobilebanking.features.media.dto;

import lombok.Builder;

@Builder    //build object through builder pattern, no need constructor, builder chaining
public record MediaResponse(
        String name,    //unique
        String contentType, //PNG, JPEG, MP4
        String extension,
        String uri,
        Long size
) {
}
