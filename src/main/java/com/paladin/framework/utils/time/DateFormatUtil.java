package com.paladin.framework.utils.time;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateFormatUtil {

	private final static Map<String, ThreadLocal<SimpleDateFormat>> threadLocalMap = new HashMap<>();

	private static ThreadLocal<SimpleDateFormat> getThreadLocal(final String format) {

		ThreadLocal<SimpleDateFormat> threadLocal = threadLocalMap.get(format);
		
		if (threadLocal == null) {
			synchronized (threadLocalMap) {
				threadLocal = threadLocalMap.get(format);
				if (threadLocal == null) {
					threadLocal = new ThreadLocal<SimpleDateFormat>() {
						public SimpleDateFormat initialValue() {
							return new SimpleDateFormat(format);
						}
					};
					threadLocalMap.put(format, threadLocal);
				}
			}
		}

		return threadLocal;
		
	}

	/**
	 * 通过线程变量创建并获取安全的{@link SimpleDateFormat}
	 * @param format
	 * @return
	 */
	public static SimpleDateFormat getThreadSafeFormat(String format) {
		return getThreadLocal(format).get();
	}

	/**
	 * Date转换为LocalDateTime
	 * @param date
	 */
	public static LocalDateTime date2LocalDateTime(Date date){
		Instant instant = date.toInstant();
		ZoneId zoneId = ZoneId.systemDefault();
		return instant.atZone(zoneId).toLocalDateTime();
	}

	/**
	 * LocalDateTime转换为Date
	 * @param localDateTime
	 */
	public static Date localDateTime2Date( LocalDateTime localDateTime){
		ZoneId zoneId = ZoneId.systemDefault();
		ZonedDateTime zdt = localDateTime.atZone(zoneId);
		return Date.from(zdt.toInstant());
	}

}
