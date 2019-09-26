package inura.aiya;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.table.DefaultTableModel;

public class Printer {

    Connection connection = DBConnection1.ConnectDB();
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    private PrintService findPrintService(PrintService[] services) {
        try {
            File file = new File("Printer_name.txt");
            Scanner scanner = new Scanner(file);
            String printerName = scanner.nextLine();
            for (PrintService service : services) {
                if (service.getName().equalsIgnoreCase(printerName)) {
                    return service;
                }
            }
        } catch (IOException e) {

        }
        return null;
    }

    public void printString(String text) {

        DocFlavor docFlavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
        PrintService printService[] = PrintServiceLookup.lookupPrintServices(docFlavor, pras);
        PrintService service = findPrintService(printService);
        DocPrintJob printJob = service.createPrintJob();

        try {
            byte[] bytes;
            bytes = text.getBytes("CP437");
            Doc doc = new SimpleDoc(bytes, docFlavor, null);
            printJob.print(doc, null);
        } catch (UnsupportedEncodingException | PrintException e) {

        }
    }

    public void printReceipt(String totalQuantity, String totalPrice, String cash, double balance, int receiptNo, DefaultTableModel model) {

        Printer printerService = new Printer();
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date date = new Date();
        String dateTime = dateFormat.format(date);
        String bal = Double.toString(balance);

        printerService.printString("\n  ==== H & E TECHNOLOGIES === \n\n"
                + "Receipt No: " + receiptNo
                + "\nDate/Time: " + dateTime
                + "\n--------------------------------\n"
                + "Item            Qty       Amount"
                + "\n--------------------------------\n");
        for (int i = 0; i < model.getRowCount(); i++) {
            String name = model.getValueAt(i, 1).toString();
            String quantity = model.getValueAt(i, 2).toString();
            String price = model.getValueAt(i, 3).toString();

            if (name.length() > 12) {
                int index = 0;
                while (index < name.length()) {
                    printerService.printString(name.substring(index, Math.min(index + 12, name.length())));
                    String length = name.substring(index, Math.min(index + 12, name.length()));
                    if (length.length() < 12) {
                        for (int ll = length.length(); ll <= 12; ll++) {
                            printerService.printString(" ");
                        }
                    }
                    index += 12;
                    if (index < name.length()) {
                        printerService.printString("\n");
                    }
                }
                printerService.printString("   ");
            } else {
                printerService.printString("" + name + "   ");
                for (int nl = name.length(); nl <= 12; nl++) {
                    printerService.printString(" ");
                }
            }

            if (quantity.length() == 3) {
                printerService.printString("" + quantity + "     ");
            } else {
                for (int ql = quantity.length(); ql < 3; ql++) {
                    printerService.printString(" ");
                }
                printerService.printString("" + quantity + "     ");
            }

            if (price.length() == 8) {
                printerService.printString("" + price + "\n");
            } else {
                for (int pl = price.length(); pl < 8; pl++) {
                    printerService.printString(" ");
                }
                printerService.printString("" + price + "\n");
            }
        }

        printerService.printString("--------------------------------\n"
                + "Total:          ");

        if (totalQuantity.length() == 3) {
            printerService.printString("" + totalQuantity + "     ");
        } else {
            for (int tql = totalQuantity.length(); tql < 3; tql++) {
                printerService.printString(" ");
            }
            printerService.printString("" + totalQuantity + "     ");
        }

        if (totalPrice.length() == 8) {
            printerService.printString("" + totalPrice + "\n\n\n");
        } else {
            for (int tpl = totalPrice.length(); tpl < 8; tpl++) {
                printerService.printString(" ");
            }
            printerService.printString("" + totalPrice + "\n\n\n");
        }

        printerService.printString("Cash:           Rs.     ");

        if (cash.length() == 8) {
            printerService.printString("" + cash + "\n");
        } else {
            for (int cl = cash.length(); cl < 8; cl++) {
                printerService.printString(" ");
            }
            printerService.printString("" + cash + "\n");
        }

        printerService.printString("                       _________\n"
                +"Balance:        Rs.     ");

        if (bal.length() == 8) {
            printerService.printString("" + bal + "\n");
        } else {
            for (int bl = bal.length(); bl < 8; bl++) {
                printerService.printString(" ");
            }
            printerService.printString("" + bal + "\n");
        }

        printerService.printString("                       =========\n\n"
                + " Thank You For Shopping With Us!\n\n"
                + "    88-3/31, 1st Cross Street,\n"
                + "            Colombo-11\n"
                + "         Tele: 0777 316457\n\n"
                + "         H@E IT 0771004590 \n\n\n\n\n");
    }
}
