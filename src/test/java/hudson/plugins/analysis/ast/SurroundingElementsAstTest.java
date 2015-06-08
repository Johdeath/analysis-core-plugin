package hudson.plugins.analysis.ast;

import org.junit.Test;

/**
 * Tests the class {@link SurroundingElementsAst}.
 *
 * @author Christian M�stl
 * @author Ullrich Hafner
 */
public class SurroundingElementsAstTest extends AbstractAstTest {
    private static final String LINE67_METHOD = "METHOD_DEF MODIFIERS LITERAL_PRIVATE TYPE IDENT TYPE_ARGUMENTS GENERIC_START TYPE_ARGUMENT IDENT GENERIC_END IDENT "
            + "LPAREN PARAMETERS PARAMETER_DEF MODIFIERS FINAL TYPE IDENT IDENT "
            + "COMMA PARAMETER_DEF MODIFIERS FINAL TYPE LITERAL_BOOLEAN IDENT RPAREN SLIST ";
    private static final String LINE68_VAR = "VARIABLE_DEF MODIFIERS TYPE LITERAL_INT IDENT ASSIGN EXPR METHOD_CALL DOT IDENT IDENT ELIST RPAREN SEMI ";
    private static final String LINE69_VAR = "VARIABLE_DEF MODIFIERS TYPE LITERAL_INT IDENT SEMI ";
    private static final String LINE70_VAR = "VARIABLE_DEF MODIFIERS TYPE LITERAL_INT IDENT ASSIGN EXPR NUM_INT SEMI ";
    private static final String LINE71_VAR = "VARIABLE_DEF MODIFIERS TYPE IDENT IDENT ASSIGN EXPR METHOD_CALL IDENT ELIST RPAREN SEMI ";
    private static final String LINE73_VAR = "VARIABLE_DEF MODIFIERS TYPE IDENT TYPE_ARGUMENTS GENERIC_START TYPE_ARGUMENT IDENT GENERIC_END IDENT ASSIGN EXPR LITERAL_NEW IDENT TYPE_ARGUMENTS GENERIC_START TYPE_ARGUMENT IDENT GENERIC_END LPAREN ELIST RPAREN SEMI ";
    private static final String LINE75_VAR = "VARIABLE_DEF MODIFIERS TYPE LITERAL_INT IDENT SEMI ";
    private static final String LINE76_IF = "LITERAL_IF LPAREN EXPR IDENT RPAREN SLIST ";
    private static final String LINE77_ASSIGN = "EXPR ASSIGN IDENT IDENT SEMI ";
    private static final String LINE78_RCURLY = "RCURLY ";
    private static final String LINE97_RCURLY = "RCURLY ";
    private static final String LINE98_ELSE = "LITERAL_ELSE SLIST ";
    private static final String LINE99_BREAK = "LITERAL_BREAK SEMI ";
    private static final String LINE100_RCURLY = "RCURLY ";
    private static final String LINE101_RCURLY = "RCURLY ";
    private static final String LINE103_RETURN = "LITERAL_RETURN EXPR IDENT SEMI ";
    private static final String LINE104_RCURLY = "RCURLY ";

    /**
     * <pre>
       METHOD_DEF MODIFIERS LITERAL_PRIVATE TYPE IDENT TYPE_ARGUMENTS GENERIC_START TYPE_ARGUMENT IDENT GENERIC_END IDENT
            LPAREN PARAMETERS PARAMETER_DEF MODIFIERS FINAL TYPE IDENT IDENT
                COMMA PARAMETER_DEF MODIFIERS FINAL TYPE LITERAL_BOOLEAN IDENT
            RPAREN
        SLIST
            VARIABLE_DEF MODIFIERS TYPE LITERAL_INT IDENT ASSIGN EXPR METHOD_CALL DOT IDENT IDENT ELIST RPAREN SEMI
            VARIABLE_DEF MODIFIERS TYPE LITERAL_INT IDENT SEMI
            VARIABLE_DEF MODIFIERS TYPE LITERAL_INT IDENT ASSIGN EXPR NUM_INT SEMI
            VARIABLE_DEF MODIFIERS TYPE IDENT IDENT ASSIGN EXPR METHOD_CALL IDENT ELIST RPAREN SEMI

            VARIABLE_DEF MODIFIERS TYPE IDENT TYPE_ARGUMENTS GENERIC_START TYPE_ARGUMENT IDENT GENERIC_END IDENT ASSIGN EXPR LITERAL_NEW IDENT TYPE_ARGUMENTS GENERIC_START TYPE_ARGUMENT IDENT GENERIC_END LPAREN ELIST RPAREN SEMI

            VARIABLE_DEF MODIFIERS TYPE LITERAL_INT IDENT SEMI
            LITERAL_IF LPAREN EXPR IDENT RPAREN SLIST
                EXPR ASSIGN IDENT IDENT SEMI
            RCURLY
            LITERAL_ELSE SLIST
                EXPR ASSIGN IDENT PLUS MINUS METHOD_CALL IDENT ELIST RPAREN IDENT NUM_INT SEMI
            RCURLY
            LITERAL_FOR LPAREN FOR_INIT VARIABLE_DEF MODIFIERS TYPE LITERAL_INT IDENT ASSIGN EXPR NUM_INT SEMI FOR_CONDITION EXPR LT IDENT IDENT SEMI FOR_ITERATOR ELIST EXPR POST_INC IDENT RPAREN SLIST
                LITERAL_IF LPAREN EXPR LT IDENT IDENT RPAREN SLIST
                    LITERAL_IF LPAREN EXPR IDENT RPAREN SLIST
                        EXPR ASSIGN IDENT MINUS IDENT IDENT SEMI
                    RCURLY
                    LITERAL_ELSE SLIST
                        EXPR ASSIGN IDENT PLUS IDENT IDENT SEMI
                    RCURLY
                    EXPR METHOD_CALL IDENT ELIST RPAREN SEMI
                    EXPR METHOD_CALL IDENT ELIST EXPR IDENT COMMA EXPR IDENT RPAREN SEMI
                    LITERAL_IF LPAREN EXPR LNOT METHOD_CALL DOT METHOD_CALL IDENT ELIST RPAREN IDENT ELIST RPAREN RPAREN SLIST
                        EXPR POST_INC IDENT SEMI
                        EXPR METHOD_CALL DOT IDENT IDENT ELIST EXPR METHOD_CALL IDENT ELIST RPAREN RPAREN SEMI
                        EXPR METHOD_CALL IDENT ELIST RPAREN SEMI
                    RCURLY
                RCURLY
                LITERAL_ELSE SLIST
                    LITERAL_BREAK SEMI
                RCURLY
            RCURLY

            LITERAL_RETURN EXPR IDENT SEMI
        RCURLY
     * </pre>
     */
    @Test
    public void shouldPickExactly3LinesBeforeAndAfter() {
        assertThatAstIs(createAst(70), LINE70_VAR + LINE69_VAR + LINE68_VAR + LINE67_METHOD + LINE71_VAR + LINE73_VAR + LINE75_VAR);
        assertThatAstIs(createAst(71), LINE71_VAR + LINE70_VAR + LINE69_VAR + LINE68_VAR + LINE73_VAR + LINE75_VAR + LINE76_IF);
        assertThatAstIs(createAst(73), LINE73_VAR + LINE71_VAR + LINE70_VAR + LINE69_VAR + LINE75_VAR + LINE76_IF + LINE77_ASSIGN);
        assertThatAstIs(createAst(75), LINE75_VAR + LINE73_VAR + LINE71_VAR + LINE70_VAR + LINE76_IF + LINE77_ASSIGN + LINE78_RCURLY);

        assertThatAstIs(createAst(100), LINE100_RCURLY + LINE99_BREAK + LINE98_ELSE + LINE97_RCURLY + LINE101_RCURLY + LINE103_RETURN + LINE104_RCURLY);
    }

    @Test
    public void shouldHandleEmptyLines() {
        assertThatAstIs(createAst(72), LINE71_VAR + LINE70_VAR + LINE69_VAR + LINE73_VAR + LINE75_VAR + LINE76_IF);
        assertThatAstIs(createAst(74), LINE73_VAR + LINE71_VAR + LINE70_VAR + LINE75_VAR + LINE76_IF + LINE77_ASSIGN);
    }

    private Ast createAst(final int lineNumber) {
        return new SurroundingElementsAst(createJavaSourceTemporaryFile("surrounding-elements.ast-test"), lineNumber, 3);
    }

    /**
     * Verifies that the EnvironmentAst works right.
     */
    @Test
    public void shouldFindSurroundingElements() {
        String expectedResult = "LITERAL_IF LPAREN EXPR GE IDENT NUM_INT RPAREN METHOD_DEF MODIFIERS LITERAL_PUBLIC TYPE LITERAL_INT IDENT LPAREN PARAMETERS PARAMETER_DEF MODIFIERS FINAL TYPE LITERAL_INT IDENT RPAREN SLIST RCURLY EXPR ASSIGN DOT LITERAL_THIS IDENT IDENT SEMI LITERAL_RETURN EXPR IDENT SEMI LITERAL_ELSE SLIST LITERAL_RETURN EXPR UNARY_MINUS IDENT SEMI ";

        Ast ast = new SurroundingElementsAst(createJavaSourceTemporaryFile("NeedBraces_Newline.java"), 44);

        assertThatAstIs(ast, expectedResult);
    }
}
