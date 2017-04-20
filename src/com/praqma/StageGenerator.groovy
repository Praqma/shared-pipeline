package com.praqma

class StageGenerator implements Serializable {

    static def getStage() {
        return "stage('my-stage') { echo 'helloooooeee' } "
    }

    static def Closure getStageAsClosure() {
        return { stage("hahah") { steps { echo 'hello' } } }
    }
}