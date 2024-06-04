package com.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.model.Employee;
@Repository
public interface EmpRepository extends JpaRepository<Employee,Integer>{

	Boolean existsByEmail(String email);
	
	//Optional<Employee> findByEmail(String email);
	
	@Query("SELECT e FROM Employee e WHERE e.email = :email")
	Optional<Employee> findByEmail(@Param("email") String email);

	// Inline parameter approach
	//@Query("SELECT e FROM Employee e WHERE e.email = ?1")
	//Employee DeleteByEmail(String email);
}
