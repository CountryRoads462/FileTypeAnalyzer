package analyzer;

public class RabinKarpSearcher {

    private static final int a = 3;
    private static final int m = 11;

    public synchronized static boolean search(String pattern, String text) {
        if (pattern.length() > text.length()) {
            return false;
        }

        int pHFuncOfPattern = getPolyHashFunc(pattern);

        int startIndex = text.length() - pattern.length();

        String substring = text.substring(startIndex);
        char lastCharacter = substring.charAt(substring.length() - 1);
        int pHFuncOfFirstSubStr = getPolyHashFunc(substring);
        int lastHF = pHFuncOfFirstSubStr;

        if (pHFuncOfFirstSubStr == pHFuncOfPattern) {
            if (pattern.equals(substring)) {
                return true;
            }
        }
        startIndex--;
        while (startIndex >= 0) {
            substring = text.substring(startIndex, startIndex + substring.length());
            int rHFunc = getRollHashFunc(substring, lastCharacter, lastHF);
            if (rHFunc == pHFuncOfPattern) {
                if (pattern.equals(substring)) {
                    return true;
                }
            }
            lastHF = rHFunc;
            lastCharacter = substring.charAt(substring.length() - 1);
            startIndex--;
        }
        return false;
    }

    private synchronized static int getPolyHashFunc(String pattern) {
        int sum = 0;
        for (int i = 0; i < pattern.length(); i++) {
            char ch = pattern.charAt(i);
            sum += ch * Math.pow(a, i);
        }
        return Math.floorMod(sum, m);
    }

    private static int getRollHashFunc(String pattern, char lastCharacter, int lastHF) {
        int valueOfFirstChar = pattern.charAt(0);
        return Math.floorMod((int) (lastHF - (int) lastCharacter * Math.pow(a, pattern.length() - 1)) * a + valueOfFirstChar , m);
    }
}
