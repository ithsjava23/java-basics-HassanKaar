package org.example;

import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;


    public class App {
        public static void main(String[] args) {
            Scanner input = new Scanner(System.in);
            Locale swedishLocale;
            swedishLocale = new Locale ("sv", "SE");
            Locale.setDefault(swedishLocale);
            int[] ePrices = new int[24];
            boolean slide;
            slide = true;

            while (slide) {
                System.out.println("Elpriser");
                System.out.println("========");
                System.out.println("1. Inmatning");
                System.out.println("2. Min, Max och Medel");
                System.out.println("3. Sortera");
                System.out.println("4. Bästa Laddningstid (4h)");
                System.out.println("e. Avsluta");


                String option = input.next();

                if (option.equalsIgnoreCase("1"))
                    inputElpriser(ePrices, input);
                else if (option.equalsIgnoreCase("2"))
                    minMaxMedel (ePrices);
                else if (option.equalsIgnoreCase("3"))
                    sorteraElpriser(ePrices);
                else if (option.equalsIgnoreCase("4"))
                    bestChargingTime(ePrices);
                else if (option.equalsIgnoreCase("e"))
                    slide = false;
                else
                    System.out.println("Ogiltigt val. Försök igen.");

            }

            System.out.println("Programmet avslutas.");
        }


        private static void inputElpriser(int[] elpriser, Scanner input) {
            System.out.println("Ange dygnets pris i öre per intervall (2h):");

            int i = 0;
            int timme = 0;

            while (i < elpriser.length) {
                int nastaTimme = (timme + 1) % 24;

                System.out.printf("%02d-%02d: ", timme, nastaTimme);
                elpriser[i] = input.nextInt();

                i++;
                timme = nastaTimme;
            }
        }

        public static void minMaxMedel(int[] prices) {
            int minPrice = Integer.MAX_VALUE;
            int maxPrice = Integer.MIN_VALUE;
            int minTim = -1;
            int maxTim = -1;
            int total = 0;

            for (int tim = 0; tim < 24; tim++) {
                int price = prices[tim];
                total += price;

                if (price < minPrice) {
                    minPrice = price;
                    minTim = tim;
                }

                if (price > maxPrice) {
                    maxPrice = price;
                    maxTim = tim;
                }
            }

            double average = (double) total / prices.length;

            System.out.println("Lägsta pris: " + minPrice + " öre per kW/h (kl " + minTim + ")");
            System.out.println("Högsta pris: " + maxPrice + " öre per kW/h (kl " + maxTim + ")");
            System.out.println("Medelpris: " + average + " öre");

            System.out.println("Timmar med lägsta pris:");
            for (int i = 0; i < prices.length; i++) {
                if (prices[i] == minPrice) {
                    System.out.print(i + "-" + (i + 1) % 24 + " ");
                }
            }
            System.out.println();

            System.out.println("Timmar med högsta pris:");
            for (int i = 0; i < prices.length; i++) {
                if (prices[i] == maxPrice) {
                    System.out.print(i + "-" + (i + 1) % 24 + " ");
                }
            }
            System.out.println();
        }


        private static void sorteraElpriser(int[] elpriser) {
            int[] kopieradePriser = Arrays.copyOf(elpriser, elpriser.length);
            Arrays.sort(kopieradePriser);

            System.out.println("Sortering från dyrast till billigast:");
            for (int i = elpriser.length - 1; i >= 0; i--) {
                System.out.printf("%02d-%02d %d öre%n", i, i + 1, kopieradePriser[i]);
            }
        }

        private static void bestChargingTime(int[] elpriser) {
            int[] kopieradePriser = Arrays.copyOf(elpriser, elpriser.length);
            int billigasteTotalPris = Integer.MAX_VALUE;
            int startTimme = 0;

            for (int i = 0; i <= elpriser.length - 4; i++) {
                int totalPris = kopieradePriser[i] + kopieradePriser[i + 1] + kopieradePriser[i + 2] + kopieradePriser[i + 3];
                if (totalPris < billigasteTotalPris) {
                    billigasteTotalPris = totalPris;
                    startTimme = i;
                }
            }

            int slutTimme = startTimme + 3;
            int medelPris = billigasteTotalPris / 4;

            System.out.println("Bästa laddningstid (4h):");
            System.out.println("Påbörja laddning kl: " + startTimme + ":00 - Sluttid: " + slutTimme + ":59");
            System.out.println("Medelpris 4h: " + medelPris + " öre per kW/h");
        }
    }


