/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controllers.ControllerAction;
import Models.RelacionActividades;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Vigilancia
 */
public class jDialogRelacion extends javax.swing.JDialog {

    private ControllerAction controllerAction;
    private RelacionActividades relacionActividades;

    /**
     * Creates new form jDialogRelacion
     */
    public jDialogRelacion(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        llenarComboBoxRelacion();
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
        jLabel1 = new javax.swing.JLabel();
        txtNuevaRelacion = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        btnNuevoActividad = new javax.swing.JButton();
        btnGuardarRelacion = new javax.swing.JButton();
        btnCancelarNuevaActividad = new javax.swing.JButton();
        txtClave = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cmbActividad = new javax.swing.JComboBox<>();
        cmbox_Rfc = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        btnNuevoRfc = new javax.swing.JButton();
        btnGuardarAsociar = new javax.swing.JButton();
        btnCancelarAsociar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtNuevoRfc = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Opciones Relación con Actividad");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Nueva Relación"));

        jLabel1.setText("Nombre de la Relacion con Actividad");

        txtNuevaRelacion.setEnabled(false);
        txtNuevaRelacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNuevaRelacionActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Nuevo / Guardar / Cancelar"));

        btnNuevoActividad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/add-file.png"))); // NOI18N
        btnNuevoActividad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActividadActionPerformed(evt);
            }
        });

        btnGuardarRelacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/confirmacion32.png"))); // NOI18N
        btnGuardarRelacion.setEnabled(false);
        btnGuardarRelacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarRelacionActionPerformed(evt);
            }
        });

        btnCancelarNuevaActividad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/cancelar.png"))); // NOI18N
        btnCancelarNuevaActividad.setEnabled(false);
        btnCancelarNuevaActividad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarNuevaActividadActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(btnNuevoActividad, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGuardarRelacion, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelarNuevaActividad, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnCancelarNuevaActividad, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                    .addComponent(btnNuevoActividad, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGuardarRelacion, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(4, 4, 4))
        );

        txtClave.setEnabled(false);

        jLabel5.setText("Clave");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNuevaRelacion, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtClave))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNuevaRelacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtClave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder("Asociar RFC")));

        jLabel2.setText("Actividad");

        cmbox_Rfc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cmbox_RfcFocusGained(evt);
            }
        });

        jLabel3.setText("RFC Asociado");

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Nuevo / Guardar / Cancelar"));

        btnNuevoRfc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/add-file.png"))); // NOI18N
        btnNuevoRfc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoRfcActionPerformed(evt);
            }
        });

        btnGuardarAsociar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/confirmacion32.png"))); // NOI18N
        btnGuardarAsociar.setEnabled(false);

        btnCancelarAsociar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/cancelar.png"))); // NOI18N
        btnCancelarAsociar.setEnabled(false);
        btnCancelarAsociar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarAsociarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(btnNuevoRfc, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGuardarAsociar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelarAsociar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnCancelarAsociar))
                    .addComponent(btnNuevoRfc, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGuardarAsociar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(4, 4, 4))
        );

        jLabel4.setText("RFC");

        txtNuevoRfc.setEnabled(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbActividad, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbox_Rfc, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(txtNuevoRfc, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbActividad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbox_Rfc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNuevoRfc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNuevaRelacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNuevaRelacionActionPerformed

    }//GEN-LAST:event_txtNuevaRelacionActionPerformed

    private void cmbox_RfcFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cmbox_RfcFocusGained
        btnGuardarAsociar.setEnabled(true);
        btnCancelarAsociar.setEnabled(true);
        btnNuevoRfc.setEnabled(false);
        txtNuevoRfc.setText("");

    }//GEN-LAST:event_cmbox_RfcFocusGained

    private void btnCancelarAsociarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarAsociarActionPerformed
        txtNuevoRfc.setText("");
        txtNuevoRfc.setEnabled(false);
        btnNuevoRfc.setEnabled(true);
        btnGuardarAsociar.setEnabled(false);
        cmbox_Rfc.setEnabled(true);
        btnCancelarAsociar.setEnabled(false);
    }//GEN-LAST:event_btnCancelarAsociarActionPerformed

    private void btnNuevoRfcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoRfcActionPerformed
        txtNuevoRfc.setEnabled(true);
        txtNuevoRfc.setText("");
        btnNuevoRfc.setEnabled(false);
        btnGuardarAsociar.setEnabled(true);
        btnCancelarAsociar.setEnabled(true);
        cmbox_Rfc.setEnabled(false);
    }//GEN-LAST:event_btnNuevoRfcActionPerformed

    private void btnGuardarRelacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarRelacionActionPerformed
        //Guardando nueva relacion
        String nuevaRelAct = ((txtNuevaRelacion.getText().isEmpty()) || (txtNuevaRelacion.getText() == null)) ? "" : txtNuevaRelacion.getText();
        String clave = ((txtClave.getText().isEmpty()) || (txtClave.getText() == null)) ? "" : txtClave.getText();
        if ("".equals(nuevaRelAct) || "".equals(clave)) {
            JOptionPane.showMessageDialog(rootPane, "Existen datos vacios. \n Verifique por favor");
        } else {
            controllerAction = new ControllerAction();
            boolean resultado = controllerAction.solicitarInsertNuevaRelacion(nuevaRelAct, clave);
            if (resultado) {
                JOptionPane.showMessageDialog(rootPane, "Exito al guardar");
                txtNuevaRelacion.setEnabled(true);
                txtNuevaRelacion.setText("");
                txtClave.setEnabled(true);
                txtClave.setText("");
                btnGuardarRelacion.setEnabled(true);
                btnCancelarNuevaActividad.setEnabled(true);
                btnNuevoActividad.setEnabled(false);
                cmbActividad.removeAllItems();
                cmbox_Rfc.removeAllItems();
                llenarComboBoxRelacion();

            } else {
                JOptionPane.showMessageDialog(rootPane, "Hubo un problema al guardar la información. \n Verifique los datos he intente de nuevo");
            }
        }
    }//GEN-LAST:event_btnGuardarRelacionActionPerformed

    private void btnNuevoActividadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActividadActionPerformed
        txtNuevaRelacion.setEnabled(true);
        txtNuevaRelacion.setText("");
        txtClave.setEnabled(true);
        txtClave.setText("");
        btnGuardarRelacion.setEnabled(true);
        btnCancelarNuevaActividad.setEnabled(true);
        btnNuevoActividad.setEnabled(false);
    }//GEN-LAST:event_btnNuevoActividadActionPerformed

    private void btnCancelarNuevaActividadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarNuevaActividadActionPerformed
        txtNuevaRelacion.setEnabled(false);
        txtNuevaRelacion.setText("");
        txtClave.setEnabled(false);
        txtClave.setText("");
        btnGuardarRelacion.setEnabled(false);
        btnCancelarNuevaActividad.setEnabled(false);
        btnNuevoActividad.setEnabled(true);
    }//GEN-LAST:event_btnCancelarNuevaActividadActionPerformed

    //FUNCIONES
    private void llenarComboBoxRelacion() {
        controllerAction = new ControllerAction();
        List<RelacionActividades> relaciones = controllerAction.procesarListaRelaciones();
        if (!relaciones.isEmpty()) {
            for (int i = 0; i < relaciones.size(); i++) {
                cmbActividad.addItem(relaciones.get(i).getCodigoRelacion() + " " + relaciones.get(i).getDescripcionRelacion());
            }
        } else {
            cmbActividad.setEnabled(false);
        }
        relaciones = controllerAction.procesarListaRelacionesActividad();
        if (!relaciones.isEmpty()) {
            for (int i = 0; i < relaciones.size(); i++) {
                cmbox_Rfc.addItem(relaciones.get(i).getRfcRelacion());
            }
        } else {
            cmbox_Rfc.setEnabled(false);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(jDialogRelacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jDialogRelacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jDialogRelacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jDialogRelacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                jDialogRelacion dialog = new jDialogRelacion(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelarAsociar;
    private javax.swing.JButton btnCancelarNuevaActividad;
    private javax.swing.JButton btnGuardarAsociar;
    private javax.swing.JButton btnGuardarRelacion;
    private javax.swing.JButton btnNuevoActividad;
    private javax.swing.JButton btnNuevoRfc;
    private javax.swing.JComboBox<String> cmbActividad;
    private javax.swing.JComboBox<String> cmbox_Rfc;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JTextField txtClave;
    private javax.swing.JTextField txtNuevaRelacion;
    private javax.swing.JTextField txtNuevoRfc;
    // End of variables declaration//GEN-END:variables
}
