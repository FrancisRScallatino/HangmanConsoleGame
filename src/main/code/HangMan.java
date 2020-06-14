package main.code;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class HangMan
{
    public static void main(String[] args)
    {
        Scanner kbInput = new Scanner(System.in);
        Word wordToGuess = new Word("boobies", false);
        ArrayList<String> wrongGuesses = new ArrayList<>();
        String inWord, playerGuess, playAgain;
        final int wrongGuessLimit = 7;
        boolean isLetterGuessed = false, hasNonAlpha;

        System.out.println("Let's play some Hangman!");
        System.out.println("The number of letters in the word will appear as lines at the bottom of the gallows");

        do{
            do{
                System.out.print("Word you want to be guessed: ");
                inWord = kbInput.nextLine();

                Pattern alphabetic = Pattern.compile("[^a-zA-Z]");
                hasNonAlpha = alphabetic.matcher(inWord).find();

                if(hasNonAlpha){
                    System.out.println("Please enter 1 word (excluding special characters).");
                } else {
                    wordToGuess = new Word(inWord, false);
                    wordToGuess.setLetterArrayList(wordToGuess.getName().toUpperCase());
                    wrongGuesses = new ArrayList<>();

                    for (int i = 0; i < 20; i++) {
                        System.out.println();
                    }
                    draw(wordToGuess, wrongGuesses);
                }
            }while(hasNonAlpha);

            while (!wordToGuess.isGuessed() && !(wrongGuesses.size() == wrongGuessLimit))
            {
                do{
                    System.out.print("What letter would you like to guess? ");
                    playerGuess = kbInput.nextLine().toUpperCase();

                    if (playerGuess.length()>1 || !Character.isLetter(playerGuess.charAt(0))){
                        System.out.println("Please enter 1 alphabetic character.");
                    } else {
                        String finalPlayerGuess = playerGuess;
                        isLetterGuessed = wordToGuess
                                .getLetterList()
                                .stream()
                                .filter(o -> o.getName().equals(finalPlayerGuess))
                                .anyMatch(Word::isGuessed);

                        if(isLetterGuessed || wrongGuesses.contains(playerGuess))
                        {
                            System.out.println("You already guessed that letter");
                        }
                    }
                }while(playerGuess.length()>1 || !Character.isLetter(playerGuess.charAt(0))
                        || wrongGuesses.contains(playerGuess) || isLetterGuessed);

                String finalPlayerGuess1 = playerGuess;
                if(wordToGuess.getLetterList().stream().noneMatch(o -> o.getName().equals(finalPlayerGuess1)))
                {
                    wrongGuesses.add(playerGuess);
                } else {
                    wordToGuess.getLetterList()
                            .stream()
                            .filter(o -> o.getName().equals(finalPlayerGuess1))
                            .reduce((a, b) -> {throw new RuntimeException("Duplicate elements found: " + a + " and " + b);})
                            .get().setGuessed(true);
                }

                draw(wordToGuess, wrongGuesses);
            }

            if (wordToGuess.isGuessed()){
                System.out.println("You guessed the word! Congratulations!");
            } else {
                System.out.println("Uh oh! Looks like you ran out of guesses. :(");
                System.out.println("The word you had to guess was: " + wordToGuess.getName() + "\n");
            }

            do{
                System.out.println("Would you like to play again? 'yes' 'no'");
                playAgain = kbInput.nextLine().toLowerCase();

                if(!(playAgain.equals("yes") || playAgain.equals("no"))){
                    System.out.println("Please enter a specified response.");
                }
            }while(!(playAgain.equals("yes") || playAgain.equals("no")));

            if (playAgain.equals("yes")){

            }

        }while(playAgain.equals("yes"));
    }

    public static void draw (Word wordToGuess, ArrayList<String> wrongGuesses)
    {
        int lines = 8;

        for (int i = 0; i <= lines; i++)
        {
            switch (i)
            {
                case 0:
                    System.out.println("------");
                    break;
                case 1:
                case 4:
                    if (i == 1){
                        System.out.println("|    |");
                    } else {
                        System.out.print("|    ");
                        if (wrongGuesses.size() > 4){
                            System.out.println("|");
                        } else {
                            System.out.println();
                        }
                    }
                    break;
                case 2:
                    if (wrongGuesses.size()>0){
                        System.out.println("|    O");
                    } else {
                        System.out.println("|");
                    }
                    break;
                case 3:
                    if (wrongGuesses.size() > 3){
                        System.out.println("|   /|\\");
                    } else if (wrongGuesses.size() > 2) {
                        System.out.println("|   /|");
                    } else if (wrongGuesses.size() > 1){
                        System.out.println("|    |");
                    } else {
                        System.out.println("|");
                    }
                    break;
                case 5:
                    if(wrongGuesses.size() > 6){
                        System.out.println("|   / \\");
                    } else if (wrongGuesses.size() > 5){
                        System.out.println("|   /");
                    } else {
                        System.out.println("|");
                    }
                    break;
                case 6:
                    System.out.println("|");
                    break;
                case 7:
                    for (int j = 0; j < wordToGuess.getLength(); j++) {
                        String currentLetter = String.valueOf(wordToGuess.getName().charAt(j)).toUpperCase();
                        boolean isLetterGuessed = wordToGuess.getLetterList()
                                .stream()
                                .filter(o -> o.getName().equals(currentLetter))
                                .reduce((a, b) -> {throw new RuntimeException("Duplicate elements found: " + a + " and " + b);})
                                .get().isGuessed();

                        if(isLetterGuessed){
                            System.out.print(currentLetter + " ");
                        } else {
                            System.out.print("_ ");
                        }
                    }
                    System.out.println();
                    break;
                case 8:
                    System.out.print("Wrong guesses: ");
                    if (wrongGuesses.size() != 0) {
                        for (String s : wrongGuesses) {
                            System.out.print(s + " ");
                        }
                    }
                    System.out.println("\n");
            }
        }
    }
}
