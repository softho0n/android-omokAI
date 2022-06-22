package edu.skku.cs.omokwithmvp;

public class HardScoreUpdateRequestDataModel {
    private String name;
    private String passwd;
    private String score;

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getPasswd() {
        return passwd;
    }

    public String getName() {
        return name;
    }

    public String getScore() {
        return score;
    }
}
