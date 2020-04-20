import java.util.ArrayList;

public class Encryptor {
    
    //Initialize the components
    public ArrayList<String> input = new ArrayList<String>();
    private int n=4;
    private int s = 1;
    private int t;
    private String key= "default";
    
    public Encryptor() {
    }

    public void setN(int n) {
        
        //Value of shifts can't be greater than 26
        this.n = n%26;
    }

    public int getN() {
        return this.n;
    }

    public void setS(int s) {
        
        //Value of shifts can't be greater than 26
        this.s = s%26;
    }

    public int getS() {
        return this.s;
    }

    public String getInput() {
        return String.join("", input);
    }

    public void setInput(String x) {
        
        //Convert string to array
        input.clear();
        String[] c = x.split("");
        for(String i: c) {
            input.add(i);
        }
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        
        //Filter out any non-letter characters
        String[] c = key.split("(\\W)");
        this.key = String.join("", c);
    }

    /** Encryption using number values <p>
     * k = 1 -> Encrypt <p>
     * k = 2 -> Decrypt
    */
    public String encrypt(int k) {
        
        //Setup the counters and no. of shifts
        this.t = this.s;
        int c=0;
        
        //Convert input into an ASCII array
        String[] str = new String[input.size()];
        int[] r = new int[input.size()];

        //Begin encryption/decryption
        for(int i=0; i<input.size(); i++) {
            r[i] = (int)input.get(i).charAt(0);

            //Reset if number of shifts is max
            if(c==this.n) {
                this.t=this.s;
                c = 0;
            }
            
            //Uppercase letters
            if (r[i] > 64 && r[i] < 91) {
                r[i] = k==1 ? r[i]+t : r[i]-t;
                r[i] = (r[i] > 90) ? r[i]-26 : (r[i] < 65) ? r[i]+26: r[i];
                this.t+=this.s;
                c++;
            }
            
            //Lowercase letters
            if (r[i] > 96 && r[i] < 123) {
                r[i] = k==1 ? r[i]+t : r[i]-t;
                r[i] = (r[i] > 122) ? r[i]-26 : (r[i] < 97) ? r[i]+26: r[i];
                this.t+=this.s;
                c++;
            }

            //Convert the new ASCII code into character and replace the old one
            str[i] = Character.toString((char)r[i]);
        }
        
        //Merge the array into string
        return String.join("", str);
    }

    /** Encryption using keyword <p>
     * k = 1 -> Encrypt <p>
     * k = 2 -> Decrypt
    */
    public String encryptKey(int k) {
        
        //setup the counter
        int c=0;
        
        //Convert input into an ASCII array
        String[] str = new String[input.size()];
        int[] r = new int[input.size()];
        
        //Begin encryption/decryption
        for(int i=0; i<input.size(); i++) {
            r[i] = (int)input.get(i).charAt(0);
            
            //Reset counter if last letter of keyword is reached
            if (c == key.length()) c=0;
            
            //obtain upper and lower case letters' positions
            if(((int)this.key.charAt(c)) > 64 && ((int)this.key.charAt(c)) < 91) this.t = ((int)this.key.charAt(c)) -63;
            if(((int)this.key.charAt(c)) > 96 && ((int)this.key.charAt(c)) < 123) this.t = ((int)this.key.charAt(c)) -96;
            
            //Uppercase letters
            if (r[i] > 64 && r[i] < 91) {
                r[i] = k==1 ? r[i]+t : r[i]-t;
                r[i] = (r[i] > 90) ? r[i]-26 : (r[i] < 65) ? r[i]+26: r[i];
                c++;
            }
            
            //Lowercase letters
            if (r[i] > 96 && r[i] < 123) {
                r[i] = k==1 ? r[i]+t : r[i]-t;
                r[i] = (r[i] > 122) ? r[i]-26 : (r[i] < 97) ? r[i]+26: r[i];
                c++;
            }

            //Convert the new ASCII code into character and replace the old one
            str[i] = Character.toString((char)r[i]);
        }
        
        //Merge the array into string
        return String.join("", str);
    }
}