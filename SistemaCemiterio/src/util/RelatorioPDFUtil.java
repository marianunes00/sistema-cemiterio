package util;


import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


import javax.swing.JTable;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RelatorioPDFUtil {

    
    public static void gerarRelatorio(
            JTable tabela,
            String titulo,
            String caminho,
            String usuario
    ) throws DocumentException, IOException {

        
        // Verifica se a tabela tem dados         
        if (tabela == null || tabela.getRowCount() == 0) {
            throw new IllegalArgumentException(
                "Não há dados na tabela para gerar o relatório."
            );
        }

        //Cria um novo arquivo do tipo pdf e colcoa no diretorio         
        File arquivoPDF = new File(caminho);
        File diretorio = arquivoPDF.getParentFile();

        // Cria o diretório caso não exista
        if (diretorio != null && !diretorio.exists()) {
            diretorio.mkdirs();
        }

        // Criar o documento PDF , define as margens e que vai ser na horinzontal
        Document document = new Document(PageSize.A4.rotate(), 20, 20, 20, 20);

        PdfWriter.getInstance(document, new FileOutputStream(arquivoPDF));
        document.open();

        // 
        //  Título do relatório
        //  Cria um novo paragrafo define alinhamento, fonte tamanho e que vai ser bold depois coloca tudo no documento
        Font fonteTitulo = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
        Paragraph tituloPdf = new Paragraph(titulo, fonteTitulo);
        tituloPdf.setAlignment(Element.ALIGN_CENTER);
        document.add(tituloPdf);

        document.add(new Paragraph(" ")); // Espaçamento
 
        // Coloca no docuemnto as informações do usuário e data
        Font fonteInfo = new Font(Font.FontFamily.HELVETICA, 10);

        String dataHora = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

        Paragraph info = new Paragraph(
                "Usuário: " + usuario + " | Gerado em: " + dataHora,
                fonteInfo
        );
        info.setAlignment(Element.ALIGN_RIGHT); //alinhamento
        document.add(info);

        document.add(new Paragraph(" ")); // Espaçamento

         
        //Criar tabela PDF
        PdfPTable pdfTable = new PdfPTable(tabela.getColumnCount());
        pdfTable.setWidthPercentage(100);

         
        //Denfine as informações do Cabeçalho da tabela
        Font fonteCabecalho = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);

        for (int i = 0; i < tabela.getColumnCount(); i++) {
            PdfPCell cell = new PdfPCell(
                    new Phrase(tabela.getColumnName(i), fonteCabecalho)
            );
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY); //define cor do background
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);//define alinhamento
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            pdfTable.addCell(cell);
        }

        //  Dados da tabela
         
        Font fonteDados = new Font(Font.FontFamily.HELVETICA, 11);

        for (int row = 0; row < tabela.getRowCount(); row++) {
            for (int col = 0; col < tabela.getColumnCount(); col++) {

                Object valor = tabela.getValueAt(row, col);

                PdfPCell cell = new PdfPCell(
                        new Phrase(
                                valor == null ? "" : valor.toString(),
                                fonteDados
                        )
                );

                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);

                pdfTable.addCell(cell);
            }
        }

        
        // Adiciona a tabela ao documento
        document.add(pdfTable);
        
        // Fecha documento
        document.close();
        
        // Abri o PDF automaticamente         
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().open(arquivoPDF);
        }
    }
}
