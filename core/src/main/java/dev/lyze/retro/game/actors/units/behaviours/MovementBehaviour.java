package dev.lyze.retro.game.actors.units.behaviours;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import dev.lyze.retro.game.actors.units.Unit;
import lombok.Setter;

public class MovementBehaviour extends Behaviour {
    private int currentSleepTicks;
    @Setter
    private int sleepTicks;

    public MovementBehaviour(Unit unit, int sleepTicks) {
        super(unit);

        this.sleepTicks = sleepTicks;
    }

    @Override
    public void tick(float duration) {
        if (currentSleepTicks++ < sleepTicks)
            return;

        if (unit.getCurrentPoint() + 1 >= unit.getPathPoints().size())
            return;

        currentSleepTicks = 0;

        var moveAction = Actions.action(MoveToAction.class);
        unit.setCurrentPoint(unit.getCurrentPoint() + 1);
        moveAction.setPosition(unit.getPathPoints().get(unit.getCurrentPoint()).getX() * unit.getGame().getMap().getTileWidth(), unit.getPathPoints().get(unit.getCurrentPoint()).getY() * unit.getGame().getMap().getTileHeight());
        moveAction.setDuration(duration);

        unit.addAction(moveAction);

    }
}
