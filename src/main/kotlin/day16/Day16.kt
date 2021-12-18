package day16

import util.ResLoader
import java.io.Reader

class BitwiseReader(
    val r: Reader
) {
    private var _intStore = 0
    private var _bitsInStore = 0
    private var _totalBitsRead = 0

    private val bitmasks = arrayOf(
        0b0,
        0b1,
        0b11,
        0b111,
        0b1111,
        0b11111,
        0b111111,
        0b1111111,
        0b11111111,
        0b111111111,
        0b1111111111,
        0b11111111111,
        0b111111111111,
        0b1111111111111,
        0b11111111111111,
        0b111111111111111,
        0b1111111111111111,
        0b11111111111111111,
        0b111111111111111111,
        0b1111111111111111111,
        0b11111111111111111111,
        0b111111111111111111111,
        0b1111111111111111111111,
        0b11111111111111111111111,
        0b111111111111111111111111
    )

    private fun readBytes(amount: Int) {
        var toRead = amount
        var nextI = r.read()
        while ((nextI >= 0) && (toRead > 0)) {
            _intStore = _intStore shl 4
            val nextChar = Char(nextI)
            val next = "$nextChar".toInt(16)
            _intStore += next
            _bitsInStore += 4
            toRead -= 4
            if (toRead > 0) nextI = r.read()
        }
    }

    fun getBits(num: Int): Int {
        if (num > _bitsInStore) readBytes(num - _bitsInStore)

        if (num > _bitsInStore) return -1

        val offset = _bitsInStore - num
        val mask = bitmasks[num] shl offset

        val ret = (_intStore and mask) ushr offset

        _intStore = _intStore and bitmasks[offset]
        _bitsInStore = offset

        _totalBitsRead += num

        return ret
    }

    fun readLiteral(): Long {
        var ret = 0L

        var goOn = true
        while (goOn) {
            ret = ret shl 4
            val next5bits = getBits(5)
            goOn = (next5bits and 16) > 0
            ret += next5bits and 15
        }

        return ret
    }

    fun readOperator(): List<Packet> {
        val lengthTyp = getBits(1)
        return when (lengthTyp) {
            1 -> (1..getBits(11)).map { getNextPacket() }

            else -> {
                val bits = getBits(15)
                val start = _totalBitsRead
                buildList {
                    while (_totalBitsRead < start + bits) {
                        val nextPacket = getNextPacket()
                        add(nextPacket)
                    }
                }
            }

        }
    }

    fun getNextPacket(): Packet {
        val start = _totalBitsRead
        val version = getBits(3)
        val type = getBits(3)
        return if (type == 4)
            Literal(version, readLiteral(), _totalBitsRead - start)
        else {
            Operator(version, type, readOperator(), _totalBitsRead - start)
        }
    }

}

sealed class Packet
data class Literal(val version: Int, val value: Long, val length: Int) : Packet()
data class Operator(val version: Int, val type: Int, val packets: List<Packet>, val length: Int) : Packet()

fun sumVersion(packet: Packet): Int =
    when (packet) {
        is Literal -> packet.version
        is Operator -> packet.version + packet.packets.sumOf { sumVersion(it) }
    }

fun value(packet: Packet): Long =
    when (packet) {
        is Literal -> packet.value
        is Operator -> when (packet.type) {
            0 -> packet.packets.sumOf(::value)
            1 -> packet.packets.fold(1L) { acc, next -> acc * value(next) }
            2 -> packet.packets.minOf(::value)
            3 -> packet.packets.maxOf(::value)
            5 -> if (value(packet.packets[0]) > value(packet.packets[1])) 1 else 0
            6 -> if (value(packet.packets[0]) < value(packet.packets[1])) 1 else 0
            7 -> if (value(packet.packets[0]) == value(packet.packets[1])) 1 else 0
            else -> throw RuntimeException("wrong operator")
        }
    }


fun day16() {
    val parser = BitwiseReader(ResLoader.readText("input_16").reader())

    val packet = parser.getNextPacket()

    val sum = sumVersion(packet)

    println("Day16\nHex-Code\n==============\nThe total Version is: $sum")

    val value = value(packet)

    println("The Value is: $value")
}
