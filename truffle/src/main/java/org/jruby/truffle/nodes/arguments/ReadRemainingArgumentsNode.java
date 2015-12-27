/*
 * Copyright (c) 2015 Oracle and/or its affiliates. All rights reserved. This
 * code is released under a tri EPL/GPL/LGPL license. You can use it,
 * redistribute it and/or modify it under the terms of the:
 *
 * Eclipse Public License version 1.0
 * GNU General Public License version 2
 * GNU Lesser General Public License version 2.1
 */
package org.jruby.truffle.nodes.arguments;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.utilities.ConditionProfile;
import com.oracle.truffle.api.source.SourceSection;
import org.jruby.truffle.nodes.RubyNode;
import org.jruby.truffle.runtime.RubyArguments;
import org.jruby.truffle.runtime.RubyContext;

public class ReadRemainingArgumentsNode extends RubyNode {

    private final int start;
    private final ConditionProfile remainingArguments = ConditionProfile.createBinaryProfile();

    public ReadRemainingArgumentsNode(RubyContext context, SourceSection sourceSection, int start) {
        super(context, sourceSection);
        this.start = start;
    }

    @Override
    public Object[] executeObjectArray(VirtualFrame frame) {
        final int count = RubyArguments.getUserArgumentsCount(frame.getArguments());

        if (remainingArguments.profile(start < count)) {
            return RubyArguments.extractUserArgumentsFrom(frame.getArguments(), start);
        } else {
            return new Object[] {};
        }
    }

    @Override
    public Object execute(VirtualFrame frame) {
        return executeObjectArray(frame);
    }

}
