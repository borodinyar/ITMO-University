package md2html;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Md2Html {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("You should enter the name of two files");
            return;
        }

        String answer = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(args[0], StandardCharsets.UTF_8))) {
            MarkdownSource source = new MarkdownSource(reader);
            answer = new Md2HtmlParser(source).parse();
        } catch (IOException e) {
            System.out.println("Something wrong with input file " + e.getMessage());
            return;
        }

        try (FileWriter fWriter = new FileWriter(args[1], StandardCharsets.UTF_8);
             Writer writer = new BufferedWriter(fWriter)) {

            writer.write(answer);
        } catch (IOException e) {
            System.out.println("Something wrong with output file " + e.getMessage());
        }
    }
}
