package mapper;
import strings.ForkedString;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;

import java.io.*;

public final class XSLT 
    implements StringMapper
{

    final String transformation;

    public XSLT(String transformation) {
        this.transformation = transformation;
    }

    static String doTransform(String xml, String xsl) throws TransformerException {
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer(
                    new StreamSource(new ByteArrayInputStream(xsl.getBytes())));

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        transformer.transform(
            new StreamSource(new ByteArrayInputStream(xml.getBytes())),
            new StreamResult(bytes));

        return bytes.toString();
    }

    @Override
    public ForkedString transform(ForkedString fork) {
        String source = fork.string;
        try {
            return ForkedString.of(doTransform(source,transformation));
        } catch (TransformerException e) {
            String message = "transforming <<" + source + ">> with " + transformation;
            throw new RuntimeException(message,e);
        }
    }	

}
