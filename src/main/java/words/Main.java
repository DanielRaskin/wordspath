package words;

import java.io.InputStream;

public class Main {
    public static void main(String[] args) throws Exception {
        WordPathFinder finder;
        try (InputStream is = Main.class.getResourceAsStream("/russian_nouns.txt")) {
            finder = new WordPathFinder(new String(is.readAllBytes()));
        }
        if (args.length != 2) {
            System.err.println("Usage: java Main <from word> <to word>");
            System.exit(1);
        }
        var result = finder.findPath(args[0], args[1]);
        if (result == null) {
            System.out.println("No path found");
        }  else {
            for (var string : result) {
                System.out.println(string);
            }
        }
    }
}