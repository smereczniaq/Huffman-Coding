# Huffman Coding

Authors:
- [Grzegorz Smereczniak](https://github.com/smereczniaq)

This project implements the Huffman coding algorithm in Java programming language. Huffman coding is a lossless data compression algorithm that is commonly used for reducing the size of files. It assigns variable-length codes to different characters based on their frequency of occurrence, resulting in more efficient encoding for commonly used characters.

## Usage
To use the Huffman coding program, follow the steps below:

1. Create an object of the `Huffman` class.
2. Call the `huffman` method of the `Huffman` class, providing the necessary arguments.
   - The first argument, `pathToRootDir`, should be the path to the file you want to compress or decompress. The file should have a .txt extension.
   - The second argument, `compress`, is a boolean value. If `compress` is `true`, the method will return a compressed file. The compressed file's name will be the original file's name appended with "Compressed.txt". If `compress` is `false`, the method will return a decompressed file. The decompressed file's name will be the compressed file's name appended with "Decompressed.txt".
3. Retrieve the compressed or decompressed file as returned by the `huffman` method.

**Note:** The program does not compress files containing unusual signs, such as Polish characters like "ą".

## Example
```java
public class Main {
    public static void main(String[] args) {
        String pathToRootDir = "path/to/your/file.txt";
        boolean compress = true;
        
        Huffman huffman = new Huffman();
        huffman.huffman(pathToRootDir, compress);
    }
}
```
In this example, the program creates an object of the Huffman class, sets the pathToRootDir variable to the desired file path, and sets compress to true to compress the file.
