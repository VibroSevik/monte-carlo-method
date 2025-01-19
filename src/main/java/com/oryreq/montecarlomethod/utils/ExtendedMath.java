package com.oryreq.montecarlomethod.utils;

/**
 * This class realizes object so-called <strong>ExtendedMath</strong>. <br>
 * It is used to get some extended formulas for calculating like a factorial, combination and Bernoulli formula. <br>
 *
 * @Date: 29.09.2024
 * @Author: Vsevolod @Oryreq Ashihmin
 */
public class ExtendedMath {

    public static int factorial(int n) {
        if (n <= 1) {
            return 1;
        }
        return n * factorial(n - 1);
    }

    public static int combination(int n, int k) {
        return factorial(n) / (factorial(k) * factorial(n - k));
    }

    public static double binomialDistribution(int k, int n, double p) {
        return combination(n, k) * Math.pow(p, k) * Math.pow(1 - p, n - k);
    }

    public static double puassonDistribution(double lambda, int m) {
        return Math.pow(lambda, m) / factorial(m) * Math.exp(-lambda);
    }

    public static double uniformDistribution(int a, int b, double x) {
        if (x <= a) {
            return 0;
        } else if (x >= b) {
            return 1;
        }
        return (b - a) * x + a;
    }

    public static int roundToHundreds(int number) {
        int digitsCount = getDigitsCount(number);
        int firstDigit = number / (int) Math.pow(10, digitsCount - 1);
        if (firstDigit * Math.pow(10, digitsCount - 1) == number) {
            return number;
        }
        return (firstDigit + 1) * (int) Math.pow(10, digitsCount - 1);
    }

    public static int getDigitsCount(int number) {
        int digitsCount = 0;
        while (number > 0) {
            digitsCount++;
            number /= 10;
        }
        return digitsCount;
    }

    public static double convertFromBasicCoordinates(double a, double b, double x) {
        return (x - a) / (b - a);
    }

    public static double convertToBasicCoordinates(double a, double b, double x) {
        return (b - a) * x + a;
    }

    public static double standardLinearTransformation(double multiplier, double summand, double x) {
        return x * multiplier + summand;
    }

    public static double normalDistributionDensity(double mathExpectation, double standardError, double x) {
        var coefficient = 1 / (Math.sqrt(2 * Math.PI) * standardError);
        var power = -Math.pow(x - mathExpectation, 2) / (2 * standardError * standardError);
        return coefficient * Math.exp(power);
    }

}
