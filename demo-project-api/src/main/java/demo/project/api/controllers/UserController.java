package demo.project.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import demo.project.api.security.JwtTokenProvider;
import demo.project.model.data.User;
import demo.project.persistence.interfaces.PersistenceService;
import demo.project.service.imp.UserServiceImp;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;


@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserServiceImp userService;
	
	
	@Autowired
	private PersistenceService persistenceService;
	
	@ApiImplicitParams({ @ApiImplicitParam(name = JwtTokenProvider.HeaderKey, required = true, dataType = "string", paramType = "header") })
	@PreAuthorize("hasRole('admin')")	
	@RequestMapping(method = RequestMethod.GET,value="/getall")
	public List<User> getAll() {
		return userService.getAll();	
		
	}
	@RequestMapping(method = RequestMethod.GET,value="/set/{Id}")
	public boolean addData(@PathVariable int Id) {
		persistenceService.set("tdata", 20,Id);		
		return true;
	}
	@RequestMapping(method = RequestMethod.GET,value="/get")
	@ApiImplicitParams({ @ApiImplicitParam(name = JwtTokenProvider.HeaderKey, required = true, dataType = "string", paramType = "header") })
	public String getData() {
		
		return persistenceService.<Integer>get("tdata").toString();
	}
	
}
