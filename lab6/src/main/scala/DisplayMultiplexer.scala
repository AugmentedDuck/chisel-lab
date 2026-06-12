import chisel3._
import chisel3.util._

class DisplayMultiplexer(maxCount: Int) extends Module {
  val io = IO(new Bundle {
    val sum = Input(UInt(8.W))
    val price = Input(UInt(8.W))
    val seg = Output(UInt(7.W))
    val an = Output(UInt(4.W))
  })

  val sevSeg = WireDefault("b1111111".U(7.W))
  val select = WireDefault("b0001".U(4.W))
  
  // *** your code starts here

  val newClock = RegInit("d100000".U)
  newClock := Mux(newClock === "d100000".U, 0.U, newClock + 1.U)

  val displayReg = RegInit(0.U(2.W))
  when (newClock === 0.U) {
    displayReg := displayReg + 1.U
  }


  val input = WireDefault(Cat(io.price, io.sum))
  val singleInput = WireDefault(0.U(4.W))
  
  switch(displayReg) {
    is (0.U) {
      select := "b0001".U
      singleInput := input(3, 0)
    }
    is (1.U) {
      select := "b0010".U
      singleInput := input(7, 4)
    }
    is (2.U) {
      select := "b0100".U
      singleInput := input(11, 8)
    }
    is (3.U) {
      select := "b1000".U
      singleInput := input(15, 12)
    }
  }

  val decoder = Module(new SevenSegDec())

  decoder.io.in := singleInput

  sevSeg := decoder.io.out

  // *** your code ends here

  io.seg := ~sevSeg
  io.an := ~select
}
