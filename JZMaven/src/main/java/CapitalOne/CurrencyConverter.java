package CapitalOne;

class CurrencyConverter {
    double EXCHANGE_RATE = 0.85d;
    int[] eu_coins = new int[]{200, 100, 50, 20, 10, 5, 2, 1};

    public int numOfCoins(int amount_usd) {
        int euro_in_cents = (int) (amount_usd * EXCHANGE_RATE * 100);
        int res = 0, i = 0;
        while (euro_in_cents > 0) {
            if (euro_in_cents >= eu_coins[i]) {
                res += euro_in_cents / eu_coins[i];
                euro_in_cents = euro_in_cents % eu_coins[i];
            }
            i++;
        }
        return res;
    }
}
