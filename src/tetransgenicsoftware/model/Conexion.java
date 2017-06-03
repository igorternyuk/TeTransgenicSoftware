package tetransgenicsoftware.model;

import java.sql.Connection; //establecer la conexíon
import java.sql.Statement;  //ejecutar consultas
import java.sql.ResultSet; // Tabla virtual MYSQL
import java.sql.DriverManager; //Obtener la conexíon
import java.sql.SQLException;

public class Conexion {
    //Atribustos
    private final Connection con;
    private Statement sentencia;
    private ResultSet tablaVirtual;
    //Construtor
    public Conexion(String server, String user, String pass, String dbName) throws ClassNotFoundException, SQLException{
        //Definir la URL de conexíon
        //jdbc:mysql://localhost/bd?user=igor&password=1319
        String protocolo = "jdbc:mysql://";
        String lineaUsuario = "user=" + user;
        String lineaPass = "password=" + pass;        
        String url = protocolo + server + "/" + dbName + "?" +  lineaUsuario +
        "&" + lineaPass;
        //System.out.println(url);
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection(url);
    }
    //Datos necesarios
    /*
        server --> localhost
        user --> igor
        password --> 1319
        bdName --> bd_alimentos
    */

    /**
     *
     * @param sql puede ser: insert, delete, update
     */

    public void ejecutar(String sql) throws SQLException{
        sentencia = con.createStatement();
        sentencia.execute(sql);
        System.out.println(sql);
        deconectar();
    }
    
    /**
     **@param select es la sentencia select
     * @return Tabla virtual de tipo ResultSet
     */
    public ResultSet ejecutarSelect(String select) throws SQLException{
        sentencia = con.createStatement();
        tablaVirtual = sentencia.executeQuery(select);
        //System.out.println(select);
        return tablaVirtual;
    }
    
    public void deconectar() throws SQLException{
        sentencia.close();
        
    }
//    public static void main(String[] args) throws ClassNotFoundException {
//        try {
//            Conexion c = new Conexion("localhost", "igor", "1319", "bd_alimentos");
//            String insert = "insert into alimento values(null,'ValzmanChocolate','Roshen',true);";
//            c.ejecutar(insert);
//        } catch (SQLException ex) {
//            System.out.println("Los datos son incorrectos!");
//            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
}
