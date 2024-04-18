package tiff.wordle.restservice;

//import org.springframework.boot.a

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import tiff.wordle.Helpers;

import java.io.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;

@RestController
public class Controller {
    //    @GetMapping("/printing")
//    public String pe(@RequestParam(value = "guess") String guess) {
//        System.out.println(guess);
//        return "Hello world";
//
//    }


    private String word = "drape";
    private int i = -1;

    int numGuesses = 0;

    ArrayList<String> list2;

    public Controller() throws IOException {
        File file = ResourceUtils.getFile("/Users/tiffanyuong/Desktop/wordle/src/main/resources/english_wordlist.txt");
        InputStream in = new FileInputStream(file);
        String reset = new String(in.readAllBytes());
        String[] list = (reset.split("\\s"));
        list2= new ArrayList<>();
        for (String s: list) {
            if (s.length() ==5) {
                list2.add(s);
            }

        }
        shuffle(list2);

    }

    @GetMapping("/guess")
    public ResponseEntity<?> exceptions(@RequestParam(value = "guess") String guess) {

        if (guess.length() != 5 || !Helpers.containsAlphabet(guess)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Make a five letter guess or a guess containing letters in th4e alphabet");

        } else {

            return ResponseEntity.ok(guess(guess));
        }


    }

    public int[] guess(String guess) {

        int[] returningArr = new int
                [guess.length()];
        numGuesses++;


        for (int k = 0; k < guess.length(); k++) {
            boolean isFound = false;

            for (int i = 0; i < word.length(); i++) {
                if (guess.substring(k, k + 1).equals(word.substring(i, i + 1))) {
                    isFound = true;

                    if (k == i) {
                        returningArr[i] = 2;
                        break;
                    } else {
                        returningArr[k] = 1;
                    }
                }
            }
            if (!isFound) {
                returningArr[k] = 0;
            }
        }
        return returningArr;
    }

    @PostMapping("/reset")
    public ResponseEntity<?> reset() throws IOException {
        word =resetWord();
        System.out.println(word);

        return ResponseEntity.noContent().build();
    }


    public String resetWord() throws IOException {

            String n= list2.get(0);
            list2.remove(0);
            return n;


    }

    @GetMapping("/final")

    public ResponseEntity<?> revealAnswer() {
        if (numGuesses > 5) {
            return ResponseEntity.ok().body(word);
        } else {
            return ResponseEntity.ok().body("keep guessing");
        }
    }

    public void shuffle(ArrayList<String> list2) {

        for (int i=0; i<list2.size(); i++) {
            String currentWord = list2.get(i);
            int randomIndex = (int) (Math.random() *list2.size());
            String randomWord = list2.get(randomIndex);
            list2.set(i, randomWord);
            list2.set(randomIndex, currentWord);
        }

    }
}




