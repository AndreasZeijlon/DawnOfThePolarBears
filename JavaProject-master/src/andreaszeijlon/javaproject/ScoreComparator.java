package andreaszeijlon.javaproject;

import java.util.Comparator;

class ScoreComparator implements Comparator<HighScore>
{
    public int compare(HighScore highscore1, HighScore highscore2) {
	if(highscore1.getHighScore() < highscore2.getHighScore()){return 1;}
	else if(highscore1.getHighScore() == highscore2.getHighScore()){return 0;}
	else{return -1;}
    }

}
