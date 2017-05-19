def call(stageName, tOut=5, closure) {
   def isTriggeredByUser = false
   def userId = ""
   // Identify if build was triggered by user
   for (def cause: currentBuild.rawBuild.getCauses()) {
       // User pressed rebuild button
       if (cause instanceof hudson.model.Cause.UserIdCause) {
           isTriggeredByUser = true
           userId = cause.getUserId()
       }
       // User pressed retrigger button
       if (isClassExists("com.sonyericsson.hudson.plugins.gerrit.trigger.hudsontrigger.GerritUserCause")) {
           if (cause instanceof com.sonyericsson.hudson.plugins.gerrit.trigger.hudsontrigger.GerritUserCause) {
               isTriggeredByUser = true
               userId = cause.getUserName()
           }
       }
   }
   stage(stageName) {
       def userInput = false
       // if build is triggered by user then ask if user wants to skip the stage
       if (isTriggeredByUser == true) {
           echo "Pipeline started by user. Ask if user wants to skip stage ${stageName}"
           msg = "Pipeline was triggered by user ${userId}. \nBecause of that you have an option to skip stages\n Will continue execution in ${tOut} min if no answer given"
           inputParams = [[$class: 'BooleanParameterDefinition', defaultValue: false, description: '', name: "Skip stage ${stageName}"]]
           userInput = askUserToSkipStageWithTimeOut(msg, inputParams, tOut)
       }
       // User choose to skip the stage
       if (userInput == true) {
           echo "User decided to skip the stage"
       } else {
           // Otherwise we run it
           closure.call()
       }
   }
}

def isClassExists(def classPath) {
    try {
        classname = Class.forName(classPath)
        return true
    } catch (ClassNotFoundException e) {
        return false
    }
}

def askUserToSkipStageWithTimeOut(def msg, def inputParams, def tOut) {
    def userInput = false
    try {
        timeout(time: tOut, unit: 'MINUTES') {
            userInput = input(message: msg, parameters: inputParams)
        }
    } catch(error) {
        def user = error.getCauses()[0].getUser()
        if('SYSTEM' == user.toString()) { // SYSTEM means timeout.
            echo "Timeout. Return false"
            return null
        } else {
            echo "Aborted by: [${user}]"
            throw error
        }
    }
    return userInput
}
