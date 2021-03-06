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

/**
 * UNDER DEVELOPMENT. DO NOT USE!
 */
class ContinueFuzzer extends AbstractFuzzer {

  ContinueFuzzer(FuzzingContext context) {
    super(context);
  }

  /* (non-Javadoc)
   * @see com.google.javascript.jscomp.fuzzing.AbstractFuzzer#isEnough(int)
   */
  @Override
  protected boolean isEnough(int budget) {
    Scope scope = context.scopeManager.localScope();
    if (scope.loopNesting > 0) {
      return budget >= 1;
    } else {
      return false;
    }
  }

  /* (non-Javadoc)
   * @see com.google.javascript.jscomp.fuzzing.AbstractFuzzer#generate(int)
   */
  @Override
  protected Node generate(int budget) {
    Node node = new Node(Token.CONTINUE);
    Scope localScope = context.scopeManager.localScope();
    double toLabel = getOwnConfig().optDouble("toLabel");
    if (budget > 1 &&
        localScope.loopLabels.size() > 0 &&
        context.random.nextDouble() < toLabel) {
      node.addChildToBack(
          Node.newString(Token.LABEL_NAME,
              localScope.randomLabelForContinue(context.random)));
    }
    return node;
  }

  /* (non-Javadoc)
   * @see com.google.javascript.jscomp.fuzzing.AbstractFuzzer#getConfigName()
   */
  @Override
  protected String getConfigName() {
    return "continue";
  }

}
