package fr.code.spotify_back.cataloguecontext.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "song_content")
public class SongContent implements Serializable{

	@Id
	@Column(name ="song_id")
	private Long songId; 
	
	@MapsId
	@OneToOne
	@JoinColumn(name = "song_id", referencedColumnName = "id")
	private Song song;
	
	@Lob
	@Column(name = "file", nullable = false)
	private byte[] file;
	
	@Column(name = "file_content_type")
	private String fileContenType;

	public Long getSongId() {
		return songId;
	}

	public void setSongId(Long songId) {
		this.songId = songId;
	}

	public Song getSong() {
		return song;
	}

	public void setSong(Song song) {
		this.song = song;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	public String getFileContenType() {
		return fileContenType;
	}

	public void setFileContenType(String fileContenType) {
		this.fileContenType = fileContenType;
	}
	
	
}
