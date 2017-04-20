package com.praqma

class StageGenerator {
    static def getStageClosure() {
        return { stage("hello") { echo 'foobar' } }
    }

    static def echoThis(context) {
        context.echo "HELLO FROM CONTEXT"
    }
}