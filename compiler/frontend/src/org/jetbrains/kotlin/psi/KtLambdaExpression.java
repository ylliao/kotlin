/*
 * Copyright 2010-2015 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jetbrains.kotlin.psi;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.lexer.KtTokens;

import java.util.List;

public class KtLambdaExpression extends KtExpressionImpl {


    public KtLambdaExpression(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public <R, D> R accept(@NotNull KtVisitor<R, D> visitor, D data) {
        return visitor.visitLambdaExpression(this, data);
    }

    @NotNull
    public KtFunctionLiteral getFunctionLiteral() {
        return (KtFunctionLiteral) findChildByType(KtNodeTypes.FUNCTION_LITERAL);
    }

    @NotNull
    public List<KtParameter> getValueParameters() {
        return getFunctionLiteral().getValueParameters();
    }

    @Nullable
    public KtBlockExpression getBodyExpression() {
        return getFunctionLiteral().getBodyExpression();
    }

    public boolean hasDeclaredReturnType() {
        return getFunctionLiteral().getTypeReference() != null;
    }

    @NotNull
    public KtElement asElement() {
        return this;
    }

    @NotNull
    public ASTNode getLeftCurlyBrace() {
        return getFunctionLiteral().getNode().findChildByType(KtTokens.LBRACE);
    }

    @Nullable
    public ASTNode getRightCurlyBrace() {
        return getFunctionLiteral().getNode().findChildByType(KtTokens.RBRACE);
    }

    @NotNull
    @Override
    public KtFile getContainingKtFile() {
        PsiFile file = getContainingFile();
        assert file instanceof KtFile : "KtElement not inside KtFile: " + file + " " + file.getText();
        return (KtFile) file;
    }

    @Override
    public <D> void acceptChildren(@NotNull KtVisitor<Void, D> visitor, D data) {
        KtPsiUtil.visitChildren(this, visitor, data);
    }

    @Override
    public String toString() {
        return getNode().getElementType().toString();
    }

    @NotNull
    @Override
    public KtElement getPsiOrParent() {
        return this;
    }
}
