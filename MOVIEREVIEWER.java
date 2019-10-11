package solution.Assignment2;

import java.io.Serializable;

public class MOVIEREVIEWER implements Serializable {

    public MOVIEREVIEWER(int id, String text, int realPolarity) {
        this.id = id;
        this.text = text;
        this.realPolarity = realPolarity;
        this.predictedPolarity = 0; // Set a default value. To be changed later.
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public int getPredictedPolarity() {
        return predictedPolarity;
    }

    public void setPredictedPolarity(int predictedPolarity) {
        this.predictedPolarity = predictedPolarity;
    }

    public int getRealPolarity() {
        return realPolarity;
    }

    private final int id;
    private final String text;
    private int predictedPolarity;
    private final int realPolarity;

}