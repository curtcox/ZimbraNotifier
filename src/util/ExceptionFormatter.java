package util;

import java.util.Arrays;
import strings.ForkedString;

public final class ExceptionFormatter {

    public final static String HEADER = tr(th("class") + th("method") + th("line"));

    public static ForkedString format(Throwable e) {
            e.printStackTrace();
            StringBuilder out = new StringBuilder();
            out.append(h(e.getMessage()));
            out.append("<table>");
            out.append(HEADER);
            for (StackTraceElement element : e.getStackTrace()) {
                out.append(
                    tr(
                        td(element.getClassName()) +
                        td(element.getMethodName()) +
                        td(element.getLineNumber()+"")
                ));	
            }
            out.append("</table>");
            return ForkedString.of(out.toString(),Arrays.asList(e.getStackTrace()),StackTraceElement.class);
    }

    static String td(String text) { return "<td>" + text + "</td>"; }
    static String tr(String text) { return "<tr>" + text + "</tr>"; }
    static String th(String text) { return "<th>" + text + "</th>"; }
    static String h(String text) { return "<h3>" + text + "</h3>"; }
}
