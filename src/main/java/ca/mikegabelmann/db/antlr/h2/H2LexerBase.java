package ca.mikegabelmann.db.antlr.h2;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Lexer;

public abstract class H2LexerBase extends Lexer
{
    public H2LexerBase self;
    
    public H2LexerBase(CharStream input)
    {
        super(input);
        self = this;
    }

    protected boolean IsNewlineAtPos(int pos)
    {
        int la = _input.LA(pos);
        return la == -1 || la == '\n';
    }
}
