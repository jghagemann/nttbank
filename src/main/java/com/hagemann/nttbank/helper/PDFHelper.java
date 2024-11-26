package com.hagemann.nttbank.helper;

import com.hagemann.nttbank.domain.correntista.Correntista;
import com.hagemann.nttbank.domain.transacao.Transacao;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PDFHelper {

    public static ByteArrayOutputStream gerarPdfTransacoes(Correntista correntista, List<Transacao> transacoes) {
        try (PDDocument document = new PDDocument(); ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            PDFont helveticaBold = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
            PDFont helvetica = new PDType1Font(Standard14Fonts.FontName.HELVETICA);


            contentStream.setFont(helveticaBold, 18);
            contentStream.beginText();
            contentStream.newLineAtOffset(50, 750);
            contentStream.showText("Relatório de Transações");
            contentStream.endText();

            contentStream.setFont(helvetica, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(50, 730);
            contentStream.showText("Correntista: " + correntista.getNome() + " " + correntista.getSobrenome());
            contentStream.newLineAtOffset(0, -15);
            contentStream.showText("Email: " + correntista.getEmail());
            contentStream.newLineAtOffset(0, -15);
            contentStream.showText("Ativo: " + (Boolean.TRUE.equals(correntista.getAtivo()) ? "Sim" : "Não"));
            contentStream.endText();

            float margin = 50;
            float yPosition = 690;
            float[] columnWidths = {120, 120, 100, 100, 120};
            String[] headers = {"Data", "Valor", "Origem", "Destino", "Categoria"};

            drawTableHeader(contentStream, headers, columnWidths, yPosition);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            yPosition -= 25;

            for (Transacao transacao : transacoes) {
                if (yPosition < 50) {
                    contentStream.close();
                    page = new PDPage(PDRectangle.A4);
                    document.addPage(page);
                    contentStream = new PDPageContentStream(document, page);
                    yPosition = 750;
                    drawTableHeader(contentStream, headers, columnWidths, yPosition);
                    yPosition -= 25;
                }

                contentStream.beginText();
                contentStream.setFont(helvetica, 10);
                contentStream.newLineAtOffset(margin, yPosition);


                contentStream.showText(transacao.getDataTransacao().format(formatter));
                contentStream.newLineAtOffset(columnWidths[0], 0);
                contentStream.showText("R$ " + transacao.getValor());
                contentStream.newLineAtOffset(columnWidths[1], 0);
                contentStream.showText(transacao.getContaOrigem().getNumero());
                contentStream.newLineAtOffset(columnWidths[2], 0);
                contentStream.showText(transacao.getContaDestino() != null ? transacao.getContaDestino().getNumero() : "-");
                contentStream.newLineAtOffset(columnWidths[3], 0);
                contentStream.showText(transacao.getCategoria().name());

                contentStream.endText();
                yPosition -= 20;
            }

            contentStream.close();
            document.save(baos);
            return baos;
        } catch (IOException e) {
            throw new RuntimeException("Erro ao gerar relatório em PDF", e);
        }
    }

    private static void drawTableHeader(PDPageContentStream contentStream, String[] headers, float[] columnWidths, float yPosition) throws IOException {
        PDFont helveticaBold = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);

        contentStream.setFont(helveticaBold, 10);
        contentStream.setLineWidth(1f);
        contentStream.setStrokingColor(0, 0, 0);

        float xPosition = 50;
        contentStream.beginText();
        contentStream.newLineAtOffset(xPosition, yPosition);

        for (int i = 0; i < headers.length; i++) {
            contentStream.showText(headers[i]);
            xPosition += columnWidths[i];
            contentStream.newLineAtOffset(columnWidths[i], 0);
        }

        contentStream.endText();
    }
}
