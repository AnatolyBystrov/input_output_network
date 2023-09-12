package telran.io.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class CopyAppl {

    private static final int BUFFER_SIZE = 1024 * 1024;  // 1MB

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        if (!areArgumentsValid(args)) {
            return;
        }

        File sourceFile = new File(args[0]);
        File destinationFile = new File(args[1]);

        if (!isFileCopyPossible(sourceFile, destinationFile, args)) {
            return;
        }

        try {
            copyFile(sourceFile, destinationFile);
            long endTime = System.currentTimeMillis();
            System.out.printf("Successful copying of %d bytes from the file %s to the file %s. Time %dms%n",
                    sourceFile.length(), args[0], args[1], (endTime - startTime));
        } catch (IOException e) {
            System.out.println("Error copying file: " + e.getMessage());
        }
    }

    private static boolean areArgumentsValid(String[] args) {
        if (args.length < 2) {
            System.out.println("Too few arguments");
            return false;
        }
        return true;
    }

    private static boolean isFileCopyPossible(File source, File destination, String[] args) {
        if (!source.exists()) {
            System.out.println("source file " + args[0] + " must exist");
            return false;
        }
        File parentDir = destination.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            System.out.println("The directory " + parentDir.getAbsolutePath() + " must exist");
            return false;
        }
        boolean overwrite = args.length > 2 && "overwrite".equalsIgnoreCase(args[2]);
        if (destination.exists() && !overwrite) {
            System.out.println("File " + args[1] + " cannot be overwritten");
            return false;
        }
        return true;
    }

    private static void copyFile(File source, File destination) throws IOException {
        try (FileInputStream fis = new FileInputStream(source);
             FileOutputStream fos = new FileOutputStream(destination)) {

            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;

            while ((bytesRead = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
        }
    }
}
