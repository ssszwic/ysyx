module RegFiles(
  input             clock,
  input             reset,
  input     [4:0]   rs1Addr,
  input     [4:0]   rs2Addr,
  // data
  input     [63:0]  rdData,
  output    [63:0]  rs1Data,
  output    [63:0]  rs2Data
  // control
  input             regCtrl_wen,
  input     [4:0]   regCtrl_rdAddr,
);

reg   [63:0]  regFiles [0:31];
genvar i;

always@(posedge clock) begin
  if(reset) begin
    regFiles[0] <= 64'b0;
  end
  else begin
    regFiles[0] <= 64'b0;
  end
end

generate
  for(i = 1; i < 32; i = i + 1) begin
    always@(posedge clock) begin
      if(reset) begin
        regFiles[i] <= 64'b0;
      end
      else if(regCtrl_wen & (regCtrl_rdAddr == i)) begin
        regFiles[i] <= rdData;
      end
      else begin
        regFiles[i] <= regFiles[i];
      end
    end
  end
endgenerate

assign rs1Data = regFiles[rs1Addr];
assign rs2Data = regFiles[rs2Addr];

import "DPI-C" function void set_gpr_ptr(input logic [63:0] a []);
initial set_gpr_ptr(regFiles);

endmodule