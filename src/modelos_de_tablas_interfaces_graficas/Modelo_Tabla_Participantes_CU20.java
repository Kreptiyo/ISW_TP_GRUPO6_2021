package modelos_de_tablas_interfaces_graficas;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import dominio.Participante;

public class Modelo_Tabla_Participantes_CU20 extends AbstractTableModel{
	
	 public Modelo_Tabla_Participantes_CU20(List<Participante> participantes) {
	    	this.data = participantes;
	    }
	   

		private String[] columnNames =  {"Participantes"};
		private List<Participante> data ;

		
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
	    
	    	Participante p = data.get(row);
	    	
	            switch(col) {
	    	        case 0:
	    	        	return p.getNombre();
	            }
	            return null;
	        }

	        public Class getColumnClass(int c) {
	            return getValueAt(0, c).getClass();
	        }

}
