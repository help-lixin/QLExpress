package com.alibaba.qlexpress4.runtime.instruction;

import com.alibaba.qlexpress4.QLOptions;
import com.alibaba.qlexpress4.exception.ErrorReporter;
import com.alibaba.qlexpress4.runtime.LeftValue;
import com.alibaba.qlexpress4.runtime.QResult;
import com.alibaba.qlexpress4.runtime.QRuntime;

/**
 * @Operation: load variable from local to global scope, create when not exist
 * @Input: 0
 * @Output: 1 left value of local variable
 *
 * Author: DQinYuan
 */
public class LoadInstruction extends QLInstruction {

    private final String name;

    public LoadInstruction(ErrorReporter errorReporter, String name) {
        super(errorReporter);
        this.name = name;
    }

    @Override
    public QResult execute(QRuntime qRuntime, QLOptions qlOptions) {
        qRuntime.push(getOrCreateSymbol(qRuntime));
        return QResult.CONTINUE_RESULT;
    }

    private LeftValue getOrCreateSymbol(QRuntime qRuntime) {
        LeftValue symbol = qRuntime.getSymbol(name);
        if (symbol == null) {
            symbol = qRuntime.defineSymbol(name, Object.class);
        }
        return symbol;
    }
}
