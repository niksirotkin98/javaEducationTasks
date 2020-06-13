package optionA;

public class Exercise5 {

	public static void main(String[] args) {
		if(args.length == 0) {
			System.out.println("No arguments passed");
			return;
		}
		try {
			long sum=0, mul=1;
			for(String s : args) {
				int i = Integer.valueOf(s);
				sum += i; mul *= i;
			}
			System.out.println("Argument's sum:"+sum+"\nArgument's multiplication:"+mul);
		} catch(Exception e) {
			System.out.println("Invalid argument: "+e);
		}
	}

}
