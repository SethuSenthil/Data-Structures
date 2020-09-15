//Sethu Senthil
import java.io.*;
import java.math.BigInteger;


public class FileTemplate {

    public FileTemplate() {
        File fileName = new File("example.txt");

        System.out.println("Input Sample: \t Output Sample:");

        try {
            //read text file
            BufferedReader input = new BufferedReader(new FileReader(fileName));
            String text;

            //parse text file
            while((text=input.readLine()) != null){

                ///init variables
                BigInteger firstNum = BigInteger.TWO;
                BigInteger secondNum = BigInteger.ONE;
                BigInteger getSub = new BigInteger(text);
                getSub = getSub.subtract(BigInteger.ONE);

                System.out.print(text + "\t \t  ");


                //Find nth digit
                for (BigInteger i = getSub; i.compareTo(BigInteger.ZERO) > 0; i = i.subtract(BigInteger.ONE) ) {

                    BigInteger sum = firstNum.add(secondNum);
                    firstNum = secondNum;
                    secondNum = sum;

                }

                System.out.println(secondNum);

            }

        } catch (IOException e) {
            System.out.println("file not found");
        }
    }
    public static void main(String[] args){
        System.out.println("Running...");
        FileTemplate app = new FileTemplate();
    }
}