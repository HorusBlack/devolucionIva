package View;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Victor.Espindola
 */
public class GrillaConBotones extends javax.swing.JFrame {

    public GrillaConBotones() {
        initComponents();
        setTabla();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        //Configuracion del JTable
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableEjemplo = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);

        jScrollPane1.setViewportView(jTableEjemplo);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 671, Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE));

        pack();
    }

    private void setTabla() {

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
        Object[][] datos = new Object[][]{
            {false, "20/01/2018", "abc123", "N/D", "20", "0.16", "0", "0", "20.16", new JButton("Clic aquí")},
            {false, "20/01/2018", "abc123", "N/D", "20", "0.16", "0", "0", "20.16", new JButton("Clic aquí")},
            {false, "20/01/2018", "abc123", "N/D", "20", "0.16", "0", "0", "20.16", new JButton("Clic aquí")}
        };

        // Defino el TableModel y le indico los datos y nombres de columnas
        jTableEjemplo.setModel(new javax.swing.table.DefaultTableModel(
                datos,
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
        jTableEjemplo.setDefaultRenderer(JButton.class, new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable jtable, Object objeto, boolean estaSeleccionado, boolean tieneElFoco, int fila, int columna) {
                /**
                 * Observen que todo lo que hacemos en éste método es retornar
                 * el objeto que se va a dibujar en la celda. Esto significa que
                 * se dibujará en la celda el objeto que devuelva el TableModel.
                 * También significa que este renderer nos permitiría dibujar
                 * cualquier objeto gráfico en la grilla, pues retorna el objeto
                 * tal y como lo recibe.
                 */
                return (Component) objeto;
            }
        });

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
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < jTableEjemplo.getModel().getColumnCount(); i++) {
                        if (!jTableEjemplo.getModel().getColumnClass(i).equals(JButton.class)) {
                            sb.append("\n").append(jTableEjemplo.getModel().getColumnName(i)).append(": ").append(jTableEjemplo.getModel().getValueAt(fila, i));
                        }
                    }
                    JOptionPane.showMessageDialog(null, "Seleccionada la fila " + fila + sb.toString());
                }
            }
        });
    }

    /**
     * @param args the command line arguments //
     */
//    public static void main(String args[]) {
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(() -> {
//            new GrillaConBotones().setVisible(true);
//        });
//    }
    // Variables declaration - do not modify                     
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableEjemplo;
    // End of variables declaration                   
}
