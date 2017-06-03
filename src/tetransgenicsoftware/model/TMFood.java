package tetransgenicsoftware.model;

import exeptions.BrandNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class TMFood implements TableModel{
    private final ArrayList<Food> lista; // La liste de alimentos que viene desde base de datos
    private final Data data; // El objeto de tipo data que nos permite obtener los objetos
                             //de tipo brand por el ID
    private JButton btn = new JButton("Vista");
    private final String[] CABECERA = {"ID", "Nombre", "Marca", "Transgenic", "Cambiar", "Visualizar"};
    public TMFood(ArrayList<Food> lista, Data data) {
        this.lista = lista;
        this.data = data;
        btn.setName("visualizar");
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
        return 6;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return CABECERA[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return (columnIndex == 4) ? Boolean.class : Object.class;        
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 4;
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
            case 3:
                return f.isTransgenic() ? "Sí" : "No";
            case 4:
                return f.isTransgenic();
            case 5:
                return btn;
            default:
                return null;
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
            case 3:
                f.setTransgenic((boolean) aValue);
                break;
            case 4 :
                f.setTransgenic((boolean) aValue);
                break;
            case 5 :
                btn = (JButton)aValue;
                break;
            default:
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
