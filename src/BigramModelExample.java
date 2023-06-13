
import java.io.IOException;

public class BigramModelExample {
    public static final String LOVE_IS_IN_THE_AIR = "resources_examples/love_is_in_the_air.txt";
    public static final String LOVE_IS_IN_THE_AIR_MODEL_DIR = "resources_examples/LOVE_IS_IN_THE_AIR_MODEL_DIR";
    public static void main(String[] args) throws IOException {
        BigramModel sG = new BigramModel();
        sG.initModel(LOVE_IS_IN_THE_AIR);
        sG.saveModel(LOVE_IS_IN_THE_AIR_MODEL_DIR);
        System.out.println("done");

        System.out.println(sG.getMostFrequentProceeding("love"));
        System.out.println(sG.getMostFrequentProceeding("children"));
        System.out.println(sG.getMostFrequentProceeding("in"));

        System.out.println(sG.getProb("love", "is"));
        System.out.println(sG.getProb("hand", "in"));
        System.out.println(sG.getProb("we", "the"));

        System.out.println(sG.getClosestWord("Penny"));
        System.out.println(sG.getClosestWord("nervous"));
        System.out.println(sG.getClosestWord("close"));

        System.out.println(sG.getMaxProb("every", 0));
        System.out.println(sG.getMaxProb("game", 0));
        System.out.println(sG.getMaxProb("play", 0));

        System.out.println(sG.getMaxProbSentence("love", 5));
        System.out.println(sG.getMaxProbSentence("smell", 5));
        System.out.println(sG.getMaxProbSentence("penny", 5));

        System.out.println(sG.isLegalSentence("He smiles and walks"));
        System.out.println(sG.isLegalSentence("love is in the air"));
        System.out.println(sG.isLegalSentence("No, we are not related"));


        System.out.println(sG.probOfSentence("He smiles and walks"));
        System.out.println(sG.probOfSentence("Then the oldest one screams"));
        System.out.println(sG.probOfSentence("when she is nervous"));

        sG.clearModel();

    }
}
