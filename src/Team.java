public class Team {
    // Constructor
    public Team (String name, int elo, int totalCards, int totalCorner, int totalScore, int totalMatch, int unavPlayer, int streak, char streakType) {
        this.name = name;
        this.elo = elo;
        this.totalCards = totalCards;
        this.totalCorner = totalCorner;
        this.totalScore = totalScore;
        this.totalMatch = totalMatch;
        this.unavPlayer = unavPlayer;
        this.streak = streak;
        this.streakType = streakType;
    }
    // Properties
    String name;
    char streakType;
    int elo;
    int totalScore;
    int totalCorner;
    int totalMatch;
    int totalCards;
    int unavPlayer;
    int streak;
    // Methods
    public void addCards(int i1, Team team, int i2) {
        totalCards += i1;
        team.totalCards += i2;
    }

    public void addScore(int i, Team team, int i2) {
        totalScore += i;
        team.totalScore += i2;
    }

    public void addCorner(int i, Team team, int i2) {
        totalCorner += i;
        team.totalCorner += i2;
    }

    public void addMatch(Team team) {
        totalMatch ++;
        team.totalMatch ++;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUnavPlayer(int unavPlayer) {
        this.unavPlayer = unavPlayer;
    }

    public void setStreak(int netScore) {
        if(netScore > 0) {
            if(streakType == 'W')
                streak++;
            else
                streak = 1;
            streakType = 'W';
        }
        else if (netScore < 0) {
            if(streakType == 'L')
                streak++;
            else
                streak = 1;
            streakType = 'L';
        }
        else {
            if(streakType == 'D')
                streak++;
            else
                streak = 1;
            streakType = 'D';
        }
    }

    public void eloEval(Team team, int result) {
        double pos1 = (1.0)/(1 + Math.pow(10, (elo - team.elo)/400.0));
        System.out.println(pos1);
        double pos2 = 1 - pos1;
        System.out.println(pos2);
        if (result == 1) {
            elo = (int) (elo + (30) * (1 - pos1));
            team.elo = (int) (team.elo + (30) * (0 - pos2));
        }
        else if (result == 2) {
            elo = (int) (elo + (30) * (0 - pos1));
            team.elo = (int) (team.elo + (30) * (1 - pos2));
        }
        else {
            elo = (int) (elo + (30) * (0.5 - pos1));
            team.elo = (int) (team.elo + (30) * (0.5 - pos2));
        }
    }

    public String toString() {
        return ("TEAM:" + name + "_ELO:" + elo + "_GOAL:" + totalScore + "_CORNER:" + totalCorner + "_CARD:" +
                totalCards + "_STREAK:" + streak + "_STREAKTYPE:" + streakType + "_UNAVPLAYER:" + unavPlayer +
                "_TOTALMATCH:" + totalMatch + ";\n");
    }
}
