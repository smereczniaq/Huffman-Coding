package pl.edu.pw.ee;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanTree {

    private HuffmanNode root;
    static private String text;

    HuffmanTree(String text) {
        this.text = text;
        setEncodingTree();
    }

    HuffmanTree() {
        root = new HuffmanNode();
    }

    public void setRoot(HuffmanNode root) {
        this.root = root;
    }

    public HuffmanNode getRoot() {
        return root;
    }

    public int exportCompressed(String pathToRootDir, boolean isTree) throws IOException { 
        if (pathToRootDir == null) {
            throw new IllegalArgumentException("Given path cannot be a null!");
        }

        String result = isTree ? bitCodeTree() : bitCodeText();
        String outputFileName = isTree ? pathToRootDir + "CompressedTree.txt" : pathToRootDir + "Compressed.txt";

        int zerosToAdd = 8 - (result.length() % 8);

        FileOutputStream writer = new FileOutputStream(outputFileName);

        String substringToEncode;

        writer.write((byte) Integer.parseInt(addZerosAtBeginningOfBinary(Integer.toBinaryString(zerosToAdd)), 2));

        for (int i = 0; i < zerosToAdd; i++) {
            result += "0";
        }

        for (int i = 0; i < result.length(); i += 8) {
            substringToEncode = "";

            for (int j = 0; j < 8; j++) {
                substringToEncode += result.charAt(i + j);
            }

            byte byteOfSubstring = (byte) Integer.parseInt(substringToEncode, 2);

            writer.write(byteOfSubstring);
        }

        return (int) writer.getChannel().size();
    }

    public int exportDecompressed(String fileText, String pathToRootDir) {
        if (fileText == null) {
            throw new IllegalArgumentException("Given fileText cannot be a null!");
        }
        if (pathToRootDir == null) {
            throw new IllegalArgumentException("Given path cannot be a null!");
        }

        File writtenFile = null;

        try {
            FileWriter writer = new FileWriter(pathToRootDir);
            writer.write(fileText);
            writtenFile = new File(pathToRootDir);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return (int) writtenFile.length();
    }

    private Map<Character, Integer> calculateFrequenciesOfNodes() {
        Map<Character, Integer> dict = new HashMap<Character, Integer>();

        for (char c : text.toCharArray()) {
            Integer val = dict.get(c);
            if (val != null) {
                dict.put(c, val + 1);
            } else {
                dict.put(c, 1);
            }
        }

        return dict;
    }

    private PriorityQueue<HuffmanNode> setPqOfChars() {
        Map<Character, Integer> map = calculateFrequenciesOfNodes();

        int sizeOfPq = map.size();
        PriorityQueue<HuffmanNode> result
                = new PriorityQueue<>(sizeOfPq, new HuffmanNodeComparator());

        for (Map.Entry<Character, Integer> set : map.entrySet()) {
            char key = set.getKey();
            int value = set.getValue();
            result.add(new HuffmanNode(key, value));
        }

        return result;
    }

    private void validateChilds(HuffmanNode n) {
        if (!n.getLeftChild().hasChild() && n.getRightChild().hasChild()) {
            HuffmanNode tmp = n.getLeftChild();
            n.setLeftChild(n.getRightChild());
            n.setRightChild(tmp);
        }

        if (n.getRightChild().getFrequency() > n.getLeftChild().getFrequency()) {
            HuffmanNode tmp = n.getLeftChild();
            n.setLeftChild(n.getRightChild());
            n.setRightChild(tmp);
        }
    }

    private void setEncodingTree() {
        PriorityQueue<HuffmanNode> pq = setPqOfChars();
        HuffmanNode leftChild;
        HuffmanNode rightChild;
        HuffmanNode mergedNodes;
        boolean onlyOneNode = true;

        while (pq.size() > 1) {
            onlyOneNode = false;
            leftChild = pq.poll();
            rightChild = pq.poll();
            int valuesOfChilds = leftChild.getFrequency() + rightChild.getFrequency();
            mergedNodes = new HuffmanNode(valuesOfChilds, leftChild, rightChild);
            validateChilds(mergedNodes);
            pq.add(mergedNodes);
        }

        root = pq.poll();

        if (onlyOneNode) {
            root.setCode("0");
        } else {
            setCharCode(root);
        }
    }

    private void setCharCode(HuffmanNode root) {
        setCharCode(root, "");
    }

    private void setCharCode(HuffmanNode head, String code) {
        if (head != null) {
            setCharCode(head.getLeftChild(), code + "0");
            setCharCode(head.getRightChild(), code + "1");

            if (!head.hasChild()) {
                head.setCode(code);
            }
        }
    }

    private void addNodesToList(HuffmanNode head, ArrayList<HuffmanNode> listOfNodes) {

        if (head != null) {
            addNodesToList(head.getLeftChild(), listOfNodes);
            addNodesToList(head.getRightChild(), listOfNodes);

            listOfNodes.add(head);
        }
    }

    public char getCharKey(String code) { // do testowania
        if (code == null) {
            throw new IllegalArgumentException("Given character code cannot be a null!");
        }

        if (code.equals("")) {
            throw new IllegalArgumentException("Given code cannot be empty!");
        }

        ArrayList<HuffmanNode> listOfNodes = new ArrayList<>();

        addNodesToList(root, listOfNodes);
        int i;

        for (i = 0; i < listOfNodes.size(); i++) {
            if (code.equals(listOfNodes.get(i).getCode())) {
                break;
            }
        }

        return i == listOfNodes.size() ? Character.MIN_VALUE : listOfNodes.get(i).getKey();
    }

    private HuffmanNode getCharCode(char c, HuffmanNode node) {
        if (node != null) {
            if (node.getKey() == c) {
                return node;
            } else {
                HuffmanNode foundNode = getCharCode(c, node.getLeftChild());
                if (foundNode == null) {
                    foundNode = getCharCode(c, node.getRightChild());
                }
                return foundNode;
            }
        } else {
            return null;
        }
    }

    private String bitCodeText() {
        String result = "";

        for (int i = 0; i < text.length(); i++) {
            result += getCharCode(text.charAt(i), root).getCode();
        }

        return result;
    }

    private String bitCodeTree() {
        String result = "";

        result = bitCodeTree(root, result);

        result = result.trim();

        return result;
    }

    private String bitCodeTree(HuffmanNode head, String result) {
        if (head != null) {
            char c = head.getKey();
            if (head.hasChild()) {
                result += "0";
            } else {
                result += "1";
                String charInBinaryString = addZerosAtBeginningOfBinary(Integer.toBinaryString(c));
                result += charInBinaryString;
            }

            result = bitCodeTree(head.getLeftChild(), result);
            result = bitCodeTree(head.getRightChild(), result);
        }

        return result;
    }

    public HuffmanNode setDecodingTree(ArrayList<Character> tree, boolean isRoot) { // do testowania
        if (tree == null) {
            throw new IllegalArgumentException("Tree in ArrayList cannot be a null!");
        }

        HuffmanNode currentNode = new HuffmanNode();

        char c = tree.remove(0);

        if (isRoot && c == '1') {
            currentNode.setKey(tree.remove(0));
            currentNode.setCode("0");
            return currentNode;
        }

        if (c == '1' && !isRoot) {
            currentNode.setKey(tree.remove(0));
            return currentNode;
        } else {
            currentNode.setLeftChild(setDecodingTree(tree, false));
            currentNode.setRightChild(setDecodingTree(tree, false));
        }

        setCharCode(currentNode);

        return currentNode;
    }

    public ArrayList<Character> treeFromFileAsArrayList(String pathToRootDir) {
        if (pathToRootDir == null) {
            throw new IllegalArgumentException("Given argument is a null!");
        }

        String treeInBinaryCode = convertBytesToBinary(pathToRootDir);
        String resultInString = "";

        int addedZeros = Integer.parseInt(treeInBinaryCode.substring(0, 8), 2);

        for (int i = 8; i < treeInBinaryCode.length() - addedZeros; i++) {
            char current = treeInBinaryCode.charAt(i);

            resultInString += treeInBinaryCode.charAt(i);
            if (treeInBinaryCode.charAt(i) == '1') {
                resultInString += (char) Integer.parseInt(treeInBinaryCode.substring(i + 1, i + 9), 2);
                i += 8;
            }
        }

        ArrayList<Character> result = new ArrayList<>();

        for (char c : resultInString.toCharArray()) {
            result.add(c);
        }

        return result;
    }

    public String convertBytesToBinary(String pathToRootDir) {
        if (pathToRootDir == null) {
            throw new IllegalArgumentException("Given argument is a null!");
        }

        String result = "";

        File file = new File(pathToRootDir);
        int fileSize = (int) file.length();

        String substring;
        byte[] allBytesInFile = new byte[fileSize];

        try ( InputStream inputStream = new FileInputStream(pathToRootDir)) {

            allBytesInFile = inputStream.readAllBytes();

            for (int i = 0; i < fileSize; i++) {
                substring = unsignedByte(allBytesInFile[i]);
                substring = addZerosAtBeginningOfBinary(substring);
                result += substring;
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return result;
    }

    private String addZerosAtBeginningOfBinary(String num) {
        if (num == null) {
            throw new IllegalArgumentException("Given argument is a null!");
        }

        if (num.length() % 8 == 0) {
            return num;
        }

        int zerosToAddAtBeginning = 8 - (num.length() % 8);
        String result = "";

        for (int i = 0; i < zerosToAddAtBeginning; i++) {
            result += "0";
        }

        return result + num;
    }

    private String unsignedByte(byte b) {
        return Integer.toBinaryString(Byte.toUnsignedInt(b));
    }
}
