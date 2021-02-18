import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

    /**
     * Initiate arguments to the tests
     */
    public class isPrimeTest {
        int INTEGER_BELOW_TWO = 1;
        int INTEGER_PRIME = 3;
        int INTEGER_EVEN_NUMBER = 12;
        int INTEGER_ODD_NOT_PRIME = 51;
    /**
     * Initiate all tests
     */
    public boolean[] runTests(){
        return new boolean[]{test_One(),test_Two(),test_Three(),test_Four()};
    }

    /**
     * Test with an integer below two.
     * Expected to return false
     * @return if the tests failed or not
     * when using an argument lesser than two
     */
    private boolean test_One(){
        isPrime ispr = new isPrime();
        return ispr.isPrime(INTEGER_BELOW_TWO) ? true : false;
    }

    /**
     * Test with a prime number. Expected
     * to return true since three is
     * a prime number
     * @return if the tests fails or not
     */
    private boolean test_Two(){
        isPrime ispr = new isPrime();
        return ispr.isPrime(INTEGER_PRIME) ? true : false;
    }

    /**
     * Test with an even integer above two
     * Should return to false
     * @return if the tests fails or not
     */
    private boolean test_Three(){
        isPrime ispr = new isPrime();
        return !ispr.isPrime(INTEGER_EVEN_NUMBER) ? true : false;
    }

    /**
     * Test with an odd non-prime integer
     * should return false
     * @return if the test fails or not
     */
    private boolean test_Four(){
        isPrime ispr = new isPrime();
        return !ispr.isPrime(INTEGER_ODD_NOT_PRIME) ? true : false;
    }

    /**
     * method to initiate the tests of
     * isPrime
     * @param args not used
     */
    public static void main(String[] args) {
        isPrimeTest primeTest = new isPrimeTest();
        boolean[] testResults = primeTest.runTests();
        int nrOfTests = testResults.length;
        int nrOfSuccededTests = 0;
        for (int i = 0;i < nrOfTests;i++){
            if (testResults[i]){
            nrOfSuccededTests = nrOfSuccededTests + 1; }
        }
            System.out.println("------ TEST RESULTS ------ \n");
            System.out.println((nrOfTests == nrOfSuccededTests ? "SUCCESS\n" : "FAILED\n"));
            System.out.println(nrOfSuccededTests + " OUT OF " + nrOfTests + " TESTS PASSED\n");
        }
    }