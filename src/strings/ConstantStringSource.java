package strings;

import java.util.Iterator;

/**
 * Produces the same String over and over.
 */
final class ConstantStringSource
    extends AbstractStringSource
{

    static Iterator<ForkedString> of(ForkedString value) {
        return new ConstantStringSource(value);
    }

    final ForkedString value;

    private ConstantStringSource(ForkedString value) {
        this.value = value;
    }

    @Override
    public ForkedString next() {
        return value;
    }

}
