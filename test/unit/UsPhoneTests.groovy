import net.zorched.constraints.UsPhoneConstraint
import org.apache.commons.logging.LogFactory
import org.junit.Test

class UsPhoneTests {

    @Test
    void testPhoneValidationNotCalledIfFalsePassedAsParam() {
        def v = getConstraint(false)
        
        assert v.validate("555-555-1212")
        assert v.validate("")
        assert v.validate( null)
    }

    @Test
    void testValidUsPhones() {
        def v = getConstraint(true)
        
        assert v.validate("555-555-1212")
        assert v.validate("1-800-555-1212")
    }

    @Test
    void testInvalidUsPhones() {
        def v = getConstraint(true)
        
        assert ! v.validate("555-555-12123")
        assert ! v.validate("1-800-5553-1212")
        assert ! v.validate("1-800-5553-xxxx")
        assert ! v.validate("         ")
        assert ! v.validate("")
        assert ! v.validate(null)
    }

    def log = LogFactory.getLog("xxx")
    private def getConstraint(boolean param) {
        def v = new UsPhoneConstraint()
        v.metaClass.getParams = {-> param }
        v.metaClass.getLog = {-> return log }
        return v
    }
}
