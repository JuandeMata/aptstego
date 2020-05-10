import java.io.*;
import java.util.Scanner;

public class MessageReader {

    private Scanner userInput = new Scanner(System.in);
    private String inputPath;
    private String input;

    public String inputSelector() throws IOException {
        System.out.println("Please, specify the kind of hidden message you want to use:");
        do {
            System.out.println("Do you want to insert a message in the terminal?Y/N");
            input = userInput.nextLine().toLowerCase();
        } while (!input.equals("y") && !input.equals("yes") && !input.equals("n") && !input.equals("no"));

        switch (input) {
            case "yes":
            case "y":
                inputPath=insertMessage();
                return inputPath;
            default:
                do {
                    System.out.println("Do you want to hide a file?Y/N");
                    input = userInput.nextLine().toLowerCase();
                } while (!input.equals("y") && !input.equals("yes") && !input.equals("n") && !input.equals("no"));

                switch (input) {
                    case "yes":
                    case "y":
                        inputPath = selectFile();
                        return inputPath;
                    default:
                        System.out.println("None of the available options where seleted");
                }
        }
        return null;
    }

    private String selectFile() throws IOException {
        System.out.println("Please, introduce the full path where the fle is located:");
        String path = userInput.nextLine();
        return path;
    }

    private String insertMessage() throws IOException {
        System.out.println("Please, insert the message you want to send. Press ENTER at the end:");
        byte[] message = userInput.nextLine().getBytes();

        String path = "fileGenerated.txt";
        File file = new File(path);
        FileOutputStream fileOS = new FileOutputStream(file);
        fileOS.write(message);
        fileOS.close();

        return path;
        }
    }


