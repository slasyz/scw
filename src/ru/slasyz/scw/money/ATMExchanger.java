package ru.slasyz.scw.money;

import com.sun.deploy.util.ArrayUtil;

import java.util.*;

public class ATMExchanger {
    private int[] coins;

    public ATMExchanger(int[] coins) {
        this.coins = coins;
        Arrays.sort(this.coins);

        // Reverse, i.e. make it sorted in descending order.
        for (int i = 0; i < this.coins.length / 2; i++) {
            int tmp = this.coins[i];
            this.coins[i] = this.coins[this.coins.length - i - 1];
            this.coins[this.coins.length - i - 1] = tmp;
        }
    }

    public int[] getCoins() {
        return coins;
    }

    public List<int[]> getExchangeList(int sum) {
        return step(sum, 0, new int[coins.length]);
    }

    private List<int[]> step(int sum, int start, int[] countToStart) {
        List<int[]> result = new ArrayList<>();
        int[] newCountToStart = Arrays.copyOf(countToStart, countToStart.length);

        if (start < coins.length - 1) {
            // Not last step:
            // iterate by count of "start"-th coin.
            for (int cnt = 0; cnt <= sum / coins[start]; cnt++) {
                newCountToStart[start] = cnt;

                List<int[]> stepResult = step(sum - cnt*coins[start], start+1, newCountToStart);
                result.addAll(stepResult);
            }
        } else {
            // Last step:
            // check if we can make correct exchange, then append it to result.
            if (sum % coins[start] == 0) {
                newCountToStart[start] = sum / coins[start];
                result.add(newCountToStart);
            }
        }

        return result;
    }

    private boolean isCorrect(int sum, int[] exchange) {
        int actualSum = 0;

        for (int i = 0; i < exchange.length; i++)
            actualSum += exchange[i] * coins[i];

        return actualSum == sum;
    }

    public static void main(String[] argv) {
        // Input data.
        Scanner in = new Scanner(System.in);

        boolean fail = false;
        int sum = 0;
        try {
            System.out.print("Введите сумму: ");
            sum = in.nextInt();
            in.nextLine();

            if (sum <= 0)
                fail = true;
        } catch (InputMismatchException ex) {
            fail = true;
        }

        // Output an error message and exit.
        if (fail) {
            System.out.println("Сумма должна быть целым числом больше нуля.");
            System.exit(1);
        }

        System.out.print("Введите номиналы монет через пробел: ");
        List<String> coinsString = Arrays.asList(in.nextLine().split("\\s+"));

        // Put all coins to set to ensure uniqueness.
        Set<Integer> coinSet = new HashSet<>();
        for (String coin : coinsString) {
            fail = false;
            int input;

            try {
                input = Integer.parseInt(coin);
                coinSet.add(input);

                // Wrong value of coin (must be natural number).
                if (input <= 0)
                    fail = true;
            } catch (NumberFormatException ex) {
                // Wrong number format.
                fail = true;
            }

            // Output an error message and exit.
            if (fail) {
                System.out.println("Номиналы монет должны быть целыми числами больше нуля.");
                System.exit(1);
            }
        }

        // Move it to array.
        int[] coinList = new int[coinSet.size()];
        int i = 0;
        for (int coin : coinSet) {
            coinList[i] = coin;
            i++;
        }

        // Start the algorithm.
        ATMExchanger exchanger = new ATMExchanger(coinList);
        List<int[]> exchanges = exchanger.getExchangeList(sum);

        // Output the result.
        System.out.println("Список разменов:");
        for (int[] exchange : exchanges) {
            for (int coin : exchange)
                System.out.printf("%d ", coin);
            System.out.println();
        }

        System.out.print("(монеты:");
        for (int coin : exchanger.getCoins())
            System.out.printf(" %d", coin);
        System.out.println(")");
        System.out.printf("Всего %s разменов.", exchanges.size());
    }
}
