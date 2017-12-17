package edu.ucab.desarrollo.viucab.dataAccessLayer;


import com.google.gson.Gson;
import edu.ucab.desarrollo.viucab.common.Registry;
import edu.ucab.desarrollo.viucab.common.entities.Video;
import edu.ucab.desarrollo.viucab.common.exceptions.M11.BdConnectException;
import edu.ucab.desarrollo.viucab.common.exceptions.MessageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/***
 * creada por M011
 */
@Path("/dao")
public class Dao{

    //Gson gson = new Gson();
    Connection conn = null;
    String query;

    public Dao(){

    }

    @GET
    @Path("/conectar")
    @Produces("Application/json")
    public String conectarDB(){



        try{
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost/ViUCAB", "postgres", "5530321");
            return "Conectado a BD";
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return "1";
        } catch (SQLException e) {
            e.printStackTrace();
            return "2";
        }
        //return "No conecto";
    }

    @GET
    @Path("/desconectar")
    @Produces("Application/json")
    public String desconectarDB() throws SQLException {
        conn.close();
        return "Desconectado";
    }

    @GET
    @Path("/insertar_usuario")
    @Produces("Application/json")
    public String insertarUsuario() throws SQLException {
        conectarDB();
        query = "INSERT into usuario(usu_id, usu_login, usu_clave, usu_token, usu_act, usu_correo, usu_avatar) values (2, 'luis', '123', 'papa', true, 'gmail', 'papa')";

        try{
            Statement st = conn.createStatement();
            st.executeUpdate(query);
            desconectarDB();
            return "Se ejecuto exitosamente";
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

    @GET
    @Path("/get_sugerencias-like")
    @Produces("Application/json")
    public String getSugerenciasLike(int id_usuario, String categoria) throws SQLException { //Recuerda cambiar esto a json
        conectarDB();
       // List<Gson> lista = new List<Gson>();
        Gson gson = new Gson();
        ArrayList<Video> lista = new ArrayList<Video>();

        query = "SELECT * FROM video WHERE vid_id = ((SELECT id_video FROM like WHERE vid_usuario = (SELECT id_suscripcion FROM suscripcion WHERE id_suscriptor = "+id_usuario+"))) AND (vid_categoria = (SELECT cat_id WHERE cat_valor = "+categoria+"))";

        try{
            Statement st = conn.createStatement();
            st.executeUpdate(query);
            ResultSet rs = st.executeQuery(query);

            while (rs.next()){
                Video video = new Video();
                video.setId(rs.getInt("vid_id"));
                video.setNombre(rs.getString("vid_titulo"));
                video.setDescripcion(rs.getString("vid_descripcion"));
                video.setImagen(rs.getString("vid_imagen"));
                video.setUrl(rs.getString("vid_url"));
                video.setFecha(rs.getString("vid_fecha"));
                video.setVisitas(rs.getInt("vid_visitas"));
                video.setUsuario(rs.getInt("vid_usuario"));
                video.setCategoria(rs.getInt("vid_categoria"));
                lista.add(video);

            }
            return gson.toJson(lista);
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

    @GET
    @Path("/get_sugerencias-suscripciones")
    @Produces("Application/json")
    public String getSugerenciasSuscripciones(int id_usuario, String categoria) throws SQLException { //Recuerda cambiar esto a json
        conectarDB();
        // List<Gson> lista = new List<Gson>();
        Gson gson = new Gson();
        ArrayList<Video> lista = new ArrayList<Video>();

        query = "SELECT * FROM video WHERE vid_usuario = (SELECT id_suscripcion FROM suscripcion WHERE id_suscriptor = "+id_usuario+") AND (vid_categoria = (SELECT cat_id WHERE cat_valor = "+categoria+"))";
        try{
            Statement st = conn.createStatement();
            st.executeUpdate(query);
            ResultSet rs = st.executeQuery(query);

            while (rs.next()){
                Video video = new Video();
                video.setId(rs.getInt("vid_id"));
                video.setNombre(rs.getString("vid_titulo"));
                video.setDescripcion(rs.getString("vid_descripcion"));
                video.setImagen(rs.getString("vid_imagen"));
                video.setUrl(rs.getString("vid_url"));
                video.setFecha(rs.getString("vid_fecha"));
                video.setVisitas(rs.getInt("vid_visitas"));
                video.setUsuario(rs.getInt("vid_usuario"));
                video.setCategoria(rs.getInt("vid_categoria"));
                lista.add(video);

            }
            return gson.toJson(lista);
        }
        catch (Exception e){
            return e.getMessage();
        }
    }
/*
    @GET
    @Path("/get_sugerencias-Preferencias")
    @Produces("Application/json")
    public String getSugerenciasPreferencias(int id_usuario, String categoria) throws SQLException {
        conectarDB();
        query = "SELECT * FROM video WHERE vid_id = ((SELECT id_video FROM like WHERE vid_usuario = (SELECT id_suscripcion FROM suscripcion WHERE id_suscriptor = "+id_usuario+"))) AND (vid_categoria = (SELECT cat_id WHERE cat_valor = "+categoria+"))";
        Video video = new Video();
        try{
            Statement st = conn.createStatement();
            st.executeUpdate(query);
            ResultSet rs = st.executeQuery(query);

            while (rs.next()){
                video.setId(rs.getInt("vid_id"));
                video.setNombre(rs.getString("vid_titulo"));
                video.setDescripcion(rs.getString("vid_descripcion"));
                video.setImagen(rs.getString("vid_imagen"));
                video.setUrl(rs.getString("vid_url"));
                video.setFecha(rs.getString("vid_fecha"));
                video.setVisitas(rs.getInt("vid_visitas"));
                video.setUsuario(rs.getInt("vid_usuario"));
                video.setCategoria(rs.getInt("vid_categoria"));
            }
            return "Se ejecuto exitosamente";
        }
        catch (Exception e){
            return e.getMessage();
        }
    }*/


/*
public abstract class Dao implements IDao
{
    private static Logger logger = LoggerFactory.getLogger( Dao.class );

    private static Connection conn = null;

    private static Connection conInstance;

    /**
     * Metodo para devolver una unica instancia de la conexion
     * @return instancia de la conexion

    public static Connection getConInstance() {

        try {
            conInstance = getBdConnect();
        }catch (BdConnectException e){
            MessageException error = new MessageException(e, Dao.class.getSimpleName(),
                    Thread.currentThread().getStackTrace()[1].getMethodName());
            logger.error("Error: ", error.toString());
        }

        return conInstance;
    }

    /**
     * Metodo que realiza la conexion con la base de datos
     * @return Conexion hecha a la base de datos
     * @throws ClassNotFoundException Si la clase no es encontrada
     * @throws SQLException Problemas con sql
     * @throws Exception
     * @see Connection
     * @see Statement

    public static Connection getBdConnect() throws BdConnectException
    {

        try
        {
            Class.forName( Registry.BD_CLASS_FOR_NAME );
            conn = DriverManager.getConnection( Registry.BD_URL, Registry.BD_USER, Registry.BD_PASSWORD );
        }
        catch ( ClassNotFoundException e )
        {
            logger.error( "Metodo: {} {}", "getBdConnect", e.toString() );
            throw new BdConnectException( e );
        }
        catch ( SQLException e )
        {
            logger.error( "Metodo: {} {}", "getBdConnect", e.toString() );
            throw new BdConnectException( e );
        }
        return conn;
    }

    protected static void closeConnection()
    {
        try
        {
            conn.close();
        }
        catch ( SQLException e )
        {
            logger.error( "Metodo: {} {}", "getBdConnect", e.toString() );
        }
    }
*/
}
