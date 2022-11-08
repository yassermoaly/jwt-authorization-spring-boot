package demo.project.api.security;

import io.jsonwebtoken.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;
import demo.project.persistence.interfaces.PersistenceService;
import demo.project.utility.interfaces.Hashing;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@PropertySource("classpath:jwt-token.properties")
public class JwtTokenProvider implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	Hashing hashing;
	
	@Autowired
	PersistenceService persistenceService;
	
	@Autowired
    private UserDetailsService userDetailsService;
	
	public static final String AnonymousAccessRole = "AnonymousAccess";
	public static final String AnonymousSubjectName = "Anonymous";
	private static int ExpirySeconds;
	public static String SigningKey ;
	public static final String HeaderKey = "Authorization";
	public static final String PersistenceKey = "token";
	private static final String Authorities = "scopes";
	private static final String RefreshTokenHeaderName = "refresh-token";
	
	
	private static Boolean ExpirySliding;
	private static Boolean SaveServerSide;
	
	@Value("${jwt-token.expiry-seconds}")
	private  String expirySeconds;
	@Value("${jwt-token.signing-key}")
	private  String signingKey;
	
	@Value("${jwt-token.expiry-sliding}")
	private  String expirySliding;
	@Value("${jwt-token.save-server-side}")
	private  String saveServerSide;
		



	private String token;
	
	@PostConstruct
	private void initProperties()  {
		JwtTokenProvider.ExpirySeconds = Integer.parseInt(expirySeconds);
		JwtTokenProvider.SigningKey = signingKey;		
		JwtTokenProvider.ExpirySliding = Boolean.parseBoolean(expirySliding);
		JwtTokenProvider.SaveServerSide = Boolean.parseBoolean(saveServerSide);
	}
	
	
	private String getUsernameFromToken(Claims claims) {
        return getClaimFromToken(claims,Claims::getSubject);
    }

    public Date getExpirationDateFromToken(Claims claims) {
        return getClaimFromToken(claims, Claims::getExpiration);
    }

    private <T> T getClaimFromToken(Claims claims,Function<Claims, T> claimsResolver) {      
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(JwtTokenProvider.SigningKey)
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(Claims claims) {    
        final Date expiration = getExpirationDateFromToken(claims);
        return expiration.before(new Date());
    }
    
   
    
    
    public String generateToken(Authentication authentication) {      	     
        final String authorities = authentication==null?("ROLE_"+AnonymousAccessRole):authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        String jwtToken =  Jwts.builder()
                .setSubject(authentication==null?AnonymousSubjectName:authentication.getName())
                .claim(JwtTokenProvider.Authorities, authorities)
                .signWith(SignatureAlgorithm.HS256, JwtTokenProvider.SigningKey)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JwtTokenProvider.ExpirySeconds*1000))
                .compact();
        if(SaveServerSide) {
	    	UUID uuid = UUID.randomUUID();	    	
	    	String tokenId = JwtTokenProvider.PersistenceKey+uuid.toString();
	    	persistenceService.set(tokenId, ExpirySeconds, jwtToken);	
	    	return hashing.Sign(tokenId);
        }
    	return jwtToken;
    }
    
    public String refreshTokenExpiry(String token) {
    
    	  
    	final Claims claims = getAllClaimsFromToken(token);
    
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, JwtTokenProvider.SigningKey)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JwtTokenProvider.ExpirySeconds*1000))
                .compact();
    } 

    public Boolean validateToken (Claims claims) {
        return (!isTokenExpired(claims));
    }

    public UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest req, HttpServletResponse res) {  
    	String jwtTokenHeader = req.getHeader(JwtTokenProvider.HeaderKey);   
    	 if (!Strings.isNullOrEmpty(jwtTokenHeader)) {
	    	String authTokenId = new String(); 
	    	if(SaveServerSide) {
		    	authTokenId =  hashing.verify(jwtTokenHeader);
		    	this.token = persistenceService.<String>get(authTokenId);
	    	}
	    	else
	    	{
	    		this.token = jwtTokenHeader;
	    	}
	    	if(!Strings.isNullOrEmpty(token)) {    		
	    		Claims claims = getAllClaimsFromToken(token);
	        	String username = getUsernameFromToken(claims);
	        	String authorites = claims.get(JwtTokenProvider.Authorities).toString();         
	            Set<SimpleGrantedAuthority> authorities = new HashSet<>();
	             for(String authority :authorites.split(",") ) {
	             	authorities.add(new SimpleGrantedAuthority(authority));
	             }        	
	          //   UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        		 UserDetails userDetails = new org.springframework.security.core.userdetails.User(username, "", authorities);

	        	
	        	if (validateToken(claims)) {     
	        		if(ExpirySliding) {
		        		String refreshedToken = refreshTokenExpiry(token);
		        		if(SaveServerSide)
		        			persistenceService.set(authTokenId, ExpirySeconds, refreshedToken);
		        		else {
		        			res.addHeader(JwtTokenProvider.RefreshTokenHeaderName, refreshedToken);
		        		}
	        		}
	        		return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
	        	}
	    	}    	
    	 }
        return null;
    }

}