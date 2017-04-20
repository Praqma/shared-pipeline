package com.praqma

class StageGenerator {
    static def getStageClosure() {
        return { stage("hello") { echo 'foobar' } }
    }
}