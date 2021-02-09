package buildtools;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CloneRepo {

    private File tempDirectory;


    CloneRepo(String tempDirPath) throws IOException {
        tempDirectory = createTempDir(tempDirPath);
        System.out.println(tempDirectory + " created!");
    }

    /*
        Creates temporary directory
    */
    private File createTempDir(String tempDirPath) throws IOException {
        File tempdir = Files.createTempDirectory(
                Paths.get(tempDirPath),
                "TempDir")
                .toFile();
        return tempdir;
    }

    /*
      Deletes directory
    */
    public void deleteDir(File tempDir){
        try {
            System.out.println("Directory " + tempDir + " deleted!");
            FileUtils.deleteDirectory(tempDir);
        } catch (Exception e) {
            System.out.println("Directory " + tempDir + " could not be deleted!");
        }
    }

    /*
    Set temporary directory
    */
    public void setTempDir(File Dir){ tempDirectory = Dir; }

    /*
        Get temporary directory
    */
    public File getTempDir(){ return tempDirectory; }


    public static void main(String[] args) throws IOException {
        CloneRepo cloneTest = new CloneRepo(".");

    }

}


