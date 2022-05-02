package models;

import java.util.ArrayList;
import java.util.List;

public class Squad {
    private String squadName;
    private String cause;
    private static boolean isRegisteredHero = false;
    private List<Hero> heroMembers = new ArrayList<>();
    private static List<Squad> squadList = new ArrayList<>();
    private int squadId;
    private boolean isSquadFull = false;

    public Squad(String name, String cause, models.Hero hero) {
        this.squadName = name.trim();
        this.cause = cause.trim();
        crossCheckHero(hero.getHeroID());

        if (isRegisteredHero) {
            hero.setSquadAlliance(squadName);
            heroMembers.add(hero);
            squadList.add(this);
            this.squadId = squadList.size();
        } else {
            System.out.println("HERO ISN'T REGISTERED");
        }
    }
    public String getName() {
        return squadName;
    }

    public int getSquadId() {
        return squadId;
    }

    public void addMembers(models.Hero hero) {
        if (heroMembers.size() >= 3) {
            isSquadFull = true;
        } else {
            heroMembers.add(hero);
        }
    }
    public boolean getSquadFull() {
        return isSquadFull;
    }

    public List<models.Hero> getMembers() {
        return heroMembers;
    }

    public String getCause() {
        return cause;
    }
    public void changeHeroSquad(models.Hero hero, Squad newSquad) {
        if (heroMembers.size() >= 3) {
            isSquadFull = true;
        } else {
            Squad currentSquad = null;
            for (Squad squad : squadList) {
                if (hero.getSquadAlliance().equalsIgnoreCase(squad.squadName)) {
                    currentSquad = squad;
                    break;
                }
            }

            for (Squad squad : squadList) {
                if (newSquad.squadName.equalsIgnoreCase(squad.squadName)) {

                    if (!hero.getSquadAlliance().equalsIgnoreCase("")) {
                        //IF HERO EXISTED IN PREVIOUS SQUAD
                        currentSquad.heroMembers.remove(hero);
                        newSquad.heroMembers.add(hero);
                        hero.updateSquad(newSquad.squadName);
                        break;

                    } else {
                        //IF HERO HAD NO ALLIANCE
                        newSquad.heroMembers.add(hero);
                        hero.setSquadAlliance(newSquad.squadName);
                    }

                } else {
                    System.out.println("Squad Doesn't exist");
                }
            }
        }
    }
}
