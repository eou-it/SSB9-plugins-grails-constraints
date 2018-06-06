import net.zorched.constraints.ComparisonConstraint
import org.apache.commons.logging.LogFactory
import org.junit.Test

class ComparisonTests  {

    @Test
    void testComparisonOfMatchingWorks() {
        def v = getConstraint()

        assert v.validate("xxx", new TestObj(other:"xxx"))
    }
    @Test
    void testComparisonOfNotMatchingWorks() {
        def v = getConstraint()

        assert ! v.validate("yyy", new TestObj(other:"xxx"))
    }

    @Test
    void testComparisonOfNumber() {
        def v = getConstraint()

        assert v.validate(1, new TestObj(other: 1))
        assert ! v.validate(1, new TestObj(other: 2))
    }

    @Test
    void testComparisonOfNullValues() {
        def v = getConstraint()

        assert ! v.validate(null, new TestObj(other:"xxx"))
        assert ! v.validate(null, new TestObj(other:null))
        assert ! v.validate("xxx", new TestObj(other:null))
    }

    def log = LogFactory.getLog("xxx")
    private def getConstraint() {
        def v = new ComparisonConstraint()
        v.metaClass.getParams = {-> "other" }
        v.metaClass.getLog = {-> return log }

        return v
    }
}

class TestObj {
    def other
}
