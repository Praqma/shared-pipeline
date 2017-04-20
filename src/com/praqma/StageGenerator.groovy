package com.praqma

class StageGenerator {
    static def getStageClosure(context) {
        println context
        return context.stage("hello") { it.echo 'foobar' }
    }


    static def echoThis(context) {
        context.echo "HELLO FROM CONTEXT"
    }
}