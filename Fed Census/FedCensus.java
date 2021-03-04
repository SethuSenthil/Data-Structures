import java.util.*;
import java.io.*;

public class FedCensus {
    ArrayList<Citizen> listOfCitizens;

    public class Citizen implements Comparable<Citizen> {

        private String first;
        private String last;
        private String streetName;
        private int streetNumber;
        private String relation;
        private String rentOwn;
        private double valueProp;
        private String gender;
        private double age;
        private String maritalStatus;
        private int ageFirstMarriage;
        private boolean attendedSchool;
        private boolean canRead;
        private String birthPlace;
        private String fathersBirthPlace;
        private String mothersBirthPlace;
        private String mothertongue;
        private int yearImmigrated;
        private String occupation;
        private String industry;
        private String transcibedRemarks;

        public Citizen(String first, String last, String streetName, String streetNumber, String relation,
                String rentOwn, String valueProp, String gender, String age, String maritalStatus,
                String ageFirstMarriage, String attendedSchool, String canRead, String birthPlace,
                String fathersBirthPlace, String mothersBirthPlace, String mothertongue, String yearImmigrated,
                String occupation, String industry, String transcibedRemarks) {
            this.first = first;
            this.last = last;
            this.streetName = streetName;

            try {
                this.streetNumber = Integer.parseInt(streetNumber);
            } catch (NumberFormatException e) {
                this.streetNumber = -1;
            }

            this.relation = relation;
            this.rentOwn = rentOwn.substring(0, 1);
            if (valueProp.charAt(0) == '$')
                valueProp = valueProp.substring(1);
            try {
                this.valueProp = Double.parseDouble(valueProp);
            } catch (NumberFormatException e) {
                if (valueProp.contains("/")) {

                    String whole = valueProp.substring(0, valueProp.indexOf(" "));
                    String number = valueProp.substring(valueProp.indexOf(" ") + 1, valueProp.indexOf("/"));
                    String denom = valueProp.substring(valueProp.indexOf("/") + 1);
                    this.valueProp = Double.parseDouble(whole) + Double.parseDouble(number) + Double.parseDouble(denom);

                }

            }

            this.gender = gender;

            try {
                this.age = Double.parseDouble(age);
            } catch (NumberFormatException e) {
                if (age.charAt(0) == '.' || age.equals("un"))
                    this.age = -1;
                else if (age.charAt(1) == ' ' && age.contains("/")) {
                    String whole = age.substring(0, age.indexOf(" "));
                    double dec;
                    if (age.substring(age.indexOf(" ") + 1, age.indexOf("/")).contains("*"))
                        dec = 0.5;
                    else {
                        String number = age.substring(age.indexOf(" ") + 1, age.indexOf("/"));
                        String denom = age.substring(age.indexOf("/") + 1);
                        dec = Double.parseDouble(number) / Double.parseDouble(denom);
                    }

                    this.age = Double.parseDouble(whole) + dec;
                } else if (age.contains("*")) {
                    this.age = Double.parseDouble(age.substring(0, age.indexOf("*")));
                } else {
                    String number = age.substring(0, age.indexOf("/"));
                    String denom = age.substring(age.indexOf("/") + 1);
                    this.age = Double.parseDouble(number) / Double.parseDouble(denom);
                }
            }

            this.maritalStatus = maritalStatus;
            try {
                this.ageFirstMarriage = Integer.parseInt(ageFirstMarriage);
            } catch (NumberFormatException e) {
                this.ageFirstMarriage = -1;
            }

            if (attendedSchool.equals("Yes")) {
                this.attendedSchool = true;
            } else {
                this.attendedSchool = false;
            }

            if (canRead.equals("Yes")) {
                this.canRead = true;
            } else {
                this.canRead = false;
            }

            this.birthPlace = birthPlace;
            this.fathersBirthPlace = fathersBirthPlace;
            this.mothersBirthPlace = mothersBirthPlace;
            this.mothertongue = mothertongue;

            try {
                this.yearImmigrated = Integer.parseInt(yearImmigrated);
            } catch (NumberFormatException e) {
                this.yearImmigrated = -1;
            }

            this.occupation = occupation.substring(0, 1).toUpperCase() + occupation.substring(1).toLowerCase();

            this.industry = industry;
            this.transcibedRemarks = transcibedRemarks;
        }

        public int compareTo(Citizen other) {
            if (getFirst().compareTo(other.getFirst()) < 0)
                return -1;
            if (getFirst().compareTo(other.getFirst()) > 0)
                return 1;

            if (getLast().compareTo(other.getLast()) < 0)
                return -1;
            if (getLast().compareTo(other.getLast()) > 0)
                return 1;

            if (getStreet().compareTo(other.getStreet()) < 0)
                return -1;
            if (getStreet().compareTo(other.getStreet()) > 0)
                return 1;

            if (getStreetNum() < other.getStreetNum())
                return -1;
            if (getStreetNum() > other.getStreetNum())
                return 1;

            if (getRelation().compareTo(other.getRelation()) < 0)
                return -1;

            if (getRelation().compareTo(other.getRelation()) < 0)
                return 1;

            if (getRentOrOwn().compareTo(other.getRentOrOwn()) < 0)
                return -1;
            if (getRentOrOwn().compareTo(other.getRentOrOwn()) > 0)
                return 1;

            if (getProperValue() < other.getProperValue())
                return -1;
            if (getProperValue() > other.getProperValue())
                return 1;

            if (getGender().compareTo(other.getGender()) < 0)
                return -1;
            if (getGender().compareTo(other.getGender()) > 0)
                return 1;

            if (getAge() < other.getAge())
                return -1;
            if (getAge() > other.getAge())
                return 1;

            if (getMaritalStatus().compareTo(other.getMaritalStatus()) < 0)
                return -1;
            if (getMaritalStatus().compareTo(other.getMaritalStatus()) > 0)
                return 1;

            if (getAgeFirstMarrage() < other.getAgeFirstMarrage())
                return -1;
            if (getAgeFirstMarrage() > other.getAgeFirstMarrage())
                return 1;

            if (didAttendSchool())
                return -1;

            if (!didAttendSchool())
                return 1;

            return 0;
        }

        private boolean didAttendSchool() {
            return attendedSchool;
        }

        private int getAgeFirstMarrage() {
            return ageFirstMarriage;
        }

        private String getMaritalStatus() {
            return maritalStatus;
        }

        private int getYearImmigrated() {
            return yearImmigrated;
        }

        private String getRelation() {
            return relation;
        }

        private int getStreetNum() {
            return streetNumber;
        }

        public String getLast() {
            return last;
        }

        public String getFirst() {
            return first;
        }

        public String getRentOrOwn() {
            return rentOwn;
        }

        public Double getProperValue() {
            return valueProp;
        }

        public String getGender() {
            return gender;
        }

        public String getRemarks() {
            return transcibedRemarks;
        }

        public String getStreet() {
            return streetName;
        }

        public String getBirthPlace() {
            return birthPlace;
        }

        public Double getAge() {
            return age;
        }

        public String toString() {
            return String.format("%-25sAge: %s", last + ", " + first, age);
        }

        public String getMotherTongue() {
            return mothertongue;
        }

        public String getOccupation() {
            return occupation;
        }

        public String getFathersBirthplace() {
            return fathersBirthPlace;
        }

    }

    public FedCensus() {
        listOfCitizens = new ArrayList<Citizen>();
        File fileName = new File("FedCensus1930_CambriaCountyPA.txt");

        try {
            BufferedReader input = new BufferedReader(new FileReader(fileName));
            String text;
            while ((text = input.readLine()) != null) {
                if (text.length() > 2 && text.substring(0, 2).equals("17")) {
                    String first = text.substring(71, 88).trim();
                    String last = text.substring(55, 71).trim();
                    String streetName = text.substring(55, 71).trim();
                    String streetNumber = text.substring(20, 36).trim();
                    String relation = text.substring(88, 108).trim();
                    String rentOwn = text.substring(108, 133).trim();
                    String valueProp = text.substring(113, 121).trim();
                    String gender = text.substring(133, 134).trim();
                    String age = text.substring(143, 151).trim();
                    String maritalStatus = text.substring(151, 162).trim();
                    String ageFirstMarriage = text.substring(156, 162).trim();
                    String attendedSchool = text.substring(162, 167);
                    String canRead = text.substring(167, 173).trim();
                    String birthPlace = text.substring(173, 190).trim();
                    String fathersBirthPlace = text.substring(190, 207).trim();
                    String mothersBirthPlace = text.substring(207, 224).trim();
                    String mothertongue = text.substring(224, 235).trim();
                    String yearImmigrated = text.substring(235, 241).trim();
                    String occupation = text.substring(252, 274).trim();
                    String industry = text.substring(274, 303).trim();
                    String transcibedRemarks = text.substring(342).trim();

                    listOfCitizens.add(new Citizen(first, last, streetName, streetNumber, relation, rentOwn, valueProp,
                            gender, age, maritalStatus, ageFirstMarriage, attendedSchool, canRead, birthPlace,
                            fathersBirthPlace, mothersBirthPlace, mothertongue, yearImmigrated, occupation, industry,
                            transcibedRemarks));
                }
            }
        } catch (IOException io) {
            System.err.println("File err");
        }

        Collections.sort(listOfCitizens);

        for (Citizen c : listOfCitizens)
            System.out.println(c);

        for (int x = listOfCitizens.size() - 1; x >= 0; x--) {
            String last = listOfCitizens.get(x).getLast();
            String first = listOfCitizens.get(x).getFirst();
            if (last.equals(".") && first.equals("."))
                listOfCitizens.remove(x);
        }
        streetCitizen();
        lineOfStars();
        birthPlaceAge();
        lineOfStars();
        motherTongueName();
        lineOfStars();
        occupationFatherBirthplace();
        lineOfStars();
        genderRemarks();
        lineOfStars();
        rentOwnValues();
        lineOfStars();
        yearsPeopleImmigrated();

    }

    public void lineOfStars() {
        System.out.println("********************************************************************");
    }

    public void rentOwnValues() {
        TreeMap<String, TreeSet<Double>> rentOwnValuesMap = new TreeMap<>();
        for (Citizen c : listOfCitizens) {
            if (!rentOwnValuesMap.containsKey(c.getRentOrOwn()))
                rentOwnValuesMap.put(c.getRentOrOwn(), new TreeSet<Double>());
            rentOwnValuesMap.get(c.getRentOrOwn()).add(c.getProperValue());
        }
        Iterator<String> it = rentOwnValuesMap.keySet().iterator();

        while (it.hasNext()) {
            String rentOwn = it.next();
            System.out.println(rentOwn + ":");
            TreeSet<Double> temp = rentOwnValuesMap.get(rentOwn);
            for (Double num : temp)
                System.out.println("\t" + num);
        }
    }

    public void genderRemarks() {
        TreeMap<String, HashSet<String>> genderRemarksMap = new TreeMap();
        for (Citizen c : listOfCitizens) {
            if (!genderRemarksMap.containsKey(c.getGender()))
                genderRemarksMap.put(c.getGender(), new HashSet<String>());
            genderRemarksMap.get(c.getGender()).add(c.getRemarks());
        }

        Iterator<String> it = genderRemarksMap.keySet().iterator();

        while (it.hasNext()) {
            String gender = it.next();
            System.out.println(gender + ":");
            HashSet<String> temp = genderRemarksMap.get(gender);
            for (String st : temp)
                System.out.println("\t" + st);
        }
    }

    public void streetCitizen() {
        TreeMap<String, TreeSet<Citizen>> streetCitizenMap = new TreeMap<>();

        for (Citizen c : listOfCitizens) {
            if (!streetCitizenMap.containsKey(c.getStreet()))
                streetCitizenMap.put(c.getStreet(), new TreeSet<Citizen>());
            streetCitizenMap.get(c.getStreet()).add(c);
        }

        Iterator<String> it = streetCitizenMap.keySet().iterator();

        while (it.hasNext()) {
            String street = it.next();
            System.out.println(street + ":");
            TreeSet<Citizen> temp = streetCitizenMap.get(street);

            for (Citizen c : temp)
                System.out.println("\t" + c);
        }
    }

    public void birthPlaceAge() {
        TreeMap<String, PriorityQueue<Double>> birthplaceAgeMap = new TreeMap<>();
        for (Citizen c : listOfCitizens) {
            if (!birthplaceAgeMap.containsKey(c.getBirthPlace())) {
                birthplaceAgeMap.put(c.getBirthPlace(), new PriorityQueue<Double>());
            }
            birthplaceAgeMap.get(c.getBirthPlace()).add(c.getAge());
        }
        Iterator<String> it = birthplaceAgeMap.keySet().iterator();
        while (it.hasNext()) {
            String birthplace = it.next();
            System.out.println(birthplace + ":");
            PriorityQueue<Double> temp = birthplaceAgeMap.get(birthplace);
            System.out.println("[");
            while (!temp.isEmpty()) {
                double age = temp.poll();
                if (age >= 0) {
                    if (temp.peek() != null) {
                        System.out.print(age + ", ");
                    } else {
                        System.out.print(age);
                    }
                }
            }
            System.out.println("]");
        }
    }

    public void motherTongueName() {
        TreeMap<String, ArrayList<String>> motherTongueNameMap = new TreeMap<>();
        for (Citizen c : listOfCitizens) {
            if (!motherTongueNameMap.containsKey(c.getMotherTongue()))
                motherTongueNameMap.put(c.getMotherTongue(), new ArrayList<String>());
            motherTongueNameMap.get(c.getMotherTongue()).add(c.getFirst() + " " + c.getLast());
        }

        Iterator<String> it = motherTongueNameMap.keySet().iterator();

        while (it.hasNext()) {
            String motherTongue = it.next();
            System.out.println(motherTongue + ":");
            ArrayList<String> temp = motherTongueNameMap.get(motherTongue);
            for (String c : temp)
                System.out.println("\t" + c);
        }
    }

    public void occupationFatherBirthplace() {
        TreeMap<String, HashSet<String>> occupationFatherBirthplace = new TreeMap<>();

        for (Citizen c : listOfCitizens) {
            if (!occupationFatherBirthplace.containsKey(c.getOccupation()))
                occupationFatherBirthplace.put(c.getOccupation(), new HashSet<String>());
            occupationFatherBirthplace.get(c.getOccupation()).add(c.getFathersBirthplace());
        }

        Iterator<String> it = occupationFatherBirthplace.keySet().iterator();
        while (it.hasNext()) {
            String occupation = it.next();
            System.out.println(occupation + ";");
            HashSet<String> temp = occupationFatherBirthplace.get(occupation);
            Iterator<String> hashIt = temp.iterator();

            while (hashIt.hasNext())
                System.out.println("\t" + hashIt.next());
        }
    }

    public void yearsPeopleImmigrated() {
        TreeMap<Integer, HashSet<String>> yearsPeopleImmigrated = new TreeMap<>();

        for (Citizen c : listOfCitizens) {
            if (!yearsPeopleImmigrated.containsKey(c.getYearImmigrated()))
                yearsPeopleImmigrated.put(c.getYearImmigrated(), new HashSet<String>());
            yearsPeopleImmigrated.get(c.getYearImmigrated()).add(c.getFirst());
        }

        Iterator<Integer> it = yearsPeopleImmigrated.keySet().iterator();
        while (it.hasNext()) {
            int year = it.next();

            if (year == -1) {
                System.out.println("Unknown Year:");
            } else {
                System.out.println(year + ":");
            }

            HashSet<String> temp = yearsPeopleImmigrated.get(year);
            Iterator<String> hashIt = temp.iterator();

            while (hashIt.hasNext())
                System.out.println("\t" + hashIt.next());
        }
    }

    public static void main(String[] args) {
        FedCensus app = new FedCensus();
    }
}