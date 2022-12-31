package Project2;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

/**
 *
 * @author Muhammed
 */
public class LinearProbingHash<Key> {

    Key[] table;
    int size; // size of hastable
    int numOfElement; // number of full elements

    public LinearProbingHash(int size) {
        table = (Key[]) new Object[size];
        this.size = size;
    }

    public int hash(Key t) {
        return ((t.hashCode() & 0x7fffffff) % size); //will result in a positive integer.(all 1 except the sign bit.)
    }

    //contains method returns true if hash map contains the Key
    public boolean contains(Key key) {
        int ix = hash(key);
        System.out.print(" hash : " + ix);
        int startIx = ix;

        while (table[ix] != null && (ix + 1 != startIx)) {
            if (table[ix].equals(key)) {
                return true; //found
            }
            ix = (ix + 1) % size; // cycle through
        }
        return false;
    }

    public int search(Key key) {

        int ix = hash(key);
        int startIx = ix;

        while (table[ix] != null && (ix + 1 != startIx)) {
            if (table[ix].equals(key)) {
                return ix; //found
            }
            ix = (ix + 1) % size; // cycle through

        }
        return -1;
    }
    
    public String getName(int v1){
        return (String)table[v1];
    }

    public boolean insert(Key key) {
        int ix = hash(key);
        // System.out.print(" hash " + key +" : " + ix);
        // must check for a whole cycle there can be infinite loop
        if (numOfElement == size) {
            System.out.println(" : The table is full! \n");
            resize(size + size / 2);
            return false;
        }
        while (table[ix] != null) {
            if (table[ix].equals(key)) {
                // System.out.println(" : Table has the key");
                return false;
            }
            ix = (ix + 1) % size;
        }
        // if table[ix]==null this means it is empty. Insert.
        table[ix] = key;
        numOfElement++;
        return true;
    }

    public String toString() {
        String s = "[";
        for (int i = 0; i < size; i++) {
            s += table[i] + ",";
        }
        return s + "]";
    }

    public void resize(int capacity) {
        System.out.println("Re size: " + capacity);
        LinearProbingHash<Key> temp = new LinearProbingHash<>(capacity);
        for (int i = 0; i < size; i++) {
            if (table[i] != null) {
                temp.insert(table[i]);
            }
        }
        table = temp.table;
        size = temp.size;
        numOfElement = temp.numOfElement;

    }

}
