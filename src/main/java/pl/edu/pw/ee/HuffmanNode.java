package pl.edu.pw.ee;

public class HuffmanNode {

    private char key;
    private int frequency;
    private String code;
    private HuffmanNode leftChild;
    private HuffmanNode rightChild;

    HuffmanNode(char key, int frequency) {
        setKey(key);
        setFrequency(frequency);
        code = "";
        setLeftChild(null);
        setRightChild(null);
    }

    HuffmanNode(int frequency, HuffmanNode leftChild, HuffmanNode rightChild) {
        setFrequency(frequency);
        setLeftChild(leftChild);
        setRightChild(rightChild);
        code = "";
    }

    HuffmanNode(char key) {
        setKey(key);
        setLeftChild(null);
        setRightChild(null);
    }

    HuffmanNode() {
        setLeftChild(null);
        setRightChild(null);
    }

    public void setKey(char key) {
        this.key = key;
    }

    public char getKey() {
        return key;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setLeftChild(HuffmanNode leftChild) {
        this.leftChild = leftChild;
    }

    public HuffmanNode getLeftChild() {
        return leftChild;
    }

    public void setRightChild(HuffmanNode rightChild) {
        this.rightChild = rightChild;
    }

    public HuffmanNode getRightChild() {
        return rightChild;
    }

    public boolean hasChild() {
        return (leftChild != null);
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public boolean isLeaf() {
        return (leftChild == null && rightChild == null);
    }
}
