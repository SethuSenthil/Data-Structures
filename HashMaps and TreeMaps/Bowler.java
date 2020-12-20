public class Bowler implements Comparable<Bowler> {
    String lastName, firstName;
    int score;

    public Bowler(String firstName, String lastName, int score) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.score = score;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public int getScore() {
        return score;
    }

    public int compareTo(Bowler otherBowler) {
        if (lastName.compareTo(otherBowler.getLastName()) < 0)
            return -1;
        else if (lastName.compareTo(otherBowler.getLastName()) > 0)
            return 1;
        else {
            if (firstName.compareTo(otherBowler.getFirstName()) < 0)
                return -1;
            else if (firstName.compareTo(otherBowler.getFirstName()) > 0)
                return 1;
        }
        return 0;
    }

    public String toString() {
        return lastName + ", " + firstName + " - " + score;
    }
}