package andreaszeijlon.javaproject;

public class HighScore
{
        private int highScore;
        private String name;

        public HighScore(final String name, final int score) {
    	this.name = name;
    	this.highScore = score;

        }

        public int getHighScore() {
    	return highScore;
        }

        public String getName() {
    	return name;
        }

}
