
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FileTemplate {

    public static void main(String[] args) {
        File f = new File("example.txt");
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String nextLine;
            int tripNum = 1;
            while((nextLine = br.readLine()) != null) {
                Calendar c = Calendar.getInstance();
                Calendar c1 = Calendar.getInstance();
                String[] s = nextLine.split(" ");

                int day = Integer.parseInt(s[0]);
                int hr = Integer.parseInt(s[0]);
                int min = Integer.parseInt(s[0]);

                c.add(Calendar.DAY_OF_MONTH, day);
                c.add(Calendar.HOUR_OF_DAY, hr);
                c.add(Calendar.MINUTE, min);


                System.out.println("\nTrip: " + tripNum);
                System.out.println();
                SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");
                SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm aa");

                System.out.println("Departure Date and Time: " + timeFormatter.format(c1.getTime()) +" "+ dateFormatter.format(c1.getTime()));
                System.out.println();
                System.out.println("Arrival Date and Time: " + timeFormatter.format(c.getTime()) +" "+ dateFormatter.format(c.getTime()));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}