package main.code;

public class Letter extends Word
{
    private boolean isGuessed;

    public boolean isGuessed() { return isGuessed; }
    public void setGuessed(boolean guessed) { this.isGuessed = guessed; }

    Letter(String name, boolean isGuessed) {
        super(name, isGuessed);
    }
}
