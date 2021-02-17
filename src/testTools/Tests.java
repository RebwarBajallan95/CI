package testTools;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class to run the test suit towards the
 * target project isPrime after the repository
 * has been cloned and the files have been compiled.
 */
public class Tests {
    File results;

    /**
     * Run tests towards the target project
     * @param testDirectory the directory
     * to the compiled files
     */
    public Tests(File testDirectory) throws Exception {
        System.out.println("Initiating tests!");
        runTests(testDirectory);
    }

    public List<Path> listFiles(Path path) throws IOException {
        List<Path> result;
        try (Stream<Path> walk = Files.walk(path)) {
            result = walk.filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        }
        return result;
    }

    /**
     * Execute the tests and
     * generate results to the text file results
     * @return The results
     * of the tests
     */
    private void runTests(File testDirectory) throws Exception {
        /**
         * Change to testDirectory later
         */
        for(Path p : listFiles(testDirectory.toPath())){
            File f = p.toFile();
            System.out.println(f.getName());
            if (f.canExecute() && f.getName().contains(".class") && f.getName().contains("Test")){
                start(f.toPath(),testDirectory.toString());
            }
        }
    }

    /**
     * @return the test results
     * of the current build
     */
    public File getResults(){
        return results;
    }

    /**
     * Run the program given as an argument
     * @param classname the name of the file to be run
     * @throws Exception thrown if the file cannot be found
     */
    public static void start(Path classname,String directory) throws Exception {
        System.out.println("Initiating process with class " + classname);
        ProcessBuilder classPath = new ProcessBuilder("java","-cp", directory);
        Process cp = classPath.start();
        cp.waitFor(1,TimeUnit.SECONDS);
        try{
            System.out.println(directory+ "/" +classname);
            ProcessBuilder execute = new ProcessBuilder("java", "isPrimeTest");
            execute.directory(new File(directory+"/src/"));
            execute.redirectError(ProcessBuilder.Redirect.appendTo(new File("errorFile1.txt")));
            execute.redirectOutput(ProcessBuilder.Redirect.appendTo(new File("logFile.txt")));
            Process executionProcess = execute.start();
            executionProcess.waitFor(5, TimeUnit.SECONDS);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public static void main(String[] args) throws Exception {
        //Tests t = new Tests("");
    }
}
