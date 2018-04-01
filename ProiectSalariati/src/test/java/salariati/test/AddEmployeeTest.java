package salariati.test;

import static org.junit.Assert.*;

import salariati.exception.EmployeeException;
import salariati.model.Employee;

import org.junit.Before;
import org.junit.Test;

import salariati.repository.implementations.EmployeeImpl;
import salariati.repository.interfaces.EmployeeRepositoryInterface;
import salariati.repository.mock.EmployeeMock;
import salariati.validator.EmployeeValidator;
import salariati.controller.EmployeeController;
import salariati.enumeration.DidacticFunction;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class AddEmployeeTest {

	private EmployeeRepositoryInterface employeeRepository;
	private EmployeeController controller;
	private EmployeeValidator employeeValidator;
	
	@Before
	public void setUp() throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(new File("employeeDB/test-employee.txt"));
        writer.print("");
        writer.close();

		employeeRepository = new EmployeeImpl("employeeDB/test-employee.txt");
		employeeValidator  = new EmployeeValidator();
		controller         = new EmployeeController(employeeRepository, employeeValidator);
	}
	
	@Test
	public void testRepositoryMock() {
		assertTrue(controller.getEmployeesList().isEmpty());
		assertEquals(0, controller.getEmployeesList().size());
	}
	
	@Test
	public void testAddValidEmployee() {
		Employee newEmployee = new Employee("ValidLastName", "1910509055057", DidacticFunction.ASISTENT, "3000");
		assertTrue(employeeValidator.isValid(newEmployee));
		String[] attrs = new String[4];
		attrs[0] = "ValidLastName";
		attrs[1] = "1910509055057";
		attrs[2] = "ASISTENT";
		attrs[3] = "3000";
		try {
			controller.addEmployee(attrs);
			assertEquals(1, controller.getEmployeesList().size());
			assertTrue(newEmployee.equals(controller.getEmployeesList().get(controller.getEmployeesList().size() - 1)));
		} catch (EmployeeException e) {
			e.printStackTrace();
			assertFalse("this employee should be valid",true);
		}
	}

}
