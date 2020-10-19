import java.io.*;

public class FileTemplate {
    static String projectName = "Sprial";

    public FileTemplate() {
        File fileName = new File("example.txt");

        try {
            // read file
            BufferedReader input = new BufferedReader(new FileReader(fileName));
            String text;

            // parse file
            while ((text = input.readLine()) != null) {

                // init vars
                System.out.println("SprialNum: " + text);
                int sprilNum = Integer.parseInt(text);
                String[][] grid = new String[sprilNum][sprilNum];

                int sRow = 0;
                int sCol = sRow;

                int eRow = sprilNum - 1;
                int eCol = eRow;

                try {
                    // calc loops
                    for (int i = 0; i < grid.length; i++)
                        for (int j = 0; j < grid[0].length; j++)
                            grid[i][j] = "-";

                    while (sRow <= eRow && sCol <= eCol) {
                        for (int col = sCol; col <= eCol; col++)
                            grid[sRow][col] = "*";
                        sRow++;


                        if (sCol > 0)
                            sCol++;

                        for (int row = sRow; row <= eRow; row++)
                            grid[row][eCol] = "*";
                        eCol--;
                        sRow++;


                        for (int col = eCol; col >= sCol; col--)
                            grid[eRow][col] = "*";
                        eRow--;
                        eCol--;


                        for (int row = eRow; row >= sRow; row--)
                            grid[row][sCol] = "*";
                        sCol++;
                        eRow--;

                    }

                    if (sprilNum % 4 == 2)
                        grid[sprilNum / 2][(sprilNum / 2) - 1] = "-";

                    // print results
                    for (int i = 0; i < grid.length; i++) {
                        for (int j = 0; j < grid[0].length; j++)
                            System.out.print(grid[i][j] + " ");
                        System.out.println();
                    }

                } catch (NumberFormatException err) {
                    System.out.println(err);
                }
            }

        } catch (IOException e) {
            System.out.println("File Not Found");
        }
    }

    public static void main(String[] args) {
        System.out.println("Running " + projectName + " entry point...");
        FileTemplate app = new FileTemplate();
    }
}