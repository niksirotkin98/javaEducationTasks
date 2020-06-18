package optionBExercise;

import java.util.*;

interface SortInterface{
    public boolean sort(int a, int b);
}

class OptionBExercise {

	public static void main(String [] args) {

        System.out.print("Enter numbers separated by space(' '):");
        String inp = (new Scanner(System.in)).nextLine();
        // Read input and cast from string to number: split string using regexp
        String arr[] = inp.split("[^-?\\d]+");
        int numbers[] = castStringToInt(arr);
        //check for numbers
        if(numbers.length == 0){
            System.out.println("No numbers entered.");
            return; //if there are no numbers - exit
        }

        // print needed information using printMethod(),
        // with the main argument passing array of numbers
        printEven(numbers);
        printOdd(numbers);
        printDividedByOr(numbers, 9, 3); // prints numbers divisible by either 3 or 9
        printDividedByAnd(numbers, 7, 5); // prints numbers divisible by either 5 and 7
        printSortedByDecreaseAbsolute(numbers);
        print3DigitNotMatch(numbers); // prints all three-digit numbers in decimal not the same digits
        // prints greatest common factor and least common multiple
        System.out.println("GCF and lCM: "+getGCFShort(numbers)+" - "+getLCMShort(numbers));
        printSimple(numbers);
        printSorted(numbers); // mean sorted by increase
        printSortedByDecrease(numbers);
        printSortedFrequency(numbers); // prints numbers in decreasing order of occurrence of numbers
        printHappy(numbers);
        printFibonacci(numbers);
        printPalindrome(numbers);
        printEqualHalfSumOfNeighboring(numbers); // ex: 1,2,3 - print only 2 cause (1+3)/2 == 2
        printPeriods(numbers); // divide array into pairs, then then derive a periodic fraction from this pair
        // pair(first became numerator, second became denominator - pair(a,b) - a/b
        for(int n : numbers) // draws a pascal triangle from the first positive number
            if(n > 0){
                pascalTriangle(n);
                break;
            }
    }

    static ArrayList<Integer> getDelimiters(int number){
        int i = Math.abs(number),
            del = 2,
            it = 1,
            minus = -1;
        ArrayList<Integer> delimiters = new ArrayList<Integer>();
        if(number == 0) return delimiters;
        delimiters.add(number/i);

        if(i % 2 == 0)
            while(i % 2 == 0){
                delimiters.add(2);
                i /= 2;
            }

        if(i % 3 == 0)
            while(i % 3 == 0){
                delimiters.add(3);
                i /= 3;
            }

        while(i != 1){
            while(i % del == 0){
                delimiters.add(del);
                i /= del;
            }
            del = 6*it + minus;
            if(minus < 0){
                minus = 1;
            } else {
                minus = -1;
                it++;
            }
        }
        return  delimiters;
    }
    static ArrayList<Integer> getDelimitersIntersection(ArrayList<Integer>[] delimitersArray){
        ArrayList<Integer> intersection = new ArrayList<Integer>(delimitersArray[0]), preIntersection;
        Iterator<Integer> currentIterator, delimitersNextIterator;
        int currentI, delimitersI;
        for(int i = 1; i < delimitersArray.length; i++){
            preIntersection = new ArrayList<Integer>();
            currentIterator = intersection.iterator();
            delimitersNextIterator = delimitersArray[i].iterator();
            currentI = currentIterator.next();
            delimitersI = delimitersNextIterator.next();
            while(true){
                if(currentI == delimitersI){
                    preIntersection.add(currentI);
                    if(!currentIterator.hasNext() || !delimitersNextIterator.hasNext())break;
                    currentI = currentIterator.next();
                    delimitersI = delimitersNextIterator.next();
                } else if(currentI > delimitersI){
                    if(!delimitersNextIterator.hasNext())break;
                    delimitersI = delimitersNextIterator.next();
                } else {
                    if(!currentIterator.hasNext())break;
                    currentI = currentIterator.next();
                }
            }
            intersection = preIntersection;
        }
        return intersection;
    }
    static ArrayList<Integer> getDelimitersUnion(ArrayList<Integer>[] delimitersArray){
        ArrayList<Integer> union = new ArrayList<Integer>(delimitersArray[0]);
        for(int i = 1; i < delimitersArray.length; i++){
            ArrayList<Integer> preIntersection = new ArrayList<Integer>();
            Iterator<Integer> currentIterator = union.iterator(), delimitersNextIterator = delimitersArray[i].iterator();
            int currentI = currentIterator.next(), delimitersI = delimitersNextIterator.next();
            while (true){
                if(currentI == delimitersI){
                    preIntersection.add(currentI);
                    if(!currentIterator.hasNext())break;
                    if(!delimitersNextIterator.hasNext())break;
                    currentI = currentIterator.next();
                    delimitersI = delimitersNextIterator.next();
                    continue;
                }
                if(currentI > delimitersI){
                    if(!delimitersNextIterator.hasNext())break;
                    delimitersI = delimitersNextIterator.next();
                    continue;
                }
                if(currentI < delimitersI){
                    if(!currentIterator.hasNext())break;
                    currentI = currentIterator.next();
                    continue;
                }
            }
            union = preIntersection;
        }
        return union;
    }
    static ArrayList<Integer>[] getDelimitersFromNumbers(int [] numbers){
        ArrayList<Integer>[] delimiters = new ArrayList[numbers.length];
        for(int i = 0; i < numbers.length; i++) delimiters[i] = getDelimiters(numbers[i]);
        return delimiters;
    }

    static HashMap<Integer, Integer> getDelimitersInHashMap(int number){
        int i = Math.abs(number),
                del = 2,
                it = 1,
                minus = -1;
        HashMap<Integer, Integer> delimiters = new HashMap<Integer, Integer>();
        delimiters.put(1,number/i);

        while(i != 1){
            while(i % del == 0){
                delimiters.put(del,delimiters.getOrDefault(del,0)+1);
                i /= del;
            }
            if(del == 2){
                del = 3;
            }else{
                del = 6*it + minus;
                if(minus < 0){
                    minus = 1;
                } else {
                    minus = -1;
                    it++;
                }
            }
        }
        return  delimiters;
    }
    static HashMap<Integer, Integer>[] getDelimitersFromNumbersInHashMap(int [] numbers){
        HashMap<Integer, Integer>[] delimiters = new HashMap[numbers.length];

        for(int i = 0; i < numbers.length; i++)
        	delimiters[i] = getDelimitersInHashMap(numbers[i]);

        return delimiters;
    }
    static HashMap<String, Integer> patternsMatch(String s, int length, int maxLength){
        String pattern, current, verifiable;
        int maxPatternLength = Math.min(s.length()/2 + 1,maxLength+1);
        HashMap<String, Integer> patterns = new HashMap<>();

        for(length = length; length < maxPatternLength; length++){
            int lastIndex = s.length() - length;

            for(int patternStartIndex = 0; patternStartIndex < lastIndex - length + 1; patternStartIndex++){
                current = s.substring(patternStartIndex,patternStartIndex+length);

                if(patterns.get(current) == null) {
                    patterns.put(current, 1);

                    for(int currentPatternIndex = patternStartIndex+length; currentPatternIndex < lastIndex + 1; currentPatternIndex+=length){
                        verifiable = s.substring(currentPatternIndex,currentPatternIndex+length);

                        if(verifiable.equals(current))
                            patterns.put(current,patterns.get(current)+1);
                    }
                }
            }
        }
        return patterns;
    }
    static HashMap<String, Integer> getPatterns(String s, int length, int maxLength, int minNumberOfEntries){
        HashMap<String, Integer> patterns = patternsMatch(s, length, maxLength);
        ArrayList<String> deleteNeeded = new ArrayList<>();

        for(String key : patterns.keySet()){
            if(patterns.get(key) < minNumberOfEntries) deleteNeeded.add(key);
        }

        for(String key : deleteNeeded) patterns.remove(key);

        return patterns;
    }

    static String getMinPeriod(HashMap<String, Integer> patterns, String period){
        int minKeyLength = 99, i = 99;
        String minKey = "";

        for(String key : patterns.keySet())
            minKeyLength = minKeyLength > key.length()? key.length(): minKeyLength;

        for(String key : patterns.keySet())
            if(key.length() == minKeyLength && i > period.indexOf(key)){
                i = period.indexOf(key);
                minKey = key;
            }
        return minKey;
    }
    static String getPeriod(double number){
        String full_s = String.valueOf(number),
                s = full_s.substring(full_s.indexOf(".")+1,full_s.length()-1),
                period = "";
        full_s = full_s.substring(0,full_s.indexOf(".")+1);

        HashMap<String, Integer> patterns = getPatterns(s, 1, 99, 2);
        ArrayList<String> longKeys = new ArrayList<>();
        int maxKeyLength = 0, minIndex = 99;

        for(String key : patterns.keySet()) 
        	maxKeyLength = key.length() > maxKeyLength? key.length():maxKeyLength;

        for(String key : patterns.keySet()) 
        	if(key.length() == maxKeyLength) longKeys.add(key);

        for(String key : longKeys) {
            int index = s.indexOf(key);
            if(minIndex > index){
                minIndex = index;
                period = key;
            }
        }


        patterns = getPatterns(period, 2, 99, 2);
        if(patterns.size()>0){
            period = getMinPeriod(patterns, period);
        }

        patterns = getPatterns(period, 1, 99, 2);
        if(patterns.size() != 0 && period.length() == 2)
            period = period.substring(0,1);

        String losted = s.replaceAll(period,"");

        if(period.length() == 0 || maxKeyLength == 1 || losted.length() > 3 || period.equals("0"))
            return "";
        else
            return full_s + s.substring(0,minIndex)+"("+period+")";
    }

    static float digitMatchFrequencyByOccurrences(int number){
        String digits[] = String.valueOf(number).split("");
        int frequency = 0, countOfDigits = 0,
                standardDigit[] = new int[10];

        for(int i = 0; i < digits.length; i++){
            standardDigit[Integer.parseInt(digits[i])]++;
        }

        for(int f : standardDigit) {
            frequency += f;
            countOfDigits += f == 0? 0: 1;
        }

        return (float)frequency/countOfDigits;
    }

    static long getLCM(int [] numbers){
        int gcf = getGCF(numbers);
        long mul = 1;

        for(int number : numbers)
        	mul *= number;

        return (int)mul/(int)Math.pow(gcf,numbers.length-1);
    }
    static long getLCMFrom2Numbers(long a, long b){
        if(a == 0 || b == 0) return 0;
        return Math.abs(a*b)/getGCFFrom2Numbers(a,b);
    }
    static long getLCMShort(int [] numbers){
        if(numbers.length == 1) return numbers[0];
        long lcm = getLCMFrom2Numbers(numbers[0], numbers[1]);

        for(int i = 2; i < numbers.length; i++)
            lcm = getLCMFrom2Numbers(lcm,numbers[i]);

        return lcm;
    }

    static int getMultiplierByDelimiters(ArrayList<Integer> delimiters){
        int multiplier = 1;

        for(int i=0;i<delimiters.size();multiplier *= delimiters.get(i), i++);

        return multiplier;
    }
    static int getGCF(int [] numbers){
        return getMultiplierByDelimiters(getDelimitersIntersection(getDelimitersFromNumbers(numbers)));
    }
    static int getGCFFrom2Numbers(long a, long b){
        a = Math.abs(a);
        b = Math.abs(b);
        long del = 1, min = a > b? b : a;

        if( a == 0 || b == 0) return 0;

        for(int i = 1; i <= min; i++)
            if((a % i == 0) && (b % i == 0)) del = i;

        return (int)del;
    }
    static int getGCFShort(int [] numbers){
        if(numbers.length == 1) return numbers[0];
        int gcf = getGCFFrom2Numbers(numbers[0], numbers[1]);

        for(int i = 2; i < numbers.length; gcf = getGCFFrom2Numbers(gcf,numbers[i]), i++);

        return gcf;
    }
    static int getGCF(ArrayList<Integer>[] delimiters){
        return getMultiplierByDelimiters(getDelimitersIntersection(delimiters));
    }
    static int reverseNumber(int number){
        int sign = Integer.signum(number);
        char snumber[] = String.valueOf(Math.abs(number)).toCharArray();
        String reversed = "";

        for(int i = 0 ; i < snumber.length; i++)
            reversed += snumber[snumber.length - i - 1];

        return sign*Integer.parseInt(reversed);
    }
    static int digitMatchFrequency(int number){
        String digits[] = String.valueOf(number).split("");
        int frequency = 0, countOfDigits = 0,
                standardDigit[] = new int[10];

        for(int i = 0; i < digits.length; i++)
            standardDigit[Integer.parseInt(digits[i])]++;

        for(int f : standardDigit) {
            frequency += f;
            countOfDigits += f == 0? 0: 1;
        }

        return frequency-countOfDigits;
    }

    static int[] castStringToInt(String [] arr){
        int numbers[] = new int[ arr.length ];

        for(int i = 0; i < numbers.length; i++)
        	numbers[i] = Integer.parseInt(arr[i]);

        return numbers;
    }
    static int[] getDigits(int number){
        String stringNumber[] = String.valueOf(number).split("");
        int digits[] = new int[stringNumber.length];

        for(int i = 0; i < stringNumber.length; i++)
        	digits[i] = Integer.parseInt(stringNumber[i]);

        return digits;
    }
    static int[] abs(int [] numbers){
        int absoluteNumbers[] = numbers.clone();

        for(int i=0; i < numbers.length; i++) 
        	absoluteNumbers[i] = Math.abs(absoluteNumbers[i]);

        return absoluteNumbers;
    }
    static int[] sort(int[] numbers, SortInterface comparator){
        int sorted[] = numbers.clone();

        for(int i = 0; i < sorted.length-1; i++){

            for(int j = 0; j < sorted.length-1-i; j++){
                if(comparator.sort(sorted[j],sorted[j+1])){
                    int k = sorted[j];
                    sorted[j] = sorted[j+1];
                    sorted[j+1] = k;
                }
            }
        }
        return sorted;
    }

    static boolean digitAccord(int number, int digitCount){
        number = (int) (Math.abs(number) / Math.pow(10,digitCount-1));

        return (number > 0)&&(number<10)? true: false;
    }
    static boolean digitMatch(int number){
        char digits[] = String.valueOf(number).toCharArray();

        for(int i = 0; i < digits.length; i++){
            for(int j = i+1; j < digits.length; j++)
            	if(digits[i] == digits[j]) return true;
        }

        return false;
    }
    static boolean simpleNumber(int number){
        if(getDelimiters(number).size() <= 2 ) return true;

        return false;
    }
    static boolean happyNumber(int number){
        if(number < 1) return false;
        if(number == 1) return true;
        int notHappy[] = {37,58,89,145,42,20,4,16,37};

        for(int n : notHappy)
        	if(n == number) return false;

        int sum = 0, digits[] = getDigits(number);

        for(int digit : digits)
            sum+=digit*digit;

        return happyNumber(sum);
    }
    static boolean fibonacciNumber(int number){
        int f0 = 1, f1 = 1, fn = 2, counter = 2;

        if(number < 0) return false;
        else if(number == 0) return true;
        else if(number == 1) return true;

        while (fn < number){
            fn = f0+f1;
            f0 = f1;
            f1 = fn;
        }

        if(fn == number) return true;
        return false;
    }
    static boolean equalReversed(int number){
        return number == reverseNumber(number)?true: false;
    }

    static void printEven(int [] numbers){
        System.out.print("Even numbers: ");

        for(int i : numbers)
            if(i % 2 == 0) System.out.print(i+" ");

        System.out.println();
        return;
    }
    static void printOdd(int [] numbers){
        System.out.print("Odd numbers: ");

        for(int i : numbers)
            if(i % 2 != 0) System.out.print(i+" ");

        System.out.println();
        return;
    }
    static void printDividedByOr(int [] numbers, int ... dividers){
        String sdiv = "";
        for (int d: dividers) sdiv += d + " ";
        System.out.print("Numbers divided by one of "+sdiv+": ");

        for(int i : numbers)
            for(int d : dividers)
                if(i % d == 0){
                    System.out.print(i+"(/"+d+") ");
                    break;
                }

        System.out.println();
        return;
    }
    static void printDividedByAnd(int [] numbers, int ... dividers){
        String sdiv = "";
        int mul = 1;

        for (int d: dividers) { sdiv += d + "*"; mul *= d; }

        sdiv = sdiv.substring(0,sdiv.length()-1);
        System.out.print("Numbers divided by one of "+sdiv+": ");

        for(int i : numbers)
            if(i % mul == 0)
                System.out.print(i+"(/"+sdiv+") ");

        System.out.println();
        return;
    }
    static void print3DigitNotMatch(int [] numbers){
        System.out.print("Three-digit numbers in which digits are not match: ");

        for(int n : numbers)
            if(digitAccord(n, 3) && !digitMatch(n)) System.out.print(n + " ");

        System.out.println();
    }
    static void printSimple(int numbers[]){
        System.out.print("Simple numbers: ");

        for(int n : numbers)
            if(simpleNumber(n)) System.out.print(n + " ");

        System.out.println();
    }
    static void printHappy(int numbers[]){
        System.out.print("Happy numbers: ");

        for(int n : numbers)
            if(happyNumber(n)) System.out.print(n + " ");

        System.out.println();
    }
    static void printFibonacci(int numbers[]){
        System.out.print("Fibonacci numbers: ");

        for(int n : numbers)
            if(fibonacciNumber(n)) System.out.print(n + " ");

        System.out.println();
    }
    static void printPalindrome(int numbers[]){
        System.out.print("Equal reversed numbers: ");

        for(int n : numbers)
            if(equalReversed(n)) System.out.print(n + " ");

        System.out.println();
    }
    static void printDelimitersFromNumbersInHashMap(int[] numbers){
        HashMap<Integer, Integer>[] delimiters = getDelimitersFromNumbersInHashMap(numbers);

        for(int i = 0; i < numbers.length; i++) System.out.println(numbers[i]+" -> "+delimiters[i]);
    }
    static void printSortedByDecreaseAbsolute(int [] numbers){
        int [] sorted = sort(numbers, (a,b)->Math.abs(a)<Math.abs(b));
        System.out.print("Reverse sorted absolute numbers: ");

        for(int n :sorted) System.out.print(n+" ");

        System.out.println();
    }
    static void printSortedByDecrease(int [] numbers){
        int [] sorted = sort(numbers, (a,b)->a<b);
        System.out.print("Sorted by decrease: ");

        for(int n :sorted) System.out.print(n+" ");

        System.out.println();
    }
    static void printSorted(int [] numbers){
        int [] sorted = sort(numbers, (a,b)->a>b);
        System.out.print("Sorted by increase: ");

        for(int n :sorted) System.out.print(n+" ");

        System.out.println();
    }
    static void printEqualHalfSumOfNeighboring(int numbers[]){
        System.out.print("Sum of neighboring numbers: ");

        if(numbers.length > 2) {
            for (int i = 1; i < numbers.length - 1; i++) {
                int sum = numbers[i - 1] + numbers[i + 1];
                if (numbers[i] == (float) sum / 2)
                    System.out.print(numbers[i] + ":(" + numbers[i - 1] + "+" + numbers[i + 1] + ")/2 -> "+sum+"/2\t");
            }
        } else System.out.print("not enough numbers");
        System.out.println();
    }
    static void printSortedFrequency(int [] numbers){
        int [] sorted = sort(numbers,
                (a,b)->digitMatchFrequency(Math.abs(a))<digitMatchFrequency(Math.abs(b)) );

        System.out.print("Sorted by frequency of digit occurrence: ");

        for(int n :sorted) System.out.print(n+"("+digitMatchFrequency(Math.abs(n))+") ");

        System.out.println();
    }
    static void printPeriods(int numbers[]){
        System.out.print("Periodic fraction: ");

        for(int i=1; i < numbers.length; i++)
            if(numbers[i-1]>0 && numbers[i]>0){
                String period = getPeriod(((double)numbers[i-1])/((double)numbers[i]));
                if(period.length() > 0)
                    System.out.print(numbers[i-1]+"/"+numbers[i]+" = "+period+" ");
            }
        System.out.println();
    }
    static void pascalTriangle(int number){
        number = number>20?20:number;
        System.out.println("Pascal triangle for "+number+": ");
        String out = "";
        int numbers[] = new int[number+2], i = 0;
        numbers[0] = 1;
        i = 1;

        for(; i <= number+1; i++){
            int newNumbers[] = new int[number+2];

            for(int j = 1; j < i + 1; j++)
                newNumbers[j] = numbers[j-1]+numbers[j];
            numbers = newNumbers;
            out = "";

            for(int tabs=1; tabs<(number+2-i); out+="\t",tabs++);

            for(int n : numbers)out+= n==0?"":"\t"+n+(n>=1000?"":"\t");

            System.out.println(out);
        }
    }
}
