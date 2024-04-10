package com.jz.algo.array;

import java.util.Random;

class Shuffle {
    public boolean validWordAbbreviation(String word, String abbr) {
        int i = 0 ;
        for (; i < abbr.length(); i++) {
            int start = i;
            if( Character.isDigit(abbr.charAt(i))){
                while(Character.isDigit(abbr.charAt(i))){
                    start++;
                }
                String str = abbr.substring(i, start);
                if(str.startsWith("0") || str.length() == 0)return false;
                i = i + Integer.valueOf(str);

            } else {
                if( word.charAt(i)  != abbr.charAt(i)) return false;
            }

        }
        return i == 0;
    }
}