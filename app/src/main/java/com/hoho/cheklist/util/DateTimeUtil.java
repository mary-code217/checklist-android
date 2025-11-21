package com.hoho.cheklist.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public final class DateTimeUtil {
    // 날짜+시간 (DB created_at, updated_at 용)
    private static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    // 날짜만 (점검일시 기본값 등에 쓸 수 있음)
    private static final String DATE_PATTERN = "yyyy-MM-dd";

    // 한국 시간대(KST)
    private static final TimeZone KST = TimeZone.getTimeZone("Asia/Seoul");

    private DateTimeUtil() {
    }

    /**
     * 현재 시각을 "yyyy-MM-dd HH:mm:ss" 포맷 문자열로 반환
     * 예) 2025-11-21 13:45:12
     */
    public static String nowDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat(DATETIME_PATTERN, Locale.KOREA);
        sdf.setTimeZone(KST);
        return sdf.format(new Date());
    }

    /**
     * 오늘 날짜를 "yyyy-MM-dd" 포맷 문자열로 반환
     * 예) 2025-11-21
     */
    public static String nowDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN, Locale.KOREA);
        sdf.setTimeZone(KST);
        return sdf.format(new Date());
    }
}
