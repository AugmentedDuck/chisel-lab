import chisel3._

class CountSevenSeg extends Module {
  val io = IO(new Bundle {
    val seg = Output(UInt(7.W))
    val an = Output(UInt(4.W))
  })

  val sevSeg = WireDefault("b1111111".U(7.W))

  // *** your code starts here
  val countReg = RegInit(0.U(4.W))

  countReg := Mux(countReg === 9.U, 0.U, countReg + 1.U)

  val sevenSegDecoder = Module(new SevenSegDecoder())

  sevenSegDecoder.io.sw := countReg
  sevSeg := sevenSegDecoder.io.seg

  // *** your code ends here

  io.seg := sevSeg
  io.an := "b1110".U
}

// generate Verilog
object CountSevenSeg extends App {
  emitVerilog(new CountSevenSeg())
}
