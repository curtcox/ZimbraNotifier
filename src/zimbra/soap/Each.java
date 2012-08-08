package zimbra.soap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPElement;

/**
 *
 * @author Curt
 */
final class Each {
    
    static List<SOAPElement> of(Iterator<?> iterator) {
        List<SOAPElement> list = new ArrayList<SOAPElement>();
        while (iterator.hasNext()) {
            Object next = iterator.next();
            if (next instanceof SOAPElement) {
                list.add((SOAPElement)next);
            }
        }
    
        return list;
    }

    static List<Name> of(SOAPElement se) {
        List<Name> list = new ArrayList<Name>();
        Iterator<Name> iterator = se.getAllAttributes();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
    
        return list;
    }

}
