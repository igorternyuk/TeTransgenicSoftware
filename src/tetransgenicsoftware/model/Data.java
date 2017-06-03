package tetransgenicsoftware.model;

import exeptions.BrandNotFoundException;
import exeptions.CouldNotReadConfigFileException;
import exeptions.FoodNotFoundException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Data {
    private final String PATH_TO_CONFIG_FILE = "config/config.txt";
    private String servidor, usuario, contrasena, bdNombre;
    private final Conexion con;
    private String sql;
    private ResultSet tablaVirtual;
    
    public Data() throws ClassNotFoundException, SQLException, CouldNotReadConfigFileException, IOException{
        leerArchivo(PATH_TO_CONFIG_FILE);
        con = new Conexion(servidor, usuario, contrasena, bdNombre);
    }
    
    public Brand getBrand(int idBrand) throws SQLException, BrandNotFoundException{
        Brand b = null;
        sql = "select * from marca where id=" + idBrand;
        tablaVirtual = con.ejecutarSelect(sql);
        if(tablaVirtual.next()){
            b = new Brand(tablaVirtual.getInt(1), tablaVirtual.getString(2));
        }
        con.deconectar();
        if(b == null){
            throw new BrandNotFoundException("No existe la marca con el is:"+idBrand);
        } else {
            return b;
        }
    }
    
    public Food getFood(int id) throws SQLException, FoodNotFoundException{
        Food f = null;
        sql = "select * from alimento where id=" + id;
        tablaVirtual = con.ejecutarSelect(sql);
        if(tablaVirtual.next()){
            f = new Food();
            f.setId(tablaVirtual.getInt(1)); //1 --> id --> int
            f.setName(tablaVirtual.getString(2)); //2 --> nombre --> String
            f.setBrand(tablaVirtual.getInt(3)); //3 --> marca --> String
            f.setTransgenic(tablaVirtual.getBoolean(4)); //4 --> trans --> boolean
        }
        con.deconectar();
        if(f == null){
            throw new FoodNotFoundException("No existe la comida con el id:" + id);
        } else {
            return f;
        }
    }
    
    public ArrayList<Food> getFoodList() throws SQLException{
        sql = "select * from alimento";
        return selectFood(sql);
    }
    
    public ArrayList<Brand> getBrandList() throws SQLException{
        sql = "select * from marca";
        return selectBrands(sql);
    }
    
    public ArrayList<Food> getFoodList(boolean transgenic) throws SQLException{
        sql = "select * from alimento where trans = " + Food.TRANSGENIC;
        return selectFood(sql);        
        /*ArrayList<Food>  auxList = new ArrayList<>();
        for(Food f : foodList){
            if(f.isTransgenic() == Food.TRANSGENIC){
                auxList.add(f);
            }
        }
        return auxList;*/
    }
    
    public int getFoodCount(boolean transgenic) throws SQLException{
        sql = "select count(id) from alimento where trans = " + transgenic;
        return countFood(sql);
    }
    
    public int getFoodCount() throws SQLException{
        sql = "select count(id) from alimento";
        return countFood(sql);
    }
    
    public void addFood(Food f) throws SQLException{
        sql = "insert into alimento values(null,'" +
               f.getName() + "','" + 
               f.getBrand() + "', " +
               f.isTransgenic() +");";
        //f.setId(foodList.size() + 1);
        //foodList.add(f);
        con.ejecutar(sql);
    }
    
    public void deleteFood(int id) throws SQLException{
        sql = "delete from alimento where id = " + id;
        con.ejecutar(sql);
    }
    
    public void updateFood(Food f) throws SQLException{
        sql = "update alimento set nombre = '" + f.getName() +
        "',marca = '" + f.getBrand() + "', trans = " + f.isTransgenic() + " where id =" + f.getId();
        con.ejecutar(sql);
    }
    
    public ArrayList<Food> searchFood(String regExp) throws SQLException{
        sql = "select * from alimento where nombre like '%" + regExp +
               "%' or marca like '%" + regExp + "%';";
        return selectFood(sql);
        /*ArrayList<Food> auxList = new ArrayList<>();
        for(Food f : foodList){
            if(f.getName().toLowerCase().contains(regExp) ||
               f.getBrand().toLowerCase().contains(regExp)){
                auxList.add(f);
            }
        } 
        return auxList;*/
    }
    private ArrayList<Brand> selectBrands(String sql) throws SQLException{        
        ArrayList<Brand> brandList = new ArrayList<>();
        tablaVirtual = con.ejecutarSelect(sql);
        while(tablaVirtual.next()){
            Brand b = new Brand(tablaVirtual.getInt(1), tablaVirtual.getString(2));
            brandList.add(b);
        }
        con.deconectar();
        return brandList;
    }
    
    private ArrayList<Food> selectFood(String sql) throws SQLException{
        Food f;
        ArrayList<Food> listaDeAlimentos = new ArrayList<>();
        tablaVirtual = con.ejecutarSelect(sql);
        while(tablaVirtual.next()){
            f = new Food();
            f.setId(tablaVirtual.getInt(1)); //1 --> id --> int
            f.setName(tablaVirtual.getString(2)); //2 --> nombre --> String
            f.setBrand(tablaVirtual.getInt(3)); //3 --> marca --> String
            f.setTransgenic(tablaVirtual.getBoolean(4)); //4 --> trans --> boolean
            listaDeAlimentos.add(f);
        }
        con.deconectar();
        return listaDeAlimentos;  
    }
    
    private int countFood(String sql) throws SQLException{
        tablaVirtual = con.ejecutarSelect(sql);
        int contador = 0;
        if(tablaVirtual.next()){
            contador = tablaVirtual.getInt(1);
        }
        con.deconectar();
        return contador;
    }
    
    private enum LoadState{
        SERVER,
        USER,
        PASSWORD,
        DATABASE,
        STOP
    }
    
    private void leerArchivo(String pathToFile) throws CouldNotReadConfigFileException, IOException{
        BufferedReader br = null;
            InputStream ins = getClass().getResourceAsStream(pathToFile);
            if (ins == null) {
                throw new CouldNotReadConfigFileException("Could not read config file");
            } else {
            try {
                br = new BufferedReader(new InputStreamReader(ins, "UTF-8"));
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
            }
                LoadState loadState;
                String str;
                while ((str = br.readLine()) != null) {
                    if(str.equalsIgnoreCase("[Server]")){
                        loadState = LoadState.SERVER;
                    } else if(str.equalsIgnoreCase("[User]")){
                        loadState = LoadState.USER;
                    } else if(str.equalsIgnoreCase("[Password]")){
                        loadState = LoadState.PASSWORD;
                    } else if(str.equalsIgnoreCase("[database]")){
                        loadState = LoadState.DATABASE;
                    } else {
                        loadState = LoadState.STOP;
                    }
                    if(loadState != LoadState.STOP){
                        str = br.readLine();
                    }
                    switch (loadState) {
                        case SERVER:
                            servidor = str;
                            break;
                        case USER:
                            usuario = str;
                            break;
                        case PASSWORD:
                            contrasena = str;
                            break;
                        case DATABASE:
                            bdNombre = str;
                            break;
                        case STOP :
                            break;
                    }
                }
            }
            //System.err.println("El programa se acabó de leer archivo de configuracíones con éxito");
    }
}
