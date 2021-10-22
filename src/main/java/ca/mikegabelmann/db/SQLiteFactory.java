package ca.mikegabelmann.db;

import ca.mgabelmann.db.antlr.sqlite.SQLiteLexer;
import ca.mgabelmann.db.antlr.sqlite.SQLiteParser;
import ca.mgabelmann.db.antlr.sqlite.SQLiteParserListener;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;


public class SQLiteFactory {
    /** Logger. */
    private static final Logger LOG = LogManager.getLogger(SQLiteFactory.class);


    public void parseFile(InputStream is) throws IOException {
        SQLiteLexer lexer = new SQLiteLexer(new ANTLRInputStream(is));
        SQLiteParser parser = new SQLiteParser(new CommonTokenStream(lexer));

        parser.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
                throw new IllegalStateException("failed to parse at line " + line + " due to " + msg, e);
            }
        });

        parser.addParseListener(new SQLiteParserListener() {
            @Override
            public void enterParse(SQLiteParser.ParseContext ctx) {
                System.out.println("starting parse");
            }

            @Override
            public void exitParse(SQLiteParser.ParseContext ctx) {
                System.out.println("exiting parse");
            }

            @Override
            public void enterSql_stmt_list(SQLiteParser.Sql_stmt_listContext ctx) {

            }

            @Override
            public void exitSql_stmt_list(SQLiteParser.Sql_stmt_listContext ctx) {

            }

            @Override
            public void enterSql_stmt(SQLiteParser.Sql_stmtContext ctx) {

            }

            @Override
            public void exitSql_stmt(SQLiteParser.Sql_stmtContext ctx) {

            }

            @Override
            public void enterAlter_table_stmt(SQLiteParser.Alter_table_stmtContext ctx) {

            }

            @Override
            public void exitAlter_table_stmt(SQLiteParser.Alter_table_stmtContext ctx) {

            }

            @Override
            public void enterAnalyze_stmt(SQLiteParser.Analyze_stmtContext ctx) {

            }

            @Override
            public void exitAnalyze_stmt(SQLiteParser.Analyze_stmtContext ctx) {

            }

            @Override
            public void enterAttach_stmt(SQLiteParser.Attach_stmtContext ctx) {

            }

            @Override
            public void exitAttach_stmt(SQLiteParser.Attach_stmtContext ctx) {

            }

            @Override
            public void enterBegin_stmt(SQLiteParser.Begin_stmtContext ctx) {

            }

            @Override
            public void exitBegin_stmt(SQLiteParser.Begin_stmtContext ctx) {

            }

            @Override
            public void enterCommit_stmt(SQLiteParser.Commit_stmtContext ctx) {

            }

            @Override
            public void exitCommit_stmt(SQLiteParser.Commit_stmtContext ctx) {

            }

            @Override
            public void enterRollback_stmt(SQLiteParser.Rollback_stmtContext ctx) {

            }

            @Override
            public void exitRollback_stmt(SQLiteParser.Rollback_stmtContext ctx) {

            }

            @Override
            public void enterSavepoint_stmt(SQLiteParser.Savepoint_stmtContext ctx) {

            }

            @Override
            public void exitSavepoint_stmt(SQLiteParser.Savepoint_stmtContext ctx) {

            }

            @Override
            public void enterRelease_stmt(SQLiteParser.Release_stmtContext ctx) {

            }

            @Override
            public void exitRelease_stmt(SQLiteParser.Release_stmtContext ctx) {

            }

            @Override
            public void enterCreate_index_stmt(SQLiteParser.Create_index_stmtContext ctx) {

            }

            @Override
            public void exitCreate_index_stmt(SQLiteParser.Create_index_stmtContext ctx) {

            }

            @Override
            public void enterIndexed_column(SQLiteParser.Indexed_columnContext ctx) {

            }

            @Override
            public void exitIndexed_column(SQLiteParser.Indexed_columnContext ctx) {

            }

            @Override
            public void enterCreate_table_stmt(SQLiteParser.Create_table_stmtContext ctx) {

            }

            @Override
            public void exitCreate_table_stmt(SQLiteParser.Create_table_stmtContext ctx) {
                LOG.debug("table={}", ctx.table_name().getText());


            }

            @Override
            public void enterColumn_def(SQLiteParser.Column_defContext ctx) {

            }

            @Override
            public void exitColumn_def(SQLiteParser.Column_defContext ctx) {
                //LOG.trace("Column_defContext, text={}", ctx.getText());

                LOG.debug("columnName={}, typeName={}", ctx.column_name().getText(), ctx.type_name().getText());

                for (SQLiteParser.Column_constraintContext constraint : ctx.column_constraint()) {
                    LOG.debug("\tconstraint={}", constraint.getText());
                }
            }

            @Override
            public void enterType_name(SQLiteParser.Type_nameContext ctx) {

            }

            @Override
            public void exitType_name(SQLiteParser.Type_nameContext ctx) {

            }

            @Override
            public void enterColumn_constraint(SQLiteParser.Column_constraintContext ctx) {

            }

            @Override
            public void exitColumn_constraint(SQLiteParser.Column_constraintContext ctx) {

            }

            @Override
            public void enterSigned_number(SQLiteParser.Signed_numberContext ctx) {

            }

            @Override
            public void exitSigned_number(SQLiteParser.Signed_numberContext ctx) {

            }

            @Override
            public void enterTable_constraint(SQLiteParser.Table_constraintContext ctx) {

            }

            @Override
            public void exitTable_constraint(SQLiteParser.Table_constraintContext ctx) {
                //LOG.trace("Table_constraintContext, text={}", ctx.getText());

                /*
                PRIMARY KEY (contact_id, group_id),

                FOREIGN KEY (local_id)
                  REFERENCES contacts (remote_id)
                    ON DELETE CASCADE
                    ON UPDATE NO ACTION,
                */

                //primary key columns
                if (ctx.indexed_column().size() > 0) {
                    LOG.debug("primary key");

                    for (SQLiteParser.Indexed_columnContext record : ctx.indexed_column()) {
                        LOG.debug("\tcolumn={}", record.getText());
                    }
                }

                if (ctx.foreign_key_clause() != null) {
                    LOG.debug("foreign key, table={}", ctx.foreign_key_clause().foreign_table().getText());

                    for (SQLiteParser.Column_nameContext record : ctx.foreign_key_clause().column_name()) {
                        LOG.debug("\tforeign column={}", record.getText());
                    }

                    for (SQLiteParser.Column_nameContext record : ctx.column_name()) {
                        LOG.debug("\tlocal column={}", record.getText());
                    }
                }
            }

            @Override
            public void enterForeign_key_clause(SQLiteParser.Foreign_key_clauseContext ctx) {

            }

            @Override
            public void exitForeign_key_clause(SQLiteParser.Foreign_key_clauseContext ctx) {

            }

            @Override
            public void enterConflict_clause(SQLiteParser.Conflict_clauseContext ctx) {

            }

            @Override
            public void exitConflict_clause(SQLiteParser.Conflict_clauseContext ctx) {

            }

            @Override
            public void enterCreate_trigger_stmt(SQLiteParser.Create_trigger_stmtContext ctx) {

            }

            @Override
            public void exitCreate_trigger_stmt(SQLiteParser.Create_trigger_stmtContext ctx) {

            }

            @Override
            public void enterCreate_view_stmt(SQLiteParser.Create_view_stmtContext ctx) {

            }

            @Override
            public void exitCreate_view_stmt(SQLiteParser.Create_view_stmtContext ctx) {

            }

            @Override
            public void enterCreate_virtual_table_stmt(SQLiteParser.Create_virtual_table_stmtContext ctx) {

            }

            @Override
            public void exitCreate_virtual_table_stmt(SQLiteParser.Create_virtual_table_stmtContext ctx) {

            }

            @Override
            public void enterWith_clause(SQLiteParser.With_clauseContext ctx) {

            }

            @Override
            public void exitWith_clause(SQLiteParser.With_clauseContext ctx) {

            }

            @Override
            public void enterCte_table_name(SQLiteParser.Cte_table_nameContext ctx) {

            }

            @Override
            public void exitCte_table_name(SQLiteParser.Cte_table_nameContext ctx) {

            }

            @Override
            public void enterRecursive_cte(SQLiteParser.Recursive_cteContext ctx) {

            }

            @Override
            public void exitRecursive_cte(SQLiteParser.Recursive_cteContext ctx) {

            }

            @Override
            public void enterCommon_table_expression(SQLiteParser.Common_table_expressionContext ctx) {

            }

            @Override
            public void exitCommon_table_expression(SQLiteParser.Common_table_expressionContext ctx) {

            }

            @Override
            public void enterDelete_stmt(SQLiteParser.Delete_stmtContext ctx) {

            }

            @Override
            public void exitDelete_stmt(SQLiteParser.Delete_stmtContext ctx) {

            }

            @Override
            public void enterDelete_stmt_limited(SQLiteParser.Delete_stmt_limitedContext ctx) {

            }

            @Override
            public void exitDelete_stmt_limited(SQLiteParser.Delete_stmt_limitedContext ctx) {

            }

            @Override
            public void enterDetach_stmt(SQLiteParser.Detach_stmtContext ctx) {

            }

            @Override
            public void exitDetach_stmt(SQLiteParser.Detach_stmtContext ctx) {

            }

            @Override
            public void enterDrop_stmt(SQLiteParser.Drop_stmtContext ctx) {

            }

            @Override
            public void exitDrop_stmt(SQLiteParser.Drop_stmtContext ctx) {

            }

            @Override
            public void enterExpr(SQLiteParser.ExprContext ctx) {

            }

            @Override
            public void exitExpr(SQLiteParser.ExprContext ctx) {

            }

            @Override
            public void enterRaise_function(SQLiteParser.Raise_functionContext ctx) {

            }

            @Override
            public void exitRaise_function(SQLiteParser.Raise_functionContext ctx) {

            }

            @Override
            public void enterLiteral_value(SQLiteParser.Literal_valueContext ctx) {

            }

            @Override
            public void exitLiteral_value(SQLiteParser.Literal_valueContext ctx) {

            }

            @Override
            public void enterInsert_stmt(SQLiteParser.Insert_stmtContext ctx) {

            }

            @Override
            public void exitInsert_stmt(SQLiteParser.Insert_stmtContext ctx) {

            }

            @Override
            public void enterUpsert_clause(SQLiteParser.Upsert_clauseContext ctx) {

            }

            @Override
            public void exitUpsert_clause(SQLiteParser.Upsert_clauseContext ctx) {

            }

            @Override
            public void enterPragma_stmt(SQLiteParser.Pragma_stmtContext ctx) {

            }

            @Override
            public void exitPragma_stmt(SQLiteParser.Pragma_stmtContext ctx) {

            }

            @Override
            public void enterPragma_value(SQLiteParser.Pragma_valueContext ctx) {

            }

            @Override
            public void exitPragma_value(SQLiteParser.Pragma_valueContext ctx) {

            }

            @Override
            public void enterReindex_stmt(SQLiteParser.Reindex_stmtContext ctx) {

            }

            @Override
            public void exitReindex_stmt(SQLiteParser.Reindex_stmtContext ctx) {

            }

            @Override
            public void enterSelect_stmt(SQLiteParser.Select_stmtContext ctx) {

            }

            @Override
            public void exitSelect_stmt(SQLiteParser.Select_stmtContext ctx) {

            }

            @Override
            public void enterJoin_clause(SQLiteParser.Join_clauseContext ctx) {

            }

            @Override
            public void exitJoin_clause(SQLiteParser.Join_clauseContext ctx) {

            }

            @Override
            public void enterSelect_core(SQLiteParser.Select_coreContext ctx) {

            }

            @Override
            public void exitSelect_core(SQLiteParser.Select_coreContext ctx) {

            }

            @Override
            public void enterFactored_select_stmt(SQLiteParser.Factored_select_stmtContext ctx) {

            }

            @Override
            public void exitFactored_select_stmt(SQLiteParser.Factored_select_stmtContext ctx) {

            }

            @Override
            public void enterSimple_select_stmt(SQLiteParser.Simple_select_stmtContext ctx) {

            }

            @Override
            public void exitSimple_select_stmt(SQLiteParser.Simple_select_stmtContext ctx) {

            }

            @Override
            public void enterCompound_select_stmt(SQLiteParser.Compound_select_stmtContext ctx) {

            }

            @Override
            public void exitCompound_select_stmt(SQLiteParser.Compound_select_stmtContext ctx) {

            }

            @Override
            public void enterTable_or_subquery(SQLiteParser.Table_or_subqueryContext ctx) {

            }

            @Override
            public void exitTable_or_subquery(SQLiteParser.Table_or_subqueryContext ctx) {

            }

            @Override
            public void enterResult_column(SQLiteParser.Result_columnContext ctx) {

            }

            @Override
            public void exitResult_column(SQLiteParser.Result_columnContext ctx) {

            }

            @Override
            public void enterJoin_operator(SQLiteParser.Join_operatorContext ctx) {

            }

            @Override
            public void exitJoin_operator(SQLiteParser.Join_operatorContext ctx) {

            }

            @Override
            public void enterJoin_constraint(SQLiteParser.Join_constraintContext ctx) {

            }

            @Override
            public void exitJoin_constraint(SQLiteParser.Join_constraintContext ctx) {

            }

            @Override
            public void enterCompound_operator(SQLiteParser.Compound_operatorContext ctx) {

            }

            @Override
            public void exitCompound_operator(SQLiteParser.Compound_operatorContext ctx) {

            }

            @Override
            public void enterUpdate_stmt(SQLiteParser.Update_stmtContext ctx) {

            }

            @Override
            public void exitUpdate_stmt(SQLiteParser.Update_stmtContext ctx) {

            }

            @Override
            public void enterColumn_name_list(SQLiteParser.Column_name_listContext ctx) {

            }

            @Override
            public void exitColumn_name_list(SQLiteParser.Column_name_listContext ctx) {

            }

            @Override
            public void enterUpdate_stmt_limited(SQLiteParser.Update_stmt_limitedContext ctx) {

            }

            @Override
            public void exitUpdate_stmt_limited(SQLiteParser.Update_stmt_limitedContext ctx) {

            }

            @Override
            public void enterQualified_table_name(SQLiteParser.Qualified_table_nameContext ctx) {

            }

            @Override
            public void exitQualified_table_name(SQLiteParser.Qualified_table_nameContext ctx) {

            }

            @Override
            public void enterVacuum_stmt(SQLiteParser.Vacuum_stmtContext ctx) {

            }

            @Override
            public void exitVacuum_stmt(SQLiteParser.Vacuum_stmtContext ctx) {

            }

            @Override
            public void enterFilter_clause(SQLiteParser.Filter_clauseContext ctx) {

            }

            @Override
            public void exitFilter_clause(SQLiteParser.Filter_clauseContext ctx) {

            }

            @Override
            public void enterWindow_defn(SQLiteParser.Window_defnContext ctx) {

            }

            @Override
            public void exitWindow_defn(SQLiteParser.Window_defnContext ctx) {

            }

            @Override
            public void enterOver_clause(SQLiteParser.Over_clauseContext ctx) {

            }

            @Override
            public void exitOver_clause(SQLiteParser.Over_clauseContext ctx) {

            }

            @Override
            public void enterFrame_spec(SQLiteParser.Frame_specContext ctx) {

            }

            @Override
            public void exitFrame_spec(SQLiteParser.Frame_specContext ctx) {

            }

            @Override
            public void enterFrame_clause(SQLiteParser.Frame_clauseContext ctx) {

            }

            @Override
            public void exitFrame_clause(SQLiteParser.Frame_clauseContext ctx) {

            }

            @Override
            public void enterSimple_function_invocation(SQLiteParser.Simple_function_invocationContext ctx) {

            }

            @Override
            public void exitSimple_function_invocation(SQLiteParser.Simple_function_invocationContext ctx) {

            }

            @Override
            public void enterAggregate_function_invocation(SQLiteParser.Aggregate_function_invocationContext ctx) {

            }

            @Override
            public void exitAggregate_function_invocation(SQLiteParser.Aggregate_function_invocationContext ctx) {

            }

            @Override
            public void enterWindow_function_invocation(SQLiteParser.Window_function_invocationContext ctx) {

            }

            @Override
            public void exitWindow_function_invocation(SQLiteParser.Window_function_invocationContext ctx) {

            }

            @Override
            public void enterCommon_table_stmt(SQLiteParser.Common_table_stmtContext ctx) {

            }

            @Override
            public void exitCommon_table_stmt(SQLiteParser.Common_table_stmtContext ctx) {

            }

            @Override
            public void enterOrder_by_stmt(SQLiteParser.Order_by_stmtContext ctx) {

            }

            @Override
            public void exitOrder_by_stmt(SQLiteParser.Order_by_stmtContext ctx) {

            }

            @Override
            public void enterLimit_stmt(SQLiteParser.Limit_stmtContext ctx) {

            }

            @Override
            public void exitLimit_stmt(SQLiteParser.Limit_stmtContext ctx) {

            }

            @Override
            public void enterOrdering_term(SQLiteParser.Ordering_termContext ctx) {

            }

            @Override
            public void exitOrdering_term(SQLiteParser.Ordering_termContext ctx) {

            }

            @Override
            public void enterAsc_desc(SQLiteParser.Asc_descContext ctx) {

            }

            @Override
            public void exitAsc_desc(SQLiteParser.Asc_descContext ctx) {

            }

            @Override
            public void enterFrame_left(SQLiteParser.Frame_leftContext ctx) {

            }

            @Override
            public void exitFrame_left(SQLiteParser.Frame_leftContext ctx) {

            }

            @Override
            public void enterFrame_right(SQLiteParser.Frame_rightContext ctx) {

            }

            @Override
            public void exitFrame_right(SQLiteParser.Frame_rightContext ctx) {

            }

            @Override
            public void enterFrame_single(SQLiteParser.Frame_singleContext ctx) {

            }

            @Override
            public void exitFrame_single(SQLiteParser.Frame_singleContext ctx) {

            }

            @Override
            public void enterWindow_function(SQLiteParser.Window_functionContext ctx) {

            }

            @Override
            public void exitWindow_function(SQLiteParser.Window_functionContext ctx) {

            }

            @Override
            public void enterOf_OF_fset(SQLiteParser.Of_OF_fsetContext ctx) {

            }

            @Override
            public void exitOf_OF_fset(SQLiteParser.Of_OF_fsetContext ctx) {

            }

            @Override
            public void enterDefault_DEFAULT__value(SQLiteParser.Default_DEFAULT__valueContext ctx) {

            }

            @Override
            public void exitDefault_DEFAULT__value(SQLiteParser.Default_DEFAULT__valueContext ctx) {

            }

            @Override
            public void enterPartition_by(SQLiteParser.Partition_byContext ctx) {

            }

            @Override
            public void exitPartition_by(SQLiteParser.Partition_byContext ctx) {

            }

            @Override
            public void enterOrder_by_expr(SQLiteParser.Order_by_exprContext ctx) {

            }

            @Override
            public void exitOrder_by_expr(SQLiteParser.Order_by_exprContext ctx) {

            }

            @Override
            public void enterOrder_by_expr_asc_desc(SQLiteParser.Order_by_expr_asc_descContext ctx) {

            }

            @Override
            public void exitOrder_by_expr_asc_desc(SQLiteParser.Order_by_expr_asc_descContext ctx) {

            }

            @Override
            public void enterExpr_asc_desc(SQLiteParser.Expr_asc_descContext ctx) {

            }

            @Override
            public void exitExpr_asc_desc(SQLiteParser.Expr_asc_descContext ctx) {

            }

            @Override
            public void enterInitial_select(SQLiteParser.Initial_selectContext ctx) {

            }

            @Override
            public void exitInitial_select(SQLiteParser.Initial_selectContext ctx) {

            }

            @Override
            public void enterRecursive__select(SQLiteParser.Recursive__selectContext ctx) {

            }

            @Override
            public void exitRecursive__select(SQLiteParser.Recursive__selectContext ctx) {

            }

            @Override
            public void enterUnary_operator(SQLiteParser.Unary_operatorContext ctx) {

            }

            @Override
            public void exitUnary_operator(SQLiteParser.Unary_operatorContext ctx) {

            }

            @Override
            public void enterError_message(SQLiteParser.Error_messageContext ctx) {

            }

            @Override
            public void exitError_message(SQLiteParser.Error_messageContext ctx) {

            }

            @Override
            public void enterModule_argument(SQLiteParser.Module_argumentContext ctx) {

            }

            @Override
            public void exitModule_argument(SQLiteParser.Module_argumentContext ctx) {

            }

            @Override
            public void enterColumn_alias(SQLiteParser.Column_aliasContext ctx) {

            }

            @Override
            public void exitColumn_alias(SQLiteParser.Column_aliasContext ctx) {

            }

            @Override
            public void enterKeyword(SQLiteParser.KeywordContext ctx) {
                //LOG.debug("keyword={}", ctx.getText());
            }

            @Override
            public void exitKeyword(SQLiteParser.KeywordContext ctx) {

            }

            @Override
            public void enterName(SQLiteParser.NameContext ctx) {

            }

            @Override
            public void exitName(SQLiteParser.NameContext ctx) {

            }

            @Override
            public void enterFunction_name(SQLiteParser.Function_nameContext ctx) {

            }

            @Override
            public void exitFunction_name(SQLiteParser.Function_nameContext ctx) {

            }

            @Override
            public void enterSchema_name(SQLiteParser.Schema_nameContext ctx) {

            }

            @Override
            public void exitSchema_name(SQLiteParser.Schema_nameContext ctx) {

            }

            @Override
            public void enterTable_name(SQLiteParser.Table_nameContext ctx) {

            }

            @Override
            public void exitTable_name(SQLiteParser.Table_nameContext ctx) {

            }

            @Override
            public void enterTable_or_index_name(SQLiteParser.Table_or_index_nameContext ctx) {

            }

            @Override
            public void exitTable_or_index_name(SQLiteParser.Table_or_index_nameContext ctx) {

            }

            @Override
            public void enterNew_table_name(SQLiteParser.New_table_nameContext ctx) {

            }

            @Override
            public void exitNew_table_name(SQLiteParser.New_table_nameContext ctx) {

            }

            @Override
            public void enterColumn_name(SQLiteParser.Column_nameContext ctx) {

            }

            @Override
            public void exitColumn_name(SQLiteParser.Column_nameContext ctx) {

            }

            @Override
            public void enterCollation_name(SQLiteParser.Collation_nameContext ctx) {

            }

            @Override
            public void exitCollation_name(SQLiteParser.Collation_nameContext ctx) {

            }

            @Override
            public void enterForeign_table(SQLiteParser.Foreign_tableContext ctx) {

            }

            @Override
            public void exitForeign_table(SQLiteParser.Foreign_tableContext ctx) {
                //LOG.debug("text={}", ctx.getText());
            }

            @Override
            public void enterIndex_name(SQLiteParser.Index_nameContext ctx) {

            }

            @Override
            public void exitIndex_name(SQLiteParser.Index_nameContext ctx) {

            }

            @Override
            public void enterTrigger_name(SQLiteParser.Trigger_nameContext ctx) {

            }

            @Override
            public void exitTrigger_name(SQLiteParser.Trigger_nameContext ctx) {

            }

            @Override
            public void enterView_name(SQLiteParser.View_nameContext ctx) {

            }

            @Override
            public void exitView_name(SQLiteParser.View_nameContext ctx) {

            }

            @Override
            public void enterModule_name(SQLiteParser.Module_nameContext ctx) {

            }

            @Override
            public void exitModule_name(SQLiteParser.Module_nameContext ctx) {

            }

            @Override
            public void enterPragma_name(SQLiteParser.Pragma_nameContext ctx) {

            }

            @Override
            public void exitPragma_name(SQLiteParser.Pragma_nameContext ctx) {

            }

            @Override
            public void enterSavepoint_name(SQLiteParser.Savepoint_nameContext ctx) {

            }

            @Override
            public void exitSavepoint_name(SQLiteParser.Savepoint_nameContext ctx) {

            }

            @Override
            public void enterTable_alias(SQLiteParser.Table_aliasContext ctx) {

            }

            @Override
            public void exitTable_alias(SQLiteParser.Table_aliasContext ctx) {

            }

            @Override
            public void enterTransaction_name(SQLiteParser.Transaction_nameContext ctx) {

            }

            @Override
            public void exitTransaction_name(SQLiteParser.Transaction_nameContext ctx) {

            }

            @Override
            public void enterWindow_name(SQLiteParser.Window_nameContext ctx) {

            }

            @Override
            public void exitWindow_name(SQLiteParser.Window_nameContext ctx) {

            }

            @Override
            public void enterAlias(SQLiteParser.AliasContext ctx) {

            }

            @Override
            public void exitAlias(SQLiteParser.AliasContext ctx) {

            }

            @Override
            public void enterFilename(SQLiteParser.FilenameContext ctx) {

            }

            @Override
            public void exitFilename(SQLiteParser.FilenameContext ctx) {

            }

            @Override
            public void enterBase_window_name(SQLiteParser.Base_window_nameContext ctx) {

            }

            @Override
            public void exitBase_window_name(SQLiteParser.Base_window_nameContext ctx) {

            }

            @Override
            public void enterSimple_func(SQLiteParser.Simple_funcContext ctx) {

            }

            @Override
            public void exitSimple_func(SQLiteParser.Simple_funcContext ctx) {

            }

            @Override
            public void enterAggregate_func(SQLiteParser.Aggregate_funcContext ctx) {

            }

            @Override
            public void exitAggregate_func(SQLiteParser.Aggregate_funcContext ctx) {

            }

            @Override
            public void enterTable_function_name(SQLiteParser.Table_function_nameContext ctx) {

            }

            @Override
            public void exitTable_function_name(SQLiteParser.Table_function_nameContext ctx) {

            }

            @Override
            public void enterAny_name(SQLiteParser.Any_nameContext ctx) {

            }

            @Override
            public void exitAny_name(SQLiteParser.Any_nameContext ctx) {

            }

            @Override
            public void visitTerminal(TerminalNode terminalNode) {

            }

            @Override
            public void visitErrorNode(ErrorNode errorNode) {

            }

            @Override
            public void enterEveryRule(ParserRuleContext parserRuleContext) {

            }

            @Override
            public void exitEveryRule(ParserRuleContext parserRuleContext) {

            }
        });

        parser.parse();

    }
}
