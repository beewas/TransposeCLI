import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Transpose {
    public static List<List<String>> transpose(List<List<String>> str, int num, boolean trunc, boolean right){
        List<List<String>> out = new ArrayList<>();
        int max = 0;
        for (List<String> strings : str) if (strings.size() > max) max = strings.size();
        for (int i = 0; i < max; i++){
            List<String> add = new ArrayList<>();
            for (List<String> strings : str) if (strings.size() > i) {
                if (strings.get(i).length() >= num) {
                    if (trunc) add.add(strings.get(i).substring(0, num));
                    else add.add(strings.get(i));
                }
                else if (right) add.add(" ".repeat(num - strings.get(i).length()) + strings.get(i));
                else add.add(strings.get(i) + " ".repeat(num - strings.get(i).length()));
            }
            out.add(add);
        }
        return out;
    }

    public static List<List<String>> getText(Scanner sc){
        List<List<String>> out = new ArrayList<>();
        String line;
        int level = 0;
        while(sc.hasNextLine()){
            out.add(new ArrayList<>());
            line = sc.nextLine();
            for (String s: line.split("\\s+")) if (!s.isEmpty()) out.get(level).add(s);
            level++;
        }
        sc.close();
        return out;
    }

    public static String print(List<List<String>> in){
        StringBuilder out = new StringBuilder();
        for (List<String> x: in) {
            for (int i = 0; i < x.size() - 1; i++) out.append(x.get(i)).append(' ');
            out.append(x.get(x.size() - 1));
            out.append('\n');
        }
        return out.toString();
    }

    public static void main(String[] args){
        int a = 10;
        boolean trunc = false, right = false;
        String out = "";
        List<List<String>> fine = new ArrayList<>();
        for (int i = 0; i < args.length; i++){
            switch (args[i]) {
                case "-a" -> {
                    i++;
                    a = Integer.parseInt(args[i]);
                }
                case "-t" -> trunc = true;
                case "-r" -> right = true;
                case "-o" -> {
                    i++;
                    out = args[i];
                }
            }
        }

        if (args.length > 0 && new File(args[args.length - 1]).isFile()) try {
                fine = transpose(getText(new Scanner(new File(args[args.length - 1]))), a, trunc, right);
        } catch (Exception e) {
                System.out.println(e.getMessage());
        }
        else if (args.length > 0 && !(new File(args[args.length - 1]).isFile()))
            System.out.println("Wrong input filename!");
        else fine = transpose(getText(new Scanner(System.in)), a, trunc, right);

        if (args.length > 0 && !out.equals("")) try {
            FileWriter fw = new FileWriter(out);
            fw.write(print(fine));
            fw.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        else System.out.print(print(fine));
    }
}
