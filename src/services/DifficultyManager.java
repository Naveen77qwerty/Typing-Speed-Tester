package services;

import enums.DifficultyLevel;
import core.WordGenerator;
import java.util.HashMap;
import java.util.Map;

public class DifficultyManager {
    private Map<DifficultyLevel, Integer> sentenceLengthMap;
    private WordGenerator wordGenerator;
    
    public DifficultyManager(WordGenerator wordGenerator) {
        this.wordGenerator = wordGenerator;
        initializeLengthMap();
    }
    
    private void initializeLengthMap() {
        sentenceLengthMap = new HashMap<>();
        sentenceLengthMap.put(DifficultyLevel.EASY, 50);     // Easy: sentences up to 50 chars
        sentenceLengthMap.put(DifficultyLevel.MEDIUM, 100);  // Medium: sentences up to 100 chars
        sentenceLengthMap.put(DifficultyLevel.HARD, 200);    // Hard: sentences up to 200 chars
    }
    
    public void setDifficulty(DifficultyLevel level) {
        wordGenerator.setDifficulty(level);
    }
    
    public DifficultyLevel getCurrentDifficulty() {
        return wordGenerator.getDifficulty();
    }
    
    public int getMaxSentenceLength() {
        return sentenceLengthMap.get(getCurrentDifficulty());
    }
}
