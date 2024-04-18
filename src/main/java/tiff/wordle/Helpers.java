package tiff.wordle;

public class Helpers {


    public static boolean containsAlphabet(String word) {
        for (char letter = 'a'; letter <= 'z'; letter++) {
            if (word.indexOf(letter) != -1) {
                return true;
            }
        }
        return false;
    }
}




