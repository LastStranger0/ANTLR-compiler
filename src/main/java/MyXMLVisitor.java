import AST.*;

import java.util.ArrayList;

public class MyXMLVisitor extends xmlBaseVisitor<Node> {
    private final xmlParserV1 xmlParserV1;
    private final VarAndFunRegist regist = new VarAndFunRegist();
    private final String name;

    public MyXMLVisitor(xmlParserV1 xmlParserV1, String name){
        this.name = name;
        this.xmlParserV1 = xmlParserV1;
    }

    @Override
    public Node visitAdd_assignment(xmlParser.Add_assignmentContext ctx) {
        Add_Assignment add_assignment = new Add_Assignment();
        add_assignment.expression = (Expression) this.visitExpression(ctx.expression());
        add_assignment.name = new Name();
        add_assignment.name.name = this.name;
        add_assignment.number_literal = new Number_Literal();
        add_assignment.number_literal.number = Double.parseDouble(ctx.NUMBER_LITERAL().getText());
        return add_assignment;
    }

    @Override
    public Node visitOperation(xmlParser.OperationContext ctx) {
        Operation operation = new Operation();
        if (ctx.get_operation() != null){
            operation.get_operation = (Get_Operation) visitGet_operation(ctx.get_operation());
        }
        if (ctx.initialise_var() != null){
            operation.initialise_var = (Initialise_Var) visitInitialise_var(ctx.initialise_var());
        }
        if (ctx.assignment() != null){
            operation.assignment = (Assignment) visitAssignment(ctx.assignment());
        }
        if (ctx.add_assignment() != null){
            operation.add_assignment = (Add_Assignment) visitAdd_assignment(ctx.add_assignment());
        }
        if (ctx.if_statement() != null){
            operation.if_statement = (If_Statement) visitIf_statement(ctx.if_statement());
        }
        if (ctx.for_statement() != null){
            operation.for_statement = (For_Statement) visitFor_statement(ctx.for_statement());
        }
        if (ctx.while_statement() != null){
            operation.while_statement = (While_Statement) visitWhile_statement(ctx.while_statement());
        }
        if (ctx.type_cast()!=null){
            operation.type_cast = (Type_Cast) visitType_cast(ctx.type_cast());
        }
        return operation;
    }

    @Override
    public Node visitGet_array_elem(xmlParser.Get_array_elemContext ctx) {
        Get_Array_Elem get_array_elem = new Get_Array_Elem();
        get_array_elem.name = new Name();
        get_array_elem.number_literal = new Number_Literal();
        get_array_elem.name.name = ctx.NAME().getText();
        get_array_elem.number_literal.number = Double.parseDouble(ctx.NUMBER_LITERAL().getText());
        return get_array_elem;
    }

    @Override
    public Node visitGet_elem(xmlParser.Get_elemContext ctx) {
        Get_Elem get_elem = new Get_Elem();
        if (ctx.NAME() != null) {
            get_elem.name1 = new Name();
            get_elem.name2 = new Name();
            get_elem.name1.name = ctx.NAME().get(0).getText();
            get_elem.name2.name = ctx.NAME().get(1).getText();
        }
        if (ctx.params() !=null) {
            get_elem.params = (Params) this.visitParams(ctx.params());
        }
        if(ctx.get_elem() !=null) {
            get_elem.get_elem = (Get_Elem) this.visitGet_elem(ctx.get_elem());
        }
        return get_elem;
    }

    @Override
    public Node visitAssignment(xmlParser.AssignmentContext ctx) {
        Assignment assignment = new Assignment();
        assignment.name = new Name();
        assignment.number_literal = new Number_Literal();
        assignment.name.name = ctx.NAME().getText();
        assignment.expression = (Expression) this.visitExpression(ctx.expression());
        assignment.number_literal.number = Double.parseDouble(ctx.NUMBER_LITERAL().getText());
        switch (ctx.TYPE().getText()) {
            case "document" -> assignment.type = Type.DOCUMENT;
            case "node" -> assignment.type = Type.NODE;
            case "attribute" -> assignment.type = Type.ATTRIBUTE;
            case "string" -> assignment.type = Type.STRING;
            case "int" -> assignment.type = Type.INT;
            case "float" -> assignment.type = Type.FLOAT;
        }
        return assignment;
    }

    @Override
    public Node visitElse_block(xmlParser.Else_blockContext ctx) {
        Else_Block else_block = new Else_Block();
        else_block.operations = new ArrayList<>();
        for (int i = 0; i < ctx.operation().size(); i++){
            else_block.operations.add((Operation) this.visitOperation(ctx.operation().get(i)));
        }
        return else_block;
    }

    @Override
    public Node visitCondition(xmlParser.ConditionContext ctx) {
        Condition condition = new Condition();
        condition.expression1 = (Expression) this.visitExpression(ctx.expression().get(0));
        if (ctx.expression().get(1) != null) {
            condition.expression2 = new ArrayList<>();
            for (int i = 0; i < ctx.expression().size(); i++) {
                condition.expression2.add((Expression) this.visitExpression(ctx.expression().get(i)));
            }
        }
        return condition;
    }

    @Override
    public Node visitElse_if_block(xmlParser.Else_if_blockContext ctx) {
        Else_If_Block else_if_block = new Else_If_Block();
        else_if_block.condition = (Condition) this.visitCondition(ctx.condition());
        else_if_block.operations = new ArrayList<>();
        for(int i = 0; i < ctx.operation().size(); i++){
            else_if_block.operations.add((Operation) this.visitOperation(ctx.operation().get(i)));
        }
        return else_if_block;
    }

    @Override
    public Node visitExpression(xmlParser.ExpressionContext ctx) {
        Expression expression = new Expression();
        if (ctx.NAME()!= null){
            expression.name = new Name();
            expression.name.name = ctx.NAME().getText();
        }
        if(ctx.STRING_LITERAL()!=null){
            expression.string_literal = new String_Literal();
            expression.string_literal.str = ctx.STRING_LITERAL().getText();
        }
        if (ctx.NUMBER_LITERAL()!=null){
            expression.number_literal = new Number_Literal();
            expression.number_literal.number = Double.parseDouble(ctx.NUMBER_LITERAL().getText());
        }
        if (ctx.get_operation()!=null){
            expression.get_operation = (Get_Operation) this.visitGet_operation(ctx.get_operation());
        }
        if (ctx.expression().get(0)!=null){
            expression.expression1 = (Expression) this.visitExpression(ctx.expression().get(0));
        }
        if (ctx.expression().get(1)!=null){
            expression.expression2 = (Expression) this.visitExpression(ctx.expression().get(1));
        }
        if (ctx.ACTION_OPERATOR()!=null){
            switch (ctx.ACTION_OPERATOR().getText()){
                case "+" -> expression.action_operator = Action_Operator.PLUS;
                case "-" -> expression.action_operator = Action_Operator.MINUS;
                case "*" -> expression.action_operator = Action_Operator.MULTIPLICATION;
                case "/" -> expression.action_operator = Action_Operator.DIVISION;
            }
        }
        if (ctx.BOOL_OPERATOR()!=null){
            switch (ctx.BOOL_OPERATOR().getText()){
                case ">" -> expression.bool_operator = Bool_Operator.MORE_THAN;
                case "<" -> expression.bool_operator = Bool_Operator.LESS_THAN;
                case "==" -> expression.bool_operator = Bool_Operator.EQUAL;
                case "!=" -> expression.bool_operator = Bool_Operator.NOT_EQUAL;
                case ">=" -> expression.bool_operator = Bool_Operator.MORE_EQUAL;
                case "<=" -> expression.bool_operator = Bool_Operator.LESS_EQUAL;
            }
        }
        return expression;
    }

    @Override
    public Node visitFor_statement(xmlParser.For_statementContext ctx) {
        For_Statement for_statement = new For_Statement();
        for_statement.range_statement = (Range_Statement) this.visitRange_statement(ctx.range_statement());
        for_statement.operations = new ArrayList<>();
        for (int i = 0; i < ctx.operation().size(); i++){
            for_statement.operations.add((Operation) this.visitOperation(ctx.operation().get(i)));
        }
        return for_statement;
    }

    @Override
    public Node visitFunc_call(xmlParser.Func_callContext ctx) {
        return super.visitFunc_call(ctx);
    }

    @Override
    public Node visitGet_operation(xmlParser.Get_operationContext ctx) {
        return super.visitGet_operation(ctx);
    }

    @Override
    public Node visitIf_block(xmlParser.If_blockContext ctx) {
        return super.visitIf_block(ctx);
    }

    @Override
    public Node visitIf_statement(xmlParser.If_statementContext ctx) {
        return super.visitIf_statement(ctx);
    }

    @Override
    public Node visitInitialise_func(xmlParser.Initialise_funcContext ctx) {
        return super.visitInitialise_func(ctx);
    }

    @Override
    public Node visitInitialise_var(xmlParser.Initialise_varContext ctx) {
        return super.visitInitialise_var(ctx);
    }

    @Override
    public Node visitParams(xmlParser.ParamsContext ctx) {
        return super.visitParams(ctx);
    }

    @Override
    public Node visitRange_statement(xmlParser.Range_statementContext ctx) {
        return super.visitRange_statement(ctx);
    }

    @Override
    public Node visitWhile_statement(xmlParser.While_statementContext ctx) {
        return super.visitWhile_statement(ctx);
    }

    @Override
    public Node visitType_cast(xmlParser.Type_castContext ctx) {
        return super.visitType_cast(ctx);
    }

    @Override
    public Node visitXml(xmlParser.XmlContext ctx) {
        return super.visitXml(ctx);
    }

}