package com.praqma

class StageGenerator {
    static def getStageClosure() {
        return { stage("hello") { steps { echo 'foobar' } } }
    }
}