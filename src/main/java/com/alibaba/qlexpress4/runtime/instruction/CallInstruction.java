package com.alibaba.qlexpress4.runtime.instruction;

import com.alibaba.qlexpress4.QLOptions;
import com.alibaba.qlexpress4.exception.ErrorReporter;
import com.alibaba.qlexpress4.runtime.QResult;
import com.alibaba.qlexpress4.runtime.QRuntime;

/**
 * @Operation: call a lambda with fixed number of arguments
 * @Input: ${argNum} + 1
 * @Output: 1, lambda return result
 *
 * Author: DQinYuan
 */
public class CallInstruction extends QLInstruction {

    private final int argNum;

    public CallInstruction(ErrorReporter errorReporter, int argNum) {
        super(errorReporter);
        this.argNum = argNum;
    }

    @Override
    public QResult execute(QRuntime qRuntime, QLOptions qlOptions) {
        return QResult.CONTINUE_RESULT;
    }
}
