package fr.code.spotify_back.cataloguecontext.application;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.code.spotify_back.cataloguecontext.application.dto.FavoriteSongDTO;
import fr.code.spotify_back.cataloguecontext.repository.FavoriteRepository;
import fr.code.spotify_back.cataloguecontext.application.dto.ReadSongInfoDTO;
import fr.code.spotify_back.cataloguecontext.application.dto.SaveSongDTO;
import fr.code.spotify_back.cataloguecontext.application.dto.SongContentDTO;
import fr.code.spotify_back.cataloguecontext.application.mapper.SongContentMapper;
import fr.code.spotify_back.cataloguecontext.application.mapper.SongMapper;
import fr.code.spotify_back.cataloguecontext.domain.Favorite;
import fr.code.spotify_back.cataloguecontext.domain.FavoriteId;
import fr.code.spotify_back.cataloguecontext.domain.Song;
import fr.code.spotify_back.cataloguecontext.domain.SongContent;
import fr.code.spotify_back.cataloguecontext.repository.SongContentRepository;
import fr.code.spotify_back.cataloguecontext.repository.SongRepository;
import fr.code.spotify_back.infrastructure.service.dto.State;
import fr.code.spotify_back.infrastructure.service.dto.StateBuilder;
import fr.code.spotify_back.usercontext.ReadUserDTO;
import fr.code.spotify_back.usercontext.application.UserService;


@Service
@Transactional
public class SongService {

	private final SongMapper songMapper;
	
	private final SongRepository songRepository; 
	
	private final SongContentRepository songContentRepository; 
	
	private final SongContentMapper songContentMapper;
	
	private final FavoriteRepository favoriteRepository ; 
	
	private final UserService userService ; 
	
	public SongService(SongMapper songMapper, SongRepository songRepository, UserService userService,FavoriteRepository favoriteRepository, 
			SongContentRepository songContentRepository, SongContentMapper songContentMapper) {
		this.songRepository = songRepository ; 
		this.songMapper = songMapper;
		this.favoriteRepository = favoriteRepository;
		this.songContentMapper = songContentMapper;
		this.songContentRepository = songContentRepository;
		this.userService = userService;
	}
	
	public ReadSongInfoDTO create(SaveSongDTO saveSongDTO) {
		Song song = songMapper.saveSongDTOToSong(saveSongDTO);
		Song songSaved = songRepository.save(song);
		
		SongContent songContent = songContentMapper.saveSongDTOToSong(saveSongDTO);
		songContent.setSong(songSaved);
		
		songContentRepository.save(songContent);
		return songMapper.songToReadSongInfoDTO(songSaved);
		
	}
	
	@Transactional(readOnly = true)
	public List<ReadSongInfoDTO> getAll(){
		
		List<ReadSongInfoDTO> allSongs = songRepository.findAll()
				.stream()
				.map(songMapper::songToReadSongInfoDTO)
				.toList();
		if(userService.isAuthenticated()) {
			return fetchFavoritesStatusForSongs(allSongs);
		}
		return allSongs; 
	}
	
	public Optional<SongContentDTO> getOneByPublicId(UUID publicId){
		Optional<SongContent> songByPublicId = songContentRepository.findOneBySongPublicId(publicId);
		return songByPublicId.map(songContentMapper::songContentToSongContentDTO);
		
	}
	
	public List<ReadSongInfoDTO> search(String searchTerm){
		List<ReadSongInfoDTO> searchedSongs = songRepository.findByTitleOrAuthorContaining(searchTerm)
				.stream()
				.map(songMapper::songToReadSongInfoDTO)
				.collect(Collectors.toList());
		
		if(userService.isAuthenticated()) {
			return fetchFavoritesStatusForSongs(searchedSongs);
		}else {
			return searchedSongs;
		}
	}
	
	public State<FavoriteSongDTO, String> addOrRemoveFromFavorite(FavoriteSongDTO favoriteSongDTO, String email){
		StateBuilder<FavoriteSongDTO, String> builder = State.builder();
		Optional<Song> songToLikeOpt = songRepository.findOneByPublicId(favoriteSongDTO.publicId());
		if(songToLikeOpt.isEmpty()) {
			return builder.forError("Song public id doesn't exist").build();
		}
		
		Song songToLike = songToLikeOpt.get();
		
		ReadUserDTO userWhoLikedSong = userService.getByEmail(email).orElseThrow();
		
		if(favoriteSongDTO.favorite()) {
			Favorite favorite = new Favorite();
			favorite.setSongPublicId(songToLike.getPublicId());
			favorite.setUserEmail(userWhoLikedSong.email());
			favoriteRepository.save(favorite);
		}else {
			FavoriteId favoriteId = new FavoriteId(songToLike.getPublicId(), userWhoLikedSong.email());
			favoriteRepository.deleteById(favoriteId);
			favoriteSongDTO = new FavoriteSongDTO(false, songToLike.getPublicId());
		}
		
		return builder.forSuccess(favoriteSongDTO).build();
	}
	
	public List<ReadSongInfoDTO> fetchFavoriteSongs(String email){
		return songRepository.findAllFavoriteByUserEmail(email)
				.stream()
				.map(songMapper::songToReadSongInfoDTO)
				.toList();
	}
	
	private List<ReadSongInfoDTO> fetchFavoritesStatusForSongs(List<ReadSongInfoDTO> songs){
		ReadUserDTO authenticatedUser = userService.getAuthenticatedUserFromSecurityContext();
		
		List<UUID> songPublicIds = songs.stream().map(ReadSongInfoDTO::getPublicId).toList();
		
		List<UUID> userFavoriteSongs = favoriteRepository.findAllByUserEmailAndSongPublicIdIn(authenticatedUser.email(), songPublicIds).stream().map(Favorite::getSongPublicId).toList();
		
		return songs.stream().peek(
				song -> {
					if(userFavoriteSongs.contains(song.getPublicId())) {
						song.setFavorite(true);
					}
				}).toList();
	}
	
}
