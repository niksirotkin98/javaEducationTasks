package optionA;

import java.util.Scanner;

public class Exercise3 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		try {
			System.out.print("Input count of random numbers:");
			final int randomCount = sc.nextInt();
			System.out.print("Use carriage return?[true/false]:");
			char carriageReturn = sc.nextBoolean()?(char)13:(char)32;
			for(int i=0; i<randomCount; i++)
				System.out.print(String.format("%.3f",Math.random())+carriageReturn);
		} catch(Exception e) {
			System.out.println("Invalid input: "+e);
		} finally {
			sc.close();
		}
	}

}
