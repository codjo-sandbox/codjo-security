package net.codjo.security.common.message;

import com.thoughtworks.xstream.converters.basic.ThreadSafeSimpleDateFormat;
import net.codjo.security.common.message.XmlCodec;
import net.codjo.security.common.message.XmlCodec.DateFormatter;

import java.text.ParseException;
import java.util.Date;
import java.util.TimeZone;

public class DateFormatterFactory {
    public static DateFormatter createDateFormatter(String format, int initialPoolSize, int maxPoolSize) {
        return new XStream_1_2_Formatter(format, initialPoolSize, maxPoolSize);
    }

    private static class XStream_1_2_Formatter implements DateFormatter {
        private final ThreadSafeSimpleDateFormat dateFormat;

        private XStream_1_2_Formatter(String format, int initialPoolSize, int maxPoolSize) {
            dateFormat = new ThreadSafeSimpleDateFormat(format, initialPoolSize, maxPoolSize);
        }

        public String format(Date obj) {
            return dateFormat.format(obj);
        }

        public Object parse(String str) throws ParseException {
            return dateFormat.parse(str);
        }
    }
}
