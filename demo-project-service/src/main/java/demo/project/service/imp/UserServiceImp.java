package demo.project.service.imp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import demo.project.model.data.User;
import demo.project.repository.UserRepository;
import demo.project.service.interfaces.UserService;
@Service
public class UserServiceImp implements UserService,UserDetailsService {
	@Autowired
	private UserRepository userRepository;
	
	public Optional<User> get(long Id){
		return userRepository.findById(Id);
	}
	public User Save(User User){
		userRepository.save(User);
		 return User;
	}
	
	public List<User> getAll(){
		List<User> result=new ArrayList<User>();
		userRepository.findAll().forEach(result::add);
		return result;
	}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
	}	
	
	private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
		});
		return authorities;
	}
}
