package main.MEM

import chisel3._
import chisel3.util._

class MemInterface extends Bundle {
  val clock   = Input(Clock())
  val reset   = Input(Bool())
  // read
  val ren     = Input(Bool())
  val addr    = Input(UInt(32.W))
  val rData   = Output(UInt(64.W))
  val rvalid  = Output(Bool())
  val hit     = Output(Bool())
  // write
  val wen     = Input(Bool())
  val wData   = Input(UInt(64.W))
  val wMask   = Input(UInt(8.W))
}

class MemVirtual extends BlackBox with HasBlackBoxResource {
  val io = IO(new Bundle {
    // read
    val clock   = Input(Clock())
    val reset   = Input(Bool())
    val ioMem   = new MemInterface
  })
  addResource("/MemVirtual.v")
}
