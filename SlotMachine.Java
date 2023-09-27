import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class SlotMachine {

    private final int MAX_LINES = 3;
    private final int MAX_BET = 100;
    private final int MIN_BET = 1;
    private final int ROWS = 3;
    private final int COLS = 3;

    private final int[] symbolCount = {2, 4, 6, 8};
    private final int[] symbolValue = {5, 4, 3, 2};

    private Random random = new Random();
    private Scanner scanner = new Scanner(System.in);

    public int[] checkWinnings(char[][] columns, int lines, int bet) {
        int winnings = 0;
        List<Integer> winningsLines = new ArrayList<>();

        for (int line = 0; line < lines; line++) {
            char symbol = columns[0][line];
            boolean isWinningLine = true;

            for (int col = 0; col < COLS; col++) {
                if (columns[col][line] != symbol) {
                    isWinningLine = false;
                    break;
                }
            }

            if (isWinningLine) {
                winnings += symbolValue[symbol - 'A'] * bet;
                winningsLines.add(line + 1);
            }
        }

        return new int[]{winnings, winningsLines.size()};
    }

    public char[][] getSlotMachineSpin() {
        List<Character> allSymbols = new ArrayList<>();
        for (int i = 0; i < symbolCount.length; i++) {
            char symbol = (char) ('A' + i);
            for (int j = 0; j < symbolCount[i]; j++) {
                allSymbols.add(symbol);
            }
        }

        char[][] columns = new char[COLS][ROWS];

        for (int col = 0; col < COLS; col++) {
            List<Character> currentSymbols = new ArrayList<>(allSymbols);
            for (int row = 0; row < ROWS; row++) {
                int index = random.nextInt(currentSymbols.size());
                char value = currentSymbols.remove(index);
                columns[col][row] = value;
            }
        }

        return columns;
    }

    public void printSlotMachine(char[][] columns) {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                System.out.print(columns[col][row]);
                if (col != COLS - 1) {
                    System.out.print(" | ");
                }
            }
            System.out.println();
        }
    }

    public int spin(int balance) {
        int lines = getNumberOfLines();
        int bet;

        while (true) {
            bet = getBet();
            int totalBet = bet * lines;

            if (totalBet > balance) {
                System.out.println("You do not have enough to bet that amount, your current balance is " + balance);
            } else {
                break;
            }
        }

        System.out.println("You are betting $" + bet + " on " + lines + " lines. Total bet is $" + (bet * lines));

        char[][] slots = getSlotMachineSpin();
        printSlotMachine(slots);

        int[] winningsInfo = checkWinnings(slots, lines, bet);
        int winnings = winningsInfo[0];
        int winningLines = winningsInfo[1];

        if (winnings > 0) {
            System.out.println("You won $" + winnings + ".");
            System.out.println("You won on lines: " + (winningLines == 0 ? "None" : String.join(", ", IntStream.of(winningsInfo[2]).mapToObj(String::valueOf).toArray(String[]::new))));
        } else {
            System.out.println("Sorry, you didn't win this time. Remember, gambling should be for entertainment purposes only.");
        }

        return winnings - (bet * lines);
    }

    public int getNumberOfLines() {
        int lines;
        while (true) {
            System.out.print("Enter the number of lines to bet on (1-" + MAX_LINES + "): ");
            if (scanner.hasNextInt()) {
                lines = scanner.nextInt();
                if (lines >= 1 && lines <= MAX_LINES) {
                    break;
                } else {
                    System.out.println("Enter a valid number of lines.");
                }
            } else {
                System.out.println("Please enter a number.");
                scanner.next(); // Consume invalid input
            }
        }
        return lines;
    }

    public int getBet() {
        int bet;
        while (true) {
            System.out.print("What would you like to bet on each line? ($" + MIN_BET + "-$" + MAX_BET + "): ");
            if (scanner.hasNextInt()) {
                bet = scanner.nextInt();
                if (bet >= MIN_BET && bet <= MAX_BET) {
                    break;
                } else {
                    System.out.println("Amount must be between $" + MIN_BET + " - $" + MAX_BET + ".");
                }
            } else {
                System.out.println("Please enter a number.");
                scanner.next(); // Consume invalid input
            }
        }
        return bet;
    }

    public void main() {
        System.out.println("Welcome to the Responsible Gambling Awareness Program.");
        System.out.println("This program is designed to educate you about responsible gambling.");
        System.out.println("Gambling should be done for fun and not as a way to make money.");
        System.out.println("If you think you have a gambling problem, please seek help.");

        int balance = deposit();

        while (true) {
            System.out.println("Current balance is $" + balance);
            System.out.print("Press enter to play (q to quit): ");
            String answer = scanner.nextLine();
            if (answer.equals("q")) {
                break;
            }
            balance += spin(balance);
        }

        System.out.println("You left with $" + balance);
        System.out.println("Remember to gamble responsibly and seek help if needed.");
        scanner.close();
    }

    public int deposit() {
        int amount;
        while (true) {
            System.out.print("What would you like to deposit? $");
            if (scanner.hasNextInt()) {
                amount = scanner.nextInt();
                if (amount > 0) {
                    break;
                } else {
                    System.out.println("Amount must be greater than 0.");
                }
            } else {
                System.out.println("Please enter a number.");
                scanner.next(); // Consume invalid input
            }
        }
        return amount;
    }

    public static void main(String[] args) {
        SlotMachine slotMachine = new SlotMachine();
        slotMachine.main();
    }
}

