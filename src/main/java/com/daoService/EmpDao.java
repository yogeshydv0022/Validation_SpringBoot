package com.daoService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.customException.EmployeeNotFoundException;
import com.model.Employee;
import com.repository.EmpRepository;

@Service
public class EmpDao {
	
	@Autowired
	private EmpRepository empRepository;

	// Create Employee
	public Employee create(Employee employee) {
		Employee emp = null;
		emp = empRepository.save(employee);
		return emp;
	}

	// find By id
	public Employee getById(int id) {
		Employee emp=null;
		 emp = empRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(
				new StringBuffer().append("Employee '").append(id).append("'not exist").toString()));
		return emp;
	}
	public Employee getSingleUser(int id) {
		Employee u = empRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(
				new StringBuffer().append("User '").append(id).append("'not exist").toString()));
		return u;
	}

	// find By id
	public Employee getByEmail(String email) {
		Employee emp = empRepository.findByEmail(email).orElseThrow(() -> new EmployeeNotFoundException(
				new StringBuffer().append("Employee '").append(email).append("'not exist").toString()));
		return emp;
	}

	// updateById
	public Employee updateuser(int id, Employee emp) {
		// Retrieve the existing entity from the database
		Employee e = empRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(
				new StringBuffer().append("Employee '").append(id).append("'not exist").toString()));

		// Update fields of the existing entity with the new values
		e.setName(emp.getName());
		e.setEmail(emp.getEmail());
		e.setAge(emp.getAge());
		e.setGraduationDate(emp.getGraduationDate());
		e.setAddress(emp.getAddress());
		e.setPassword(emp.getPassword());
		
		Employee updatedUser = empRepository.save(e);
		// Save the updated entity back to the database
		return updatedUser;
	}

	// updateByEmail
	public Employee updateByEmail(String email, Employee emp) {
		// Retrieve the existing entity from the database
		Employee e = empRepository.findByEmail(email).orElseThrow(() -> new EmployeeNotFoundException(
				new StringBuffer().append("Employee '").append(email).append("'not exist").toString()));

		// Update fields of the existing entity with the new values
		e.setName(emp.getName());
		e.setAge(emp.getAge());
		e.setGraduationDate(emp.getGraduationDate());
		e.setAddress(emp.getAddress());
		e.setPassword(emp.getPassword());
		Employee updatedUser = empRepository.save(e);
		// Save the updated entity back to the database
		return updatedUser;
	}

	// Delete UserbyId
	public void deleteById(int id) {
		Employee uid = getById(id);
		empRepository.delete(uid);
	}

	// Delete UserbyId
	public void deleteByEmail(String email) {
		Employee e = getByEmail(email);
		empRepository.delete(e);
	}

	// get All Employess
	public List<Employee> getAllEmployees() {
		List<Employee> list = empRepository.findAll();
		return list;
	}

}
