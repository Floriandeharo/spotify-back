package fr.code.spotify_back.cataloguecontext.application.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record FavoriteSongDTO(@NotNull boolean favorite,@NotNull UUID publicId) {

}
