import chisel3._

class Delay extends Module {
  val io = IO(new Bundle {
    val din = Input(UInt(8.W))
    val dout = Output(UInt(8.W))
  })

  val res = Wire(UInt())
  val res1 = Wire(UInt())

  // ***** your code starts here *****

  val valReg1 = RegInit(0.U)
  val valReg2 = RegInit(0.U)

  valReg1 := io.din
  res1 := valReg1 
  valReg2 := res1

  // below is dummy code to make this example compile
  res := valReg2

  // ***** your code ends here *****

  io.dout := res
}