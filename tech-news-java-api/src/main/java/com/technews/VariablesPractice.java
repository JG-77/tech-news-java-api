package com.technews;

public class VariablesPractice {
    public static void main(String[] args) {
        int val1 = 2;
        double val2 = 3.1;
        String val3 = "sample text";

        double sum = val1 + val2;// since there is a decimal value, the sum must be a double as well

        String concat = val1 + val3;//calculated variable concat is a String

        // value of sum is 5.1
        System.out.println(sum);

        // value of concat is "2sample text"
        System.out.println(concat);
    }
}
