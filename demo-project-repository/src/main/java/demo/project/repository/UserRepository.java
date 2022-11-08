package demo.project.repository;

import org.springframework.data.repository.CrudRepository;
import demo.project.model.data.User;


public interface UserRepository extends CrudRepository<User,Long> {
	User findByUsername(String username);
}
