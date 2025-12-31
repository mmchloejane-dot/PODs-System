import java.util.Scanner;
import java.util.regex.Pattern;

public class PODS {

    static final int MAX = 100;
    static String[] orgName = new String[MAX];
    static String[] category = new String[MAX];
    static String[] barangay = new String[MAX];
    static String[] leader = new String[MAX];
    static String[] contact = new String[MAX];

    static int count = 0;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        loadSampleData(); 

        int choice;
        do {
            showMenu();
            choice = getMenuChoice();

            switch (choice) {
                case 1 -> addOrganization();
                case 2 -> searchOrganizationByName();
                case 3 -> editOrganization();
                case 4 -> displayAllOrganizations();
                case 5 -> displayOrganizationsByBarangay();
                case 6 -> countOrganizationsByCategory();
                case 7 -> System.out.println("Program ended.");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 7);
    }

    static void showMenu() {
        System.out.println("\n=== PEOPLE ORGANIZATIONS DIRECTORY SYSTEM ===");
        System.out.println("1. Add People Organization");
        System.out.println("2. Search Organization by Name");
        System.out.println("3. Edit Organization Information");
        System.out.println("4. Display All Organizations");
        System.out.println("5. Display Organizations by Barangay");
        System.out.println("6. Count Organizations by Category");
        System.out.println("7. End");
        System.out.print("Enter choice: ");
    }

    static int getMenuChoice() {
        while (!sc.hasNextInt()) {
            sc.next();
            System.out.print("Enter a valid number: ");
        }
        int choice = sc.nextInt();
        sc.nextLine(); 
        return choice;
    }

    // ================= Add People Organization =================
    static void addOrganization() {
        if (count >= MAX) {
            System.out.println("Directory is full.");
            return;
        }

        System.out.print("Organization Name: ");
        String name = sc.nextLine();
        if (!validateName(name)) {
            System.out.println("Error: Invalid name format (min 3 letters).");
            return;
        }

        System.out.print("Category (Youth/Women/Senior/Environmental/Livelihood/Fisherfolk): ");
        String cat = sc.nextLine();
        if (!validateCategory(cat)) {
            System.out.println("Error: Invalid category.");
            return;
        }

        System.out.print("Barangay: ");
        String brgy = sc.nextLine();
        if (!validateBarangay(brgy)) {
            System.out.println("Error: Invalid barangay.");
            return;
        }

        System.out.print("Leader Name: ");
        leader[count] = sc.nextLine();

        System.out.print("Contact Number: ");
        contact[count] = sc.nextLine();

        orgName[count] = name;
        category[count] = cat;
        barangay[count] = brgy;
        
        count++;
        System.out.println("Organization successfully added.");
    }

    // ================= Search Organization by Name =================
    static void searchOrganizationByName() {
        System.out.print("Enter organization name to search: ");
        String key = sc.nextLine().toLowerCase();

        boolean found = false;
        for (int i = 0; i < count; i++) {
            // Case-insensitive search
            if (orgName[i].toLowerCase().contains(key)) {
                displayOne(i);
                found = true;
            }
        }
        if (!found) System.out.println("Organization not found.");
    }

    // ================= Edit Organization Information =================
    static void editOrganization() {
        System.out.print("Enter EXACT organization name to edit: ");
        String key = sc.nextLine();

        for (int i = 0; i < count; i++) {
            if (orgName[i].equalsIgnoreCase(key)) {
                System.out.println("--- Editing " + orgName[i] + " ---");
                
                System.out.print("New Leader: ");
                leader[i] = sc.nextLine();

                // Validation added to Edit to prevent corrupting data
                String newCat;
                do {
                    System.out.print("New Category: ");
                    newCat = sc.nextLine();
                } while (!validateCategory(newCat));
                category[i] = newCat;

                System.out.print("New Barangay: ");
                barangay[i] = sc.nextLine();

                System.out.print("New Contact: ");
                contact[i] = sc.nextLine();

                System.out.println("Record updated successfully.");
                return;
            }
        }
        System.out.println("Organization not found.");
    }

    // ================= Display All Organization =================
    static void displayAllOrganizations() {
        if (count == 0) {
            System.out.println("No records found.");
            return;
        }
        for (int i = 0; i < count; i++) {
            displayOne(i);
        }
    }

    // ================= Display Organization by Barangay =================
    static void displayOrganizationsByBarangay() {
        System.out.print("Enter barangay: ");
        String brgy = sc.nextLine();

        boolean found = false;
        for (int i = 0; i < count; i++) {
            // Uses equalsIgnoreCase to ensure "central" matches "Central"
            if (barangay[i].equalsIgnoreCase(brgy)) {
                displayOne(i);
                found = true;
            }
        }
        if (!found) System.out.println("No organizations found in this barangay.");
    }

    // ================= Count Organization by Category =================
    static void countOrganizationsByCategory() {
        System.out.print("Enter category: ");
        String cat = sc.nextLine();

        int total = 0;
        for (int i = 0; i < count; i++) {
            if (category[i].equalsIgnoreCase(cat)) {
                total++;
            }
        }
        System.out.println("Total organizations in " + cat + ": " + total);
    }

    static void displayOne(int i) {
        System.out.println("\n------------------------------");
        System.out.println("Org Name: " + orgName[i]);
        System.out.println("Leader  : " + leader[i]);
        System.out.println("Category: " + category[i]);
        System.out.println("Barangay: " + barangay[i]);
        System.out.println("Contact : " + contact[i]);
        System.out.println("------------------------------");
    }

    static boolean validateName(String name) {
        return Pattern.matches("[A-Za-z ]{3,}", name);
    }

    static boolean validateBarangay(String brgy) {
        return brgy.length() >= 3;
    }

    static boolean validateCategory(String cat) {
        String[] valid = {"Youth", "Women", "Senior", "Environmental", "Livelihood", "Fisherfolk"};
        for (String v : valid) {
            if (v.equalsIgnoreCase(cat)) return true;
        }
        return false;
    }

    static void loadSampleData() {
      String[][] data = {
        {"Mati Youth Environmental Volunteers","Juan Tamad","Environmental","Central","09123456789"},
        {"Lawigan Environmental Protectors","Ronald Perez","Environmental","Lawigan","09678912345"},
        {"Green Mati Environmental Advocates","Hannah Pie","Environmental","Central","09666777888"},
        {"Mati Coastal Environmental Watch","Daniel Padilla","Environmental","Dahican","09223334455"},
        {"Barangay Dahican Fisherfolk Association","Pedro Pendoco","Fisherfolk","Dahican","09234567891"},
        {"Barangay Matiao Fisherfolk Cooperative","Carlos Cruz","Fisherfolk","Matiao","09789123456"},
        {"Barangay Mamali Fisherfolk Union","Edgar Vaez","Fisherfolk","Mamali","09333444555"},
        {"Mati Fisherfolk Sustainable Network","Ben Perez","Fisherfolk","Dahican","09888999111"},
        {"Women of Barangay Bobon Group","Maria Lopez","Women","Bobon","09345678912"},
        {"Tam-isan Women Entrepreneurs","Liza Saltik","Women","Tam-isan","09891234567"},
        {"Barangay Taguibo Womens Association","Rosa Castillo","Women","Taguibo","09555666777"},
        {"Senior Citizens of Barangay Don Salvador","Andres Bonifacio","Senior","Don Salvador","09456789123"},
        {"Barangay Senior Care Group","Felis Navidad","Senior","Martinez","09111222333"},
        {"Senior Citizens of Barangay Lawigan","Tomas Aguilar","Senior","Lawigan","09777888999"},
        {"Mati Young Leaders Organization","Carla Strada","Youth","Central","09567891234"},
        {"Youth for Community Development Mati","Kevin Morales","Youth","Central","09912345678"},
        {"Youth Sports and Leadership Club","Mark Antonio","Youth","Bobon","09444555666"},
        {"Barangay Central Youth Council","Aaron Jhon Poe","Youth","Central","09112223344"},       
        {"Mati Livelihood Skills Association","Noel Avillar","Livelihood","Central","09222333444"},
        {"Womens Handicraft Livelihood Group","Angel Locsin","Livelihood","Matiao","09999111222"},
        };

        for (int i = 0; i < data.length; i++) {
            orgName[i] = data[i][0];
            leader[i] = data[i][1];
            category[i] = data[i][2];
            barangay[i] = data[i][3];
            contact[i] = data[i][4];
        }
        count = data.length;
    }
}