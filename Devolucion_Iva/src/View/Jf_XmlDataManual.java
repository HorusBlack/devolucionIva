/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Models.XmlDatos;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author horusblack
 */
public class Jf_XmlDataManual extends javax.swing.JFrame {

    private List<XmlDatos> xmlDatosList;

    /**
     * Creates new form NewJFrame
     *
     * @param listaDatos
     */
    public Jf_XmlDataManual(List<XmlDatos> listaDatos) {
        initComponents();
        preConfiguracion();
        setTabla(listaDatos);

    }

    private void preConfiguracion() {
        //Posicion del jframe
        this.setLocationRelativeTo(null);
        //Elementos adicionales

    }

    private void setTabla(List<XmlDatos> listaDatos) {
        xmlDatosList = new ArrayList<>();
        xmlDatosList = listaDatos;
        Object[][] datos = null;
        Object[][] datos_2 = new Object[xmlDatosList.size()][10];

        // Esta lista contiene los nombres que se mostrarán en el encabezado de cada columna de la grilla
        String[] columnas = new String[]{"Seleccion", "Fecha Factura", "Folio Fiscal", "Concepto XML", "Sub-Total", "IVA", "IVA Retenido", "ISR Retenido",
            "Total", "Acción"};

        // Estos son los tipos de datos de cada columna de la lista
        final Class[] tiposColumnas = new Class[]{
            java.lang.Boolean.class,
            java.lang.String.class,
            java.lang.String.class,
            java.lang.String.class,
            java.lang.String.class,
            java.lang.String.class,
            java.lang.String.class,
            java.lang.String.class,
            java.lang.String.class,
            JButton.class // <- noten que aquí se especifica que la última columna es un botón
        };

        // Agrego los registros que contendrá la grilla.
        // Observen que el último campo es un botón
        for (int i = 0; i < xmlDatosList.size(); i++) {
            String string = xmlDatosList.get(i).getFechaFactura();
            String[] parts = string.split("T");
            String part1 = parts[0];
            String dateFormat = "";
            //No se estan cargando todos los datos
            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(part1);
                dateFormat = new SimpleDateFormat("dd-MM-yyyy").format(date);

            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(this, "Hubo un problema al cargar la fecha: " + ex);
            }

            datos_2[i][0]= false;
            datos_2[i][1]=dateFormat;
            datos_2[i][2]=xmlDatosList.get(i).getFolioFiscal();
            datos_2[i][3]=xmlDatosList.get(i).getConceptoXml();
            datos_2[i][4]=xmlDatosList.get(i).getSubTotal();
            datos_2[i][5]=xmlDatosList.get(i).getIva();
            datos_2[i][6]=xmlDatosList.get(i).getIva_retenido();
            datos_2[i][7]=xmlDatosList.get(i).getIsr();
            datos_2[i][8]=xmlDatosList.get(i).getTotal();
            datos_2[i][9]=new JButton("Quitar");
        }

        // Defino el TableModel y le indico los datos y nombres de columnas
        jTableEjemplo.setModel(new javax.swing.table.DefaultTableModel(
                datos_2,
                columnas) {
            // Esta variable nos permite conocer de antemano los tipos de datos de cada columna, dentro del TableModel
            Class[] tipos = tiposColumnas;

            @Override
            public Class getColumnClass(int columnIndex) {
                // Este método es invocado por el CellRenderer para saber que dibujar en la celda,
                // observen que estamos retornando la clase que definimos de antemano.
                return tipos[columnIndex];
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                // Sobrescribimos este método para evitar que la columna que contiene los botones sea editada.
                return !(this.getColumnClass(column).equals(JButton.class));
            }
        });

        // El objetivo de la siguiente línea es indicar el CellRenderer que será utilizado para dibujar el botón
        jTableEjemplo.setDefaultRenderer(JButton.class, (JTable jtable, Object objeto, boolean estaSeleccionado, boolean tieneElFoco, int fila, int columna) -> (Component) objeto /**
         * Observen que todo lo que hacemos en éste método es retornar el objeto
         * que se va a dibujar en la celda. Esto significa que se dibujará en la
         * celda el objeto que devuelva el TableModel. También significa que
         * este renderer nos permitiría dibujar cualquier objeto gráfico en la
         * grilla, pues retorna el objeto tal y como lo recibe.
         */
        );

        /**
         * Por último, agregamos un listener que nos permita saber cuando fue
         * pulsada la celda que contiene el botón. Noten que estamos capturando
         * el clic sobre JTable, no el clic sobre el JButton. Esto también
         * implica que en lugar de poner un botón en la celda, simplemente
         * pudimos definir un CellRenderer que hiciera parecer que la celda
         * contiene un botón. Es posible capturar el clic del botón, pero a mi
         * parecer el efecto es el mismo y hacerlo de esta forma es más "simple"
         */
        jTableEjemplo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = jTableEjemplo.rowAtPoint(e.getPoint());
                int columna = jTableEjemplo.columnAtPoint(e.getPoint());

                /**
                 * Preguntamos si hicimos clic sobre la celda que contiene el
                 * botón, si tuviéramos más de un botón por fila tendríamos que
                 * además preguntar por el contenido del botón o el nombre de la
                 * columna
                 */
                if (jTableEjemplo.getModel().getColumnClass(columna).equals(JButton.class)) {
                    /**
                     * Aquí pueden poner lo que quieran, para efectos de este
                     * ejemplo, voy a mostrar en un cuadro de dialogo todos los
                     * campos de la fila que no sean un botón.
                     */
                    for (int i = 0; i < jTableEjemplo.getModel().getColumnCount(); i++) {
                        if (!jTableEjemplo.getModel().getColumnClass(i).equals(JButton.class)) {
                            //sb.append("\n").append(jTableEjemplo.getModel().getColumnName(i)).append(": ").append(jTableEjemplo.getModel().getValueAt(fila, i));
                            try {
                                DefaultTableModel dtm = (DefaultTableModel) jTableEjemplo.getModel(); //TableProducto es el nombre de mi tabla ;)
                                dtm.removeRow(jTableEjemplo.getSelectedRow());
                            } catch (ArrayIndexOutOfBoundsException ex) {

                            }

                        }
                    }

                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableEjemplo = new javax.swing.JTable();

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jTableEjemplo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTableEjemplo);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 903, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 85, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableEjemplo;
    // End of variables declaration//GEN-END:variables
}
