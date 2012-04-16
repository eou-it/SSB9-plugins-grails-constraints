package net.zorched.test

class CFoo {
    String bar
    List baz

    static constraints = {
        bar(twoLong: true, nullable:true)
        baz(twoLong: true)
    }
}
