
import java.io.*;
import java.util.*;

public class BigramModel {

    public static final String VOC_FILE_SUFFIX = ".voc";
    public static final String COUNTS_FILE_SUFFIX = ".counts";
    public static final String SOME_NUM = "some_num";
    public static final int ELEMENT_NOT_FOUND = -1;
    List<String> mVocabulary;
    int[][] mBigramCounts;
    double[][] mBigramProbs;

    List<Integer> mUnigramCounts = new ArrayList<>();

    public void initModel(String fileName) throws IOException{
        mVocabulary = buildVocabularyIndex(fileName);
        mBigramCounts = buildCountsArray(fileName, mVocabulary);
        mBigramProbs = calcBigramProbs();
    }


    public List<String> buildVocabularyIndex(String fileName) throws IOException {
        File file = new File(fileName);
        List<String> vocabulary = BigramModel.readAllTokens(file);
        List<String> vocabulary2 = new ArrayList<>();
        for (String string : vocabulary) {
            if (string.matches("[0-9]+")) {
                string = SOME_NUM;
            }
            if (!vocabulary2.contains(string)) {
                mUnigramCounts.add(1);
                vocabulary2.add(string);
                continue;
            }
            int index = vocabulary2.indexOf(string);
            mUnigramCounts.set(index, mUnigramCounts.get(index) + 1);

        }
        return vocabulary2;
    }

    int[][] buildCountsArray(String fileName, List<String> vocabulary) throws IOException {
        int[][] bigramCounts = new int[vocabulary.size()][vocabulary.size()];
        File file = new File(fileName);
        List<String> tokens = BigramModel.readAllTokens(file);
        for (int i = 0; i < tokens.size() - 1; i++) {
            String token1 = tokens.get(i);
            String token2 = tokens.get(i + 1);
            if (token1.matches("[0-9]+")) {
                token1 = SOME_NUM;
            }
            if (token2.matches("[0-9]+")) {
                token2 = SOME_NUM;
            }
            int index1 = vocabulary.indexOf(token1);
            int index2 = vocabulary.indexOf(token2);
            bigramCounts[index1][index2]++;
        }
        return bigramCounts;
    }


    public static List<String> readAllTokens(File file)
            throws IOException {
        List<String> tokens = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new FileReader(file))) {
            String line = null;
            while ((line = reader.readLine()) != null) {

                //split every line by whitespaces
                String[] split = line.split("\\s+");
                for (String token : split) {
                    //convert the token to lowercase and trim trailing spaces and punctuation marks
                    token = token.replaceFirst("^\\p{Punct}*", "")
                            .replaceFirst("\\p{Punct}*$", "")
                            .toLowerCase().trim();
                    if (token.length() > 0) {
                        //add non-empty tokens to the list
                        tokens.add(token);
                    }
                }
            }
        }
        return tokens;
    }

    /*
     * @pre: word is in lowercase
     * @pre: the method initModel was called (the language model is initialized)
     * @post: $ret = -1 if word is not in vocabulary, otherwise $ret = the index of word in vocabulary
     */
    public int getWordIndex(String word){
        if (mVocabulary.contains(word)) {
            return mVocabulary.indexOf(word);
        }
        return ELEMENT_NOT_FOUND;
    }

    /*
     * @pre: word1 and word2 are in lowercase
     * @pre: the method initModel was called (the language model is initialized)
     * @pre: word1 and word2 are in vocabulary
     * @post: $ret = the number of times word2 is followed by word1 in the text
     */

    public int getBigramCount(String word1, String word2) {
        int index1 = getWordIndex(word1);
        int index2 = getWordIndex(word2);
        return mBigramCounts[index1][index2];
    }

    /*
     * @pre word in lowercase, and is in mVocabulary
     * @pre: the method initModel was called (the language model is initialized)
     * @post $ret = the word with the lowest vocabulary index that appears most frequently after word (if a bigram starting with
     * word was never seen, $ret will be null
     */
    public String getMostFrequentProceeding(String word){ //  Q - 7
        int indexOfWord = getWordIndex(word);
        if (indexOfWord==-1)
            return null;
        int max = 0;
        int indexMax = 0;
        for (int i = 0;i<mBigramCounts[indexOfWord].length;i++){
            if (mBigramCounts[indexOfWord][i]>max){
                indexMax = i;
                max = mBigramCounts[indexOfWord][i];
            }
        }
        if (indexOfWord==mBigramCounts.length-1 && max==0){
            return null;
        }
        return mVocabulary.get(indexMax);
    }

    /* @pre: sentence is in lowercase
     * @pre: the method initModel was called (the language model is initialized)
     * @pre: each two words in the sentence are separated with a single space
     * @post: if sentence is probable, according to the model, $ret = true, else, $ret = false
     */
    public boolean isLegalSentence(String sentence) {  //  Q - 8
        if (sentence.equals("")) return true;
        String[] arrSentence = sentence.split(" ");
        if (arrSentence.length == 1) return getWordIndex(arrSentence[0]) != -1;
        int wordIndex = getWordIndex(arrSentence[0]);
        for (int i = 0; i < arrSentence.length - 1; i++) {
            int nextIndex = getWordIndex(arrSentence[i + 1]);
            if (wordIndex == -1 || nextIndex == -1) {
                return false;
            }
            else if (mBigramCounts[wordIndex][nextIndex] == 0) {
                return false;
            }
            wordIndex = nextIndex;
        }
        return true;
    }

    public double probOfSentence(String sentence) {
        if (!isLegalSentence(sentence)) {
            return 0;
        }
        String[] arrSentence = sentence.toLowerCase().split(" ");
        if (arrSentence.length == 1) {
            return (double) mUnigramCounts.get(getWordIndex(arrSentence[0])) / mVocabulary.size();
        }
        int wordIndex = getWordIndex(arrSentence[0]);
        double prob = 1.0;
        for (int i = 0; i < arrSentence.length - 1; i++) {
            int nextIndex = getWordIndex(arrSentence[i + 1]);
            prob *= (double) mBigramCounts[wordIndex][nextIndex] / mUnigramCounts.get(wordIndex);
            wordIndex = nextIndex;
        }
        return prob;
    }

    public double[][] calcBigramProbs(){
        double[][] mBigramProb1 = new double[mBigramCounts.length][mBigramCounts.length];
        for (int i = 0; i < mVocabulary.size(); i++) {
            for (int j = 0; j < mVocabulary.size(); j++) {
                double prob = (double) mBigramCounts[i][j] / mUnigramCounts.get(i);
                mBigramProb1[i][j] = prob;
                }
            }
        return mBigramProb1;
    }

    public double getProb(String word1, String word2){
        int index1 = getWordIndex(word1);
        int index2 = getWordIndex(word2);
        return mBigramProbs[index1][index2];
    }

    public Map.Entry<String,Double> getMaxProb(String word,int k){
        int index = getWordIndex(word);
        double max = 0;
        double prevMax = 0;
        int j = 0;
        int prevJ = 0;
        for (int i = 0; i < mBigramProbs[index].length; i++) {
            if (mBigramProbs[index][i] > max) {
                prevMax = max;
                prevJ = j;
                max = mBigramProbs[index][i];
                j = i;
            }
        }
        AbstractMap.SimpleEntry<String, Double> entry;
        if (k==0){
            entry = new AbstractMap.SimpleEntry<>(mVocabulary.get(j), max);
        }
        else {
            entry = new AbstractMap.SimpleEntry<>(mVocabulary.get(prevJ), prevMax);
        }
        return entry;
    }

    public String getMaxProbSentence(String word, int length){

        List<String> sentence = new LinkedList<>();
        sentence.add(word);
        String nextWord = word;
        for (int i = 0; i < length; i++) {
            Map.Entry<String,Double> entry = getMaxProb(nextWord,0);
            if (sentence.contains(entry.getKey()))
                entry = getMaxProb(nextWord,1);
            sentence.add(entry.getKey());
            nextWord = entry.getKey();
        }
        return sentence.toString();
    }

    /*
     * @pre: arr1.length = arr2.length
     * post if arr1 or arr2 are only filled with zeros, $ret = -1, otherwise calculates CosineSim
     */
    public static double calcCosineSim(int[] arr1, int[] arr2){
        double numerator = calcNumerator(arr1, arr2);
        if (numerator == 0){
            return -1;
        }
        double denominator = calcDenominator(arr1, arr2);
        return numerator/denominator;
    }

    private static double calcNumerator(int[] arr1, int[] arr2){
        double sum = 0;
        for (int i=0;i<arr1.length;i++){
            sum += arr1[i]*arr2[i];
        }
        return sum;
    }

    private static double calcDenominator(int[] arr1, int[] arr2){
        double sum1 = 0;
        double sum2 = 0;
        for (int i=0;i<arr1.length;i++){
            sum1 += arr1[i]*arr1[i];
            sum2 +=arr2[i]*arr2[i];
        }
        return (Math.sqrt(sum1)*Math.sqrt(sum2));
    }

    public String getClosestWord(String word) { //  Q - 10
        int wordIndex = getWordIndex(word);
        int[] wordVector = mBigramCounts[wordIndex];
        int indexOfMax = 0;
        double maxCosinemSim = 0;
        for (int i = 0; i < mBigramCounts.length; i++) {
            if (i == wordIndex) {
                continue;
            }
            double cosineSim = calcCosineSim(wordVector, mBigramCounts[i]);
            if (cosineSim > maxCosinemSim) {
                maxCosinemSim = cosineSim;
                indexOfMax = i;
            }
        }
        return mVocabulary.get(indexOfMax);
    }

    /*
     * @pre: the method initModel was called (the language model is initialized)
     * @pre: fileName is a legal file path
     */
    public void saveModel(String fileName) throws IOException{ // Q-3
        BufferedWriter vocFile = createBufferWriter(fileName + VOC_FILE_SUFFIX);
        BufferedWriter countsFile = createBufferWriter(fileName + COUNTS_FILE_SUFFIX);
        writeToVoc(vocFile);
        writeToCounts(countsFile);
    }

    private void writeToVoc(BufferedWriter vocFile) throws IOException {
        int numberOfWords = mVocabulary.size();
        vocFile.write(numberOfWords + " words");
        for (int i = 0; i<mVocabulary.size();i++){
            vocFile.newLine();
            vocFile.write(i + "," + mVocabulary.get(i));
        }
        vocFile.close();
    }

    private void writeToCounts(BufferedWriter countsFile) throws IOException {
        for (int j = 0; j<mBigramCounts.length;j++){
            for(int p = 0;p<mBigramCounts[j].length;p++){
                if(mBigramCounts[j][p]!=0){
                    countsFile.write(j + "," + p + ":" + mBigramCounts[j][p]);
                    countsFile.newLine();
                }
            }
        }
        countsFile.close();
    }
    private static BufferedWriter createBufferWriter(String fileName) throws IOException {
        File toFile = new File(fileName);
        FileWriter fWriter = new FileWriter(toFile);
        return new BufferedWriter(fWriter);
    }
    /*
     * @pre: fileName is a legal file path
     */
    public void loadModel(String fileName) throws IOException{ // Q - 4
        BufferedReader vocFile = createBufferReader(fileName + VOC_FILE_SUFFIX);
        BufferedReader countsFile = createBufferReader(fileName + COUNTS_FILE_SUFFIX);
        loadVoc(vocFile);
        loadCounts(countsFile);
        this.mBigramProbs = calcBigramProbs();

    }

    private static BufferedReader createBufferReader(String fileName) throws FileNotFoundException {
        File fromFile = new File(fileName);
        FileReader fReader = new FileReader(fromFile);
        return new BufferedReader(fReader);
    }

    private void loadVoc(BufferedReader vocFile) throws IOException {
        String line;
        mVocabulary = new LinkedList<>();
        line = vocFile.readLine();
        while ((line = vocFile.readLine()) != null){
            Scanner scLine = new Scanner(line);
            scLine.useDelimiter(",");
            String wordIndex = scLine.next();
            int index = Integer.parseInt(wordIndex);
            String word = scLine.nextLine();
            mVocabulary.add( word.substring(1));
        }
    }

    private void loadCounts(BufferedReader countsFile) throws IOException {
        String line;
        mBigramCounts = new int[mVocabulary.size()][mVocabulary.size()];
        while ((line = countsFile.readLine())!= null){
            Scanner scLine = new Scanner(line);
            scLine.useDelimiter("[,:]");
            mBigramCounts[scLine.nextInt()][scLine.nextInt()] = scLine.nextInt();
            scLine.close();
        }
    }

    public void clearModel() { // Q - 5
        mVocabulary = null;
        mBigramCounts = null;
        mBigramProbs = null;
    }
}
