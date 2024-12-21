package fr.code.spotify_back.usercontext.presentation;

import java.net.URI;
import java.text.MessageFormat;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.code.spotify_back.usercontext.ReadUserDTO;
import fr.code.spotify_back.usercontext.application.UserService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class AuthResource {
	
	private final UserService userService;
	
	private final ClientRegistration registration;
	
	public AuthResource(UserService userService, ClientRegistrationRepository registrations) {
		this.userService = userService;
		this.registration = registrations.findByRegistrationId("okta") ;
	}
	
	@GetMapping("/get-authenticated-user")
	public ResponseEntity<ReadUserDTO> getAuthenticatedUser(@AuthenticationPrincipal OAuth2User user){
			System.out.print("erreur");
		if ( user == null) {
			return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
		}else {
			userService.syncWithIdp(user);
			ReadUserDTO userFromAuthentication = userService.getAuthenticatedUserFromSecurityContext();
			return ResponseEntity.ok().body(userFromAuthentication);
		}
		
	}
	
	
	
	@PostMapping("/logout")
	public ResponseEntity<?> logout(HttpServletRequest request) {
		String issuerUri = registration.getProviderDetails().getIssuerUri();
		String originUrl = request.getHeader(HttpHeaders.ORIGIN);
		Object[] params = {issuerUri, registration.getClientId(), originUrl};
		String logoutUrl = MessageFormat.format("{0}v2/logout?clientId={1}&returnTo={2}", params);
		request.getSession().invalidate();
		return ResponseEntity.ok().body(Map.of("logoutUrl", logoutUrl));
	}
	

}