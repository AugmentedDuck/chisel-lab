import chisel3._
import chisel3.util._

class SevenSegDec extends Module {
  val io = IO(new Bundle {
    val in = Input(UInt(4.W))
    val out = Output(UInt(7.W))
  })

  val sevSeg = WireDefault(0.U)

  // *** add your table from Lab 6 here or use the version from Lab 8.
  switch(io.in) {
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
    is (10.U) {sevSeg := "b1110111".U}
    is (11.U) {sevSeg := "b1111100".U}
    is (12.U) {sevSeg := "b0111001".U}
    is (13.U) {sevSeg := "b1011110".U}
    is (14.U) {sevSeg := "b1111001".U}
    is (15.U) {sevSeg := "b1110001".U}
  }

  // *** end adding the table

  io.out := sevSeg
}


