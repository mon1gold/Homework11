import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

public class Main {
    private static File currentDirectory;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        currentDirectory = new File(System.getProperty("user.dir"));

        while (true) {
            System.out.print(currentDirectory.getAbsolutePath() + "> ");
            String[] command = scanner.nextLine().trim().split("\\s+");

            switch (command[0]) {
                case "cd":
                    if (command.length < 2) {
                        System.out.println("Usage: cd <directory>");
                        break;
                    }
                    changeDirectory(command[1]);
                    break;
                case "ls":
                    listDirectory();
                    break;
                case "pwd":
                    printWorkingDirectory();
                    break;
                case "cp":
                    if (command.length < 3) {
                        System.out.println("Usage: cp <source> <destination>");
                        break;
                    }
                    copyFile(command[1], command[2]);
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("Unknown command: " + command[0]);
            }
        }
    }

    private static void changeDirectory(String directoryPath) {
        File newDirectory;

        if (directoryPath.startsWith("/")) {
            newDirectory = new File(directoryPath);
        } else {
            newDirectory = new File(currentDirectory, directoryPath);
        }

        if (!newDirectory.exists()) {
            System.out.println("Directory not found: " + directoryPath);
            return;
        }

        if (!newDirectory.isDirectory()) {
            System.out.println(directoryPath + " is not a directory");
            return;
        }

        currentDirectory = newDirectory;
    }

    private static void listDirectory() {
        File[] files = currentDirectory.listFiles();
        if (files == null) {
            System.out.println("Unable to read directory: " + currentDirectory.getAbsolutePath());
            return;
        }

        for (File file : files) {
            System.out.println(file.getName());
        }
    }

    private static void printWorkingDirectory() {
        System.out.println(currentDirectory.getAbsolutePath());
    }

    private static void copyFile(String sourcePath, String destinationPath) {
        File source = new File(sourcePath);
        File destination = new File(destinationPath);

        if (!source.exists()) {
            System.out.println("File not found: " + sourcePath);
            return;
        }

        if (destination.isDirectory()) {
            destination = new File(destination, source.getName());
        }

        try {
            Files.copy(source.toPath(), destination.toPath());
        } catch (IOException e) {
            System.out.println("Unable to copy file: " + e.getMessage());
        }
    }
}