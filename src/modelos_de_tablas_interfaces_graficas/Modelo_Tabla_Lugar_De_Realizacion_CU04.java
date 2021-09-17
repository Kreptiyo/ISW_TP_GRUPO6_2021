package modelos_de_tablas_interfaces_graficas;

import javax.swing.table.AbstractTableModel;



public class Modelo_Tabla_Lugar_De_Realizacion_CU04 extends AbstractTableModel{

	public Modelo_Tabla_Lugar_De_Realizacion_CU04() {
		
	}
	
    private String[] columnNames =  {"Lugar de Realización","Disponibilidad"};

    public int getColumnCount() {
        return columnNames.length;
    }


    public String getColumnName(int col) {
        return columnNames[col];
    }



    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return 0;
	}
    
	
}
