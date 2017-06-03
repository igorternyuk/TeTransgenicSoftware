package tetransgenicsoftware.model;

import exeptions.BrandNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class TMTransFood implements TableModel {
    private final ArrayList<Food> lista; // La liste de alimentos que viene desde base de datos
    private final Data data; // El objeto de tipo data que nos permite obtener los objetos
                             //de tipo brand por el ID
    private final String[] CABECERA = {"ID", "Nombre", "Marca"};
    public TMTransFood(ArrayList<Food> lista, Data data) {
        this.lista = lista;
        this.data = data;
    }
    
    public Food getFood(int rowIndex){
        return lista.get(rowIndex);
    }
    
    @Override
    public int getRowCount() {
        return lista.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return CABECERA[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return Object.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Food f = lista.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return String.valueOf(f.getId());
            case 1:
                return f.getName();
            case 2: {
                try {
                    return data.getBrand(f.getBrand());
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(),
                    "SQLError", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(TMFood.class.getName()).log(Level.SEVERE, null, ex);
                } catch (BrandNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(TMFood.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            default: return null;
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Food f = lista.get(rowIndex);
        switch (columnIndex) {
            case 0:
                f.setId((int) aValue);
                break;
            case 1:
                f.setName((String) aValue);
                break;
            case 2:
                f.setBrand((int) aValue);
                break;
        }
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
    }
    
}
