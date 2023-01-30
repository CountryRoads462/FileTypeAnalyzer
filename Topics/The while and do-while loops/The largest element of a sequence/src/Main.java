import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int max = Integer.MIN_VALUE;
        while (true) {
            int num = scanner.nextInt();
            if (num == 0) {
                break;
            }
            if (num > max) {
                max = num;
            }
        }
        System.out.println(max);

    }
}