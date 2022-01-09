package day17

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day17Test {
    @Test
    fun `find x velocity within 20 - 30`() {
        val result = findXVel(20, 30)

        assertThat(result).isEqualTo(6)
    }

    @Test
    fun `find y velocity within -5 - -10`() {
        val result = findYVel(-5, -10)

        assertThat(result).isEqualTo(9)
    }

    @Test
    fun `find maximum height for initial yVel of 9`() {
        val result = findYMax(9)

        assertThat(result).isEqualTo(45)
    }

    @Test
    fun `check if xVel and yVel fit`() {
        val result = checkFit(6, 9)

        assertThat(result).isTrue()
    }

    @Test
    fun `check if initialValue hits eventually`() {
        val result = checkIfProbeHitsTarget(
            Probe(0, 0, 6, 9),
            ProbeTarget(
                xRange = 20..30,
                yRange = -10..-5
            )
        )

        assertThat(result).isTrue()
    }

    @Test
    fun `find all initial x vectors`() {
        val result = findXVelRange(20..30)

        assertThat(result).isEqualTo(6..30)
    }

    @Test
    fun `find all initial y vectors`() {
        val result = findYVelRange(-10..-5)

        assertThat(result).isEqualTo(-10..9)
    }

    @Test
    fun `find all velocities for given Target`() {
        val result = findAllVelocities(
            ProbeTarget(
                20..30,
                -10..-5
            )
        )

        assertThat(result).hasSize(112)
    }
}
