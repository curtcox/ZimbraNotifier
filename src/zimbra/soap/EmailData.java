package zimbra.soap;
/**
 *
 *    Zimbra Tray - small notification app for Zimbra Collaboration Suite.
 *
 *
 *    All intellectual property rights in the Software are protected by
 *    international copyright laws.
 *
 *
 *    Please make sure that third-party modules and libraries are used
 *    according to their respective licenses.
 *
 *    Any modifications to this package must retain all copyright notices
 *    of the original copyright holder(s) for the original code used.
 *
 *    After any such modifications, the original code will still remain
 *    copyrighted by the copyright holder(s) or original author(s).
 *
 *
 *     Copyright (C) 2009 Andrew Orlov
 *     mail:	     andrew.v.orlov@gmail.com
 *
 *
 *     This program is free software; you can redistribute it and/or modify it
 *     under the terms of the GNU General Public License as published by the Free
 *     Software Foundation; either version 2 of the License, or (at your option)
 *     any later version.
 *
 *     This program is distributed in the hope that it will be useful, but
 *     WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 *     or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 *     for more details.
 *
 *     You should have received a copy of the GNU General Public License along
 *     with this program; if not, write to the Free Software Foundation, Inc., 59
 *     Temple Place, Suite 330, Boston, MA 02111-1307 USA
 *
 *
 */




import java.util.HashMap;
import java.util.Map;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPElement;

/**
 *
 * @author Andrew Orlov
 */
public final class EmailData {

    
    final String nodeName;
    final Map<String,String> values = new HashMap<String,String>();

    static EmailData of(String nodeName, Map<String,String> values) {
        return new EmailData(nodeName,values);
    }

    static EmailData of(SOAPElement element) {
        String nodeName = element.getNodeName();
        Map<String,String> values = new HashMap<String,String>();
        values.put("value", element.getValue());
        for (Name attribute : Each.of(element)) {
            values.put(attribute.getLocalName(),element.getAttributeValue(attribute));
        }
        return new EmailData(nodeName,values);
    }
    
    private EmailData(String nodeName, Map<String,String> values) {
        this.nodeName = nodeName;
        this.values.putAll(values);
    }
    
    @Override
    public String toString() {
        return "<Email>" + nodeName + ":" + values.toString() + "</Email>";
    }
}
