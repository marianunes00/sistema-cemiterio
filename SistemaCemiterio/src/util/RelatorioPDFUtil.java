package util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import javax.swing.JTable;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RelatorioPDFUtil {

    public static void gerarRelatorio(
            JTable tabela,
            String titulo,
            String caminho,
            String usuario
    ) throws Exception {

        Document document = new Document(PageSize.A4.rotate(), 20, 20, 20, 20);
        PdfWriter.getInstance(document, new FileOutputStream(caminho));
        document.open();

        Font fonteTitulo = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
        Paragraph tituloPdf = new Paragraph(titulo, fonteTitulo);
        tituloPdf.setAlignment(Element.ALIGN_CENTER);
        document.add(tituloPdf);

        document.add(new Paragraph(" "));

        Font fonteInfo = new Font(Font.FontFamily.HELVETICA, 10);
        String dataHora = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

        Paragraph info = new Paragraph(
                "Usuário: " + usuario + " | Gerado em: " + dataHora,
                fonteInfo
        );
        info.setAlignment(Element.ALIGN_RIGHT);
        document.add(info);

        document.add(new Paragraph(" "));

        PdfPTable pdfTable = new PdfPTable(tabela.getColumnCount());
        pdfTable.setWidthPercentage(100);

        Font fonteCabecalho = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        for (int i = 0; i < tabela.getColumnCount(); i++) {
            PdfPCell cell = new PdfPCell(
                    new Phrase(tabela.getColumnName(i), fonteCabecalho)
            );
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfTable.addCell(cell);
        }

        Font fonteDados = new Font(Font.FontFamily.HELVETICA, 11);
        for (int row = 0; row < tabela.getRowCount(); row++) {
            for (int col = 0; col < tabela.getColumnCount(); col++) {
                Object valor = tabela.getValueAt(row, col);
                pdfTable.addCell(
                        new Phrase(valor == null ? "" : valor.toString(), fonteDados)
                );
            }
        }

        document.add(pdfTable);
        document.close();
    }
}
