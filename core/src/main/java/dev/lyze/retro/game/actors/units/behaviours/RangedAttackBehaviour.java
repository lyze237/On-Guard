package dev.lyze.retro.game.actors.units.behaviours;

import com.github.czyzby.kiwi.log.Logger;
import com.github.czyzby.kiwi.log.LoggerService;
import dev.lyze.retro.game.actors.units.Unit;

public class RangedAttackBehaviour extends Behaviour {
    private static final Logger logger = LoggerService.forClass(RangedAttackBehaviour.class);

    private int damage, fields;

    public RangedAttackBehaviour(Unit unit, int damage, int fields) {
        super(unit);

        this.damage = damage;
        this.fields = fields;
    }

    @Override
    public boolean tick(float duration) {
        boolean hit = false;

        for (Unit loopUnit : unit.getGame().getRoundUnits()) {
            if (unit.isPlayerUnit() != loopUnit.isPlayerUnit()) {
                for (int i = 1; i <= fields; i++) {
                    var nextPathPoint = unit.getPathPoints().get(unit.getCurrentPoint() + i);
                    if (unit.getGame().getMap().mapCoordsEqualsPixelCoords(nextPathPoint.getX(), nextPathPoint.getY(), (int) loopUnit.getX(), (int) loopUnit.getY())) {
                        logger.info(unit.toString() + " hit " + loopUnit.toString());
                        loopUnit.damage(damage);
                        hit = true;
                    }
                }
            }
        }

        if (hit) {
            bobUnit(duration);

            for (int i = 1; i <= fields; i++) {
                var nextPathPoint = unit.getGame().getMap().convertToPixelCoords(unit.getPathPoints().get(unit.getCurrentPoint() + i));
                unit.getGame().spawnParticle(unit.getGame().getAss().getRangedAttackParticle(), nextPathPoint.getX(),nextPathPoint.getY(), duration * 0.2f);
            }
        }

        return hit;
    }
}
