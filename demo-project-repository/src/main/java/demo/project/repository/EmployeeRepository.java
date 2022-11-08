package demo.project.repository;






import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import demo.project.model.data.Employee;

public interface EmployeeRepository extends CrudRepository<Employee,Long> {

	@Query(nativeQuery=true,value="SELECT * FROM employees where name = :name")
	List<Employee> getAllNativeQuery(@Param("name") String name);
	 
	@Query("SELECT e FROM Employee e WHERE LOWER(e.name) = LOWER(:name)")
	Optional<Employee> findByName(@Param("name") String name);
	
	@Query("SELECT e FROM Employee e WHERE e.name like %:name%")
	List<Employee> findLikeName(@Param("name") String name);
	
	 @Procedure(name = "EMPLOYEEUPDATE",procedureName="EmployeeUpdate")
	 void EmployeeUpdateName(@Param("IN_ID") Integer IN_ID,@Param("IN_NAME") String IN_NAME);
	
	
	
	
}


