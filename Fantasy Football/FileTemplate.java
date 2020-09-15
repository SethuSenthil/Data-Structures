import java.io.*;

public class FileTemplate {

    public FileTemplate() {
        File fileName = new File("example.txt");

        try {
            //read file
            BufferedReader input = new BufferedReader(new FileReader(fileName));
            String text;

            //parse file
            while((text=input.readLine()) != null){

                System.out.println(text);

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