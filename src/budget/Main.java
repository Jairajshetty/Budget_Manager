package budget;
import java.io.*;
import java.util.*;
public class Main {
    private static double income=0;
    private static Map<String,ArrayList<String>> purchases=new HashMap<>();
    private static Boolean exited=true;
    public static void main(String[] args) {
        // write your code here

        Scanner scanner=new Scanner(System.in);

        while (exited){
            printMenu();
            int choosenMenu=scanner.nextInt();
            System.out.println();
            switch (choosenMenu){
                case 1:AddIncome();
                break;
                case 2:AddPurchase();
                break;
                case 3:ShowPurchases();
                break;
                case 4:ShowBalance();
                break;
                case 5:SavePurchases();
                break;
                case 6:LoadPurchases();
                break;
                case 7:AnalysePurchases();
                break;
                case 0:ExitFunc();
                break;
                default:break;
            }
        }
    }
    public static void printMenu(){
        System.out.println("Choose your action:");
        System.out.println("1) Add income");
        System.out.println("2) Add purchase");
        System.out.println("3) Show list of purchases");
        System.out.println("4) Balance");
        System.out.println("5) Save");
        System.out.println("6) Load");
        System.out.println("7) Analyse (Sort)");
        System.out.println("0) Exit");
    }
    public static void DisplayPurchaseTypes(){
        System.out.println("Choose the type of purchase");
        System.out.println("1) Food");
        System.out.println("2) Clothes");
        System.out.println("3) Entertainment");
        System.out.println("4) Other");
        System.out.println("5) Back");
    }
    public static void DisplayPurchaseTypes1(){
        System.out.println("Choose the type of purchase");
        System.out.println("1) Food");
        System.out.println("2) Clothes");
        System.out.println("3) Entertainment");
        System.out.println("4) Other");
        System.out.println("5) All");
        System.out.println("6) Back");
    }
    public static void AddIncome(){
        Scanner scanner=new Scanner(System.in);
        System.out.println("Enter income:");
        double in= scanner.nextDouble();
        income+=in;
        System.out.println("Income was added!");
        System.out.println();
    }
    public static void AddPurchase(){
        Scanner scanner=new Scanner(System.in);
        boolean backCondition=true;
        while (backCondition){
            DisplayPurchaseTypes();
            int purchaseType=scanner.nextInt();
                switch (purchaseType){
                    case 1:addPurchases(purchaseType,"Food");
                        break;
                    case 2:addPurchases(purchaseType,"Clothes");
                        break;
                    case 3:addPurchases(purchaseType,"Entertainment");
                        break;
                    case 4:addPurchases(purchaseType,"Other");
                        break;
                    case 5:backCondition=false;
                    System.out.println();
                    break;
                    default:break;
                }
        }

    }
    public static void ShowPurchases(){
        Scanner scanner=new Scanner(System.in);
        if(isPurchasesEmpty()){
            System.out.println("Purchase list is empty");
        }else{
            boolean truth=true;
            while (truth){
                DisplayPurchaseTypes1();
                int pType=scanner.nextInt();
                System.out.println();
                switch (pType){
                    case 1:showPurchases(pType,"Food");
                    break;
                    case 2:showPurchases(pType,"Clothes");
                    break;
                    case 3:showPurchases(pType,"Entertainment");
                    break;
                    case 4:showPurchases(pType,"Other");
                    break;
                    case 5:showPurchases(pType,"All");
                    break;
                    case 6:truth=false;
                    System.out.println();
                    break;
                    default:break;
                }
            }


        }
        System.out.println();

    }
    public static void ShowBalance(){
        System.out.println("Balance: $"+income);
        System.out.println();
    }
    public static void ExitFunc(){
        System.out.println("Bye!");
        exited=false;
    }
    public static boolean isPurchasesEmpty(){
        boolean res=true;
        for (ArrayList<String> p:purchases.values()
             ) {
            if(p.size()!=0){
                res=false;
                break;
            }
        }
        return res;
    }

    public static void showPurchases(int pType,String category){
        System.out.println(category+":");
        if(category.equals("All")){
            if(isPurchasesEmpty()){
                System.out.println("Purchase list is empty");
            }else {
                double total = 0;
                for (String p : purchases.keySet()
                ) {
                    for (String s : purchases.get(p)
                    ) {
                        System.out.println(s);
                        total += Double.parseDouble(s.split("\\$")[s.split("\\$").length-1]);
                    }
                }
                System.out.println("Total sum: $" + total);
            }
        }
        else{
            if(purchases.get(category).size()==0){
                System.out.println("Purchase list is empty");
            }else {
                double total = 0;
                for (String p : purchases.get(category)) {
                    System.out.println(p);
                    total += Double.parseDouble(p.split("\\$")[p.split("\\$").length-1]);
                }

                System.out.println("Total sum: $" + total);
            }
        }
        System.out.println();
    }
    public static void addPurchases(int pType,String category){
        Scanner scanner=new Scanner(System.in);
        System.out.println();
        System.out.println("Enter purchase name:");
        String purchaseName=scanner.nextLine();
        System.out.println("Enter its price:");
        double price=scanner.nextDouble();
        income-=price;
        if(purchases.containsKey(category)){
            purchases.get(category).add(purchaseName+" $"+String.format("%.2f",price));
        }else{
            ArrayList<String> temp=new ArrayList<>();
            temp.add(purchaseName + " $" + String.format("%.2f",price));
            purchases.put(category,temp );
        }
        System.out.println("Purchase was added!");
        System.out.println();
    }
    public static void SavePurchases(){
        File file = new File("purchases.txt");
        try (PrintWriter printWriter = new PrintWriter(file)) {
            printWriter.println(income);
            for (String p : purchases.keySet()
            ) {
                printWriter.println(p+" "+purchases.get(p).size());
                for (String s : purchases.get(p)
                ) {
                    printWriter.println(s);
                }
            }
        } catch (IOException e) {
            System.out.printf("An exception occurs %s", e.getMessage());
        }
        System.out.println("Purchases were saved!");
        System.out.println();
    }
    public static void LoadPurchases(){
        File file = new File("purchases.txt");

        try (Scanner scanner = new Scanner(file)) {
            income=Double.parseDouble(scanner.nextLine());
            while (scanner.hasNext()) {
                String[] KeyValuePair=scanner.nextLine().split(" ");
                String key=KeyValuePair[0];
                int numOfValues=Integer.parseInt(KeyValuePair[1]);
                ArrayList<String> temp=new ArrayList<>();
                for(int i=0;i<numOfValues;i++){
                    temp.add(scanner.nextLine());
                }
                purchases.put(key,temp);
            }
        } catch (FileNotFoundException e) {
            System.out.println("No file found: " + "pathToFile");
        }

        System.out.println("Purchases were loaded!");
        System.out.println();
    }
    public static void AnalysePurchases(){
        Scanner scanner=new Scanner(System.in);
        boolean truth=true;
        while (truth){
            PrintSortOptions();
            int sortOption=scanner.nextInt();
            System.out.println();
            switch (sortOption){
                case 1:SortAllPurchases();
                break;
                case 2:SortByType();
                break;
                case 3:SortCertainType();
                break;
                case 4: truth=false;
                break;
                default:break;
            }
        }
    }
    public static void PrintSortOptions(){
        System.out.println("How do you want to sort?");
        System.out.println("1) Sort all purchases");
        System.out.println("2) Sort by type");
        System.out.println("3) Sort certain type");
        System.out.println("4) Back");
    }

    public static void SortAllPurchases(){
        if(isPurchasesEmpty()){
            System.out.println("Purchase list is empty!");
        }else{
            System.out.println("All:");
            ArrayList<String> allPurchases=new ArrayList<>();
            for (String p : purchases.keySet()
            ) {
                for (String s : purchases.get(p)
                ) {
                    allPurchases.add(s);
                }
            }
            SortAscending(allPurchases);
            System.out.println("Total: $" + String.format("%.2f",calculateTotal()));
        }
        System.out.println();
    }
    public static void SortByType(){
        System.out.println("Types:");
        ArrayList<String> allPurchases=new ArrayList<>();
        if(isPurchasesEmpty()){
                allPurchases.add("Food"+" - $"+0);
                allPurchases.add("Entertainment"+" - $"+0);
                allPurchases.add("Clothes"+" - $"+0);
                allPurchases.add("Other"+" - $"+0);
            for (String s:allPurchases
                 ) {
                System.out.println(s);
            }
        }else{
            for (String p : purchases.keySet()
            ) {
                double total=0;
                for (String s : purchases.get(p)
                ) {
                    total += Double.parseDouble(s.split("\\$")[s.split("\\$").length-1]);
                }
                allPurchases.add(p+" - $"+String.format("%.2f",total));

            }
            SortAscending(allPurchases);
        }


        System.out.println("Total sum: $" + String.format("%.2f",calculateTotal()));
        System.out.println();
    }

    public static void SortCertainType(){
        Scanner scanner=new Scanner(System.in);
        System.out.println("Choose the type of purchase");
        System.out.println("1) Food");
        System.out.println("2) Clothes");
        System.out.println("3) Entertainment");
        System.out.println("4) Other");
        int TypeOptionSelected=scanner.nextInt();
        System.out.println();
        switch (TypeOptionSelected){
            case 1:sortByType("Food");
            break;
            case 2:sortByType("Clothes");
            break;
            case 3:sortByType("Entertainment");
            break;
            case 4:sortByType("Other");
            break;
            default:break;

        }
    }

    public static void SortAscending(ArrayList<String> allPurchases){
        int allPurchasesLength=allPurchases.size();
        String[] tempAarrList=new String[allPurchasesLength];

        for(int i=0;i<allPurchasesLength;i++){
            tempAarrList[i]=allPurchases.get(i);
        }

        for(int i=0;i<allPurchasesLength-1;i++){
            for(int j=0;j<allPurchasesLength-i-1;j++){
                if(Double.parseDouble(tempAarrList[j].split("\\$")[tempAarrList[j].split("\\$").length-1])<Double.parseDouble(tempAarrList[j+1].split("\\$")[tempAarrList[j+1].split("\\$").length-1])){
                    String temp=tempAarrList[j+1];
                    tempAarrList[j+1]=tempAarrList[j];
                    tempAarrList[j]=temp;
                }
            }
        }
        for (String a:
                tempAarrList) {
            System.out.println(a);
        }


    }
    public static Double calculateTotal(){
        double total=0;
        for (String p : purchases.keySet()
        ) {
            for (String s : purchases.get(p)
            ) {
                total += Double.parseDouble(s.split("\\$")[s.split("\\$").length-1]);
            }
        }
        return total;
    }

    public static Double calculateTotalofCategory(String type){
        double total=0;

            for (String s : purchases.get(type)
            ) {
                total += Double.parseDouble(s.split("\\$")[s.split("\\$").length-1]);
            }
        return total;
    }

    public static void sortByType(String type){
        if(isPurchasesEmpty()||purchases.get(type).size()==0){
            System.out.println("Purchase list is empty!");
        }else{
            System.out.println(type+":");
            ArrayList<String> allPurchases=new ArrayList<>();
            for (String s : purchases.get(type)
            ) {
                allPurchases.add(s);
            }
            SortAscending(allPurchases);
            System.out.println("Total sum: $" + String.format("%.2f",calculateTotalofCategory(type)));

        }
        System.out.println();

    }
}
