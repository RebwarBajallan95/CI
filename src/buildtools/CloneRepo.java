package buildtools;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Class to clone a repository
 */
public class CloneRepo {
    private File tempDirectory;

    /**
     * Constructor to clone a repository
     * from github
     * @param gitUrl the URL to the github repository
     * @param branchName name of the branch where the push is being made
     * @param tempDirPath path to put a temporary directory
     * @throws IOException
     * @throws GitAPIException
     */
    public CloneRepo(String gitUrl, String branchName, String tempDirPath) throws IOException, GitAPIException {
        tempDirectory = createTempDir(tempDirPath);
        System.out.println(tempDirectory + " created!");
        cloneRepository(gitUrl, branchName, tempDirectory);
    }


    /**
     *
     * @param gitUrl the URL to the github repository
     * @param branchName name of the branch where the push is being made
     * @param tempDir the temporary directory to put all logs produced
     *                during the tests
     * @throws GitAPIException
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


    /**
     * @param tempDirPath path to put the temporary directory
     * @return a directory
     * @throws IOException
     */
    private File createTempDir(String tempDirPath) throws IOException {
        File tempdir = Files.createTempDirectory(
                Paths.get(tempDirPath),
                "TempDir")
                .toFile();
        return tempdir;
    }


    /**
     * @param tempDir the directory to be deleted
     */
    public void deleteDir(File tempDir){
        try {
            System.out.println("Directory " + tempDir + " deleted!");
            FileUtils.deleteDirectory(tempDir);
        } catch (Exception e) {
            System.out.println("Directory " + tempDir + " could not be deleted!");
        }
    }


    /**
     * @param Dir directory to be set as the
     *            temporary directory
     */
    public void setTempDir(File Dir){ tempDirectory = Dir; }


    /**
     * @return the temporary directory
     */
    public File getTempDir(){ return tempDirectory; }


    /**
     * @param args Not used
     * @throws IOException
     * @throws GitAPIException
     */
    public static void main(String[] args) throws IOException, GitAPIException {
        CloneRepo cloneTest = new CloneRepo("https://github.com/RebwarBajallan95/RandomProject",
                                        "testBranch",
                                        "./temp"
                                );

    }

}


