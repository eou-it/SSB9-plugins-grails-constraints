package net.zorched.test

import grails.validation.Validateable

/**
 * net.zorched.test
 * User: geoff
 * Date: Jan 7, 2010
 * Time: 8:28:25 PM
 */
public class AddressAnno implements Validateable {
    String street
    String zip
    String phone

    static constraints = {
        street(blank: false)
        zip(usZip: true)
        phone(usPhone: true)
    }
}
