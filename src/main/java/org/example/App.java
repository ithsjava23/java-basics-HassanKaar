package org.example;

import java.util.Locale;
import java.util.Scanner;


public class App {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Locale swedishLocale = new Locale("sv", "SE");
        Locale.setDefault(swedishLocale);
        int[] prices = new int[24];
        String choice;

        do {
            System.out.print(
                    """
                   Elpriser
                   ========
                   1. Inmatning
                   2. Min, Max och Medel
                   3. Sortera
                   4. Bästa Laddningstid (4h)
                   e. Avsluta
                     """);


            choice = scanner.nextLine();
            choice = choice.toLowerCase();

            if (choice.equals("1")) {
                ElPrices(prices, scanner);
            } else if (choice.equals("2")) {
                MinMaxMedel(prices);
            } else if (choice.equals("3")) {
                Sort(prices);
            } else if (choice.equals("4")) {
                BestChargingTime(prices);
            } else if (choice.equals("e")) {
                System.out.print("Programmet avslutas.");
            }
        } while (!choice.equals("e"));
    }

    public static void ElPrices(int[] prices, Scanner scanner) {
        System.out.print("Ange dygnets pris i öre per intervall (2h)\n");

        for (int tim = 0; tim < 24; tim++) {
            System.out.print((tim) + "-" + (tim + 1) + ": ");
            prices[tim] = scanner.nextInt();
            scanner.nextLine();
        }

    }

    public static void MinMaxMedel(int[] prices) {
        int minPrice = Integer.MAX_VALUE;
        int maxPrice = Integer.MIN_VALUE;
        int minTim = -1;
        int maxTim = -1;
        int sum = 0;

        for (int tim = 0; tim < 24; tim++) {
            int price = prices[tim];
            if (price < minPrice) {
                minPrice = price;
                minTim = tim;
            }

            if (price > maxPrice) {
                maxPrice = price;
                maxTim = tim;
            }
            sum += price;
        }
        float medel;
        medel = (float) sum / prices.length;

        System.out.printf("Lägsta pris: %02d-%02d, %d öre/kWh\n", minTim, minTim + 1, minPrice);
        System.out.printf("Högsta pris: %02d-%02d, %d öre/kWh\n", maxTim, maxTim + 1, maxPrice);
        System.out.printf("Medelpris: %.2f öre/kWh\n", medel);
    }

    public static void BestChargingTime(int[] prices) {
        int lowestPrice = Integer.MAX_VALUE;
        int bestStartTime = -1;

        for (int startTime = 0; startTime < 21; startTime++) {
            int totalPrice = 0;
            for (int i = startTime; i < startTime + 4; i++) {
                totalPrice += prices[i];
            }
            if (totalPrice < lowestPrice) {
                lowestPrice = totalPrice;
                bestStartTime = startTime;
            }
        }
        float medelValue = (float) lowestPrice / 4;

        System.out.printf("Påbörja laddning klockan %02d\n", bestStartTime, +3);
        System.out.printf("Medelpris 4h: %.1f öre/kWh\n", medelValue);
    }

    public static void Sort(int[] prices) {
        int[] hours = new int[24];
        for (int i = 0; i < 24; i++) {
            hours[i] = i;
        }
        boolean sorted = false;

        while (!sorted) {
            sorted = true;
            for (int i = 0; i < 23; i++) {
                if (prices[i] < prices[i + 1]) {

                    int tempPrice = prices[i];
                    prices[i] = prices[i + 1];
                    prices[i + 1] = tempPrice;

                    int tempHour = hours[i];
                    hours[i] = hours[i + 1];
                    hours[i + 1] = tempHour;

                    sorted = false;
                }
            }
        }
        for (int i = 0; i < 24; i++) {
            System.out.printf("%02d-%02d %d öre\n", hours[i], hours[i] + 1, prices[i]);
        }
    }
}