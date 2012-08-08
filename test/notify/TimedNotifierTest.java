package notify;

import notify.NotifiableList;
import notify.TimedNotifier;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import mapper.NullMapper;
import strings.ForkedString;
import org.junit.Test;


public class TimedNotifierTest {

    @Test
    public void run_returns_if_not_running() {
        NotifiableList notifier = NotifiableList.of();
        ForkedString message = ForkedString.of("foo");
        TimedNotifier timedNotifier = TimedNotifier.builder()
            .running(trues(0))
            .onChangeNotify(notifier)
            .transformer(NullMapper.of())
            .messages(list(message))
            .build();		

        for (Object o : timedNotifier) {}
    }

    @Test
    public void run_when_running_notifies() {
        NotifiableList notifier = NotifiableList.of();
        ForkedString message = ForkedString.of("foo");
        TimedNotifier timedNotifier = TimedNotifier.builder()
            .running(trues(1))
            .onChangeNotify(notifier)
            .transformer(NullMapper.of())
            .messages(list(message))
            .build();		

        for (Object o : timedNotifier) {}

        assertEquals(1,notifier.list.size());
        assertEquals(message,notifier.list.get(0));
    }

    @Test
    public void run_only_notifies_once_when_message_is_the_same() {
        NotifiableList notifier = NotifiableList.of();
        ForkedString message = ForkedString.of("foo");

        TimedNotifier timedNotifier = TimedNotifier.builder()
            .running(trues(2))
            .onChangeNotify(notifier)
            .transformer(NullMapper.of())
            .messages(list(message,message))
            .build();		

        for (Object o : timedNotifier) {}

        assertEquals(1,notifier.list.size());
        assertEquals(message,notifier.list.get(0));
    }

    @Test
    public void run_notifies_twice_when_message_is_different() {
        NotifiableList notifier = NotifiableList.of();
        ForkedString message1 = ForkedString.of("foo");
        ForkedString message2 = ForkedString.of("bar");

        TimedNotifier timedNotifier = TimedNotifier.builder()
            .running(trues(2))
            .onChangeNotify(notifier)
            .transformer(NullMapper.of())
            .messages(list(message1,message2))
            .build();		

        for (Object o : timedNotifier) {}

        assertEquals(2,notifier.list.size());
        assertEquals(message1,notifier.list.get(0));
        assertEquals(message2,notifier.list.get(1));
    }

    ForkedString forked(String... lines) {
        return ForkedString.forked(lines);
    }
    Iterator list(ForkedString... values) {
        List<ForkedString> list = new ArrayList<ForkedString>();
        for (ForkedString value : values) {
            list.add(value);
        }
        return list.iterator();
    }

    Iterable<Boolean> trues(int count) {
        List<Boolean> list = new ArrayList<Boolean>();
        for (int i=0; i<count; i++) {
            list.add(true);
        }
        return list;
    }

    Iterator<ForkedString> blanks(int count) {
        List<ForkedString> list = new ArrayList<ForkedString>();
        for (int i=0; i<count; i++) {
            list.add(ForkedString.of(""));
        }
        return list.iterator();
    }

}
