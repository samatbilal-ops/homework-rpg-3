package com.narxoz.rpg.battle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class BattleEngine {
    private static BattleEngine instance;
    private Random random = new Random(1L);

    private BattleEngine() {
    }

    public static BattleEngine getInstance() {
        if (instance == null) {
            instance = new BattleEngine();
        }
        return instance;
    }

    public BattleEngine setRandomSeed(long seed) {
        this.random = new Random(seed);
        return this;
    }

    public void reset() {
        // TODO: reset any battle state if you add it
    }

    public EncounterResult runEncounter(List<Combatant> teamA, List<Combatant> teamB) {
        // TODO: validate inputs and run round-based battle
        // TODO: use random if you add critical hits or target selection
        if (teamA == null || teamB == null) {
            throw new IllegalArgumentException("Team can't be null");
        }

        EncounterResult result = new EncounterResult();
        int rounds = 0;
        while (!teamA.isEmpty() && !teamB.isEmpty()) {
            rounds++;
            for (Combatant attacker : new ArrayList<>(teamA)) {
                if (teamB.isEmpty()) {
                    break;
                }

                Combatant target = teamB.get(0);
                target.takeDamage(attacker.getAttackPower());

                result.addLog(attacker.getName() + " hits " + target.getName() + " for " + attacker.getAttackPower());

                if (!target.isAlive()) {
                    result.addLog(target.getName() + " died ");
                    teamB.remove(target);
                }
            }

            for (Combatant attacker : new ArrayList<>(teamB)) {
                if (teamA.isEmpty()) {
                    break;
                }

                Combatant target = teamA.get(0);
                target.takeDamage(attacker.getAttackPower());

                result.addLog(attacker.getName() + " hits " + target.getName() + " for " + attacker.getAttackPower());

                if (!target.isAlive()) {
                    result.addLog(target.getName() + " died");
                    teamA.remove(target);
                }
            }
        }

        result.setRounds(rounds);
        result.setWinner(teamA.isEmpty() ? "Team B" : "Team A");

        return result;
    }
}