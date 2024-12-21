package fr.code.spotify_back.cataloguecontext.domain;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class FavoriteId implements Serializable{

	UUID songPublicId;
	
	String userEmail;
	
	public FavoriteId() {
	}
	
	public FavoriteId(UUID songPublicId, String userEmail) {
		this.songPublicId = songPublicId;
		this.userEmail = userEmail; 
	}

	public UUID getSongPublicId() {
		return songPublicId;
	}

	public void setSongPublicId(UUID songPublicId) {
		this.songPublicId = songPublicId;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	@Override
	public int hashCode() {
		return Objects.hash(songPublicId, userEmail);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FavoriteId other = (FavoriteId) obj;
		return Objects.equals(songPublicId, other.songPublicId) && Objects.equals(userEmail, other.userEmail);
	}
	
	
}
