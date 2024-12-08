package com.techpalle.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import com.techpalle.bean.Admin;
import com.techpalle.bean.Employee;


@WebServlet("/")
public class MyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	     doPost(request,response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path =request.getServletPath();
		switch(path) {
		case "/validateadmin" :validateAdmin(request,response);
		break;
		case "/registeradmin":registerAdmin(request,response);
		break;
		case"/openemployeeform":openEmployeeForm(request,response);
		break;
		case"/add":addEmployee(request,response);
		break;
		case"/home":homePage(request,response);
		break;
		case"/deleteemployee":deleteEmployee(request,response);
		break;
		case"/getdatafromtable":getDataFromTable(request,response);
		break;
		case"/update":update(request,response);
		}
	}
	private void update(HttpServletRequest request, HttpServletResponse response) {
		SessionFactory sf=new Configuration().configure().buildSessionFactory();
		Session s=sf.openSession();
		Transaction t= s.beginTransaction();
		
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("txtName");
		int salary = Integer.parseInt(request.getParameter("txtSalary"));
		
		Employee e1 = s.get(Employee.class, id);
		e1.setName(name);
		e1.setSalary(salary);
		s.update(e1);
		
		t.commit();
		
		try {
			response.sendRedirect("home");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	private void getDataFromTable(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id"));
		
		SessionFactory sf=new Configuration().configure().buildSessionFactory();
		Session s=sf.openSession();
		Transaction t= s.beginTransaction();
		Employee e1 = s.get(Employee.class, id);
		
		request.setAttribute("employeedata", e1);
		RequestDispatcher rd=request.getRequestDispatcher("register.jsp");
		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		t.commit();
		try {
			response.sendRedirect("home");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	//delete from employee where id = ?
	private void deleteEmployee(HttpServletRequest request, HttpServletResponse response) {
		SessionFactory sf=new Configuration().configure().buildSessionFactory();
		Session s=sf.openSession();
		Transaction t= s.beginTransaction();
		int id = Integer.parseInt(request.getParameter("id"));
		Employee e1=s.get(Employee.class, id);
		s.delete(e1);
		t.commit();
		try {
			response.sendRedirect("home");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	private void homePage(HttpServletRequest request, HttpServletResponse response) {
		SessionFactory sf=new Configuration().configure().buildSessionFactory();
		Session s=sf.openSession();
		Transaction t= s.beginTransaction();
		
		Criteria c=s.createCriteria(Employee.class);
		//c.add(Restrictions.<use methods for filtering the records from database)
		List<Employee> employee_list=c.list();
		request.setAttribute("employeelist", employee_list);
		RequestDispatcher rd=request.getRequestDispatcher("home.jsp");
		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	private void openEmployeeForm(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.sendRedirect("register.jsp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	private void registerAdmin(HttpServletRequest request, HttpServletResponse response) {
		String name =request.getParameter("txtName");
		String email =request.getParameter("txtEmail");
		String password =request.getParameter("txtPassword");
		
		Admin a= new Admin(name, email, password);
		
		SessionFactory sf= new Configuration().configure().buildSessionFactory();
		Session s=sf.openSession();
		Transaction t=s.beginTransaction();
		s.save(a);
		t.commit();
		s.close();
		sf.close();
		try {
			response.sendRedirect("adminlogin.jsp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void validateAdmin(HttpServletRequest request, HttpServletResponse response) {
		SessionFactory sf= new Configuration().configure().buildSessionFactory();
		Session s=sf.openSession();
		Transaction t= s.beginTransaction();
		//get() will retrieve the recording from database one by one
//		Admin a = s.get(Admin.class, 1);
		//Creteria interface is a used to fetch all objects from db
		Criteria c= s.createCriteria(Admin.class);//depprecated method
		List<Admin> l = c.list();
		
		Criteria c1=s.createCriteria(Employee.class);
		List<Employee> e1=c1.list();
		request.setAttribute("employeelist", e1);
		
		String email = request.getParameter("txtEmail");
		String password = request.getParameter("txtPassword");
		boolean isDataPresent = false;
		for(Admin a :l)
		{
			if(email.equals(a.getEmail()) && password.equals(a.getPassword())) {
				isDataPresent = true;
				break;
			}
		
		}
		if(isDataPresent)
		{
			RequestDispatcher rd= request.getRequestDispatcher("home.jsp");
			try {
				rd.forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		else
		{
			request.setAttribute("message", "Invalid Admin");
			RequestDispatcher rd= request.getRequestDispatcher("adminlogin.jsp");
		}
		
	}

	private void addEmployee(HttpServletRequest request, HttpServletResponse response) {
		String name = request.getParameter("txtName");
		int salary = Integer.parseInt(request.getParameter("txtSalary"));
		
		Employee e1= new Employee(name, salary);
		
		//create session
		SessionFactory sf= new Configuration().configure().buildSessionFactory();
		
		//open session
		Session s= sf.openSession();
		
		//Begin the transaction
		Transaction t= s.beginTransaction();		
		//send the query
		s.save(e1);
		
		//commit the transaction
		t.commit();
		
		//close the session
		s.close();
		
		//destroy session
		sf.close();
		
		try {
			response.sendRedirect("register.jsp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
		
		
