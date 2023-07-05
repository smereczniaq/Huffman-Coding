package pl.edu.pw.ee;

import java.io.IOException;
import java.util.ArrayList;
import junit.framework.Assert;
import org.junit.Test;

public class HuffmanTreeTest {

    @Test(expected = IllegalArgumentException.class)
    public void convertBytesToBinaryCheckIfNullTest() {
        //given
        HuffmanTree tree = new HuffmanTree();
        String pathToRootDir = null;

        //when
        tree.convertBytesToBinary(pathToRootDir);

        //then
        assert false;
    }

    @Test
    public void convertBytesToBinaryTest() {
        //given
        HuffmanTree tree = new HuffmanTree();
        String pathToRootDir = "slowo_niemanieCompressedTree.txt";

        //when
        String result = tree.convertBytesToBinary(pathToRootDir);
        String expected = "0000011100010110000110110110110110010101011010011011011100000000";

        //then
        Assert.assertEquals(expected, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void treeFromFileAsArrayListCheckIfNullTest() {
        //given
        HuffmanTree tree = new HuffmanTree();
        String pathToRootDir = null;

        //when
        tree.treeFromFileAsArrayList(pathToRootDir);

        //then
        assert false;
    }

    @Test
    public void treeFromFileAsArrayListTest() {
        //given
        HuffmanTree tree = new HuffmanTree();
        String pathToRootDir = "slowo_niemanieCompressedTree.txt";

        //when
        String expectedTreeInString = "0001a1m1e01i1n";
        ArrayList<Character> expected = new ArrayList<>();
        ArrayList<Character> result = tree.treeFromFileAsArrayList(pathToRootDir);

        for (int i = 0; i < expectedTreeInString.length(); i++) {
            expected.add(expectedTreeInString.charAt(i));
        }

        //then
        Assert.assertEquals(expected, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setDecodingTreeCheckIfNullTest() {
        //given
        HuffmanTree tree = new HuffmanTree();

        //when
        tree.setDecodingTree(null, true);

        //then
        assert false;
    }

    @Test
    public void setDecodingTreeOneNodeTest() {
        //given
        HuffmanTree huffmanTree = new HuffmanTree();
        HuffmanNode tree = new HuffmanNode('a');
        tree.setCode("0");
        HuffmanNode treeFromArrayList;
        ArrayList<Character> treeInArrayList = new ArrayList<>();

        //when
        treeInArrayList.add('1');
        treeInArrayList.add('a');
        treeFromArrayList = huffmanTree.setDecodingTree(treeInArrayList, true);
        boolean areTreesEquals = (tree.getKey() == treeFromArrayList.getKey())
                && (tree.getFrequency() == treeFromArrayList.getFrequency())
                && (tree.getCode().equals(treeFromArrayList.getCode()))
                && (tree.hasChild() == treeFromArrayList.hasChild());

        //then
        Assert.assertTrue(areTreesEquals);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getCharKeyCheckIfNullTest() {
        //given
        HuffmanTree huffmanTree = new HuffmanTree();

        //when
        huffmanTree.getCharKey(null);

        //then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void getCharKeyCheckEmptyCodeTest() {
        //given
        HuffmanTree huffmanTree = new HuffmanTree();

        //when
        huffmanTree.getCharKey("");

        //then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void exportDecompressedCheckIfFileTextNullTest() {
        //given
        HuffmanTree huffmanTree = new HuffmanTree();

        //when
        huffmanTree.exportDecompressed(null, "");

        //then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void exportDecompressedCheckIfPathNullTest() {
        //given
        HuffmanTree huffmanTree = new HuffmanTree();

        //when
        huffmanTree.exportDecompressed("", null);

        //then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void exportCompressedCheckIfPathNullTest() throws IOException {
        //given
        HuffmanTree huffmanTree = new HuffmanTree();

        //when
        huffmanTree.exportCompressed(null, true);

        //then
        assert false;
    }
}
