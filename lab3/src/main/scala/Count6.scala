import chisel3._

class Count6 extends Module {
  val io = IO(new Bundle {
    val dout = Output(UInt(8.W))
  })

  val res = Wire(UInt())

  // ***** your code starts here *****
  val valReg = RegInit(0.U(3.W))

  valReg := Mux(valReg === 6.U, 0.U, valReg + 1.U)
  res := valReg

  // ***** your code ends here *****

  io.dout := res
}