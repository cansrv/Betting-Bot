import java.io.IOException;
import java.util.Scanner;
public class DataInput {
    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        // Var
        Configurator config = new Configurator("src/leagues/tr1/teamdata.txt","src/leagues/tr1/teamdata.txt");
        URLReader reader1;
        String totData;
        String teamRaw;
        String teamName;
        String teamsTotal;
        StringBuilder tbw = new StringBuilder();
        String[] teamData;
        Team[] teams;
        int elo;
        int goal;
        int corner;
        int card;
        int streak;
        char streakType;
        int unavPlayer;
        int totalMatch;
        int menu;
        // Code
        totData = config.getData();
        teamData = totData.split(";\n");
        teams = new Team[teamData.length];
        for (int i = 0; i < teamData.length ; i++) {
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
        int indexOft1 = -1;
        int indexOft2 = -1;
        do {
            System.out.println("1: Enter values by hand \n2: Enter values by link \n3: Manual change \n9: Close the program");
            menu = scan.nextInt();
            if (menu == 1) {
                System.out.println("Enter the first team");
                teamsTotal = scan.next();
                for (int i = 0; i < teams.length; i++) {
                    if (teamsTotal.equals(teams[i].name)) {
                        System.out.println("Team 1 is selected.");
                        indexOft1 = i;
                    }
                }
                if(indexOft1 == -1) {
                    System.out.println("No such team exists, start again.");
                    continue;
                }
                System.out.println("Enter the second team");
                teamsTotal = scan.next();
                for (int i = 0; i < teams.length; i++) {
                    if (teamsTotal.equals(teams[i].name)) {
                        System.out.println("Team 2 is selected.");
                        indexOft2 = i;
                    }
                }
                if(indexOft2 == -1) {
                    System.out.println("No such team exists, start again.");
                    continue;
                }
                System.out.println("Enter scores of the teams");
                int goalt1 = scan.nextInt();
                int goalt2 = scan.nextInt();
                teams[indexOft1].addScore(goalt1, teams[indexOft2], goalt2);
                teams[indexOft1].setStreak(goalt1 - goalt2);
                if(goalt1 - goalt2 > 0)
                    teams[indexOft1].eloEval(teams[indexOft2], 1);
                if(goalt1 - goalt2 == 0)
                    teams[indexOft1].eloEval(teams[indexOft2], 0);
                if(goalt1 - goalt2 < 0)
                    teams[indexOft1].eloEval(teams[indexOft2], 2);
                System.out.println("Enter corners of the teams");
                teams[indexOft1].addCorner(scan.nextInt(), teams[indexOft2], scan.nextInt());
                System.out.println("Enter cards of the teams");
                teams[indexOft1].addCards(scan.nextInt(), teams[indexOft2], scan.nextInt());
                System.out.println("Enter the number of players unable to play the next match");
                teams[indexOft1].setUnavPlayer(scan.nextInt());
                teams[indexOft2].setUnavPlayer(scan.nextInt());
                teams[indexOft1].addMatch(teams[indexOft2]);

                for (Team team : teams) {
                    tbw.append(team.toString());
                }
            } else if (menu == 2) {
                System.out.println("Enter link");
                reader1 = new URLReader(scan.next());
                String s1 = reader1.getData();
                teamsTotal = reader1.teamFinder();
                System.out.println(teamsTotal);
                int index1 = -1;
                int index2 = -1;
                for (int i = 0; i < teams.length; i++) {
                    if (teamsTotal.contains(teams[i].name) )
                        index2 = i;
                }
                if(index2 == -1) {
                    System.out.println("CUK");
                    System.out.println("No such team is found");
                    continue;
                }

                for (int i = 0; i < teams.length; i++) {
                    if (teamsTotal.contains(teams[i].name) && i != index2 )
                        index1 = i;
                }
                if(index1 == -1) {
                    System.out.println("No such team is found");
                    continue;
                }
                if(index1 > index2) {
                    reader1.teamDataModifier(teams[index2], teams[index1]);
                    System.out.println(teams[index2].toString());
                    System.out.println(teams[index1].toString());
                }
                else {
                    reader1.teamDataModifier(teams[index1], teams[index2]);
                System.out.println(teams[index1].toString());
                System.out.println(teams[index2].toString());
                }

            } else if (menu == 3) {

            }
        } while(menu != 9);
        for (Team team : teams) {
            tbw.append(team.toString());
        }
        config.writeData(tbw.toString().substring(0, tbw.toString().length()-2));
    }
}
