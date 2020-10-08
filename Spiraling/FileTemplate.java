import java.io.*;
import java.util.ArrayList;

public class FileTemplate {

    public FileTemplate() {
        File fileName = new File("example.txt");

        try {
            //read file
            BufferedReader input = new BufferedReader(new FileReader(fileName));
            String text;

            //parse file
            while((text=input.readLine()) != null){

                int spiralNum = Integer.parseInt(text);
                //System.out.println(spiralNum);
                String[][] grid = new String[spiralNum][spiralNum];


                 for (int i = 0; i < grid.length; i++) {
                     for (int j = 0; j < grid[i].length; j++) {
                         grid[i][j] = "-";
                     }
                 }

                 int startRow = 0;
                 int startCol = 0;
                 int endRow = Integer.parseInt(text) - 1;
                 int endCol = Integer.parseInt(text) - 1;

                 while(startRow  <= endRow && startCol <= endCol){

                    //right
                     for(int c = startCol; c <= endCol; c++){
                        grid[startRow][c] = "*";
                     }

                     startRow++;

                     //down

                     for(int r = startRow; r <= endRow; r++){
                         grid[r][endCol] = "*";
                     }
                     endCol -= 2;

                     for(int c = endCol; c >= startCol; c--){
                         grid[endRow][c] = "*";
                     }

                     for(int r = endRow; r >= startRow; r--){
                         grid[r][startCol] = "*";
                     }

                    startCol += 2;



                 }

                 for (int i = 0; i < grid.length; i++) {
                    for (int j = 0; j < grid[i].length; j++) {
                       System.out.print(grid[i][j]);
                    }
                    System.out.println();
                }

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