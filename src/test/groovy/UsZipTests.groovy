import net.zorched.constraints.UsZipConstraint
import org.apache.commons.logging.LogFactory
import org.junit.Test

class UsZipTests {

    @Test
    void testValidationNotCalledIfFalsePassedAsParam() {
        def v = getConstraint(false)
        
        assert v.validate("55512")
        assert v.validate("")
        assert v.validate( null)
    }

    @Test
    void testValidatesFiveZip() {
        def v = getConstraint(true)
        
        assert v.validate("53212")
        assert v.validate("00000")
    }

    @Test
    void testValidatesFivePlus4Zip() {
        def v = getConstraint(true)

        assert v.validate("53212-4567")
        assert v.validate("00000-5678")
    }

    @Test
    void testDoesntValidateInvalidZips() {
        def v = getConstraint(true)

        assert ! v.validate("5321")
        assert ! v.validate("53215-456")
        assert ! v.validate("53215-")
        assert ! v.validate("53215-45678")
        assert ! v.validate("056789")
        assert ! v.validate("")
        assert ! v.validate("       ")
        assert ! v.validate(null)
    }

    def log = LogFactory.getLog("xxx")
    private def getConstraint(boolean param) {
        def v = new UsZipConstraint()
        v.metaClass.getParams = {-> param }
        v.metaClass.getLog = {-> return log }
        return v
    }
}
