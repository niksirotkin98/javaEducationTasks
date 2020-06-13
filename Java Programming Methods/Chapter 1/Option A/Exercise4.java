import java.util.Scanner;

public class Exercise4 {

	public static void main(String[] args) {
		final String password = "password";
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter password:");
		if(sc.next().indexOf(password) != -1)
			System.out.println("Valid password");
		else
			System.out.println("Invalid password");
		sc.close();
	}

}
