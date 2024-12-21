package fr.code.spotify_back.cataloguecontext.application.dto;

import java.util.UUID;

import jakarta.persistence.Lob;

public record SaveContentDTO(UUID publicId, @Lob byte[] file, String fileContentType) {

}
