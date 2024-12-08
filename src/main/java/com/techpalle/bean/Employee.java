package com.techpalle.bean;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity//which class to be mapped with database

public class Employee {
	@Id // specifies which column contains primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name="EmployeeName")
	private String name;
	private int salary;
	
	public Employee() {
		super();
	}
		
	public Employee(String name, int salary) {
		this.name = name;
		this.salary = salary;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}

	public Employee(int id,String name, int salary) {
		this.id = id;
		this.name = name;
		this.salary = salary;
	}
	
	
	

}
