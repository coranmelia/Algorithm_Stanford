import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import java.io.*;
import java.util.*;

public class TwoSum {

    public static void main(String[] args)
            throws FileNotFoundException{
        Scanner in = new Scanner(new File("data/2sum.txt"));
        long[] numbers = new long[1000000];
        HashTable table = new HashTable(500000);
        for (int i = 0; i < 1000000; i++){
            long l = in.nextLong();
            numbers[i] = l;
            table.addLong(l);
        }
        /*
         * The 1000000 inputs range from -1*10^11 to 1*10^11
         * Suppose it is distributed evenly, every bucket of range 200000 expect one number.
         * For every a, we expect to find b in three buckets: index[-a], index[-a]-1, index[-a]+1.
         */
        int count = 0;
        HashSet<Integer> Sum = new HashSet<Integer>();
        for (long a : numbers){
            int i = (int) ((-a / table.RANGE) + 500000) / 2;
            for (int j = Math.max(0, i-1); j < 500000 && j <= i+1; j++){
                for (long b : table.table[j].list){
                    long sum = (int) (a + b);
                    if (sum < 10000 && sum > -10000){
                        if (!Sum.contains((int) sum)){
                            count++;
                            Sum.add((int) sum);
                        }
                    }
                }
            }
        }
        System.out.println(count);
    }
}

class HashTable {
    public Bucket[] table;
    public final int RANGE = 200000;

    public HashTable(int size){
        table = new Bucket[size];
        for (int i = 0; i < size; i++){
            table[i] = new Bucket();
        }
    }
    public void addLong(long l){
        int index = (int) ((l / RANGE) + 500000) / 2;
        table[index].addLong(l);
    }
    public int bucketSize(int i){
        return table[i].size;
    }
}

class Bucket{
    public ArrayList<Long> list;
    public int size = 0;

    public Bucket(){
        list = new ArrayList<Long>();
    }
    public void addLong(long l){
        if (!list.contains(l)){
            list.add(l);
        }
        size++;
    }
}
