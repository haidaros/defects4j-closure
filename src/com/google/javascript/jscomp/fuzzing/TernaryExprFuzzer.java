/*
 * Copyright 2013 The Closure Compiler Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.javascript.jscomp.fuzzing;

import com.google.javascript.rhino.Node;
import com.google.javascript.rhino.Token;

import java.util.Arrays;

/**
 * UNDER DEVELOPMENT. DO NOT USE!
 */
class TernaryExprFuzzer extends AbstractFuzzer {

  TernaryExprFuzzer(FuzzingContext context) {
    super(context);
  }

  private ExpressionFuzzer exprFuzzer;


  /* (non-Javadoc)
   * @see com.google.javascript.jscomp.fuzzing.AbstractFuzzer#generate(int)
   */
  @Override
  protected Node generate(int budget) {
    AbstractFuzzer[] fuzzers = new AbstractFuzzer[3];
    Arrays.fill(fuzzers, getExprFuzzer());
    Node[] components = distribute(budget - 1, fuzzers);
    return new Node(Token.HOOK, components);
  }

  private ExpressionFuzzer getExprFuzzer() {
    if (exprFuzzer == null) {
      exprFuzzer =
          new ExpressionFuzzer(context);
    }
    return exprFuzzer;
  }

  /* (non-Javadoc)
   * @see com.google.javascript.jscomp.fuzzing.AbstractFuzzer#isEnough(int)
   */
  @Override
  protected boolean isEnough(int budget) {
    if (budget < 1) {
      return false;
    } else {
      return getExprFuzzer().isEnough((budget - 1) / 3);
    }
  }

  /* (non-Javadoc)
   * @see com.google.javascript.jscomp.fuzzing.AbstractFuzzer#getConfigName()
   */
  @Override
  protected String getConfigName() {
    return "ternaryExpr";
  }

}
