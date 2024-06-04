package com.controller;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.customException.EmployeeNotFoundException;
import com.daoService.EmpDao;
import com.model.Employee;
import com.repository.EmpRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/employee")
public class EmpController {

	@Autowired
	private EmpDao service;

	@Autowired
	private EmpRepository repository;

	// Add User
	@PostMapping("/create")
	public ResponseEntity<?> createEmployee(@Valid @RequestBody Employee emp, BindingResult result) {
		ResponseEntity<?> resp = null;
		if (result.hasErrors()) {
			List<String> errorlist = result.getAllErrors().stream()
					.map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
			return new ResponseEntity<>("Validation error : " + errorlist, HttpStatus.BAD_REQUEST);
		}
		// add check for email exists in a DB
		if (repository.existsByEmail(emp.getEmail())) {
			return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
		}
		try {
			Employee e = service.create(emp);
			resp = new ResponseEntity<String>("Employee '" + e.getEmail() + "' created", HttpStatus.CREATED);
			// 201-created
		} catch (Exception e) {
			e.printStackTrace();
			resp = new ResponseEntity<String>("Unable to save emaployee", HttpStatus.INTERNAL_SERVER_ERROR); // 500-Internal
			// Server Error
		}
		return resp;
	}

	// Get AllUsers
	@GetMapping("/employees")
	public ResponseEntity<?> getAllUsers() {
		ResponseEntity<?> resp = null;
		try {
			List<Employee> list = service.getAllEmployees();
			resp = new ResponseEntity<List<Employee>>(list, HttpStatus.OK);// 200 OK
		} catch (Exception e) {
			e.printStackTrace();
			resp = new ResponseEntity<String>("Unable to get employee", HttpStatus.INTERNAL_SERVER_ERROR);// 500-Internal
																											// Server
																											// Error
		}
		return resp;
	}

	// GetById Employee
	@GetMapping("/employee/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") int id) {
		ResponseEntity<?> resp = null;
		try {
			Employee emp = service.getSingleUser(id);
			resp = new ResponseEntity<Employee>(emp, HttpStatus.OK);// 200 OK

		} catch (EmployeeNotFoundException enfe) {
			throw enfe;
		} catch (Exception e) {
			e.printStackTrace();
			resp = new ResponseEntity<String>("Unable to find Employee", HttpStatus.INTERNAL_SERVER_ERROR);// 404-NOT_FOUND
		}
		return resp;
	}

	// GetByEmail Employee
	@GetMapping("/employee/{email}")
	public ResponseEntity<?> getOneEmployee(@PathVariable("email") String email) {
		ResponseEntity<?> resp = null;
		try {
			Employee emp = service.getByEmail(email);
			resp = new ResponseEntity<Employee>(emp, HttpStatus.OK);// 200 OK

		} catch (EmployeeNotFoundException enfe) {
			throw enfe;
		} catch (Exception e) {
			e.printStackTrace();
			resp = new ResponseEntity<String>("Unable to find Employee", HttpStatus.INTERNAL_SERVER_ERROR);// 404-NOT_FOUND
		}
		return resp;
	}

	// Update UserByID
	@PutMapping("/employee/{id}")
	public ResponseEntity<?> updateInvoice(@PathVariable("id") int id, @RequestBody Employee emp) {

		ResponseEntity<?> resp = null;
		try {
			Employee e = service.updateuser(id, emp);
			resp = new ResponseEntity<String>("Emplyee Id: " + e.getId() + " Updated Successfull " + e, HttpStatus.OK);
		} catch (EmployeeNotFoundException enfe) {
			throw enfe;

		} catch (Exception e) {
			e.printStackTrace();
			resp = new ResponseEntity<String>("Unable to Update employee", HttpStatus.INTERNAL_SERVER_ERROR); // 404-NOT_FOUND
		}
		return resp;
	}

	// UpdateByemail
	@PutMapping("/employee/{email}")
	public ResponseEntity<?> updateInvoice(@PathVariable("email") String email, @RequestBody Employee emp) {

		ResponseEntity<?> resp = null;
		try {
			Employee e = service.updateByEmail(email, emp);
			resp = new ResponseEntity<String>("Emplyee email: " + e.getEmail() + " Updated Successfull " + e,
					HttpStatus.OK);
		} catch (EmployeeNotFoundException enfe) {
			throw enfe;

		} catch (Exception e) {
			e.printStackTrace();
			resp = new ResponseEntity<String>("Unable to Update employee", HttpStatus.INTERNAL_SERVER_ERROR); // 404-NOT_FOUND
		}
		return resp;
	}

	// Delete userById
	@DeleteMapping("/employee/{id}")
	public ResponseEntity<?> deleteOneUser(@PathVariable("id") int id) {
		ResponseEntity<?> resp = null;
		try {
			service.deleteById(id);
			resp = new ResponseEntity<String>("User " + id + " deleted", HttpStatus.OK);
		} catch (EmployeeNotFoundException enfe) {
			throw enfe;
		} catch (Exception e) {
			e.printStackTrace();
			resp = new ResponseEntity<String>("Unable to delete employee", HttpStatus.INTERNAL_SERVER_ERROR);// 404-NOT_FOUND
		}
		return resp;
	}

	// Delete userByemail
	@DeleteMapping("/employee/{email}")
	public ResponseEntity<?> deleteOneUser(@PathVariable("email") String email) {
		ResponseEntity<?> resp = null;
		try {
			service.deleteByEmail(email);
			resp = new ResponseEntity<String>("User " + email + " deleted", HttpStatus.OK);
		} catch (EmployeeNotFoundException enfe) {
			throw enfe;
		} catch (Exception e) {
			e.printStackTrace();
			resp = new ResponseEntity<String>(" Unable to delete employee", HttpStatus.INTERNAL_SERVER_ERROR);// 404-NOT_FOUND
		}
		return resp;
	}

}
