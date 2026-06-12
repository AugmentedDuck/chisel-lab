/*
 * Blinking LED: the hardware version of Hello World
 *
 * Copyright: 2013, Technical University of Denmark, DTU Compute
 * Author: Martin Schoeberl (martin@jopdesign.com)
 * 
 */

import chisel3._

class X extends Module {
  val io = IO(new Bundle{
    val in = Flipped(new DecoupledIO(UInt(8.W)))
    val out = new DecoupledIO(UInt(8.W))
  })

  val dataReg = RegInit(0.U(8.W))
  val emptyReg = RegInit(true.B)

  io.in.ready := emptyReg
  io.out.valid := !emptyReg
  io.out.bits := dataReg

  when (emptyReg & io.in.valid) {
    dataReg := io.in.bits
    emptyReg := false.B
  }

  when (!emptyReg & io.out.ready) {
    emptyReg := true.B
  }
}

class DecoupledIO[T <: Data](gen: T) extends Bundle {
  val ready = Input(Bool())
  val valid = Output(Bool())
  val bits = Output(gen)
}

/**
 * An object extending App to generate the Verilog code.
 */
object HelloMain extends App {
  println("Hello World, I will now generate the Verilog file!")
  emitVerilog(new X())
}