/***************************************************************************************
* Copyright (c) 2014-2022 Zihao Yu, Nanjing University
*
* NEMU is licensed under Mulan PSL v2.
* You can use this software according to the terms and conditions of the Mulan PSL v2.
* You may obtain a copy of Mulan PSL v2 at:
*          http://license.coscl.org.cn/MulanPSL2
*
* THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
* EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
* MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
*
* See the Mulan PSL v2 for more details.
***************************************************************************************/

#include <isa.h>
#include "local-include/reg.h"

const char *regs[] = {
  "$0", "ra", "sp", "gp", "tp", "t0", "t1", "t2",
  "s0", "s1", "a0", "a1", "a2", "a3", "a4", "a5",
  "a6", "a7", "s2", "s3", "s4", "s5", "s6", "s7",
  "s8", "s9", "s10", "s11", "t3", "t4", "t5", "t6"
};

void isa_reg_display() {
  int i;
  // pc reg
  printf("%-12s", "pc");
  printf("0x%016lx    ", cpu.pc);
  printf("%ld\n", cpu.pc);
  // normal 32 reg
  for (i = 0; i < 32; i++) {
    printf("%-12s", reg_name(i, 0));
    printf("0x%016lx    ", gpr(i));
    printf("%ld\n", gpr(i));
  }
}

word_t isa_reg_str2val(const char *s, bool *success) {
  // pc reg
  if (strcmp(s, "pc")){
    *success = true;
    return cpu.pc;
  }
  // noemal reg
  int i = 0;
  for (i = 0; i < 32; i++) {
    if(strcmp(s, regs[i]) == 0) {
      break;
    }
  }
  if (i == 32) {
    *success = false;
    return 0;
  }
  else {
    *success = true;
    return gpr(i);
  }
}
