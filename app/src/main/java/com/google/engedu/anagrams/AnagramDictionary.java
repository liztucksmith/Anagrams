/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Arrays;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    private ArrayList<String> wordList;
    private HashSet<String> wordSet;
    private HashMap<String, ArrayList<String>> lettersToWord;


    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        wordSet = new HashSet<String>();
        lettersToWord = new HashMap<String, ArrayList<String>>();
        wordList = new ArrayList<String>();
        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordList.add(word);
            wordSet.add(word);
            String sortedWord = sortLetters(word);
            if(lettersToWord.containsKey(sortedWord)){
                ArrayList<String> currentList = lettersToWord.get(sortedWord);
                currentList.add(word);
            }
            else{
                ArrayList<String> newList = new ArrayList<>();
                newList.add(word);
                lettersToWord.put(sortedWord, newList);
            }
        }
    }

    public boolean isGoodWord(String word, String base) {
        return wordSet.contains(word) && word.indexOf(base) < 0;
    }

    public List<String> getAnagrams(String targetWord) {
        return lettersToWord.get(sortLetters(targetWord));
    }

    public String sortLetters(String abc){
        char[] abcArray = abc.toCharArray();
        Arrays.sort(abcArray);
        return Arrays.toString(abcArray);
    }

    public List<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        for(char alp = 'a'; alp <= 'z'; alp++){
            String currentWord = word + Character.toString(alp);
            List anagrams = getAnagrams(currentWord);
            if(anagrams!=null){
                result.addAll(getAnagrams(currentWord));
            }
        }

        return result;
    }

    public String pickGoodStarterWord() {
        return "stop";
    }
}
