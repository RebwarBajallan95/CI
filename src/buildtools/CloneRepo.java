package buildtools;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CloneRepo {

    private File tempDirectory;


    public CloneRepo(String gitUrl, String branchName, String tempDirPath) throws IOException, GitAPIException {
        tempDirectory = createTempDir(tempDirPath);
        System.out.println(tempDirectory + " created!");
        cloneRepository(gitUrl, branchName, tempDirectory);
    }


    /*
       Clone git repository
    */
    private void cloneRepository(String gitUrl, String branchName, File tempDir) throws GitAPIException {
        System.out.println("Cloning from " + gitUrl + " to " + tempDir);
        try (Git result = Git.cloneRepository()
                .setURI(gitUrl)
                .setBranch(branchName)
                .setDirectory(tempDir)
                .call()) {
            // Note: the call() returns an opened repository already which needs to be closed to avoid file handle leaks!
            System.out.println("Having repository: " + result.getRepository().getDirectory());
        }
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


    public static void main(String[] args) throws IOException, GitAPIException {
        CloneRepo cloneTest = new CloneRepo("https://github.com/RebwarBajallan95/RandomProject",
                                        "testBranch",
                                        "./temp"
                                );

    }

}


