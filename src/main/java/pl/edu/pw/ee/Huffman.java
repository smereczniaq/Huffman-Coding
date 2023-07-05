package pl.edu.pw.ee;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Huffman {

    public int huffman(String pathToRootDir, boolean compress) throws IllegalCharacterException {
        if (pathToRootDir == null) {
            throw new IllegalArgumentException("You must give correct filename!");
        }

        if (compress) {
            int sizeOfCompressedFile = encode(pathToRootDir, true);
            int sizeOfTree = encode(pathToRootDir, false);
            return sizeOfCompressedFile + sizeOfTree;
        } else {
            int amountOfCharsInDecompressedFile = decode(pathToRootDir);
            return amountOfCharsInDecompressedFile;
        }
    }

    private int encode(String pathToRootDir, boolean isTree) {
        String originalText = "";
        int size;

        originalText = originalFileText(pathToRootDir);

        for (int i = 0; i < originalText.length(); i++) {
            if (originalText.charAt(i) > 127) {
                throw new IllegalCharacterException("Unknown character!");
            }
        }

        HuffmanTree tree = new HuffmanTree(originalText);

        try {
            size = tree.exportCompressed(removeFileExtension(pathToRootDir, true), isTree);
        } catch (IOException e) {
            e.printStackTrace();
            size = 0;
        }

        return size;
    }

    private int decode(String pathToRootDir) {
        String fileText = "";
        HuffmanTree tree = new HuffmanTree();
        String treeFile = removeFileExtension(pathToRootDir, true) + "Tree.txt";
        ArrayList<Character> treeAsArrayList = tree.treeFromFileAsArrayList(treeFile);
        tree.setRoot(tree.setDecodingTree(treeAsArrayList, true));
        String fileInBinary = tree.convertBytesToBinary(pathToRootDir);
        int zerosAdded = Integer.parseInt(fileInBinary.substring(0, 8), 2);
        int i, j;
        char characterDecoded = Character.MIN_VALUE;
        String characterCode = "";

        for (i = 8; i < (fileInBinary.length() - zerosAdded); i++) {
            characterCode += fileInBinary.charAt(i);
            characterDecoded = tree.getCharKey(characterCode);

            if (characterDecoded != Character.MIN_VALUE) {
                fileText += characterDecoded;
                characterCode = "";
            }
        }

        try {
            String decompressedFileName = removeFileExtension(pathToRootDir, true) + "Decompressed.txt";
            FileWriter exitFile = new FileWriter(decompressedFileName);
            exitFile.write(fileText);
            exitFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileText.length();
    }

    private String originalFileText(String pathToRootDir) {
        File originalFile = new File(pathToRootDir);
        String originalFileText = "";
        String nextLine;

        try {
            Scanner reader = new Scanner(originalFile);

            while (reader.hasNextLine()) {
                originalFileText += reader.nextLine();

                if (reader.hasNextLine()) {
                    originalFileText += '\n';
                }
            }

            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return originalFileText;
    }

    private static String removeFileExtension(String filename, boolean removeAllExtensions) {
        if (filename == null || filename.isEmpty()) {
            return filename;
        }

        String extPattern = "(?<!^)[.]" + (removeAllExtensions ? ".*" : "[^.]*$");
        return filename.replaceAll(extPattern, "");
    }
}
