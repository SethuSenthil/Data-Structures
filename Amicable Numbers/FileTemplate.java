//Sethu Senthil

import java.io.*;
import java.util.*;

public class FileTemplate {

    public void println(String str){
        //caus im lazy
       System.out.println(str);
    }

    public ArrayList findFactors(int num){
        ArrayList<Integer> factors = new ArrayList<Integer>();

        //finds all factors including itself
        for(int i = 1; i <= num; ++i) {
            if (num % i == 0) {
                factors.add(i);
            }
        }
        //removes the factor of itself from the list
        factors.remove(factors.size() - 1);
        return factors;
    }
    //adds an list of ints
    public int addNums(ArrayList<Integer> nums){
       int sum = 0;

       for (int num : nums) {
           sum += num;
       }

       return sum;
    }

    //converts a list into a sentence list without the oxford comma
    public String commaFormat(ArrayList<Integer> num){

        String result = "";
        for (int i = 0; i < num.size(); i++) {
            if(i != num.size() - 1){
              result += num.get(i) + ", ";

            }else{
              result = result.substring(0, result.length() - 1) + " and " + num.get(i); //remove oxford comma
            }

            if(result.indexOf("1, and") != -1)  //removes oxford comma if factors less than 3 numbers
            result = result.replace("1, and", "1 and");
        }
        return result;
    }

    public FileTemplate() {
        File fileName = new File("example.txt");

        try {
            //read file
            BufferedReader input = new BufferedReader(new FileReader(fileName));
            String text;
            while((text=input.readLine()) != null){
                //parse file
                String[] pieces = text.split(" ");
                int firstNum = Integer.parseInt(pieces[0]);
                int secondNum = Integer.parseInt(pieces[1]);

                ArrayList<Integer> factors = findFactors(firstNum);
                ArrayList<Integer> factors2 = findFactors(secondNum);

                int factorSum = addNums(factors);
                int factorSum2 = addNums(factors2);

                String template = "The numbers " + firstNum + " and " + secondNum;

                if((factorSum == secondNum) && (factorSum2 == firstNum)){
                    println(template + " are amicable.");
                }else{
                    println(template + " are not amicable.");
                }

                //printer
                 println("\t" + "Factors of " + firstNum + " are " + commaFormat(factors) + ". Sum is " + factorSum + ".");
                 println("\t" + "Factors of " + secondNum + " are " + commaFormat(factors2)  + ". Sum is " + factorSum2 + ".");
                 println("");
            }

        } catch (IOException e) {
            System.out.println("file not found");
        }
    }
    public static void main(String[] args){
        System.out.println("Yay! It actually might be working!");
        FileTemplate app = new FileTemplate();
    }
}