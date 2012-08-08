package notify;

import java.util.Iterator;
import mapper.NullMapper;
import mapper.StringMapper;
import strings.ForkedString;
import util.All;


/**
 * Periodically polls for a new message and transform.
 * Whenever the result changes, a notification is made.
 * @author curt
 */
public final class TimedNotifier 
    implements Iterable<ForkedString>, Iterator<ForkedString>, Notifiable
{

    ForkedString value = ForkedString.EMPTY; // The current transformed value
    final Notifiable notifier; // Who we notify
    final Iterator<ForkedString<?>> messages; // Get new messages from here
    final Iterable<Boolean> running; // Run as long as this returns true
    final ChangeDetector detector; // This transform is used to determine equivalence
    final StringMapper transformer; // This transform is used to display

    private TimedNotifier(Builder builder) {
        this.running = builder.running;
        this.notifier = builder.notifiable;
        this.detector = builder.detector;
        this.transformer = builder.transformer;
        this.messages = builder.messages;
    }

    @Override
    public Iterator iterator() {
        return this;
    }

    public static class Builder {
        Iterable<Boolean> running = All.TRUE;
        Notifiable notifiable = NotifiableList.of();
        ChangeDetector detector = PartitioningChangeDetector.of(NullMapper.of());
        StringMapper transformer = NullMapper.of();
        Iterator<ForkedString<?>> messages;

        public TimedNotifier build() {
            return new TimedNotifier(this);
        }

        public Builder running(Iterable<Boolean> running) {
            this.running = running;
            return this;
        }
        public Builder onChangeNotify(Notifiable notifiable) {
            this.notifiable = notifiable;
            return this;
        }
        public Builder detector(ChangeDetector changeDetector) {
            this.detector = changeDetector;
            return this;
        }
        public Builder transformer(StringMapper transformer) {
            this.transformer = transformer;
            return this;
        }
        public Builder messages(Iterator<ForkedString<?>> messages) {
            this.messages = messages;
            return this;
        }
    }

    public static Builder builder() {
        return new Builder();	
    }
	
    @Override
    public void deliverNotification(ForkedString notification) {
        notifier.deliverNotification(transformer.transform(value));
    }

    @Override
    public boolean hasNext() {
        return messages.hasNext();
    }

    @Override
    public ForkedString next() {
    	ForkedString newValue = messages.next();
    	if (detector.changed(value,newValue)) {
            value = newValue;
            deliverNotification(null);
    	}
        return newValue;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}
