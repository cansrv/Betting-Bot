import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class URLReader {
    // Properties
    URL url;
    String data1 = "";
    String teamsLine = "";
    // Constructors
    public URLReader(String address) throws IOException {
        url = new URL(address);

    }
    // Methods
    public String getData () throws IOException {
        String junk;
        BufferedReader in = new BufferedReader(
                new InputStreamReader(url.openStream()));

        while ((junk = in.readLine()) != null) {
            if(junk.equals("\n"))
                continue;
            if(junk.contains("-  Ma� Detaylar�"))
                teamsLine = junk;
            data1 += junk + "\n";
        }
        in.close();
        return data1;
    }
    public String teamFinder () {
        String teams = "";
        System.out.println(teamsLine);
        if(teamsLine.contains("HATAYSPOR"))
            teams += "HATAYSPOR ";
        if(teamsLine.contains("ALANYASPOR"))
            teams += "ALANYASPOR ";
        if(teamsLine.contains("BE��KTA�"))
            teams += "BESIKTAS ";
        if(teamsLine.contains("ERZURUMSPOR"))
            teams += "ERZURUMSPOR ";
        if(teamsLine.contains("R�ZESPOR"))
            teams += "RIZESPOR ";
        if(teamsLine.contains("S�VASSPOR"))
            teams += "SIVASSPOR ";
        if(teamsLine.contains("KARAG�MR�K"))
            teams += "KARAGUMRUK ";
        if(teamsLine.contains("FENERBAH�E"))
            teams += "FENERBAHCE ";
        if(teamsLine.contains("ANTALYASPOR"))
            teams += "ANTALYASPOR ";
        if(teamsLine.contains("GALATASARAY"))
            teams += "GALATASARAY ";
        if(teamsLine.contains("GAZ�ANTEP"))
            teams += "GAZIANTEP ";
        if(teamsLine.contains("GEN�LERB�RL���"))
            teams += "GENCLERBIRLIGI ";
        if(teamsLine.contains("G�ZTEPE"))
            teams += "GOZTEPE ";
        if(teamsLine.contains("KAYSER�SPOR"))
            teams += "KAYSERISPOR ";
        if(teamsLine.contains("KONYASPOR"))
            teams += "KONYASPOR ";
        if(teamsLine.contains("KASIMPA�A"))
            teams += "KASIMPASA ";
        if(teamsLine.contains("BA�AK�EH�R"))
            teams += "MEDIPOL BASAKSEHIR ";
        if(teamsLine.contains("ANKARAG�C�"))
            teams += "ANKARAGUCU ";
        if(teamsLine.contains("TRABZONSPOR"))
            teams += "TRABZONSPOR ";
        if(teamsLine.contains("MALATYASPOR"))
            teams += "MALATYASPOR ";
        if(teamsLine.contains("DEN�ZL�SPOR"))
            teams += "DENIZLISPOR ";
        String[] teamArray = teams.split(" ");
        String[] teamArrayOrdered = new String[2];
        if(teamsLine.indexOf(teamArray[0]) > teamsLine.indexOf(teamArray[1])) {
            teamArrayOrdered[1] = teamArray[0];
            teamArrayOrdered[0] = teamArray[1];
        } else {
            teamArrayOrdered[0] = teamArray[0];
            teamArrayOrdered[1] = teamArray[1];
        }
        return teamArrayOrdered[0] + " " + teamArrayOrdered[1];

    }
    public void teamDataModifier(Team team1, Team team2) {
        int goal1 = 0;
        int goal2 = 0;
        String dataCopy = data1;
        while(dataCopy.contains("grdTakim1_rptGoller")) {
            goal1++;
            dataCopy = dataCopy.substring(dataCopy.indexOf("grdTakim1_rptGoller") + 1);
        }
        dataCopy = data1;
        while(dataCopy.contains("grdTakim2_rptGoller")) {
            goal2++;
            dataCopy = dataCopy.substring(dataCopy.indexOf("grdTakim2_rptGoller") + 1);
        }
        if(goal1 != 0)
            goal1 = (goal1 -1) / 2;
        if(goal2 != 0)
            goal2 = (goal2 -1) / 2;
        team1.addScore(goal1, team2, goal2);
        team1.addMatch(team2);
        if(goal1-goal2 > 0)
            team1.eloEval(team2, 1);
        else if(goal1-goal2 < 0)
            team1.eloEval(team2, 2);
        else
            team1.eloEval(team2, 0);
        team1.setStreak( goal1-goal2 );
        dataCopy = data1;
        goal1 = 0;
        goal2 = 0;
        while(dataCopy.contains("grdTakim1_rptKartlar")) {
            goal1++;
            dataCopy = dataCopy.substring(dataCopy.indexOf("grdTakim1_rptKartlar") + 1);
        }
        dataCopy = data1;
        while(dataCopy.contains("grdTakim2_rptKartlar")) {
            goal2++;
            dataCopy = dataCopy.substring(dataCopy.indexOf("grdTakim2_rptKartlar") + 1);
        }
        if(goal1 != 0)
            goal1 = (goal1 -1) / 3;
        if(goal2 != 0)
            goal2 = (goal2 -1) / 3;
        team1.addCards(goal1, team2, goal2);
    }
}
