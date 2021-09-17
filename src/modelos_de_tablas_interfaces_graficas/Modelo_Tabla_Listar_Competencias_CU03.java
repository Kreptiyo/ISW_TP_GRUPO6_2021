package modelos_de_tablas_interfaces_graficas;

import java.util.List;

import javax.swing.table.AbstractTableModel;
import dominio.Competencia;
import dominio.Eliminacion_Doble;
import dominio.Eliminacion_Simple;
import dominio.Liga;


public class Modelo_Tabla_Listar_Competencias_CU03 extends AbstractTableModel{
	
	
    public Modelo_Tabla_Listar_Competencias_CU03(List<Competencia> competencias) {
    	this.data = competencias;
    }


	private String[] columnNames =  {"Nombre","Deporte", "Modalidad", "Estado"};
	private List<Competencia> data ;

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
    
    	Competencia c = data.get(row);
    	
            switch(col) {
    	        case 0:
    	        	return c.getNombre();
    	        case 1:
    	        	return c.getDeporte().getNombre(); 
    	        case 2:
    	        	
    	        	if(c.getModalidad().getClass().equals(Eliminacion_Simple.class)) {
    	        		return "Eliminación Simple"; 
    	        	}
    	        	
    	        	if(c.getModalidad().getClass().equals(Eliminacion_Doble.class)) {
    	        		return "Eliminación Doble"; 
    	        	}
    	        	
    	        	if(c.getModalidad().getClass().equals(Liga.class)) {
    	        		return "Liga"; 
    	        	}
    	        	
    	        	
    	        case 3:
    	        	return c.getEstado(); 
            }
            return null;
        }

    public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
    }
        
    public Integer obtenerIdCompetencia(int row){
            Competencia c = data.get(row);
            return c.getId();
    }
}
