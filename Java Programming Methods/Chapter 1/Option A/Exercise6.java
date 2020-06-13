package optionA;

import java.text.*;
import java.util.*;

public class Exercise6 {
	public static void main(String [] args) {
		DateFormat df = new SimpleDateFormat("dd MMM yyyy HH:mm");
		Calendar dateReception = new GregorianCalendar(2020,5,10,19,37);
		Calendar dateCompletion = new GregorianCalendar();
		System.out.println("-Junior Developer-");
		System.out.println("Full name: Nik Sirotkin");
		System.out.println("Task:");
		System.out.println("\t-reception:"+df.format(dateReception.getTime()));
		System.out.println("\t-completion:"+df.format(dateCompletion.getTime()));
	}
}
