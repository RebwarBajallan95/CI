package testTools;

import java.io.*;
import java.lang.reflect.Method;
import java.util.stream.Collectors;

/**
 * Class to run the test suit towards the
 * target project isPrime after the repository
 * has been cloned and the files have been compiled.
 */
public class Tests {
    File results;
    OutputStream os;

    /**
     * Run tests towards the target project
     * @param testDirectory the directory
     * to the compiled files
     */
    public Tests(String testDirectory, OutputStream os) throws Exception {
        results = new File("results.txt");
        this.os = os;
        runTests(testDirectory);
    }

    /**
     * Execute the tests and
     * generate results to the text file results
     * @return The results
     * of the tests
     */
    private void runTests(String testDirectory) throws Exception {
        /**
         * Change to testDirectory later
         */
        File directory = new File(System.getProperty("user.dir") + "/src/testTools");
        File[] directoryList = directory.listFiles();
        for(File f : directoryList){
            if (f.canExecute() && f.getName().contains(".class") && f.getName().contains("Test")){
                System.out.println(f.getName());
                start(f.getName());
            }
        }
    }

    /**
     * @return the test results
     * of the current build
     */
    public File getResults() {
        return results;
    }

    /**
     * Run the program given as an argument
     * @param classname the name of the file to be run
     * @throws Exception thrown if the file cannot be found
     */
    public static void start(final String classname) throws Exception {  // to keep it simple
        final String[] args1 = {};
        String[] splitString = classname.split("[.]");
        Class clazz = Class.forName("testTools." + splitString[0]);
        final Method main = clazz.getMethod("main", String[].class);
        new Thread(() -> {
            try {
                main.invoke(null, new Object[]{args1});
            } catch(Exception e) {
                throw new AssertionError(e);
            }
        }).start();
    }

    public static void main(String[] args) throws Exception {
        OutputStream os = new FileOutputStream("");
        Tests tests = new Tests(" ", os);
        File results = tests.getResults();
    }
}
