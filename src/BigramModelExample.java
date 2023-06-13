
import java.io.IOException;

public class BigramModelExample {
    public static final String LOVE_IS_IN_THE_AIR = "resources/love_is_in_the_air.txt";
    public static final String LOVE_IS_IN_THE_AIR_MODEL_DIR = "resources/LOVE_IS_IN_THE_AIR_MODEL_DIR";
    public static void main(String[] args) throws IOException {
        bigramProject sG = new bigramProject();
        sG.initModel(LOVE_IS_IN_THE_AIR);
        sG.saveModel(LOVE_IS_IN_THE_AIR_MODEL_DIR);
        System.out.println("done");

        System.out.println(sG.getMostFrequentProceeding("love"));
        System.out.println(sG.getMostFrequentProceeding("is"));
        System.out.println(sG.getMostFrequentProceeding("in"));

        System.out.println(sG.getProb("love", "is"));
        System.out.println(sG.getProb("is", "in"));
        System.out.println(sG.getProb("in", "the"));

        System.out.println(sG.getClosestWord("love"));
        System.out.println(sG.getClosestWord("is"));
        System.out.println(sG.getClosestWord("in"));

        System.out.println(sG.getMaxProb("love", 2));
        System.out.println(sG.getMaxProb("is", 1));
        System.out.println(sG.getMaxProb("in", 3));

        System.out.println(sG.getMaxProbSentence("love", 2));
        System.out.println(sG.getMaxProbSentence("is", 1));
        System.out.println(sG.getMaxProbSentence("in", 3));

        System.out.println(sG.isLegalSentence("love is in the air"));
        System.out.println(sG.probOfSentence("love is in the air"));
        System.out.println(sG.isLegalSentence("love is in the air love"));

    }
}
