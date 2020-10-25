package ui;

import model.HockeyPlayer;
import model.HockeyTeam;
import model.QualifiedTeams;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// Hockey team application
// ** Uses parts of the TellerApp program **
public class HockeyApp {
    private static final String JSON_STORE = "./data/hockeyTeams.json";
    public QualifiedTeams qualified;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: run hockey application
    public HockeyApp() {
        qualified = new QualifiedTeams();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runHockeyApp();
    }

    // MODIFIES: this
    // EFFECTS: process user input
    public void runHockeyApp() {
        boolean keepGoing = true;
        String command = null;
        input = new Scanner(System.in);

        requestLoad();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                requestSave();
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("\nYou miss 100% of the shots you don't take. Bye now!");
    }

    public void requestLoad() {
        boolean keepGoing = true;
        String command = null;
        input = new Scanner(System.in);

        while (keepGoing) {
            System.out.println("\nLoad existing file?:");
            System.out.println("\ty -> Yes");
            System.out.println("\tn -> No");
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("y")) {
                loadTeams();
                keepGoing = false;
            } else if (command.equals("n")) {
                keepGoing = false;
            } else {
                System.out.println("Selection not valid... Shoot again!");
            }
        }
    }

    public void requestSave() {
        boolean keepGoing = true;
        String command = null;
        input = new Scanner(System.in);

        while (keepGoing) {
            System.out.println("\nSave before quitting?:");
            System.out.println("\ty -> Yes");
            System.out.println("\tn -> No");
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("y")) {
                saveTeams();
                keepGoing = false;
            } else if (command.equals("n")) {
                keepGoing = false;
            } else {
                System.out.println("Selection not valid... Shoot again!");
            }
        }
    }

    // EFFECTS: display main menu of options to user
    public void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> Create new hockey team");
        System.out.println("\tb -> Create new player + add to existing team");
        System.out.println("\tc -> List all hockey teams");
        System.out.println("\td -> List all players in team");
        System.out.println("\te -> View/Edit player");
        System.out.println("\tf -> View/Edit team");
        System.out.println("\tg -> View Best Player in Team");
        System.out.println("\th -> View Best Team");
        System.out.println("\ti -> Save teams to file");
        System.out.println("\tj -> Load teams from file");
        System.out.println("\tq -> Quit");
    }

    // EFFECTS: display menu of options to user for edit player
    public void displayMenu2() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> Add goal");
        System.out.println("\tb -> Add assist");
        System.out.println("\tq -> Quit");
    }

    // EFFECTS: displays menu of options to user for edit team
    public void displayMenu3() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> Add win");
        System.out.println("\tb -> Add loss");
        System.out.println("\tq -> Quit");
    }

    // MODIFIES: this
    // EFFECTS: process user command for main menu
    public void processCommand(String command) {
        if (command.equals("a")) {
            createNewTeam();
        } else if (command.equals("b")) {
            createNewPlayer();
        } else if (command.equals("c")) {
            viewAllQualifiedTeams();
        } else if (command.equals("d")) {
            viewAllPlayersInTeam();
        } else if (command.equals("e")) {
            editPlayer();
        } else if (command.equals("f")) {
            editTeam();
        } else if (command.equals("g")) {
            findTopPlayer();
        } else if (command.equals("h")) {
            findTopTeam();
        } else if (command.equals("i")) {
            saveTeams();
        } else if (command.equals("j")) {
            loadTeams();
        } else {
            System.out.println("Selection not valid... Shoot again!");
        }
    }

    // EFFECTS: saves hockey teams to file
    private void saveTeams() {
        try {
            jsonWriter.open();
            jsonWriter.write(qualified);
            jsonWriter.close();
            System.out.println("Saved hockey teams to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // EFFECTS: loads hockey teams from file
    private void loadTeams() {
        try {
            qualified = jsonReader.read();
            System.out.println("Loaded hockey teams from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: process user command for edit player
    public void processCommand2(String command, int team, int player, String playerName) {
        if (command.equals("a")) {
            addGoal(team, player, playerName);
        } else if (command.equals("b")) {
            addAssist(team, player, playerName);
        } else {
            System.out.println("Selection not valid... Shoot again!");
        }
    }

    // MODIFIES: this
    // EFFECTS: process user command for edit team
    public void processCommand3(String command, int team, String teamName) {
        if (command.equals("a")) {
            addWin(team, teamName);
        } else if (command.equals("b")) {
            addLoss(team, teamName);
        } else {
            System.out.println("Selection not valid... Shoot again!");
        }
    }

    // MODIFIES: this
    // EFFECTS: create new hockey team via user input
    public void createNewTeam() {
        System.out.println("Enter team wins: ");
        int wins = input.nextInt();

        System.out.println("Enter team losses: ");
        int losses = input.nextInt();

        input.nextLine();
        System.out.println("Enter team name: ");
        String name = input.nextLine();

        HockeyTeam team = new HockeyTeam(name, wins, losses);
        qualified.qualifyTeam(team);
        System.out.println("" + name + " added successfully!");
    }

    // REQUIRES: at least one team exists
    //           + selected team exists in list of teams
    // MODIFIES: this
    // EFFECTS: create new hockey player via user input
    //          + adds to existing team
    public void createNewPlayer() {
        String teams = qualified.teamList();
        System.out.println("Select a team: [0, 1, 2..]");
        System.out.println(teams);
        int team = input.nextInt();

        System.out.println("Enter player goals: ");
        int goals = input.nextInt();

        System.out.println("Enter player assists: ");
        int assists = input.nextInt();

        input.nextLine();
        System.out.println("Enter name of player: ");
        String name = input.nextLine();

        HockeyPlayer player = new HockeyPlayer(name, goals, assists);
        qualified.getTeam(team).addPlayer(player);
        System.out.println("" + name + " was added to team " + qualified.getTeam(team).getTeamName()
                + " successfully!");
    }

    // EFFECTS: return string representation of all teams qualified
    public void viewAllQualifiedTeams() {
        System.out.println(qualified.teamList());
    }

    // REQUIRES: at least one team exists
    //           + selected team exists in list of teams
    // EFFECTS: return string representation of players on a team
    public void viewAllPlayersInTeam() {
        System.out.println("Select a team: [0, 1, 2..]");
        System.out.println(qualified.teamList());
        int team = input.nextInt();

        System.out.println("Player's in " + qualified.getTeam(team).getTeamName() + ":");
        System.out.println(qualified.getTeam(team).playerList());
    }

    // REQUIRES: at least one team exists
    //           + selected team exists in list of teams
    //           + at least one player exists
    //           + selected player exists in list of players
    // MODIFIES: this
    // EFFECTS: process user input to add goals/assists to player or transfer to separate team
    public void editPlayer() {
        System.out.println("Select a team: [0, 1, 2..]");
        System.out.println(qualified.teamList());
        int team = input.nextInt();
        System.out.println("Select a player: [0, 1, 2..]");
        System.out.println(qualified.getTeam(team).playerList());
        int player = input.nextInt();
        String playerName = qualified.getTeam(team).getPlayer(player).getName();
        boolean keepGoing = true;
        String command = null;

        while (keepGoing) {
            System.out.println(qualified.getTeam(team).getPlayer(player).toString());
            displayMenu2();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand2(command, team, player, playerName);
            }
        }
    }

    // REQUIRES: at least one team exists
    //           + selected team exists in list of teams
    // MODIFIES: this
    // EFFECTS: process user input to add wins/losses to team
    public void editTeam() {
        System.out.println("Select a team: [0, 1, 2..]");
        System.out.println(qualified.teamList());
        int team = input.nextInt();

        String teamName = qualified.getTeam(team).getTeamName();
        boolean keepGoing = true;
        String command = null;

        while (keepGoing) {
            System.out.println(qualified.getTeam(team).teamStats());
            displayMenu3();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand3(command, team, teamName);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: add goal to player
    public void addGoal(int team, int player, String playerName) {
        qualified.getTeam(team).getPlayer(player).addGoal();
        System.out.println("Goal added to " + playerName + " successfully!");
    }

    // MODIFIES: this
    // EFFECTS: add assist to player
    public void addAssist(int team, int player, String playerName) {
        qualified.getTeam(team).getPlayer(player).addAssist();
        System.out.println("Assist added to " + playerName + " successfully!");
    }

    // MODIFIES: this
    // EFFECTS: add win to team
    public void addWin(int team, String teamName) {
        qualified.getTeam(team).addWin();
        System.out.println("Win added to " + teamName + " successfully!");
    }

    // MODIFIES: this
    // EFFECTS: add loss to team
    public void addLoss(int team, String teamName) {
        qualified.getTeam(team).addLoss();
        System.out.println("Loss added to " + teamName + " successfully!");
    }

    // REQUIRES: at least one team exists
    //           + selected team exists in list of teams
    // EFFECTS: return string representation of top player in chosen team
    public void findTopPlayer() {
        System.out.println("Select a team: [0, 1, 2..]");
        System.out.println(qualified.teamList());
        int team = input.nextInt();
        System.out.println("Top player in " + qualified.getTeam(team).getTeamName()
                + " : " + qualified.getTeam(team).topPlayer() + "");

    }

    // EFFECTS: return string representation of number one team qualified
    public void findTopTeam() {
        System.out.println("Top Team: " + qualified.topTeam().getTeamName() + "");
    }
}


