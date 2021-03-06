package buildtools;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class to compile Java files
 * from within a Java application
 */
public class CompileFiles {
    private Map<Path, Integer> results = new HashMap<Path, Integer>();
    private Path pathToTemp;

    /**
     * Constructor to compile all
     * files within the directory
     * in the provided file path
     * @param pathToFolder path to directory
     * @throws IOException
     */
    public CompileFiles(Path pathToFolder) throws IOException, InterruptedException {
        File logFile = new File("logFile.txt");
        File errorFile = new File("errorFile.txt");
        pathToTemp = pathToFolder;
        List<Path> paths = listFiles(pathToFolder);
        ProcessBuilder classPath = new ProcessBuilder("java","-cp", pathToTemp.toString());
        Process cp = classPath.start();
        cp.waitFor(5,TimeUnit.SECONDS);
        List<String> args = new LinkedList<String>();
        args.add("javac");
        for(int i=0;i<paths.size();i++){
            args.add(paths.get(i).toString());
        }
        try {
            ProcessBuilder compile = new ProcessBuilder(args);
            compile.directory(new File("."));
            compile.inheritIO();
            compile.redirectError(ProcessBuilder.Redirect.appendTo(errorFile));
            compile.redirectOutput(ProcessBuilder.Redirect.appendTo(logFile));
            Process compileProcess = compile.start();
            compileProcess.waitFor(5, TimeUnit.SECONDS);
        }catch (Exception e){
            System.err.println(e);
        }
    }


    private String getFileName(String path){
        String[] bits = path.split("/");
        return bits[bits.length-1];
    }

    /**
     * Compile java file given path, output provided in OutputStream
     * @param javaFilePath
     * @param output Output from the JavaCompiler object writes to the output stream
     * @throws IOException
     */
    public void compile (Path javaFilePath, OutputStream output) throws IOException {

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager manager = compiler.getStandardFileManager(null, null, null);
        try {
            manager.setLocation(StandardLocation.CLASS_PATH, Arrays.asList(new File(pathToTemp.toString())));
            System.out.println(pathToTemp.toString());
        } catch (Exception e ){
            System.out.println(e);
        }
        int result = compiler.run(null, null, output,
                String.valueOf(javaFilePath));
        results.put(javaFilePath, result);
    }

    /**
     * List all file paths to the
     * files in a directory
     * @param path the file path to a directory
     * @return list with file paths to each file
     * @throws IOException
     */
    public List<Path> listFiles(Path path) throws IOException {
        List<Path> result;
        try (Stream<Path> walk = Files.walk(path)) {
            result = walk.filter(Files::isRegularFile)
                    .filter(FilePath -> isJavaFile(String.valueOf(FilePath)))
                    .collect(Collectors.toList());
        }
        return result;
    }

    /**
     * @param Filepath
     * @return whether file ends
     * with .java or not.
     */
    public boolean isJavaFile(String Filepath) {
        if (Filepath.endsWith(".java"))
            return true;
        else
            return false;
    }

    /**
     * @return compile status 1 for success, 0 for failure
     */
    public int compileStatus() {
        List<Integer> values = new ArrayList<>(results.values());
        for(int i = 0;i<values.size();i++){
            if(values.get(i) !=0)
                return 0;
        }
        return 1;
    }

}