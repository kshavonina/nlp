package com.hack.proj;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Lemitization {
    public static void main(String[] args) throws IOException {
        // public EnglishAnalyzer(CharArraySet stopwords) - мы сделаем свой стоп-лист, чтобы можно было

        String pathStopwords =
                "C:\\Users\\Kseniia_Shavonina\\IdeaProjects\\nlp-hackathon\\src\\main\\resources\\stopwords.txt";

        List<String> listOfStopWords = createListOfStopWords(pathStopwords);


        // create stopwords chararrayset for creating specific english analyser
        CharArraySet stopwords = new CharArraySet(listOfStopWords, true);
        EnglishAnalyzer englishAnalyzer = new EnglishAnalyzer(stopwords);

        String testString = "<p>I want to implement a passwordless email based authentication (like in medium.com!), now I want to verify if this logic flow is secure or not:</p>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "<ol>\n" +
                "\n" +
                "\n" +
                "\n" +
                "<li>User submits his email</li>\n" +
                "\n" +
                "\n" +
                "\n" +
                "<li>Server generates a random token, sets to Redis a new value where the KEY is the token, the VALUE is the email or the corresponding user id, also make the key expires within an hour or some soon future value (within tens of minutes or hours), send a link including the token to the user email (i.e. <code>example.com/login/token/{TOKEN}</code>)</li>\n" +
                "\n" +
                "\n" +
                "\n" +
                "<li>User visits his inbox and press on that link</li>\n" +
                "\n" +
                "\n" +
                "\n" +
                "<li>Server checks for {TOKEN} key, if it exists, authenticate the user and redirect to homepage, if not redirect to some error page.</li>\n" +
                "\n" +
                "\n" +
                "\n" +
                "</ol>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "<p>Is this approach secure, is there something hidden I can't figure out yet?\n" +
                "\n" +
                "\n" +
                "\n" +
                "Also Note that this method authenticate the user with only <code>GET</code> methods. \n" +
                "\n" +
                "\n" +
                "\n" +
                "I use Django/Python, so if there is some package doing that, what is it?\n" +
                "\n" +
                "\n" +
                "\n" +
                "Also is it relevant and secure for new user registrations/password-change/other user checks? Is there impact in case of DDoS attacks like making my server spam random emails, consuming the server using having a big Redis memory consumption in short time?</p>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "<p>For token generation, I will use some value generated by <code>random.SystemRandom().choice('abcdefghijklmnopqrstuvwxyz0123456789')</code>\n" +
                "\n" +
                "\n" +
                "\n" +
                "long enough to be hard to guess ( 60-80 characters) and it is valid for a short time like I mentioned.</p>\n" +
                "\n" +
                "\n" +
                "\n";



        List<String> tokenizedString = tokenizeString(englishAnalyzer, testString);

        for (String str : tokenizedString) {
            System.out.println(str);
        }


        // stemming




    }

    public static List<String> tokenizeString(Analyzer analyzer, String string) {
        List<String> result = new ArrayList<String>();
        try {
            TokenStream stream  = analyzer.tokenStream(null, new StringReader(string));
            stream.reset();
            while (stream.incrementToken()) {
                result.add(stream.getAttribute(CharTermAttribute.class).toString());
            }
        } catch (IOException e) {
            // not thrown b/c we're using a string reader...
            throw new RuntimeException(e);
        }
        return result;
    }

    private static List<String> createListOfStopWords(String path) throws IOException {
        // read stop words
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader
                (new FileInputStream
                        (path)));

        // create stop words list
        List<String> listOfStopWords = new ArrayList<>();
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            listOfStopWords.add(line);
        }

        /*for (int i = 0; i < listOfStopWords.size(); i++) {
            System.out.println(i + ": " + listOfStopWords.get(i));
        }*/

        return listOfStopWords;
    }


}