import java.io.*;

public class Template {
    public Template() {
        File fileName = new File("text.txt");
        try {
            BufferedReader input = new BufferedReader(new FileReader(fileName));
            String text;
            int r = 0;
            String[][] output = null;
            int[][] helpers = {
                  { 29, 24, 19, 14, 10, 5 }, { 28, 23, 18, 13, 9, 4 }, {
                       27, 22, 17, 12, 8, 3 },{ 26, 21, 16, 11, 7, 2 },
                       { 25, 20, 15, 10, 6, 1 } };
            String[] n = { "G#", "G", "F#", "F", "E", "D#", "D", "C#", "C#", "B", "A#", "A", "G#", "G", "F#", "F", "E",
                    "D#", "D", "C#", "C", "B", "A#", "A", "G#", "G", "F#", "F", "E" };
            while ((text = input.readLine()) != null) {
                String[] str = text.split(",");

                if (output == null) {
                    output = new String[30][str.length + 1];
                    output[0][0] = "measures";

                    for (int i = 1; i <= str.length; i++){
                        output[0][i] = "" + i;
                    }

                    for (int i = 1; i <= n.length; i++){
                        output[i][0] = n[i - 1];
                    }
                }

                for (int i = 0; i < str.length; i++) {
                    for (int j = 0; j < 6; j++) {
                        if (str[i].charAt(j) == 'o' || str[i].charAt(j) == '*'){
                            output[helpers[r][j]][i + 1] = "O";
                        }
                    }
                }
                r++;
            }

            for (int i = 0; i < output.length; i++) {
                for (int j = 0; j < output[0].length; j++) {
                    if (output[i][j] == null){
                        System.out.print("\t");
                    }
                    else{
                        System.out.print(output[i][j] + "\t");
                    }
                }
                System.out.println();
            }

        } catch (IOException e) {
            System.out.println("Error: File not found.");
        }
    }
    public static void main(String[] args) {
        Template app = new Template();
    }
}