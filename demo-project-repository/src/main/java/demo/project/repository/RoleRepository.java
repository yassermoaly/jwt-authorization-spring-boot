package demo.project.repository;

import org.springframework.data.repository.CrudRepository;

import demo.project.model.data.Role;

public interface RoleRepository extends CrudRepository<Role,Long> {

}
