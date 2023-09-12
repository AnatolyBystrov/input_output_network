package telran.io;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class MyFiles {

    public static void displayDir(String path, int maxDepth) throws IOException {
        Path dirPath = Path.of(path).toAbsolutePath().normalize();

        if (!Files.isDirectory(dirPath)) {
            throw new IllegalArgumentException(dirPath + " is not a directory.");
        }

        Files.walk(dirPath, maxDepth)
                .forEach(p -> {
                    int level = p.getNameCount() - dirPath.getNameCount();
                    String indent = " ".repeat(level * 4); // Each level will have an indentation of 4 spaces
                    String nodeType = Files.isDirectory(p) ? "dir" : "file";
                    System.out.println(indent + p.getFileName() + " - " + nodeType);
                });
    }


    public static void displayDirWithFileTree(String path, int maxDepth) throws IOException {
        Path rootPath = Paths.get(path);

        if (!Files.isDirectory(rootPath)) {
            throw new IllegalArgumentException(path + " is not a directory.");
        }

        Files.walkFileTree(rootPath, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                printPath(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                printPath(dir);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) {
                System.err.println("Error accessing file: " + file.toString() + " " + exc.getMessage());
                return FileVisitResult.CONTINUE;
            }

            private void printPath(Path path) {
                int level = rootPath.relativize(path).getNameCount();
                String indentation = "  ".repeat(level);
                String type = Files.isDirectory(path) ? " - dir" : " - file";
                System.out.println(indentation + path.getFileName() + type);
            }
        });
    }
}