package mapper;
import javax.xml.transform.*;

public class XSLTExample {

   public static void main(String[] args) throws TransformerException {
	   String xml = 
"<?xml version=\"1.0\"?>" +
 "<howto>" +
 "<topic>" +
     "<title>Java</title>" +
     "<url>http://www.rgagnon/javahowto.htm</url>" +
 "</topic>" +
   "<topic>" +
     "<title>PowerBuilder</title>" +
     "<url>http://www.rgagnon/pbhowto.htm</url>"+
 "</topic>"+
     "<topic>"+
       "<title>Javascript</title>"+
       "<url>http://www.rgagnon/jshowto.htm</url>"+
 "</topic>"+
     "<topic>"+
       "<title>VBScript</title>"+
       "<url>http://www.rgagnon/vbshowto.htm</url>"+
 "</topic>"+
 "</howto>";
	   
	   String xsl =
   "<?xml version='1.0'?>" +
   "<xsl:stylesheet xmlns:xsl='http://www.w3.org/1999/XSL/Transform' version='1.0'>" +
   "<xsl:template match='/'>" +
   "<html>" +
     "<head><title>HowTo</title></head>" +
     "<body>" +
     "<table border='1'>" +
        "<tr>" +
            "<th>Title</th>" +
            "<th>URL</th>" +
        "</tr>" +
        "<xsl:for-each select='howto/topic'>" +
            "<tr>" +
                "<td><xsl:value-of select='title'/></td>" + 
                "<td><xsl:value-of select='url'/></td>" +
            "</tr>" +
        "</xsl:for-each>" +
     "</table>" +
   "</body></html>" +
   "</xsl:template>" +
   "</xsl:stylesheet>".replaceAll("'", "\"")
 ;
	   
	   System.out.println(XSLT.doTransform(xml,xsl));
   }
}
