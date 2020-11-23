# Haley's Personal Project

## Hockey Team Application:


A **hockey team application**. Enter statistics for players and teams and view who is qualified
for the NHL and determine how they are doing. Possible features include visual team and player leaderboards.
This application may be used by hardcore hockey fans, including me and my dad! Canada's national winter sport
*definitely* deserves a handy UI to catch up with your favourite hockey teams and players. 

## My User Stories:
- **As a user, I want to be able to** create a new hockey team and add it to a list of qualified teams.
- **As a user, I want to be able to** create a hockey player and add it to a hockey team.
- **As a user, I want to be able to** edit a hockey player's statistics. (points, goals, assists)
- **As a user, I want to be able to** transfer a hockey player between teams.
- **As a user, I want to be able to** retire a hockey player from a team. 
- **As a user, I want to be able to** edit a hockey team's statistics. (games played, wins, losses)
- **As a user, I want to be able to** select a hockey team and list all the players on that team.
- **As a user, I want to be able to** find the top player in a team.
- **As a user, I want to be able to** save my created hockey teams to a file.
- **As a user, I want to be able to** load previously created hockey teams from a file.
- **As a user**, when I select quit from the application menu, I want to be reminded to save my hockey teams to file and have the option to do so or not.
- **As a user**, when I start the application, I want to be given the option to load my hockey teams from a file.

## Phase 4: Task 2:
- Test and design a class in your model package that is robust.  You must have at least one method that throws a checked exception.  You must have one test for the case where the exception is expected and another where the exception is not expected.

I have chosen to give the QualifiedTeams class a robust design, where the TeamAlreadyExistsException is thrown if the user tries to add a team with an already existing team name. Cases of where the exception is thrown and where it is not thrown has been tested in QualifiedTeamsTest.