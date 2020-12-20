import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.IntStream;

public class pad {

    private ArrayList<HashSet<Integer>> list;

    public pad() {
        list = new ArrayList<HashSet<Integer>>();

        int rand = (int) (Math.random() * 10) + 2;

        IntStream.range(0, rand).mapToObj(i -> new HashSet<Integer>()).forEach(hashSet -> {
            while (hashSet.size() < 20) {
                hashSet.add((int) (Math.random() * 60));
            }
            list.add(hashSet);
        });

        for (HashSet<Integer> hash : list) {
            System.out.println("Hash:" + hash);
        }

        System.out.println();

        ArrayList<HashSet<Integer>> intersectionlist = new ArrayList<HashSet<Integer>>();
        {
            int i = 0;
            while (i < list.size() - 1) {
                intersectionlist.add(Intersection(list.get(i), list.get(i + 1)));
                i += 2;
            }
        }

        switch (list.size() % 2) {
            case 0 -> {
                while (intersectionlist.size() > 1) {
                    intersectionlist.set(0, Intersection(intersectionlist.get(0), intersectionlist.get(1)));
                    intersectionlist.remove(1);
                }
                System.out.println("\nIntersectionOfAll: " + intersectionlist);
            }
            case 1 -> {
                while (intersectionlist.size() > 1) {
                    intersectionlist.set(0, Intersection(intersectionlist.get(0), intersectionlist.get(1)));
                    intersectionlist.remove(1);
                }

                System.out.println(
                        "\nIntersectionOfAll: " + Intersection(intersectionlist.get(0), list.get(list.size() - 1)));
            }
        }

        ArrayList<HashSet<Integer>> evenintersectionlist = new ArrayList<HashSet<Integer>>();
        {
            int i = 0;
            while (i < list.size() - 1) {
                evenintersectionlist.add(EvenIntersection(list.get(i), list.get(i + 1)));
                i += 2;
            }
        }

        switch (list.size() % 2) {

            case 0 -> {
                while (evenintersectionlist.size() > 1) {
                    evenintersectionlist.set(0,
                            EvenIntersection(evenintersectionlist.get(0), evenintersectionlist.get(1)));
                    evenintersectionlist.remove(1);
                }

                System.out.println("\nEvenIntersectionOfAll: " + evenintersectionlist);
            }

            case 1 -> {
                while (evenintersectionlist.size() > 1) {
                    evenintersectionlist.set(0,
                            EvenIntersection(evenintersectionlist.get(0), evenintersectionlist.get(1)));
                    evenintersectionlist.remove(1);
                }

                System.out.println("\nEvenIntersectionOfAll: "
                        + EvenIntersection(evenintersectionlist.get(0), list.get(list.size() - 1)));
            }
        }

        ArrayList<HashSet<Integer>> unionlist = new ArrayList<HashSet<Integer>>();
        {
            int i = 0;
            while (i < list.size() - 1) {
                unionlist.add(Union(list.get(i), list.get(i + 1)));
                i += 2;
            }
        }

        switch (list.size() % 2) {

            case 0 -> {
                while (unionlist.size() > 1) {
                    unionlist.set(0, Union(unionlist.get(0), unionlist.get(1)));
                    unionlist.remove(1);
                }

                System.out.println("\nUnionOfAll: " + unionlist);
            }

            case 1 -> {
                while (unionlist.size() > 1) {
                    unionlist.set(0, Union(unionlist.get(0), unionlist.get(1)));
                    unionlist.remove(1);
                }

                System.out.println("\nUnionOfAll: " + Union(unionlist.get(0), list.get(list.size() - 1)));
            }

        }

        ArrayList<HashSet<Integer>> evenunionlist = new ArrayList<HashSet<Integer>>();
        int i = 0;
        while (i < list.size() - 1) {
            evenunionlist.add(EvenUnion(list.get(i), list.get(i + 1)));
            i += 2;
        }

        switch (list.size() % 2) {
            case 0 -> {
                while (evenunionlist.size() > 1) {
                    evenunionlist.set(0, EvenUnion(evenunionlist.get(0), evenunionlist.get(1)));
                    evenunionlist.remove(1);
                }

                System.out.println("\nEvenUnionOfAll: " + evenunionlist);
            }
            case 1 -> {
                while (evenunionlist.size() > 1) {
                    evenunionlist.set(0, EvenUnion(evenunionlist.get(0), evenunionlist.get(1)));
                    evenunionlist.remove(1);
                }

                System.out.println("\nEvenUnionOfAll: " + EvenUnion(evenunionlist.get(0), list.get(list.size() - 1)));
            }
        }
    }

    public HashSet<Integer> Intersection(HashSet<Integer> a, HashSet<Integer> b) {

        HashSet<Integer> intersection = new HashSet<Integer>(a);
        intersection.retainAll(b);
        return intersection;

    }

    public HashSet<Integer> EvenIntersection(HashSet<Integer> a, HashSet<Integer> b) {
        HashSet<Integer> intersect = Intersection(a, b);

        HashSet<Integer> evenintersect = new HashSet<Integer>();

        for (Integer i : intersect) {
            if (i % 2 == 0)
                evenintersect.add(i);
        }
        return evenintersect;
    }

    public HashSet<Integer> Union(HashSet<Integer> a, HashSet<Integer> b) {
        HashSet<Integer> union = new HashSet<Integer>();
        HashSet<Integer> intersect = Intersection(a, b);

        for (Integer num : a) {
            union.add(num);
        }

        for (Integer x : b) {
            union.add(x);
        }

        HashSet<Integer> finalunion = new HashSet<Integer>();
        for (Integer num : union) {
            if (!intersect.contains(num))
                finalunion.add(num);
        }
        return finalunion;
    }

    public HashSet<Integer> EvenUnion(HashSet<Integer> a, HashSet<Integer> b) {

        HashSet<Integer> union = Union(a, b);
        HashSet<Integer> evenUnion = new HashSet<Integer>();

        for (Integer num : union) {
            if (num % 2 == 0)
                evenUnion.add(num);
        }
        return evenUnion;
    }

    public static void main(String[] args) {

        pad app = new pad(); //run

    }

}