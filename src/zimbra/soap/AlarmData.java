package zimbra.soap;

import javax.xml.soap.SOAPElement;


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



/**
 *
 * @author Andrew Orlov
 */
final class AlarmData {
    
    final long appointmentInvId;
    final long invId;
    final long nextAlarm;
    final long alarmInstStart;
    final String name;
    final String loc;

    public AlarmData(SOAPElement se) {
        if (!"alarmData".equals(se.getNodeName())) {
            throw new IllegalArgumentException("Wrong SOAP element");
        }
        appointmentInvId = Long.parseLong(se.getParentElement().getAttribute("id"));
        invId            = Long.parseLong(se.getAttribute("invId"));
        nextAlarm        = Long.parseLong(se.getAttribute("nextAlarm"));
        alarmInstStart   = Long.parseLong(se.getAttribute("alarmInstStart"));
        name             = se.getAttribute("name");
        loc              = se.getAttribute("loc");
    }

    @Override
    public String toString() {
    	return "<alarm>" +
                   " app id= " + appointmentInvId + 
                   " inv id=" + invId +
                   " next alarm =" + nextAlarm +
                   " alarm start=" + alarmInstStart +
                   " name=" + name +
                   " loc=" + loc +
    	       "</alarm>";
    }
}
