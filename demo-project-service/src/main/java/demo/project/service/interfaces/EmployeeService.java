package demo.project.service.interfaces;

import java.util.List;
import java.util.Optional;
import demo.project.model.data.Employee;

public interface EmployeeService {	
	
	Optional<Employee> get(long Id);
	Employee Save(Employee employee);	
	List<Employee> getAll();
}
