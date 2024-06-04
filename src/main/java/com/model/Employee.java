package com.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Emp1")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotBlank(message = "Username must not be empty")//empty or null check
	@Size(min = 3, max = 20, message = "Name must be from 3 to 20 characters !!")
	private String name;

	@NotEmpty(message = "Email is required !!")
	@Email(message = "The email is not a valid email !!")
	private String email;

	@NotNull(message = "age is required !!")
	@Min(value = 18, message = "Age must be equal or greater than 18")
	private int age;

	@NotNull(message = "Graduation date is required.")
	@Past(message = "Graduation date must be in the past.")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date graduationDate=new Date();

	@NotBlank(message = "Password is required.")
	@Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,15})", message = "Password must be 6 characters long and combination of uppercase letters, lowercase letters, numbers, special characters.")
	private String password;

	@NotBlank(message = "[Address] City is required.")
	private String address;


}
