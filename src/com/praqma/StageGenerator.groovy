class StageGenerator {
    public static String returParameter(String param) {
        return param
    }

    public static String getStage() {
        return "stage('my-stage') { echo 'helloooooeee' } "
    }

    public static Closure getStageAsClosure() {
        return { stage("hahah") { echo 'hello' } }
    }
}