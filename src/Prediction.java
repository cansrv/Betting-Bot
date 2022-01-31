import java.io.IOException;
import java.util.Scanner;

public class Prediction {


    public Prediction() throws IOException {
    }

    public static void main(String[] args) throws IOException {
        // Properties

        String totData;
        String t1name;
        String t2name;
        String teamRaw;
        String teamName;
        Team t1 = null;
        Team t2 = null;
        String[] teamData;
        Team[] teams;
        Scanner scan = new Scanner(System.in);
        Configurator config = new Configurator("src/leagues/tr1/teamdata.txt","src/leagues/tr1/teamdata.txt");
        // Code
        totData = config.getData();
        teamData = totData.split(";\n");
        teams = new Team[teamData.length];
        for (int i = 0; i < teamData.length ; i++) {
            int elo;
            int goal;
            int corner;
            int card;
            int streak;
            char streakType;
            int unavPlayer;
            int totalMatch;
            teamRaw = teamData[i];
            teamName = teamRaw.substring(teamRaw.indexOf("TEAM:") + 5, teamRaw.indexOf("_"));
            teamRaw = teamRaw.substring(teamRaw.indexOf("_") + 1);
            elo = Integer.parseInt(teamRaw.substring(teamRaw.indexOf("ELO:")+4, teamRaw.indexOf("_")));
            teamRaw = teamRaw.substring(teamRaw.indexOf("_") + 1);
            goal = Integer.parseInt(teamRaw.substring(teamRaw.indexOf("GOAL:")+5, teamRaw.indexOf("_")));
            teamRaw = teamRaw.substring(teamRaw.indexOf("_") + 1);
            corner = Integer.parseInt(teamRaw.substring(teamRaw.indexOf("CORNER:")+7, teamRaw.indexOf("_")));
            teamRaw = teamRaw.substring(teamRaw.indexOf("_") + 1);
            card = Integer.parseInt(teamRaw.substring(teamRaw.indexOf("CARD:")+5, teamRaw.indexOf("_")));
            teamRaw = teamRaw.substring(teamRaw.indexOf("_") + 1);
            streak = Integer.parseInt(teamRaw.substring(teamRaw.indexOf("STREAK:")+7, teamRaw.indexOf("_")));
            teamRaw = teamRaw.substring(teamRaw.indexOf("_") + 1);
            streakType = teamRaw.charAt(teamRaw.indexOf("STREAKTYPE:")+11);
            teamRaw = teamRaw.substring(teamRaw.indexOf("_") + 1);
            unavPlayer = Integer.parseInt(teamRaw.substring(teamRaw.indexOf("UNAVPLAYER:")+11, teamRaw.indexOf("_")));
            teamRaw = teamRaw.substring(teamRaw.indexOf("_") + 1);
            totalMatch = Integer.parseInt(teamRaw.substring(teamRaw.indexOf("TOTALMATCH:")+11));
            teams[i] = new Team(teamName, elo, card, corner, goal, totalMatch, unavPlayer, streak, streakType);
        }
        System.out.println("Previous data is loaded.");
        t1name = scan.next();
        t2name = scan.next();
        for(int i = 0; i < teams.length; i++) {
            if(t1name.equals(teams[i].name))
                t1 = teams[i];
            if(t2name.equals(teams[i].name))
                t2 = teams[i];
        }
        double t1EloMultiplier = 1;
        double t2EloMultiplier = 1;
        if(t1.streakType == 'W')
            t1EloMultiplier = Math.pow(1.05, t1.streak);
        else if (t1.streakType == 'W')
            t1EloMultiplier = Math.pow(0.95, t1.streak);
        if(t2.streakType == 'W')
            t2EloMultiplier = Math.pow(1.05, t2.streak);
        else if (t1.streakType == 'W')
            t2EloMultiplier = Math.pow(0.95, t2.streak);
        System.out.println("Teams are selected");
        double winProbT1 = 1.0/(1.0 + Math.pow(10,((t1.elo*t1EloMultiplier - t2.elo*t2EloMultiplier)/400)));
        double winProbT2 = 1.0 - winProbT1;
        String probWin = "";
        if(winProbT1 > winProbT2) {
            probWin = t1.name + " has " + (winProbT1*100);
        }
        else
            probWin = t2.name + " has " + (winProbT2*100);
        System.out.println(winProbT1 + " : is the win probability of " + t1.name);
        System.out.println(winProbT2 + " : is the win probability of " + t2.name);
        System.out.println((t1.totalScore/(t1.totalMatch*1.0)) + " : is the average score per match of " + t1.name);
        System.out.println((t2.totalScore/(t2.totalMatch*1.0)) + " : is the average score per match of " + t2.name);
        System.out.println(t1.name + " has " + t1.streak + t1.streakType + " streak");
        System.out.println(t2.name + " has " + t2.streak + t2.streakType + " streak");
        System.out.println("Considering the data, total goal will be " + ((t2.totalScore/(t2.totalMatch*1.0))+
                (t1.totalScore/(t1.totalMatch*1.0))) + " and " + probWin + " chance of winning.");
    }
}
