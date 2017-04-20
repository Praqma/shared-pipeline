package com.praqma

class StageGenerator {
    static def getStageClosure(context) {
        return context.stage("hello") { echo 'foobar' }
    }

    static def echoThis(context) {
        context.echo "HELLO FROM CONTEXT"
    }
}