package fr.code.spotify_back.cataloguecontext.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.Validator;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

import fr.code.spotify_back.cataloguecontext.application.SongService;
import fr.code.spotify_back.cataloguecontext.application.dto.FavoriteSongDTO;
import fr.code.spotify_back.cataloguecontext.application.dto.ReadSongInfoDTO;
import fr.code.spotify_back.cataloguecontext.application.dto.SaveSongDTO;
import fr.code.spotify_back.cataloguecontext.application.dto.SongContentDTO;
import fr.code.spotify_back.infrastructure.service.dto.State;
import fr.code.spotify_back.infrastructure.service.dto.StatusNotification;
import fr.code.spotify_back.usercontext.ReadUserDTO;
import fr.code.spotify_back.usercontext.application.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
public class SongResource {

	private final SongService songService;
	
	private final Validator validator;
	
	private UserService userService;
	
	private final ObjectMapper objectMapper = new ObjectMapper();
	
	public SongResource(SongService songService, Validator validator, UserService userService) {
		this.songService = songService;
		this.validator = validator;
		this.userService = userService; 
	}
	
	@PostMapping(value = "/songs", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ReadSongInfoDTO> add(@RequestPart(name = "cover") MultipartFile cover,
											   @RequestPart(name = "file") MultipartFile file,
											   @RequestPart(name = "dto") String saveSongDTOToString) throws IOException{
	    
		System.out.print("----ok--1-");
		SaveSongDTO saveSongDTO = objectMapper.readValue(saveSongDTOToString, SaveSongDTO.class);
		System.out.print("----ok---");
		saveSongDTO = new SaveSongDTO(saveSongDTO.title(), saveSongDTO.author(), cover.getBytes(),
				cover.getContentType(), file.getBytes(), file.getContentType());
		System.out.print(saveSongDTO+ " ----------- ");

		Set<ConstraintViolation<SaveSongDTO>> violations = validator.validate(saveSongDTO);
		System.out.print("-----  test -----");
		if(!violations.isEmpty()) {
			System.out.print("poutre");
			String violationsJoined = violations.stream()
												.map(violation -> violation.getPropertyPath()+ " "+violation.getMessage())
												.collect(Collectors.joining());
			ProblemDetail validationIssue = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, 
					"Validation errors for the fields : "+ violationsJoined);
			return ResponseEntity.of(validationIssue).build();
		} else {
			return ResponseEntity.ok(songService.create(saveSongDTO));
		}
		
		
	}
	
	@GetMapping("/songs")
	public ResponseEntity<List<ReadSongInfoDTO>> getAll(){
		return ResponseEntity.ok(songService.getAll());
	}
	
	@GetMapping("/songs/get-content")
	public ResponseEntity<SongContentDTO> getOneByPublicId(@RequestParam UUID publicId){
		Optional <SongContentDTO> songContentByPublicId = songService.getOneByPublicId(publicId);
		return songContentByPublicId.map(ResponseEntity::ok)
				.orElseGet(()-> ResponseEntity
						.of(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "UUID Unknown")).build());
	}
	
	@GetMapping("/songs/search")
	public ResponseEntity<List<ReadSongInfoDTO>> search(@RequestParam String term){
		return ResponseEntity.ok(songService.search(term));
	}
	
	@PostMapping("/songs/like")
	public ResponseEntity<FavoriteSongDTO> addOrRemoveFromFavorite(@Valid @RequestBody FavoriteSongDTO favoriteSongDTO){
		ReadUserDTO userFromAuthentication = userService.getAuthenticatedUserFromSecurityContext();
		State<FavoriteSongDTO, String> favoriteSongResponse = songService.addOrRemoveFromFavorite(favoriteSongDTO, userFromAuthentication.email());

	
		if(favoriteSongResponse.getStatus().equals(StatusNotification.ERROR)) {
			ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, favoriteSongResponse.getError());
			return ResponseEntity.of(problemDetail).build();
		}else {
			return ResponseEntity.ok(favoriteSongResponse.getValue());
		}	
	}
	
	@GetMapping("/songs/like")
	public ResponseEntity<List<ReadSongInfoDTO>> fetchFavoriteSongs(){
		ReadUserDTO userFromAuthentication = userService.getAuthenticatedUserFromSecurityContext();
		return ResponseEntity.ok(songService.fetchFavoriteSongs(userFromAuthentication.email()));
	}
	
	
}
