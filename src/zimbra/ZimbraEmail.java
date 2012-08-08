package zimbra;

import java.util.Date;
import util.Check;

public final class ZimbraEmail 
    implements Comparable<ZimbraEmail>
{

    public final String author;
    public final String title;
    public final long date;
    public final String description;
    public final boolean old;

    private ZimbraEmail(String author, String title, long date, String description, boolean old) {
        this.author = Check.notNull(author);
        this.title = Check.notNull(title);
        this.date = date;
        this.description = Check.notNull(description);
        this.old = old;
    }

    public static ZimbraEmail of(String author, String title, long date, String description) {
        return new ZimbraEmail(author,title,date,description,false);
    }

    ZimbraEmail withOld() {
        return new ZimbraEmail(author,title,date,description,true);
    }

    @Override
    public boolean equals(Object other) {
        if (other==this) {
            return true;
        }
        if (!(other instanceof ZimbraEmail)) {
            return false;
        }
        ZimbraEmail that = (ZimbraEmail) other;
        return author.equals(that.author) &&
               title.equals(that.title) &&
               date == that.date &&
               old == that.old &&
               description.equals(that.description);
    }

    boolean isSameAs(ZimbraEmail that) {
        return author.equals(that.author) &&
               title.equals(that.title) &&
               date == that.date &&
               description.equals(that.description);
    }
    
    @Override
    public int hashCode() {
        return author.hashCode() ^
               title.hashCode() ^
               (int) date ^
               description.hashCode();
    }
    
    @Override
    public int compareTo(ZimbraEmail that) {
        long diff = date - that.date;
        if (diff>0) {
            return 1;
        } 
        if (diff<0) {
            return -1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return
            " author=" + author +
            " title="  + title +
            " date="   + new Date(date) +
            " description=" + description
        ;
    }
	
}
