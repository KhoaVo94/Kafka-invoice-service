package com.khoavo.util;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.khoavo.domain.Invoice;
import com.khoavo.domain.Items;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class PDFWriter {

    static final List<String> ITEM_INVOICE_COLUMN_NAMES = Arrays.asList("#", "Item & Description", "Qty", "Rate", "Amount");

    public static void invoiceToPDF(Invoice invoice) throws FileNotFoundException, DocumentException {
        String fileName = String.format("%s.pdf", invoice.getInvoiceNumber());

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(fileName));

        document.open();

        Font headerFont = FontFactory.getFont(FontFactory.COURIER, 18, BaseColor.BLACK);
        Font titleFont = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Font textFont = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);

        document.add(new Paragraph("Test Invoice", headerFont));

        document.add(new Paragraph("# " + invoice.getInvoiceNumber().toString(), textFont));

        lineBreak(document, 3);

        document.add(new Paragraph(invoice.getSeller().getName(), FontFactory.getFont(FontFactory.COURIER, 16, Font.BOLD,BaseColor.BLACK)));
        document.add(new Paragraph(invoice.getSeller().getAddress().getAddressLine(), textFont));
        document.add(new Paragraph(invoice.getSeller().generalAddress(), textFont));

        lineBreak(document, 2);

        document.add(new Paragraph("Bill To", titleFont));
        document.add(new Paragraph(invoice.getCustomer().getName(), FontFactory.getFont(FontFactory.COURIER, 16, Font.BOLD,BaseColor.BLACK)));
        document.add(new Paragraph(invoice.getCustomer().getAddress().getAddressLine(), textFont));
        document.add(new Paragraph(invoice.getCustomer().generalAddress(), textFont));

        lineBreak(document, 2);

        document.add(new Paragraph(String.format("Invoice Date: %s", invoice.getInvoiceDate())));

        lineBreak(document, 1);

        addItemTable(document, invoice.getItems());

        lineBreak(document, 1);

        document.add(new Paragraph(String.format("Sub total: $%s", invoice.subTotal()), titleFont));
        document.add(new Paragraph(String.format("Discount(%s%%): $%s", invoice.getDiscountPercent(), invoice.discountAmount()), titleFont));
        document.add(new Paragraph(String.format("Sales Tax(%s%%): $%s", invoice.getTaxPercent(), invoice.taxAmount()), titleFont));
        document.add(new Paragraph(String.format("Total: $%.2f", invoice.total()), FontFactory.getFont(FontFactory.COURIER, 14, Font.BOLD, BaseColor.BLACK)));

        document.close();
    }


    private static void lineBreak(Document document, int numberOfLines) throws DocumentException {
        for (int i = 0; i < numberOfLines; i++) {
            document.add(new Paragraph("                                   "));
        }
    }

    private static void addItemTable(Document document, Items items) throws DocumentException {
        PdfPTable table = new PdfPTable(ITEM_INVOICE_COLUMN_NAMES.size());
        ITEM_INVOICE_COLUMN_NAMES.forEach(columnTitle -> {
            PdfPCell header = new PdfPCell();
            header.setBackgroundColor(BaseColor.LIGHT_GRAY);
            header.setBorderWidth(2);
            header.setPhrase(new Phrase(columnTitle));
            table.addCell(header);
        });
        items.items().forEach(item -> {
            table.addCell(String.valueOf(items.items().indexOf(item)));
            PdfPCell itemCell = new PdfPCell();
            itemCell.addElement(new Paragraph(item.getName(), FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK)));
            itemCell.addElement(new Paragraph(item.getDescription(), FontFactory.getFont(FontFactory.COURIER, 12, BaseColor.DARK_GRAY)));
            table.addCell(itemCell);
            table.addCell(String.valueOf(item.getAmount().getQuantity()));
            table.addCell(String.valueOf(item.getAmount().getRate()));
            table.addCell(String.valueOf(item.getAmount().getAmount()));
        });
        document.add(table);
    }
}
