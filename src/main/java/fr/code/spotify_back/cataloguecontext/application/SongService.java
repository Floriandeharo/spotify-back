package fr.code.spotify_back.cataloguecontext.application;

import org.springframework.stereotype.Service;

import fr.code.spotify_back.cataloguecontext.application.dto.ReadSongInfoDTO;
import fr.code.spotify_back.cataloguecontext.application.dto.SaveSongDTO;
import fr.code.spotify_back.cataloguecontext.application.mapper.SongContentMapper;
import fr.code.spotify_back.cataloguecontext.application.mapper.SongMapper;
import fr.code.spotify_back.cataloguecontext.domain.Song;
import fr.code.spotify_back.cataloguecontext.domain.SongContent;
import fr.code.spotify_back.cataloguecontext.repository.SongContentRepository;
import fr.code.spotify_back.cataloguecontext.repository.SongRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class SongService {

	private final SongMapper songMapper;
	
	private final SongRepository songRepository; 
	
	private final SongContentRepository songContentRepository; 
	
	private final SongContentMapper songContentMapper;
	
	public SongService(SongMapper songMapper, SongRepository songRepository, 
			SongContentRepository songContentRepository, SongContentMapper songContentMapper) {
		this.songRepository = songRepository ; 
		this.songMapper = songMapper;
		this.songContentMapper = songContentMapper;
		this.songContentRepository = songContentRepository; 
	}
	
	public ReadSongInfoDTO create(SaveSongDTO saveSongDTO) {
		Song song = songMapper.saveSongDTOToSong(saveSongDTO);
		Song songSaved = songRepository.save(song);
		
		SongContent songContent = songContentMapper.saveSongDTOToSong(saveSongDTO);
		songContent.setSong(songSaved);
		
		songContentRepository.save(songContent);
		return songMapper.songToReadSongInfoDTO(songSaved);
		
	}
}
