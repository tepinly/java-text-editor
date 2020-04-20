import java.util.Scanner;

/*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*
 * WARNING: CHANGE PARTITION PATH FOR 'location' & 'location2' IN THE MANAGER CLASS ACCORDING TO YOUR PC
 *~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*/

public class MainClass {
    static Manager manage = new Manager();
    static Encryptor enc = new Encryptor();
    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        String n = "";
        while(!n.equals("8")) {
            System.out.println("\n~Main Menu\n~Current file path: |" + manage.getLocation(1) + manage.getFileName() +"\n" +
            "\n1.Change folder path\n2.Select/Create text file\n3.Read file content" +
            "\n4.Edit file\n5.Copy file\n6.Compress file\n7.Encryption menu\n8.Exit\n");
            n = scan.nextLine();
            switch(n) {
                case("1"): case1();
                break;
                case("2"): case2();
                break;
                case("3"): case3();
                break;
                case("4"): editMenu();
                break;
                case("5"): case5();
                break;
                case("6"): case6();
                break;
                case("7"): encryptorMenu();
                break;
            }
        }
        scan.close();
    }

    public static void encryptorMenu() {
        if(check()) return;
        String n = "";
        while(!n.equals("7")) {
            System.out.println("\n~Encryption Menu:\n~Current file path: |" + manage.getLocation(1) + manage.getFileName() +"\n" + 
            "\n1.Encrypt file\n2.Decrypt file\n3.Modify number of shifts (current: " + enc.getN() + ")\n" +
            "4.Modify value of each shift (current: " + enc.getS() + ")\n5.Encrypt using a keyword\n" + 
            "6.Decrypt using a keyword\n7.Back to main menu\n");
            n = scan.nextLine();
            switch(n) {
                case("1"): case11();
                break;
                case("2"): case12();
                break;
                case("3"): case13();
                break;
                case("4"): case14();
                break;
                case("5"): case15();
                break;
                case("6"): case16();
                break;
            }
        }
    }

    public static void editMenu() {
        if(check()) return;
        String n = "";
        loop: while(!n.equals("8")) {
            System.out.println("\n~Edit Menu\n~Current file path: |" + manage.getLocation(1) + manage.getFileName() +"\n" + 
            "\n1.Add text to file\n2.Replace text in file\n3.Replace certain repeated text in file" +
            "\n4.Clear file\n5.Rename file\n6.Move file\n7.Delete file\n8.Back to main menu\n");
            n = scan.nextLine();
            switch(n) {
                case("1"): case21();
                break;
                case("2"): case22();
                break;
                case("3"): case23();
                break;
                case("4"): case24();
                break;
                case("5"): case25();
                break;
                case("6"): case26();
                break;
                case("7"): case27();
                break loop;
            }
        }
    }

    /** Check if the given file path exists */
    public static boolean check() {
        if(manage.getFileName().equals("")) {
            System.out.println("No primary file selected, select/create one through option 2 in the main menu.");
            return true;
        }
        return false;
    }

    /** Check if the file is empty */
    public static boolean fileEmpty() {
        if(enc.getInput().equals("")) {
            System.out.println("File is empty.");
            return true;
        }
        return false;
    }

    //Folder path
    public static void case1() {
        System.out.print("Enter folder path| ");
        String s = scan.next();
        manage.locate(s, 1);
    }

    //File name
    public static void case2() {
        System.out.print("Enter file name: ");
        String s = scan.next();
        manage.setFileName(s);
    }

    //Read
    public static void case3() {
        if(check()) return;
        if(fileEmpty()) return;
        System.out.println("\n~");
        manage.display();
        System.out.println("~");
    }

    //Copy
    public static void case5() {
        if(check()) return;
        System.out.print("~Enter the folder path| ");
        String x = scan.nextLine();
        System.out.print("~Enter the file name you want to copy your content to: ");
        String y = scan.nextLine();
        if(manage.check(y).equals(manage.getFileName())) {
            System.out.println("Can't copy the file to itself!");
            return;
        }
        System.out.println("\n~Choose the copying method:\n1.Full copy\n2.Copy half of the file\n3.Copy letters only\n4.Copy vowels only\n");
        int n = scan.nextInt();
        System.out.println("");
        manage.copy(x, y, n);
        System.out.println("Content copied.");
    }

    //Compress
    public static void case6() {
        manage.copy(manage.getLocation(1), manage.getFileName(), 5);
        System.out.println("File compressed.");
    }

    //Encrypt
    public static void case11() {
        enc.setInput(manage.read());
        if(fileEmpty()) return;
        String mainFile = manage.getFileName();
        String newFile = manage.rawName(manage.getFileName())+"_encrypted";
        manage.create(newFile, 2);
        manage.copy(manage.getLocation(2), newFile, 1);
        manage.setFileName(newFile);
        manage.rewrite(enc.encrypt(1));
        manage.setFileName(mainFile);
        System.out.println("Encrypted copy created.");
    }

    //Decrypt
    public static void case12() {
        enc.setInput(manage.read());
        if(fileEmpty()) return;
        String mainFile = manage.getFileName();
        String newFile = manage.rawName(manage.getFileName())+"_decrypted";
        manage.create(newFile, 2);
        manage.copy(manage.getLocation(2), newFile, 1);
        manage.setFileName(newFile);
        manage.rewrite(enc.encrypt(2));
        manage.setFileName(mainFile);
        System.out.println("Decrypted copy created.");
    }

    //Shifts number
    public static void case13() {
        System.out.print("\n~Enter number of shifts: ");
        enc.setN(scan.nextInt());
    }

    //Shift value
    public static void case14() {
        System.out.print("\n~Enter the value per shift: ");
        enc.setS(scan.nextInt());
    }

    //Encrypt with keyword
    public static void case15() {
        enc.setInput(manage.read());
        if(fileEmpty()) return;
        System.out.print("\n~Enter the keyword: ");
        String y=  scan.nextLine();
        enc.setKey(y);
        String mainFile = manage.getFileName();
        String newFile = manage.rawName(manage.getFileName())+"_encrypted";
        manage.create(newFile, 2);
        manage.copy(manage.getLocation(2), newFile, 1);
        manage.setFileName(newFile);
        manage.rewrite(enc.encryptKey(1));
        manage.setFileName(mainFile);
        System.out.println("Encrypted copy created.");
    }

    //Decrypt with keyword
    public static void case16() {
        enc.setInput(manage.read());
        if(fileEmpty()) return;
        System.out.print("\n~Enter the keyword: ");
        String y=  scan.nextLine();
        enc.setKey(y);
        String mainFile = manage.getFileName();
        String newFile = manage.rawName(manage.getFileName())+"_decrypted";
        manage.create(newFile, 2);
        manage.copy(manage.getLocation(2), newFile, 1);
        manage.setFileName(newFile);
        manage.rewrite(enc.encryptKey(2));
        manage.setFileName(mainFile);
        System.out.println("Decrypted copy created.");
    }

    //Add text
    public static void case21() {
        System.out.println("\n~Enter 'stop' to stop adding text");
        String x = "";
        while(true) {
            x = scan.nextLine();
            if (x.equalsIgnoreCase("stop")) break;
            manage.write(x);
        }
    }

    //Replace first
    public static void case22() {
        System.out.print("\n~Enter text you want to replace: ");
        String x = "";
        x = scan.nextLine();
        System.out.print("\n~Enter new text: ");
        String s = "";
        s = scan.nextLine();
        manage.edit(x, s, 1);
        System.out.println("\nText replaced.");
    }

    //Replace All
    public static void case23() {
        System.out.print("\n~Enter text want to replace: ");
        String x = "";
        x = scan.nextLine();
        System.out.print("\n~Enter new text: ");
        String s = "";
        s = scan.nextLine();
        manage.edit(x, s, 2);
        System.out.println("\nText replaced.");
    }

    //clear
    public static void case24() {
        manage.clear();
        System.out.println("\nFile cleared.");
    }

    //rename
    public static void case25() {
        System.out.print("\n~Enter the new file name: ");
        String x = "";
        x = scan.nextLine();
        manage.rename(x, 1);
        System.out.println("\n'" + manage.getFileName() + "' renamed to '" + manage.check(x) + "'");
        manage.setFileName(x);
    }

    //move
    public static void case26() {
        System.out.print("\n~Enter the new folder path| ");
        String x = "";
        x = scan.nextLine();
        manage.rename(x, 2);
        System.out.println("\n'" + manage.getFileName() + "' moved to '" + manage.getLocation(2) + "'");
        manage.locate(x, 1);
    }

    //delete
    public static void case27() {
        manage.delete();
        System.out.println("File deleted.");
    }
}