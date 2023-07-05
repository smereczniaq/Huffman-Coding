package pl.edu.pw.ee;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import junit.framework.Assert;
import org.junit.Test;

public class HuffmanTest {

    @Test(expected = IllegalArgumentException.class)
    public void huffmanCheckIfNullException() {
        //given
        Huffman huffman = new Huffman();
        String filename = null;

        //when
        huffman.huffman(filename, true);

        //then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void readNotExistingFile() {
        //given
        Huffman huffman = new Huffman();
        String filename = "filename";

        //when
        huffman.huffman(filename, true);

        //then
        assert false;
    }

    @Test(expected = IllegalCharacterException.class)
    public void huffmanCatchIllegalCharacterException() {
        //given
        Huffman huffman = new Huffman();
        String originalFileName = "niemanie.txt";

        //when
        huffman.huffman(originalFileName, true);

        //then
        assert false;
    }

    @Test
    public void huffmanCheckIfCompressedFileIsSmallerThanOriginal() {
        //given
        Huffman huffman = new Huffman();
        String fileToCompressName = "niemanie_bez_znakow.txt";
        File originalFile = new File(fileToCompressName);

        //when
        int originalFileSize = (int) originalFile.length();
        int compressedFileSize = huffman.huffman(fileToCompressName, true);

        //then
        Assert.assertTrue(originalFileSize > compressedFileSize);
    }

    @Test
    public void checkIfOriginalAndDecompressedFilesAreEqual() {
        //given
        Huffman huffman = new Huffman();
        String originalFileName = "niemanie_bez_znakow.txt";
        String compressedFileName = "niemanie_bez_znakowCompressed.txt";
        String decompressedFileName = "niemanie_bez_znakowCompressedDecompressed.txt";
        String originalText = "g";
        String decompressedText = "";

        huffman.huffman(originalFileName, true);
        huffman.huffman(compressedFileName, false);

        //when
        try {
            File originalFile = new File(originalFileName);
            File decompressedFile = new File(decompressedFileName);

            Scanner originalFileReader = new Scanner(originalFile);
            Scanner decompressedFileReader = new Scanner(decompressedFile);

            originalText = "";

            while (originalFileReader.hasNextLine()) {
                originalText += originalFileReader.nextLine();
            }

            while (decompressedFileReader.hasNextLine()) {
                decompressedText += decompressedFileReader.nextLine();
            }

            originalFileReader.close();
            decompressedFileReader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //then
        Assert.assertEquals(originalText, decompressedText);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkIfEncodingFileIsEmptyTest() {
        //given
        Huffman huffman = new Huffman();
        String fileToCompressName = "pusty_plik.txt";

        //when
        huffman.huffman(fileToCompressName, true);

        //then
        assert false;
    }

    @Test
    public void huffmanCheckIfAmountOfCharsInDecompressedEqualsOriginalWhenSingleChar() {
        //given
        Huffman huffman = new Huffman();
        String originalFileName = "pojedynczy_znak.txt";
        String fileToDecompressName = "pojedynczy_znakCompressed.txt";

        //when
        huffman.huffman(originalFileName, true);
        File originalFile = new File(originalFileName);
        int amountOfCharsInDecompressedFile = huffman.huffman(fileToDecompressName, false);
        int amountOfCharsInOriginalFile = (int) originalFile.length();

        //then
        Assert.assertEquals(amountOfCharsInOriginalFile, amountOfCharsInDecompressedFile);
    }
    
    @Test
    public void huffmanCompressTest(){
        //given
        Huffman huffman = new Huffman();
        String originalFileName = "Huffman_Strzeminski_oddanie_2022.txt";
        
        //when
        huffman.huffman(originalFileName, true);
    }
    
    @Test
    public void huffmanDecompressTest(){
        //given
        Huffman huffman = new Huffman();
        String fileName = "Huffman_Strzeminski_oddanie_2022Compressed.txt";
        
        //when
        huffman.huffman(fileName, false);
    }
}
