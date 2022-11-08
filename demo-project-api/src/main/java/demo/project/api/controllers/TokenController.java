package demo.project.api.controllers;

import javax.naming.AuthenticationException;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import demo.project.api.security.JwtTokenProvider;
import demo.project.model.view.AuthToken;
import demo.project.model.view.LoginRequest;
import demo.project.persistence.interfaces.PersistenceService;
import demo.project.utility.interfaces.Hashing;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/login")
public class TokenController {
		@Autowired
		Hashing hashing;
		
		@Autowired
		PersistenceService persistenceService;
		
		@Autowired
	    private AuthenticationManager authenticationManager;

	    @Autowired
	    private JwtTokenProvider jwtTokenUtil;


	    @RequestMapping(value = "/generate-token", method = RequestMethod.POST)
	    public ResponseEntity<?> register(@RequestBody LoginRequest loginUser) throws AuthenticationException {
	    		boolean emptyBody = loginUser==null || Strings.isEmpty(loginUser.getUsername())|| Strings.isEmpty(loginUser.getPassword());
	        final Authentication authentication = emptyBody?null:authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(
	                        loginUser.getUsername(),
	                        loginUser.getPassword()
	                )
	        );
	        final String token = jwtTokenUtil.generateToken(authentication);
	        SecurityContextHolder.getContext().setAuthentication(authentication);	        
	        return ResponseEntity.ok(new AuthToken(token));  	       
	       
	    }

}
