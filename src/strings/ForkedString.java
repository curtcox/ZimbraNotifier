package strings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import util.Check;

/**
 * A String and alternate representations of it.
 * For instance, an address could be one big string, or it
 * could be a list of address parts.
 */
public final class ForkedString<T> {

    public final String string;
    public final List<T> parts;
    public final Class type;
    
    public static final ForkedString EMPTY = new ForkedString("",Collections.EMPTY_LIST, String.class);
    
    private ForkedString(String string, List<T> parts, Class type) {
    	this.string = Check.notNull(string);
    	this.parts = immutable(parts, type);
        this.type = type;
    }

    private List<T> immutable(List<T> parts, Class type) {
        List<T> copy = new ArrayList();
        for (T part : parts) {
            copy.add((T) type.cast(part));
        }
        return Collections.unmodifiableList(copy);
    }
    
    public static ForkedString of(String string) {
    	return new ForkedString(string,Collections.singletonList(string), String.class);
    }

    public static <T> ForkedString<T> fromList(Class type, List<T> list) {
        StringBuilder out = new StringBuilder();
        for (Object part : list) {
            out.append("" + part);
        }
        return new ForkedString(out.toString(),copy(list),type);
    }

    public static <T> ForkedString<T> from(Class type, T... values) {
        StringBuilder out = new StringBuilder();
        for (Object part : values) {
            out.append("" + part);
        }
        return new ForkedString(out.toString(),copy(Arrays.asList(values)),type);
    }

    public static ForkedString of(String string, List<?> list, Class type) {
        return new ForkedString(string,list,type);
    }

    @Override
    public String toString() {
        return string;
    }
	
   public static ForkedString forked(String... lines) {
       List<String> replaced = new ArrayList<String>();
       for (String line : Check.notNull(lines)) {
           replaced.add(line.replaceAll("'", "\""));
       }
       return ForkedString.fromList(String.class,replaced);   
   }
   
   static <T> List<T> copy(List<T> original) {
       List<T> copy = new ArrayList<T>();
       copy.addAll(original);
       return copy;
   }
   
   @Override
   public boolean equals(Object object) {
       ForkedString other = (ForkedString) object;
       return string.equals(other.string) && 
              parts.equals(other.parts);
   }

    @Override
    public int hashCode() {
        return string.hashCode() ^ parts.hashCode();
    }

}
