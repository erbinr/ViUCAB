package edu.ucab.desarrollo.viucab.domainLogicLayer.M11;

import edu.ucab.desarrollo.viucab.common.entities.Entity;
import edu.ucab.desarrollo.viucab.domainLogicLayer.Command;
import org.slf4j.LoggerFactory;

/**
 * Created by Daniel on 25/11/2017.
 */
public class GetEstadistica1 extends Command {

    final static org.slf4j.Logger logger = LoggerFactory.getLogger(GetEstadistica1.class);
    private static Entity est;

    public GetEstadistica1(Entity est) {
    }

    @Override
    public void execute() {

        try {

        }
        catch (Exception e){

        }
    }

    @Override
    public Entity Return() {
        return est;
    }
}
