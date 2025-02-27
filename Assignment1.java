import java.util.Scanner;

public class Assignment1 {
    
    private double originalDecimal;
    private double absDecimal;
    private int splitInt;
    private double splitDecimal;
    private String binary;
    private String sign;
    private String significand;
    private int exponent;

    public Assignment1(double originalDecimal) {
        this.originalDecimal = originalDecimal; 
        this.absDecimal = Math.abs(originalDecimal);
        this.splitInt = (int) Math.floor(absDecimal);
        this.splitDecimal = absDecimal - splitInt;
        this.binary = "";
        this.sign = (absDecimal < 0) ? "1" : "0";
        this.significand = "";
    }

    //method to convert integer and decimal to binary and combine
    public double convertBinary() {
        int newInt = splitInt;
        int intBinary = 0;
        String decBinary = ""; 
        double newDec = splitDecimal;
        int counterInt = 0;
        int counterDec = 1;

        while (newInt > 0) {
            int remainder = newInt % 2;
            newInt = newInt / 2;
            intBinary += remainder * Math.pow(10, counterInt);
            counterInt += 1;
        }

        while (newDec > 0 && counterDec < 5) { 
            newDec = newDec *2; 
            int intValue = (int) Math.floor(newDec); 
            decBinary += (intValue == 1) ? 1 : 0; 
            newDec = newDec - intValue; 
            counterDec += 1;
        }
        
        this.binary = intBinary + "." + decBinary;
        return Double.parseDouble(this.binary);
    }

    //method to get significand
    public int getSignificand() {
        String significand = "";
        String stringBinary = String.valueOf(binary);
        String[] parts = stringBinary.split("\\."); // parts[0]=10100 parts[1]=0
        String combinedParts = parts[0] + parts[1];
        Boolean oneFound = false; 

        //for input 10100.0 should get 1.01000  

        for (int i=0; i < combinedParts.length() && significand.length() < 5; i++) {
            char bit = combinedParts.charAt(i);    
            if (oneFound) {
                significand += bit;
            }
            if (bit == '1') {
                oneFound = true;  
            }
        }

        this.significand = significand;
        return Integer.parseInt(significand);
    }

    //method to get exponent
    public int getExponent() {
        String exbonent = "";
        String stringBinary = String.valueOf(binary);
        String[] parts = stringBinary.split("\\."); // parts[0]=101 parts[1]=0101
        Integer counter = 0;
        Boolean oneFound = false;

        //for input 101.01 should get 1.0101
        if (Integer.valueOf(parts[0]) > 0) {
            for (int i=0; i < parts[0].length(); i++) {  
                char bit = parts[0].charAt(i);   
                if (oneFound) {
                    counter += 1;
                }
                if (bit == '1') {
                    oneFound = true;  
                }
            } 
        } else {
            for (int i=0; i < parts[1].length(); i++) {  
                char bit = parts[1].charAt(i);   
                if (oneFound) {
                    counter -= 1;
                }
                if (bit == '1') {
                    oneFound = true;  
                }
            } 
        }
        
        String stringExponent = "";
        int number = counter;
        int counter2 = 0;
        while (number > 0 && stringExponent.length() < 4) {
            int remainder = number % 2;
            number = number / 2;
            stringExponent = remainder + stringExponent; 
            counter2 += 1;
        }

        while (stringExponent.length() < 3) {
            stringExponent = "0" + stringExponent;
        }

        this.exponent = Integer.valueOf(stringExponent);
        return Integer.valueOf(this.exponent);
    }

    //ecxess three

    //normalize

    public static void main(String[] args) {
        System.out.println("1 convert to binary");
        System.out.println("2 normalize binary number");
        System.out.println("3 convert the exbonent to excess 3");
        System.out.println("4 Show the bit pattern (sign, significand, and exponent binary bits)");
        System.out.println("5 all");
        System.out.println("Choose an option enter 1, 2, 3, 4, or 5: ");
        Scanner menu = new Scanner(System.in);
        String userChosenTask = menu.nextLine();

        System.out.println("Enter a decimal number:");
        Scanner decimalInput = new Scanner(System.in);
        String userDecimalInput = decimalInput.nextLine(); 
        Double userDecimalInputReal = 0.0;
        try {
            userDecimalInputReal = Double.parseDouble(userDecimalInput);
        } catch (NumberFormatException e){
            System.out.println("Invalid input. Not a double.");
            System.exit(0);
        }
        if (((userDecimalInputReal >= -31 && userDecimalInputReal <= -0.125)||(userDecimalInputReal >= 0.125 && userDecimalInputReal <= 31))) {
            if (userChosenTask.equals("5")) {
                Assignment1 x = new Assignment1(userDecimalInputReal);
                System.out.println("Binary = " + x.convertBinary()); 
                System.out.println("sign: " + x.sign +"\nexponent: "+x.getExponent()+"\nsignificand: " +x.getSignificand());
            }
        } else {
            System.out.println("Invalid input. Out of range.");
        }

        menu.close();
        decimalInput.close();
    }
}