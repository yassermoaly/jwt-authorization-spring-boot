package demo.project.service.interfaces;

import java.util.List;
import java.util.Optional;
import demo.project.model.data.User;

public interface UserService{	
	Optional<User> get(long Id);
	User Save(User User);	
	List<User> getAll();	
}
