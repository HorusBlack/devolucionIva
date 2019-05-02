/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controllers.IvaAcredController;
import Controllers.PolizaDatos;
import Controllers.PolizaDatosString;
import Controllers.XmlDatos;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class Jf_FacturasIvaAcred extends javax.swing.JFrame {

    private DefaultTableModel tablaIva;
    private DefaultTableModel defaultTableIva;
    private String periodo, asunto, empresa;
    private int numRegistros;
    private IvaAcredController ivaAcred;
    private List<PolizaDatos> listPolizaDatos;

    /**
     * Creates new form jframePrincipal
     */
    public Jf_FacturasIvaAcred() {
        initComponents();
        preConfiguracion();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelMenus = new javax.swing.JPanel();
        calendarMes = new com.toedter.calendar.JMonthChooser();
        calendarAnio = new com.toedter.calendar.JYearChooser();
        btnProcesarIva = new javax.swing.JButton();
        panelResumenDatos = new javax.swing.JPanel();
        lbPeriodo = new javax.swing.JLabel();
        lbEmpresa = new javax.swing.JLabel();
        lbAsunto = new javax.swing.JLabel();
        lbRegistros = new javax.swing.JLabel();
        panelInfo = new javax.swing.JPanel();
        SpIva = new javax.swing.JScrollPane();
        tablaIvaAcred = new javax.swing.JTable();
        ScrollTotalIva = new javax.swing.JScrollPane();
        tablaTotalIva = new javax.swing.JTable();
        btnGuardarIva = new javax.swing.JButton();
        btnExcel = new javax.swing.JButton();
        panelConcepto = new javax.swing.JPanel();
        scrollPaneConcepto = new javax.swing.JScrollPane();
        txta_Concepto = new javax.swing.JTextArea();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuIva = new javax.swing.JMenu();

        setTitle("AgroEcologia Iva");

        panelMenus.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtro"));

        calendarMes.setBorder(javax.swing.BorderFactory.createTitledBorder("Seleccionar Mes"));

        calendarAnio.setBorder(javax.swing.BorderFactory.createTitledBorder("Seleccionar Año"));

        btnProcesarIva.setBackground(new java.awt.Color(0, 153, 153));
        btnProcesarIva.setText("Procesar");
        btnProcesarIva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProcesarIvaActionPerformed(evt);
            }
        });

        panelResumenDatos.setBorder(javax.swing.BorderFactory.createTitledBorder("Extracto"));

        lbPeriodo.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N

        lbEmpresa.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N

        lbAsunto.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N

        lbRegistros.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N

        javax.swing.GroupLayout panelResumenDatosLayout = new javax.swing.GroupLayout(panelResumenDatos);
        panelResumenDatos.setLayout(panelResumenDatosLayout);
        panelResumenDatosLayout.setHorizontalGroup(
            panelResumenDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelResumenDatosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelResumenDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbPeriodo)
                    .addComponent(lbEmpresa)
                    .addComponent(lbAsunto)
                    .addComponent(lbRegistros))
                .addContainerGap(122, Short.MAX_VALUE))
        );
        panelResumenDatosLayout.setVerticalGroup(
            panelResumenDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelResumenDatosLayout.createSequentialGroup()
                .addComponent(lbPeriodo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbEmpresa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbAsunto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbRegistros))
        );

        javax.swing.GroupLayout panelMenusLayout = new javax.swing.GroupLayout(panelMenus);
        panelMenus.setLayout(panelMenusLayout);
        panelMenusLayout.setHorizontalGroup(
            panelMenusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMenusLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(calendarMes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(calendarAnio, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnProcesarIva, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelResumenDatos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelMenusLayout.setVerticalGroup(
            panelMenusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMenusLayout.createSequentialGroup()
                .addComponent(panelResumenDatos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMenusLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelMenusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(calendarAnio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(calendarMes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelMenusLayout.createSequentialGroup()
                        .addComponent(btnProcesarIva)
                        .addGap(8, 8, 8)))
                .addGap(32, 32, 32))
        );

        panelInfo.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        SpIva.setBorder(javax.swing.BorderFactory.createTitledBorder("100% IVA ACREDITABLE"));

        tablaIvaAcred.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tablaIvaAcred.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tablaIvaAcred.setToolTipText("");
        tablaIvaAcred.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tablaIvaAcred.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tablaIvaAcredMousePressed(evt);
            }
        });
        SpIva.setViewportView(tablaIvaAcred);

        ScrollTotalIva.setBorder(javax.swing.BorderFactory.createTitledBorder("Totales"));

        tablaTotalIva.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tablaTotalIva.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        ScrollTotalIva.setViewportView(tablaTotalIva);

        btnGuardarIva.setBackground(new java.awt.Color(255, 102, 102));
        btnGuardarIva.setText("Guardar");

        btnExcel.setBackground(new java.awt.Color(0, 204, 51));
        btnExcel.setText("Generar Excel");

        javax.swing.GroupLayout panelInfoLayout = new javax.swing.GroupLayout(panelInfo);
        panelInfo.setLayout(panelInfoLayout);
        panelInfoLayout.setHorizontalGroup(
            panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(SpIva, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(panelInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ScrollTotalIva, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnGuardarIva, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExcel))
                .addGap(513, 513, 513))
        );
        panelInfoLayout.setVerticalGroup(
            panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(SpIva, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(ScrollTotalIva, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelInfoLayout.createSequentialGroup()
                        .addComponent(btnExcel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGuardarIva)))
                .addContainerGap())
        );

        panelConcepto.setBorder(javax.swing.BorderFactory.createTitledBorder("Concepto XML Completo"));

        txta_Concepto.setColumns(30);
        txta_Concepto.setRows(5);
        txta_Concepto.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txta_Concepto.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txta_Concepto.setEnabled(false);
        scrollPaneConcepto.setViewportView(txta_Concepto);

        javax.swing.GroupLayout panelConceptoLayout = new javax.swing.GroupLayout(panelConcepto);
        panelConcepto.setLayout(panelConceptoLayout);
        panelConceptoLayout.setHorizontalGroup(
            panelConceptoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConceptoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPaneConcepto)
                .addContainerGap())
        );
        panelConceptoLayout.setVerticalGroup(
            panelConceptoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPaneConcepto)
        );

        menuIva.setBackground(new java.awt.Color(153, 204, 255));
        menuIva.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        menuIva.setText("FACTURAS  IVA ACRED");
        jMenuBar1.add(menuIva);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelMenus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelInfo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelConcepto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelMenus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelConcepto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(130, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tablaIvaAcredMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaIvaAcredMousePressed

        String numCol = "";
        //Obtiene el no. de columna y lo comvierte en String
        numCol = Arrays.toString(tablaIvaAcred.getSelectedColumns());
        //Si es la columna correcta realiza la accion
        if (tablaIvaAcred.getSelectedRow() != -1) {
            txta_Concepto.setVisible(true);

            //obteniendo el valor de la celda en la coordenada
            String codigo = (String) tablaIva.getValueAt(tablaIvaAcred.getSelectedRow(), 5);
            txta_Concepto.setText(codigo);

            // Lo imprimimos en pantalla
        }
        /*
        if (numCol.equals("[5]")) {
          
        } else {
            txta_Concepto.setVisible(false);

        }*/
    }//GEN-LAST:event_tablaIvaAcredMousePressed

    private void btnProcesarIvaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProcesarIvaActionPerformed

        //XML DATOS
        int mes = calendarMes.getMonth();
        int year = calendarAnio.getYear();
        String[] numMes = {"01 Enero", "02 Febrero", "03 Marzo", "04 Abril", "05 Mayo", "06 Junio", "07 Julio", "08 Agosto", "09 Septiembre", "10 Octubre",
            "11 Noviembre", "12 Diciembre"};
        String urlMes = numMes[mes];
        tablaIvaAcred.removeAll();
        //INICIALIZAR TABLA
        inicializarTablaIva(urlMes, mes, year);
        inicializarTablaTotalIva();

        //Ver si es posible cambiar el nombre de las carpetas para que tenga un mismo formato y sea mas facil acceder

    }//GEN-LAST:event_btnProcesarIvaActionPerformed

    /**
     * Metodo que obtiene y maqueta la tabla de devolucion de Iva
     */
    private void inicializarTablaIva(String numMes, int mes, int anio) {
        tablaIva = new DefaultTableModel();
        //Titulos para la tabla
        String[] titulos = {"#Factura", "Fecha Factura", "#Poliza", "Fecha Poliza", "Folio Fiscal", "Conceptos XML", "Sub-Total", "IVA", "IVA Retenido", "ISR Retenido",
            "Total", "Cruce: Estado de cuenta", "Pago: Fecha", "Pago: Concepto S.E.C", "Pago: Forma Pago", "RFC Proveedor", "Nombre Proveedor", "Concepto", "Relación con Activ.",
            "Cta. de la que se realiza el pago", "Observaciones"};
        //Ingresando titulos
        tablaIva.setColumnIdentifiers(titulos);

        //Clase que obtiene los datos xml
        ivaAcred = new IvaAcredController();
        //url de los documentos Mack
        String URL = "C:\\Users\\Macktronica\\Desktop\\Dac Simulacion\\" + anio + "\\" + numMes;
        String URL_lap = "H:\\Dac Simulacion\\" + anio + "\\" + numMes;
        //Lista de objetos xmlDatos
        List<XmlDatos> llenarDatosTabla = ivaAcred.datosDevolucionIva(URL_lap);
        listPolizaDatos = ivaAcred.solicitudPolizaDatos(mes, anio);
        if (!listPolizaDatos.isEmpty()) {
            for (int i = 0; i < listPolizaDatos.size(); i++) {
                System.out.println("Datos lpd: " + listPolizaDatos.get(i).getNumeroPoliza());
                System.out.println("Datos lpd: " + listPolizaDatos.get(i).getEjercicio());
                System.out.println("Datos lpd: " + listPolizaDatos.get(i).getFechaPoliza());
                System.out.println("Datos lpd: " + listPolizaDatos.get(i).getConceptoPoliza());
                System.out.println("Datos lpd: " + listPolizaDatos.get(i).getDocumentos());
                System.out.println("\n");
            }
        }
        //Solicitud datos BD

        //checar esta validacion
        if (!llenarDatosTabla.isEmpty()) {
            //llenando la tabla de la info

            for (int i = 0; i < llenarDatosTabla.size(); i++) {

                String string = llenarDatosTabla.get(i).getFechaFactura();
                String[] parts = string.split("T");
                String part1 = parts[0];
                String newstring = "";

                try {
                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(part1);
                    newstring = new SimpleDateFormat("dd-MM-yyyy").format(date);

                } catch (ParseException ex) {
                    Logger.getLogger(Jf_FacturasIvaAcred.class.getName()).log(Level.SEVERE, null, ex);
                }

                tablaIva.addRow(new Object[]{"N/D", newstring, "N/D", "N/D", llenarDatosTabla.get(i).getFolioFiscal(),
                    llenarDatosTabla.get(i).getConceptoXml(), llenarDatosTabla.get(i).getSubTotal(), "N/D", "N/D", "N/D", llenarDatosTabla.get(i).getTotal(),
                    "N/D", "N/D", "N/D", "N/D", "N/D", "N/D", "N/D", "N/D", "N/D", "N/D", "N/D",});
            }

            String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre",
                "Noviembre", "Diciembre"};

            datosResumen(meses[mes], llenarDatosTabla.size(), anio);

            TableRowSorter<TableModel> ordenTabla = new TableRowSorter<>(tablaIva);
            tablaIvaAcred.setModel(tablaIva);
            tablaIvaAcred.setRowSorter(ordenTabla);

            //tamaño manual
            TableColumn columna;
            for (int i = 0; i < 8; i++) {
                switch (i) {
                    case 0:
                        //factura
                        columna = tablaIvaAcred.getColumn(titulos[i]);
                        columna.setMinWidth(50);
                        break;
                    case 1:
                        //Fecha Factura
                        columna = tablaIvaAcred.getColumn(titulos[i]);
                        columna.setMinWidth(130);
                        break;
                    case 2:
                        //#poliza
                        columna = tablaIvaAcred.getColumn(titulos[i]);
                        columna.setMinWidth(50);
                        break;
                    case 3:
                        //Fecha Poliza
                        columna = tablaIvaAcred.getColumn(titulos[i]);
                        columna.setMinWidth(130);
                        break;
                    case 4:
                        //Folio Fiscal
                        columna = tablaIvaAcred.getColumn(titulos[i]);
                        columna.setMinWidth(270);
                        break;
                    case 5:
                        //Conceptos XML
                        columna = tablaIvaAcred.getColumn(titulos[i]);
                        columna.setMinWidth(300);
                        break;
                    case 6:
                        //Sub-total
                        columna = tablaIvaAcred.getColumn(titulos[i]);
                        columna.setMinWidth(70);
                        break;
                    case 7:
                        //Iva
                        columna = tablaIvaAcred.getColumn(titulos[i]);
                        columna.setMinWidth(40);
                        break;
                    default:
                        break;
                }

            }
        } else {
            JOptionPane.showMessageDialog(this, "No existen archivos en este periodo para procesar");
        }

    }

    private void inicializarTablaTotalIva() {
        defaultTableIva = new DefaultTableModel();
        String[] titulos = {"Gastos 16%", "Compras 0%", "IVA", "Total"};
        Double[] valores = {0.00, 0.00, 0.00, 0.00};
        defaultTableIva.setColumnIdentifiers(titulos);
        defaultTableIva.addRow(valores);
        TableColumn columna;
        tablaTotalIva.setModel(defaultTableIva);
        for (int i = 0; i <= 3; i++) {
            switch (i) {
                case 0:
                    //factura
                    columna = tablaTotalIva.getColumn(titulos[i]);
                    columna.setMinWidth(90);
                    break;
                case 1:
                    //Fecha Factura
                    columna = tablaTotalIva.getColumn(titulos[i]);
                    columna.setMinWidth(90);
                    break;
                case 2:
                    //#poliza
                    columna = tablaTotalIva.getColumn(titulos[i]);
                    columna.setMinWidth(40);
                    break;
                case 3:
                    //Fecha Poliza
                    columna = tablaTotalIva.getColumn(titulos[i]);
                    columna.setMinWidth(50);
                    break;
                default:
                    break;
            }

        }

    }

    /**
     * Metodo que preconfigura el diseño del jframe
     */
    private void preConfiguracion() {
        //Posicion del jframe
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        int height = pantalla.height;
        int width = pantalla.width;
        this.setSize(width / 2, height / 2);
        this.setLocationRelativeTo(null);
        //Elementos adicionales

        txta_Concepto.setVisible(false);
        txta_Concepto.setLineWrap(true);
        lbAsunto.setVisible(false);
        lbEmpresa.setVisible(false);
        lbPeriodo.setVisible(false);
        lbRegistros.setVisible(false);

    }

    private void datosResumen(String mes, int registros, int anio) {
        numRegistros = registros;
        empresa = "Agroecología Intensiva para el Campo S.A de C.V";
        asunto = "Relación del 100% de Operaciones con Proveedores Tasa 16 %";
        periodo = mes + " " + anio;
        lbAsunto.setText(asunto);
        lbAsunto.setVisible(true);
        lbEmpresa.setText(empresa);
        lbEmpresa.setVisible(true);
        lbPeriodo.setText("Periodo: " + periodo);
        lbPeriodo.setVisible(true);
        lbRegistros.setText("Registros: " + String.valueOf(numRegistros));
        lbRegistros.setVisible(true);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane ScrollTotalIva;
    private javax.swing.JScrollPane SpIva;
    private javax.swing.JButton btnExcel;
    private javax.swing.JButton btnGuardarIva;
    private javax.swing.JButton btnProcesarIva;
    private com.toedter.calendar.JYearChooser calendarAnio;
    private com.toedter.calendar.JMonthChooser calendarMes;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JLabel lbAsunto;
    private javax.swing.JLabel lbEmpresa;
    private javax.swing.JLabel lbPeriodo;
    private javax.swing.JLabel lbRegistros;
    private javax.swing.JMenu menuIva;
    private javax.swing.JPanel panelConcepto;
    private javax.swing.JPanel panelInfo;
    private javax.swing.JPanel panelMenus;
    private javax.swing.JPanel panelResumenDatos;
    private javax.swing.JScrollPane scrollPaneConcepto;
    private javax.swing.JTable tablaIvaAcred;
    private javax.swing.JTable tablaTotalIva;
    private javax.swing.JTextArea txta_Concepto;
    // End of variables declaration//GEN-END:variables
}