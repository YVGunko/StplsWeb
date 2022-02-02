package hello;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.swing.DefaultListModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;


public class utils {
	public class OutDocIdAndPartBoxNumber {

	    public OutDocIdAndPartBoxNumber(String id, int quantity) {
			super();
			this.outDocId = id;
			this.number = quantity;
		}
		String outDocId;
	    Integer number;
	}
	
	//private static final ZoneId DEFAULT_ZONE_ID = ZoneId.of("UTC");

		public static LocalDateTime startOfDay() {
			return LocalDateTime.now(ZoneId.systemDefault()).with(LocalTime.MIN);}
	
		public static LocalDateTime endOfDay() {
			return LocalDateTime.now(ZoneId.systemDefault()).with(LocalTime.MAX);}
	
		public static boolean belongsToCurrentDay(final LocalDateTime localDateTime) {
		return localDateTime.isAfter(startOfDay()) && localDateTime.isBefore(endOfDay());}
	
		//note that week starts with Monday
		public static LocalDateTime startOfWeek() {
			return LocalDateTime.now(ZoneId.systemDefault())
			.with(LocalTime.MIN)
			.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));}
	
		//note that week ends with Sunday
		public static LocalDateTime endOfWeek() {
			return LocalDateTime.now(ZoneId.systemDefault())
			.with(LocalTime.MAX)
			.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));}
	
		public static boolean belongsToCurrentWeek(final LocalDateTime localDateTime) {
			return localDateTime.isAfter(startOfWeek()) && localDateTime.isBefore(endOfWeek());}
	
		public static LocalDateTime startOfMonth() {
			return LocalDateTime.now(ZoneId.systemDefault())
			.with(LocalTime.MIN)
			.with(TemporalAdjusters.firstDayOfMonth());}
		
		public static LocalDateTime endOfMonth() {
			return LocalDateTime.now(ZoneId.systemDefault())
			.with(LocalTime.MAX)
			.with(TemporalAdjusters.lastDayOfMonth());
		}

		public static boolean belongsToCurrentMonth(final LocalDateTime localDateTime) {
			return localDateTime.isAfter(startOfMonth()) && localDateTime.isBefore(endOfMonth());
			}
		public static boolean belongsToCurrentMonth(final Date date) {
			return toLocalDate(date).isAfter(startOfMonth()) && toLocalDate(date).isBefore(endOfMonth());
			}
		public static boolean belongsToCurrentDay(final Date date) {
			return toLocalDate(date).isAfter(startOfDay()) && toLocalDate(date).isBefore(endOfDay());
			}
	
		public static long toMills(final LocalDateTime localDateTime) {
			return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();}
	
		public static Date toDate(final LocalDateTime localDateTime) {
			return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
		}
		public static LocalDateTime toLocalDate(final Date dateToConvert) {
		    return dateToConvert.toInstant()
		    	      .atZone(ZoneId.systemDefault())
		    	      .toLocalDateTime();
		    	}
		public static String toString(final LocalDateTime localDateTime) {
			return localDateTime.format(DateTimeFormatter.ISO_DATE_TIME);
		}
		public static String toStringOnlyDate(final LocalDateTime localDateTime) {
			return localDateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
		}

		public static LocalDateTime startOfMonth(Date dateToConvert) {
		    return dateToConvert.toInstant()
		      .atZone(ZoneId.systemDefault())
		      .toLocalDateTime()
		      .with(LocalTime.MIN)
		      .with(TemporalAdjusters.lastDayOfMonth());
		}
		
		public static LocalDateTime endOfMonth(Date dateToConvert) {
		    return dateToConvert.toInstant()
		      .atZone(ZoneId.systemDefault())
		      .toLocalDateTime()
		      .with(LocalTime.MAX)
		      .with(TemporalAdjusters.lastDayOfMonth());
		}
		
	   public static Date getStartOfDay(Date date) {
	       return org.apache.commons.lang3.time.DateUtils.truncate(date, Calendar.DATE);
	   }
	   public static Date getEndOfDay(Date date) {
	       return org.apache.commons.lang3.time.DateUtils.truncate(date, Calendar.DATE);
	   }
	   public static Date atStartOfDay(Date date) {
		    LocalDateTime localDateTime = dateToLocalDateTime(date);
		    LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
		    return localDateTimeToDate(startOfDay);
		}

		public static Date atEndOfDay(Date date) {
		    LocalDateTime localDateTime = dateToLocalDateTime(date);
		    LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
		    return localDateTimeToDate(endOfDay);
		}

		private static LocalDateTime dateToLocalDateTime(Date date) {
		    return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
		}

		private static Date localDateTimeToDate(LocalDateTime localDateTime) {
		    return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
		}
		
		public static boolean empty( final String s ) {
			  // Null-safe, short-circuit evaluation.
			  return s == null || s.trim().isEmpty();
			}

		public static Date addDays(Date date, int days){
		        Calendar cal = Calendar.getInstance();
		        cal.setTime(date);
		        cal.add(Calendar.DATE, days); //minus number would decrement the days
		        return cal.getTime();
		    }
		public static Date lastDayOfMonth(Date date){
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(date);
	        cal.set(Calendar.DAY_OF_MONTH, 31);
	        return cal.getTime();
	    }
		public static Date getStartOfYear(){
			Calendar cal = Calendar.getInstance();

			cal.set(Calendar.DAY_OF_YEAR, 1);
			return cal.getTime();
		}
		public static Date addYears(Date date, int years)
	    {
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(date);
	        cal.add(Calendar.YEAR, years); //minus number would decrement the days
	        return cal.getTime();
	    }
		public static Date sDateToDate (String dateInString, String format) throws ParseException {
			SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());
			return (formatter.parse(dateInString));
		}
		
	    public static String ListToStr(DefaultListModel list, String delimiter) {
	        String result = "";
	        for (int i = 0; i < list.size(); i++) {
	            result = list.getElementAt(i).toString() + delimiter;
	        }
	        result = result.trim();
	        return result;
	    }
}
