package zimbra.rss;

import strings.ForkedString;

final class ZimbraRssXmlSample {
    
    static final ForkedString SAMPLE = ForkedString.forked( 
        "<?xml version='1.0'?>",
        "<rss version='2.0'>",
          "<channel>",
               "<title>Zimbra inbox</title>",
               "<link>http://www.zimbra.com</link>",
               "<description>Zimbra item inbox in RSS format.</description>",
               "<generator>Zimbra RSS Feed Servlet</generator>",
               "<item>",
                   "<title>JRebel takes off, lands on more expensive planet</title>",
                   "<description>Hey Curt, Just wanted to remind you that in only a few short weeks, JRebel is going through that price change we announced last month. Since that ...</description>", 
                   "<author>jrebel@zeroturnaround.com</author>",
                   "<pubDate>Fri, 19 Aug 2011 07:17:06 -0500</pubDate>",
               "</item>",
               "<item>",
                   "<title>Andi at Seibert tomorrow (8/24/11)</title>",
                   "<description>Hey guys, Tomorrow Andi will be here from 11am-2:45pm. Please stop by if you have any question for her J If I don't see you - have a great weekend! ...</description>" ,
                   "<author>'Jenna Dixon' &lt;jenna.dixon@asolutions.com></author>" ,
                   "<pubDate>Tue, 23 Aug 2011 14:02:08 -0500</pubDate>" ,
               "</item>" ,
                "</channel>" ,
           "</rss>" 
        );  	

}
