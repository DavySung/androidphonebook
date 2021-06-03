package com.example.ast2_phonebook.Lib;
import com.example.ast2_phonebook.Room.Entities.Phonebook;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Pattern;

public class MyHash {

    private final ArrayList<Phonebook>[] hashTable;

    public MyHash() {

        hashTable = new ArrayList[27];
        for(int i = 0; i < hashTable.length; i++){
            hashTable[i] = new ArrayList<Phonebook>();
        }
    }

    //put everything in a linked list
    public ArrayList<Phonebook> toList(boolean reverse) {
        ArrayList<Phonebook> c = new ArrayList ();
        for(int i = 0; i < hashTable.length; i++){
            for(int j = 0; j < hashTable[i].size(); j++){
                c.add(hashTable[i].get(j));
            }
        }
        if(reverse == true) {
            Collections.reverse(c);
        }
        return c;
    }

    // for search
    public ArrayList<Phonebook> shortList(String wantedStr) {
        ArrayList<Phonebook> c = new ArrayList ();
        for(int i = 0; i < hashTable.length; i++){
            for(int j = 0; j < hashTable[i].size(); j++){
                if (Pattern.compile(Pattern.quote(wantedStr), Pattern.CASE_INSENSITIVE).matcher(hashTable[i].get(j).getFirstName()).find()) {
                    c.add(hashTable[i].get(j));
                }
            }
        }
        return c;
    }

    //for a specific key, this function calculate the offset of the element
    public int calcOffsetByKey(int k) {
        int offset = 0;
        if (k < 0 || k >= hashTable.length) {
            offset = 0;
        } else {
            for(int i = 0; i < k; i++){
                // offset is the sum of the size of all previous list.
                offset = offset + hashTable[i].size();
            }
        }
        return offset;
    }

    // build hash table. use Arraylist to resolve collision.
    // sort all Arraylist
    public void buildHashTable(ArrayList<Phonebook> list) {
        if(list == null) {
            return;
        }
        for(int i = 0; i < list.size(); i++){
            Phonebook c = list.get(i);
            int hashTableIndex = hash(c.getFirstName());
            hashTable[hashTableIndex].add(c);
        }

        for(int i = 0; i < hashTable.length; i++){
            Collections.sort(hashTable[i]);
        }
        return;
    }

    // hash the first char of a string
    private int hash(String s) {
        // get the first char and convert to uppercase to make it case insensitive.
        char c = s.toUpperCase().charAt(0);
        // get ascii value of the first char
        int asciiValue = (int)c;
        if(asciiValue >= 65 && asciiValue <= 90) {
            asciiValue = asciiValue - 64;
        } else {
            asciiValue = 0;
        }
        return asciiValue;
    }

    public void dump(){
        for(int i = 0; i < hashTable.length; i++){

            System.out.print("[" + i + "] ");
            for(int j = 0; j < hashTable[i].size(); j++){
                System.out.print("->(" + hashTable[i].get(j).getFirstName() + " / " +
                        hashTable[i].get(j).getLastName() + " / " + hashTable[i].get(j).getPhone() + ")");
            }
            System.out.print("\n");
        }
    }
}
