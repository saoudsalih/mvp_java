package in.aikya.mt.utils;

import java.util.ArrayList;
import java.util.HashMap;


public abstract class TraxsmartSteganoGraphy {

    /**
     * here steganographed is true the custructor allow class to convert steganographed text to original text
     * @param text
     * @param steganographed
     * @param steganoPattern
     */
    public TraxsmartSteganoGraphy(String text, boolean steganographed, String steganoPattern){
        if(steganographed){

            deCoding(text,steganoPattern);
        }else{
            enCoding(text,steganoPattern);
        }
    }
    protected abstract void getSteganoWord(String stegano);
    protected abstract void getText(String stegano);
    private ArrayList<String> BINARY_NUMBER ;
    private ArrayList<String> NIBBLE;
    private String LOWER_NIBBLE="";
    private String HIGHER_NIBBLE="";
    private ArrayList<String> ALPHABETS;
    private HashMap<String,Integer> hmap_d_ALPHABET;
    HashMap<Integer,String> hmap_e_ALPHABET;
    private HashMap<String,Integer> en_hmap_BINARY;
    private HashMap<Integer,String> dec_hmap_BINARY;
    private ArrayList<Integer> DECIMAL_FOR_NIBBLE;
    private ArrayList<Integer> DECIMALVALUES;
    private HashMap<String,Integer> hmap_BINRYASCII;
    private ArrayList<Integer> ASCII_VALUES;
    private HashMap<Integer,String> hmap_ASCII_CHAR;

    private  void enCoding(String PASSWORD, String steganoPattern){

        PASSWORD=PASSWORD.toUpperCase();
        int ASCII[]=new int[PASSWORD.length()];

        for(int i=0;i<PASSWORD.length();i++){
            ASCII[i]=CharToASCII(PASSWORD.charAt(i));
        }

        BINARY_NUMBER =new ArrayList<String>();

        for(int i=0;i<ASCII.length;i++)
        {
            BINARY_NUMBER.add(printBinaryFormat(ASCII[i]));
        }

        NIBBLE=new ArrayList<String>();

        for(int i=0;i<BINARY_NUMBER.size();i++)
        {
            for(int j=0;j<BINARY_NUMBER.get(i).length();j++)
            {
                if(j<=3)
                {
                    LOWER_NIBBLE=LOWER_NIBBLE+BINARY_NUMBER.get(i).charAt(j);
                }
                else
                {
                    HIGHER_NIBBLE=HIGHER_NIBBLE+BINARY_NUMBER.get(i).charAt(j);
                }
            }

            NIBBLE.add(LOWER_NIBBLE);
            NIBBLE.add(HIGHER_NIBBLE);

            LOWER_NIBBLE="";
            HIGHER_NIBBLE="";

        }

        en_hmap_BINARY=new HashMap<String,Integer>();
        en_hmap_BINARY.put("0000",0);
        en_hmap_BINARY.put("0001",1);
        en_hmap_BINARY.put("0010",2);
        en_hmap_BINARY.put("0011",3);
        en_hmap_BINARY.put("0100",4);
        en_hmap_BINARY.put("0101",5);
        en_hmap_BINARY.put("0110",6);
        en_hmap_BINARY.put("0111",7);
        en_hmap_BINARY.put("1000",8);
        en_hmap_BINARY.put("1001",9);
        en_hmap_BINARY.put("1010",10);
        en_hmap_BINARY.put("1011",11);
        en_hmap_BINARY.put("1100",12);
        en_hmap_BINARY.put("1101",13);
        en_hmap_BINARY.put("1110",14);
        en_hmap_BINARY.put("1111",15);

        DECIMAL_FOR_NIBBLE=new ArrayList<Integer>();

        for(int i=0;i<NIBBLE.size();i++)
        {
            DECIMAL_FOR_NIBBLE.add(en_hmap_BINARY.get(NIBBLE.get(i)));
        }

        hmap_e_ALPHABET=new HashMap<Integer,String>();

        hmap_e_ALPHABET.put(0,"Q");
        hmap_e_ALPHABET.put(1,"J");
        hmap_e_ALPHABET.put(2,"Z");
        hmap_e_ALPHABET.put(3,"V");
        hmap_e_ALPHABET.put(4,"Y");
        hmap_e_ALPHABET.put(5,"B");
        hmap_e_ALPHABET.put(6,"G");
        hmap_e_ALPHABET.put(7,"H");
        hmap_e_ALPHABET.put(8,"D");
        hmap_e_ALPHABET.put(9,"C");
        hmap_e_ALPHABET.put(10,"L");
        hmap_e_ALPHABET.put(11,"N");
        hmap_e_ALPHABET.put(12,"O");
        hmap_e_ALPHABET.put(13,"I");
        hmap_e_ALPHABET.put(14,"A");
        hmap_e_ALPHABET.put(15,"E");

        ALPHABETS=new ArrayList<String>();

        for(int i=0;i<DECIMAL_FOR_NIBBLE.size();i++)
        {
            ALPHABETS.add(hmap_e_ALPHABET.get(DECIMAL_FOR_NIBBLE.get(i)));
        }
        String[] WORDS={"amazone","bodhiinfo","cloud","diversion","earthline","facebook","goalinfo","helpline","in","jack","kingfra","line","menubar","network","or","plane","quest","reorder","sing","to","under","veeble","wins","xray","yet","zoo"};

        String SECRET_SENTENCE="";

        for(int i=0;i<ALPHABETS.size();i++)
        {
            for(int j=0;j<WORDS.length;j++)
            {
                if(WORDS[j].startsWith(ALPHABETS.get(i).toLowerCase()))
                {
                    SECRET_SENTENCE=SECRET_SENTENCE+steganoPattern+WORDS[j];
                }

            }
        }

        getSteganoWord(SECRET_SENTENCE);

    }

    private void deCoding(String SECRET_SENTNCE, String steganoPattern){

        String[] WORDS=SECRET_SENTNCE.split(steganoPattern);
        ALPHABETS=new ArrayList<String>();
        for(String word : WORDS){
            if(word.length()>0)
                ALPHABETS.add(""+word.toUpperCase().charAt(0));
        }

        hmap_d_ALPHABET=new HashMap<String,Integer>();
        hmap_d_ALPHABET.put("Q",0);
        hmap_d_ALPHABET.put("J",1);
        hmap_d_ALPHABET.put("Z",2);
        hmap_d_ALPHABET.put("V",3);
        hmap_d_ALPHABET.put("Y",4);
        hmap_d_ALPHABET.put("B",5);
        hmap_d_ALPHABET.put("G",6);
        hmap_d_ALPHABET.put("H",7);
        hmap_d_ALPHABET.put("D",8);
        hmap_d_ALPHABET.put("C",9);
        hmap_d_ALPHABET.put("L",10);
        hmap_d_ALPHABET.put("N",11);
        hmap_d_ALPHABET.put("O",12);
        hmap_d_ALPHABET.put("I",13);
        hmap_d_ALPHABET.put("A",14);
        hmap_d_ALPHABET.put("E",15);

        DECIMALVALUES=new ArrayList<Integer>();

        for(int i=0;i<ALPHABETS.size();i++){
            DECIMALVALUES.add(hmap_d_ALPHABET.get(ALPHABETS.get(i)));
        }
        dec_hmap_BINARY=new HashMap<Integer,String>();
        dec_hmap_BINARY.put(0,"0000");
        dec_hmap_BINARY.put(1,"0001");
        dec_hmap_BINARY.put(2,"0010");
        dec_hmap_BINARY.put(3,"0011");
        dec_hmap_BINARY.put(4,"0100");
        dec_hmap_BINARY.put(5,"0101");
        dec_hmap_BINARY.put(6,"0110");
        dec_hmap_BINARY.put(7,"0111");
        dec_hmap_BINARY.put(8,"1000");
        dec_hmap_BINARY.put(9,"1001");
        dec_hmap_BINARY.put(10,"1010");
        dec_hmap_BINARY.put(11,"1011");
        dec_hmap_BINARY.put(12,"1100");
        dec_hmap_BINARY.put(13,"1101");
        dec_hmap_BINARY.put(14,"1110");
        dec_hmap_BINARY.put(15,"1111");

        NIBBLE=new ArrayList<String>();

        for(int i=0;i<DECIMALVALUES.size();i++){
            NIBBLE.add(dec_hmap_BINARY.get(DECIMALVALUES.get(i)));
        }
        ArrayList<String> BINARY_VALUES=new ArrayList<String>();

        String LOWER="";
        String HIGHER="";

        for(int i=0;i<NIBBLE.size();i++){
            LOWER=NIBBLE.get(i);
            HIGHER=NIBBLE.get(++i);
            BINARY_VALUES.add(LOWER+HIGHER);
        }
        hmap_BINRYASCII=new HashMap<String,Integer>();

        hmap_BINRYASCII.put("01000001", 65);
        hmap_BINRYASCII.put("01000010", 66);
        hmap_BINRYASCII.put("01000011", 67);
        hmap_BINRYASCII.put("01000100", 68);
        hmap_BINRYASCII.put("01000101", 69);
        hmap_BINRYASCII.put("01000110", 70);
        hmap_BINRYASCII.put("01000111", 71);
        hmap_BINRYASCII.put("01001000", 72);
        hmap_BINRYASCII.put("01001001", 73);
        hmap_BINRYASCII.put("01001010", 74);
        hmap_BINRYASCII.put("01001011", 75);
        hmap_BINRYASCII.put("01001100", 76);
        hmap_BINRYASCII.put("01001101", 77);
        hmap_BINRYASCII.put("01001110", 78);
        hmap_BINRYASCII.put("01001111", 79);
        hmap_BINRYASCII.put("01010000", 80);
        hmap_BINRYASCII.put("01010001", 81);
        hmap_BINRYASCII.put("01010010", 82);
        hmap_BINRYASCII.put("01010011", 83);
        hmap_BINRYASCII.put("01010100", 84);
        hmap_BINRYASCII.put("01010101", 85);
        hmap_BINRYASCII.put("01010110", 86);
        hmap_BINRYASCII.put("01010111", 87);
        hmap_BINRYASCII.put("01011000", 88);
        hmap_BINRYASCII.put("01011001", 89);
        hmap_BINRYASCII.put("01011010", 90);

        ASCII_VALUES=new ArrayList<Integer>();

        for(int i=0;i<BINARY_VALUES.size();i++){
            ASCII_VALUES.add(hmap_BINRYASCII.get(BINARY_VALUES.get(i)));
        }

        hmap_ASCII_CHAR=new HashMap<Integer,String>();
        hmap_ASCII_CHAR.put(65,"A");
        hmap_ASCII_CHAR.put(66,"B");
        hmap_ASCII_CHAR.put(67,"C");
        hmap_ASCII_CHAR.put(68,"D");
        hmap_ASCII_CHAR.put(69,"E");
        hmap_ASCII_CHAR.put(70,"F");
        hmap_ASCII_CHAR.put(71,"G");
        hmap_ASCII_CHAR.put(72,"H");
        hmap_ASCII_CHAR.put(73,"I");
        hmap_ASCII_CHAR.put(74,"J");
        hmap_ASCII_CHAR.put(75,"K");
        hmap_ASCII_CHAR.put(76,"L");
        hmap_ASCII_CHAR.put(77,"M");
        hmap_ASCII_CHAR.put(78,"N");
        hmap_ASCII_CHAR.put(79,"O");
        hmap_ASCII_CHAR.put(80,"P");
        hmap_ASCII_CHAR.put(81,"Q");
        hmap_ASCII_CHAR.put(82,"R");
        hmap_ASCII_CHAR.put(83,"S");
        hmap_ASCII_CHAR.put(84,"T");
        hmap_ASCII_CHAR.put(85,"U");
        hmap_ASCII_CHAR.put(86,"V");
        hmap_ASCII_CHAR.put(87,"W");
        hmap_ASCII_CHAR.put(88,"X");
        hmap_ASCII_CHAR.put(89,"Y");
        hmap_ASCII_CHAR.put(90,"Z");

        String SECRET_PASSWORD="";

        for(int i=0;i<ASCII_VALUES.size();i++)
        {
            SECRET_PASSWORD=SECRET_PASSWORD+hmap_ASCII_CHAR.get(ASCII_VALUES.get(i));
        }
        getText(SECRET_PASSWORD.toLowerCase());
    }



    public static int CharToASCII(final char character){
        return (int)character;
    }

    private static String printBinaryFormat(int number){
        String binaryNumber="0";

        int binary[] = new int[25];
        int index = 0;
        while(number > 0){
            binary[index++] = number%2;
            number = number/2;
        }
        for(int i = index-1;i >= 0;i--){
            binaryNumber=binaryNumber+binary[i];

        }
        return binaryNumber;
    }

}
