package General.General_Super_Entertainment_Pi;

import java.util.Iterator;

public class CharacterIterator implements Iterator<Character> {

    private final String str;
    private int pos = 0;

    public CharacterIterator(String str) {
        this.str = str;
    }

    public boolean hasNext() {
        return pos < str.length();
    }

    public Character next() {
        return str.charAt(pos++);
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {

        return this.str;
    }

    public String get(int itr) {

        return this.str.charAt(itr) + "";
    }

    public int size() {

        return this.str.length();
    }
}