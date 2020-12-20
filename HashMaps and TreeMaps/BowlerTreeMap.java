import java.io.*;
import java.util.TreeMap;
import java.util.*;
import java.util.Map.*;

public class BowlerTreeMap {
    public static void main(String[] args) {
        File name = new File("BowlingData.txt");
        TreeMap<Integer, PriorityQueue<Bowler>> map = new TreeMap<Integer, PriorityQueue<Bowler>>();

        try {
            BufferedReader input = new BufferedReader(new FileReader(name));

            String text = "";
            while ((text = input.readLine()) != null) {
                String[] arr = text.split(" ");

                Bowler bowler = new Bowler(arr[0], arr[1], Integer.valueOf(arr[2]));
                //firstName, lastName, score

                if (!map.containsKey(bowler.getScore())) {
                    map.put(bowler.getScore(), new PriorityQueue<Bowler>());
                }
                map.get(bowler.getScore()).add(bowler);
            }
        } catch (IOException io) {
            System.err.println("File does not exist");
        }

        Set<Entry<Integer, PriorityQueue<Bowler>>> entrySet = map.entrySet();

        for (Entry<Integer, PriorityQueue<Bowler>> entry : entrySet) {

            int score = entry.getKey();
            System.out.print(score + ": ");
            PriorityQueue<Bowler> pq = entry.getValue();

            while (pq.peek() != null) {
                System.out.print(pq.poll() + "; ");
            }

            System.out.println();
        }
    }
}