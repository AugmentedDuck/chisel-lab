import chisel3._
import chisel3.util._

class SevenSegDecoder extends Module {
  val io = IO(new Bundle {
    val sw = Input(UInt(4.W))
    val seg = Output(UInt(7.W))
    val an = Output(UInt(4.W))
  })

  val sevSeg = WireDefault(0.U(7.W))

  // ***** your code starts here *****
  // b0000000.u = None
  // b0000001.u = Middle Top
  // b0000010.u = Right Top
  // b0000100.u = Right Bottom
  // b0001000.u = Middle Bottom
  // b0010000.u = Left Bottom
  // b0100000.u = Left Top
  // b1000000.u = Middle Center

  switch(io.sw) {
    is (0.U) {sevSeg := "b0111111".U}
    is (1.U) {sevSeg := "b0000110".U}
    is (2.U) {sevSeg := "b1011011".U}
    is (3.U) {sevSeg := "b1001111".U}
    is (4.U) {sevSeg := "b1100110".U}
    is (5.U) {sevSeg := "b1101101".U}
    is (6.U) {sevSeg := "b1111101".U}
    is (7.U) {sevSeg := "b0000111".U}
    is (8.U) {sevSeg := "b1111111".U}
    is (9.U) {sevSeg := "b1101111".U}
    // is (10.U) {sevSeg := "b1110111".U}
  }

  // ***** your code ends here *****

  io.seg := ~sevSeg
  io.an := "b1110".U
}

// generate Verilog
object SevenSegDecoder extends App {
  emitVerilog(new SevenSegDecoder())
}


