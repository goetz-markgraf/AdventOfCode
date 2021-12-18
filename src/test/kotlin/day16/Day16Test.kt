package day16

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

class Day16Test {
    @Test
    fun `shall read bits from a hex string`() {
        val input = "F"
        val parser = BitwiseReader(input.reader())

        val actual1 = parser.getBits(1)
        assertThat(actual1).isEqualTo(1)

        val actual2 = parser.getBits(2)
        assertThat(actual2).isEqualTo(3)

        val actual3 = parser.getBits(3)
        assertThat(actual3).isEqualTo(-1)
    }

    @Test
    fun `shall read a literal packet`() {
        val input = "D2FE28"
        val parser = BitwiseReader(input.reader())

        val version = parser.getBits(3)
        val type = parser.getBits(3)
        assertThat(version).isEqualTo(6)
        assertThat(type).isEqualTo(4)

        val num1complete = parser.getBits(5)
        val goOne1 = num1complete and 16
        val num1 = num1complete and 15

        assertThat(goOne1).isGreaterThan(0)
        assertThat(num1).isEqualTo(0b0111)

        val num2complete = parser.getBits(5)
        val goOne2 = num2complete and 16
        val num2 = num2complete and 15

        assertThat(goOne2).isGreaterThan(0)
        assertThat(num2).isEqualTo(0b1110)

        val num3complete = parser.getBits(5)
        val goOne3 = num3complete and 16
        val num3 = num3complete and 15

        assertThat(goOne3).isEqualTo(0)
        assertThat(num3).isEqualTo(0b0101)

        val result = (num1 shl 8) + (num2 shl 4) + (num3)
        assertThat(result).isEqualTo(2021)
    }

    @Test
    fun `shall read and find the end itself`() {
        val parser = BitwiseReader("D2FE28".reader())

        val version = parser.getBits(3)
        val type = parser.getBits(3)
        assertThat(version).isEqualTo(6)
        assertThat(type).isEqualTo(4)

        val number = parser.readLiteral()

        assertThat(number).isEqualTo(2021L)
    }

    @Test
    fun `shall read literal packet`() {
        val parser = BitwiseReader("D2FE28".reader())

        val packet = parser.getNextPacket()

        assertThat(packet).isNotNull
        val lit = packet as Literal
        assertThat(lit.version).isEqualTo(6)
        assertThat(lit.value).isEqualTo(2021)
        assertThat(lit.length).isEqualTo(21)
    }

    @Test
    fun `shall find an operator type 0`() {
        val parser = BitwiseReader("38006F45291200".reader())

        val packet = parser.getNextPacket()

        assertThat(packet).isInstanceOf(Operator::class.java)

        val op = packet as Operator

        assertThat(op.type).isEqualTo(6)
        assertThat(op.version).isEqualTo(1)
        assertThat(op.packets).hasSize(2)
    }

    @Test
    fun `shall find an operator type 1`() {
        val parser = BitwiseReader("EE00D40C823060".reader())

        val packet = parser.getNextPacket()

        assertThat(packet).isInstanceOf(Operator::class.java)

        val op = packet as Operator

        assertThat(op.type).isEqualTo(3)
        assertThat(op.version).isEqualTo(7)
        assertThat(op.packets).hasSize(3)
    }

    @Test
    fun `parse different inputs`() {
        var result = BitwiseReader(
            "8A004A801A8002F478"
                .reader()
        ).getNextPacket()
        var sum = sumVersion(result)
        assertThat(sum).isEqualTo(16)

        result = BitwiseReader(
            "620080001611562C8802118E34"
                .reader()
        ).getNextPacket()
        sum = sumVersion(result)
        assertThat(sum).isEqualTo(12)

        result = BitwiseReader(
            "C0015000016115A2E0802F182340"
                .reader()
        ).getNextPacket()
        sum = sumVersion(result)
        assertThat(sum).isEqualTo(23)

        result = BitwiseReader(
            "A0016C880162017C3686B18A3D4780"
                .reader()
        ).getNextPacket()
        sum = sumVersion(result)
        assertThat(sum).isEqualTo(31)
    }

    @TestFactory
    fun `test values`() = listOf(
        "C200B40A82" to 3L,
        "04005AC33890" to 54L,
        "880086C3E88112" to 7L,
        "CE00C43D881120" to 9L,
        "D8005AC2A8F0" to 1L,
        "F600BC2D8F" to 0L,
        "9C005AC2F8F0" to 0L,
        "9C0141080250320F1802104A08" to 1L
    ).map {
        DynamicTest.dynamicTest(it.first) {
            val parser = BitwiseReader(it.first.reader())
            val packet = parser.getNextPacket()
            val value = value(packet)

            assertThat(value).isEqualTo(it.second)
        }
    }
}
