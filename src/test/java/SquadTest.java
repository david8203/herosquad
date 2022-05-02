import models.Hero;
import models.Squad;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SquadTest {
    @Before
    public void setUp() throws Exception {
    }

    private Hero setupNewHero() {
        return new Hero("Batman", 48, "Money", "Loneliness");
    }

    private Hero setupHero2() {
        return new Hero("Iron Man", 30, "Money", "Ego");
    }

    private Squad setupNewSquad(Hero hero) {
        return new Squad("Justice League", "Just Exist", hero);
    }
    private Squad setupNewSquad2(Hero hero) {
        return new Squad("Avengers", "Just Exist again", hero);
    }

    @Test
    public void squadInstanciatedCorrectly() {
        Squad squad = setupNewSquad(setupNewHero());
        assertTrue(squad instanceof Squad);
    }

    @Test
    public void getSquadName() {
        Squad squad = setupNewSquad(setupNewHero());
        assertTrue(squad.getName() instanceof String);
    }
    @Test
    public void getSquadCause() {
        Squad squad = setupNewSquad(setupNewHero());
        assertTrue(squad.getCause() instanceof String);
    }
    @Test
    public void addingHeroToExistingSquad() {
        Squad squad = setupNewSquad(setupNewHero());
        squad.addMembers(setupHero2());
        assertEquals(2, squad.getMembers().size());
    }

    @Test
    public void addHero_NotExceedLimitOf1_int() {
        /* Change back limit to 1 before testing again -- Current Limit is 3 hence test below */
        Squad squad = setupNewSquad(setupNewHero());
        squad.addMembers(setupHero2());
        assertEquals(2, squad.getMembers().size());
    }

    @Test
    public void addHero_NotExceedLimitOf3_int() {
        Squad squad = setupNewSquad(setupNewHero());
        squad.addMembers(setupHero2());
        squad.addMembers(new Hero("1", 1, "1", "1"));
        squad.addMembers(new Hero("2", 1, "1", "1"));
        squad.addMembers(new Hero("3", 1, "1", "1"));
        squad.addMembers(new Hero("4", 1, "1", "1"));
        assertEquals(3, squad.getMembers().size());
        assertTrue(squad.getSquadFull());
    }
}
