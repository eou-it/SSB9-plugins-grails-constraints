package net.zorched.test

class CFoo {
    String bar
    List baz

    static hasMany = [
            baz: String
    ]

    static constraints = {
        bar(twoLong: true, nullable:true)
        baz(twoLong: true)
    }
}
