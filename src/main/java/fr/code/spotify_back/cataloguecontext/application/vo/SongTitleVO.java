package fr.code.spotify_back.cataloguecontext.application.vo;

import jakarta.validation.constraints.NotBlank;

public record SongTitleVO(@NotBlank String value) {

}
