package admin.com.almoskyadmin.utils.print;

import com.mazenrashed.printooth.Printooth;
import com.mazenrashed.printooth.data.DefaultPrinter;
import com.mazenrashed.printooth.data.Printable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import admin.com.almoskyadmin.model.OrderListdto;
import admin.com.almoskyadmin.model.data;

import static admin.com.almoskyadmin.utils.constants.Constants.COMPANY_ADDRESS3;
import static admin.com.almoskyadmin.utils.constants.Constants.COMPANY_BILL_GREETING1;
import static admin.com.almoskyadmin.utils.constants.Constants.COMPANY_BILL_GREETING2;
import static admin.com.almoskyadmin.utils.constants.Constants.COMPANY_DATE;
import static admin.com.almoskyadmin.utils.constants.Constants.COMPANY_GROSS;
import static admin.com.almoskyadmin.utils.constants.Constants.COMPANY_ITEM_AMOUNT;
import static admin.com.almoskyadmin.utils.constants.Constants.COMPANY_ITEM_DESCRIPTION;
import static admin.com.almoskyadmin.utils.constants.Constants.COMPANY_ITEM_QUANTITY;
import static admin.com.almoskyadmin.utils.constants.Constants.COMPANY_ITEM_TOTAL;
import static admin.com.almoskyadmin.utils.constants.Constants.COMPANY_NAME;
import static admin.com.almoskyadmin.utils.constants.Constants.COMPANY_ORDER_NO;
import static admin.com.almoskyadmin.utils.constants.Constants.COMPANY_TAX;
import static admin.com.almoskyadmin.utils.constants.Constants.COMPANY_TELE;
import static admin.com.almoskyadmin.utils.constants.Constants.COMPANY_TELE2;
import static admin.com.almoskyadmin.utils.constants.Constants.COMPANY_TRN;
import static admin.com.almoskyadmin.utils.constants.Constants.COMPANY_VAT;
import static admin.com.almoskyadmin.utils.constants.Constants.NASAB_OFFER;
import static admin.com.almoskyadmin.utils.constants.Constants.TERMS1;
import static admin.com.almoskyadmin.utils.constants.Constants.TERMS2;
import static admin.com.almoskyadmin.utils.constants.Constants.TERMS3;
import static admin.com.almoskyadmin.utils.constants.Constants.TERMS4;
import static admin.com.almoskyadmin.utils.constants.Constants.TERMS5;
import static admin.com.almoskyadmin.utils.constants.Constants.TERMS6;
import static admin.com.almoskyadmin.utils.constants.Constants.TERMS7;
import static admin.com.almoskyadmin.utils.constants.Constants.TERMS_CONDITIONS;

public class PrintBill {
    public void bluetoothInvoicePrinting(int count, ArrayList<data.Result> drycleanList, ArrayList<data.Result> ironList, ArrayList<data.Result> washList, int orderId, OrderListdto.Result userData, String total, String vat, String offer, String subtotal) {
        for (int i = 0; i < count; i++) {
            ArrayList<Printable> printables = new ArrayList<>();
            printables.add(new Printable.PrintableBuilder()
                    .setAlignment(DefaultPrinter.Companion.getALLIGMENT_CENTER())
                    .setText(COMPANY_TAX)
                    .setEmphasizedMode(DefaultPrinter.Companion.getEMPHASISED_MODE_BOLD())
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
            printables.add(new Printable.PrintableBuilder()
                    .setAlignment(DefaultPrinter.Companion.getALLIGMENT_CENTER())
                    .setText(COMPANY_NAME)
                    .setEmphasizedMode(DefaultPrinter.Companion.getEMPHASISED_MODE_BOLD())
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
            printables.add(new Printable.PrintableBuilder()
                    .setAlignment(DefaultPrinter.Companion.getALLIGMENT_CENTER())
                    .setText(COMPANY_ADDRESS3)
                    .setEmphasizedMode(DefaultPrinter.Companion.getEMPHASISED_MODE_BOLD())
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
            printables.add(new Printable.PrintableBuilder()
                    .setAlignment(DefaultPrinter.Companion.getALLIGMENT_CENTER())
                    .setText(COMPANY_TRN)
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
            printables.add(new Printable.PrintableBuilder()
                    .setAlignment(DefaultPrinter.Companion.getALLIGMENT_CENTER())
                    .setText(COMPANY_TELE)
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
            printables.add(new Printable.PrintableBuilder()
                    .setAlignment(DefaultPrinter.Companion.getALLIGMENT_CENTER())
                    .setText(COMPANY_TELE2)
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
            printables.add(new Printable.PrintableBuilder()
                    .setText("................................................")
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
            printables.add(new Printable.PrintableBuilder()
                    .setText(COMPANY_ORDER_NO + orderId)
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
            printables.add(new Printable.PrintableBuilder()
                    .setText(COMPANY_DATE + getDate() + " " + getTime())
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(3)
                    .build());

            printables.add(new Printable.PrintableBuilder()
                    .setText("Customer Name  :" + userData.getName())
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
            printables.add(new Printable.PrintableBuilder()
                    .setText("Mobile Number  :" + userData.getcMobile())
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
            printables.add(new Printable.PrintableBuilder()
                    .setText("Address        :" + userData.getArea())
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(4)
                    .build());
            printables.add(new Printable.PrintableBuilder()
                    .setText(COMPANY_ITEM_DESCRIPTION + createSpace(true, COMPANY_ITEM_DESCRIPTION, COMPANY_ITEM_DESCRIPTION.length()) +
                            COMPANY_ITEM_QUANTITY + createSpace(true, COMPANY_ITEM_QUANTITY, COMPANY_ITEM_QUANTITY.length()) +
                            COMPANY_ITEM_AMOUNT + createSpace(true, COMPANY_ITEM_AMOUNT, COMPANY_ITEM_AMOUNT.length()))
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
            printables.add(new Printable.PrintableBuilder()
                    .setText("................................................")
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
            //dryclean
            for (data.Result item : drycleanList) {
                printables.add(new Printable.PrintableBuilder()
                        .setText("Dry Clean")
                        .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                        .setNewLinesAfter(2)
                        .build());
                printables.add(new Printable.PrintableBuilder()
                        .setText(item.getItemName()+ createSpace(true, COMPANY_ITEM_DESCRIPTION, item.getItemName().length()) +
                                item.getQty()+ createSpace(true, COMPANY_ITEM_QUANTITY, String.valueOf(item.getQty()).length()) +
                                getPrice(item.getQty(), item.getPrice()) + createSpace(true, COMPANY_ITEM_AMOUNT, getPrice(item.getQty(), item.getPrice()).length()))
                        .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                        .setNewLinesAfter(2)
                        .build());
            }
            //washList
            for (data.Result item : washList) {
                printables.add(new Printable.PrintableBuilder()
                        .setText("Wash + Iron")
                        .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                        .setNewLinesAfter(2)
                        .build());
                printables.add(new Printable.PrintableBuilder()
                        .setText(item.getItemName()+ createSpace(true, COMPANY_ITEM_DESCRIPTION, item.getItemName().length()) +
                                item.getQty()+ createSpace(true, COMPANY_ITEM_QUANTITY, String.valueOf(item.getQty()).length()) +
                                getPrice(item.getQty(), item.getPrice()) + createSpace(true, COMPANY_ITEM_AMOUNT, getPrice(item.getQty(), item.getPrice()).length()))
                        .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                        .setNewLinesAfter(2)
                        .build());
            }
            //iron
            for (data.Result item : ironList) {
                printables.add(new Printable.PrintableBuilder()
                        .setText("Wash + Iron")
                        .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                        .setNewLinesAfter(2)
                        .build());
                printables.add(new Printable.PrintableBuilder()
                        .setText(item.getItemName()+ createSpace(true, COMPANY_ITEM_DESCRIPTION, item.getItemName().length()) +
                                item.getQty()+ createSpace(true, COMPANY_ITEM_QUANTITY, String.valueOf(item.getQty()).length()) +
                                getPrice(item.getQty(), item.getPrice()) + createSpace(true, COMPANY_ITEM_AMOUNT, getPrice(item.getQty(), item.getPrice()).length()))
                        .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                        .setNewLinesAfter(2)
                        .build());
            }

            printables.add(new Printable.PrintableBuilder()
                    .setText("................................................")
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
//            String total = String.valueOf(order.getOrder().getExlusivePrice());
            printables.add(new Printable.PrintableBuilder()
                    .setText(COMPANY_ITEM_TOTAL + createSpace(true, COMPANY_ITEM_TOTAL.length(), total.length()) + total)
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());

            printables.add(new Printable.PrintableBuilder()
                    .setText(NASAB_OFFER + createSpace(true, NASAB_OFFER.length(), offer.length()) + offer)
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(4)
                    .build());
//            String vat = String.valueOf(order.getOrder().getVat());
            printables.add(new Printable.PrintableBuilder()
                    .setText(COMPANY_VAT + createSpace(true, COMPANY_VAT.length(), vat.length()) + vat)
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(4)
                    .build());

//            String gross = String.valueOf(order.getOrder().getPrice());
            printables.add(new Printable.PrintableBuilder()
                    .setText(COMPANY_GROSS + createSpace(true, COMPANY_GROSS.length(), subtotal.length()) + subtotal)
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(4)
                    .build());
            printables.add(new Printable.PrintableBuilder()
                    .setText("................................................")
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
            printables.add(new Printable.PrintableBuilder()
                    .setAlignment(DefaultPrinter.Companion.getALLIGMENT_LEFT())
                    .setText(TERMS_CONDITIONS)
                    .setEmphasizedMode(DefaultPrinter.Companion.getEMPHASISED_MODE_BOLD())
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
            printables.add(new Printable.PrintableBuilder()
                    .setAlignment(DefaultPrinter.Companion.getALLIGMENT_LEFT())
                    .setText(TERMS1)
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
            printables.add(new Printable.PrintableBuilder()
                    .setAlignment(DefaultPrinter.Companion.getALLIGMENT_LEFT())
                    .setText(TERMS2)
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
            printables.add(new Printable.PrintableBuilder()
                    .setAlignment(DefaultPrinter.Companion.getALLIGMENT_LEFT())
                    .setText(TERMS3)
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
            printables.add(new Printable.PrintableBuilder()
                    .setAlignment(DefaultPrinter.Companion.getALLIGMENT_LEFT())
                    .setText(TERMS4)
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
            printables.add(new Printable.PrintableBuilder()
                    .setAlignment(DefaultPrinter.Companion.getALLIGMENT_LEFT())
                    .setText(TERMS5)
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
            printables.add(new Printable.PrintableBuilder()
                    .setAlignment(DefaultPrinter.Companion.getALLIGMENT_LEFT())
                    .setText(TERMS6)
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
            printables.add(new Printable.PrintableBuilder()
                    .setAlignment(DefaultPrinter.Companion.getALLIGMENT_LEFT())
                    .setText(TERMS7)
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
            printables.add(new Printable.PrintableBuilder()
                    .setText("................................................")
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
            printables.add(new Printable.PrintableBuilder()
                    .setAlignment(DefaultPrinter.Companion.getALLIGMENT_CENTER())
                    .setText(COMPANY_BILL_GREETING1)
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
            printables.add(new Printable.PrintableBuilder()
                    .setAlignment(DefaultPrinter.Companion.getALLIGMENT_CENTER())
                    .setText(COMPANY_BILL_GREETING2)
                    .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                    .setNewLinesAfter(2)
                    .build());
            Printooth.INSTANCE.printer().print(printables);
        }
    }

    public static String getDate() {
        return new SimpleDateFormat("dd-MM-yyyy").format(new Date().getTime());
    }

    public static String getTime() {
        return new SimpleDateFormat("hh:mm").format(new Date().getTime());
    }

    private String getPrice(int qty, double Price) {
        double price = qty * Price;
        return String.format(Locale.US, "%.2f", price);
    }


    private String createSpace(boolean isBluetooth, String item, int length) {
        int total;
        int num;
        switch (item) {
            case COMPANY_ITEM_DESCRIPTION:
                total = isBluetooth ? 36 : 20;
                num = total - length;
                return new String(new char[num]).replace('\0', ' ');
            case COMPANY_ITEM_QUANTITY:
                total = isBluetooth ? 5 : 5;
                num = total - length;
                return new String(new char[num]).replace('\0', ' ');
            case COMPANY_ITEM_AMOUNT:
                total = isBluetooth ? 8 : 8;
                num = total - length;
                return new String(new char[num]).replace('\0', ' ');
        }
        return null;
    }

    private String createSpace(boolean isBluetooth, int firstLength, int secondLegth) {
        int num = isBluetooth ? 48 - firstLength : 32 - firstLength;
        num = num - secondLegth;
        return new String(new char[num]).replace('\0', ' ');
    }
}
