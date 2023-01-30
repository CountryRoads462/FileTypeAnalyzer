package analyzer;

import java.util.Comparator;

public class PatternPriorityComparator implements Comparator<Pattern> {

    @Override
    public int compare(Pattern pattern, Pattern t1) {
        int firstPatternPriority = pattern.getPriority();
        int secondPatternPriority = t1.getPriority();
        return Integer.compare(secondPatternPriority, firstPatternPriority);
    }
}
