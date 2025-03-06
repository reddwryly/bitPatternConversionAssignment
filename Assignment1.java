import java.util.Scanner;

public class Assignment1 {
    
    private double absDecimal;
    private int splitInt;
    private double splitDecimal;
    private String binary;
    private String sign;
    private String significand;
    private String exponent;

    public Assignment1(double originalDecimal) {
        this.absDecimal = Math.abs(originalDecimal);
        this.splitInt = (int) Math.floor(absDecimal);
        this.splitDecimal = absDecimal - splitInt;
        this.binary = "";
        this.sign = (originalDecimal < 0) ? "1" : "0";
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
    public String getSignificand() {
        String significand = "";
        String stringBinary = String.valueOf(binary);
        String[] parts = stringBinary.split("\\."); 
        String combinedParts = "";
        if (this.splitDecimal == 0) {
            combinedParts = parts[0];
        } else {
            combinedParts = parts[0] + parts[1];
        }

        Boolean oneFound = false; 

        for (int i=0; i < combinedParts.length() && significand.length() < 4; i++) {
            char bit = combinedParts.charAt(i);    
            if (oneFound) {
                significand += bit;
            }
            if (bit == '1') {
                oneFound = true;  
            }
        }
         
        while (significand.length() < 4) {
            significand += "0";
        }

        this.significand = significand;
        return this.significand;
    }

    //method to get exponent
    public String getExponent() {
        String stringBinary = String.valueOf(binary);
        String[] parts = stringBinary.split("\\."); 
        Integer counter = 0;
        Boolean oneFound = false;

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
                if (!oneFound) {
                    counter -= 1;
                }
                if (bit == '1') {
                    oneFound = true;  
                }
            } 
        }
        
        int number = counter + 3;
        String stringExponent = "";

        while (number > 0 && stringExponent.length() < 4) {
            int remainder = number % 2;
            number = number / 2;
            stringExponent = remainder + stringExponent; 
        }

        while (stringExponent.length() < 3) {
            stringExponent = "0" + stringExponent;
        }

        this.exponent = stringExponent;
        return this.exponent;
    }

    //normailze method combine significant and exponent into scientific notation
    public String normalize() {
        String stringBinary = String.valueOf(binary);
        String[] parts = stringBinary.split("\\.");
        int decimalExponent = Integer.parseInt(getExponent(), 2);
        if (Integer.valueOf(parts[0]) > 0) {
            return "1." + getSignificand() + " * 2^" + decimalExponent;
        } else {
            return "1." + getSignificand() + " * 2^-" + decimalExponent;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Boolean run = true;
        while (run) {
            System.out.println("1 convert to binary");
            System.out.println("2 normalize binary number");
            System.out.println("3 convert the exbonent to excess 3");
            System.out.println("4 Show the sign, significand, and exponent");
            System.out.println("Choose an option enter 1, 2, 3, or 4: ");
            System.out.println("Enter Y to exit");
            String userChosenTask = scanner.nextLine();
            if (userChosenTask.equals("Y")) {
                run = false;
            }
            if (userChosenTask.equals("1") || userChosenTask.equals("2") || userChosenTask.equals("3") || userChosenTask.equals("4")) {
                System.out.println("Enter a decimal number:");
                String userDecimalInput = scanner.nextLine(); 
                Double userDecimalInputReal = 0.0;
                try {
                    userDecimalInputReal = Double.parseDouble(userDecimalInput);
                } catch (NumberFormatException e){
                    System.out.println("Invalid input. Not a double.");
                    System.exit(0);
                }
                if (((userDecimalInputReal >= -31 && userDecimalInputReal <= -0.125)||(userDecimalInputReal >= 0.125 && userDecimalInputReal <= 31))) {
                    Assignment1 x = new Assignment1(userDecimalInputReal);
                    x.convertBinary();
                    if (userChosenTask.equals("1")) {
                        System.out.println("Binary: " + ((x.sign.equals("1")) ? "-"+x.convertBinary() : x.convertBinary()));
                    } else if (userChosenTask.equals("2")) {
                        System.out.println("normalized: " + ((x.sign.equals("1")) ? "-"+x.normalize() : x.normalize()));
                    } else if (userChosenTask.equals("3")) {
                        System.out.println("Excess 3 Exponent: " + x.getExponent());
                    } else if (userChosenTask.equals("4")) {
                        System.out.println("1. Sign: " + x.sign + "\n2. Significand: " + x.getSignificand() + "\n3. Exponent: " + x.getExponent());
                    }
                } else {
                    System.out.println("Invalid input. Out of range.");
                }
            } else if (userChosenTask.equals("Y")) {
                System.out.println("You have exited");
            } else {
                System.out.println("Invalid input. Not a number 1-4");
            }
        }
        scanner.close();
    }
}