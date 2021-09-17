package modelos_de_tablas_interfaces_graficas;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import dominio.Encuentro;
import dominio.Participante;

public class Modelo_Tabla_Proximos_Encuentros_CU20 extends AbstractTableModel{
	
	public Modelo_Tabla_Proximos_Encuentros_CU20(List<Encuentro> encuentros) {
    	this.data = encuentros;
    }
   

	public List<Encuentro> getData() {
		return data;
	}

	public void setData(List<Encuentro> data) {
		this.data = data;
	}


	private String[] columnNames =  {"Participante A","Participante B", "Lugar de Realización"};
	private List<Encuentro> data ;

	
	public int getColumnCount() {
        return columnNames.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public int getRowCount() {
        return data.size();
    }
     
    public Object getValueAt(int row, int col) {
	    
    	Encuentro e = data.get(row);
    	
            switch(col) 
            {
    	        case 0:
    	        	return e.getParticipante1().getNombre();
    	        case 1:
    	        	return e.getParticipante2().getNombre();
    	        case 2:
    	        	return e.getLugarDeRealizacion().getNombre();
    	        	
            }
            return null;
        }

        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

}
