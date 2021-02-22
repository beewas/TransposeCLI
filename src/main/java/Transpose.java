import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Transpose {
    //set num as -1 if num is not explicit specified
    public static List<List<String>> transpose(List<List<String>> str, int num, boolean trunc, boolean right) {
        List<List<String>> out = new ArrayList<>();
        int max = 0;
        if (num == -1 && (trunc || right)) num = 10;
        for (List<String> strings : str)
            if (strings.size() > max) {
                max = strings.size();
            }
        for (int i = 0; i < max; i++) {
            List<String> add = new ArrayList<>();
            for (List<String> strings : str) {
                if (strings.size() > i) {
                    if (strings.get(i).length() >= num) {
                        if (trunc) add.add(strings.get(i).substring(0, num));
                        else add.add(strings.get(i));
                    } else if (right) add.add(" ".repeat(num - strings.get(i).length()) + strings.get(i));
                    else add.add(strings.get(i) + " ".repeat(num - strings.get(i).length()));
                }
            }
            out.add(add);
        }
        return out;
    }

    public static List<List<String>> getText(Scanner sc) {
        List<List<String>> out = new ArrayList<>();
        String line;
        while (sc.hasNextLine()) {
            ArrayList<String> a = new ArrayList<>();
            line = sc.nextLine();
            for (String s : line.split("\\s+")) {
                if (!s.isEmpty()) a.add(s);
            }
            out.add(a);
        }
        sc.close();
        return out;
    }

    public static String print(List<List<String>> in) {
        StringBuilder out = new StringBuilder();
        for (List<String> x : in) {
            for (int i = 0; i < x.size() - 1; i++) {
                out.append(x.get(i)).append(' ');
            }
            out.append(x.get(x.size() - 1));
            out.append('\n');
        }
        return out.toString();
    }

    public static void main(String[] args) {
        int a = -1;
        int flags = 0;
        boolean trunc = false, right = false;
        String out = "";
        List<List<String>> fine = new ArrayList<>();

        //arguments handling
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-a" -> {
                    i++;
                    a = Integer.parseInt(args[i]);
                    if (a < 0) {
                        System.out.println("The word size cannot be negative!");
                        System.exit(1);
                    }
                    flags++;
                }
                case "-t" -> {
                    trunc = true;
                    flags++;
                }
                case "-r" -> {
                    right = true;
                    flags++;
                }
                case "-o" -> {
                    if (i < args.length - 1) {
                        i++;
                        out = args[i];
                        flags += 2;
                    } else {
                        System.out.println("Empty output filename!");
                        System.exit(1);
                    }
                }
                default -> {
                    if (!new File(args[i]).isFile()) {
                        System.out.println("Wrong argument!");
                        System.exit(1);
                    }
                }
            }
        }

        //working with IO
        if (args.length != flags && new File(args[args.length - 1]).isFile()) {
            try {
                fine = transpose(getText(new Scanner(new File(args[args.length - 1]))), a, trunc, right);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.exit(2);
            }
        } else if (args.length != flags && !(new File(args[args.length - 1]).isFile())) {
            System.out.println("Wrong input filename!");
            System.exit(1);
        } else {
            fine = transpose(getText(new Scanner(System.in)), a, trunc, right);
        }

        if (args.length > 0 && !out.equals("")) {
            try {
                FileWriter fw = new FileWriter(out);
                fw.write(print(fine));
                fw.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.exit(2);
            }
        } else {
            System.out.print(print(fine));
        }
    }
}
