package game.towerlogic;

import common.SpriteType;
import common.Tile;
import common.core.Vector2;
import game.Critter;
import game.GameManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * New strategy that shoots to the critter closest to the end point.
 */
public class AttackNearestToEndPointStrategy extends AttackStrategyBase {
    private Tile endPoint;
    public Tile getEndPoint(){return endPoint;}

    /**
     * Default Constructor
     * @param leTower Tower holding the strategy
     */
    public AttackNearestToEndPointStrategy(Tower leTower) {
        super(leTower);
        for (Tile[] tileArray:
                GameManager.getInstance().tileManager.getSceneryTiles()) {
            for (Tile leTile :
                    tileArray) {
                if(leTile.getType() == SpriteType.EXIT_POINT){
                    endPoint = leTile;
                    break;
                }
            }
            if(endPoint != null) break;
        }
    }

    /**
     * Implementaton of the {@link IAttackStrategy} interface. This class does damage to the enemy closest
     * to the exit/end tile
     *
     * @param possibleTargets All the possible targets that can be damaged
     * @return affected targets
     */
    @Override
    public List<Critter> doDamage(List<Critter> possibleTargets) {
        Collections.sort(possibleTargets,
                (o1, o2) -> (int)(Vector2.Distance(o1.getPosition(), getEndPoint().getPosition())*1000) -
                        (int)(Vector2.Distance(o2.getPosition(), getEndPoint().getPosition())*1000)
        );
        Critter target =  possibleTargets.get(0);
        target.setHealthPoints(target.getHealthPoints() - (float)getTower().getDamage());
        List<Critter> ret = new ArrayList<>();
        ret.add(target);
        return ret;
    }
    /**
     * Method to get identifier type
     * @return an Enum of nearest to end point
     */

    @Override
    public AttackStrategyEnum getTypeIdentifier() {
        return AttackStrategyEnum.NEARESTTOENDPOINT;
    }
}
