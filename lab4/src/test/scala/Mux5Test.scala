import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class Mux5Test extends AnyFlatSpec with ChiselScalatestTester {
  "Mux5 " should "pass" in {
    test(new Mux5()) { dut =>
      for (n <- 0 to 15) {
        for (sel <- 0 to 4) {
          dut.io.a.poke((n & 0x1).U)
          dut.io.b.poke(((n >> 1) & 0x1).U)
          dut.io.c.poke(((n >> 2) & 0x1).U)
          dut.io.d.poke(((n >> 3) & 0x1).U)
          dut.io.e.poke(((n >> 4) & 0x1).U)
          dut.io.sel.poke(sel.U)
          dut.clock.step(1)

          val res = (n >> sel) & 0x1

          println(n + " " + sel + " " + res + " " + dut.io.y.peek().litValue)
          dut.io.y.expect(res.U)
        }
      }
    }
  }
}
