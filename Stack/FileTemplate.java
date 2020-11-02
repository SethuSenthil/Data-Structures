//Wheres Ahsoka Tano??!!
import java.io.*;
import java.util.Stack;

public class FileTemplate {
    public class SWCharacter {
         String name;
         String birthYear;
         String gender;
         String homeWorld;
         String species;

        public SWCharacter(String name, String birthYear, String gender, String homeWorld, String species){
            this.name = name;
            this.birthYear = birthYear;
            this.gender = gender;
            this.homeWorld = homeWorld;
            this.species = species;
        }

    }


    public void intToBinary(int ogNum){  //part 1
        Stack<Integer> binaryStack = new Stack<Integer>();

        int num = ogNum;
        while (num != 0)
        {
            int num2 = num % 2;
            binaryStack.push(num2);
            num /= 2;
        }
        System.out.print("\n" + ogNum + " in binary is ");
        while (!binaryStack.isEmpty()){
            System.out.print(binaryStack.pop());
        }
        System.out.println();
    }

    public FileTemplate() {
        File fileName = new File("example.csv");
        //intToBinary(1039);


        try {
            //read file
            BufferedReader input = new BufferedReader(new FileReader(fileName));
            String text;

            text=input.readLine(); //skip meta data

            //init Stacks
            Stack<SWCharacter> male = new Stack<SWCharacter>();
            Stack<SWCharacter> female = new Stack<SWCharacter>();
            Stack<SWCharacter> droids = new Stack<SWCharacter>();
            Stack<SWCharacter> validBirth = new Stack<SWCharacter>();

            //parse file
            while((text=input.readLine()) != null){
                int offset = 0;
                if(text.contains("\"")){
                    offset++;
                }
                String[] s = text.split(",");
                SWCharacter thisPerson = new SWCharacter(s[0], s[5 + offset], s[6 + offset], s[7 + offset], s[8 + offset]);

               if(!thisPerson.birthYear.equals("NA"))
                  validBirth.push(thisPerson);

              if(thisPerson.gender.contains("female")){
                 female.push(thisPerson);
              }else if(thisPerson.gender.contains("male")){
                  male.push(thisPerson);
              }else{
                droids.push(thisPerson);
            }


            }

            String formatLiteral = "%1$-30s %2$-30s";

           System.out.println("Male Characters");
           System.out.println(String.format(formatLiteral, "Name" ,"Homeworld"));
            while (!male.isEmpty()){
                SWCharacter maleChar = male.pop();
                System.out.println(String.format(formatLiteral, maleChar.name , maleChar.homeWorld.contains("NA") ? "Unknown" : maleChar.homeWorld));
            }

            System.out.println("\n");


            System.out.println("Female Characters");
            System.out.println(String.format(formatLiteral, "Name" ,"Homeworld"));
             while (!female.isEmpty()){
                 SWCharacter maleChar = female.pop();
                 System.out.println(String.format(formatLiteral, maleChar.name , maleChar.homeWorld.contains("NA") ? "Unknown" : maleChar.homeWorld));
             }


             System.out.println("\n");


             System.out.println("Droids");
             System.out.println(String.format(formatLiteral, "Name" ,"Homeworld"));
              while (!droids.isEmpty()){
                  SWCharacter maleChar = droids.pop();
                  System.out.println(String.format(formatLiteral, maleChar.name , maleChar.homeWorld.contains("NA") ? "Unknown" : maleChar.homeWorld));
              }


              System.out.println("\n");


              System.out.println("Ages");
              System.out.println(String.format(formatLiteral + "%3$-30s", "Name" ,"Homeworld", "Birth Year(BBY)"));
               while (!validBirth.isEmpty()){
                   SWCharacter maleChar = validBirth.pop();
                   System.out.println(String.format(formatLiteral + "%3$-30s", maleChar.name , maleChar.homeWorld, Double.parseDouble(maleChar.birthYear.replace("BBY", ""))));
               }





        } catch (IOException e) {
            System.out.println("File Not Found");
        }
    }
    public static void main(String[] args){
        System.out.println("Running...");
        FileTemplate app = new FileTemplate();
    }
}