package fr.code.spotify_back.cataloguecontext.application.vo;

import jakarta.validation.constraints.NotBlank;

public record SongAuthorVO(@NotBlank String value) {

}
