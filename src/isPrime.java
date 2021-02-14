/**
 * Class to determine if an integer
 * is a prime number or not
 */
public class isPrime {
    /**
     * Funktion to determine if an Integer
     * is a prime number or not
     * @param n the number to be tested
     * @return boolean value whether value
     * is prime or not
     */
    public static boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }
        for (int i = 2; i < n; i++) {
            if(n % i == 0){
                return false;
            }
        }
        return true;
    }
}

// TEST