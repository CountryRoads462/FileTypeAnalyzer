import java.util.Scanner;

class RemoveExtraSpacesProblem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String text = scanner.nextLine();

        text = text.replaceAll("\\s+", " ");

        System.out.println(text);
    }
}