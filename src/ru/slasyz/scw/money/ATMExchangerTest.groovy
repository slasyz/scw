package ru.slasyz.scw.money

class ATMExchangerTest extends GroovyTestCase {
    void setUp() {
        super.setUp()
    }

    void testSmallExchange() {
        int[] coinList = [1, 2, 3, 4, 5]

        def atm = new ATMExchanger(coinList)

        assertEquals(7, atm.getExchangeList(5).size())
    }

    void testBigExchange() {
        int[] coinList = [1, 2, 3, 4, 5]

        def atm = new ATMExchanger(coinList)

        assertEquals(643287, atm.getExchangeList(200).size())
    }
}