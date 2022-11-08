package demo.project.service.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.project.repository.EmployeeRepository;
import demo.project.service.interfaces.EmployeeService;
import demo.project.model.data.Employee;

@Service
public class EmployeeServiceImp implements EmployeeService {	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	public Optional<Employee> get(long Id){	
		List<Employee> _result = employeeRepository.findLikeName("Ah");
		List<Employee> ___result = employeeRepository.getAllNativeQuery("Tarek Khedr Abdallah");
		
		employeeRepository.EmployeeUpdateName(1,"test Yasser");
		return employeeRepository.findById(Id);
	}
	public Employee Save(Employee employee){
		 employeeRepository.save(employee);
		 return employee;
	}
	
	public List<Employee> getAll(){
	//	Iterable<Employee> _result  = employeeRepository.getAllByName("Ah");
		List<Employee> result=new ArrayList<Employee>();
		employeeRepository.findAll().forEach(result::add);
		return result;
	}
}
