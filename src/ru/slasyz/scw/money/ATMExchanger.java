package ru.slasyz.scw.money;

import com.sun.deploy.util.ArrayUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
}
