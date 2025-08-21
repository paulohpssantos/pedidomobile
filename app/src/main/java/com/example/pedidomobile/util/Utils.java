package com.example.pedidomobile.util;

import android.view.View;
import android.widget.TextView;

import com.example.pedidomobile.R;
import com.google.android.material.snackbar.Snackbar;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class Utils {

    public static final Locale locale = Locale.getDefault();

    public static String replaceCpfCnpj(String cnpj){
        cnpj = cnpj.replace('.', ' ');//onde há ponto coloca espaço
        cnpj = cnpj.replace('/', ' ');//onde há barra coloca espaço
        cnpj = cnpj.replace('-', ' ');//onde há traço coloca espaço
        cnpj = cnpj.replaceAll(" ", "");//retira espaço
        return cnpj;
    }

    public static boolean isCnpjValido(String cnpj) {
        if (!cnpj.substring(0, 1).equals("")) {
            try {
                cnpj = replaceCpfCnpj(cnpj);
                int soma = 0, dig;
                String cnpj_calc = cnpj.substring(0, 12);

                if (cnpj.length() != 14) {
                    return false;
                }
                char[] chr_cnpj = cnpj.toCharArray();
                /* Primeira parte */
                for (int i = 0; i < 4; i++) {
                    if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9) {
                        soma += (chr_cnpj[i] - 48) * (6 - (i + 1));
                    }
                }
                for (int i = 0; i < 8; i++) {
                    if (chr_cnpj[i + 4] - 48 >= 0 && chr_cnpj[i + 4] - 48 <= 9) {
                        soma += (chr_cnpj[i + 4] - 48) * (10 - (i + 1));
                    }
                }
                dig = 11 - (soma % 11);
                cnpj_calc += (dig == 10 || dig == 11) ? "0" : Integer.toString(
                        dig);
                /* Segunda parte */
                soma = 0;
                for (int i = 0; i < 5; i++) {
                    if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9) {
                        soma += (chr_cnpj[i] - 48) * (7 - (i + 1));
                    }
                }
                for (int i = 0; i < 8; i++) {
                    if (chr_cnpj[i + 5] - 48 >= 0 && chr_cnpj[i + 5] - 48 <= 9) {
                        soma += (chr_cnpj[i + 5] - 48) * (10 - (i + 1));
                    }
                }
                dig = 11 - (soma % 11);
                cnpj_calc += (dig == 10 || dig == 11) ? "0" : Integer.toString(
                        dig);
                return cnpj.equals(cnpj_calc);
            }
            catch (Exception e) {
                return false;
            }
        }
        else {
            return false;
        }
    }

    public static boolean validaEmail(final String email) {
        if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return true;
        }
        return false;
    }

    public static BigDecimal parseToBigDecimal(String value) {
        String replaceable = String.format("[%s,.\\s]", getCurrencySymbol());

        String cleanString = value.replaceAll(replaceable, "");

        try {
            return new BigDecimal(cleanString).setScale(
                    2, BigDecimal.ROUND_FLOOR).divide(new BigDecimal(100), BigDecimal.ROUND_FLOOR);
        } catch (NumberFormatException e) {
            return new BigDecimal(0);

        }
    }

    public static String formataValor(String valor) {
        DecimalFormat df = new DecimalFormat("0.00");
        return String.valueOf(df.format(Double.valueOf(valor)));

    }

    public static String formataValorString(String valor) {
        BigDecimal bD = new BigDecimal(formataValorDouble(formataValor(valor)));
        String newFormat = String.valueOf(NumberFormat.getCurrencyInstance(Locale.getDefault()).format(bD));
        String replaceable = String.format("[%s]", getCurrencySymbol());
        return newFormat.replaceAll(replaceable, "");

    }

    public static String formataValorDouble(String valor) {
        String replaceable = String.format("[%s,.\\s]", getCurrencySymbol());
        String cleanString = valor.replaceAll(replaceable, "");
        StringBuilder stringBuilder = new StringBuilder(cleanString.replaceAll(" ", ""));

        return String.valueOf(stringBuilder.insert(cleanString.length() - 2, '.'));

    }

    public static String getCurrencySymbol() {
        return NumberFormat.getCurrencyInstance(Locale.getDefault()).getCurrency().getSymbol();

    }

    public static void msgConfirmacao(View contextView, String msg){
        Snackbar snackbar = Snackbar.make(contextView, msg, Snackbar.LENGTH_LONG);
        View snackbarLayout = snackbar.getView();
        int snackbarTextId = com.google.android.material.R.id.snackbar_text;
        TextView textView = snackbarLayout.findViewById(snackbarTextId);
        textView.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_sucesso, 0, 0, 0);
        snackbar.show();
    }
}
