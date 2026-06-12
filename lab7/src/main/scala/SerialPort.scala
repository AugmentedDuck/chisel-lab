import chisel3._
import chisel3.util._
import chisel.lib.uart._

/**
  * This is the top level to for the UART output and a test blinking LED.
  */
class SerialPort(frequ: Int) extends Module {
  val io = IO(new Bundle {
    val tx = Output(Bool())
    val led = Output(Bool())
  })
  io.tx := true.B
  io.led := true.B

  val uart = Module(new BufferedTx(100000000, 115200))

  val newClock = RegInit("d100000".U)
  newClock := Mux(newClock === "d100000".U, 0.U, newClock + 1.U)

  val displayReg = RegInit(0.U(2.W))
  when (newClock === 0.U) {
    displayReg := displayReg + 1.U
  }

  when (displayReg === 0.U) {
    uart.io.channel.valid := true.B
    uart.io.channel.bits := '0'.U
  } .otherwise {
    uart.io.channel.valid := false.B
  }

  switch (displayReg) {
    is ("b00".U) {
      uart.io.channel.valid := true.B
      uart.io.channel.bits := '0'.U
    }
    is ("b01".U) {
      uart.io.channel.valid := false.B
    }
    is ("b10".U) {
      uart.io.channel.valid := true.B
      uart.io.channel.bits := '1'.U
    }
    is ("b11".U) {
          uart.io.channel.valid := false.B
    }
  }
}

// generate Verilog
object SerialPort extends App {
  emitVerilog(new SerialPort(100000000))
}