package edu.ucab.desarrollo.viucab.domainLogicLayer.M02_Home;

import edu.ucab.desarrollo.viucab.common.entities.Entity;
import edu.ucab.desarrollo.viucab.common.entities.Video;
import edu.ucab.desarrollo.viucab.dataAccessLayer.DaoFactory;

import edu.ucab.desarrollo.viucab.dataAccessLayer.M02_Home.GetHomeDao;
import edu.ucab.desarrollo.viucab.domainLogicLayer.Command;

import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Created by estefania on 29/11/2017.
 */
public class GetMasVistosComando extends Command {
    ArrayList<Video> resultado=null;
    final static org.slf4j.Logger logger = LoggerFactory.getLogger(GetMasVistosComando.class);

    public GetMasVistosComando() {

    }




    @Override
    public void execute() {
        try {
            GetHomeDao dao =  DaoFactory.instanciateGetHome();
            ArrayList<Video> video = dao.listaVideoTop();
            //Guardamos lo que devuelve el DAO
            resultado=video;

        }
        catch (Exception e){

        }

    }

    @Override
    public Entity Return() {
        return null;
    }
}
