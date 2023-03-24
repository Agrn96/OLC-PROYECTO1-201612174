/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Principal;

import Analizador.Parser;
import Analizador.lexico;
import Automata.Arbol;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.StringReader;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import olc1_proyecto1_201612174.OLC1_PROYECTO1_201612174;
import static olc1_proyecto1_201612174.OLC1_PROYECTO1_201612174.OpenArchivo;
import static olc1_proyecto1_201612174.OLC1_PROYECTO1_201612174.arboles;

public class VentanaPrincipal extends javax.swing.JFrame {

    JFileChooser jFile = new JFileChooser();
    File file;
    int contArbol = 1;
    int contSig = 1;
    int contTabla = 1;
    int cont_afd = 1;
    String ruta = "";
    int contRepo = 0;
    // String cadenaAnilar = "";

    public VentanaPrincipal() {
        initComponents();
    }
//___________________________metodos referentes al archivo

    public void cargarArchivo() {
        if (jFile.showDialog(null, "Open") == JFileChooser.APPROVE_OPTION) {
            file = jFile.getSelectedFile();
            ruta = file.getPath();
            String cadenaAnilar = olc1_proyecto1_201612174.OLC1_PROYECTO1_201612174.OpenArchivo(file, ruta);
            jTextArea1.setText(cadenaAnilar);
        }
    }

    public void guardarComo() {
        JFileChooser guardar = new JFileChooser();
        guardar.showSaveDialog(null);
        guardar.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        File archivo = guardar.getSelectedFile();

        FileWriter write;
        try {
            write = new FileWriter(archivo, true);
            write.write(jTextArea1.getText());
            write.close();
        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Error al guardar el archivo");
        }

    }

    public void guardarArchivo() {
        if (!"".equals(ruta)) {
            try {
                File file = new File(ruta);
                String contenido = jTextArea1.getText();
                String mensaje = guardar2(contenido, file);
                if (mensaje != null) {
                    ruta = file.getPath();
                    String name = file.getName();
                    JOptionPane.showMessageDialog(null, mensaje);
                } else {
                    JOptionPane.showMessageDialog(null, "Erro al guardar", "", JOptionPane.ERROR_MESSAGE);

                }
            } catch (Exception e) {
            }
        }
    }

    public String guardar2(String contenido, File file) {
        String cadena = "";
        try {
            FileOutputStream salida = new FileOutputStream(file);
            byte temp[] = contenido.getBytes();
            salida.write(temp);
            cadena = "Guardado correctamente";
        } catch (Exception e) {
        }

        return cadena;
    }

//___________________________________________________
    public void mostrarArbol() {
        ImageIcon img = new ImageIcon("ARBOLES_201612174/arbol" + contArbol + ".jpg");
        JLabel label = new JLabel();
        label.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());
        label.setIcon(img);
        jScrollPane2.setViewportView(label);
    }

    public void mostrarTablaSig() {
        ImageIcon img = new ImageIcon("SIGUIENTES_201612174/siguientes" + contSig + ".jpg");
        JLabel label = new JLabel();
        label.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());
        label.setIcon(img);
        jScrollPane2.setViewportView(label);
    }

    public void mostrarTablaTransicion() {
        ImageIcon img = new ImageIcon("TRANSICIONES_201612174/Transiciones" + contTabla + ".jpg");
        JLabel label = new JLabel();
        label.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());
        label.setIcon(img);
        jScrollPane2.setViewportView(label);
    }

    public void mostraAFD() {
        ImageIcon img = new ImageIcon("AFD_201612174/AFD" + cont_afd + ".jpg");
        JLabel label = new JLabel();
        label.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());
        label.setIcon(img);
        jScrollPane2.setViewportView(label);
    }

//_____________________________
    public void Analizar() {
        try {
            System.out.println("_______iniciar analisis__________");
            String cadena = "//soy un comentario \n //comentario 2";
            lexico lex = new lexico(new BufferedReader(new StringReader(jTextArea1.getText())));
            Parser analisar = new Parser(lex);
            analisar.parse();
        } catch (Exception e) {
            System.err.print("Error");
        }

    }

//__________________
    public void generarGrafos() {
        int cont = 0;
        for (Arbol arbol : arboles) {
            cont++;
            arbol.asignarAnulabilidad(arbol.rootArbol());
            String cadena2 = arbol.getContenido(arbol.rootArbol());
            System.out.println("NOMBRE:_" + arbol.getName());

            // arbol.ObtenerSiguientes(arbol.rootArbol());
            arbol.crearTabla_siguientes();
            //__________
            System.out.println("___________iNicio___");
            arbol.crearPrimerEstado();
            arbol.crearEstado();
            arbol.mostrarTransiicones();
            arbol.asignarEstado_aceptacion();
            System.out.println("__________Fin______________________");

            arbol.graficar("ARBOLES_201612174/arbol" + String.valueOf(cont) + ".jpg", "ARBOLES_201612174/arbol" + String.valueOf(cont), cadena2);
            arbol.graficar("SIGUIENTES_201612174/siguientes" + String.valueOf(cont) + ".jpg", "SIGUIENTES_201612174/siguientes" + String.valueOf(cont), arbol.GraficarTabla());

            arbol.graficar("TRANSICIONES_201612174/Transiciones" + String.valueOf(cont) + ".jpg", "TRANSICIONES_201612174/Transiciones" + String.valueOf(cont), arbol.GraficarTranciones());
            arbol.graficar("AFD_201612174/AFD" + String.valueOf(cont) + ".jpg", "AFD_201612174/AFD" + String.valueOf(cont), arbol.GenerarAFD());

        }
    }
//_________________

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jComboBox2 = new javax.swing.JComboBox<>();
        jButton4 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Abrir", "Guardar", "Guardar como", "Generar archivo de salida" }));
        jComboBox1.setToolTipText("");
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jButton1.setText("Aceptar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jButton2.setText("1Generar DFA");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Analizar Entradas");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Arboles", "Siguientes", "Transiciones", "Automatas" }));

        jButton4.setText("Aceptar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jScrollPane3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane3.setViewportView(jTextArea2);

        jButton5.setText("Anterior");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("Next");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(57, 57, 57)
                                .addComponent(jScrollPane2))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton1)
                                .addGap(109, 109, 109)
                                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40)
                                .addComponent(jButton4)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 888, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton2)
                                .addGap(47, 47, 47)
                                .addComponent(jButton3)
                                .addGap(118, 118, 118)
                                .addComponent(jButton5)
                                .addGap(96, 96, 96)
                                .addComponent(jButton6)))
                        .addGap(0, 41, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4))
                .addGap(51, 51, 51)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton2)
                            .addComponent(jButton3)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton5)
                            .addComponent(jButton6))))
                .addGap(35, 35, 35)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(79, Short.MAX_VALUE))
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
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        if (!OLC1_PROYECTO1_201612174.arboles.isEmpty()) {
            OLC1_PROYECTO1_201612174.arboles.clear();
        } else {
            Analizar();

            jTextArea2.setText("Anlisis realizado con exito");
            this.generarGrafos();
            JOptionPane.showMessageDialog(null, "Anlisis finalizado");
        }


    }//GEN-LAST:event_jButton2ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:


    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:

        if (jComboBox1.getSelectedItem() == "Abrir") {
            if (!OLC1_PROYECTO1_201612174.arboles.isEmpty()) {
                OLC1_PROYECTO1_201612174.arboles.clear();
                jTextArea2.setText("");
            }

            jTextArea2.setText("Archivo Abierto");

            this.cargarArchivo();
        } else if (jComboBox1.getSelectedItem() == "Guardar como") {
            guardarComo();
        } else if (jComboBox1.getSelectedItem() == "Guardar") {
            guardarArchivo();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        int size = olc1_proyecto1_201612174.OLC1_PROYECTO1_201612174.arboles.size();
        if (jComboBox2.getSelectedItem() == "Arboles") {
            mostrarArbol();
        } else if (jComboBox2.getSelectedItem() == "Siguientes") {
            mostrarTablaSig();
        } else if (jComboBox2.getSelectedItem() == "Transiciones") {
            mostrarTablaTransicion();
        } else if (jComboBox2.getSelectedItem() == "Automatas") {
            mostraAFD();
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:

        int size = olc1_proyecto1_201612174.OLC1_PROYECTO1_201612174.arboles.size();
        if (jComboBox2.getSelectedItem() == "Arboles") {
            if (contArbol > 1) {
                contArbol -= 1;
                mostrarArbol();
            } else {
                contArbol = size;
            }
        } else if (jComboBox2.getSelectedItem() == "Siguientes") {
            if (contSig > 1) {
                contSig -= 1;
                mostrarTablaSig();
            } else {
                contSig = size;
            }
        } else if (jComboBox2.getSelectedItem() == "Transiciones") {
            if (contTabla > 1) {
                contTabla -= 1;
                mostrarTablaTransicion();
            } else {
                contTabla = size;
            }
        } else if (jComboBox2.getSelectedItem() == "Automatas") {
            if (cont_afd > 1) {
                cont_afd -= 1;
                mostraAFD();
            } else {
                cont_afd = size;
            }
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:

        int size = olc1_proyecto1_201612174.OLC1_PROYECTO1_201612174.arboles.size();
        if (jComboBox2.getSelectedItem() == "Arboles") {
            if (contArbol < size) {
                contArbol += 1;
                mostrarArbol();
            } else {
                contArbol = 0;
            }
        } else if (jComboBox2.getSelectedItem() == "Siguientes") {
            if (contSig < size) {
                contSig += 1;
                mostrarTablaSig();
            } else {
                contSig = 0;
            }
        } else if (jComboBox2.getSelectedItem() == "Transiciones") {
            if (contTabla < size) {
                contTabla += 1;
                mostrarTablaTransicion();
            } else {
                contTabla = 0;
            }
        } else if (jComboBox2.getSelectedItem() == "Automatas") {
            if (cont_afd < size) {
                cont_afd += 1;
                mostraAFD();
            } else {
                cont_afd = 0;
            }
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    // End of variables declaration//GEN-END:variables
}
