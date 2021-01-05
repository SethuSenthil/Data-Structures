import java.util.*;
import java.io.*;

public class Bowling {

    public Bowling() {
        File name = new File("BowlingData.txt");
        ArrayList<Bowler> list = new ArrayList<Bowler>();
        try {
            BufferedReader input = new BufferedReader(new FileReader(name));
            String text;
            while ((text = input.readLine()) != null) {
                String[] array = text.split(" ");
                String first = array[0];
                String last = array[1];
                int score = Integer.parseInt(array[2]);
                list.add(new Bowler(first, last, score));
            }

            TreeMap<Integer, PriorityQueue<Bowler>> map = new TreeMap<>();
            BComparator bcomparator = new BComparator();

            for (int i = 0; i < list.size(); i++) {
                int score = list.get(i).getScore();
                PriorityQueue<Bowler> temp = new PriorityQueue<>(bcomparator);

                for (int j = 0; j < list.size(); j++) {
                    if (list.get(j).getScore() == score) {
                        temp.add(list.get(j));
                        map.put(score, temp);
                    }
                }
            }

            //dupe iterator
            Iterator it = map.keySet().iterator();
            Iterator it2 = map.keySet().iterator();
            Iterator it3 = map.keySet().iterator();
            Iterator it4 = map.keySet().iterator();

            System.out.print("{");
            while (it.hasNext()) {
                Object obj = it.next();
                System.out.print(obj + "=[");
                int increment = 0;
                for (Bowler bowler : map.get(obj)) {
                    increment++;
                    if (map.get(obj).size() == increment)
                        System.out.print(bowler.getFirst() + " " + bowler.getLast());
                    else
                        System.out.print(bowler.getFirst() + " " + bowler.getLast() + ", ");
                }
                System.out.print("]");
            }
            System.out.print("}");

            System.out.println(
                    " \n +++++++++++++++++++++++++++++++++++ \n +++++++++++++ENTRY SET+++++++++++++ \n +++++++++++++++++++++++++++++++++++");

            while (it2.hasNext()) {
                Object obj = it2.next();
                System.out.println(obj);
            }

            System.out.println(
                    " \n +++++++++++++++++++++++++++++++++++ \n +++++++++++++++KEYS++++++++++++++++ \n +++++++++++++++++++++++++++++++++++");

            while (it3.hasNext()) {
                Object obj = it3.next();
                System.out.print(obj + "=[");
                int increment = 0;

                for (Bowler bowler : map.get(obj)) {
                    increment++;

                    if (map.get(obj).size() == increment)
                        System.out.print(bowler.getFirst() + " " + bowler.getLast());
                    else
                        System.out.print(bowler.getFirst() + " " + bowler.getLast() + ", ");
                }
                System.out.println("]");
            }

            System.out.println(
                    " \n +++++++++++++++++++++++++++++++++++ \n ++++++++++++++VALUES+++++++++++++++ \n +++++++++++++++++++++++++++++++++++");

            while (it4.hasNext()) {
                Object obj = it4.next();
                System.out.print("[");

                int increment = 0;

                for (Bowler bowler : map.get(obj)) {
                    increment++;
                    if (map.get(obj).size() == increment)
                        System.out.print(bowler.getFirst() + " " + bowler.getLast());
                    else
                        System.out.print(bowler.getFirst() + " " + bowler.getLast() + ", ");

                }
                System.out.println("]");
            }

        } catch (IOException io) {
            System.err.println("Cannot Find File");
        }

    }

    public class Bowler {
        private String first;
        private String last;
        private int score;

        public Bowler(String first, String last, int score) {
            this.first = first;
            this.last = last;
            this.score = score;
        }

        public String getFirst() {
            return first;
        }

        public String getLast() {
            return last;
        }

        public int getScore() {
            return score;
        }

    }

    //Comparator Stuff
    public class BComparator implements Comparator<Bowler> {
        public int compare(Bowler a, Bowler b) {
            int comp = a.getLast().compareTo(b.getLast());

            if (comp != 0)
                return comp;
            else
                return a.getFirst().compareTo(b.getFirst());

        }
    }

    public static void main(String[] args) {
        Bowling app = new Bowling();
    }

}