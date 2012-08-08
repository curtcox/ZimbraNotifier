package notify;

import mapper.StringMapper;
import strings.ForkedString;

/**
 * Signify change when there are any new parts.
 * @author Curt
 */
public final class PartitioningChangeDetector
    implements ChangeDetector
{

    final StringMapper partitioner;

    private PartitioningChangeDetector(StringMapper partitioner) {
        this.partitioner = partitioner;
    }

    public static PartitioningChangeDetector of(StringMapper partitioner) {
        return new PartitioningChangeDetector(partitioner);
    }
    
    @Override
    public boolean changed(ForkedString old, ForkedString newValue) {
    	return !partitioner.transform(old).parts.containsAll(
                partitioner.transform(newValue).parts);
    }


}
