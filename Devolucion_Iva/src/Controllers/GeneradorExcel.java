/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;
//Poi 3.16

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
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

    private int numFilasTabla, numColumnasTabla, filaDatos;

    public void generarExcelAuxiliarIvaAcred(JTable tableAuxIvaAcred1, String tituloPestaniaHoja, String periodo, String anio) throws IOException {
        JFileChooser seleccionar = new JFileChooser();
        File archivo;
        if (seleccionar.showDialog(null, "Exportar Excel") == JFileChooser.APPROVE_OPTION) {
            archivo = seleccionar.getSelectedFile();
            Workbook book;
            //nuevo archivo
            book = new XSSFWorkbook();
            //hoja de trabajo
            Sheet hoja = book.createSheet(tituloPestaniaHoja + " " + periodo);
            //Definiendo alineamiento de titulo
            CellStyle tituloEstilo = book.createCellStyle();
            CellStyle subTitulos = book.createCellStyle();
            CellStyle subTitulosDos = book.createCellStyle();
            CellStyle sbt3 = book.createCellStyle();
            CellStyle sbt4 = book.createCellStyle();

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
            celdaTitulo.setCellValue("AGROECOLOGIA INTENSIVA PARA EL CAMPO S.A. DE C.V.");

            //(int firstRow, int lastRow, int firstCol, int lastCol)
            //alcance del conbinado de celdas
            hoja.addMergedRegion(new CellRangeAddress(1, 1, 2, 10));

            subTitulos.setFont(fuenteSubtitulo);
            Row filaSubtitulo = hoja.createRow(2);
            Cell celdaSubTitulo = filaSubtitulo.createCell(2);
            celdaSubTitulo.setCellStyle(subTitulos);
            celdaSubTitulo.setCellValue("R.F.C    AIC171129UAA");
            hoja.addMergedRegion(new CellRangeAddress(2, 2, 2, 10));

            subTitulosDos.setFont(fuenteSubtituloDos);
            Row filaSubtituloDos = hoja.createRow(3);
            Cell celdaSubTituloDos = filaSubtituloDos.createCell(2);
            celdaSubTituloDos.setCellStyle(subTitulosDos);
            celdaSubTituloDos.setCellValue("CARRETERA MEXICO OAXACA KM 97, JANTETELCO, JANTETELCO MORELOS, C.P. 62970");
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
            //Inicio de fila para empezar a dibujar los registros
            filaDatos = 8;
            CellStyle datosEstilo = book.createCellStyle();
            datosEstilo.setBorderBottom(BorderStyle.THIN);
            datosEstilo.setBorderLeft(BorderStyle.THIN);
            datosEstilo.setBorderRight(BorderStyle.THIN);
            datosEstilo.setBorderTop(BorderStyle.THIN);

            for (int i = 0; i < numFilasTabla; i++) {
                //?
                Row fila = hoja.createRow(i + filaDatos);
                //?
                for (int j = 0; j < numColumnasTabla; j++) {
                    Cell celda = fila.createCell(j);
                    celda.setCellStyle(datosEstilo);
                    celda.setCellValue(String.valueOf(tableAuxIvaAcred1.getValueAt(i, j)));
                }
            }

            //tamaño automatico a celdas
            hoja.autoSizeColumn(0);
            hoja.autoSizeColumn(1);
            hoja.autoSizeColumn(2);
            hoja.autoSizeColumn(3);
            hoja.autoSizeColumn(4);

            hoja.setZoom(100);

            try {
                //Creando el archivo fisico
                book.write(new FileOutputStream(archivo + ".xlsx"));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(GeneradorExcel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
