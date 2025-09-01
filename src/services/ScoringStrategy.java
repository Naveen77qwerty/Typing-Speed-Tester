package services;

public interface ScoringStrategy {
    double calculateWPM(String text, double time);

    double calculateAccuracy(String original, String typed);
}