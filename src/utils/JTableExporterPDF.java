/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.*;
import com.itextpdf.layout.element.*;
import java.awt.Desktop;
import javax.swing.*;
import javax.swing.table.TableModel;
import java.io.File;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;

public class JTableExporterPDF{

    public static void exportJTableToPDF(JTable table) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu PDF");

        int userChoice = fileChooser.showSaveDialog(null);

        if (userChoice == JFileChooser.APPROVE_OPTION) {
            try {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                if (!filePath.toLowerCase().endsWith(".pdf")) {
                    filePath += ".pdf";
                }

                PdfWriter writer = new PdfWriter(filePath);
                PdfDocument pdf = new PdfDocument(writer);
                Document document = new Document(pdf);
                PdfFont font = PdfFontFactory.createFont(
                        "C:/Windows/Fonts/arial.ttf",
                        PdfEncodings.IDENTITY_H,
                        true
                );
                document.setFont(font);

                TableModel model = table.getModel();

                // Tạo bảng trong PDF
                Table pdfTable = new Table(model.getColumnCount());

                // Header
                for (int i = 0; i < model.getColumnCount(); i++) {
                    pdfTable.addHeaderCell(new Cell().add(
                            new Paragraph(model.getColumnName(i))
                    ));
                }

                // Data
                for (int i = 0; i < model.getRowCount(); i++) {
                    for (int j = 0; j < model.getColumnCount(); j++) {
                        Object value = model.getValueAt(i, j);
                        pdfTable.addCell(new Cell().add(
                                new Paragraph(value == null ? "" : value.toString())
                        ));
                    }
                }

                document.add(pdfTable);
                document.close();

                Desktop.getDesktop().open(new File(filePath));

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Lỗi xuất PDF!");
                e.printStackTrace();
            }
        }
    }
}
