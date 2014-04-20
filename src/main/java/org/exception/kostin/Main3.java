package org.exception.kostin;

import java.util.Scanner;

/**
 * @author roman
 * @see http://kostin.ws/java/java-exceptions.html
 */
public class Main3 { 
	public static void main(String[] args) { 
		System.out.println("----");
		int[] m = {-1,0,1}; 
		Scanner sc = new Scanner(System.in); 
		try { 
			int a = sc.nextInt();
			m[a] = 4/a; 
			System.out.println(m[a]); 
		} catch (ArithmeticException e) { 
			System.out.println("Произошла недопустимая арифметическая операция"); 
		} catch (ArrayIndexOutOfBoundsException e) { 
			System.out.println("Обращение по недопустимому индексу массива"); 
		} 
	} 
}