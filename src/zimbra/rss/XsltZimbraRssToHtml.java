package zimbra.rss;

import javax.xml.transform.TransformerException;
import mapper.StringMapper;
import mapper.XSLT;
import strings.ForkedString;

public final class XsltZimbraRssToHtml 
   implements StringMapper
{

   final XSLT xslt = new XSLT(TRANSFORM);
   
static final String TRANSFORM = 
"<?xml version='1.0'?>" +
   "<xsl:stylesheet xmlns:xsl='http://www.w3.org/1999/XSL/Transform' version='1.0'>" +
   "<xsl:template match='/'>" +
	   "<html>" +
		   "<body>" +
		    "<xsl:for-each select='rss/channel/item'>" +
		    "<xsl:sort select='pubDate' order='descending' />" +
			    "<xsl:if test='not(position() > 15)'>" + 
		            "<b><xsl:value-of select='author'/></b> -- " +
		            "<xsl:value-of select='pubDate'/> <br/>" +
			        "<b><xsl:value-of select='title'/></b> <br/>" +
		            "<xsl:value-of select='description'/>" +
			        "<br/><br/>" +
			   "</xsl:if>" +
		   "</xsl:for-each>" +
		   "</body>" +
	   "</html>" +
   "</xsl:template>" +
   "</xsl:stylesheet>"
;
     
   public static void main(String[] args) throws TransformerException {
       System.out.println(new XsltZimbraRssToHtml().transform(ZimbraRssXmlSample.SAMPLE));
   }

    @Override
    public ForkedString transform(ForkedString string) {
            return stripExtraHtml(xslt.transform(string));
    }

    /**
     * Strip any HTML that confuses the Swing HTML component
     */
    ForkedString stripExtraHtml(ForkedString html) {
        return ForkedString.of(html.string.replace(q("<META http-equiv='Content-Type' content='text/html; charset=UTF-8'>"), ""));	
    }

    static String q(String text) {
            return text.replaceAll("'", "\"");
    }
}
