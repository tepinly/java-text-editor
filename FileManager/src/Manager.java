import java.io.*;
import java.nio.file.*;
import java.util.zip.*;
import java.util.regex.Pattern;

public class Manager {
    Pattern p = Pattern.compile("[\\w / - . \\\\]+\\.(jar|class|txt)", Pattern.CASE_INSENSITIVE);
    private String fileName = "";

    // WARNING: CHANGE PARTITION PATH FOR 'location' & 'location2' ACCORDING TO YOUR
    // PC PARTITION LETTERS
    private String location = "C:\\";
    private String location2 = "C:\\";
    // WARNING: CHANGE PARTITION PATH FOR 'location' & 'location2' ACCORDING TO YOUR
    // PC PARTITION LETTERS

    /*
     * ONE MORE WARNING BELOW, SCROLL DOWN | V
     */

    /** format the file with a '.txt' at the end */
    public String check(String x) {
        return x.matches(".+(.txt$)") ? x : x + ".txt";
    }

    /** check if the file exists within the given path */
    public boolean checkPath() {
        Path path = Paths.get(this.location + this.fileName);
        if (Files.notExists(path)) {
            System.out.println("File doesn't exist.");
            return true;
        }
        return false;
    }

    /**
     * Set fileName according to selected file
     * <p>
     * Create one with given name if it doesn't exist
     */
    public void setFileName(String x) {
        this.fileName = check(x);
        Path path = Paths.get(this.location + this.fileName);
        if (Files.notExists(path)) {
            this.create(x, 1);
        }
    }

    /** Get the file path */
    public String getFileName() {
        return this.fileName;
    }

    /**
     * Get the path location
     * <p>
     * n = 1 -> main location
     * <p>
     * n = 2 -> location2
     */
    public String getLocation(int n) {
        return n == 1 ? this.location : this.location2;
    }

    /**
     * Create file
     * <p>
     * n = 1 -> main location
     * <p>
     * n = 2 -> location2
     */
    public void create(String y, int n) {
        String s = n == 1 ? this.location : this.location2;
        locate(s, n);
        this.fileName = check(y);
        try {
            File file = new File(s + this.fileName);
            if (file.createNewFile())
                System.out.println("File created.");
            else
                return;
        } catch (IOException e) {
            System.out.print("\nERROR: ");
            e.printStackTrace();
        }
    }

    /**
     * Set the directory path location
     * <p>
     * n = 1 -> main location
     * <p>
     * n = 2 -> location2
     */
    public void locate(String x, int n) {

        /*
         * ~*~*~*~*~
         * WARNING: CHANGE THE LETTERS "DdCc" ACCORDING TO YOUR PC PARTITION LETTERS 
         * ~*~*~*~*~
         */

        // ensure that the directory path begins at a valid partition using regular
        // expressions
        if (!x.matches("(^[DdCc]:)|(^[DdCc]:)\\\\.*")) {
            System.out.println("Partition doesn't exist. please enter a proper partition name");
            return;
        }

        // if the last folder in the given directory path doesn't exist, create one
        Path path = Paths.get(x);
        if (Files.notExists(path)) {
            File newPath = new File(x);
            if (newPath.mkdir())
                System.out.println("Last folder doesn't exist, created one with the given name.");
            else {
                System.out.println("Several folders in given path don't exist, please re-enter the paths correctly.");
                return;
            }
        }

        // Ensure the directory path ends with a backslash
        String s = Character.toString(x.charAt(x.length() - 1));
        if (n == 1) {
            this.location = s.equals("\\") ? x : x + "\\";

            Path file = Paths.get(this.location + this.fileName);
            if (Files.notExists(file))
                this.fileName = "";
        } else
            this.location2 = s.equals("\\") ? x : x + "\\";
    }

    /** Add content to already existing ones in the file */
    public void write(String x) {
        if (checkPath())
            return;
        try {
            BufferedWriter buffWrite = new BufferedWriter(new FileWriter(this.location + this.fileName, true));
            buffWrite.write(x);
            buffWrite.newLine();
            buffWrite.close();
        } catch (IOException e) {
            System.out.print("\nERROR: ");
            e.printStackTrace();
        }
    }

    /** Rewrite the file regardless of previously existing content */
    public void rewrite(String x) {
        if (checkPath())
            return;
        try {
            BufferedWriter buffWrite = new BufferedWriter(new FileWriter(this.location + this.fileName));
            buffWrite.write(x);
            buffWrite.newLine();
            buffWrite.close();
        } catch (IOException e) {
            System.out.print("\nERROR: ");
            e.printStackTrace();
        }
    }

    /**
     * Replace the string 's' with the string 'y'
     * <p>
     * n = 1 -> replace the first matching string only
     * <p>
     * n =2 -> replace all matching strings
     */
    public void edit(String s, String y, int n) {
        try {
            Path path = Paths.get(this.location + this.fileName);

            String k = Files.readString(path);
            k = n == 1 ? k.replaceFirst(s, y) : k.replaceAll(s, y);

            BufferedWriter writer = new BufferedWriter(new FileWriter(this.location + this.fileName));
            writer.write(k);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Clear file */
    public void clear() {
        if (checkPath())
            return;
        try {
            FileWriter writer = new FileWriter(this.location + this.fileName);
            BufferedWriter buffWrite = new BufferedWriter(writer);
            buffWrite.write("");
            buffWrite.close();
        } catch (IOException e) {
            System.out.print("\nERROR: ");
            e.printStackTrace();
        }
    }

    /**Rename or move the file <p> 
     * n = 1 -> Rename file with the new name string 's' 
     * <p> 
     * n = 2 -> Move the file to directory path of string 's'
     */
    public void rename(String s, int n) {
        if(n == 1) check(s);
        else locate(s, 2);

        Path path = Paths.get(this.location + this.fileName);
        Path pathNew = Paths.get(n == 1 ? this.location + s : this.location2 + this.fileName);

        try {
            Files.move(path, pathNew);
        } 
        catch (IOException e) {
            System.out.print("\nERROR: ");
            e.printStackTrace();
        }
    }

    /** Delete file */
    public void delete() {
        Path path = Paths.get(this.location + this.fileName);

        try {
            Files.delete(path);
            this.fileName = "";
        } 
        catch (IOException e) {
            System.out.print("\nERROR: ");
            e.printStackTrace();
        }
    }

    /** Read file content and print it out on console */
    public void display() {
        if (checkPath()) return;
        try {
            FileReader reader = new FileReader(this.location + this.fileName);
            BufferedReader buffRead = new BufferedReader(reader);
            String line;
            while((line = buffRead.readLine()) != null) {
                System.out.println(line);
            }
            buffRead.close();
        }
        catch(IOException e) {
            System.out.print("\nERROR: ");
            e.printStackTrace();
        }
    }

    /** Read file content and return in string format */
    public String read() {
        if (checkPath()) return "";
        String k = "";
        try {
            BufferedReader buffRead = new BufferedReader(new FileReader(this.location + this.fileName));
            String line;
            while((line = buffRead.readLine()) != null) {
                k+= line;
            }
            buffRead.close();
        }
        catch(IOException e) {
            System.out.print("\nERROR: ");
            e.printStackTrace();
        }
        return k;
    }

    /** Copy from original file to another existing one <p>
     * n = 1 -> Full copy <p>
     * n = 2 -> Half copy <p>
     * n = 3 -> Letters only <p>
     * n = 4 -> Vowels only <p>
     * n = 5 -> Compressed copy (Creates a new copy)
    */
    public void copy(String x, String y, int n) {
        locate(x, 2);
        Path path2 = Paths.get(this.location2 + check(y));
        if (Files.notExists(path2)) {
            System.out.println("File doesn't exist");
            return;
        }

        try{
            FileOutputStream fos = new FileOutputStream (this.location2 + check(y));
            Path path = Paths.get(this.location + this.fileName);
            byte[] bytes = Files.readAllBytes(path);

            if(n<3) {
                fos.write(bytes, 0, n==2 ? (bytes.length/2) : bytes.length); 
            }

            else if(n < 5) {
                FileReader reader = new FileReader(this.location + this.fileName);
                BufferedReader buffReader = new BufferedReader(reader);
                String v;

                while((v = buffReader.readLine()) != null) {
                    int i;
                    for(i=0; i<v.length(); i++) {
                        if(n== 3 && !Character.toString(v.charAt(i)).matches("[a-z]|[A-Z]")) continue;
                        if(n== 4 && !Character.toString(v.charAt(i)).matches("[aeiouAEIOU]")) continue;
                        fos.write(v.charAt(i));
                    }
                }
                buffReader.close();
            }

            else if(n == 5) {
                FileOutputStream fos2 = new FileOutputStream(rawName(this.location + this.fileName) + ".zip");
                ZipOutputStream zos = new ZipOutputStream(fos2);
                zos.putNextEntry(new ZipEntry(this.fileName));

                zos.write(bytes, 0, bytes.length);
                zos.closeEntry();
                zos.close();
            }

            fos.close();
        }
        catch(IOException e) {
            System.out.print("\nERROR: ");
            e.printStackTrace();
        }
    }

    /** obtain file name without the '.txt' format at the end */
    public String rawName(String x) {
        String k = "";
        for(int i=0; i< x.length()-4; i++) {
            k+= Character.toString(x.charAt(i));
        }
        return k;
    }
}