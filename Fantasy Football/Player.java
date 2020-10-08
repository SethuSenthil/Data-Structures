public class Player implements Comparable <Player>
{
    private final double average_pick;
    private final String name;
    private final String position;
    private final String team;
    private final int bye;
    private final double overall;
    private final double std_dev;
    private final double high_Rd;
    private final double low_Rd;
    private final int times_drafted;

    public Player(double pick, String n, String pos, String t, int off, double o, double dev, double hRd, double lRd, int d)
    {
        average_pick = pick;
        name = n;
        position = pos;
        team = t;
        bye = off;
        overall = o;
        std_dev = dev;
        high_Rd = hRd;
        low_Rd = lRd;
        times_drafted = d;
    }

    public double getAverage_pick()
    {
        return average_pick;
    }

    public String getName()
    {
        return name;
    }

    public String getPosition()
    {
        return position;
    }

    public String getTeam()
    {
        return team;
    }

    public int getBye()
    {
        return bye;
    }

    public double getOverall()
    {
        return overall;
    }

    public double getStd_dev()
    {
        return std_dev;
    }

    public double getHigh_Rd()
    {
        return high_Rd;
    }

    public double getLow_Rd()
    {
        return low_Rd;
    }

    public int getTimes_drafted()
    {
        return times_drafted;
    }

    public double getPickedPos()
    {
        int a = (int) average_pick;
        double rounds = average_pick;
        rounds -= a;
        return Math.round(rounds * 100.0);
    }
    public int compareTo(Player b)
    {
        int num1 = (int)average_pick;
        int num2 = (int)b.getAverage_pick();

        if(this.getPickedPos() < b.getPickedPos())
            return -1;
        if(this.getPickedPos() > b.getPickedPos())
            return 1;
         if(num1 < num2)
            return -1;
         if(num1 > num2)
            return 1;
         return 0;
    }

    public String toString()
    {
        return String.format("Name: %-25s%-5s%5s%8s%8s%8s", name,position, team,""+low_Rd,""+high_Rd,""+times_drafted,""+overall);
    }
}