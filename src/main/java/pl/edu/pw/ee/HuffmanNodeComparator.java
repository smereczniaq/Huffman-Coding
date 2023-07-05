package pl.edu.pw.ee;

import java.util.Comparator;

public class HuffmanNodeComparator implements Comparator<HuffmanNode> {

    @Override
    public int compare(HuffmanNode o1, HuffmanNode o2) {
        if (o1.getFrequency() > o2.getFrequency()) {
            return 1;
        } else if (o1.getFrequency() < o2.getFrequency()) {
            return -1;
        }
        return 0;
    }
}
