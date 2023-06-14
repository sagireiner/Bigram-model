
import java.io.IOException;

public class BigramModelExample {
    public static final String CORPUS = "resources_examples/corpus.txt";
    public static final String CORPUS_MODEL_DIR = "resources_examples/CORPUS_MODEL_DIR";
    public static void main(String[] args) throws IOException {
        BigramModel sG = new BigramModel();
//        sG.initModel(CORPUS);
//        sG.saveModel(CORPUS_MODEL_DIR);

        /* after saving the model, you can load it from the file */
         sG.loadModel(CORPUS_MODEL_DIR);

        /* test the model */
        System.out.println(sG.getMostFrequentProceeding("love"));
        System.out.println(sG.getMostFrequentProceeding("hey"));
        System.out.println(sG.getMostFrequentProceeding("take"));

        System.out.println(sG.getProb("what", "is"));
        System.out.println(sG.getProb("because", "in"));
        System.out.println(sG.getProb("we", "are"));

        System.out.println(sG.getClosestWord("love"));
        System.out.println(sG.getClosestWord("nervous"));
        System.out.println(sG.getClosestWord("close"));

        System.out.println(sG.getKMaxProb("every"));
        System.out.println(sG.getKMaxProb("game"));
        System.out.println(sG.getKMaxProb("play"));

        System.out.println(sG.getMaxProbSentence("love", 4));
        System.out.println(sG.getMaxProbSentence("what", 4));
        System.out.println(sG.getMaxProbSentence("there", 4));
        System.out.println(sG.getMaxProbSentence("i", 4));
        System.out.println(sG.getMaxProbSentence("we", 4));
        System.out.println(sG.getMaxProbSentence("you", 4));
        System.out.println(sG.getMaxProbSentence("they", 4));
        System.out.println(sG.getMaxProbSentence("he", 4));
        System.out.println(sG.getMaxProbSentence("she", 4));
        System.out.println(sG.getMaxProbSentence("it", 4));
        System.out.println(sG.getMaxProbSentence("the", 4));
        System.out.println(sG.getMaxProbSentence("a", 5));
        System.out.println(sG.getMaxProbSentence("an", 5));
        System.out.println(sG.getMaxProbSentence("and", 5));
        System.out.println(sG.getMaxProbSentence("or", 5));
        System.out.println(sG.getMaxProbSentence("but", 5));
        System.out.println(sG.getMaxProbSentence("if", 5));
        System.out.println(sG.getMaxProbSentence("because", 5));
        System.out.println(sG.getMaxProbSentence("when", 5));
        System.out.println(sG.getMaxProbSentence("where", 5));

        System.out.println(sG.isLegalSentence("what if i"));
        System.out.println(sG.isLegalSentence("love is in the air"));
        System.out.println(sG.isLegalSentence("please take care"));


        System.out.println(sG.probOfSentence("what if i"));
        System.out.println(sG.probOfSentence("love is in the air"));
        System.out.println(sG.probOfSentence("i want to"));

        sG.clearModel();

    }
}
