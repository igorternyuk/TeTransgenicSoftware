package tetransgenicsoftware.gui.renderer;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;

public class Renderer extends DefaultTableCellRenderer{
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
        boolean isSelected, boolean hasFocus, int row, int column){
        if(value instanceof JButton){
            JButton btn = (JButton)value;
            if(isSelected){
                //System.out.println("El boton es seleccionado");
                //btn.setForeground(table.getSelectionForeground());
                //btn.setBackground(table.getSelectionBackground());
                btn.setForeground(Color.BLACK);
                btn.setBackground(Color.CYAN);
            } else {
                btn.setForeground(table.getForeground());
                btn.setBackground(UIManager.getColor("Button.background"));
            }
            return btn;
        } else if(value instanceof JCheckBox){   
            JCheckBox cbx = (JCheckBox)value;
            return cbx;
        } else {
            return super.getTableCellRendererComponent(table, value, isSelected,
                   hasFocus, row, column);
        }
    }
}
