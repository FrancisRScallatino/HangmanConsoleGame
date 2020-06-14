package main.code;

import java.util.ArrayList;

public class Word
{
    private ArrayList<Letter> letters;
    private String name;
    private int length;
    private boolean isGuessed;

    public Word(String name, boolean isGuessed){
        this.name = name;
        this.isGuessed = isGuessed;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public boolean isGuessed() {
        int uniqueLetters = letters.size();
        int counter = 0;

        for (Letter l : letters){
            if(l.isGuessed()) counter++;
        }

        if(counter == uniqueLetters) isGuessed = true;

        return isGuessed;
    }

    public int getLength() {
        if (length == 0){
            length = name.length();
        }
        return length;
    }

    public ArrayList<Letter> getLetterList() { return letters; }
    public void setLetterArrayList (String word)
    {
        letters = new ArrayList<>();
        boolean isNotIncluded;

        for (int i = 0; i < word.length(); i++) {
            String currentLetter = String.valueOf(word.charAt(i));

            isNotIncluded = letters.stream().noneMatch(o -> o.getName().equals(currentLetter));

            if (isNotIncluded) letters.add(new Letter (currentLetter, false));
        }
    }


    public boolean hasLetter (String guessedLetter)
    {
        for (Letter l : letters){
            if(guessedLetter.equals(l.getName())){
                l.setGuessed(true);
                return true;
            }
        }
        return false;
    }
}
