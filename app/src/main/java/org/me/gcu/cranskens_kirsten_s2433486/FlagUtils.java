package org.me.gcu.cranskens_kirsten_s2433486;

import android.content.Context;
import java.util.HashMap;
import java.util.Map;


public class FlagUtils {

    private static final Map<String, String> CURR_TO_CC = new HashMap<>();

    static {
        // --- Major / common ---
        CURR_TO_CC.put("USD", "us");
        CURR_TO_CC.put("EUR", "eu");
        CURR_TO_CC.put("GBP", "gb");
        CURR_TO_CC.put("JPY", "jp");
        CURR_TO_CC.put("AUD", "au");
        CURR_TO_CC.put("CAD", "ca");
        CURR_TO_CC.put("CHF", "ch");
        CURR_TO_CC.put("CNY", "cn");
        CURR_TO_CC.put("NZD", "nz");
        CURR_TO_CC.put("AED", "ae");
        CURR_TO_CC.put("AFN", "af");
        CURR_TO_CC.put("ALL", "al");
        CURR_TO_CC.put("AMD", "am");
        CURR_TO_CC.put("ANG", "cw");
        CURR_TO_CC.put("AOA", "ao");
        CURR_TO_CC.put("ARS", "ar");
        CURR_TO_CC.put("AWG", "aw");
        CURR_TO_CC.put("AZN", "az");
        CURR_TO_CC.put("BAM", "ba");
        CURR_TO_CC.put("BBD", "bb");
        CURR_TO_CC.put("BDT", "bd");
        CURR_TO_CC.put("BGN", "bg");
        CURR_TO_CC.put("BHD", "bh");
        CURR_TO_CC.put("BIF", "bi");
        CURR_TO_CC.put("BMD", "bm");
        CURR_TO_CC.put("BND", "bn");
        CURR_TO_CC.put("BOB", "bo");
        CURR_TO_CC.put("BOV", "bo");
        CURR_TO_CC.put("BRL", "br");
        CURR_TO_CC.put("BSD", "bs");
        CURR_TO_CC.put("BTN", "bt");
        CURR_TO_CC.put("BWP", "bw");
        CURR_TO_CC.put("BYN", "by");
        CURR_TO_CC.put("BZD", "bz");
        CURR_TO_CC.put("CDF", "cd");
        CURR_TO_CC.put("CHE", "ch");
        CURR_TO_CC.put("CHW", "ch");
        CURR_TO_CC.put("CLF", "cl");
        CURR_TO_CC.put("CLP", "cl");
        CURR_TO_CC.put("COP", "co");
        CURR_TO_CC.put("COU", "co");
        CURR_TO_CC.put("CRC", "cr");
        CURR_TO_CC.put("CUC", "cu");
        CURR_TO_CC.put("CUP", "cu");
        CURR_TO_CC.put("CVE", "cv");
        CURR_TO_CC.put("CZK", "cz");
        CURR_TO_CC.put("DJF", "dj");
        CURR_TO_CC.put("DKK", "dk");
        CURR_TO_CC.put("DOP", "doo");
        CURR_TO_CC.put("DZD", "dz");
        CURR_TO_CC.put("EGP", "eg");
        CURR_TO_CC.put("ERN", "er");
        CURR_TO_CC.put("ETB", "et");
        CURR_TO_CC.put("FJD", "fj");
        CURR_TO_CC.put("FKP", "fk");
        CURR_TO_CC.put("GEL", "ge");
        CURR_TO_CC.put("GGP", "gg");
        CURR_TO_CC.put("GHS", "gh");
        CURR_TO_CC.put("GIP", "gi");
        CURR_TO_CC.put("GMD", "gm");
        CURR_TO_CC.put("GNF", "gn");
        CURR_TO_CC.put("GTQ", "gt");
        CURR_TO_CC.put("GYD", "gy");
        CURR_TO_CC.put("HKD", "hk");
        CURR_TO_CC.put("HNL", "hn");
        CURR_TO_CC.put("HRK", "hr");
        CURR_TO_CC.put("HTG", "ht");
        CURR_TO_CC.put("HUF", "hu");
        CURR_TO_CC.put("IDR", "id");
        CURR_TO_CC.put("ILS", "il");
        CURR_TO_CC.put("IMP", "im");
        CURR_TO_CC.put("INR", "in");
        CURR_TO_CC.put("IQD", "iq");
        CURR_TO_CC.put("IRR", "ir");
        CURR_TO_CC.put("ISK", "is");
        CURR_TO_CC.put("JEP", "je");
        CURR_TO_CC.put("JMD", "jm");
        CURR_TO_CC.put("JOD", "jo");
        CURR_TO_CC.put("KES", "ke");
        CURR_TO_CC.put("KGS", "kg");
        CURR_TO_CC.put("KHR", "kh");
        CURR_TO_CC.put("KMF", "km");
        CURR_TO_CC.put("KPW", "kp");
        CURR_TO_CC.put("KRW", "kr");
        CURR_TO_CC.put("KWD", "kw");
        CURR_TO_CC.put("KYD", "ky");
        CURR_TO_CC.put("KZT", "kz");
        CURR_TO_CC.put("LAK", "la");
        CURR_TO_CC.put("LBP", "lb");
        CURR_TO_CC.put("LKR", "lk");
        CURR_TO_CC.put("LRD", "lr");
        CURR_TO_CC.put("LSL", "ls");
        CURR_TO_CC.put("LYD", "ly");
        CURR_TO_CC.put("MAD", "ma");
        CURR_TO_CC.put("MDL", "md");
        CURR_TO_CC.put("MGA", "mg");
        CURR_TO_CC.put("MKD", "mk");
        CURR_TO_CC.put("MMK", "mm");
        CURR_TO_CC.put("MNT", "mn");
        CURR_TO_CC.put("MOP", "mo");
        CURR_TO_CC.put("MRU", "mr");
        CURR_TO_CC.put("MUR", "mu");
        CURR_TO_CC.put("MVR", "mv");
        CURR_TO_CC.put("MWK", "mw");
        CURR_TO_CC.put("MXN", "mx");
        CURR_TO_CC.put("MYR", "my");
        CURR_TO_CC.put("MZN", "mz");
        CURR_TO_CC.put("NAD", "na");
        CURR_TO_CC.put("NGN", "ng");
        CURR_TO_CC.put("NIO", "ni");
        CURR_TO_CC.put("NOK", "no");
        CURR_TO_CC.put("NPR", "np");
        CURR_TO_CC.put("OMR", "om");
        CURR_TO_CC.put("PAB", "pa");
        CURR_TO_CC.put("PEN", "pe");
        CURR_TO_CC.put("PGK", "pg");
        CURR_TO_CC.put("PHP", "ph");
        CURR_TO_CC.put("PKR", "pk");
        CURR_TO_CC.put("PLN", "pl");
        CURR_TO_CC.put("PYG", "py");
        CURR_TO_CC.put("QAR", "qa");
        CURR_TO_CC.put("RON", "ro");
        CURR_TO_CC.put("RSD", "rs");
        CURR_TO_CC.put("RUB", "ru");
        CURR_TO_CC.put("RWF", "rw");
        CURR_TO_CC.put("SAR", "sa");
        CURR_TO_CC.put("SBD", "sb");
        CURR_TO_CC.put("SCR", "sc");
        CURR_TO_CC.put("SDG", "sd");
        CURR_TO_CC.put("SEK", "se");
        CURR_TO_CC.put("SGD", "sg");
        CURR_TO_CC.put("SHP", "sh");
        CURR_TO_CC.put("SLE", "sl");
        CURR_TO_CC.put("SOS", "so");
        CURR_TO_CC.put("SRD", "sr");
        CURR_TO_CC.put("SSP", "ss");
        CURR_TO_CC.put("STN", "st");
        CURR_TO_CC.put("SVC", "sv");
        CURR_TO_CC.put("SYP", "sy");
        CURR_TO_CC.put("SZL", "sz");
        CURR_TO_CC.put("THB", "th");
        CURR_TO_CC.put("TJS", "tj");
        CURR_TO_CC.put("TMT", "tm");
        CURR_TO_CC.put("TND", "tn");
        CURR_TO_CC.put("TOP", "to");
        CURR_TO_CC.put("TRY", "tr");
        CURR_TO_CC.put("TTD", "tt");
        CURR_TO_CC.put("TWD", "tw");
        CURR_TO_CC.put("TZS", "tz");
        CURR_TO_CC.put("UAH", "ua");
        CURR_TO_CC.put("UGX", "ug");
        CURR_TO_CC.put("UYU", "uy");
        CURR_TO_CC.put("UYW", "uy");
        CURR_TO_CC.put("UZS", "uz");
        CURR_TO_CC.put("VED", "ve");
        CURR_TO_CC.put("VES", "ve");
        CURR_TO_CC.put("VND", "vn");
        CURR_TO_CC.put("VUV", "vu");
        CURR_TO_CC.put("WST", "ws");
        CURR_TO_CC.put("XAF", "cm");
        CURR_TO_CC.put("XAG", null);
        CURR_TO_CC.put("XAU", null);
        CURR_TO_CC.put("XBA", null);
        CURR_TO_CC.put("XCD", "ag");
        CURR_TO_CC.put("XDR", null);
        CURR_TO_CC.put("XOF", "sn");
        CURR_TO_CC.put("XPD", null);
        CURR_TO_CC.put("XPF", "pf");
        CURR_TO_CC.put("XPT", null);
        CURR_TO_CC.put("XTS", null);
        CURR_TO_CC.put("XXX", null);
        CURR_TO_CC.put("YER", "ye");
        CURR_TO_CC.put("ZAR", "za");
        CURR_TO_CC.put("ZMW", "zm");
        CURR_TO_CC.put("ZWL", "zw");
        CURR_TO_CC.put("BTC", null);
        CURR_TO_CC.put("XBT", null);
        CURR_TO_CC.put("ETH", null);
    }

    public static String extractCurrencyCodeFromTitle(String title) {
        if (title == null) return null;
        int lastOpen = title.lastIndexOf("(");
        int lastClose = title.lastIndexOf(")");
        if (lastOpen != -1 && lastClose != -1 && lastClose > lastOpen) {
            return title.substring(lastOpen + 1, lastClose).trim().toUpperCase();
        }
        return null;
    }

    public static int getFlagResIdFromCurrency(Context ctx, String currencyCode) {
        if (currencyCode == null) return 0;

        String cc = CURR_TO_CC.get(currencyCode.toUpperCase());

        // Special handling: EUR packs often lack "eu"
        if (cc == null && "EUR".equalsIgnoreCase(currencyCode)) {
            // try EU first; if not present, try a Eurozone country
            int tryId = tryResolve(ctx, "eu");
            if (tryId != 0) return tryId;
            tryId = tryResolve(ctx, "de");
            if (tryId != 0) return tryId;
            tryId = tryResolve(ctx, "fr");
            if (tryId != 0) return tryId;
            tryId = tryResolve(ctx, "it");
            if (tryId != 0) return tryId;
            return tryResolve(ctx, "es");
        }


        if (cc == null) return 0;

        return tryResolve(ctx, cc);
    }

    private static int tryResolve(Context ctx, String cc) {
        if (cc == null) return 0;
        int id = ctx.getResources().getIdentifier(cc, "drawable", ctx.getPackageName());
        if (id != 0) return id;
        id = ctx.getResources().getIdentifier("flag_" + cc, "drawable", ctx.getPackageName());
        if (id != 0) return id;
        return ctx.getResources().getIdentifier(cc + "_flag", "drawable", ctx.getPackageName());
    }

    public static String getCountryCodeForCurrency(String currencyCode) {
        if (currencyCode == null) return null;
        String cc = CURR_TO_CC.get(currencyCode.toUpperCase());
        return cc; // may be null for metals/crypto/etc.
    }

    public static String getCountryNameForCurrency(android.content.Context ctx, String currencyCode) {
        String cc = getCountryCodeForCurrency(currencyCode);
        if (cc == null) return null;
        // Locale with country only gives you its display name (e.g., "China")
        java.util.Locale loc = new java.util.Locale("", cc.toUpperCase());
        String name = loc.getDisplayCountry();
        return (name == null || name.isEmpty()) ? null : name;
    }

}
