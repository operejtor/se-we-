package pl.plneconomy.item;

import java.util.Arrays;

public enum Denomination {
    GR_1(1, "1gr"),
    GR_2(2, "2gr"),
    GR_5(5, "5gr"),
    GR_10(10, "10gr"),
    GR_20(20, "20gr"),
    GR_50(50, "50gr"),
    ZL_1(100, "1zl"),
    ZL_2(200, "2zl"),
    ZL_5(500, "5zl"),
    ZL_10(1000, "10zl"),
    ZL_20(2000, "20zl"),
    ZL_50(5000, "50zl"),
    ZL_100(10000, "100zl"),
    ZL_200(20000, "200zl"),
    ZL_500(50000, "500zl");

    private final long grosze;
    private final String key;

    Denomination(long grosze, String key) {
        this.grosze = grosze;
        this.key = key;
    }

    public long grosze() {
        return grosze;
    }

    public String key() {
        return key;
    }

    public static String fmt(long grosze) {
        long zl = grosze / 100;
        long gr = Math.abs(grosze % 100);
        return String.format("%d,%02d zł", zl, gr);
    }

    public static long parse(String value) {
        String cleaned = value.trim().replace("zł", "").replace(" ", "").replace(',', '.');
        if (cleaned.isBlank()) {
            throw new IllegalArgumentException("Empty amount");
        }
        String[] split = cleaned.split("\\.");
        long zl = Long.parseLong(split[0]);
        long gr = split.length > 1 ? Long.parseLong((split[1] + "00").substring(0, 2)) : 0;
        return zl * 100 + (zl < 0 ? -gr : gr);
    }

    public static Denomination byKey(String key) {
        return Arrays.stream(values()).filter(d -> d.key.equals(key)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown denomination: " + key));
    }
}
