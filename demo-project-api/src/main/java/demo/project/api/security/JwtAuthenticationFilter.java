package demo.project.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import demo.project.persistence.interfaces.PersistenceService;
import demo.project.utility.interfaces.Hashing;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;


public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	Hashing hashing;
	
	@Autowired
	PersistenceService persistenceService;
	
    @Autowired
    private JwtTokenProvider jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {  	
        try {	        	
        	UsernamePasswordAuthenticationToken authentication = jwtTokenUtil.getAuthentication(req,res);
        	if(authentication!=null) {
        		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
        		SecurityContextHolder.getContext().setAuthentication(authentication);    
        		
        	}        	
        } catch (IllegalArgumentException e) {
            logger.error("an error occured during getting username from token", e);
        } catch (ExpiredJwtException e) {
            logger.warn("the token is expired and not valid anymore", e);
        } catch(SignatureException e){
            logger.error("Authentication Failed. Username or Password not valid.");
        }
        chain.doFilter(req, res);
    }
    
}
