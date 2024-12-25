package fr.code.spotify_back.cataloguecontext.application.dto;

import fr.code.spotify_back.cataloguecontext.application.vo.SongAuthorVO;
import fr.code.spotify_back.cataloguecontext.application.vo.SongTitleVO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record SaveSongDTO(@Valid SongTitleVO title,
						 @Valid SongAuthorVO author,
						 @NotNull byte[] cover,
						 @NotNull String coverContentType,
						 @NotNull byte[] file,
						 @NotNull String fileContentType) {

}
