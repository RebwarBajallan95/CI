package buildtools;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CompileFiles {

    private Map<Path, Integer> results = new HashMap<Path, Integer>();

    CompileFiles(Path pathToFolder, OutputStream output) throws IOException {
        List<Path> paths = listFiles(pathToFolder);
        paths.forEach(x -> {
            try {
                compile(x, output);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /*
        Compile java file given path, output provided in OutputStream
        @Returns int - zero if success, non-zero otherwise
     */
    public void compile (Path javaFilePath, OutputStream output) throws IOException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        int result = compiler.run(null, null, output,
                String.valueOf(javaFilePath));
        results.put(javaFilePath, result);
    }

    /*
       list all files from given path
       @ Returns: List of paths
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

    /*
        @Returns true if filepath maps to a .java file
    */
    public boolean isJavaFile(String Filepath) {
        if (Filepath.endsWith(".java"))
            return true;
        else
            return false;
    }

    /*
       @Returns compile status 1 for success, 0 for failure
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
