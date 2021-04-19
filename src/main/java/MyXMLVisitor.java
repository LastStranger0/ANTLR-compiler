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
        if(ctx.NAME()!=null) {
            add_assignment.name = new Name();
            add_assignment.name.name = ctx.NAME().getText();
        }
        if(ctx.NUMBER_LITERAL()!=null) {
            add_assignment.number_literal = new Number_Literal();
            add_assignment.number_literal.number = Double.parseDouble(ctx.NUMBER_LITERAL().getText());
        }
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
        if(ctx.NAME()!=null){
            get_array_elem.name = new Name();
            get_array_elem.name.name = ctx.NAME().getText();
        }
        if (ctx.NUMBER_LITERAL()!=null) {
            get_array_elem.number_literal = new Number_Literal();
            get_array_elem.number_literal.number = Double.parseDouble(ctx.NUMBER_LITERAL().getText());
        }
        return get_array_elem;
    }

    @Override
    public Node visitGet_elem(xmlParser.Get_elemContext ctx) {
        Get_Elem get_elem = new Get_Elem();
        if (ctx.NAME().size() > 0) {
            get_elem.name1 = new Name();
            get_elem.name1.name = ctx.NAME().get(0).getText();
        }
        if (ctx.NAME().size() > 1) {
            get_elem.name2 = new Name();
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
        if (ctx.NAME()!=null) {
            assignment.name = new Name();
            assignment.name.name = ctx.NAME().getText();
        }
        if (ctx.NUMBER_LITERAL()!=null) {
            assignment.number_literal = new Number_Literal();
            assignment.number_literal.number = Double.parseDouble(ctx.NUMBER_LITERAL().getText());
        }
        assignment.expression = (Expression) this.visitExpression(ctx.expression());
        if (ctx.TYPE()!=null) {
            switch (ctx.TYPE().getText()) {
                case "document" -> assignment.type = Type.DOCUMENT;
                case "node" -> assignment.type = Type.NODE;
                case "attribute" -> assignment.type = Type.ATTRIBUTE;
                case "string" -> assignment.type = Type.STRING;
                case "int" -> assignment.type = Type.INT;
                case "float" -> assignment.type = Type.FLOAT;
            }
        }
        return assignment;
    }

    @Override
    public Node visitElse_block(xmlParser.Else_blockContext ctx) {
        Else_Block else_block = new Else_Block();
        if (ctx.operation()!=null) {
            else_block.operations = new ArrayList<>();
            for (int i = 0; i < ctx.operation().size(); i++) {
                else_block.operations.add((Operation) this.visitOperation(ctx.operation().get(i)));
            }
        }
        return else_block;
    }

    @Override
    public Node visitCondition(xmlParser.ConditionContext ctx) {
        Condition condition = new Condition();
        condition.expression1 = (Expression) this.visitExpression(ctx.expression().get(0));
        if (ctx.expression().size() > 0) {
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
        if(ctx.operation()!=null) {
            else_if_block.operations = new ArrayList<>();
            for (int i = 0; i < ctx.operation().size(); i++) {
                else_if_block.operations.add((Operation) this.visitOperation(ctx.operation().get(i)));
            }
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
        if (ctx.expression().size() > 0) {
            expression.expression1 = (Expression) this.visitExpression(ctx.expression().get(0));
        }
        if (ctx.expression().size() > 1) {
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
        if(ctx.operation()!=null) {
            for_statement.operations = new ArrayList<>();
            for (int i = 0; i < ctx.operation().size(); i++) {
                for_statement.operations.add((Operation) this.visitOperation(ctx.operation().get(i)));
            }
        }
        return for_statement;
    }

    @Override
    public Node visitFunc_call(xmlParser.Func_callContext ctx) {
        Func_Call func_call = new Func_Call();
        if (ctx.TYPE() != null) {
            switch (ctx.TYPE().getText()) {
                case "document" -> func_call.type = Type.DOCUMENT;
                case "node" -> func_call.type = Type.NODE;
                case "attribute" -> func_call.type = Type.ATTRIBUTE;
                case "string" -> func_call.type = Type.STRING;
                case "int" -> func_call.type = Type.INT;
                case "float" -> func_call.type = Type.FLOAT;
            }
        }
        if (ctx.NAME()!=null) {
            func_call.name = new Name();
            func_call.name.name = ctx.NAME().getText();
        }
        if (ctx.params()!=null){
            func_call.params = (Params) this.visitParams(ctx.params());
        }
        return func_call;
    }

    @Override
    public Node visitGet_operation(xmlParser.Get_operationContext ctx) {
        Get_Operation get_operation = new Get_Operation();
        if (ctx.get_array_elem()!=null) {
            get_operation.get_array_elem = (Get_Array_Elem) this.visitGet_array_elem(ctx.get_array_elem());
        }
        if (ctx.get_elem()!=null) {
            get_operation.get_elem = (Get_Elem) this.visitGet_elem(ctx.get_elem());
        }
        if (ctx.func_call()!=null) {
            get_operation.func_call = (Func_Call) this.visitFunc_call(ctx.func_call());
        }
        return get_operation;
    }

    @Override
    public Node visitIf_block(xmlParser.If_blockContext ctx) {
        If_Block if_block = new If_Block();
        if_block.condition = (Condition) this.visitCondition(ctx.condition());
        if(ctx.operation()!=null) {
            if_block.operations = new ArrayList<>();
            for (int i = 0; i < ctx.operation().size(); i++) {
                if_block.operations.add((Operation) this.visitOperation(ctx.operation().get(i)));
            }
        }
        return if_block;
    }

    @Override
    public Node visitIf_statement(xmlParser.If_statementContext ctx) {
        If_Statement if_statement = new If_Statement();
        if_statement.if_block = (If_Block) this.visitIf_block(ctx.if_block());
        if (ctx.else_block()!=null){
            if_statement.else_block = (Else_Block) this.visitElse_block(ctx.else_block());
        }
        if(ctx.else_if_block()!=null) {
            if_statement.else_if_blocks = new ArrayList<>();
            for (int i = 0; i < ctx.else_if_block().size(); i++) {
                if_statement.else_if_blocks.add((Else_If_Block) this.visitElse_if_block(ctx.else_if_block().get(i)));
            }
        }
        return if_statement;
    }

    @Override
    public Node visitInitialise_func(xmlParser.Initialise_funcContext ctx) {
        Initialise_Func initialise_func = new Initialise_Func();
        if(ctx.NAME()!=null){
            initialise_func.name = new Name();
            initialise_func.name.name = ctx.NAME().get(0).getText();
        }
        switch (ctx.TYPE().get(0).getText()) {
            case "document" -> initialise_func.type = Type.DOCUMENT;
            case "node" -> initialise_func.type = Type.NODE;
            case "attribute" -> initialise_func.type = Type.ATTRIBUTE;
            case "string" -> initialise_func.type = Type.STRING;
            case "int" -> initialise_func.type = Type.INT;
            case "float" -> initialise_func.type = Type.FLOAT;
        }
        if(ctx.TYPE().get(1)!=null){
            initialise_func.typesOfVar = new ArrayList<>();
            for(int i =1; i< ctx.TYPE().size();i++){
                switch (ctx.TYPE().get(i).getText()) {
                    case "document" -> initialise_func.typesOfVar.add(Type.DOCUMENT);
                    case "node" -> initialise_func.typesOfVar.add(Type.NODE);
                    case "attribute" -> initialise_func.typesOfVar.add(Type.ATTRIBUTE);
                    case "string" -> initialise_func.typesOfVar.add(Type.STRING);
                    case "int" -> initialise_func.typesOfVar.add(Type.INT);
                    case "float" -> initialise_func.typesOfVar.add(Type.FLOAT);
                }
            }
        }
        if (ctx.NAME().get(1)!=null){
            initialise_func.namesOfVar = new ArrayList<>();
            for (int i = 1; i < ctx.NAME().size(); i++){
                Name name = new Name();
                name.name = ctx.NAME().get(i).getText();
                initialise_func.namesOfVar.add(name);
            }
        }
        if (ctx.operation()!=null){
            initialise_func.operations = new ArrayList<>();
            for (int i = 0; i < ctx.operation().size(); i++){
                initialise_func.operations.add((Operation) this.visitOperation(ctx.operation().get(i)));
            }
        }
        return initialise_func;
    }

    @Override
    public Node visitInitialise_var(xmlParser.Initialise_varContext ctx) {
        Initialise_Var initialise_var = new Initialise_Var();
        if (ctx.NAME()!=null){
            initialise_var.name = new Name();
            initialise_var.name.name = ctx.NAME().getText();
        }
        switch (ctx.TYPE().get(0).getText()) {
            case "document" -> initialise_var.type = Type.DOCUMENT;
            case "node" -> initialise_var.type = Type.NODE;
            case "attribute" -> initialise_var.type = Type.ATTRIBUTE;
            case "string" -> initialise_var.type = Type.STRING;
            case "int" -> initialise_var.type = Type.INT;
            case "float" -> initialise_var.type = Type.FLOAT;
        }
        initialise_var.expression = (Expression) this.visitExpression(ctx.expression());
        return initialise_var;
    }

    @Override
    public Node visitParams(xmlParser.ParamsContext ctx) {
        Params params = new Params();
        params.expressions = new ArrayList<>();
        for(int i = 0; i < ctx.expression().size(); i++){
            params.expressions.add((Expression) this.visitExpression(ctx.expression().get(i)));
        }
        return params;
    }

    @Override
    public Node visitRange_statement(xmlParser.Range_statementContext ctx) {
        Range_Statement range_statement = new Range_Statement();
        switch (ctx.TYPE().getText()) {
            case "document" -> range_statement.type = Type.DOCUMENT;
            case "node" -> range_statement.type = Type.NODE;
            case "attribute" -> range_statement.type = Type.ATTRIBUTE;
            case "string" -> range_statement.type = Type.STRING;
            case "int" -> range_statement.type = Type.INT;
            case "float" -> range_statement.type = Type.FLOAT;
        }
        if (ctx.NAME().get(0)!=null){
            range_statement.name1 = new Name();
            range_statement.name1.name = ctx.NAME().get(0).getText();
        }
        if (ctx.NAME().get(1)!=null){
            range_statement.name2 = new Name();
            range_statement.name2.name = ctx.NAME().get(0).getText();
        }
        return range_statement;
    }

    @Override
    public Node visitWhile_statement(xmlParser.While_statementContext ctx) {
        While_Statement while_statement = new While_Statement();
        while_statement.condition = (Condition) this.visitCondition(ctx.condition());
        if(ctx.operation()!=null){
            while_statement.operations = new ArrayList<>();
            for (int i = 0; i < ctx.operation().size(); i++){
                while_statement.operations.add((Operation) this.visitOperation(ctx.operation().get(i)));
            }
        }
        return while_statement;
    }

    @Override
    public Node visitType_cast(xmlParser.Type_castContext ctx) {
        Type_Cast type_cast = new Type_Cast();
        if(ctx.NAME().get(0)!=null){
            type_cast.name1 = new Name();
            type_cast.name1.name = ctx.NAME().get(0).getText();
        }
        if(ctx.NAME().get(1)!=null){
            type_cast.name2 = new Name();
            type_cast.name2.name = ctx.NAME().get(1).getText();
        }
        switch (ctx.TYPE().getText()) {
            case "document" -> type_cast.type = Type.DOCUMENT;
            case "node" -> type_cast.type = Type.NODE;
            case "attribute" -> type_cast.type = Type.ATTRIBUTE;
            case "string" -> type_cast.type = Type.STRING;
            case "int" -> type_cast.type = Type.INT;
            case "float" -> type_cast.type = Type.FLOAT;
        }
        return type_cast;
    }

    @Override
    public Node visitXml(xmlParser.XmlContext ctx) {
        XML xml = new XML();
        if (ctx.operation()!=null){
            xml.operations = new ArrayList<>();
            for(int i = 0; i < ctx.operation().size(); i++){
                xml.operations.add((Operation) this.visitOperation(ctx.operation().get(i)));
            }
        }
        if (ctx.initialise_func()!=null){
            xml.initialise_funcs = new ArrayList<>();
            for(int i = 0; i < ctx.initialise_func().size(); i++){
                xml.initialise_funcs.add((Initialise_Func) this.visitInitialise_func(ctx.initialise_func().get(i)));
            }
        }
        return xml;
    }

}