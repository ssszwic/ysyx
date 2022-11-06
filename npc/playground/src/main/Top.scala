package main

import chisel3._
import chisel3.util._

class Top extends Module {
  val io = IO(new Bundle {
    // inst Memory
    val instData  = Input(UInt(64.W))
    val instAddr  = Output(UInt(64.W))
    // memory
    val rData     = Input(UInt(64.W))
    val rAddr     = Output(UInt(64.W))
    val wData     = Output(UInt(64.W))
    val wAddr     = Output(UInt(64.W))
    val wen       = Output(Bool())
    val length    = Output(UInt(2.W))
  })

  // declare module
  val IFUInst = Module(new IFU)
  val IDUInst = Module(new IDU)
  val RegFilesInst = Module(new RegFiles)
  val ALUInst = Module(new ALU)
  val MemExtendsInst = Module(new MemExtends)

  // default nextpc = pc + 4
  val nextpcDefault = Wire(UInt(64.W))
  nextpcDefault := IFUInst.io.pc + 4.U

  // IO
  io.instAddr := IFUInst.io.pc
  io.rAddr    := ALUInst.io.result
  io.wData    := RegFilesInst.io.rs2Data
  io.wAddr    := ALUInst.io.result
  io.wen      := IDUInst.io.wenMem
  io.length   := IDUInst.io.lengthMem

  // IFU
  IFUInst.io.nextpc   := Mux(ALUInst.io.nextpcSel || IDUInst.io.jumpSel, ALUInst.io.result, nextpcDefault)
  IFUInst.io.instGet  := io.instData

  // IDU
  IDUInst.io.inst := IFUInst.io.inst

  // RegFiles
  RegFilesInst.io.rs1Addr := IDUInst.io.rs1Addr
  RegFilesInst.io.rs2Addr := IDUInst.io.rs2Addr
  RegFilesInst.io.wen     := IDUInst.io.wenReg
  RegFilesInst.io.wAddr   := IDUInst.io.rdAddr
  RegFilesInst.io.wData   := Mux(IDUInst.io.jumpSel, nextpcDefault, 
                                  Mux(IDUInst.io.loadMem, MemExtendsInst.io.result, ALUInst.io.result))

  // ALU
  ALUInst.io.rs1  := RegFilesInst.io.rs1Data
  ALUInst.io.rs2  := RegFilesInst.io.rs2Data
  ALUInst.io.imme := IDUInst.io.imme
  ALUInst.io.pc   := IFUInst.io.pc
  ALUInst.io_alu  <> IDUInst.io_alu

  // MemExtends
  MemExtendsInst.io.data      := io.rData
  MemExtendsInst.io.lengthMem := IDUInst.io.lengthMem
  MemExtendsInst.io.unsignMem := IDUInst.io.unsignMem
}