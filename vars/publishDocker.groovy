def call(args) {
    def tagLatest = args.tagLatest != null ? args.tagLatest : true
    def dockerFiles = args.dockerFiles ?: "."
    assert args.credentialsId != null
    assert args.dockerTag != null

    try { 
        withCredentials([usernamePassword(credentialsId: args.credentialsId, passwordVariable: 'docker_pw', usernameVariable: 'docker_user')]) {
            sh 'echo $docker_pw | docker login --username $docker_user --password-stdin'
            def rez = sh script: "docker build ${args.dockerFiles}", returnStdout: true
            def matchedId = (rez =~ /(?m)^Successfully built ([a-z0-9]{12})$/)[0][1]
            echo "Built image with id $matchedId"
            echo "sh docker tag"
            if(tagLatest) {
                echo "sh docker tag $matchedId $dockerTag:latest"
            }

        }        
    } finally {
        sh 'docker logout'
    }
}