package edu.skku.cs.omokwithmvp;

public class RankingUserModel {
    private String name;
    private String nickname;
    private String score;

    public void setScore(String score) {
        this.score = score;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    public String getNickname() {
        return nickname;
    }
}
