/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;
//Poi 3.16

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author horusblack
 */
public class GeneradorExcel {

    private int numFilasTabla, numColumnasTabla, empezarLlenadoDesdeFila, ultimaFilaRegistros;
    private Workbook book;
    private JFileChooser seleccionar;
    private String nombreEmpresa, direccion, rfc, otros;

    /**
     * Función que genera un archivo excel apartir de los datos recibidos por
     * una tabla y confirma que los datos se procesaron correctamente en la BD.
     *
     * @param tablaCienPorciento
     * @param tablaTotalCien
     * @param tituloPestaniaHoja
     * @param periodo
     * @param anio
     * @param exitoExport
     * @param empresa
     * @return boolean
     */
    public boolean generarExcelCienIvaAcred(JTable tablaCienPorciento, JTable tablaTotalCien, String tituloPestaniaHoja, String periodo, String anio, boolean exitoExport, String empresa) {
        seleccionar = new JFileChooser();
        boolean exito1, exito2, exito3 = false;
        exito1 = exitoExport;
        int valorEmpresa = Integer.parseInt(empresa);
        if (valorEmpresa == 1) {
            nombreEmpresa = "ASOCIACIÓN DE SORGEROS DE TEHUACAN TIERRA DE DIOSES, S.A. DE C.V.";
            direccion = "PRIVADA 12 ORIENTE NO. 107, FRANCISCO SARABIA, TEHUACAN, PUEBLA";
            rfc = "";

        } else if (valorEmpresa == 2) {
            nombreEmpresa = "AGROECOLOGIA INTENSIVA PARA EL CAMPO S.A. DE C.V.";
            direccion = "CARRETERA MEXICO OAXACA KM 97, JANTETELCO, JANTETELCO MORELOS, C.P. 62970";
            rfc = "R.F.C    AIC171129UAA";
        }

        File archivo;
        if (seleccionar.showDialog(null, "Exportador Excel") == JFileChooser.APPROVE_OPTION) {
            archivo = seleccionar.getSelectedFile();

            //nuevo archivo
            book = new XSSFWorkbook();
            //hoja de trabajo
            Sheet hoja = book.createSheet(tituloPestaniaHoja + " " + periodo.toUpperCase() + " " + anio);
            //Titulos de tablas combinadas
            /*Titulos y subtitulos de cabecezas*/
            CellStyle tituloEstilo = book.createCellStyle();
            CellStyle subTitulos = book.createCellStyle();
            CellStyle subTitulosDos = book.createCellStyle();
            CellStyle sbt3 = book.createCellStyle();
            CellStyle sbt4 = book.createCellStyle();

            //Definiendo alineamiento de los titulos y subtitulos
            tituloEstilo.setAlignment(HorizontalAlignment.CENTER);
            tituloEstilo.setVerticalAlignment(VerticalAlignment.CENTER);

            subTitulos.setAlignment(HorizontalAlignment.CENTER);
            subTitulos.setVerticalAlignment(VerticalAlignment.CENTER);

            subTitulosDos.setAlignment(HorizontalAlignment.CENTER);
            subTitulosDos.setVerticalAlignment(VerticalAlignment.CENTER);

            sbt3.setAlignment(HorizontalAlignment.LEFT);
            sbt3.setVerticalAlignment(VerticalAlignment.CENTER);

            sbt4.setAlignment(HorizontalAlignment.LEFT);
            sbt4.setVerticalAlignment(VerticalAlignment.CENTER);

            /*
            Importar font de Poi
            Propiedades de la fuente
             */
            Font fuenteTitulo = (Font) book.createFont();
            fuenteTitulo.setFontName("Arial");
            fuenteTitulo.setBold(true);
            fuenteTitulo.setColor(IndexedColors.BLACK.getIndex());
            fuenteTitulo.setFontHeightInPoints((short) 19);

            Font fuenteSubtitulo = (Font) book.createFont();
            fuenteSubtitulo.setFontName("Arial");
            fuenteSubtitulo.setBold(true);
            fuenteSubtitulo.setColor(IndexedColors.BLACK.getIndex());
            fuenteSubtitulo.setFontHeightInPoints((short) 16);

            Font fuenteSubtituloDos = (Font) book.createFont();
            fuenteSubtituloDos.setFontName("Arial");
            fuenteSubtituloDos.setBold(true);
            fuenteSubtituloDos.setColor(IndexedColors.BLACK.getIndex());
            fuenteSubtituloDos.setFontHeightInPoints((short) 13);

            //editando el titulo
            tituloEstilo.setFont(fuenteTitulo);
            //Crea fila en hoja
            Row filaTitulo = hoja.createRow(1);
            //Empieza a dibujar desde x celda
            Cell celdaTitulo = filaTitulo.createCell(2);
            celdaTitulo.setCellStyle(tituloEstilo);
            //Titulo de la cabecera en Hoja de trabajo
            celdaTitulo.setCellValue(nombreEmpresa);

            //(int firstRow, int lastRow, int firstCol, int lastCol)
            //alcance del conbinado de celdas
            hoja.addMergedRegion(new CellRangeAddress(1, 1, 2, 10));

            subTitulos.setFont(fuenteSubtitulo);
            Row filaSubtitulo = hoja.createRow(2);
            Cell celdaSubTitulo = filaSubtitulo.createCell(2);
            celdaSubTitulo.setCellStyle(subTitulos);
            celdaSubTitulo.setCellValue(rfc);
            hoja.addMergedRegion(new CellRangeAddress(2, 2, 2, 10));

            subTitulosDos.setFont(fuenteSubtituloDos);
            Row filaSubtituloDos = hoja.createRow(3);
            Cell celdaSubTituloDos = filaSubtituloDos.createCell(2);
            celdaSubTituloDos.setCellStyle(subTitulosDos);
            celdaSubTituloDos.setCellValue(direccion);
            hoja.addMergedRegion(new CellRangeAddress(3, 3, 2, 10));

            sbt3.setFont(fuenteSubtituloDos);
            Row fs3 = hoja.createRow(5);
            Cell cs3 = fs3.createCell(1);
            cs3.setCellStyle(sbt3);
            cs3.setCellValue("RELACION DEL 100% DE OPERACIONES CON PROVEEDORES TASA 16 %");
            hoja.addMergedRegion(new CellRangeAddress(5, 5, 1, 4));

            sbt4.setFont(fuenteSubtituloDos);
            Row fs4 = hoja.createRow(6);
            Cell cs4 = fs4.createCell(1);
            cs4.setCellStyle(sbt4);
            cs4.setCellValue("IVA ACREDITABLE: " + periodo.toUpperCase() + " " + anio);
            hoja.addMergedRegion(new CellRangeAddress(6, 6, 1, 4));

            String[] cabecera = new String[]{"", "FECHA DE FACTURA", "FOLIO DE FACTURA", "FOLIO UUID", "PROVEEDOR", "RFC", "CONCEPTO", "BASE 0%", "BASE 16%", "RETENCIÓN 4%", "RETENCIÓN 10%",
                "RETENCIÓN 10.67%", "COUTA COMPENSATORIA", "IVA", "TOTAL", "FECHA DE PAGO", "CUENTA DE BANCO", "FORMA DE PAGO", "TIPO POLIZA", "NO. POLIZA", "RELACIÓN CON ACTIVIDAD",
                "CRUCE EDO. CUENTA"};

            //Bordes de las celdas
            CellStyle celdasCabeceraTabla = book.createCellStyle();

            celdasCabeceraTabla.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
            celdasCabeceraTabla.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            celdasCabeceraTabla.setBorderBottom(BorderStyle.THIN);
            celdasCabeceraTabla.setBorderLeft(BorderStyle.THIN);
            celdasCabeceraTabla.setBorderRight(BorderStyle.THIN);
            celdasCabeceraTabla.setBorderTop(BorderStyle.THIN);
            celdasCabeceraTabla.setAlignment(HorizontalAlignment.CENTER);
            celdasCabeceraTabla.setVerticalAlignment(VerticalAlignment.CENTER);

            Font font = book.createFont();
            font.setFontName("Arial");
            font.setBold(true);
            font.setColor(IndexedColors.WHITE.getIndex());
            font.setFontHeightInPoints((short) 12);
            celdasCabeceraTabla.setFont(font);
//            //CABECERAS
            Row filaEncabezado_1 = hoja.createRow(7);
            //Titulo de las cabeceras de excel
            for (int i = 1; i < cabecera.length; i++) {

                Cell celdaEncabezado = filaEncabezado_1.createCell(i);
                celdaEncabezado.setCellStyle(celdasCabeceraTabla);
                celdaEncabezado.setCellValue(cabecera[i]);
            }

            //INICIA PARTE DATOS
            //Numero filas
            numFilasTabla = tablaCienPorciento.getRowCount();
            //Numero columnas
            numColumnasTabla = (tablaCienPorciento.getColumnCount() - 4);
            //No.filas de cabecera+inicio de datos+1
            ultimaFilaRegistros = (tablaCienPorciento.getRowCount()) + 10;
            empezarLlenadoDesdeFila = 8;

            CellStyle datosEstilo = book.createCellStyle();
            //bordes de la tabla

            datosEstilo.setBorderBottom(BorderStyle.THIN);
            datosEstilo.setBorderLeft(BorderStyle.THIN);
            datosEstilo.setBorderRight(BorderStyle.THIN);
            datosEstilo.setBorderTop(BorderStyle.THIN);

            for (int i = 0; i < numFilasTabla; i++) {
                //?
                Row fila = hoja.createRow(i + empezarLlenadoDesdeFila);
                //Obteniendo información de las columnas

                for (int a = 1; a < numColumnasTabla; a++) {
                    Cell celda;
                    celda = fila.createCell(a);
                    celda.setCellStyle(datosEstilo);
                    if (tablaCienPorciento.getValueAt(i, a) == null) {
                        celda.setCellValue("");
                    } else {
                        if (a < 22) {
                            celda.setCellValue(String.valueOf(tablaCienPorciento.getValueAt(i, a)));
                        }

                    }
                }

            }
            //FIN PARTE DATOS
            //DATOS TOTAL
            CellStyle txtT1 = book.createCellStyle();
            txtT1.setAlignment(HorizontalAlignment.CENTER);

            Font fontTotal = (Font) book.createFont();
            fontTotal.setFontName("Arial");
            fontTotal.setBold(true);
            fontTotal.setColor(IndexedColors.BLACK.getIndex());
            fontTotal.setFontHeightInPoints((short) 12);
            txtT1.setFont(fontTotal);

            CellStyle txtT2 = book.createCellStyle();
            txtT1.setAlignment(HorizontalAlignment.CENTER);

            Font cabeceraTotales = (Font) book.createFont();
            cabeceraTotales.setFontName("Arial");
            cabeceraTotales.setBold(true);
            cabeceraTotales.setColor(IndexedColors.BLACK.getIndex());
            cabeceraTotales.setFontHeightInPoints((short) 10);
            txtT2.setFont(cabeceraTotales);

            Row stx = hoja.createRow(ultimaFilaRegistros + 1);
            Cell st_1 = stx.createCell(7);
            st_1.setCellStyle(txtT2);
            st_1.setCellValue("Total Base 0%");

            Cell st_2 = stx.createCell(8);
            st_2.setCellStyle(txtT2);
            st_2.setCellValue("Total Base 16%");

            Cell st_3 = stx.createCell(9);
            st_3.setCellStyle(txtT2);
            st_3.setCellValue("Total Retención 4%");

            Cell st_4 = stx.createCell(10);
            st_4.setCellStyle(txtT2);
            st_4.setCellValue("Total Retención 10%");

            Cell st_5 = stx.createCell(11);
            st_5.setCellStyle(txtT2);
            st_5.setCellValue("Total Retención 10.67%");

            Cell st_6 = stx.createCell(12);
            st_6.setCellStyle(txtT2);
            st_6.setCellValue("Total Cuota Compensatoria");

            Cell st_7 = stx.createCell(13);
            st_7.setCellStyle(txtT2);
            st_7.setCellValue("Total IVA");

            Cell st_8 = stx.createCell(14);
            st_8.setCellStyle(txtT2);
            st_8.setCellValue("Total Final");

            Row fila = hoja.createRow(ultimaFilaRegistros + 2);
            //Obteniendo información de las columnas

            for (int a = 0; a < tablaTotalCien.getColumnCount(); a++) {
                Cell celda;
                celda = fila.createCell((a) + 7);
                celda.setCellStyle(datosEstilo);
                celda.setCellValue(String.valueOf(tablaTotalCien.getValueAt(0, a)));
            }

            //FIN TOTALES
            for (int i = 0; i < 22; i++) {
                hoja.autoSizeColumn(i);
            }

            try {
                book.write(new FileOutputStream(archivo + ".xlsx"));
                exito2 = true;
                exito3 = (exito1 && exito2);

            } catch (IOException ex) {
                Logger.getLogger(GeneradorExcel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return exito3;
    }

    /**
     * Funcion que genera un archivo excel a partir de los datos recibidos por
     * una jtable.
     *
     * @param tablaCienPorciento
     * @param tablaTotalCien
     * @param tituloPestaniaHoja
     * @param periodo
     * @param anio
     * @param Empresa
     */
    public void generarSoloExcelCienIva(JTable tablaCienPorciento, JTable tablaTotalCien, String tituloPestaniaHoja, String periodo, String anio, String Empresa) {
        seleccionar = new JFileChooser();
        File archivo;
        int valorEmpresa = Integer.parseInt(Empresa);
        if (valorEmpresa == 1) {
            nombreEmpresa = "ASOCIACIÓN DE SORGEROS DE TEHUACAN TIERRA DE DIOSES, S.A. DE C.V.";
            direccion = "PRIVADA 12 ORIENTE NO. 107, FRANCISCO SARABIA, TEHUACAN, PUEBLA";
            rfc = "";

        } else if (valorEmpresa == 2) {
            nombreEmpresa = "AGROECOLOGIA INTENSIVA PARA EL CAMPO S.A. DE C.V.";
            direccion = "CARRETERA MEXICO OAXACA KM 97, JANTETELCO, JANTETELCO MORELOS, C.P. 62970";
            rfc = "R.F.C    AIC171129UAA";
        }
        if (seleccionar.showDialog(null, "Exportador Excel") == JFileChooser.APPROVE_OPTION) {
            archivo = seleccionar.getSelectedFile();

            //nuevo archivo
            book = new XSSFWorkbook();
            //hoja de trabajo
            Sheet hoja = book.createSheet(tituloPestaniaHoja + " " + periodo.toUpperCase() + " " + anio);
            //Titulos de tablas combinadas
            /*Titulos y subtitulos de cabecezas*/
            CellStyle tituloEstilo = book.createCellStyle();
            CellStyle subTitulos = book.createCellStyle();
            CellStyle subTitulosDos = book.createCellStyle();
            CellStyle sbt3 = book.createCellStyle();
            CellStyle sbt4 = book.createCellStyle();

            //Definiendo alineamiento de los titulos y subtitulos
            tituloEstilo.setAlignment(HorizontalAlignment.CENTER);
            tituloEstilo.setVerticalAlignment(VerticalAlignment.CENTER);

            subTitulos.setAlignment(HorizontalAlignment.CENTER);
            subTitulos.setVerticalAlignment(VerticalAlignment.CENTER);

            subTitulosDos.setAlignment(HorizontalAlignment.CENTER);
            subTitulosDos.setVerticalAlignment(VerticalAlignment.CENTER);

            sbt3.setAlignment(HorizontalAlignment.LEFT);
            sbt3.setVerticalAlignment(VerticalAlignment.CENTER);

            sbt4.setAlignment(HorizontalAlignment.LEFT);
            sbt4.setVerticalAlignment(VerticalAlignment.CENTER);

            /*
            Importar font de Poi
            Propiedades de la fuente
             */
            Font fuenteTitulo = (Font) book.createFont();
            fuenteTitulo.setFontName("Arial");
            fuenteTitulo.setBold(true);
            fuenteTitulo.setColor(IndexedColors.BLACK.getIndex());
            fuenteTitulo.setFontHeightInPoints((short) 19);

            Font fuenteSubtitulo = (Font) book.createFont();
            fuenteSubtitulo.setFontName("Arial");
            fuenteSubtitulo.setBold(true);
            fuenteSubtitulo.setColor(IndexedColors.BLACK.getIndex());
            fuenteSubtitulo.setFontHeightInPoints((short) 16);

            Font fuenteSubtituloDos = (Font) book.createFont();
            fuenteSubtituloDos.setFontName("Arial");
            fuenteSubtituloDos.setBold(true);
            fuenteSubtituloDos.setColor(IndexedColors.BLACK.getIndex());
            fuenteSubtituloDos.setFontHeightInPoints((short) 13);

            //editando el titulo
            tituloEstilo.setFont(fuenteTitulo);
            //Crea fila en hoja
            Row filaTitulo = hoja.createRow(1);
            //Empieza a dibujar desde x celda
            Cell celdaTitulo = filaTitulo.createCell(2);
            celdaTitulo.setCellStyle(tituloEstilo);
            //Titulo de la cabecera en Hoja de trabajo
            celdaTitulo.setCellValue(nombreEmpresa);

            //(int firstRow, int lastRow, int firstCol, int lastCol)
            //alcance del conbinado de celdas
            hoja.addMergedRegion(new CellRangeAddress(1, 1, 2, 10));

            subTitulos.setFont(fuenteSubtitulo);
            Row filaSubtitulo = hoja.createRow(2);
            Cell celdaSubTitulo = filaSubtitulo.createCell(2);
            celdaSubTitulo.setCellStyle(subTitulos);
            celdaSubTitulo.setCellValue(rfc);
            hoja.addMergedRegion(new CellRangeAddress(2, 2, 2, 10));

            subTitulosDos.setFont(fuenteSubtituloDos);
            Row filaSubtituloDos = hoja.createRow(3);
            Cell celdaSubTituloDos = filaSubtituloDos.createCell(2);
            celdaSubTituloDos.setCellStyle(subTitulosDos);
            celdaSubTituloDos.setCellValue(direccion);
            hoja.addMergedRegion(new CellRangeAddress(3, 3, 2, 10));

            sbt3.setFont(fuenteSubtituloDos);
            Row fs3 = hoja.createRow(5);
            Cell cs3 = fs3.createCell(1);
            cs3.setCellStyle(sbt3);
            cs3.setCellValue("RELACION DEL 100% DE OPERACIONES CON PROVEEDORES TASA 16 %");
            hoja.addMergedRegion(new CellRangeAddress(5, 5, 1, 4));

            sbt4.setFont(fuenteSubtituloDos);
            Row fs4 = hoja.createRow(6);
            Cell cs4 = fs4.createCell(1);
            cs4.setCellStyle(sbt4);
            cs4.setCellValue("IVA ACREDITABLE: " + periodo.toUpperCase() + " " + anio);
            hoja.addMergedRegion(new CellRangeAddress(6, 6, 1, 4));

            String[] cabecera = new String[]{"", "FECHA DE FACTURA", "FOLIO DE FACTURA", "FOLIO UUID", "PROVEEDOR", "RFC", "CONCEPTO", "BASE 0%", "BASE 16%", "RETENCIÓN 4%", "RETENCIÓN 10%",
                "RETENCIÓN 10.67%", "COUTA COMPENSATORIA", "IVA", "TOTAL", "FECHA DE PAGO", "CUENTA DE BANCO", "FORMA DE PAGO", "TIPO POLIZA", "NO. POLIZA", "RELACIÓN CON ACTIVIDAD",
                "CRUCE EDO. CUENTA"};

            //Bordes de las celdas
            CellStyle celdasCabeceraTabla = book.createCellStyle();

            celdasCabeceraTabla.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
            celdasCabeceraTabla.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            celdasCabeceraTabla.setBorderBottom(BorderStyle.THIN);
            celdasCabeceraTabla.setBorderLeft(BorderStyle.THIN);
            celdasCabeceraTabla.setBorderRight(BorderStyle.THIN);
            celdasCabeceraTabla.setBorderTop(BorderStyle.THIN);
            celdasCabeceraTabla.setAlignment(HorizontalAlignment.CENTER);
            celdasCabeceraTabla.setVerticalAlignment(VerticalAlignment.CENTER);

            Font font = book.createFont();
            font.setFontName("Arial");
            font.setBold(true);
            font.setColor(IndexedColors.WHITE.getIndex());
            font.setFontHeightInPoints((short) 12);
            celdasCabeceraTabla.setFont(font);
//            //CABECERAS
            Row filaEncabezado_1 = hoja.createRow(7);

            for (int i = 1; i < cabecera.length; i++) {

                Cell celdaEncabezado = filaEncabezado_1.createCell(i);
                celdaEncabezado.setCellStyle(celdasCabeceraTabla);
                celdaEncabezado.setCellValue(cabecera[i]);
            }

            //INICIA PARTE DATOS
            numFilasTabla = tablaCienPorciento.getRowCount();
            numColumnasTabla = (tablaCienPorciento.getColumnCount() - 4);
            //No.filas de cabecera+inicio de datos+1
            ultimaFilaRegistros = (tablaCienPorciento.getRowCount()) + 10;
            empezarLlenadoDesdeFila = 8;

            CellStyle datosEstilo = book.createCellStyle();
            //bordes de la tabla

            datosEstilo.setBorderBottom(BorderStyle.THIN);
            datosEstilo.setBorderLeft(BorderStyle.THIN);
            datosEstilo.setBorderRight(BorderStyle.THIN);
            datosEstilo.setBorderTop(BorderStyle.THIN);

            for (int i = 0; i < numFilasTabla; i++) {
                //?
                Row fila = hoja.createRow(i + empezarLlenadoDesdeFila);
                //Obteniendo información de las columnas

                for (int a = 1; a < numColumnasTabla; a++) {
                    Cell celda;
                    celda = fila.createCell(a);
                    celda.setCellStyle(datosEstilo);
                    if (tablaCienPorciento.getValueAt(i, a) == null) {
                        celda.setCellValue("");
                    } else {
                        if (a < 22) {
                            celda.setCellValue(String.valueOf(tablaCienPorciento.getValueAt(i, a)));
                        }

                    }
                }

            }
            //FIN PARTE DATOS
            //DATOS TOTAL
            CellStyle txtT1 = book.createCellStyle();
            txtT1.setAlignment(HorizontalAlignment.CENTER);

            Font fontTotal = (Font) book.createFont();
            fontTotal.setFontName("Arial");
            fontTotal.setBold(true);
            fontTotal.setColor(IndexedColors.BLACK.getIndex());
            fontTotal.setFontHeightInPoints((short) 12);
            txtT1.setFont(fontTotal);

            CellStyle txtT2 = book.createCellStyle();
            txtT1.setAlignment(HorizontalAlignment.CENTER);

            Font cabeceraTotales = (Font) book.createFont();
            cabeceraTotales.setFontName("Arial");
            cabeceraTotales.setBold(true);
            cabeceraTotales.setColor(IndexedColors.BLACK.getIndex());
            cabeceraTotales.setFontHeightInPoints((short) 10);
            txtT2.setFont(cabeceraTotales);

            Row stx = hoja.createRow(ultimaFilaRegistros + 1);
            Cell st_1 = stx.createCell(7);
            st_1.setCellStyle(txtT2);
            st_1.setCellValue("Total Base 0%");

            Cell st_2 = stx.createCell(8);
            st_2.setCellStyle(txtT2);
            st_2.setCellValue("Total Base 16%");

            Cell st_3 = stx.createCell(9);
            st_3.setCellStyle(txtT2);
            st_3.setCellValue("Total Retención 4%");

            Cell st_4 = stx.createCell(10);
            st_4.setCellStyle(txtT2);
            st_4.setCellValue("Total Retención 10%");

            Cell st_5 = stx.createCell(11);
            st_5.setCellStyle(txtT2);
            st_5.setCellValue("Total Retención 10.67%");

            Cell st_6 = stx.createCell(12);
            st_6.setCellStyle(txtT2);
            st_6.setCellValue("Total Cuota Compensatoria");

            Cell st_7 = stx.createCell(13);
            st_7.setCellStyle(txtT2);
            st_7.setCellValue("Total IVA");

            Cell st_8 = stx.createCell(14);
            st_8.setCellStyle(txtT2);
            st_8.setCellValue("Total Final");

            Row fila = hoja.createRow(ultimaFilaRegistros + 2);
            //Obteniendo información de las columnas

            for (int a = 0; a < tablaTotalCien.getColumnCount(); a++) {
                Cell celda;
                celda = fila.createCell((a) + 7);
                celda.setCellStyle(datosEstilo);
                celda.setCellValue(String.valueOf(tablaTotalCien.getValueAt(0, a)));
            }

            //FIN TOTALES
            for (int i = 0; i < 22; i++) {
                hoja.autoSizeColumn(i);
            }

            try {
                book.write(new FileOutputStream(archivo + ".xlsx"));
                JOptionPane.showMessageDialog(null, "Exportacion Exitosa");
            } catch (IOException ex) {
                Logger.getLogger(GeneradorExcel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     *
     * @param tablaCienPorciento
     * @param tablaTotalCien
     * @param tituloPestaniaHoja
     * @param periodo
     * @param anio
     * @param empresa
     * @return
     */
    public boolean generarSoloExcelOtros(JTable tablaCienPorciento, JTable tablaTotalCien, String tituloPestaniaHoja, String periodo, String anio, String empresa) {
        seleccionar = new JFileChooser();
        File archivo;
        int valorEmpresa = Integer.parseInt(empresa);
        if (valorEmpresa == 1) {
            nombreEmpresa = "ASOCIACIÓN DE SORGEROS DE TEHUACAN TIERRA DE DIOSES, S.A. DE C.V.";
            direccion = "PRIVADA 12 ORIENTE NO. 107, FRANCISCO SARABIA, TEHUACAN, PUEBLA";
            rfc = "";

        } else if (valorEmpresa == 2) {
            nombreEmpresa = "AGROECOLOGIA INTENSIVA PARA EL CAMPO S.A. DE C.V.";
            direccion = "CARRETERA MEXICO OAXACA KM 97, JANTETELCO, JANTETELCO MORELOS, C.P. 62970";
            rfc = "R.F.C    AIC171129UAA";
        }
        boolean exito2=false;
        if (seleccionar.showDialog(null, "Exportador Excel") == JFileChooser.APPROVE_OPTION) {
            archivo = seleccionar.getSelectedFile();

            //nuevo archivo
            book = new XSSFWorkbook();
            //hoja de trabajo
            Sheet hoja = book.createSheet(tituloPestaniaHoja + " " + periodo.toUpperCase() + " " + anio);
            //Titulos de tablas combinadas
            /*Titulos y subtitulos de cabecezas*/
            CellStyle tituloEstilo = book.createCellStyle();
            CellStyle subTitulos = book.createCellStyle();
            CellStyle subTitulosDos = book.createCellStyle();
            CellStyle sbt3 = book.createCellStyle();
            CellStyle sbt4 = book.createCellStyle();

            //Definiendo alineamiento de los titulos y subtitulos
            tituloEstilo.setAlignment(HorizontalAlignment.CENTER);
            tituloEstilo.setVerticalAlignment(VerticalAlignment.CENTER);

            subTitulos.setAlignment(HorizontalAlignment.CENTER);
            subTitulos.setVerticalAlignment(VerticalAlignment.CENTER);

            subTitulosDos.setAlignment(HorizontalAlignment.CENTER);
            subTitulosDos.setVerticalAlignment(VerticalAlignment.CENTER);

            sbt3.setAlignment(HorizontalAlignment.LEFT);
            sbt3.setVerticalAlignment(VerticalAlignment.CENTER);

            sbt4.setAlignment(HorizontalAlignment.LEFT);
            sbt4.setVerticalAlignment(VerticalAlignment.CENTER);

            /*
            Importar font de Poi
            Propiedades de la fuente
             */
            Font fuenteTitulo = (Font) book.createFont();
            fuenteTitulo.setFontName("Arial");
            fuenteTitulo.setBold(true);
            fuenteTitulo.setColor(IndexedColors.BLACK.getIndex());
            fuenteTitulo.setFontHeightInPoints((short) 19);

            Font fuenteSubtitulo = (Font) book.createFont();
            fuenteSubtitulo.setFontName("Arial");
            fuenteSubtitulo.setBold(true);
            fuenteSubtitulo.setColor(IndexedColors.BLACK.getIndex());
            fuenteSubtitulo.setFontHeightInPoints((short) 16);

            Font fuenteSubtituloDos = (Font) book.createFont();
            fuenteSubtituloDos.setFontName("Arial");
            fuenteSubtituloDos.setBold(true);
            fuenteSubtituloDos.setColor(IndexedColors.BLACK.getIndex());
            fuenteSubtituloDos.setFontHeightInPoints((short) 13);

            //editando el titulo
            tituloEstilo.setFont(fuenteTitulo);
            //Crea fila en hoja
            Row filaTitulo = hoja.createRow(1);
            //Empieza a dibujar desde x celda
            Cell celdaTitulo = filaTitulo.createCell(2);
            celdaTitulo.setCellStyle(tituloEstilo);
            //Titulo de la cabecera en Hoja de trabajo

            celdaTitulo.setCellValue(nombreEmpresa);

            //(int firstRow, int lastRow, int firstCol, int lastCol)
            //alcance del conbinado de celdas
            hoja.addMergedRegion(new CellRangeAddress(1, 1, 2, 10));

            subTitulos.setFont(fuenteSubtitulo);
            Row filaSubtitulo = hoja.createRow(2);
            Cell celdaSubTitulo = filaSubtitulo.createCell(2);
            celdaSubTitulo.setCellStyle(subTitulos);
            celdaSubTitulo.setCellValue(rfc);
            hoja.addMergedRegion(new CellRangeAddress(2, 2, 2, 10));

            subTitulosDos.setFont(fuenteSubtituloDos);
            Row filaSubtituloDos = hoja.createRow(3);
            Cell celdaSubTituloDos = filaSubtituloDos.createCell(2);
            celdaSubTituloDos.setCellStyle(subTitulosDos);
            //Privada 12 Oriente No. 107
            celdaSubTituloDos.setCellValue(direccion);
            hoja.addMergedRegion(new CellRangeAddress(3, 3, 2, 10));

            sbt3.setFont(fuenteSubtituloDos);
            Row fs3 = hoja.createRow(5);
            Cell cs3 = fs3.createCell(1);
            cs3.setCellStyle(sbt3);
            cs3.setCellValue("OTROS DEPOSITOS");
            hoja.addMergedRegion(new CellRangeAddress(5, 5, 1, 4));

            sbt4.setFont(fuenteSubtituloDos);
            Row fs4 = hoja.createRow(6);
            Cell cs4 = fs4.createCell(1);
            cs4.setCellStyle(sbt4);
            cs4.setCellValue(periodo.toUpperCase() + " " + anio);
            hoja.addMergedRegion(new CellRangeAddress(6, 6, 1, 4));

            String[] cabecera = new String[]{"", "Tipo Deposito", "RFC", "Nombre cliente o Tercero", "Concepto", "Factura", "Fecha de Factura", "UUID", "Base 0%", "Base 16%",
                "IVA", "Total", "Fecha del Deposito", "Cuenta de Banco", "Numero de Documento", "Total Cobrado", "Cruce Edo. Cuenta"};

            //Bordes de las celdas
            CellStyle celdasCabeceraTabla = book.createCellStyle();

            celdasCabeceraTabla.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
            celdasCabeceraTabla.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            celdasCabeceraTabla.setBorderBottom(BorderStyle.THIN);
            celdasCabeceraTabla.setBorderLeft(BorderStyle.THIN);
            celdasCabeceraTabla.setBorderRight(BorderStyle.THIN);
            celdasCabeceraTabla.setBorderTop(BorderStyle.THIN);
            celdasCabeceraTabla.setAlignment(HorizontalAlignment.CENTER);
            celdasCabeceraTabla.setVerticalAlignment(VerticalAlignment.CENTER);

            Font font = book.createFont();
            font.setFontName("Arial");
            font.setBold(true);
            font.setColor(IndexedColors.WHITE.getIndex());
            font.setFontHeightInPoints((short) 12);
            celdasCabeceraTabla.setFont(font);
//            //CABECERAS
            Row filaEncabezado_1 = hoja.createRow(7);

            for (int i = 1; i < cabecera.length; i++) {

                Cell celdaEncabezado = filaEncabezado_1.createCell(i);
                celdaEncabezado.setCellStyle(celdasCabeceraTabla);
                celdaEncabezado.setCellValue(cabecera[i]);
            }

            //INICIA PARTE DATOS
            numFilasTabla = tablaCienPorciento.getRowCount();
            numColumnasTabla = (tablaCienPorciento.getColumnCount()) - 4;
            //No.filas de cabecera+inicio de datos+1
            ultimaFilaRegistros = (tablaCienPorciento.getRowCount()) + 10;
            empezarLlenadoDesdeFila = 8;

            CellStyle datosEstilo = book.createCellStyle();
            //bordes de la tabla

            datosEstilo.setBorderBottom(BorderStyle.THIN);
            datosEstilo.setBorderLeft(BorderStyle.THIN);
            datosEstilo.setBorderRight(BorderStyle.THIN);
            datosEstilo.setBorderTop(BorderStyle.THIN);

            for (int i = 0; i < numFilasTabla; i++) {
                //?
                Row fila = hoja.createRow(i + empezarLlenadoDesdeFila);
                //Obteniendo información de las columnas

                for (int a = 1; a < numColumnasTabla; a++) {
                    Cell celda;
                    celda = fila.createCell(a);
                    celda.setCellStyle(datosEstilo);
                    if (tablaCienPorciento.getValueAt(i, a) == null) {
                        celda.setCellValue("");
                    } else {
                        if (a < 16) {
                            celda.setCellValue(String.valueOf(tablaCienPorciento.getValueAt(i, a)));
                        }

                    }
                }

            }
            //FIN PARTE DATOS
            //DATOS TOTAL
            CellStyle txtT1 = book.createCellStyle();
            txtT1.setAlignment(HorizontalAlignment.CENTER);

            Font fontTotal = (Font) book.createFont();
            fontTotal.setFontName("Arial");
            fontTotal.setBold(true);
            fontTotal.setColor(IndexedColors.BLACK.getIndex());
            fontTotal.setFontHeightInPoints((short) 12);
            txtT1.setFont(fontTotal);

            CellStyle txtT2 = book.createCellStyle();
            txtT1.setAlignment(HorizontalAlignment.CENTER);

            Font cabeceraTotales = (Font) book.createFont();
            cabeceraTotales.setFontName("Arial");
            cabeceraTotales.setBold(true);
            cabeceraTotales.setColor(IndexedColors.BLACK.getIndex());
            cabeceraTotales.setFontHeightInPoints((short) 10);
            txtT2.setFont(cabeceraTotales);

            Row stx = hoja.createRow(ultimaFilaRegistros + 1);
            Cell st_1 = stx.createCell(7);
            st_1.setCellStyle(txtT2);
            st_1.setCellValue("Total Base 0%");

            Cell st_2 = stx.createCell(8);
            st_2.setCellStyle(txtT2);
            st_2.setCellValue("Total Base 16%");

            Cell st_3 = stx.createCell(9);
            st_3.setCellStyle(txtT2);
            st_3.setCellValue("Total Retención 4%");

//            Cell st_4 = stx.createCell(10);
//            st_4.setCellStyle(txtT2);
//            st_4.setCellValue("Total Retención 10%");
//
//            Cell st_5 = stx.createCell(11);
//            st_5.setCellStyle(txtT2);
//            st_5.setCellValue("Total Retención 10.67%");
//
//            Cell st_6 = stx.createCell(12);
//            st_6.setCellStyle(txtT2);
//            st_6.setCellValue("Total Cuota Compensatoria");
            Cell st_7 = stx.createCell(10);
            st_7.setCellStyle(txtT2);
            st_7.setCellValue("Total IVA");

            Cell st_8 = stx.createCell(11);
            st_8.setCellStyle(txtT2);
            st_8.setCellValue("Total Final");

            Row fila = hoja.createRow(ultimaFilaRegistros + 2);
            //Obteniendo información de las columnas

            for (int a = 0; a < tablaTotalCien.getColumnCount(); a++) {
                Cell celda;
                celda = fila.createCell((a) + 7);
                celda.setCellStyle(datosEstilo);
                celda.setCellValue(String.valueOf(tablaTotalCien.getValueAt(0, a)));
            }

            //FIN TOTALES
            for (int i = 0; i < 22; i++) {
                hoja.autoSizeColumn(i);
            }

            try {
                book.write(new FileOutputStream(archivo + ".xlsx"));
                exito2 = true;
                

            } catch (IOException ex) {
                
                Logger.getLogger(GeneradorExcel.class.getName()).log(Level.SEVERE, null, ex);
                
            }

        }
        return exito2;
    }

    /**
     * Funcion que genera un archivo excel para la vista la seccion de auxiliar
     * iva acred
     *
     * @param tableAuxIvaAcred1
     * @param table_AuxIvaAcredTotal
     * @param tituloPestaniaHoja
     * @param periodo
     * @param anio
     * @param empresa
     */
    public void generarExcelAuxiliarIvaAcred(JTable tableAuxIvaAcred1, JTable table_AuxIvaAcredTotal, String tituloPestaniaHoja, String periodo, String anio, String empresa) {

        seleccionar = new JFileChooser();
        File archivo;
        int valorEmpresa = Integer.parseInt(empresa);
        if (valorEmpresa == 1) {
            nombreEmpresa = "ASOCIACIÓN DE SORGEROS DE TEHUACAN TIERRA DE DIOSES, S.A. DE C.V.";
            direccion = "PRIVADA 12 ORIENTE NO. 107, FRANCISCO SARABIA, TEHUACAN, PUEBLA";
            rfc = "";

        } else if (valorEmpresa == 2) {
            nombreEmpresa = "AGROECOLOGIA INTENSIVA PARA EL CAMPO S.A. DE C.V.";
            direccion = "CARRETERA MEXICO OAXACA KM 97, JANTETELCO, JANTETELCO MORELOS, C.P. 62970";
            rfc = "R.F.C    AIC171129UAA";
        }
        if (seleccionar.showDialog(null, "Exportar Excel") == JFileChooser.APPROVE_OPTION) {
            archivo = seleccionar.getSelectedFile();

            //nuevo archivo
            book = new XSSFWorkbook();
            //hoja de trabajo
            Sheet hoja = book.createSheet(tituloPestaniaHoja + " " + periodo.toUpperCase() + " " + anio);
            //Titulos de tablas combinadas
            /*Titulos y subtitulos de cabecezas*/
            CellStyle tituloEstilo = book.createCellStyle();
            CellStyle subTitulos = book.createCellStyle();
            CellStyle subTitulosDos = book.createCellStyle();
            CellStyle sbt3 = book.createCellStyle();
            CellStyle sbt4 = book.createCellStyle();

            //Definiendo alineamiento de los titulos y subtitulos
            tituloEstilo.setAlignment(HorizontalAlignment.CENTER);
            tituloEstilo.setVerticalAlignment(VerticalAlignment.CENTER);

            subTitulos.setAlignment(HorizontalAlignment.CENTER);
            subTitulos.setVerticalAlignment(VerticalAlignment.CENTER);

            subTitulosDos.setAlignment(HorizontalAlignment.CENTER);
            subTitulosDos.setVerticalAlignment(VerticalAlignment.CENTER);

            sbt3.setAlignment(HorizontalAlignment.LEFT);
            sbt3.setVerticalAlignment(VerticalAlignment.CENTER);

            sbt4.setAlignment(HorizontalAlignment.LEFT);
            sbt4.setVerticalAlignment(VerticalAlignment.CENTER);

            /*
            Importar font de Poi
            Propiedades de la fuente
             */
            Font fuenteTitulo = (Font) book.createFont();
            fuenteTitulo.setFontName("Arial");
            fuenteTitulo.setBold(true);
            fuenteTitulo.setColor(IndexedColors.BLACK.getIndex());
            fuenteTitulo.setFontHeightInPoints((short) 19);

            Font fuenteSubtitulo = (Font) book.createFont();
            fuenteSubtitulo.setFontName("Arial");
            fuenteSubtitulo.setBold(true);
            fuenteSubtitulo.setColor(IndexedColors.BLACK.getIndex());
            fuenteSubtitulo.setFontHeightInPoints((short) 16);

            Font fuenteSubtituloDos = (Font) book.createFont();
            fuenteSubtituloDos.setFontName("Arial");
            fuenteSubtituloDos.setBold(true);
            fuenteSubtituloDos.setColor(IndexedColors.BLACK.getIndex());
            fuenteSubtituloDos.setFontHeightInPoints((short) 13);

            //editando el titulo
            tituloEstilo.setFont(fuenteTitulo);
            //Crea fila en hoja
            Row filaTitulo = hoja.createRow(1);
            //Empieza a dibujar desde x celda
            Cell celdaTitulo = filaTitulo.createCell(2);
            celdaTitulo.setCellStyle(tituloEstilo);
            //Titulo de la cabecera en Hoja de trabajo
            celdaTitulo.setCellValue(nombreEmpresa);

            //(int firstRow, int lastRow, int firstCol, int lastCol)
            //alcance del conbinado de celdas
            hoja.addMergedRegion(new CellRangeAddress(1, 1, 2, 10));

            subTitulos.setFont(fuenteSubtitulo);
            Row filaSubtitulo = hoja.createRow(2);
            Cell celdaSubTitulo = filaSubtitulo.createCell(2);
            celdaSubTitulo.setCellStyle(subTitulos);
            celdaSubTitulo.setCellValue(rfc);
            hoja.addMergedRegion(new CellRangeAddress(2, 2, 2, 10));

            subTitulosDos.setFont(fuenteSubtituloDos);
            Row filaSubtituloDos = hoja.createRow(3);
            Cell celdaSubTituloDos = filaSubtituloDos.createCell(2);
            celdaSubTituloDos.setCellStyle(subTitulosDos);
            celdaSubTituloDos.setCellValue(direccion);
            hoja.addMergedRegion(new CellRangeAddress(3, 3, 2, 10));

            sbt3.setFont(fuenteSubtituloDos);
            Row fs3 = hoja.createRow(5);
            Cell cs3 = fs3.createCell(0);
            cs3.setCellStyle(sbt3);
            cs3.setCellValue("RELACIÓN DEL 100% DE IVA ACREDITABLE");
            hoja.addMergedRegion(new CellRangeAddress(5, 5, 0, 5));

            sbt4.setFont(fuenteSubtituloDos);
            Row fs4 = hoja.createRow(6);
            Cell cs4 = fs4.createCell(0);
            cs4.setCellStyle(sbt4);
            cs4.setCellValue("IVA ACREDITABLE: " + periodo.toUpperCase() + " " + anio);
            hoja.addMergedRegion(new CellRangeAddress(6, 6, 0, 5));

            String[] cabecera = new String[]{"Tipo Póliza", "Numero Póliza", "Fecha", "Concepto", "Debe", "Haber"};

            //Bordes de las celdas
            CellStyle headerStyle = book.createCellStyle();

            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);
            headerStyle.setBorderTop(BorderStyle.THIN);

            CellStyle rojo = book.createCellStyle();

            rojo.setFillForegroundColor(IndexedColors.DARK_RED.getIndex());
            rojo.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            rojo.setBorderBottom(BorderStyle.THIN);
            rojo.setBorderLeft(BorderStyle.THIN);
            rojo.setBorderRight(BorderStyle.THIN);
            rojo.setBorderTop(BorderStyle.THIN);
            rojo.setAlignment(HorizontalAlignment.CENTER);
            rojo.setVerticalAlignment(VerticalAlignment.CENTER);

            //aqua
            CellStyle aqua = book.createCellStyle();
            aqua.setFillForegroundColor(IndexedColors.TEAL.getIndex());
            aqua.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            aqua.setBorderBottom(BorderStyle.THIN);
            aqua.setBorderLeft(BorderStyle.THIN);
            aqua.setBorderRight(BorderStyle.THIN);
            aqua.setBorderTop(BorderStyle.THIN);
            aqua.setAlignment(HorizontalAlignment.CENTER);
            aqua.setVerticalAlignment(VerticalAlignment.CENTER);

            //aqua2
            CellStyle aqua2 = book.createCellStyle();
            aqua2.setFillForegroundColor(IndexedColors.TEAL.getIndex());
            aqua2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            aqua2.setBorderBottom(BorderStyle.THIN);
            aqua2.setBorderTop(BorderStyle.THIN);
            aqua2.setAlignment(HorizontalAlignment.CENTER);
            aqua2.setVerticalAlignment(VerticalAlignment.CENTER);

            //partidas1
            CellStyle partidas1 = book.createCellStyle();
            partidas1.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.getIndex());
            partidas1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            partidas1.setBorderBottom(BorderStyle.THIN);
            partidas1.setBorderLeft(BorderStyle.THIN);
            partidas1.setBorderTop(BorderStyle.THIN);
            partidas1.setVerticalAlignment(VerticalAlignment.CENTER);

            //partidas3
            CellStyle partidas3 = book.createCellStyle();
            partidas3.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.getIndex());
            partidas3.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            partidas3.setBorderBottom(BorderStyle.THIN);
            partidas3.setBorderTop(BorderStyle.THIN);
            partidas3.setVerticalAlignment(VerticalAlignment.CENTER);

            //partidas2
            CellStyle partidas2 = book.createCellStyle();
            partidas2.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.getIndex());
            partidas2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            partidas2.setBorderBottom(BorderStyle.THIN);
            partidas2.setBorderRight(BorderStyle.THIN);
            partidas2.setBorderTop(BorderStyle.THIN);
            partidas2.setVerticalAlignment(VerticalAlignment.CENTER);

            Font font = book.createFont();
            font.setFontName("Arial");
            font.setBold(true);
            font.setColor(IndexedColors.WHITE.getIndex());
            font.setFontHeightInPoints((short) 12);
            headerStyle.setFont(font);

            //autosize
            Row filaEncabezado = hoja.createRow(7);
            for (int i = 0; i < cabecera.length; i++) {
                Cell celdaEncabezado = filaEncabezado.createCell(i);
                celdaEncabezado.setCellStyle(headerStyle);
                celdaEncabezado.setCellValue(cabecera[i]);

            }
            /*EMPIEZA PARTE DE LOS REGISTROS*/
            numFilasTabla = tableAuxIvaAcred1.getRowCount();
            numColumnasTabla = tableAuxIvaAcred1.getColumnCount();
            //No.filas de cabecera+inicio de datos+1
            ultimaFilaRegistros = (tableAuxIvaAcred1.getRowCount()) + 9;

            //Inicio de fila para empezar a dibujar los registros
            empezarLlenadoDesdeFila = 8;
            CellStyle datosEstilo = book.createCellStyle();
            //bordes de la tabla

            datosEstilo.setBorderBottom(BorderStyle.THIN);
            datosEstilo.setBorderLeft(BorderStyle.THIN);
            datosEstilo.setBorderRight(BorderStyle.THIN);
            datosEstilo.setBorderTop(BorderStyle.THIN);

            for (int i = 0; i < numFilasTabla; i++) {
                //?
                Row fila = hoja.createRow(i + empezarLlenadoDesdeFila);
                //Obteniendo información de las columnas
                for (int j = 0; j < numColumnasTabla; j++) {
                    Cell celda = fila.createCell(j);
                    celda.setCellStyle(datosEstilo);
                    celda.setCellValue(String.valueOf(tableAuxIvaAcred1.getValueAt(i, j)));
                }
            }

            //FORMATO Y DATOS PARA DE TOTALES
            CellStyle txtT1 = book.createCellStyle();
            txtT1.setAlignment(HorizontalAlignment.LEFT);

            Font fontTotal = (Font) book.createFont();
            fontTotal.setFontName("Arial");
            fontTotal.setBold(true);
            fontTotal.setColor(IndexedColors.BLACK.getIndex());
            fontTotal.setFontHeightInPoints((short) 12);
            txtT1.setFont(fontTotal);

            CellStyle txtT2 = book.createCellStyle();
            txtT2.setAlignment(HorizontalAlignment.LEFT);

            Font fontValTotal = book.createFont();
            fontValTotal.setFontName("Arial");
            fontValTotal.setColor(IndexedColors.BLACK.getIndex());
            fontValTotal.setFontHeightInPoints((short) 12);
            txtT2.setFont(fontValTotal);

            //DATOS TABLA TOTALES
            Row filaTotal_1 = hoja.createRow(ultimaFilaRegistros);
            Cell ct_1 = filaTotal_1.createCell(3);
            ct_1.setCellStyle(txtT1);
            ct_1.setCellValue("TOTAL CARGOS DEL PERIODO");
            
            Cell ctVal_1 = filaTotal_1.createCell(4);
            ctVal_1.setCellStyle(txtT2);
            ctVal_1.setCellValue("$"+String.valueOf(table_AuxIvaAcredTotal.getValueAt(0, 2)));
            
              Row filaTotal_2 = hoja.createRow((ultimaFilaRegistros) + 1);
            Cell ct_2 = filaTotal_2.createCell(3);
            ct_2.setCellStyle(txtT1);
            ct_2.setCellValue("TOTAL DE ABONOS DEL PERIODO");

            Cell ctVal_2 = filaTotal_2.createCell(4);
            ctVal_2.setCellStyle(txtT2);
            ctVal_2.setCellValue("$"+String.valueOf(table_AuxIvaAcredTotal.getValueAt(1, 2)));
            
            for (int i = 0; i < 6; i++) {
                hoja.autoSizeColumn(i);
            }

            //Este codigo solo es para combinar celdas
            //hoja.addMergedRegion(new CellRangeAddress((ultimaFilaRegistros) + (1), (ultimaFilaRegistros) + (1), 3, 3));
            //Creando el archivo fisico
            try {
                book.write(new FileOutputStream(archivo + ".xlsx"));
                JOptionPane.showMessageDialog(null, "Exportacion Exitosa");
            } catch (IOException ex) {
                Logger.getLogger(GeneradorExcel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * * Funcion que genera un archivo excel para la vista Detalles de Iva
     * retenido del mes
     *
     * @param tableRIM
     * @param tituloPestaniaHoja
     * @param periodo
     * @param anio
     */
    public void generarExcelIvaRetenidoMesDetalle(JTable tableRIM, String tituloPestaniaHoja, String periodo, String anio) {
        seleccionar = new JFileChooser();
        File archivo;
        if (seleccionar.showDialog(null, "Exportar Excel") == JFileChooser.APPROVE_OPTION) {
            archivo = seleccionar.getSelectedFile();

            //nuevo archivo
            book = new XSSFWorkbook();
            //hoja de trabajo
            Sheet hoja = book.createSheet(tituloPestaniaHoja + " " + periodo.toUpperCase() + " " + anio);
            //Titulos de tablas combinadas
            /*Titulos y subtitulos de cabecezas*/
            CellStyle tituloEstilo = book.createCellStyle();
            tituloEstilo.setAlignment(HorizontalAlignment.CENTER);
            tituloEstilo.setVerticalAlignment(VerticalAlignment.CENTER);

            Font fuenteTitulo = (Font) book.createFont();
            fuenteTitulo.setFontName("Arial");
            fuenteTitulo.setBold(true);
            fuenteTitulo.setColor(IndexedColors.BLACK.getIndex());
            fuenteTitulo.setFontHeightInPoints((short) 19);
            tituloEstilo.setFont(fuenteTitulo);

            Row filaTitulo = hoja.createRow(1);
            //Empieza a dibujar desde x celda
            Cell celdaTitulo = filaTitulo.createCell(1);
            celdaTitulo.setCellStyle(tituloEstilo);
            //Titulo de la cabecera en Hoja de trabajo
            celdaTitulo.setCellValue("DETALLE DE IVA RETENIDO DEL MES DE " + periodo.toUpperCase() + " " + anio);

            hoja.addMergedRegion(new CellRangeAddress(1, 1, 1, 6));

            String[] cabecera = new String[]{"", "", "FECHA", "CONCEPTO O TEXTO", "RFC DEL PROVEEDOR", "CONCEPTO DEL GASTO", "SUBTOTAL TASA 16% de IVA", "IVA ACREDITABLE TAZA 16%", "OTROS CONCEPTOS BASE",
                "IMPORTE RETENIDO", "TOTAL PAGADO", "FACTURA"};

            CellStyle headerStyle = book.createCellStyle();

            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);
            headerStyle.setBorderTop(BorderStyle.THIN);

            Font font = book.createFont();
            font.setFontName("Calibri");
            font.setBold(true);
            font.setColor(IndexedColors.BLACK.getIndex());
            font.setFontHeightInPoints((short) 12);
            headerStyle.setFont(font);

            Row cabeceraDetalles = hoja.createRow(3);
            //Empieza a dibujar desde x celda
            Cell celdaPoliza = cabeceraDetalles.createCell(0);
            celdaPoliza.setCellStyle(headerStyle);
            //Titulo de la cabecera en Hoja de trabajo
            celdaPoliza.setCellValue("POLIZA");
            hoja.addMergedRegion(new CellRangeAddress(3, 3, 0, 1));

            for (int i = 2; i < cabecera.length; i++) {
                Cell celdaEncabezado = cabeceraDetalles.createCell(i);
                celdaEncabezado.setCellStyle(headerStyle);
                celdaEncabezado.setCellValue(cabecera[i]);
            }

            /*EMPIEZA PARTE DE LOS REGISTROS*/
            numFilasTabla = tableRIM.getRowCount();
            numColumnasTabla = tableRIM.getColumnCount();
            //No.filas de cabecera+inicio de datos+1  (RIMP)
            ultimaFilaRegistros = (tableRIM.getRowCount()) + 9;
            //Desde donde empezar a poner los datos
            empezarLlenadoDesdeFila = 4;
            CellStyle datosEstilo = book.createCellStyle();
            //bordes de la tabla
            datosEstilo.setBorderBottom(BorderStyle.THIN);
            datosEstilo.setBorderLeft(BorderStyle.THIN);
            datosEstilo.setBorderRight(BorderStyle.THIN);
            datosEstilo.setBorderTop(BorderStyle.THIN);

            for (int i = 0; i < numFilasTabla; i++) {
                //?
                Row fila = hoja.createRow(i + empezarLlenadoDesdeFila);
                //Obteniendo información de las columnas
                for (int j = 0; j < numColumnasTabla; j++) {
                    Cell celda = fila.createCell(j);
                    celda.setCellStyle(datosEstilo);
                    celda.setCellValue(String.valueOf(tableRIM.getValueAt(i, j)));
                }
            }

            //Extras
            CellStyle Extra = book.createCellStyle();
            Extra.setAlignment(HorizontalAlignment.CENTER);

            Font fontExtra = (Font) book.createFont();
            fontExtra.setFontName("Arial");
            fontExtra.setBold(true);
            fontExtra.setColor(IndexedColors.BLACK.getIndex());
            fontExtra.setFontHeightInPoints((short) 12);
            Extra.setFont(fontExtra);

            Row extraRow = hoja.createRow((ultimaFilaRegistros) + 3);
            Cell ct_1 = extraRow.createCell(4);
            ct_1.setCellStyle(Extra);
            ct_1.setCellValue("\"BAJO PROTESTA DE DECIR LA VERDAD\"");

            Row extraRow2 = hoja.createRow((ultimaFilaRegistros) + 6);
            Cell ct_2 = extraRow2.createCell(4);
            ct_2.setCellStyle(Extra);
            ct_2.setCellValue("\"REPRESENTANTE LEGAL\"");

            for (int i = 0; i < 12; i++) {
                hoja.autoSizeColumn(i);
            }

            try {
                book.write(new FileOutputStream(archivo + ".xlsx"));
            } catch (IOException ex) {
                Logger.getLogger(GeneradorExcel.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    /**
     * Función que genera un archivo excel para la vista Detalles de iva
     * retenido y pagado del mes
     *
     * @param jtableIvaRetenidoMes
     * @param tituloPestaniaHoja
     * @param periodo
     * @param anio
     */
    public void generarExcelIvaRetenidoMesPagado(JTable jtableIvaRetenidoMes, String tituloPestaniaHoja, String periodo, String anio) {
        seleccionar = new JFileChooser();
        File archivo;
        if (seleccionar.showDialog(null, "Exportar Excel") == JFileChooser.APPROVE_OPTION) {
            archivo = seleccionar.getSelectedFile();

            //nuevo archivo
            book = new XSSFWorkbook();
            //hoja de trabajo
            Sheet hoja = book.createSheet(tituloPestaniaHoja + " " + periodo.toUpperCase() + " " + anio);
            //Titulos de tablas combinadas
            /*Titulos y subtitulos de cabecezas*/
            CellStyle tituloEstilo = book.createCellStyle();
            tituloEstilo.setAlignment(HorizontalAlignment.CENTER);
            tituloEstilo.setVerticalAlignment(VerticalAlignment.CENTER);

            Font fuenteTitulo = (Font) book.createFont();
            fuenteTitulo.setFontName("Arial");
            fuenteTitulo.setBold(true);
            fuenteTitulo.setColor(IndexedColors.BLACK.getIndex());
            fuenteTitulo.setFontHeightInPoints((short) 19);
            tituloEstilo.setFont(fuenteTitulo);

            Row filaTitulo = hoja.createRow(1);
            //Empieza a dibujar desde x celda
            Cell celdaTitulo = filaTitulo.createCell(1);
            celdaTitulo.setCellStyle(tituloEstilo);
            //Titulo de la cabecera en Hoja de trabajo
            celdaTitulo.setCellValue("DETALLE DE IVA RETENIDO DEL MES DE " + periodo.toUpperCase() + " " + anio);

            hoja.addMergedRegion(new CellRangeAddress(1, 1, 1, 6));

            String[] cabecera = new String[]{"", "", "FECHA", "CONCEPTO O TEXTO", "RFC DEL PROVEEDOR", "CONCEPTO DEL GASTO", "SUBTOTAL TASA 16% de IVA", "IVA ACREDITABLE TAZA 16%", "OTROS CONCEPTOS BASE",
                "IMPORTE", "TOTAL PAGADO", "FACTURA"};

            CellStyle headerStyle = book.createCellStyle();

            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);
            headerStyle.setBorderTop(BorderStyle.THIN);

            Font font = book.createFont();
            font.setFontName("Calibri");
            font.setBold(true);
            font.setColor(IndexedColors.BLACK.getIndex());
            font.setFontHeightInPoints((short) 12);
            headerStyle.setFont(font);

            Row cabeceraDetalles = hoja.createRow(3);
            //Empieza a dibujar desde x celda
            Cell celdaPoliza = cabeceraDetalles.createCell(0);
            celdaPoliza.setCellStyle(headerStyle);
            //Titulo de la cabecera en Hoja de trabajo
            celdaPoliza.setCellValue("POLIZA");
            hoja.addMergedRegion(new CellRangeAddress(3, 3, 0, 1));

            for (int i = 2; i < cabecera.length; i++) {
                Cell celdaEncabezado = cabeceraDetalles.createCell(i);
                celdaEncabezado.setCellStyle(headerStyle);
                celdaEncabezado.setCellValue(cabecera[i]);
            }

            /*EMPIEZA PARTE DE LOS REGISTROS*/
            numFilasTabla = jtableIvaRetenidoMes.getRowCount();
            numColumnasTabla = jtableIvaRetenidoMes.getColumnCount();
            //No.filas de cabecera+inicio de datos+1  (RIMP)
            ultimaFilaRegistros = (jtableIvaRetenidoMes.getRowCount()) + 9;
            //Desde donde empezar a poner los datos
            empezarLlenadoDesdeFila = 4;
            CellStyle datosEstilo = book.createCellStyle();
            //bordes de la tabla
            datosEstilo.setBorderBottom(BorderStyle.THIN);
            datosEstilo.setBorderLeft(BorderStyle.THIN);
            datosEstilo.setBorderRight(BorderStyle.THIN);
            datosEstilo.setBorderTop(BorderStyle.THIN);

            for (int i = 0; i < numFilasTabla; i++) {
                //?
                Row fila = hoja.createRow(i + empezarLlenadoDesdeFila);
                //Obteniendo información de las columnas
                for (int j = 0; j < numColumnasTabla; j++) {
                    Cell celda = fila.createCell(j);
                    celda.setCellStyle(datosEstilo);
                    celda.setCellValue(String.valueOf(jtableIvaRetenidoMes.getValueAt(i, j)));
                }
            }

            //Extras
            CellStyle Extra = book.createCellStyle();
            Extra.setAlignment(HorizontalAlignment.CENTER);

            Font fontExtra = (Font) book.createFont();
            fontExtra.setFontName("Arial");
            fontExtra.setBold(true);
            fontExtra.setColor(IndexedColors.BLACK.getIndex());
            fontExtra.setFontHeightInPoints((short) 12);
            Extra.setFont(fontExtra);

            Row extraRow = hoja.createRow((ultimaFilaRegistros) + 3);
            Cell ct_1 = extraRow.createCell(4);
            ct_1.setCellStyle(Extra);
            ct_1.setCellValue("\"BAJO PROTESTA DE DECIR LA VERDAD\"");

            Row extraRow2 = hoja.createRow((ultimaFilaRegistros) + 6);
            Cell ct_2 = extraRow2.createCell(4);
            ct_2.setCellStyle(Extra);
            ct_2.setCellValue("\"REPRESENTANTE LEGAL\"");

            for (int i = 0; i < 12; i++) {
                hoja.autoSizeColumn(i);
            }

            try {
                book.write(new FileOutputStream(archivo + ".xlsx"));
            } catch (IOException ex) {
                Logger.getLogger(GeneradorExcel.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public void generarSoloExcelActCero(JTable tablaCienPorciento, JTable tablaTotalCien, String tituloPestaniaHoja, String periodo, String anio, String numEmpresa) {
        seleccionar = new JFileChooser();
        File archivo;
        int valorEmpresa = Integer.parseInt(numEmpresa);
        if (valorEmpresa == 1) {
            nombreEmpresa = "ASOCIACIÓN DE SORGEROS DE TEHUACAN TIERRA DE DIOSES, S.A. DE C.V.";
            direccion = "PRIVADA 12 ORIENTE NO. 107, FRANCISCO SARABIA, TEHUACAN, PUEBLA";
            rfc = "";

        } else if (valorEmpresa == 2) {
            nombreEmpresa = "AGROECOLOGIA INTENSIVA PARA EL CAMPO S.A. DE C.V.";
            direccion = "CARRETERA MEXICO OAXACA KM 97, JANTETELCO, JANTETELCO MORELOS, C.P. 62970";
            rfc = "R.F.C    AIC171129UAA";
        }
        if (seleccionar.showDialog(null, "Exportador Excel") == JFileChooser.APPROVE_OPTION) {
            archivo = seleccionar.getSelectedFile();

            //nuevo archivo
            book = new XSSFWorkbook();
            //hoja de trabajo
            Sheet hoja = book.createSheet(tituloPestaniaHoja + " " + periodo.toUpperCase() + " " + anio);
            //Titulos de tablas combinadas
            /*Titulos y subtitulos de cabecezas*/
            CellStyle tituloEstilo = book.createCellStyle();
            CellStyle subTitulos = book.createCellStyle();
            CellStyle subTitulosDos = book.createCellStyle();
            CellStyle sbt3 = book.createCellStyle();
            CellStyle sbt4 = book.createCellStyle();

            //Definiendo alineamiento de los titulos y subtitulos
            tituloEstilo.setAlignment(HorizontalAlignment.CENTER);
            tituloEstilo.setVerticalAlignment(VerticalAlignment.CENTER);

            subTitulos.setAlignment(HorizontalAlignment.CENTER);
            subTitulos.setVerticalAlignment(VerticalAlignment.CENTER);

            subTitulosDos.setAlignment(HorizontalAlignment.CENTER);
            subTitulosDos.setVerticalAlignment(VerticalAlignment.CENTER);

            sbt3.setAlignment(HorizontalAlignment.LEFT);
            sbt3.setVerticalAlignment(VerticalAlignment.CENTER);

            sbt4.setAlignment(HorizontalAlignment.LEFT);
            sbt4.setVerticalAlignment(VerticalAlignment.CENTER);

            /*
            Importar font de Poi
            Propiedades de la fuente
             */
            Font fuenteTitulo = (Font) book.createFont();
            fuenteTitulo.setFontName("Arial");
            fuenteTitulo.setBold(true);
            fuenteTitulo.setColor(IndexedColors.BLACK.getIndex());
            fuenteTitulo.setFontHeightInPoints((short) 19);

            Font fuenteSubtitulo = (Font) book.createFont();
            fuenteSubtitulo.setFontName("Arial");
            fuenteSubtitulo.setBold(true);
            fuenteSubtitulo.setColor(IndexedColors.BLACK.getIndex());
            fuenteSubtitulo.setFontHeightInPoints((short) 16);

            Font fuenteSubtituloDos = (Font) book.createFont();
            fuenteSubtituloDos.setFontName("Arial");
            fuenteSubtituloDos.setBold(true);
            fuenteSubtituloDos.setColor(IndexedColors.BLACK.getIndex());
            fuenteSubtituloDos.setFontHeightInPoints((short) 13);

            //editando el titulo
            tituloEstilo.setFont(fuenteTitulo);
            //Crea fila en hoja
            Row filaTitulo = hoja.createRow(1);
            //Empieza a dibujar desde x celda
            Cell celdaTitulo = filaTitulo.createCell(2);
            celdaTitulo.setCellStyle(tituloEstilo);
            //Titulo de la cabecera en Hoja de trabajo
            celdaTitulo.setCellValue(nombreEmpresa);

            //(int firstRow, int lastRow, int firstCol, int lastCol)
            //alcance del conbinado de celdas
            hoja.addMergedRegion(new CellRangeAddress(1, 1, 2, 10));

            subTitulos.setFont(fuenteSubtitulo);
            Row filaSubtitulo = hoja.createRow(2);
            Cell celdaSubTitulo = filaSubtitulo.createCell(2);
            celdaSubTitulo.setCellStyle(subTitulos);
            celdaSubTitulo.setCellValue(rfc);
            hoja.addMergedRegion(new CellRangeAddress(2, 2, 2, 10));

            subTitulosDos.setFont(fuenteSubtituloDos);
            Row filaSubtituloDos = hoja.createRow(3);
            Cell celdaSubTituloDos = filaSubtituloDos.createCell(2);
            celdaSubTituloDos.setCellStyle(subTitulosDos);
            celdaSubTituloDos.setCellValue(direccion);
            hoja.addMergedRegion(new CellRangeAddress(3, 3, 2, 10));

            sbt3.setFont(fuenteSubtituloDos);
            Row fs3 = hoja.createRow(5);
            Cell cs3 = fs3.createCell(1);
            cs3.setCellStyle(sbt3);
            cs3.setCellValue(" INTEGRACION DE LOS ACTOS O ACTIVIDADES GRAVADOS A TASA 0% ");
            hoja.addMergedRegion(new CellRangeAddress(5, 5, 1, 8));

            sbt4.setFont(fuenteSubtituloDos);
            Row fs4 = hoja.createRow(6);
            Cell cs4 = fs4.createCell(1);
            cs4.setCellStyle(sbt4);
            cs4.setCellValue(" " + periodo.toUpperCase() + " " + anio);
            hoja.addMergedRegion(new CellRangeAddress(6, 6, 1, 4));

            String[] cabecera = new String[]{"Num.Factura", "Fecha Factura", "UUID", "Cliente", "RFC", "Concepto", "Base 0%", "Total Cobrado", "Documento de Cobro", "Fecha de cobro",
                "Cuenta De Banco", "Forma de Cobro", "Cruce Bancario"};

            //Bordes de las celdas
            CellStyle celdasCabeceraTabla = book.createCellStyle();

            celdasCabeceraTabla.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
            celdasCabeceraTabla.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            celdasCabeceraTabla.setBorderBottom(BorderStyle.THIN);
            celdasCabeceraTabla.setBorderLeft(BorderStyle.THIN);
            celdasCabeceraTabla.setBorderRight(BorderStyle.THIN);
            celdasCabeceraTabla.setBorderTop(BorderStyle.THIN);
            celdasCabeceraTabla.setAlignment(HorizontalAlignment.CENTER);
            celdasCabeceraTabla.setVerticalAlignment(VerticalAlignment.CENTER);

            Font font = book.createFont();
            font.setFontName("Arial");
            font.setBold(true);
            font.setColor(IndexedColors.WHITE.getIndex());
            font.setFontHeightInPoints((short) 12);
            celdasCabeceraTabla.setFont(font);
//            //CABECERAS
            Row filaEncabezado_1 = hoja.createRow(7);

            for (int i = 0; i < cabecera.length; i++) {

                Cell celdaEncabezado = filaEncabezado_1.createCell(i);
                celdaEncabezado.setCellStyle(celdasCabeceraTabla);
                celdaEncabezado.setCellValue(cabecera[i]);
            }

            //INICIA PARTE DATOS
            numFilasTabla = tablaCienPorciento.getRowCount();
            numColumnasTabla = (tablaCienPorciento.getColumnCount());
            //No.filas de cabecera+inicio de datos+1
            ultimaFilaRegistros = (tablaCienPorciento.getRowCount()) + 10;
            empezarLlenadoDesdeFila = 8;

            CellStyle datosEstilo = book.createCellStyle();
            //bordes de la tabla

            datosEstilo.setBorderBottom(BorderStyle.THIN);
            datosEstilo.setBorderLeft(BorderStyle.THIN);
            datosEstilo.setBorderRight(BorderStyle.THIN);
            datosEstilo.setBorderTop(BorderStyle.THIN);

            for (int i = 0; i < numFilasTabla; i++) {
                //?
                Row fila = hoja.createRow(i + empezarLlenadoDesdeFila);
                //Obteniendo información de las columnas

                for (int a = 0; a < numColumnasTabla; a++) {
                    Cell celda;
                    celda = fila.createCell(a);
                    celda.setCellStyle(datosEstilo);
                    if (tablaCienPorciento.getValueAt(i, a) == null) {
                        celda.setCellValue("");
                    } else {
                        celda.setCellValue(String.valueOf(tablaCienPorciento.getValueAt(i, a)));
                    }
                }

            }
            //FIN PARTE DATOS
            //DATOS TOTAL
            CellStyle txtT1 = book.createCellStyle();
            txtT1.setAlignment(HorizontalAlignment.CENTER);

            Font fontTotal = (Font) book.createFont();
            fontTotal.setFontName("Arial");
            fontTotal.setBold(true);
            fontTotal.setColor(IndexedColors.BLACK.getIndex());
            fontTotal.setFontHeightInPoints((short) 12);
            txtT1.setFont(fontTotal);

            CellStyle txtT2 = book.createCellStyle();
            txtT1.setAlignment(HorizontalAlignment.CENTER);

            Font cabeceraTotales = (Font) book.createFont();
            cabeceraTotales.setFontName("Arial");
            cabeceraTotales.setBold(true);
            cabeceraTotales.setColor(IndexedColors.BLACK.getIndex());
            cabeceraTotales.setFontHeightInPoints((short) 10);
            txtT2.setFont(cabeceraTotales);

            Row stx = hoja.createRow(ultimaFilaRegistros + 1);
            Cell st_1 = stx.createCell(7);
            st_1.setCellStyle(txtT2);
            st_1.setCellValue("Total Base 0%");
//
//            Cell st_2 = stx.createCell(8);
//            st_2.setCellStyle(txtT2);
//            st_2.setCellValue("Total Base 16%");
//
//            Cell st_3 = stx.createCell(9);
//            st_3.setCellStyle(txtT2);
//            st_3.setCellValue("Total Retención 4%");

//            Cell st_4 = stx.createCell(10);
//            st_4.setCellStyle(txtT2);
//            st_4.setCellValue("Total Retención 10%");
//
//            Cell st_5 = stx.createCell(11);
//            st_5.setCellStyle(txtT2);
//            st_5.setCellValue("Total Retención 10.67%");
//
//            Cell st_6 = stx.createCell(12);
//            st_6.setCellStyle(txtT2);
//            st_6.setCellValue("Total Cuota Compensatoria");
//            Cell st_7 = stx.createCell(10);
//            st_7.setCellStyle(txtT2);
//            st_7.setCellValue("Total IVA");
            Cell st_8 = stx.createCell(8);
            st_8.setCellStyle(txtT2);
            st_8.setCellValue("Total Final");

            Row fila = hoja.createRow(ultimaFilaRegistros + 2);
            //Obteniendo información de las columnas

            for (int a = 0; a < tablaTotalCien.getColumnCount(); a++) {
                Cell celda;
                celda = fila.createCell((a) + 7);
                celda.setCellStyle(datosEstilo);
                celda.setCellValue(String.valueOf(tablaTotalCien.getValueAt(0, a)));
            }

            //FIN TOTALES
            for (int i = 0; i < 22; i++) {
                hoja.autoSizeColumn(i);
            }

            try {
                book.write(new FileOutputStream(archivo + ".xlsx"));
                JOptionPane.showMessageDialog(null, "Exportacion Exitosa");
            } catch (IOException ex) {
                Logger.getLogger(GeneradorExcel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void generarSoloExcelAct16(JTable tablaCienPorciento, JTable tablaTotalCien, String tituloPestaniaHoja, String periodo, String anio, String numEmpresa) {
        seleccionar = new JFileChooser();
        File archivo;
        int valorEmpresa = Integer.parseInt(numEmpresa);
        if (valorEmpresa == 1) {
            nombreEmpresa = "ASOCIACIÓN DE SORGEROS DE TEHUACAN TIERRA DE DIOSES, S.A. DE C.V.";
            direccion = "PRIVADA 12 ORIENTE NO. 107, FRANCISCO SARABIA, TEHUACAN, PUEBLA";
            rfc = "";

        } else if (valorEmpresa == 2) {
            nombreEmpresa = "AGROECOLOGIA INTENSIVA PARA EL CAMPO S.A. DE C.V.";
            direccion = "CARRETERA MEXICO OAXACA KM 97, JANTETELCO, JANTETELCO MORELOS, C.P. 62970";
            rfc = "R.F.C    AIC171129UAA";
        }
        if (seleccionar.showDialog(null, "Exportador Excel") == JFileChooser.APPROVE_OPTION) {
            archivo = seleccionar.getSelectedFile();

            //nuevo archivo
            book = new XSSFWorkbook();
            //hoja de trabajo
            Sheet hoja = book.createSheet(tituloPestaniaHoja + " " + periodo.toUpperCase() + " " + anio);
            //Titulos de tablas combinadas
            /*Titulos y subtitulos de cabecezas*/
            CellStyle tituloEstilo = book.createCellStyle();
            CellStyle subTitulos = book.createCellStyle();
            CellStyle subTitulosDos = book.createCellStyle();
            CellStyle sbt3 = book.createCellStyle();
            CellStyle sbt4 = book.createCellStyle();

            //Definiendo alineamiento de los titulos y subtitulos
            tituloEstilo.setAlignment(HorizontalAlignment.CENTER);
            tituloEstilo.setVerticalAlignment(VerticalAlignment.CENTER);

            subTitulos.setAlignment(HorizontalAlignment.CENTER);
            subTitulos.setVerticalAlignment(VerticalAlignment.CENTER);

            subTitulosDos.setAlignment(HorizontalAlignment.CENTER);
            subTitulosDos.setVerticalAlignment(VerticalAlignment.CENTER);

            sbt3.setAlignment(HorizontalAlignment.LEFT);
            sbt3.setVerticalAlignment(VerticalAlignment.CENTER);

            sbt4.setAlignment(HorizontalAlignment.LEFT);
            sbt4.setVerticalAlignment(VerticalAlignment.CENTER);

            /*
            Importar font de Poi
            Propiedades de la fuente
             */
            Font fuenteTitulo = (Font) book.createFont();
            fuenteTitulo.setFontName("Arial");
            fuenteTitulo.setBold(true);
            fuenteTitulo.setColor(IndexedColors.BLACK.getIndex());
            fuenteTitulo.setFontHeightInPoints((short) 19);

            Font fuenteSubtitulo = (Font) book.createFont();
            fuenteSubtitulo.setFontName("Arial");
            fuenteSubtitulo.setBold(true);
            fuenteSubtitulo.setColor(IndexedColors.BLACK.getIndex());
            fuenteSubtitulo.setFontHeightInPoints((short) 16);

            Font fuenteSubtituloDos = (Font) book.createFont();
            fuenteSubtituloDos.setFontName("Arial");
            fuenteSubtituloDos.setBold(true);
            fuenteSubtituloDos.setColor(IndexedColors.BLACK.getIndex());
            fuenteSubtituloDos.setFontHeightInPoints((short) 13);

            //editando el titulo
            tituloEstilo.setFont(fuenteTitulo);
            //Crea fila en hoja
            Row filaTitulo = hoja.createRow(1);
            //Empieza a dibujar desde x celda
            Cell celdaTitulo = filaTitulo.createCell(2);
            celdaTitulo.setCellStyle(tituloEstilo);
            //Titulo de la cabecera en Hoja de trabajo
            celdaTitulo.setCellValue(nombreEmpresa);

            //(int firstRow, int lastRow, int firstCol, int lastCol)
            //alcance del conbinado de celdas
            hoja.addMergedRegion(new CellRangeAddress(1, 1, 2, 10));

            subTitulos.setFont(fuenteSubtitulo);
            Row filaSubtitulo = hoja.createRow(2);
            Cell celdaSubTitulo = filaSubtitulo.createCell(2);
            celdaSubTitulo.setCellStyle(subTitulos);
            celdaSubTitulo.setCellValue(rfc);
            hoja.addMergedRegion(new CellRangeAddress(2, 2, 2, 10));

            subTitulosDos.setFont(fuenteSubtituloDos);
            Row filaSubtituloDos = hoja.createRow(3);
            Cell celdaSubTituloDos = filaSubtituloDos.createCell(2);
            celdaSubTituloDos.setCellStyle(subTitulosDos);
            celdaSubTituloDos.setCellValue(direccion);
            hoja.addMergedRegion(new CellRangeAddress(3, 3, 2, 10));

            sbt3.setFont(fuenteSubtituloDos);
            Row fs3 = hoja.createRow(5);
            Cell cs3 = fs3.createCell(1);
            cs3.setCellStyle(sbt3);
            cs3.setCellValue("  INTEGRACION DE LOS ACTOS O ACTIVIDADES GRAVADOS A TASA 16%  ");
            hoja.addMergedRegion(new CellRangeAddress(5, 5, 1, 9));

            sbt4.setFont(fuenteSubtituloDos);
            Row fs4 = hoja.createRow(6);
            Cell cs4 = fs4.createCell(1);
            cs4.setCellStyle(sbt4);
            cs4.setCellValue(" " + periodo.toUpperCase() + " " + anio);
            hoja.addMergedRegion(new CellRangeAddress(6, 6, 1, 4));

            String[] cabecera = new String[]{"Num.Factura", "Fecha Factura", "UUID", "Cliente", "RFC", "Concepto", "Base 16%", "Iva causado", "Total", "Total Cobrado", "Documento de Cobro", "Fecha de cobro",
                "Cuenta De Banco", "Forma de Cobro", "Cruce Bancario"};

            //Bordes de las celdas
            CellStyle celdasCabeceraTabla = book.createCellStyle();

            celdasCabeceraTabla.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
            celdasCabeceraTabla.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            celdasCabeceraTabla.setBorderBottom(BorderStyle.THIN);
            celdasCabeceraTabla.setBorderLeft(BorderStyle.THIN);
            celdasCabeceraTabla.setBorderRight(BorderStyle.THIN);
            celdasCabeceraTabla.setBorderTop(BorderStyle.THIN);
            celdasCabeceraTabla.setAlignment(HorizontalAlignment.CENTER);
            celdasCabeceraTabla.setVerticalAlignment(VerticalAlignment.CENTER);

            Font font = book.createFont();
            font.setFontName("Arial");
            font.setBold(true);
            font.setColor(IndexedColors.WHITE.getIndex());
            font.setFontHeightInPoints((short) 12);
            celdasCabeceraTabla.setFont(font);
//            //CABECERAS
            Row filaEncabezado_1 = hoja.createRow(7);

            for (int i = 0; i < cabecera.length; i++) {

                Cell celdaEncabezado = filaEncabezado_1.createCell(i);
                celdaEncabezado.setCellStyle(celdasCabeceraTabla);
                celdaEncabezado.setCellValue(cabecera[i]);
            }

            //INICIA PARTE DATOS
            numFilasTabla = tablaCienPorciento.getRowCount();
            numColumnasTabla = (tablaCienPorciento.getColumnCount());
            //No.filas de cabecera+inicio de datos+1
            ultimaFilaRegistros = (tablaCienPorciento.getRowCount()) + 10;
            empezarLlenadoDesdeFila = 8;

            CellStyle datosEstilo = book.createCellStyle();
            //bordes de la tabla

            datosEstilo.setBorderBottom(BorderStyle.THIN);
            datosEstilo.setBorderLeft(BorderStyle.THIN);
            datosEstilo.setBorderRight(BorderStyle.THIN);
            datosEstilo.setBorderTop(BorderStyle.THIN);

            for (int i = 0; i < numFilasTabla; i++) {
                //?
                Row fila = hoja.createRow(i + empezarLlenadoDesdeFila);
                //Obteniendo información de las columnas

                for (int a = 0; a < numColumnasTabla; a++) {
                    Cell celda;
                    celda = fila.createCell(a);
                    celda.setCellStyle(datosEstilo);
                    if (tablaCienPorciento.getValueAt(i, a) == null) {
                        celda.setCellValue("");
                    } else {
                        celda.setCellValue(String.valueOf(tablaCienPorciento.getValueAt(i, a)));
                    }
                }

            }
            //FIN PARTE DATOS
            //DATOS TOTAL
            CellStyle txtT1 = book.createCellStyle();
            txtT1.setAlignment(HorizontalAlignment.CENTER);

            Font fontTotal = (Font) book.createFont();
            fontTotal.setFontName("Arial");
            fontTotal.setBold(true);
            fontTotal.setColor(IndexedColors.BLACK.getIndex());
            fontTotal.setFontHeightInPoints((short) 12);
            txtT1.setFont(fontTotal);

            CellStyle txtT2 = book.createCellStyle();
            txtT1.setAlignment(HorizontalAlignment.CENTER);

            Font cabeceraTotales = (Font) book.createFont();
            cabeceraTotales.setFontName("Arial");
            cabeceraTotales.setBold(true);
            cabeceraTotales.setColor(IndexedColors.BLACK.getIndex());
            cabeceraTotales.setFontHeightInPoints((short) 10);
            txtT2.setFont(cabeceraTotales);

            Row stx = hoja.createRow(ultimaFilaRegistros + 1);
//            Cell st_1 = stx.createCell(7);
//            st_1.setCellStyle(txtT2);
//            st_1.setCellValue("Total Base 0%");
//
            Cell st_2 = stx.createCell(7);
            st_2.setCellStyle(txtT2);
            st_2.setCellValue("Total Base 16%");
//
//            Cell st_3 = stx.createCell(9);
//            st_3.setCellStyle(txtT2);
//            st_3.setCellValue("Total Retención 4%");

//            Cell st_4 = stx.createCell(10);
//            st_4.setCellStyle(txtT2);
//            st_4.setCellValue("Total Retención 10%");
//
//            Cell st_5 = stx.createCell(11);
//            st_5.setCellStyle(txtT2);
//            st_5.setCellValue("Total Retención 10.67%");
//
//            Cell st_6 = stx.createCell(12);
//            st_6.setCellStyle(txtT2);
//            st_6.setCellValue("Total Cuota Compensatoria");
            Cell st_7 = stx.createCell(8);
            st_7.setCellStyle(txtT2);
            st_7.setCellValue("Total IVA");

            Cell st_8 = stx.createCell(9);
            st_8.setCellStyle(txtT2);
            st_8.setCellValue("Total");

            Cell st_9 = stx.createCell(10);
            st_9.setCellStyle(txtT2);
            st_9.setCellValue("Total Cobrado");

            Row fila = hoja.createRow(ultimaFilaRegistros + 2);
            //Obteniendo información de las columnas

            for (int a = 0; a < tablaTotalCien.getColumnCount(); a++) {
                Cell celda;
                celda = fila.createCell((a) + 7);
                celda.setCellStyle(datosEstilo);
                celda.setCellValue(String.valueOf(tablaTotalCien.getValueAt(0, a)));
            }

            //FIN TOTALES
            for (int i = 0; i < 22; i++) {
                hoja.autoSizeColumn(i);
            }

            try {
                book.write(new FileOutputStream(archivo + ".xlsx"));
                JOptionPane.showMessageDialog(null, "Exportacion Exitosa");
            } catch (IOException ex) {
                Logger.getLogger(GeneradorExcel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
