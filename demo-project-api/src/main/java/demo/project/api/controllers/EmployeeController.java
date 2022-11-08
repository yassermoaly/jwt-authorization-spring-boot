package demo.project.api.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import demo.project.api.security.*;
import demo.project.model.data.Employee;
import demo.project.model.view.VmEmployee;
import demo.project.model.view.VmEmployeeTiny;
import demo.project.service.imp.EmployeeServiceImp;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;


@RestController
@RequestMapping("/employee")
//@PreAuthorize("hasRole('"+JwtTokenProvider.AnonymousAccessRole+"')")
public class EmployeeController {
	
	@Autowired 
	DozerBeanMapper dozerBeanMapper;
	
	@Autowired
	private EmployeeServiceImp employeeService;
	
		
	@RequestMapping(method = RequestMethod.GET,value="/downloadPdf")
	//public ResponseEntity<byte[]> downloadPdf() throws IOException {
	public void downloadPdf(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
		String filePath = "F:\\bill.pdf";
		 byte[] content = Files.readAllBytes(new File(filePath).toPath());
	 
	 
//	  HttpHeaders headers = new HttpHeaders();
	 //   headers.setContentType(MediaType.parseMediaType("application/pdf"));
	    // Here you have to set the actual filename of your pdf
	    String filename = "output.pdf";
	//    headers.setContentDispositionFormData("inline", filename);
	//    headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
	//    ResponseEntity<byte[]> responseث = new ResponseEntity<>(content, headers, HttpStatus.OK);
	//    return response;
	    Path file = Paths.get(filePath);
		 response.setContentType("application/pdf");
         response.addHeader("Content-Disposition", "inline; filename="+filename);
         try
         {
             Files.copy(file, response.getOutputStream());
             response.getOutputStream().flush();
         }
         catch (IOException ex) {
             ex.printStackTrace();
         }
				
	}
	
	@RequestMapping(method = RequestMethod.GET,value="/getall")
	@ApiImplicitParams({ @ApiImplicitParam(name = JwtTokenProvider.HeaderKey, required = true, dataType = "string", paramType = "header") })
	public List<Employee> getAll() {
	//	List<VmEmployee> result=new ArrayList<VmEmployee>();	
	//	for(Employee item : employeeService.getAll()) {
	//		result.add(dozerBeanMapper.map(item, VmEmployee.class));
	//	}
		return employeeService.getAll();			 
	}
	
	@RequestMapping(method = RequestMethod.GET,value="/getalltiny")
	@ApiImplicitParams({ @ApiImplicitParam(name = JwtTokenProvider.HeaderKey, required = true, dataType = "string", paramType = "header") })
	public List<VmEmployeeTiny> getAllTiny() {
		List<VmEmployeeTiny> result=new ArrayList<VmEmployeeTiny>();	
		for(Employee item : employeeService.getAll()) {
			result.add(dozerBeanMapper.map(item, VmEmployeeTiny.class));
		}
		return result;			 
	}
	
	@RequestMapping(method = RequestMethod.GET,value="/get/{Id}")
	@ApiImplicitParams({ @ApiImplicitParam(name = JwtTokenProvider.HeaderKey, required = true, dataType = "string", paramType = "header") })
	public Optional<Employee> get(@PathVariable int Id) {
		return employeeService.get(Id);
	}
	
	@RequestMapping(method = RequestMethod.GET,value="/update/{Id}")
	public boolean update(@PathVariable int Id) {
		
	//	StringBuilder sql = new StringBuilder(
	//	        "insert into SubscriberAccountManager(subscriberId,associatedMsisdn) ");
	//	sql.append("select s.id ,cast(:associatedMsisdn as string)  from Subscriber s where s.msisdn = :subscriberMsisdn");
	//	Query query = entityManager.createQuery(sql.toString());
	//	query.setParameter("associatedMsisdn", associatedMsisdn);
	//	query.setParameter("subscriberMsisdn", subscriberMsisdn);
	//	query.executeUpdate();
		
	//	Optional<Employee> e =  employeeService.get(Id);
		// DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		  // LocalDateTime now = LocalDateTime.now();  
	//	e.get().setName(dtf.format(now));
	//	Employee rrr =  employeeService.Save(e.get());
		return true;
	}
	
	@RequestMapping(method = RequestMethod.POST,value="/get/add")
	@ApiImplicitParams({ @ApiImplicitParam(name = JwtTokenProvider.HeaderKey, required = true, dataType = "string", paramType = "header") })
	public Employee add(@RequestBody Employee employee) {
		return employeeService.Save(employee);
	}
	
	@RequestMapping(method = RequestMethod.POST,value="/get/update")
	@ApiImplicitParams({ @ApiImplicitParam(name = JwtTokenProvider.HeaderKey, required = true, dataType = "string", paramType = "header") })
	public Employee update(@RequestBody Employee employee) {
		return employeeService.Save(employee);
	}
	
}
