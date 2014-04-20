package org.exception.kostin;

/**
 * @author roman
 * @see http://kostin.ws/java/java-exceptions.html
 */
public class Main2 { 
	public static void main(String[] args) { 
		int a = 4; 
		try { 
			System.out.println(a/0); 
		} catch (ArithmeticException e) { 
			System.out.println("Произошла недопустимая арифметическая операция"); 
		} 
	} 
}