package andreaszeijlon.javaproject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public final class HighScoreList
{
        private static final HighScoreList INSTANCE = new HighScoreList();
        private final List<HighScore> highScoreList = new ArrayList<>();

        private HighScoreList() {
        }

        public static void addToHighScore(HighScore highscore) {
    	HighScoreList highscoreList = INSTANCE;
    	highscoreList.highScoreList.add(highscore);
    	Collections.sort(highscoreList.highScoreList, new ScoreComparator());}

    	public static HighScore showHighScore(int i){
	    HighScoreList highscoreList = INSTANCE;
	    return highscoreList.highScoreList.get(i);
}

    public static HighScoreList getINSTANCE() {
	return INSTANCE;
    }

    public Collection<HighScore> getHighScoreList() {
	return highScoreList;
    }
}
