import java.io.*;

public class FileTemplate {

    public FileTemplate() {
        File fileName = new File("example.txt");

        try {
            BufferedReader input = new BufferedReader(new FileReader(fileName));
            String text;
            while((text=input.readLine()) != null){
                System.out.println(text);
            }

        } catch (IOException e) {
            System.out.println("file not found");
        }
    }
    public static void main(String[] args){
        System.out.println("Hello");
        FileTemplate app= new FileTemplate();
    }
}